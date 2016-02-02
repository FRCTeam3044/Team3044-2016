package com.team3044.robotmain.Reference;

import edu.wpi.first.wpilibj.IterativeRobot;
import com.team3044.robotmain.Reference.FirstController;
import com.team3044.robotmain.Reference.SecondaryController;

public class CommonArea {
	FirstController firstJoy = FirstController.getInstance();
	SecondaryController secondaryJoy = SecondaryController.getInstance();
	
	//Drive
	public double leftDriveSpeed;
	public double rightDriveSpeed;
	public double leftAutoSpeed;
	public double rightAutoSpeed;
	public boolean isManualDrive = true;
	
	//Shooter
	public double shooterTopSpeed;
	public double shooterBotSpeed;
	public boolean aimFlag = false;
	public boolean shooterMotorFlag = false;
	public boolean shootFlag = false;
	public boolean isShot = false;
	
	//PickUp
	public boolean portcullisFlag = false;
	public boolean pickUpDrawBridgeFlag = false;
	
	//Arm
	public boolean armDrawBridgeFlag = false;
	
	//Vision
	public boolean isTargetSeen = false;
	public boolean isAligned = false;
	public boolean isUpToSpeed = false;
	public int angleToTarget;
	public double distanceFromTarget;
	
	//FirstController
	public boolean pickRollersOut = firstJoy.getRawButton(FirstController.BUTTON_LT);
	public boolean pickRollersIn = firstJoy.getRawButton(FirstController.BUTTON_RT);
	public boolean gateUp = firstJoy.getRawButton(FirstController.BUTTON_Y);
	public boolean gateDown = firstJoy.getRawButton(FirstController.BUTTON_A);
	public boolean autoPortcullis = firstJoy.getRawButton(FirstController.BUTTON_X);
	public boolean autoAlign = firstJoy.getRawButton(FirstController.BUTTON_START);
	
	//SecondaryController
	public boolean setShooterSpeed1 = secondaryJoy.getRawButton(SecondaryController.BUTTON_A);
	public boolean setShooterSpeed2 = secondaryJoy.getRawButton(SecondaryController.BUTTON_X);
	public boolean setShooterSpeed3 = secondaryJoy.getRawButton(SecondaryController.BUTTON_B);
	public boolean setShooterSpeed4 = secondaryJoy.getRawButton(SecondaryController.BUTTON_Y);
	public boolean shootBall = secondaryJoy.getRawButton(SecondaryController.BUTTON_RT);
	public boolean startShooterAtVisionSpeed = secondaryJoy.getRawButton(SecondaryController.BUTTON_START);
	public boolean startShooterAtSetSpeed = secondaryJoy.getRawButton(SecondaryController.BUTTON_RT);
}
