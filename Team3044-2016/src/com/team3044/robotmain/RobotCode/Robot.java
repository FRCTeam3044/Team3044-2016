package com.team3044.robotmain.RobotCode;

import com.team3044.robotmain.Reference.Components;
import edu.wpi.first.wpilibj.IterativeRobot;

public class Robot extends IterativeRobot {
	Defense defense = new Defense();
	Drive drive = new Drive();
	Shooter shooter = new Shooter();
	Components component = new Components();

	public void robotInit() {
		component.init();
		drive.driveInit();
		defense.defenseInit();
		shooter.shooterInit();

	}

	public void autonomousInit() {

	}

	public void autonomousPeriodic() {
		drive.driveAutoPeriodic();
		defense.defenseAutoPeriodic();
		shooter.shooterAutoPeriodic();

	}

	public void teleopInit() {

	}

	public void teleopPeriodic() {
		drive.driveTeleopPeriodic();
		defense.defenseTeleopPeriodic();
		shooter.shooterTeleopPeriodic();
	}

	public void disabledInit() {
	}

	public void disabledPeriodic() {
	}

	public void testPeriodic() {
	}
}
