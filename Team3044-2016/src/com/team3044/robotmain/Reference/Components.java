package com.team3044.robotmain.Reference;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DigitalOutput;

public class Components {

	private static Components instance = null;

	private Components() {

	}
 
	public static Components getInstance() {
		if (instance == null) {
			instance = new Components();
		}
		return instance;
	}

	// Drive System
	public CANTalon leftFrontDrive;
	public CANTalon leftBackDrive;
	public CANTalon rightFrontDrive;
	public CANTalon rightBackDrive;

	public static double leftDriveEncoder;
	public static double rightDriveEncoder;

	// Shooter
	public CANTalon topShooter;
	public CANTalon botShooter;

	// Defense
	public CANTalon lowerArm;
	public CANTalon upperArm;

	// Pick Up
	public CANTalon gateTalon;
	public CANTalon shooterTrack;
	public CANTalon Pickup3;

	// Digital IOs
	public DigitalInput conflict = new DigitalInput(7);
	public DigitalInput topTacho = new DigitalInput(5);
	public DigitalInput botTacho = new DigitalInput(4);
	public DigitalInput BallInLimit = new DigitalInput(6);
	public DigitalInput GateUpLimit = new DigitalInput(1);
	public DigitalInput GateDownLimit = new DigitalInput(2);
	

	private DigitalOutput DIO0 = new DigitalOutput(0);
	private DigitalOutput DIO3 = new DigitalOutput(3);
	private DigitalOutput DIO8 = new DigitalOutput(8);
	private DigitalOutput DIO9 = new DigitalOutput(9);
	private DigitalOutput DIO10 = new DigitalOutput(10);
	private DigitalOutput DIO11 = new DigitalOutput(11);
	private DigitalOutput DIO12 = new DigitalOutput(12);
	private DigitalOutput DIO13 = new DigitalOutput(13);
	private DigitalOutput DIO14 = new DigitalOutput(14);
	private DigitalOutput DIO15 = new DigitalOutput(15);
	private DigitalOutput DIO16 = new DigitalOutput(16);
	private DigitalOutput DIO17 = new DigitalOutput(17);
	private DigitalOutput DIO18 = new DigitalOutput(18);
	private DigitalOutput DIO19 = new DigitalOutput(19);
	private DigitalOutput DIO20 = new DigitalOutput(20);
	private DigitalOutput DIO21 = new DigitalOutput(21);
	private DigitalOutput DIO22 = new DigitalOutput(22);
	private DigitalOutput DIO23 = new DigitalOutput(23);
	private DigitalOutput DIO24 = new DigitalOutput(24);
	private DigitalOutput DIO25 = new DigitalOutput(25);
	
	public Counter topTachoCounter = new Counter(topTacho);
	public Counter botTachoCounter = new Counter(botTacho);

	public void init() {
		leftFrontDrive = new CANTalon(1);
		leftBackDrive = new CANTalon(2);
		rightFrontDrive = new CANTalon(3);
		rightBackDrive = new CANTalon(4);

		leftFrontDrive.setFeedbackDevice(FeedbackDevice.AnalogEncoder);
		rightFrontDrive.setFeedbackDevice(FeedbackDevice.AnalogEncoder);

		topShooter = new CANTalon(5);
		botShooter = new CANTalon(6);

		lowerArm = new CANTalon(7);
		upperArm = new CANTalon(8);

		gateTalon = new CANTalon(9);
		shooterTrack = new CANTalon(10);
		Pickup3 = new CANTalon(11);
		
		DIO0.set(false); 
		DIO3.set(false); 
		DIO8.set(false); 
		DIO9.set(false); 
		DIO10.set(false); 
		DIO11.set(false); 
		DIO12.set(false); 
		DIO13.set(false); 
		DIO14.set(false); 
		DIO15.set(false); 
		DIO16.set(false); 
		DIO17.set(false); 
		DIO18.set(false); 
		DIO19.set(false); 
		DIO20.set(false); 
		DIO21.set(false); 
		DIO22.set(false); 
		DIO23.set(false); 
		DIO24.set(false); 
		DIO25.set(false); 
		
	}
}
