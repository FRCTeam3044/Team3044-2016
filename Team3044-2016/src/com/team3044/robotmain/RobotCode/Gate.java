
package com.team3044.robotmain.RobotCode;

import com.team3044.robotmain.Reference.*;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Gate {
	/*//Inputs
	boolean gateUp = CommonArea.gateUp;
	boolean gateDown = CommonArea.gateDown;
	boolean chevalStart = CommonArea.chevalFlag;
	//Components.GateUpLimit.get() 
	//Components.GateDownLimit.get()
	//Components.gateTalon.getEncPosition()
	//Components.BallInLimit.get()
	boolean encoderCalibrated = false;
*/
	Components comp = Components.getInstance();
	final double currentLimit=2;
	FirstController controller = FirstController.getInstance();
	//States
	/*public enum state{
		Init, encoderZeroing, Stopped, movingUp, movingDown, chevalGoingDown, chevalDown, chevalMovingUp, stoppedChevalMid
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
	}*/

	public void gateInit() {
		
		
	}

	public void gateAutoPeriodic() {

	}

	public void gateTeleopPeriodic() {
		
		/*switch (gateState){

		//INIT
		case Init:
			if (!comp.GateUpLimit.get()){
				comp.gateTalon.set(motorSpeedUp);
				gateState = state.encoderZeroing;
			}else{
				comp.gateTalon.setPosition(0);
				encoderCalibrated = true;
				gateState = state.Stopped;
			}
			break;
		
		//ENCODERZEROING
		case encoderZeroing:
			if (comp.GateUpLimit.get()){
				comp.gateTalon.set(0);
				comp.gateTalon.setPosition(0);
				encoderCalibrated = true;
				gateState = state.Stopped;
			}else if (gateUp){
				gateState = state.movingUp;
			}else if (gateDown){
				comp.getInstance().gateTalon.set(motorSpeedDown);
				gateState = state.movingDown;
			}
			break;
		
		//STOPPED
		case Stopped:
			if (gateUp && !Components.getInstance().GateUpLimit.get()){
				Components.getInstance().gateTalon.set(motorSpeedUp);
				gateState = state.movingUp;
			}else if (gateDown && !Components.getInstance().GateDownLimit.get()){
				Components.getInstance().gateTalon.set(motorSpeedDown);
				gateState = state.movingDown;
			}else if (ChevalStart && !Components.getInstance().GateUpLimit.get() && encoderCalibrated){	//<---- look at this
				Components.getInstance().gateTalon.set(motorSpeedUp);
				gateState = state.autoCheval;
			}
			break;

			//MOVING UP
		case movingUp:
			if (Components.getInstance().GateUpLimit.get() || !gateUp){
				Components.getInstance().gateTalon.set(0);
				gateState = state.Stopped;
			}
			break;

			//MOVING DOWN
		case movingDown:
			if (Components.getInstance().GateDownLimit.get() || !gateDown){
				Components.getInstance().gateTalon.set(0);
				gateState = state.Stopped;
			}if(comp.gateTalon.getOutputCurrent() > currentLimit){}
			break;*/

			//Cheval AUTO
		/*case autoCheval:
			if(angleEncoder(Components.getInstance().gateTalon.getEncPosition())==7){
				
			}else if (Components.getInstance().GateUpLimit.get() || !ChevalStart || Utilities.tolerance(lowerEncoderLimit, Components.getInstance().gateTalon.getEncPosition(), upperEncoderLimit)){
				Components.getInstance().gateTalon.set(0);
				gateState = state.Stopped;
			}
			break;
		}*/
	}
	public void gateTestPeriodic(){
		comp.gateTalon.set(controller.getRightY());
		SmartDashboard.putString("Current "+"DB/String 0", String.valueOf(comp.gateTalon.getOutputCurrent()));
		SmartDashboard.putString("Gate Up Limit " + "DB/String 1", String.valueOf(comp.GateUpLimit.get()));
		SmartDashboard.putString("Gate Down limit " + "DB/String 2", String.valueOf(comp.GateDownLimit.get()));
		SmartDashboard.putString("Motor Speed" + "DB/String 3", String.valueOf(controller.getRightY()));
		
	}
}
