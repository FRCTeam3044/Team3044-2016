
package com.team3044.robotmain.RobotCode;

import com.team3044.robotmain.Reference.*;
import com.team3044.robotmain.RobotCode.Gate.state;

import edu.wpi.first.wpilibj.Timer;

public class Shooter {
	//Inputs
	boolean pickRollersIn;
	boolean pickRollersOut ;
	boolean startShooterAtVisionSpeed;
	boolean shootBall;
	boolean startVisionShoot;
	double shooterVisionTopSpeed;
	double shooterVisionBotSpeed;
	final double toleranceShooter = .5;
	//Components.BallInLimit.get()
	//1/Components.topTachoCounter.getPeriod(); <willgivespeedofturning
	//1/Components.botTachoCounter.getPeriod(); 

	//Motor Speeds
		final double trackMotorSpeed = 1;

		boolean lastStaging = false;
		Timer mytimer = new Timer();


	//VISION CODE SPEEDS
	//CommonArea.shooterTopSpeed;
	//CommonArea.shooterBotSpeed;

	//States
	public enum state{
		Stopped, ingestingBoulder, ejectingBoulder, startingVisionShoot, waitingForVisionShoot, Shooting, ShootingDelay
	}
	state shooterState = state.Stopped;

	

	
	public void shooterInit(){

	}

	public void shooterAutoPeriodic() {

	}
	public void shooterTeleopPeriodic() {
		pickRollersIn = CommonArea.pickRollersIn;
		pickRollersOut = CommonArea.pickRollersOut;
		startShooterAtVisionSpeed = CommonArea.startShooterAtSetSpeed;
		shootBall = CommonArea.shootFlag;
		startVisionShoot = CommonArea.isAligned;
		shooterVisionTopSpeed = CommonArea.shooterVisionTopSpeed;
		shooterVisionBotSpeed = CommonArea.shooterVisionBotSpeed;

		switch(shooterState){


		case Stopped:
			if ( startVisionShoot && Components.BallInLimit.get() ){
				Components.topShooter.set(shooterVisionTopSpeed);
				Components.botShooter.set(shooterVisionBotSpeed);
				shooterState = state.startingVisionShoot;
			}
			else if (pickRollersIn && !Components.BallInLimit.get()){
				Components.shooterTrack.set(trackMotorSpeed);
				shooterState = state.ingestingBoulder;
			}
			else if (pickRollersOut){
				Components.shooterTrack.set(-trackMotorSpeed);
				shooterState = state.ejectingBoulder;
			}
			break;



			//PickUp
		case ingestingBoulder:
			if (Components.BallInLimit.get() || !pickRollersIn){
				Components.shooterTrack.set(0);
				shooterState = state.Stopped;
			}
			break;

			//Ejecting
		case ejectingBoulder:
			if (!pickRollersOut){
				Components.shooterTrack.set(0);
				shooterState = state.Stopped;
			}
			break;


			//Vision
		case startingVisionShoot:
			if (shootBall){
				Components.shooterTrack.set(trackMotorSpeed);
				shooterState = state.Shooting;		
			}
			
			else if (!startVisionShoot){
				Components.topShooter.set(0);
				Components.botShooter.set(0);
				shooterState = state.Stopped;
			}
			else if (Utilities.tolerance(shooterVisionTopSpeed-toleranceShooter, Components.topTachoCounter.get(), shooterVisionTopSpeed+toleranceShooter) && Utilities.tolerance(shooterVisionBotSpeed-toleranceShooter, Components.botTachoCounter.get(), shooterVisionBotSpeed+toleranceShooter)){
				CommonArea.isUpToSpeed = true;
				
			}else{
				CommonArea.isUpToSpeed = false;
			}
			break;

		case Shooting:
			if (!Components.BallInLimit.get()){
				mytimer.reset();
				mytimer.start();
				shooterState = state.ShootingDelay;
			}else if (!startVisionShoot){
				Components.topShooter.set(0);
				Components.botShooter.set(0);
				shooterState = state.Stopped;}
			break;
			
		case ShootingDelay:
			
			if (mytimer.get() > 2){
				CommonArea.isShot = true;
				Components.shooterTrack.set(0);
				Components.topShooter.set(0);
				Components.botShooter.set(0);
				mytimer.stop();
				shooterState = state.Stopped;
			}else if (!startVisionShoot){
				CommonArea.isShot = true;
				Components.shooterTrack.set(0);
				Components.topShooter.set(0);
				Components.botShooter.set(0);
				shooterState = state.Stopped;
				}break;

		}
	}
}
