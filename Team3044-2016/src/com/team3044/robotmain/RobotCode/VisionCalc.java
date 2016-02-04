package com.team3044.robotmain.RobotCode;

import com.team3044.robotmain.Reference.*;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class VisionCalc {
	
	public enum state{
		WAITING,SPINSHOOTER,AUTOFIND,ALIGN,WAITFORSHOOTER,SHOOT
	}
	
	state visionState = state.WAITING;
	
	final double STARTSPEED = .5;
	final double TURNSPEED = .4;
	
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
		CommonArea.shooterVisionTopSpeed = 0;
		CommonArea.shooterVisionBotSpeed = 0;
		CommonArea.leftDriveSpeed = 0;
		CommonArea.rightDriveSpeed = 0;
		CommonArea.shooterMotorFlag = false;
		CommonArea.shootFlag = false;
		CommonArea.isShot = false;
		CommonArea.aimFlag = false;
	}
	public void Vision() {
		if (CommonArea.autoAlign) {
			CommonArea.aimFlag = true;
		}
		switch (visionState) {
		// ---------------------------------------------------------------------------------------------
		case WAITING:
			if (CommonArea.aimFlag) {
				CommonArea.shooterMotorFlag = true;
				CommonArea.shooterVisionTopSpeed = STARTSPEED;
				CommonArea.shooterVisionBotSpeed = STARTSPEED;
				visionState = state.SPINSHOOTER;
			}
			break;
		// ----------------------------------------------------------------------------------------------
		case SPINSHOOTER:
			if (!CommonArea.autoAlign) {
				Reset();
				visionState = state.WAITING;
			} else if (!CommonArea.isTargetSeen) {
				if (SmartDashboard.getBoolean("DB/Button 1")) {
					CommonArea.leftDriveSpeed = TURNSPEED;
					CommonArea.rightDriveSpeed = -TURNSPEED;
				} else {
					CommonArea.leftDriveSpeed = -TURNSPEED;
					CommonArea.rightDriveSpeed = TURNSPEED;
				}
				visionState = state.AUTOFIND;
			} else if (CommonArea.isTargetSeen && !CommonArea.isAligned) {
				visionState = state.ALIGN;
			} else if (CommonArea.isTargetSeen && CommonArea.isAligned) {
				CommonArea.shooterVisionTopSpeed = CalculatedTopSpeed(CommonArea.distanceFromTarget);
				CommonArea.shooterVisionBotSpeed = -CalculatedBotSpeed(CommonArea.distanceFromTarget);
				visionState = state.WAITFORSHOOTER;
			}
			break;
		// ---------------------------------------------------------------------------------------------
		case AUTOFIND:
			if (!CommonArea.autoAlign) {
				Reset();
				visionState = state.WAITING;
			} else if (CommonArea.isTargetSeen) {
				visionState = state.ALIGN;
			}
			break;
		// ---------------------------------------------------------------------------------------------
		case ALIGN:
			if (!CommonArea.autoAlign) {
				Reset();
				visionState = state.WAITING;
			} else if (CommonArea.isAligned) {
				CommonArea.leftDriveSpeed = 0;
				CommonArea.rightDriveSpeed = 0;
				CommonArea.shooterVisionTopSpeed = CalculatedTopSpeed(CommonArea.distanceFromTarget);
				CommonArea.shooterVisionBotSpeed = CalculatedBotSpeed(CommonArea.distanceFromTarget);
				visionState = state.WAITFORSHOOTER;
			} else {
				CommonArea.leftDriveSpeed = CalculatedTurnSpeed(CommonArea.angleToTarget);
				CommonArea.rightDriveSpeed = CalculatedTurnSpeed(CommonArea.angleToTarget);
			}
			break;
		// ----------------------------------------------------------------------------------------------
		case WAITFORSHOOTER:
			if (CommonArea.isUpToSpeed) {
				CommonArea.shootFlag = true;
				visionState = state.SHOOT;
			}
			break;
		// ----------------------------------------------------------------------------------------------
		case SHOOT:
			if (CommonArea.isShot) {
				Reset();
				visionState = state.WAITING;
			}
			break;
		}
	}
}
