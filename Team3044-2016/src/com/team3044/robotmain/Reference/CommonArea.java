package com.team3044.robotmain.Reference;

import edu.wpi.first.wpilibj.IterativeRobot;
import com.team3044.robotmain.Reference.FirstController;
import com.team3044.robotmain.Reference.SecondaryController;

public class CommonArea {
	static FirstController firstJoy = FirstController.getInstance();
	static SecondaryController secondaryJoy = SecondaryController.getInstance();
	
	//Drive
	public static double leftDriveSpeed;
	public static double rightDriveSpeed;
	public static double leftAutoSpeed;
	public static double rightAutoSpeed;
	public static boolean isManualDrive = true;
	
	//Shooter
	public static double shooterVisionTopSpeed;
	public static double shooterVisionBotSpeed;
	public static boolean aimFlag = false;
	public static boolean shooterMotorFlag = false;
	public static boolean shootFlag = false;
	public static boolean isShot = false;
	
	//PickUp
	public static boolean portcullisFlag = false;
	public static boolean pickUpDrawBridgeFlag = false;
	
	//Arm
	public static boolean armDrawBridgeFlag = false;
	
	//Vision
	public static boolean isTargetSeen = false;
	public static boolean isAligned = false;
	public static boolean isUpToSpeed = false;
	public static int angleToTarget;
	public static double distanceFromTarget;
	
	//FirstController
	public static boolean pickRollersOut = firstJoy.getRawButton(FirstController.BUTTON_LT);
	public static boolean pickRollersIn = firstJoy.getRawButton(FirstController.BUTTON_RT);
	public static boolean gateUp = firstJoy.getRawButton(FirstController.BUTTON_Y);
	public static boolean gateDown = firstJoy.getRawButton(FirstController.BUTTON_A);
	public static boolean autoPortcullis = firstJoy.getRawButton(FirstController.BUTTON_X);
	public static boolean autoAlign = firstJoy.getRawButton(FirstController.BUTTON_START);
		
	//SecondaryController
	public static boolean setShooterSpeed1 = secondaryJoy.getRawButton(SecondaryController.BUTTON_A);
	public static boolean setShooterSpeed2 = secondaryJoy.getRawButton(SecondaryController.BUTTON_X);
	public static boolean setShooterSpeed3 = secondaryJoy.getRawButton(SecondaryController.BUTTON_B);
	public static boolean setShooterSpeed4 = secondaryJoy.getRawButton(SecondaryController.BUTTON_Y);
	public static boolean shootBall = secondaryJoy.getRawButton(SecondaryController.BUTTON_RT);
	public static boolean startShooterAtSetSpeed = secondaryJoy.getRawButton(SecondaryController.BUTTON_RT); //this is button for vision shoot
}
