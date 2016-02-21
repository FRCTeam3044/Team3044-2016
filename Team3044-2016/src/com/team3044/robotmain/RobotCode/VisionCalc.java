package com.team3044.robotmain.RobotCode;

import com.team3044.robotmain.Reference.*;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class VisionCalc {

	public enum state {
		WAITING, SPINSHOOTER, AUTOFIND, ALIGN, WAITFORSHOOTER, SHOOT
	}

	state visionState = state.WAITING;

	final double SHOOTERSTARTSPEED = 60;
	final double DRIVETURNSPEED = .4;
	double p = .005, i = 0.00025, d = 0.0015;
	
	CameraPIDSource camAngle;
	AlignPIDController pidController;
	PIDController cameraPID;
	public void init(){
		SmartDashboard.putNumber("P", p * 100);
		SmartDashboard.putNumber("I", i * 100);
		SmartDashboard.putNumber("D", d * 100);
		camAngle = new CameraPIDSource();
		pidController = new AlignPIDController();
		cameraPID = new PIDController(p,i,d,camAngle,pidController);
		cameraPID.setSetpoint(0);
		cameraPID.setOutputRange(-.35, .35);
		cameraPID.setInputRange(-160, 160);
		cameraPID.setAbsoluteTolerance(7);
		cameraPID.setToleranceBuffer(3);
		cameraPID.enable();
		
		
	}
	
	public double CalculatedTurnSpeed(double Angle) {
		
		return pidController.getSpeed();
		
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
		//p = SmartDashboard.getNumber("P",0)/100;
		//i = SmartDashboard.getNumber("I",0) / 100;
		//d = SmartDashboard.getNumber("D",0)/100;
		//this.cameraPID.setPID(p, i, d);
		CommonArea.angleToTarget = (int) SmartDashboard.getNumber("ANGLE", 0);
		SmartDashboard.putString("DB/String 0", String.valueOf(CommonArea.angleToTarget));
		SmartDashboard.putString("DB/String 7", String.valueOf(CommonArea.autoAlign));
		SmartDashboard.putString("DB/String 8", String.valueOf(visionState));
		SmartDashboard.putString("DB/String 1", String.valueOf(CommonArea.isAligned));
		System.out.println("MOTOR FLAG: " + CommonArea.shooterMotorFlag);
		CommonArea.isAligned = Math.abs(this.cameraPID.getError()) < 10;//isAligned(CommonArea.angleToTarget, 10);
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
				CommonArea.shooterVisionTopSpeed = 100;
				CommonArea.shooterVisionBotSpeed = 75;
				visionState = state.WAITFORSHOOTER;
				count = 0;
				SmartDashboard.putBoolean("ALIGNED", true);
			} else if (CommonArea.isAligned && count <= 20) {
				count += 1;
			} else {
				System.out.println("aligning");
				SmartDashboard.putBoolean("ALIGNED", false);
				// count += 1;
				
				CommonArea.leftDriveSpeed = CalculatedTurnSpeed(CommonArea.angleToTarget);
				//CommonArea.rightDriveSpeed = -CalculatedTurnSpeed(CommonArea.angleToTarget);;


			}
			break;
		// ----------------------------------------------------------------------------------------------
		case WAITFORSHOOTER:
			if (!CommonArea.autoAlign) {
				Reset();
				visionState = state.WAITING;
			} /*else if (!CommonArea.isAligned) {
				visionState = state.ALIGN;
				CommonArea.leftDriveSpeed = CalculatedTurnSpeed(CommonArea.angleToTarget);
				CommonArea.rightDriveSpeed = -CalculatedTurnSpeed(CommonArea.angleToTarget);
			}*/ else if (CommonArea.isUpToSpeed) {
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
