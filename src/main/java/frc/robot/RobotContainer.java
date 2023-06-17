// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.pathplanner.lib.PathConstraints;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.pathfinder.GridVisualizer;
import frc.robot.pathfinder.Pathfinder;
import frc.robot.pathfinder.PathfinderResult;
import edu.wpi.first.wpilibj2.command.Command;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer { 
  private final Pathfinder pathfinder = new Pathfinder(1.0, "chargedUp");


  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    PathfinderResult result = pathfinder.findPath(new Translation2d(1.0, 1.6), new Translation2d(19.4, 8.0));
    GridVisualizer.visualizeGridAsPNG(pathfinder, result.getAsTrajectory(new PathConstraints(1, 1)), "C:/Users/lucah/Desktop/test/test.png");
    

    System.out.println(result.getPositionList());
    // Configure the button bindings
    configureButtonBindings();

    // var path = pathfinder.FindPath(new Translation2d(1, 0.1), new Translation2d(5, 9)).simplifiedPath;
    // for (var p : path) {
    //   System.out.println(p);
    // }
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {}

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return null;
  }
}
