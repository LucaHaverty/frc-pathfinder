package frc.robot.pathfinder.fieldloading;

import frc.robot.pathfinder.fieldloading.SDFGenerator.Obstacle;

public class Field {
    public final double sizeX;
    public final double sizeY;

    public final Obstacle[] obstacles;

    public Field(double sizeX, double sizeY, Obstacle[] obstacles) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;

        this.obstacles = obstacles;
    }
}
