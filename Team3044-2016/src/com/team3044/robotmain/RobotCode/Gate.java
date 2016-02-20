
package com.team3044.robotmain.RobotCode;

import com.team3044.robotmain.Reference.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Gate {

	boolean gateUp;
	boolean gateDown;
	boolean chevalStart;
	//Components.GateUpLimit.get() 
	//Components.GateDownLimit.get()
	//Components.gateTalon.getEncPosition()
	//Components.BallInLimit.get()
	boolean encoderCalibrated = false;

	Components comp = Components.getInstance();
	final double currentLimit=2;
	FirstController controller = FirstController.getInstance();

	public enum state{
		Init, encoderZeroing, Stopped, movingUp, movingDown, chevalGoingDown, chevalDown, chevalMovingUp, stoppedChevalMid
	}
	//state gateState = state.Stopped;
	state gateState = state.Init;

	double motorSpeedUp = -.6;
	double motorSpeedDown = .7;

	double upperEncoderLimit = -150;
	double lowerEncoderLimit = -1450;//check values

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
		comp.gateTalon.enableBrakeMode(true);
		comp.gateTalon.setEncPosition(0);
		comp.gateTalon.setInverted(true);
	}

	public void gateAutoPeriodic() {

	}

	public void gateTeleopPeriodic() {
		chevalStart = CommonArea.chevalFlag;
		gateUp = CommonArea.gateUp;
		gateDown = CommonArea.gateDown;
		System.out.println("GATE");
		SmartDashboard.putString("DB/String 2", String.valueOf(gateState));
		SmartDashboard.putString("DB/String 3", String.valueOf(comp.GateDownLimit.get()));
		switch (gateState){

		//INIT
		case Init:
			System.out.println("GATEINIT");
			if (comp.GateUpLimit.get()){
				comp.gateTalon.set(motorSpeedUp);
				gateState = state.encoderZeroing;
			}else{
				comp.gateTalon.setEncPosition(0);
				encoderCalibrated = true;
				gateState = state.Stopped;
			}
			break;

			//ENCODERZEROING
		case encoderZeroing:
			if (!comp.GateUpLimit.get()){
				comp.gateTalon.set(0);
				comp.gateTalon.setEncPosition(0);
				encoderCalibrated = true;
				gateState = state.Stopped;
			}else if (gateUp){
				gateState = state.movingUp;
			}else if (gateDown){
				comp.gateTalon.set(motorSpeedDown);
				gateState = state.movingDown;
			}
			break;

			//STOPPED
		case Stopped:
			if (gateUp && Components.getInstance().GateUpLimit.get()){
				Components.getInstance().gateTalon.set(motorSpeedUp);
				gateState = state.movingUp;
			}else if (gateDown && Components.getInstance().GateDownLimit.get()){
				Components.getInstance().gateTalon.set(motorSpeedDown);
				gateState = state.movingDown;
			}/*else if (ChevalStart && !Components.getInstance().GateUpLimit.get() && encoderCalibrated){	//<---- look at this
				Components.getInstance().gateTalon.set(motorSpeedUp);
				gateState = state.autoCheval;
			}*/
			break;

			//MOVING UP
		case movingUp:
			if (!Components.getInstance().GateUpLimit.get() ||!gateUp){
				Components.getInstance().gateTalon.set(0);
				gateState = state.Stopped;
			}
			break;

			//MOVING DOWN
		case movingDown:
			if (!Components.getInstance().GateDownLimit.get() || !gateDown){
				Components.getInstance().gateTalon.set(0);
				gateState = state.Stopped;
			}/*if(comp.gateTalon.getOutputCurrent() > currentLimit){
				}*/

			break;


			//Cheval AUTO
			/*case autoCheval:
			if(angleEncoder(Components.getInstance().gateTalon.getEncPosition())==7){

			}else if (Components.getInstance().GateUpLimit.get() || !ChevalStart || Utilities.tolerance(lowerEncoderLimit, Components.getInstance().gateTalon.getEncPosition(), upperEncoderLimit)){
				Components.getInstance().gateTalon.set(0);
				gateState = state.Stopped;
			}controller.getRightY()
			break;*/
		}
	}
	public void gateTestPeriodic(){
		
		SmartDashboard.putString("DB/String 0", String.valueOf(comp.GateUpLimit.get()));
		SmartDashboard.putString("DB/String 1", String.valueOf(comp.GateDownLimit.get()));
		
		
		/*if(controller.getRawButton(3)){
			if (comp.BallInLimit.get()){
				comp.shooterTrack.set(0);
			}else{
				comp.shooterTrack.set(-1);
			}
		}else if (controller.getRawButton(2)){
			comp.shooterTrack.set(1);

		}else if (controller.getRawButton(6)){
			comp.shooterTrack.set(-1);
		}else{
			comp.shooterTrack.set(0);
		}

		if(controller.getRawButton(4)){
			if(comp.gateTalon.getEncPosition() > -500){
				comp.gateTalon.set(-.3);
			}else{
				comp.gateTalon.set(0);
			}
		}else if (controller.getRawButton(1)){
			if(comp.gateTalon.getEncPosition() < 2500 ){
				comp.gateTalon.set(.4);;
			}else{
				comp.gateTalon.set(0);
			}

		}
		else{
			comp.gateTalon.set(0);

		}


		//Components comp = Components.getInstance();
		//comp.gateTalon.set(SmartDashboard
		//	.getDouble("DB/Slider 0"));
		SmartDashboard.putString("DB/String 5", String.valueOf(comp.gateTalon.getEncPosition()));
		SmartDashboard.putString("DB/String 6", String.valueOf(comp.BallInLimit.get()));

		//SmartDashboard.putString("Current "+"DB/String 0", String.valueOf(comp.gateTalon.getOutputCurrent()));
		//SmartDashboard.putString("Encoder "+ "DB/String 0 ", String.valueOf(comp.gateTalon.getEncPosition()));

		//SmartDasDashboard.putString("Gate Down limit " + "DB/String 2", String.valueOf(comp.GateDownLimit.get()));
		//SmartDashboard.putString("Motor Speed" + "DB/String 3", String.valueOf(controller.getRightY()));
*/
	}
}
