package com.team3044.robotmain.RobotCode;

import com.team3044.robotmain.Reference.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class VisionCalc {

	public enum state {
		WAITING, SPINSHOOTER, AUTOFIND, ALIGN, WAITFORSHOOTER, SHOOT
	}

	state visionState = state.WAITING;

	final double SHOOTERSTARTSPEED = 60;
	final double DRIVETURNSPEED = .4;

	public double CalculatedTurnSpeed(double Angle) {
		double turnSpeed;
		if (Math.abs(Angle) > 0) {
			if (Angle > 0) {
				turnSpeed = (Angle / 220) * 4;
			} else {
				turnSpeed = (Angle / 220) * 4;
			}
			if (turnSpeed > .3) {
				turnSpeed = .3;
			} else if (turnSpeed < -.3) {
				turnSpeed = -.3;
			}
		} else {
			turnSpeed = 0;
		}
		return turnSpeed;
	}

	public double CalculatedTopSpeed(double Distance) {
		double topSpeed;
		topSpeed = Distance;
		return topSpeed;
	}

	public double CalculatedBotSpeed(double Distance) {
		double botSpeed;
		botSpeed = Distance;
		return botSpeed;
	}

	public void Reset() {
		CommonArea.shooterVisionTopSpeed = 0;
		CommonArea.shooterVisionBotSpeed = 0;
		CommonArea.leftDriveSpeed = 0;
		CommonArea.rightDriveSpeed = 0;
		this.visionState = visionState.WAITING;
		CommonArea.isManualDrive = true;
		CommonArea.shooterMotorFlag = false;
		CommonArea.shootFlag = false;
		CommonArea.isShot = false;
	}

	public boolean isAligned(double Angle, double tolerance) {
		return Math.abs(Angle) < tolerance;
	}

	int count = 0;

	public void Vision() {

		CommonArea.angleToTarget = (int) SmartDashboard.getNumber("ANGLE", 0);
		SmartDashboard.putString("DB/String 0", String.valueOf(CommonArea.angleToTarget));
		SmartDashboard.putString("DB/String 7", String.valueOf(CommonArea.autoAlign));
		SmartDashboard.putString("DB/String 8", String.valueOf(visionState));
		SmartDashboard.putString("DB/String 1", String.valueOf(CommonArea.isAligned));
		System.out.println("MOTOR FLAG: " + CommonArea.shooterMotorFlag);
		CommonArea.isAligned = isAligned(CommonArea.angleToTarget, 4);
		/*
		 * if(!CommonArea.autoAlign){ Reset(); }
		 */
		switch (visionState) {
		// ---------------------------------------------------------------------------------------------
		case WAITING:
			if (CommonArea.autoAlign) {
				CommonArea.isManualDrive = false;
				CommonArea.shooterMotorFlag = true;
				CommonArea.leftDriveSpeed = 0;
				CommonArea.rightDriveSpeed = 0;
				CommonArea.shooterVisionTopSpeed = SHOOTERSTARTSPEED;
				CommonArea.shooterVisionBotSpeed = SHOOTERSTARTSPEED;
				visionState = state.SPINSHOOTER;
			} else {
				CommonArea.shooterMotorFlag = false;
			}
			break;
		// ----------------------------------------------------------------------------------------------
		case SPINSHOOTER:
			System.out.println("Aligning");
			if (!CommonArea.autoAlign) {
				Reset();
				visionState = state.WAITING;
			} else if (!SmartDashboard.getBoolean("TARGET", false)) {
				if (SmartDashboard.getBoolean("DB/Button 1")) {
					CommonArea.leftDriveSpeed = -DRIVETURNSPEED;
					CommonArea.rightDriveSpeed = DRIVETURNSPEED;
				} else {
					CommonArea.leftDriveSpeed = DRIVETURNSPEED;
					CommonArea.rightDriveSpeed = -DRIVETURNSPEED;
				}
				visionState = state.AUTOFIND;
			} else if (SmartDashboard.getBoolean("TARGET", false) && !isAligned(CommonArea.angleToTarget, 10)) {
				count = 0;
				visionState = state.ALIGN;
			} else if (SmartDashboard.getBoolean("TARGET", false) && isAligned(CommonArea.angleToTarget, 10)) {
				CommonArea.shooterVisionTopSpeed = CalculatedTopSpeed(75);
				CommonArea.shooterVisionBotSpeed = CalculatedBotSpeed(60);
				count = 0;
				System.out.println("Aligned");
				visionState = state.WAITFORSHOOTER;
			}
			break;
		// ---------------------------------------------------------------------------------------------
		case AUTOFIND:
			if (!CommonArea.autoAlign) {
				Reset();
				visionState = state.WAITING;
			} else if (SmartDashboard.getBoolean("TARGET", false)) {
				visionState = state.ALIGN;
			}
			break;
		// ---------------------------------------------------------------------------------------------
		case ALIGN:
			if (!CommonArea.autoAlign) {
				Reset();
				count = 0;
				visionState = state.WAITING;
			} else if (CommonArea.isAligned && count >= 20) {
				System.out.println("aligned");
				CommonArea.leftDriveSpeed = 0;
				CommonArea.rightDriveSpeed = 0;
				CommonArea.shooterVisionTopSpeed = CalculatedTopSpeed(100);
				CommonArea.shooterVisionBotSpeed = CalculatedBotSpeed(75);
				visionState = state.WAITFORSHOOTER;
				count = 0;
				SmartDashboard.putBoolean("ALIGNED", true);
			} else if (CommonArea.isAligned && count <= 20) {
				count += 1;
			} else {
				System.out.println("aligning");
				SmartDashboard.putBoolean("ALIGNED", false);
				// count += 1;
				if (CommonArea.angleToTarget < -4) {
					CommonArea.leftDriveSpeed = CalculatedTurnSpeed(CommonArea.angleToTarget);
					CommonArea.rightDriveSpeed = 0;
				} else if(CommonArea.angleToTarget >= 4){
					CommonArea.rightDriveSpeed = -CalculatedTurnSpeed(CommonArea.angleToTarget);
					CommonArea.leftDriveSpeed = 0;
				}else{
					CommonArea.leftDriveSpeed = 0;
					CommonArea.rightDriveSpeed = 0;
				}

			}
			break;
		// ----------------------------------------------------------------------------------------------
		case WAITFORSHOOTER:
			if (!CommonArea.autoAlign) {
				Reset();
				visionState = state.WAITING;
			} else if (!CommonArea.isAligned) {
				visionState = state.ALIGN;
				CommonArea.leftDriveSpeed = CalculatedTurnSpeed(CommonArea.angleToTarget);
				CommonArea.rightDriveSpeed = -CalculatedTurnSpeed(CommonArea.angleToTarget);
			} else if (CommonArea.isUpToSpeed) {
				CommonArea.shootFlag = true;
				System.out.println("HITTING SHOOT FLAG");
				visionState = state.SHOOT;
			}
			System.out.println("WAIT FOR SHOOTER");
			break;
		// ----------------------------------------------------------------------------------------------
		case SHOOT:
			if (CommonArea.isShot) {
				CommonArea.autoShot = true;
				Reset();
				visionState = state.WAITING;
			} else if (!CommonArea.autoAlign) {
				Reset();
				visionState = state.WAITING;
			}

			break;
		}
	}
}
