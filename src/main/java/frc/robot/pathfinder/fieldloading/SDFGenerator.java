package frc.robot.pathfinder.fieldloading;

import org.json.simple.JSONObject;

import edu.wpi.first.math.geometry.Translation2d;

public class SDFGenerator {
    /**
     * @param field the field to use
     * @param position the real life position of the robot on the field
     * @return the distance to the nearest edge of any obstacle or wall
     */
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
        protected final Translation2d position;

        /** Represents any obstacle or obstruction on the field
         * 
         * @param position the location of this obstacle
         */
        public Obstacle(Translation2d position) {
            this.position = position;
        }

        public abstract double getDistanceFrom(Translation2d point);

        /** Create an obstacle instace from a JSON obstacle
         * 
         * @param obstacleJSON the JSON object containing the obstacles data
         * @return an obstacle created from the JSON object inputed
         */
        public static Obstacle fromJSON(JSONObject obstacleJSON) {
            Translation2d position = new Translation2d((double) obstacleJSON.get("xPos"), (double) obstacleJSON.get("yPos"));

            String obstacleType = (String) obstacleJSON.get("type");
            switch (obstacleType) {
                case "rectangle":
                    return new Rectangle(position,
                            new Translation2d((double) obstacleJSON.get("width"), (double) obstacleJSON.get("height")));

                case "circle":
                    return new Circle(position, (double) obstacleJSON.get("radius"));

                case "fieldBoundaries":
                    return new Rectangle(position,
                            new Translation2d((double) obstacleJSON.get("fieldSizeX"),
                                    (double) obstacleJSON.get("FieldSizeY")));

                default:
                    return null;
            }
        }
    }

    public static class Rectangle extends Obstacle {
        private final Translation2d scale;

        /** A rectangle shaped obstacle on the field
         * 
         * @param position the bottom left corner of this rectangle
         * @param scale the scale of this rectangle
         */
        public Rectangle(Translation2d position, Translation2d scale) {
            super(position);
            this.scale = scale;
        }

        @Override
        public double getDistanceFrom(Translation2d point) {
            point.minus(position);

            Translation2d distance2d = new Translation2d(Math.abs(point.getX()), Math.abs(point.getY()))
                    .minus(scale.div(2));
            return (new Translation2d(
                        Math.max(distance2d.getX(), 0), Math.max(distance2d.getY(), 0)).getNorm()
                    + Math.min(Math.max(distance2d.getX(), distance2d.getY()), 0));
        }
    }

    public static class Circle extends Obstacle {
        private final double radius;

        /** A circle shaped obstacle on the field
         * 
         * @param position the center position of this circle
         * @param radius the radius of this circle
         */
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

        /** The outside boundaries of the field
         * 
         * @param minBounds the bottom left most corner of the field
         * @param maxBounds the top right most corner of the field
         */
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
