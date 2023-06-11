package frc.robot.pathfinder.fieldloading;

import org.json.simple.JSONObject;

import edu.wpi.first.math.geometry.Translation2d;

public class SDFGenerator {
    public static double getDistanceFromNearestObstacle(Field field, Translation2d position) {
        double minDistance = Double.MAX_VALUE;

        for (Obstacle obstacle : field.obstacles) {
            double distance = obstacle.getDistanceFrom(position);
            if (distance < minDistance)
                minDistance = distance;
        }
        return minDistance;
    }

    public static abstract class Obstacle {
        public final Translation2d position;

        public Obstacle(Translation2d position) {
            this.position = position;
        }

        public abstract double getDistanceFrom(Translation2d point);

        public static Obstacle fromJSON(JSONObject obstacle) {
            Translation2d position = new Translation2d((double)obstacle.get("xPos"), (double)obstacle.get("yPos"));

            String obstacleType = (String)obstacle.get("type");
            switch (obstacleType) {
                case "rectangle":
                        return new Rectangle(position, new Translation2d((double)obstacle.get("width"), (double)obstacle.get("height")));
                default:
                    return null;
            }
        }
    }

    public static class Rectangle extends Obstacle {
        public final Translation2d scale;

        public Rectangle(Translation2d position, Translation2d scale) {
            super(position);
            this.scale = scale;
        }

        @Override
        public double getDistanceFrom(Translation2d point) {
            point.minus(position);

            Translation2d distance2d = new Translation2d(Math.abs(point.getX()), Math.abs(point.getY())).minus(scale.div(2));
            return (new Translation2d(
                        Math.max(distance2d.getX(), 0), Math.max(distance2d.getY(), 0)).getNorm()
                    + Math.min(Math.max(distance2d.getX(), distance2d.getY()), 0));
        }
    }

    public static class Circle extends Obstacle {
        public final double radius;

        public Circle(Translation2d position, double radius) {
            super(position);
            this.radius = radius;
        }

        @Override
        public double getDistanceFrom(Translation2d point) {
            point.plus(position);

            return position.getNorm() - radius;
        }
    }

    public class FieldBoundaries extends Obstacle {
        private final Translation2d minBounds;
        private final Translation2d maxBounds;

        public FieldBoundaries(Translation2d minBounds, Translation2d maxBounds) {
            super(null);

            this.minBounds = minBounds;
            this.maxBounds = maxBounds;
        }

        @Override
        public double getDistanceFrom(Translation2d point) {
            double xDistance = Math.min(point.getX() - minBounds.getX(), maxBounds.getX() - point.getX());
            double yDistance = Math.min(point.getY() - minBounds.getY(), maxBounds.getY() - point.getY());

            return Math.min(xDistance, yDistance);
        }
    }
}
