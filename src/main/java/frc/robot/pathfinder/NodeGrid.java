package frc.robot.pathfinder;

import java.util.ArrayList;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.geometry.Translation2d;
import frc.robot.pathfinder.fieldloading.Field;
import frc.robot.pathfinder.fieldloading.SDFGenerator;

public class NodeGrid {
    private final Field field;

    private final Node[][] nodes;

    private final int numNodesX;
    private final int numNodesY;

    /**
     * @param robotWidth the width of the robot including the bumper
     * @param field      the field to generate nodes from
     */
    public NodeGrid(double robotWidth, double distanceCutoff, Field field) {
        this.field = field;

        numNodesX = (int)(field.fieldConfig.fieldSizeMeters.getX()/field.fieldConfig.nodeSpacingMeters);
        numNodesY = (int)(field.fieldConfig.fieldSizeMeters.getY()/field.fieldConfig.nodeSpacingMeters);

        // TODO: Center nodes within the field
        // Translation2d centerOffset = new Translation2d(fieldSize.getX() %
        //         nodeSpacing, fieldSize.getY() % nodeSpacing);
        // this.bottomRightPosition = bottomRightPosition.plus(centerOffset.div(2d));

        nodes = new Node[numNodesX][numNodesY];

        for (int x = 0; x < numNodesX; x++) {
            for (int y = 0; y < numNodesY; y++) {
                Translation2d fieldPos = GridToFieldPos(x, y);
                double distanceFromEdge = SDFGenerator.getDistanceFromNearestObstacle(field, fieldPos);
                nodes[x][y] = new Node(x, y, fieldPos, distanceFromEdge, distanceCutoff, robotWidth);
            }
        }

        //GridVisualizer.visualizeGridAsPNG(this, field, "C:/Users/lucah/Desktop/test/test.png");
    }

    /** @return the field position of the given grid position */
    private Translation2d GridToFieldPos(int gridX, int gridY) {
        return field.fieldConfig.bottomLeftPositionMeters.plus(new Translation2d(gridX, gridY).times(field.fieldConfig.nodeSpacingMeters));
    }

    /** @return the closest node to the given position (clamped to be in bounds) */
    public Node FindCloseNode(Translation2d pos) {
        int x = (int) Math.round((pos.getX() - field.fieldConfig.bottomLeftPositionMeters.getX()) / field.fieldConfig.nodeSpacingMeters);
        int y = (int) Math.round((pos.getY() - field.fieldConfig.bottomLeftPositionMeters.getY()) / field.fieldConfig.nodeSpacingMeters);

        // Clamp position to be in bounds
        x = MathUtil.clamp(x, 0, nodes.length - 1);
        y = MathUtil.clamp(y, 0, nodes[0].length - 1);
        return nodes[x][y];
    }

    /** @return a list of all the nodes within a 3x3 square of the center */
    public ArrayList<Node> GetNeighbors(Node center) {
        ArrayList<Node> neighbors = new ArrayList<Node>();

        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                if (x == 0 && y == 0)
                    continue;

                int checkX = center.gridX + x;
                int checkY = center.gridY + y;

                if (checkX >= 0 && checkX < nodes.length && checkY >= 0 && checkY < nodes[0].length)
                    neighbors.add(nodes[checkX][checkY]);
            }
        }
        return neighbors;
    }

    /** @return the width of this grid */
    public int getWidth() {
        return numNodesX;
    }

    /** @return the height of this grid */
    public int getHeight() {
        return numNodesY;
    }

    /** @return the node at (x, y) clamped to be instide the grid */
    public Node getNodeAt(int x, int y) {
        x = MathUtil.clamp(x, 0, numNodesX - 1);
        y = MathUtil.clamp(y, 0, numNodesY - 1);

        return nodes[x][y];
    }
}
