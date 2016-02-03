package com.team3044.robotmain.RobotCode;

import com.team3044.robotmain.Reference.*;

import edu.wpi.first.wpilibj.DigitalInput;

public class Gate {
	Components components = new Components();
	CommonArea commonarea = new CommonArea();
	
	//Inputs
	boolean gateUp = commonarea.gateUp;
	boolean gateDown = commonarea.gateDown;
	boolean portcullisStart = commonarea.portcullisFlag;
	//components.GateUpLimit.get()
	//components.GateDownLimit.get()
	//components.gateTalon.getEncPosition()
	//components.BallInLimit.get()
	
	
	//States
	public enum state{
		Stopped, movingUp, movingDown, autoPortculis
	}
	state gateState = state.Stopped;
	
	//Motor Speeds
	double motorSpeedUp = 1;
	double motorSpeedDown = 1;
	
	
	public void gateInit() {

	}

	public void gateAutoPeriodic() {

	}

	public void gateTeleopPeriodic() {
		switch (gateState){
		
		//STOPPED
		case Stopped:
			if (!components.GateUpLimit.get() && gateUp){
				components.gateTalon.set(motorSpeedUp);
				gateState = state.movingUp;
			}else if (!components.GateDownLimit.get() && gateDown){
				components.gateTalon.set(motorSpeedDown);
				gateState = state.movingDown;
			}else if (!components.BallInLimit.get() && portcullisStart){	//<---- look at this
				gateState = state.autoPortculis;
			}
		
		//MOVING UP
		case movingUp:
			if (components.GateUpLimit.get() || !gateUp){
				components.gateTalon.set(0);
				gateState = state.Stopped;
			}
		
		//MOVING DOWN
		case movingDown:
			if (components.GateDownLimit.get() || !gateDown){
				components.gateTalon.set(0);
				gateState = state.Stopped;
		}
		
		
	}

}}