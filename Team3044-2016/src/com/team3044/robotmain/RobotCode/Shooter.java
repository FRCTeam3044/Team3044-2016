package com.team3044.robotmain.RobotCode;

import com.team3044.robotmain.Reference.*;
import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Shooter {
	// Inputs
	boolean pickRollersIn;
	boolean pickRollersOut;
	boolean startShooterAtVisionSpeed;
	boolean shootBall;
	boolean startVisionShoot;
	double shooterVisionTopSpeed;
	double shooterVisionBotSpeed;
	final double toleranceShooter = .5;
	// Components.BallInLimit.get()
	// 1/Components.topTachoCounter.getPeriod(); <willgivespeedofturning
	// 1/Components.botTachoCounter.getPeriod();

	// Motor Speeds
	final double TRACKMOTORSPEED = 1;
	boolean lastStaging = false;
	Timer mytimer = new Timer();

	// VISION CODE SPEEDS
	// CommonArea.shooterTopSpeed;
	// CommonArea.shooterBotSpeed;

	// States
	public enum state {
		Stopped, ingestingBoulder, ejectingBoulder, startingVisionShoot, waitingForVisionShoot, Shooting, ShootingDelay
	}

	state shooterState = state.Stopped;

	Counter topCounter = Components.getInstance().topTachoCounter;
	Counter botCounter = Components.getInstance().botTachoCounter;
	PIDController botShooterPID;
	PIDController topShooterPID;
	
	final double p = .001, i = 0, d = 0; //NEEDS TO BE SET

	public void shooterInit() {
		topCounter.setDistancePerPulse(1);
		botCounter.setDistancePerPulse(1);
		topCounter.setPIDSourceType(PIDSourceType.kRate);
		botCounter.setPIDSourceType(PIDSourceType.kRate);
		topShooterPID = new PIDController(p,i,d,topCounter,Components.getInstance().topShooter);
		botShooterPID = new PIDController(p,i,d,botCounter,Components.getInstance().botShooter);
		topShooterPID.setInputRange(0, 150);
		botShooterPID.setInputRange(0, 150);
		topShooterPID.setOutputRange(0, 1);
		botShooterPID.setOutputRange(0, 1);
		topShooterPID.enable();
		botShooterPID.enable();
		topShooterPID.setPID(p,i,d);
		botShooterPID.setPID(p,i,d);
		
		//Set 
		topShooterPID.setAbsoluteTolerance(toleranceShooter);
		botShooterPID.setAbsoluteTolerance(toleranceShooter);
		Components.getInstance().topShooter.setPIDSourceType(PIDSourceType.kRate);
		Components.getInstance().botShooter.setPIDSourceType(PIDSourceType.kRate);
	}

	public void shooterAutoPeriodic() {

	}

	public void shooterTeleopPeriodic() {
		pickRollersIn = CommonArea.pickRollersIn;
		pickRollersOut = CommonArea.pickRollersOut;
		startShooterAtVisionSpeed = CommonArea.startShooterAtSetSpeed;
		shootBall = CommonArea.shootFlag;
		startVisionShoot = CommonArea.isAligned;
		shooterVisionTopSpeed = CommonArea.shooterVisionTopSpeed;
		shooterVisionBotSpeed = CommonArea.shooterVisionBotSpeed;
		
		
		switch (shooterState) {

		case Stopped:
			if (startVisionShoot && Components.getInstance().BallInLimit.get()) {
				topShooterPID.setSetpoint(shooterVisionTopSpeed);
				botShooterPID.setSetpoint(shooterVisionBotSpeed);
				shooterState = state.startingVisionShoot;
			} else if (pickRollersIn
					&& !Components.getInstance().BallInLimit.get()) {
				Components.getInstance().shooterTrack.set(TRACKMOTORSPEED);
				shooterState = state.ingestingBoulder;
			} else if (pickRollersOut) {
				Components.getInstance().shooterTrack.set(-TRACKMOTORSPEED);
				shooterState = state.ejectingBoulder;
			}
			break;

		// PickUp
		case ingestingBoulder:
			if (Components.getInstance().BallInLimit.get() || !pickRollersIn) {
				Components.getInstance().shooterTrack.set(0);
				shooterState = state.Stopped;
			}
			break;

		// Ejecting
		case ejectingBoulder:
			if (!pickRollersOut) {
				Components.getInstance().shooterTrack.set(0);
				shooterState = state.Stopped;
			}
			break;

		// Vision
		case startingVisionShoot:

			if (shootBall) {
				Components.getInstance().shooterTrack.set(TRACKMOTORSPEED);
				shooterState = state.Shooting;
			}

			else if (!startVisionShoot) {
				topShooterPID.setSetpoint(0);
				botShooterPID.setSetpoint(0);
				
				shooterState = state.Stopped;
			} else if (topShooterPID.onTarget() && botShooterPID.onTarget()){
				CommonArea.isUpToSpeed = true;

			} else {
				CommonArea.isUpToSpeed = false;
			}
			break;

		case Shooting:
			if (!Components.getInstance().BallInLimit.get()) {
				mytimer.reset();
				mytimer.start();
				shooterState = state.ShootingDelay;
			} else if (!startVisionShoot) {
				Components.getInstance().topShooter.set(0);
				Components.getInstance().botShooter.set(0);
				shooterState = state.Stopped;
			}
			break;

		case ShootingDelay:

			if (mytimer.get() > 2) {
				CommonArea.isShot = true;
				Components.getInstance().shooterTrack.set(0);
				Components.getInstance().topShooter.set(0);
				Components.getInstance().botShooter.set(0);
				mytimer.stop();
				shooterState = state.Stopped;
			} else if (!startVisionShoot) {
				CommonArea.isShot = true;
				Components.getInstance().shooterTrack.set(0);
				Components.getInstance().topShooter.set(0);
				Components.getInstance().botShooter.set(0);
				shooterState = state.Stopped;
			}
			break;

		}
		Components.getInstance().topShooter.set(SmartDashboard
				.getDouble("DB/Slider 0"));
		Components.getInstance().botShooter.set(SmartDashboard
				.getDouble("DB/Slider 1"));
		SmartDashboard.putString("DB/String 0", String.valueOf((1 / Components
				.getInstance().topTachoCounter.getPeriod())));
		SmartDashboard.putString("DB/String 1", String.valueOf((1 / Components
				.getInstance().botTachoCounter.getPeriod())));

	}

}
