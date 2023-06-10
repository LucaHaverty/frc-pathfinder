package frc.robot.pathfinder.fieldloading;

import frc.robot.pathfinder.fieldloading.SDFGenerator.Shape;

public class Field {
    public final double sizeX;
    public final double sizeY;

    public final Shape[] obstacles;

    public Field(double sizeX, double sizeY, Shape[] obstacles) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;

        this.obstacles = obstacles;
    }
}
