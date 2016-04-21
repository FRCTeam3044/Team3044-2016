package com.team3044.robotmain.RobotCode;

import com.team3044.robotmain.Reference.CommonArea;
import com.team3044.robotmain.Reference.Components;
import com.team3044.robotmain.Reference.DummyTacho;
import com.team3044.robotmain.Reference.FirstController;
import com.team3044.robotmain.Reference.SecondaryController;

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
	double shooterVisionTopSpeed = 110;
	double shooterVisionBotSpeed = 85;

	double lowShootSpeed = .5;

	final double toleranceShooter = 4;
	// Components.BallInLimit.get()
	// 1/Components.topTachoCounter.getPeriod(); <willgivespeedofturning
	// 1/Components.botTachoCounter.getPeriod();

	final double TRACKMOTORSPEED = .9;
	final double TOPMANUALSPEED = -.5;
	final double BOTMANUALSPEED = -.5;

	boolean lastStaging = false;
	Timer mytimer = new Timer();
	Components comp = Components.getInstance();
	FirstController controller = FirstController.getInstance();

	public enum state {
		Stopped, ingestingBoulder, ejectingBoulder, startingVisionShoot, EJECT_BALL_BACK, Shooting, ShootingDelay, startManualShoot, readyManualFire,
	}

	public state shooterState = state.Stopped;

	DummyTacho topCounter = comp.topTachoCounter;
	DummyTacho botCounter = comp.botTachoCounter;
	PIDController botShooterPID;
	PIDController topShooterPID;

	boolean startLowGoal = false;

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
		if (CommonArea.shooterInit) {
			shooterInit();
			shooterState = state.Stopped;
		}
		startShooterAtManualSpeed = FirstController.getInstance().getTriggerLeft()
				|| SecondaryController.getInstance().getTriggerLeft()
				|| SecondaryController.getInstance().getTriggerRight();

		if (FirstController.getInstance().getTriggerLeft() && !FirstController.getInstance().getTriggerRight()
				|| !SecondaryController.getInstance().getTriggerRight()) {
			if (SmartDashboard.getBoolean("DB/Button 0")) {
				topSpeed = 110;
				botSpeed = 85;
			} else {
				topSpeed = SmartDashboard.getNumber("DB/Slider 0", 100);
				botSpeed = SmartDashboard.getNumber("DB/Slider 2", 75);
			}

		} else {
			topSpeed = 65;
			botSpeed = 0;
		}
		if (botSpeed < 0) {

			comp.botShooter.setInverted(false);

		} else {

			comp.botShooter.setInverted(true);

		}

		shootBall = CommonArea.shootFlag;
		startVisionShoot = CommonArea.shooterMotorFlag;

		if (CommonArea.ejectBack) {
			shooterState = state.EJECT_BALL_BACK;
		}

		// Send Information About Shooter To DRIVERSTATION
		if (topShooterPID.getError() != 0) {
			SmartDashboard.putNumber("SSPEED", (topShooterPID.getSetpoint() - topShooterPID.getError()));
			SmartDashboard.putBoolean("BALL", comp.BallInLimit.get());
		}

		switch (shooterState) {

		case Stopped:

			if (startVisionShoot) {
				topShooterPID.setSetpoint(shooterVisionTopSpeed);
				botShooterPID.setSetpoint(shooterVisionBotSpeed);
				shooterState = state.startingVisionShoot;
				System.out.println("VISION SHOOT");
			} else if (startShooterAtManualSpeed || CommonArea.shooterToSpeed) {
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
			if (comp.shooterTrack.getSetpoint() != 0 && !pickRollersIn && !pickRollersOut) {
				comp.shooterTrack.set(0);
			}
			/*
			 * else if(this.startLowGoal){
			 * topShooterPID.setSetpoint(this.lowShootSpeed);
			 * botShooterPID.setSetpoint(this.lowShootSpeed); shooterState =
			 * state.startManualShoot; }
			 */

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

			if (!startShooterAtManualSpeed && !this.startLowGoal) {
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
			}
			break;
		case EJECT_BALL_BACK:
			if (!CommonArea.ejectBack) {
				comp.botShooter.set(0);
				comp.topShooter.set(0);
				topShooterPID.enable();
				botShooterPID.enable();
				shooterState = state.Stopped;
			}
			break;
		default:
			break;

		}
	}

	public void shooterTestPeriodic() {

	}

}
