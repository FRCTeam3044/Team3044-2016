
package com.team3044.robotmain.RobotCode;

import com.team3044.robotmain.Reference.*;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class VisionCalc {
	Components components = new Components();
	CommonArea commonarea = new CommonArea();
	
	final int WAITING = 0;
	final int SPINSHOOTER = 1;
	final int AUTOFIND = 2;
	final int ALIGN = 3;
	final int WAITFORSHOOTER = 4;
	final int SHOOT = 5;
	
	final double STARTSPEED = .5;
	final double TURNSPEED = .4;
	int state = 0;
	
	public double CalculatedTurnSpeed(int Angle){
		double turnSpeed;
		turnSpeed = Angle/100;
		return turnSpeed;
	}
	
	public double CalculatedTopSpeed(double Distance){
		double topSpeed;
		topSpeed = Distance;
		return topSpeed;
	}
	
	public double CalculatedBotSpeed(double Distance){
		double botSpeed;
		botSpeed = Distance;
		return botSpeed;
	}
	
	public void Vision() {
		switch(state){
		case(WAITING):					//Waiting to start vision process
			if(commonarea.aimFlag){     //Vision process started
				commonarea.shooterTopSpeed = STARTSPEED;
				commonarea.shooterBotSpeed = -STARTSPEED;
				state = SPINSHOOTER;
			}
		break;
		
		case(SPINSHOOTER):
			if(!commonarea.isTargetSeen){		//No target seen
				if(SmartDashboard.getBoolean("DB/Button 0")){		//If we know to turn CCW
					commonarea.leftDriveSpeed = TURNSPEED;
					commonarea.rightDriveSpeed = -TURNSPEED;
				} else {											//We go CW
					commonarea.leftDriveSpeed = -TURNSPEED;
					commonarea.rightDriveSpeed = TURNSPEED;
				}
				state = AUTOFIND;
			} else if (commonarea.isTargetSeen && !commonarea.isAligned){		//Target seen but not aligned
				state = ALIGN;
			} else if (commonarea.isTargetSeen && commonarea.isAligned && !commonarea.isUpToSpeed){		//Target seen and aligned
				commonarea.shooterTopSpeed = CalculatedTopSpeed();
				commonarea.shooterBotSpeed = -CalculatedBotSpeed();
				state = WAITFORSHOOTER;
			} 
		break;
		
		case(AUTOFIND):
			if (!commonarea.aimFlag){		//Vision process stopped
				commonarea.leftDriveSpeed = 0;
				commonarea.rightDriveSpeed = 0;
				state = WAITING;
		    } else if (commonarea.isTargetSeen){		//Target seen
				commonarea.leftDriveSpeed = 0;
				commonarea.rightDriveSpeed = 0;
				state = ALIGN;
			} 
		break;
		
		case(ALIGN):
			if (!commonarea.aimFlag){		//Vision process stopped
				state = WAITING;
				commonarea.leftDriveSpeed = 0;
				commonarea.rightDriveSpeed = 0;
			} else if (commonarea.isAligned){		//If aligned with target
				state = WAITFORSHOOTER;
			} else {
				
			}
			
	    }
	}
}
