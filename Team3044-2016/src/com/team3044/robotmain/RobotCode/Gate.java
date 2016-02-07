
package com.team3044.robotmain.RobotCode;

import com.team3044.robotmain.Reference.*;

import edu.wpi.first.wpilibj.DigitalInput;

public class Gate {
	//Inputs
	boolean gateUp = CommonArea.gateUp;
	boolean gateDown = CommonArea.gateDown;
	boolean ChevalStart = CommonArea.ChevalFlag; //set up cheval button
	//Components.GateUpLimit.get() 
	//Components.GateDownLimit.get()
	//Components.gateTalon.getEncPosition()
	//Components.BallInLimit.get()
	boolean encoderCalibrated = false;


	//States
	public enum state{
		Init, encoderZeroing, Stopped, movingUp, movingDown, autoCheval
	}
	state gateState = state.Init;

	//Motor Speeds
	double motorSpeedUp = 1;
	double motorSpeedDown = -1;

	//Encoder tolerance Values
	double upperEncoderLimit = -.7;
	double lowerEncoderLimit = -.8;//check values

	//variables
	public double initialAngleDifference;
	public double Desired;
	
	//Calculation for AnglefromVertical
	public double angleEncoder (double encoderPosition){
		double currentActualDegrees;
		double supplementOfDesired;
		currentActualDegrees = (360/1023)* encoderPosition;
		supplementOfDesired = currentActualDegrees - initialAngleDifference;
		Desired = supplementOfDesired-180;
		return Desired;
	}

	public void gateInit() {

	}

	public void gateAutoPeriodic() {

	}

	public void gateTeleopPeriodic() {
		switch (gateState){

		//INIT
		case Init:
			if (!Components.GateUpLimit.get()){
				Components.gateTalon.set(motorSpeedUp);
				gateState = state.encoderZeroing;
			}else{
				Components.gateTalon.setPosition(0);
				encoderCalibrated = true;
				gateState = state.Stopped;
			}
			break;
		
		//ENCODERZEROING
		case encoderZeroing:
			if (Components.GateUpLimit.get()){
				Components.gateTalon.set(0);
				Components.gateTalon.setPosition(0);
				encoderCalibrated = true;
				gateState = state.Stopped;
			}else if (gateUp){
				gateState = state.movingUp;
			}else if (gateDown){
				Components.gateTalon.set(motorSpeedDown);
				gateState = state.movingDown;
			}
			break;
		
		//STOPPED
		case Stopped:
			if (gateUp && !Components.GateUpLimit.get()){
				Components.gateTalon.set(motorSpeedUp);
				gateState = state.movingUp;
			}else if (gateDown && !Components.GateDownLimit.get()){
				Components.gateTalon.set(motorSpeedDown);
				gateState = state.movingDown;
			/*}else if (ChevalStart && !Components.GateUpLimit.get() && encoderCalibrated){	//<---- look at this
				Components.gateTalon.set(motorSpeedUp);
				gateState = state.autoCheval;*/
			}
			break;

			//MOVING UP
		case movingUp:
			if (Components.GateUpLimit.get() || !gateUp){
				Components.gateTalon.set(0);
				gateState = state.Stopped;
			}
			break;

			//MOVING DOWN
		case movingDown:
			if (Components.GateDownLimit.get() || !gateDown){
				Components.gateTalon.set(0);
				gateState = state.Stopped;
			}
			break;

			//Cheval AUTO
		/*case autoCheval:
			if(angleEncoder(Components.gateTalon.getAnalogInRaw())==7){
				
			}else if (Components.GateUpLimit.get() || !ChevalStart || Utilities.tolerance(lowerEncoderLimit, Components.gateTalon.getEncPosition(), upperEncoderLimit)){
				Components.gateTalon.set(0);
				gateState = state.Stopped;
			}
			break;*/
		}
	}
}
