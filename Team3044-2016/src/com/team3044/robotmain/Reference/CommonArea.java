package com.team3044.robotmain.Reference;

import edu.wpi.first.wpilibj.IterativeRobot;

public class CommonArea {
	
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
	
	//PickUp
	public boolean portcullisFlag = false;
	public boolean pickUpDrawBridgeFlag = false;
	
	//Arm
	public boolean armDrawBridgeFlag = false;
	
	
}
