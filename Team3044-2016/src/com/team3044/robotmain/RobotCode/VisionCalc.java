package com.team3044.robotmain.RobotCode;

import com.team3044.robotmain.Reference.*;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class VisionCalc {

	public enum state {
		WAITING, SPINSHOOTER, AUTOFIND, ALIGN, WAITFORSHOOTER, SHOOT
	}

	state visionState = state.WAITING;

	final double SHOOTERSTARTSPEED = .5;
	final double DRIVETURNSPEED = .4;
	
	boolean autoAlign = CommonArea.autoAlign;
	boolean aimFlag = CommonArea.aimFlag;
	boolean shooterMotorFlag = CommonArea.shooterMotorFlag;
	boolean shootFlag = CommonArea.shootFlag;
	boolean isManualDrive = CommonArea.isManualDrive;
	
	boolean isShot = CommonArea.isShot;
	boolean isTargetSeen = CommonArea.isTargetSeen;
	boolean isAligned = CommonArea.isAligned;
	boolean isUpToSpeed = CommonArea.isUpToSpeed;
	
	double shooterVisionTopSpeed = CommonArea.shooterVisionTopSpeed;
	double shooterVisionBotSpeed = CommonArea.shooterVisionBotSpeed;
	double leftDriveSpeed = CommonArea.leftDriveSpeed;
	double rightDriveSpeed = CommonArea.rightDriveSpeed;
	double distanceFromTarget = CommonArea.distanceFromTarget;
	int angleToTarget = CommonArea.angleToTarget;
	
	public double CalculatedTurnSpeed(int Angle) {
		double turnSpeed;
		turnSpeed = Angle / 100;
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
		shooterVisionTopSpeed = 0;
		shooterVisionBotSpeed = 0;
		leftDriveSpeed = 0;
		rightDriveSpeed = 0;
		isManualDrive = true;
		shooterMotorFlag = false;
		shootFlag = false;
		aimFlag = false;
		isShot = false;
	}

	public void Vision() {
		if (autoAlign) {
			aimFlag = true;
		}
		switch (visionState) {
		// ---------------------------------------------------------------------------------------------
		case WAITING:
			if (aimFlag) {
				isManualDrive = false;
				shooterMotorFlag = true;
				leftDriveSpeed = 0;
				rightDriveSpeed = 0;
				shooterVisionTopSpeed = SHOOTERSTARTSPEED;
				shooterVisionBotSpeed = -SHOOTERSTARTSPEED;
				visionState = state.SPINSHOOTER;
			}
			break;
		// ----------------------------------------------------------------------------------------------
		case SPINSHOOTER:
			if (!autoAlign) {
				Reset();
				visionState = state.WAITING;
			} else if (!isTargetSeen) {
				if (SmartDashboard.getBoolean("DB/Button 1")) {
					leftDriveSpeed = DRIVETURNSPEED;
					rightDriveSpeed = -DRIVETURNSPEED;
				} else {
					leftDriveSpeed = -DRIVETURNSPEED;
					rightDriveSpeed = DRIVETURNSPEED;
				}
				visionState = state.AUTOFIND;
			} else if (isTargetSeen && !isAligned) {
				visionState = state.ALIGN;
			} else if (isTargetSeen && isAligned) {
				shooterVisionTopSpeed = CalculatedTopSpeed(distanceFromTarget);
				shooterVisionBotSpeed = -CalculatedBotSpeed(distanceFromTarget);
				visionState = state.WAITFORSHOOTER;
			}
			break;
		// ---------------------------------------------------------------------------------------------
		case AUTOFIND:
			if (!CommonArea.autoAlign) {
				Reset();
				visionState = state.WAITING;
			} else if (isTargetSeen) {
				visionState = state.ALIGN;
			}
			break;
		// ---------------------------------------------------------------------------------------------
		case ALIGN:
			if (!autoAlign) {
				Reset();
				visionState = state.WAITING;
			} else if (isAligned) {
				leftDriveSpeed = 0;
				rightDriveSpeed = 0;
				shooterVisionTopSpeed = CalculatedTopSpeed(distanceFromTarget);
				shooterVisionBotSpeed = -CalculatedBotSpeed(distanceFromTarget);
				visionState = state.WAITFORSHOOTER;
			} else {
				leftDriveSpeed = CalculatedTurnSpeed(angleToTarget);
				rightDriveSpeed = CalculatedTurnSpeed(angleToTarget);
			}
			break;
		// ----------------------------------------------------------------------------------------------
		case WAITFORSHOOTER: 
			if (!autoAlign) {
				Reset();
				visionState = state.WAITING;
			} else if (isUpToSpeed) {
				shootFlag = true;
				visionState = state.SHOOT;
			}
			break;
		// ----------------------------------------------------------------------------------------------
		case SHOOT:
			if (isShot) {
				Reset();
				visionState = state.WAITING;
			} /*else if (!autoAlign){
				Reset();
				visionState = state.WAITING;
			}*/
			break;
		}
	}
}
