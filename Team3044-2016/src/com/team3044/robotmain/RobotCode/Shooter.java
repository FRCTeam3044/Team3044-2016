
package com.team3044.robotmain.RobotCode;

import com.team3044.robotmain.Reference.*;

public class Shooter {
	Components components = new Components();
	FirstController firstJoy = FirstController.getInstance();
	state Pick_Up_State = state.Starting_State;
	double motor_down_speed = 1; //look at motor later
	double motor_up_speed = -1;
	double positionball = 1; // this is the encoders desired place to pick boulder
	double PickUpEncoder;
	public enum state{
		Starting_State,Gate_Coming_Down,Gate_Stopped_Between,Gate_Coming_Up,Ready_To_Pick_Up_Boulder, Boulder_Ingest, Boulder_Eject,
		Ball_InGate_StoppedBetween, Ball_In_Gate_Coming_Up, Ball_In_Gate_Coming_Down, Ball_In_Gate_All_Way_Down, Ball_In_Gate_All_Way_Up, 
	};
	public void shooterInit(){
		
	}

	public void shooterAutoPeriodic() {
		
	}
	public void shooterTeleopPeriodic() {
		switch (Pick_Up_State){
		case Starting_State:
			//if (!components.GateUpLimit.get()){
			//components.gateTalon.set(motor_up_speed);
			if (firstJoy.getRawButton(1)){	//This Button is the bring gate down motor does not need limit switch
				components.gateTalon.setPosition(0);
				
				if(components.gateTalon.getEncPosition() != positionball){				//use range instead of set // dont need to check encoder
					components.gateTalon.set(motor_down_speed);
					Pick_Up_State = state.Gate_Coming_Down;
				}
			} 													/*/else if (firstJoy.getRawButton(2)){boolean GateDownLimit = components.GateDownLimit.get(); //gives a boolean the status of the limit switchif(GateDownLimit != true){components.gateTalon.set(motor_down_speed);Pick_Up_State = Gate_Coming_All_Way_Down;}}*/
		case Gate_Coming_Down:
			if (!firstJoy.getRawButton(1)){ 	//set variables for buttons
				components.gateTalon.set(0);
				Pick_Up_State = Gate_Stopped_Between;

			}else{
				components.PickUpEncoder = components.gateTalon.getAnalogInRaw();	
				if (components.PickUpEncoder == positionball){	//take components. off less than instead of ==
					components.gateTalon.set(0);
					Pick_Up_State = Ready_To_Pick_Up_Boulder;
				}
			}	
		case Gate_Coming_Up:
			if (!firstJoy.getRawButton(2)){
				components.gateTalon.set(0);
				Pick_Up_State = Gate_Stopped_Between;

			}else if (firstJoy.getRawButton(2)){
				if (components.GateUpLimit.get()){
					components.gateTalon.set(0);
					Pick_Up_State = Starting_State;
				}
			}	
		case Ready_To_Pick_Up_Boulder:
			if (firstJoy.getRawButton(2)){
				components.gateTalon.set(motor_up_speed);
				Pick_Up_State = Gate_Coming_Up;
			}else if (components.GateDownLimit.get() && firstJoy.getRawButton(7)){	//<-Start Portcullis and Drwabridge by bringing gate down
				components.gateTalon.set(motor_down_speed);
				Pick_Up_State = Gate_All_Way_Down;//moving

			}	
		}	



	}

}

