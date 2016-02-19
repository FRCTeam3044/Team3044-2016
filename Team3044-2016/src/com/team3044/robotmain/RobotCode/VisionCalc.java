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
		if (Math.abs(Angle + 9) > 0) {
			if(Angle > 0){
				turnSpeed = Angle / 160 + .1;
			}else {
				turnSpeed = Angle / 160 - .1;
			}
			if (turnSpeed > .2) {
				turnSpeed = .2;
			} else if (turnSpeed < -.2) {
				turnSpeed = -.2;
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
		CommonArea.isManualDrive = true;
		CommonArea.shooterMotorFlag = false;
		CommonArea.shootFlag = false;
		CommonArea.isShot = false;
	}

	public boolean isAligned(double Angle, double tolerance) {
		return Math.abs(Angle + 9) < tolerance;
	}

	int count = 0;

	public void Vision() {

		CommonArea.angleToTarget = (int) SmartDashboard.getNumber("ANGLE", 0);
		SmartDashboard.putString("DB/String 0",
				String.valueOf(CommonArea.angleToTarget));
		SmartDashboard.putString("DB/String 7",
				String.valueOf(CommonArea.autoAlign));
		SmartDashboard.putString("DB/String 8", String.valueOf(visionState));
		SmartDashboard.putString("DB/String 1",
				String.valueOf(isAligned(CommonArea.angleToTarget, 10)));
		System.out.println("MOTOR FLAG: " + CommonArea.shooterMotorFlag);
		CommonArea.isAligned = isAligned(CommonArea.angleToTarget, 10);
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
			} else if (SmartDashboard.getBoolean("TARGET", false)
					&& !isAligned(CommonArea.angleToTarget, 10)) {
				count = 0;
				visionState = state.ALIGN;
			} else if (SmartDashboard.getBoolean("TARGET", false)
					&& isAligned(CommonArea.angleToTarget, 10)) {
				CommonArea.shooterVisionTopSpeed = CalculatedTopSpeed(75);
				CommonArea.shooterVisionBotSpeed = CalculatedBotSpeed(60);
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
				visionState = state.WAITING;
			} else if (CommonArea.isAligned && count >= 10) {
				System.out.println("aligned");
				CommonArea.leftDriveSpeed = 0;
				CommonArea.rightDriveSpeed = 0;
				CommonArea.shooterVisionTopSpeed = CalculatedTopSpeed(100);
				CommonArea.shooterVisionBotSpeed = CalculatedBotSpeed(75);
				visionState = state.WAITFORSHOOTER;
			} else {
				System.out.println("aligning");
				count += 1;
				CommonArea.leftDriveSpeed = CalculatedTurnSpeed(CommonArea.angleToTarget);
				CommonArea.rightDriveSpeed = -CalculatedTurnSpeed(CommonArea.angleToTarget);

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
			if (CommonArea.isShot
					&& CommonArea.autoAlign) {
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
