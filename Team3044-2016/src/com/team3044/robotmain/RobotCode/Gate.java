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
	double desiredEncoderLimit = 2;
	double lowerEncoderLimit = 3;
	


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
			if(Utilities.tolerance(lowerEncoderLimit, desiredEncoderLimit, upperEncoderLimit)){
				
		}else if (Components.GateUpLimit.get() || !portcullisStart){
				Components.gateTalon.set(0);
				gateState = state.Stopped;
			}
		}
	}
}