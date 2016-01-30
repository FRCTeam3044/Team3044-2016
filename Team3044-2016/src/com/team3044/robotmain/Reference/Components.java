package com.team3044.robotmain.Reference;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DigitalInput;

public class Components {
	
	//Drive System 
	public CANTalon leftFrontDrive;
	public CANTalon leftBackDrive;
	public CANTalon rightFrontDrive;
	public CANTalon rightBackDrive;
	
	public double leftDriveEncoder;
	public double rightDriveEncoder;
	
	//Shooter
	public CANTalon topShooter;
	public CANTalon botShooter;
	
	//Defense
	public CANTalon longArm;
	public CANTalon shortArm;
	
	//Pick Up
	public CANTalon Pickup1;
	public CANTalon Pickup2;
	public CANTalon Pickup3;
	
	//Digital IOs
	public DigitalInput stagingSwitch = new DigitalInput(0);
	public DigitalInput topTacho = new DigitalInput(1);
	public DigitalInput botTacho = new DigitalInput(2);
	
		public void init(){
			leftFrontDrive = new CANTalon(1);
			leftBackDrive = new CANTalon(2);
			rightFrontDrive = new CANTalon(3);
			rightBackDrive = new CANTalon(4);
			
			topShooter = new CANTalon(5);
			botShooter = new CANTalon(6);
			
			longArm = new CANTalon(7);
			shortArm = new CANTalon(8);
			
			Pickup1 = new CANTalon(9);
			Pickup2 = new CANTalon(10);
			Pickup3 = new CANTalon(11);
	}
}
