package com.team3044.robotmain.RobotCode;

import com.team3044.robotmain.Reference.*;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class VisionCalc {

	public enum state {
		WAITING, SPINSHOOTER, AUTOFIND, ALIGN, WAITFORSHOOTER, SHOOT, RETURN, ALIGN_TWO
	}

	state visionState = state.WAITING;

	final double SHOOTERSTARTSPEED = 60;
	final double DRIVETURNSPEED = .7;
	double angleToTarget = 0;
	
	public double CalculatedTurnSpeed(double Angle) {
		if(Angle < -20){
			return -.2;
		}else if(Angle > 20){

			return .2;
		}else{
			if(Angle < -6){
				return -.15;
			}else if(Angle > 6){
				return .15;
			}else{
				return 0;
			}
			//return 0;
		}
		//
		
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
		iterator = 0;
	}

	public boolean isAligned(double Angle, double tolerance) {
		return Math.abs(Angle) < tolerance;
	}

	int count = 0;
	int k = 0;
	double value = 0;
	int iterator = 0;
	int loopCounting = 0;
	public void Vision() {
		angleToTarget = (int) SmartDashboard.getNumber("ANGLE", 0);
		SmartDashboard.putString("DB/String 0", String.valueOf(angleToTarget));
		SmartDashboard.putString("DB/String 7", String.valueOf(CommonArea.autoAlign));
		SmartDashboard.putString("DB/String 8", String.valueOf(visionState));
		SmartDashboard.putString("DB/String 1", String.valueOf(CommonArea.isAligned));
		
		System.out.println("MOTOR FLAG: " + CommonArea.shooterMotorFlag);
		CommonArea.isAligned = Math.abs(this.angleToTarget) < 20;

		switch (visionState) {
		
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
			} else if(SmartDashboard.getBoolean("TARGET", false) && Math.abs(SmartDashboard.getNumber("ANGLE")) < 6){
				visionState = state.WAITFORSHOOTER;
				CommonArea.shooterVisionTopSpeed = 110;
				CommonArea.shooterVisionBotSpeed = 85;
			}
			else{
				count = 0;
				visionState = state.ALIGN;
			} 
			break;
		
		case AUTOFIND:

			if (!CommonArea.autoAlign) {
				Reset();
				visionState = state.WAITING;
			} else if (SmartDashboard.getBoolean("TARGET", false)) {
				CommonArea.leftDriveSpeed = 0;
				CommonArea.rightDriveSpeed = 0;
				k = 0;
				this.visionState = state.RETURN;
				value = Components.getInstance().rightFrontDrive.getAnalogInPosition();
			}
			break;
		
		case RETURN:
			if (!CommonArea.autoAlign) {
				Reset();
				visionState = state.WAITING;
			}
			k += 1;
			if(k > 10){
				CommonArea.rightDriveSpeed = .4;
			}
			if( Components.getInstance().rightFrontDrive.getAnalogInPosition() - value > 500){
				CommonArea.rightDriveSpeed = 0;
				this.visionState = state.ALIGN;
			}
			break;
		case ALIGN:
			if (!CommonArea.autoAlign) {
				Reset();
				count = 0;
				visionState = state.WAITING;
			} else if (CommonArea.isAligned ) {
				System.out.println("aligned");
				CommonArea.leftDriveSpeed = 0;
				CommonArea.rightDriveSpeed = 0;

				visionState = state.ALIGN_TWO;
				count = 0;
				
			} else {
				System.out.println("aligning");
				SmartDashboard.putBoolean("ALIGNED", false);

				CommonArea.leftDriveSpeed = CalculatedTurnSpeed(CommonArea.angleToTarget);
				CommonArea.rightDriveSpeed = -CalculatedTurnSpeed(CommonArea.angleToTarget);


			}
			break;
		case ALIGN_TWO:
			if (!CommonArea.autoAlign) {
				Reset();
				visionState = state.WAITING;
			}

			if(angleToTarget < -4){
				loopCounting = 0;
				if(CommonArea.leftDriveSpeed == 0 && iterator > 5){
					CommonArea.leftDriveSpeed = -.2;
				}else if(CommonArea.leftDriveSpeed == 0){
					iterator += 1;
				}else if(CommonArea.leftDriveSpeed != -.2){
					CommonArea.leftDriveSpeed = -.2;
					iterator = 0;
				}
				
			}else if (angleToTarget > 4){
				loopCounting = 0;
				if(CommonArea.leftDriveSpeed == 0 && iterator > 5){
					CommonArea.leftDriveSpeed = .2;
				}else if(CommonArea.leftDriveSpeed == 0){
					iterator += 1;
				}else if(CommonArea.leftDriveSpeed != .2){
					CommonArea.leftDriveSpeed = .2;
					iterator = 0;
				}
			}else{
				if(loopCounting == 0){
					CommonArea.rightDriveSpeed = CommonArea.leftDriveSpeed;
				}
				if(loopCounting >= 2){
					CommonArea.rightDriveSpeed = 0;
				}
				loopCounting += 1;
				CommonArea.leftDriveSpeed = 0;
			}
			
			if(Math.abs(angleToTarget) < 5 && count > 10){
				CommonArea.shooterVisionTopSpeed = 110;
				CommonArea.shooterVisionBotSpeed = 85;
				CommonArea.leftDriveSpeed = 0;
				CommonArea.rightDriveSpeed = 0;
				visionState = state.WAITFORSHOOTER;
				SmartDashboard.putBoolean("ALIGNED", true);
				
			}
			if(Math.abs(angleToTarget) < 5){
				count += 1;
			}
			break;
		case WAITFORSHOOTER:
			if (!CommonArea.autoAlign) {
				Reset();
				visionState = state.WAITING;
			} else if (CommonArea.isUpToSpeed) {
				CommonArea.shootFlag = true;
				visionState = state.SHOOT;
			}
			
			break;
		
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
