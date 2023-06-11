package frc.robot.pathfinder.fieldloading;


import java.util.ArrayList;

import edu.wpi.first.math.geometry.Translation2d;
import frc.robot.pathfinder.fieldloading.SDFGenerator.Obstacle;

public class Field {
    public static class FieldConfig {
        private Translation2d size;

        public FieldConfig(Translation2d size) {
            this.size = size;
        }
    }

    public final FieldConfig fieldConfig;
    public final ArrayList<Obstacle> obstacles;

    public Field(FieldConfig fieldConfig, ArrayList<Obstacle> obstacles) {
        this.fieldConfig = fieldConfig;
        this.obstacles = obstacles;
    }
}
