
package com.team3044.robotmain.RobotCode;

import com.team3044.robotmain.Reference.*;

public class Defense {

	SecondaryController secondJoy = SecondaryController.getInstance();

	public enum state {
		LA_MOVING_UP, LA_MOVING_UP_TARGET, LA_MOVING_DOWN, LA_MOVING_DOWN_TARGET, LA_CONFLICT, LA_STOPPED, 
		UA_MOVING_UP, UA_MOVING_UP_TARGET, UA_MOVING_DOWN, UA_MOVING_DOWN_TARGET, UA_CONFLICT, UA_STOPPED, 
		MAIN_STOPPED, MAIN_MOVING_MANUALLY, MAIN_MOVING_TARGET
	}

	// Button 8 HOME

	public double lowerArmPOT = Components.longArm.getPosition();
	public double upperArmPOT = Components.shortArm.getPosition();
	
	public boolean LA_UP;
	public boolean LA_DOWN;
	public boolean LA_UP_TARGET;
	public boolean LA_DOWN_TARGET;
	public boolean LA_TARGET_VALUE;
	public boolean LA_STOP;
	
	public boolean UA_UP;
	public boolean UA_DOWN;
	public boolean UA_UP_TARGET;
	public boolean UA_DOWN_TARGET;
	public boolean UA_TARGET_VALUE;
	public boolean UA_STOP;
	
	public double LA_MOVING_UP_SPEED;
	public double LA_MOVING_DOWN_SPEED;
	public double UA_MOVING_UP_SPEED;
	public double UA_MOVING_DOWN_SPEED;
	
	
	state LOWER_ARM = state.LA_STOPPED;
	state MAIN = state.MAIN_STOPPED;
	state UPPER_ARM = state.UA_STOPPED;

	public boolean LA_HOME = Components.shortArm.isFwdLimitSwitchClosed();
	public boolean LA_TOO_FAR = Components.shortArm.isRevLimitSwitchClosed();
	public boolean UA_HOME = Components.longArm.isFwdLimitSwitchClosed();
	public boolean UA_TOO_FAR = Components.longArm.isRevLimitSwitchClosed();

	public void defenseInit() {

	}

	public void defenseAutoPeriodic() {

	}

	public void defenseTeleopPeriodic() {
		switch (LOWER_ARM) {
		
		default:
			LOWER_ARM = state.LA_STOPPED;
			break;

		case LA_STOPPED:
			if(LA_UP = true && !LA_TOO_FAR){
				LOWER_ARM = state.LA_MOVING_UP;
				Components.longArm.set(LA_MOVING_UP_SPEED);
			}
			if(LA_UP_TARGET = true && !LA_TOO_FAR){
				LOWER_ARM = state.LA_MOVING_UP_TARGET;
				Components.longArm.set(LA_MOVING_UP_SPEED);
			}
			if(LA_DOWN_TARGET = true && !LA_HOME){
				LOWER_ARM = state.LA_MOVING_DOWN_TARGET;
				Components.longArm.set(LA_MOVING_DOWN_SPEED);
			}
			if(LA_DOWN = true && !LA_HOME){
				LOWER_ARM = state.LA_MOVING_DOWN;
				Components.longArm.set(LA_MOVING_DOWN_SPEED);
			}
			break;
			
		case LA_CONFLICT:
			break;
		case LA_MOVING_UP:
			break;
		case LA_MOVING_DOWN:
			break;
		case LA_MOVING_UP_TARGET:
			break;
		case LA_MOVING_DOWN_TARGET:
			break;

		}
		switch (UPPER_ARM) {

		default:
			UPPER_ARM = state.UA_STOPPED;
			break;

		case UA_STOPPED:
			break;
		case UA_CONFLICT:
			break;
		case UA_MOVING_UP:
			break;
		case UA_MOVING_DOWN:
			break;
		case UA_MOVING_UP_TARGET:
			break;
		case UA_MOVING_DOWN_TARGET:
			break;
		}
		switch (MAIN) {

		default:
			MAIN = state.MAIN_STOPPED;
			break;

		case MAIN_MOVING_MANUALLY:
			break;
		case MAIN_MOVING_TARGET:
			break;
		}

	}
}
