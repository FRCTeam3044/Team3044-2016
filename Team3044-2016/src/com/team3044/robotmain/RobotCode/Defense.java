
package com.team3044.robotmain.RobotCode;

import com.team3044.robotmain.Reference.*;

public class Defense {

	SecondaryController secondJoy = SecondaryController.getInstance();

	public enum state {
		LA_MOVING_UP, LA_MOVING_UP_TARGET, LA_MOVING_DOWN, LA_MOVING_DOWN_TARGET, LA_CONFLICT, LA_STOPPED, 
		UA_MOVING_UP, UA_MOVING_UP_TARGET, UA_MOVING_DOWN, UA_MOVING_DOWN_TARGET, UA_CONFLICT, UA_STOPPED, 
		STOPPED, MOVING_MANUALLY, MOVING_TARGET
	}

	// Button 8 HOME

	final int X = 11; // This will be the angle of the POT for the LongArm
	final int Y = 12; // This will be the angle of the POT for the ShortArm
	state LOWER_ARM = state.LA_STOPPED;
	state MAIN = state.STOPPED;
	state UPPER = state.UA_STOPPED;

	public void defenseInit() {

	}

	public void defenseAutoPeriodic() {

	}

	public void defenseTeleopPeriodic() {
		switch (LOWER_ARM)
		{
		case LA_STOPPED:
			
		default:
			LOWER_ARM=state.LA_STOPPED;
			break;
		case STOPPED:
		}
			
	}
}
