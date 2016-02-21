package com.team3044.robotmain.RobotCode;

import com.team3044.robotmain.Reference.*;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Shooter {
	// Inputs
	boolean pickRollersIn = CommonArea.pickRollersIn;
	boolean pickRollersOut = CommonArea.pickRollersOut;
	boolean startShooterAtManualSpeed;
	boolean startShooterAtVisionSpeed;
	boolean shootBall;
	boolean startVisionShoot;
	double shooterVisionTopSpeed;
	double shooterVisionBotSpeed;

	final double toleranceShooter = 4;
	// Components.BallInLimit.get()
	// 1/Components.topTachoCounter.getPeriod(); <willgivespeedofturning
	// 1/Components.botTachoCounter.getPeriod();

	final double TRACKMOTORSPEED = 1;
	final double TOPMANUALSPEED = -.5;
	final double BOTMANUALSPEED = -.5;

	boolean lastStaging = false;
	Timer mytimer = new Timer();
	Components comp = Components.getInstance();
	FirstController controller = FirstController.getInstance();

	public enum state {
		Stopped, ingestingBoulder, ejectingBoulder, startingVisionShoot, waitingForVisionShoot, Shooting, ShootingDelay, startManualShoot, readyManualFire,
	}

	public state shooterState = state.Stopped;

	DummyTacho topCounter = comp.topTachoCounter;
	DummyTacho botCounter = comp.botTachoCounter;
	PIDController botShooterPID;
	PIDController topShooterPID;

	double topSpeed = 0;
	double botSpeed = 0;

	final double p = .001, i = 0, d = 0.003;

	public boolean OnTarget(double TargetValue, double Value, double Threshold) {
		return Math.abs(TargetValue - Value) < Threshold;

	}

	public void shooterInit() {
		shooterState = state.Stopped;
		topSpeed = 0;
		botSpeed = 0;

		comp.topShooter.setInverted(true);
		comp.botShooter.setInverted(true);
		topCounter.setDistancePerPulse(1);
		botCounter.setDistancePerPulse(1);
		topCounter.setPIDSourceType(PIDSourceType.kRate);
		botCounter.setPIDSourceType(PIDSourceType.kRate);
		topShooterPID = new PIDController(p, i, d, topCounter, comp.topShooter);
		botShooterPID = new PIDController(p, i, d, botCounter, comp.botShooter);
		topShooterPID.setInputRange(0, 150);
		botShooterPID.setInputRange(0, 150);
		topShooterPID.setOutputRange(0, 1);
		botShooterPID.setOutputRange(0, 1);

		topShooterPID.enable();
		botShooterPID.enable();
		topShooterPID.setPID(p, i, d);
		botShooterPID.setPID(p, i, d);

		topShooterPID.setAbsoluteTolerance(toleranceShooter);
		botShooterPID.setAbsoluteTolerance(toleranceShooter);
		comp.topShooter.setPIDSourceType(PIDSourceType.kRate);
		comp.botShooter.setPIDSourceType(PIDSourceType.kRate);

	}

	public void shooterAutoPeriodic() {

	}

	public void shooterTeleopPeriodic() {
		pickRollersIn = CommonArea.pickRollersIn;
		pickRollersOut = CommonArea.pickRollersOut;
		/*
		 * SmartDashboard.putString("DB/String 3",
		 * String.valueOf(topShooterPID.onTarget())); SmartDashboard.putString(
		 * "DB/String 4", String.valueOf(botShooterPID.onTarget()));
		 * SmartDashboard.putString("DB/String 5",
		 * String.valueOf(topCounter.getRate())); SmartDashboard.putString(
		 * "DB/String 6", String.valueOf(botCounter.getRate()));
		 */
		topSpeed = SmartDashboard.getNumber("DB/Slider 2");
		botSpeed = SmartDashboard.getNumber("DB/Slider 3");

		startShooterAtManualSpeed = FirstController.getInstance().getTriggerLeft();
		shootBall = CommonArea.shootFlag;
		startVisionShoot = CommonArea.shooterMotorFlag;
		shooterVisionTopSpeed = 110;
		shooterVisionBotSpeed = 90;
		if (CommonArea.shooterInit) {
			shooterInit();
		}
		switch (shooterState) {

		case Stopped:

			if (startVisionShoot) {
				topShooterPID.setSetpoint(shooterVisionTopSpeed);
				botShooterPID.setSetpoint(shooterVisionBotSpeed);
				shooterState = state.startingVisionShoot;
				System.out.println("VISION SHOOT");
			} else if (startShooterAtManualSpeed) {
				topShooterPID.setSetpoint(topSpeed);
				botShooterPID.setSetpoint(botSpeed);
				shooterState = state.startManualShoot;
			} else if (pickRollersIn && !comp.BallInLimit.get()) {
				comp.shooterTrack.set(-TRACKMOTORSPEED);
				shooterState = state.ingestingBoulder;
			} else if (pickRollersOut) {
				comp.shooterTrack.set(TRACKMOTORSPEED);
				shooterState = state.ejectingBoulder;
			}

			break;

		case ingestingBoulder:
			if (!pickRollersIn || comp.BallInLimit.get()) {
				comp.shooterTrack.set(0);
				shooterState = state.Stopped;
			}
			break;

		case ejectingBoulder:
			if (!pickRollersOut) {
				comp.shooterTrack.set(0);
				shooterState = state.Stopped;
			}
			break;

		case startingVisionShoot:
			System.out.println("IN VISION SHOOT");
			if (shootBall) {
				comp.shooterTrack.set(-TRACKMOTORSPEED);
				shooterState = state.Shooting;
			} else if (!startVisionShoot) {
				topShooterPID.setSetpoint(0);
				botShooterPID.setSetpoint(0);
				shooterState = state.Stopped;
			} 
			
			if (OnTarget(topShooterPID.getSetpoint(), topCounter.getRate(), 5)
					&& OnTarget(botShooterPID.getSetpoint(), botCounter.getRate(), 5)) {
				CommonArea.isUpToSpeed = true;

			} else {
				CommonArea.isUpToSpeed = false;
			}
			break;

		case startManualShoot:

			if (!startShooterAtManualSpeed) {
				topShooterPID.setSetpoint(0);
				botShooterPID.setSetpoint(0);
				shooterState = state.Stopped;

			} else if (CommonArea.isUpToSpeed == true) {
				shooterState = state.readyManualFire;

			} else if (OnTarget(topShooterPID.getSetpoint(), topCounter.getRate(), 5)
					&& OnTarget(botShooterPID.getSetpoint(), botCounter.getRate(), 5)) {
				System.out.println("Up to speed");
				CommonArea.isUpToSpeed = true;

			} else {
				CommonArea.isUpToSpeed = false;
				System.out.println("TARGET: " + botShooterPID.getSetpoint() + "\n" + botCounter.getRate() + "\n"
						+ OnTarget(botShooterPID.getSetpoint(), botCounter.getRate(), 5));
			}
			break;

		case readyManualFire:
			System.out.println("READY To fire");

			if (!OnTarget(topShooterPID.getSetpoint(), topCounter.getRate(), 5)
					|| !OnTarget(botShooterPID.getSetpoint(), botCounter.getRate(), 5)) {
				shooterState = state.startManualShoot;
			} else if (CommonArea.manualFire) {
				comp.shooterTrack.set(-TRACKMOTORSPEED);
				shooterState = state.Shooting;
			} else if (!startShooterAtManualSpeed) {
				comp.shooterTrack.set(0);
				topShooterPID.setSetpoint(0);
				botShooterPID.setSetpoint(0);
				this.shooterState = state.Stopped;
			}
			break;
		case Shooting:
			if (!comp.BallInLimit.get()) {
				mytimer.reset();
				mytimer.start();
				shooterState = state.ShootingDelay;
			} else if (!startVisionShoot) {
				topShooterPID.setSetpoint(0);
				botShooterPID.setSetpoint(0);
				shooterState = state.Stopped;
			}

			break;

		case ShootingDelay:
			if (!this.startShooterAtManualSpeed && !startVisionShoot) {
				CommonArea.isShot = true;
				comp.shooterTrack.set(0);
				topShooterPID.setSetpoint(0);
				botShooterPID.setSetpoint(0);
				mytimer.stop();
				shooterState = state.Stopped;
			} /*
				 * else if (!FirstController.getInstance().getTriggerRight()) {
				 * CommonArea.isShot = true; comp.shooterTrack.set(0);
				 * topShooterPID.setSetpoint(0); botShooterPID.setSetpoint(0);
				 * shooterState = state.Stopped; }
				 */
			break;

		}
	}

	public void shooterTestPeriodic() {
		/*
		 * if (controller.getRawButton(controller.BUTTON_LB)) {
		 * comp.topShooter.set(SmartDashboard.getDouble("DB/Slider 0"));
		 * comp.botShooter.set(SmartDashboard.getDouble("DB/Slider 1")); } else
		 * { comp.topShooter.set(0); comp.botShooter.set(0); }
		 * 
		 * SmartDashboard.putString("DB/String 2", String.valueOf((1 /
		 * Components .getInstance().topTachoCounter.getPeriod())));
		 * SmartDashboard.putString("DB/String 3", String.valueOf((1 /
		 * Components .getInstance().botTachoCounter.getPeriod())));
		 * 
		 * 
		 * comp.topShooter.set(controller.getRightY());
		 * comp.botShooter.set(controller.getLeftY()); SmartDashboard.putString(
		 * "DB/String 0", String.valueOf((1 / Components
		 * .getInstance().topTachoCounter.getPeriod())));
		 * SmartDashboard.putString("DB/String 1", String.valueOf((1 /
		 * Components .getInstance().botTachoCounter.getPeriod())));
		 */
	}

}
