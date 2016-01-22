
package com.team3044.robotmain.RobotCode;

import com.team3044.robotmain.Reference.*;

public class Defense {
	Components components = new Components();
	SecondaryController secondJoy = SecondaryController.getInstance();
    public void defenseInit() {
        
    }
    
    public void defenseAutoPeriodic() {
    	
    }
     
    public void defenseTeleopPeriodic() {
    	if(secondJoy.getRawButton(3)){
    		components.longArm.set(1);
    	}
    }

}
//Hi Ryan