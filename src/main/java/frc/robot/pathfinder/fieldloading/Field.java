package frc.robot.pathfinder.fieldloading;


import java.util.ArrayList;

import edu.wpi.first.math.geometry.Translation2d;
import frc.robot.pathfinder.fieldloading.SDFGenerator.Obstacle;

public class Field {
    public static class FieldConfig {
        public Translation2d bottomLeftPositionMeters;
        public Translation2d fieldSizeMeters;
        public double nodeSpacingMeters;

        public FieldConfig(Translation2d bottomLeftPositionMeters, Translation2d fieldSizeMeters, double nodeSpacingMeters) {
            this.bottomLeftPositionMeters = bottomLeftPositionMeters;
            this.fieldSizeMeters = fieldSizeMeters;
            this.nodeSpacingMeters = nodeSpacingMeters;
        }
    }

    public final FieldConfig fieldConfig;
    public final ArrayList<Obstacle> obstacles;

    public Field(FieldConfig fieldConfig, ArrayList<Obstacle> obstacles) {
        this.fieldConfig = fieldConfig;
        this.obstacles = obstacles;
    }
}
