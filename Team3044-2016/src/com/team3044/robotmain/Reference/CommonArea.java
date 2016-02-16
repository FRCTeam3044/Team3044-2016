package com.team3044.robotmain.Reference;

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
	public static boolean movexFeet = false;
	public static boolean atDistance = false;
	public static boolean isManualDrive = true;
	
	//Shooter
	public static double shooterVisionTopSpeed;
	public static double shooterVisionBotSpeed;
	public static boolean aimFlag = false;
	public static boolean shooterMotorFlag = false;
	public static boolean shootFlag = false; 
	public static boolean isShot = false;
	
	//Gate
	public static boolean chevalFlag = false;
	
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
	public static boolean gateUp = firstJoy.getRawButton(FirstController.BUTTON_Y);
	public static boolean manualFire = firstJoy.getRawButton(FirstController.BUTTON_X);
	public static boolean gateDown = firstJoy.getRawButton(FirstController.BUTTON_A);
	public static boolean AVALIABLEB = firstJoy.getRawButton(FirstController.BUTTON_B);
	public static boolean pickRollersOut = firstJoy.getRawButton(FirstController.BUTTON_LB);
	public static boolean pickRollersIn = firstJoy.getRawButton(FirstController.BUTTON_RB);
	public static boolean getShootertoAutoSpeed = firstJoy.getRawButton(FirstController.BUTTON_BACK);
	public static boolean setSpeed = firstJoy.getRawButton(FirstController.BUTTON_START);
	public static boolean portcullius = firstJoy.getDPadUp();
	public static boolean calibrate = firstJoy.getDPadRight();
	public static boolean chevalDeFrise = firstJoy.getDPadDown();
	public static boolean AVALIABLEDLEFT = firstJoy.getDPadLeft();
	public static boolean shooterToSpeed = firstJoy.getTriggerLeft();
	public static boolean autoAlign = firstJoy.getTriggerRight();
	
	//SecondaryController
	public static boolean X1 = secondaryJoy.getRawButton(SecondaryController.BUTTON_Y);
	public static boolean X2 = secondaryJoy.getRawButton(SecondaryController.BUTTON_X);
	public static boolean Y2 = secondaryJoy.getRawButton(SecondaryController.BUTTON_A);
	public static boolean Y1 = secondaryJoy.getRawButton(SecondaryController.BUTTON_B);
	public static boolean H2 = secondaryJoy.getRawButton(SecondaryController.BUTTON_LB);
	public static boolean H1 = secondaryJoy.getRawButton(SecondaryController.BUTTON_RB);
	public static boolean CAL = secondaryJoy.getRawButton(SecondaryController.BUTTON_BACK);
	public static boolean STOP_TARGETING = secondaryJoy.getRawButton(SecondaryController.BUTTON_START);
	public static boolean UA_Up = secondaryJoy.getDPadUp();
	public static boolean LA_Up = secondaryJoy.getDPadRight();
	public static boolean UA_Down = secondaryJoy.getDPadDown();
	public static boolean LA_Down = secondaryJoy.getDPadLeft();
	public static boolean AVALAIBLELT = secondaryJoy.getTriggerLeft();
	public static boolean AVALAIBLERT = secondaryJoy.getTriggerRight();
}
