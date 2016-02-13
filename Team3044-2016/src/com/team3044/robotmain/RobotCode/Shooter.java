
package com.team3044.robotmain.RobotCode;

import com.team3044.robotmain.Reference.*;
import com.team3044.robotmain.RobotCode.Gate.state;

import edu.wpi.first.wpilibj.Relay.Value;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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

		/*switch(shooterState){


		case Stopped:
			if ( startVisionShoot && Components.getInstance().BallInLimit.get() ){
				Components.getInstance().topShooter.set(shooterVisionTopSpeed);
				Components.getInstance().botShooter.set(shooterVisionBotSpeed);
				shooterState = state.startingVisionShoot;
			}
			else if (pickRollersIn && !Components.getInstance().BallInLimit.get()){
				Components.getInstance().shooterTrack.set(trackMotorSpeed);
				shooterState = state.ingestingBoulder;
			}
			else if (pickRollersOut){
				Components.getInstance().shooterTrack.set(-trackMotorSpeed);
				shooterState = state.ejectingBoulder;
			}
			break;



			//PickUp
		case ingestingBoulder:
			if (Components.getInstance().BallInLimit.get() || !pickRollersIn){
				Components.getInstance().shooterTrack.set(0);
				shooterState = state.Stopped;
			}
			break;

			//Ejecting
		case ejectingBoulder:
			if (!pickRollersOut){
				Components.getInstance().shooterTrack.set(0);
				shooterState = state.Stopped;
			}
			break;


			//Vision
		case startingVisionShoot:
			if (shootBall){
				Components.getInstance().shooterTrack.set(trackMotorSpeed);
				shooterState = state.Shooting;		
			}
			
			else if (!startVisionShoot){
				Components.getInstance().topShooter.set(0);
				Components.getInstance().botShooter.set(0);
				shooterState = state.Stopped;
			}
			else if (Utilities.tolerance(shooterVisionTopSpeed-toleranceShooter, Components.getInstance().topTachoCounter.get(), shooterVisionTopSpeed+toleranceShooter) && Utilities.tolerance(shooterVisionBotSpeed-toleranceShooter, Components.getInstance().botTachoCounter.get(), shooterVisionBotSpeed+toleranceShooter)){
				CommonArea.isUpToSpeed = true;
				
			}else{
				CommonArea.isUpToSpeed = false;
			}
			break;

		case Shooting:
			if (!Components.getInstance().BallInLimit.get()){
				mytimer.reset();
				mytimer.start();
				shooterState = state.ShootingDelay;
			}else if (!startVisionShoot){
				Components.getInstance().topShooter.set(0);
				Components.getInstance().botShooter.set(0);
				shooterState = state.Stopped;}
			break;
			
		case ShootingDelay:
			
			if (mytimer.get() > 2){
				CommonArea.isShot = true;
				Components.getInstance().shooterTrack.set(0);
				Components.getInstance().topShooter.set(0);
				Components.getInstance().botShooter.set(0);
				mytimer.stop();
				shooterState = state.Stopped;
			}else if (!startVisionShoot){
				CommonArea.isShot = true;
				Components.getInstance().shooterTrack.set(0);
				Components.getInstance().topShooter.set(0);
				Components.getInstance().botShooter.set(0);
				shooterState = state.Stopped;
				}break;
<<<<<<< HEAD
	
		}*/
		Components.getInstance().topShooter.set(SmartDashboard.getDouble("DB/Slider 0"));
		Components.getInstance().botShooter.set(SmartDashboard.getDouble("DB/Slider 1"));
		SmartDashboard.putString("DB/String 0", String.valueOf((1/Components.getInstance().topTachoCounter.getPeriod())));
		SmartDashboard.putString("DB/String 1", String.valueOf((1/Components.getInstance().botTachoCounter.getPeriod())));


		}

	}

