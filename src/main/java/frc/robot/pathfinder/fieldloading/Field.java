package frc.robot.pathfinder.fieldloading;


import java.util.ArrayList;

import edu.wpi.first.math.geometry.Translation2d;
import frc.robot.pathfinder.fieldloading.SDFGenerator.Obstacle;

public class Field {

    public static class FieldConfig {
        public Translation2d bottomLeftPositionMeters;
        public Translation2d fieldSizeMeters;
        public double nodeSpacingMeters;

        /** Contains all configuration settings for a field
         * 
         * @param bottomLeftPositionMeters the position of the bottom left of the field
         * @param fieldSizeMeters the full size of the field from bottom left to top rights
         * @param nodeSpacingMeters the real life distance between nodes used in pathfinding
         */
        public FieldConfig(Translation2d bottomLeftPositionMeters, Translation2d fieldSizeMeters, double nodeSpacingMeters) {
            this.bottomLeftPositionMeters = bottomLeftPositionMeters;
            this.fieldSizeMeters = fieldSizeMeters;
            this.nodeSpacingMeters = nodeSpacingMeters;
        }
    }

    public final FieldConfig fieldConfig;
    public final ArrayList<Obstacle> obstacles;

    /** Contains all the information needed to generate a NodeGrid including positions of objects 
     * 
     * @param fieldConfig the configuration of this field (size, node spacing, etc.) 
     * @param obstacles a list of all the obstacles present on the field
     */
    public Field(FieldConfig fieldConfig, ArrayList<Obstacle> obstacles) {
        this.fieldConfig = fieldConfig;
        this.obstacles = obstacles;
    }
}
