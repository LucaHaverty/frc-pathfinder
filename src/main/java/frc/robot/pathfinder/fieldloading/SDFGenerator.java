package frc.robot.pathfinder.fieldloading;

import edu.wpi.first.math.geometry.Translation2d;

public class SDFGenerator {

    public abstract class Shape {
        protected final Translation2d position;

        public Shape(Translation2d position) {
            this.position = position;
        }

        public abstract double getDistanceFrom(Translation2d point);
    }
    
    public class Rectangle extends Shape {
        private final Translation2d scale;

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

    public class Circle extends Shape {
        private final double radius;

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
}
