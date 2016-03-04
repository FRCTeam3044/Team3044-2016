
package com.team3044.robotmain.RobotCode;

import com.team3044.robotmain.Reference.*;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class NewDefense {

	public final int TARGET_LA_X1 = 256; // DEFENSE ENCODER POSITIONS LOWER ARM
	public final int TARGET_LA_X2 = 300;
	public final int TARGET_LA_Y1 = 256;
	public final int TARGET_LA_Y2 = 400;
	public final int TARGET_LA_H1 = 256;
	public final int TARGET_LA_H2 = 512;

	public final int TARGET_UA_X1 = 767; // DEFENSE ENCODER POSITIONS UPPER ARM
	public final int TARGET_UA_X2 = 896;
	public final int TARGET_UA_Y1 = 612;
	public final int TARGET_UA_Y2 = 767;
	public final int TARGET_UA_H1 = 512;
	public final int TARGET_UA_H2 = 128;

	public boolean H1; // TARGET BUTTONS
	public boolean H2;
	public boolean X1;
	public boolean X2;
	public boolean Y1;
	public boolean Y2;

	public int lowerArmEncoder; // ENCODERS
	public int upperArmEncoder;
	public int lowerArmEncoderTarget;
	public int upperArmEncoderTarget;

	public int testLimits; // DEFENSE TEST

	public final double lowerArmMovingUpSpeed = -0.15; // MOTOR SPEEDS
	public final double lowerArmMovingDownSpeed = 0.05;
	public final double lowerArmStopSpeed = 0;
	public final double upperArmMovingUpSpeed = -0.1;
	public final double upperArmMovingDownSpeed = 0.1;
	public final double upperArmStopSpeed = 0;
	public final double calibrationStopSpeed = 0;
	public final double calibrationMovingUpSpeed = -0.1;
	public final double calibrationMovingDownSpeed = 0.1;

	public CANTalon lowerArmMotor; // MOTORS
	public CANTalon upperArmMotor;

	public DigitalInput conflictDigitalIO; // CONFLICT LIMIT SWITCH


	public boolean lowerArmLimitSwitchHome; // LIMIT SWITCHES
	public boolean lowerArmLimitSwitchTooFar;
	public boolean upperArmLimitSwitchHome;
	public boolean upperArmLimitSwitchTooFar;
	public boolean limitSwitchConflict;

	public void defenseInit() {

		lowerArmMotor = Components.getInstance().lowerArm;
		upperArmMotor = Components.getInstance().upperArm;
		lowerArmMotor.set(lowerArmStopSpeed);
		upperArmMotor.set(upperArmStopSpeed);

		conflictDigitalIO = Components.getInstance().conflict;

	}

	public void defenseAutoPeriodic() {

	}

	public void defenseTeleopPeriodic() {
		
	}
}