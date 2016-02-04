package com.team3044.robotmain.RobotCode;

import com.team3044.robotmain.Reference.*;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class VisionCalc {

	final int WAITING = 0;
	final int SPINSHOOTER = 1;
	final int AUTOFIND = 2;
	final int ALIGN = 3;
	final int WAITFORSHOOTER = 4;
	final int SHOOT = 5;

	final double STARTSPEED = .5;
	final double TURNSPEED = .4;
	int state = 0;

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
		switch (state) {
		// ---------------------------------------------------------------------------------------------
		case (WAITING):
			if (CommonArea.aimFlag) {
				CommonArea.shooterMotorFlag = true;
				CommonArea.shooterVisionTopSpeed = STARTSPEED;
				CommonArea.shooterVisionBotSpeed = STARTSPEED;
				state = SPINSHOOTER;
			}
			break;
		// ----------------------------------------------------------------------------------------------
		case (SPINSHOOTER):
			if (!CommonArea.autoAlign) {
				Reset();
				state = WAITING;
			} else if (!CommonArea.isTargetSeen) {
				if (SmartDashboard.getBoolean("DB/Button 1")) {
					CommonArea.leftDriveSpeed = TURNSPEED;
					CommonArea.rightDriveSpeed = -TURNSPEED;
				} else {
					CommonArea.leftDriveSpeed = -TURNSPEED;
					CommonArea.rightDriveSpeed = TURNSPEED;
				}
				state = AUTOFIND;
			} else if (CommonArea.isTargetSeen && !CommonArea.isAligned) {
				state = ALIGN;
			} else if (CommonArea.isTargetSeen && CommonArea.isAligned) {
				CommonArea.shooterVisionTopSpeed = CalculatedTopSpeed(CommonArea.distanceFromTarget);
				CommonArea.shooterVisionBotSpeed = -CalculatedBotSpeed(CommonArea.distanceFromTarget);
				state = WAITFORSHOOTER;
			}
			break;
		// ---------------------------------------------------------------------------------------------
		case (AUTOFIND):
			if (!CommonArea.autoAlign) {
				Reset();
				state = WAITING;
			} else if (CommonArea.isTargetSeen) {
				state = ALIGN;
			}
			break;
		// ---------------------------------------------------------------------------------------------
		case (ALIGN):
			if (!CommonArea.autoAlign) {
				Reset();
				state = WAITING;
			} else if (CommonArea.isAligned) {
				CommonArea.leftDriveSpeed = 0;
				CommonArea.rightDriveSpeed = 0;
				CommonArea.shooterVisionTopSpeed = CalculatedTopSpeed(CommonArea.distanceFromTarget);
				CommonArea.shooterVisionBotSpeed = CalculatedBotSpeed(CommonArea.distanceFromTarget);
				state = WAITFORSHOOTER;
			} else {
				CommonArea.leftDriveSpeed = CalculatedTurnSpeed(CommonArea.angleToTarget);
				CommonArea.rightDriveSpeed = CalculatedTurnSpeed(CommonArea.angleToTarget);
			}
			break;
		// ----------------------------------------------------------------------------------------------
		case (WAITFORSHOOTER):
			if (CommonArea.isUpToSpeed) {
				CommonArea.shootFlag = true;
				state = SHOOT;
			}
			break;
		// ----------------------------------------------------------------------------------------------
		case (SHOOT):
			if (CommonArea.isShot) {
				Reset();
				state = WAITING;
			}
			break;
		}
	}
}
