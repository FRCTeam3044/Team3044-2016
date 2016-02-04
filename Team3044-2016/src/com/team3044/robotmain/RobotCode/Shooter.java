
package com.team3044.robotmain.RobotCode;

import com.team3044.robotmain.Reference.*;
import com.team3044.robotmain.RobotCode.Gate.state;

import edu.wpi.first.wpilibj.Timer;

public class Shooter {
	//Inputs
	boolean pickRollersIn = CommonArea.pickRollersIn;
	boolean pickRollersOut = CommonArea.pickRollersOut;
	boolean startShooterAtVisionSpeed = CommonArea.startShooterAtSetSpeed;
	boolean shootBall = CommonArea.shootBall;
	//Components.BallInLimit.get()
	//1/Components.topTachoCounter.getPeriod(); <willgivespeedofturning
	//1/Components.botTachoCounter.getPeriod(); 
	//Components.BallInLimit.get()
	
	//VISION CODE SPEEDS
	//CommonArea.shooterTopSpeed;
	//CommonArea.shooterBotSpeed;
	
	//SET SPEEDS
	double xTopSpeed = .1;
	double xBotSpeed = -.1;
	
	double yTopSpeed = .1;
	double yBotSpeed = -.1;
	
	double aTopSpeed = .1;
	double aBotSpeed = -.1;

	double bTopSpeed = .1;
	double bBotSpeed = -.1;
	
	//States
	public enum state{
		Stopped, ingestingBoulder, ejectingBoulder, startingShooter, startingvisionShoot, waitingForVisionShoot
	}
	state shooterState = state.Stopped;

	//Motor Speeds
	double motorSpeedUp = 1;
	double motorSpeedDown = 1;

	//Encoder Values
	double upperEncoderLimit = 1;
	double lowerEncoderLimit = 2;

	//variables
	public double initialAngleDifference;
	public double Desired;

	boolean lastStaging = false;
	
	Timer mytimer = new Timer();
	public void shooterInit(){

	}

	public void shooterAutoPeriodic() {

	}
	public void shooterTeleopPeriodic() {
		switch(shooterState){
		
		case Stopped:
			if (CommonArea.isAligned){
				//start motors yo vision speed
				shooterState = state.startingvisionShoot;
			}
		
		
		
		
		
		
		
		//Vision
		case startingvisionShoot:
			if (Components.topTachoCounter.equals(CommonArea.shooterVisionTopSpeed) && Components.botTachoCounter.equals(CommonArea.shooterVisionBotSpeed) ){
				CommonArea.isUpToSpeed = true;
				shooterState = state.waitingForVisionShoot;
			}else if (!CommonArea.isAligned){
				Components.topShooter.set(0);
				Components.botShooter.set(0);
				shooterState = state.Stopped;
			}
		case waitingForVisionShoot:
			if (CommonArea.shootFlag){
				//track motor =1
				//state.set Stopped
			
			}
			if (lastStaging && !Components.BallInLimit.get()){
				mytimer.reset();
				mytimer.start();
				
			}
			boolean lastStaging = Components.BallInLimit.get(); 
			if (mytimer.get() > 2){
				CommonArea.isShot = true;
				Components.topShooter.set(0);
				Components.botShooter.set(0);
				mytimer.stop();
				shooterState = state.Stopped;
			}
		}
	
}}
