package com.team3044.robotmain.RobotCode;

import com.team3044.robotmain.Reference.*;

import edu.wpi.first.wpilibj.DigitalInput;

public class Gate {
	//Inputs
	boolean gateUp = CommonArea.gateUp;
	boolean gateDown = CommonArea.gateDown;
	boolean portcullisStart = CommonArea.portcullisFlag;
	//Components.GateUpLimit.get()
	//Components.GateDownLimit.get()
	//Components.gateTalon.getEncPosition()
	//Components.BallInLimit.get()


	//States
	public enum state{
		Stopped, movingUp, movingDown, autoPortculis
	}
	state gateState = state.Stopped;

	//Motor Speeds
	double motorSpeedUp = 1;
	double motorSpeedDown = 1;

	//Encoder Values
	double upperEncoderLimit = 1;
	double lowerEncoderLimit = 2;

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

		//STOPPED
		case Stopped:
			if (!Components.GateUpLimit.get() && gateUp){
				Components.gateTalon.set(motorSpeedUp);
				gateState = state.movingUp;
			}else if (!Components.GateDownLimit.get() && gateDown){
				Components.gateTalon.set(motorSpeedDown);
				gateState = state.movingDown;
			}else if (!Components.GateUpLimit.get() && portcullisStart){	//<---- look at this
				Components.gateTalon.set(motorSpeedUp);
				gateState = state.autoPortculis;
			}

			//MOVING UP
		case movingUp:
			if (Components.GateUpLimit.get() || !gateUp){
				Components.gateTalon.set(0);
				gateState = state.Stopped;
			}

			//MOVING DOWN
		case movingDown:
			if (Components.GateDownLimit.get() || !gateDown){
				Components.gateTalon.set(0);
				gateState = state.Stopped;
			}

			//PORTCULIS AUTO
		case autoPortculis:
			if(angleEncoder(Components.gateTalon.getAnalogInRaw())==7){
				
			}else if (Components.GateUpLimit.get() || !portcullisStart || Utilities.tolerance(lowerEncoderLimit, Components.gateTalon.getEncPosition(), upperEncoderLimit)){
				Components.gateTalon.set(0);
				gateState = state.Stopped;
			}
		}
	}
}