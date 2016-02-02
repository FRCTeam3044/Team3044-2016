package com.team3044.robotmain.RobotCode;

import com.team3044.robotmain.Reference.*;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class VisionCalc {
	Components components = new Components();
	CommonArea commonarea = new CommonArea();

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
	
	public void Vision() {
		if(commonarea.autoAlign){
			commonarea.aimFlag = true;
		}
		switch (state) {
		// ---------------------------------------------------------------------------------------------
		case (WAITING):
			if (commonarea.aimFlag) {
				commonarea.shooterMotorFlag = true;
				commonarea.shooterTopSpeed = STARTSPEED;
				commonarea.shooterBotSpeed = -STARTSPEED;
				state = SPINSHOOTER;
			}
			break;
		// ----------------------------------------------------------------------------------------------
		case (SPINSHOOTER):
			if (!commonarea.aimFlag) {
				commonarea.leftDriveSpeed = 0;
				commonarea.rightDriveSpeed = 0;
				state = WAITING;
			} else if (!commonarea.isTargetSeen) {
				if (SmartDashboard.getBoolean("DB/Button 1")) {
					commonarea.leftDriveSpeed = TURNSPEED;
					commonarea.rightDriveSpeed = -TURNSPEED;
				} else {
					commonarea.leftDriveSpeed = -TURNSPEED;
					commonarea.rightDriveSpeed = TURNSPEED;
				}
				state = AUTOFIND;
			} else if (commonarea.isTargetSeen && !commonarea.isAligned) {
				state = ALIGN;
			} else if (commonarea.isTargetSeen && commonarea.isAligned) {
				commonarea.shooterTopSpeed = CalculatedTopSpeed(commonarea.distanceFromTarget);
				commonarea.shooterBotSpeed = -CalculatedBotSpeed(commonarea.distanceFromTarget);
				state = WAITFORSHOOTER;
			}
			break;
		// ---------------------------------------------------------------------------------------------
		case (AUTOFIND):
			if (!commonarea.aimFlag) {
				commonarea.leftDriveSpeed = 0;
				commonarea.rightDriveSpeed = 0;
				state = WAITING;
			} else if (commonarea.isTargetSeen) {
				commonarea.leftDriveSpeed = 0;
				commonarea.rightDriveSpeed = 0;
				state = ALIGN;
			}
			break;
		// ---------------------------------------------------------------------------------------------
		case (ALIGN):
			if (!commonarea.aimFlag) {
				commonarea.leftDriveSpeed = 0;
				commonarea.rightDriveSpeed = 0;
				state = WAITING;
			} else if (commonarea.isAligned) {
				commonarea.shooterTopSpeed = CalculatedTopSpeed(commonarea.distanceFromTarget);
				commonarea.shooterBotSpeed = -CalculatedBotSpeed(commonarea.distanceFromTarget);
				state = WAITFORSHOOTER;
			} else {
				commonarea.leftDriveSpeed = CalculatedTurnSpeed(commonarea.angleToTarget);
				commonarea.rightDriveSpeed = -CalculatedTurnSpeed(commonarea.angleToTarget);
			}
			break;
		// ----------------------------------------------------------------------------------------------
		case (WAITFORSHOOTER):
			if (commonarea.isUpToSpeed) {
				commonarea.shootFlag = true;
				state = SHOOT;
			}
			break;
		// ----------------------------------------------------------------------------------------------
		case (SHOOT):
			if (commonarea.isShot) {
				commonarea.shootFlag = false;
				commonarea.isShot = false;
				commonarea.aimFlag = false;
				state = WAITING;
			}
			break;
		}
	}
}
