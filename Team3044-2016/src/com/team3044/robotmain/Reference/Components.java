package com.team3044.robotmain.Reference;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.DigitalInput;

public class Components {
	
	//Drive System 
	public static CANTalon leftFrontDrive;
	public static CANTalon leftBackDrive;
	public static CANTalon rightFrontDrive;
	public static CANTalon rightBackDrive;
	
	public static double leftDriveEncoder;
	public static double rightDriveEncoder;
	
	//Shooter
	public static CANTalon topShooter;
	public static CANTalon botShooter;
	
	//Defense
	public static CANTalon longArm;
	public static CANTalon shortArm;
	
	//Pick Up
	public static CANTalon gateTalon;
	public static CANTalon Pickup2;
	public static CANTalon Pickup3;

	
	//Digital IOs
	public static DigitalInput stagingSwitch = new DigitalInput(0);
	public static DigitalInput topTacho = new DigitalInput(1);
	public static DigitalInput botTacho = new DigitalInput(2);
	
	public static DigitalInput GateUpLimit = new DigitalInput(2);
	public static DigitalInput GateDownLimit = new DigitalInput(3);
	public static DigitalInput BallInLimit = new DigitalInput(4);
	
	public static Counter topTachoCounter = new Counter(topTacho);
	public static Counter botTachoCounter = new Counter(botTacho);
	
		public void init(){
			leftFrontDrive = new CANTalon(1);
			leftBackDrive = new CANTalon(2);
			rightFrontDrive = new CANTalon(3);
			rightBackDrive = new CANTalon(4);
			
			topShooter = new CANTalon(5);
			botShooter = new CANTalon(6);
			
			longArm = new CANTalon(7);
			shortArm = new CANTalon(8);
			
			gateTalon = new CANTalon(9);
			Pickup2 = new CANTalon(10);
			Pickup3 = new CANTalon(11);
	}
}
