
package com.team3044.robotmain.RobotCode;

import com.team3044.robotmain.Reference.*;

public class Defense {
	Components components = new Components();
	SecondaryController secondJoy = SecondaryController.getInstance();
    public void defenseInit() {
    
    	
	final int MOVING = 1;
	final int STOPPING = 2;
	final int HOME = 3;
	final int SALLY_PORT1 = 4;
	final int SALLY_PORT2 = 5;
	final int DRAW_BRIDGE1 = 6;
	final int DRAWBRIDGE2 = 7;
	final int OTHER = 8;
	
	int DEFENSE_STATE = 9;
    }
    
    public void defenseAutoPeriodic() {
    	
    }
     
    public void defenseTeleopPeriodic() {
    	switch(DEFENSE_STATE){
    	case: HOME
    		
    	break;
    	}
    }

}