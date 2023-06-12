package frc.robot.pathfinder;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.geometry.Translation2d;

public class Node {
    public final int gridX;
    public final int gridY;
    public final Translation2d fieldPos;
    public final double distFromObj;
    public final double distanceWeight;

    public final boolean driveable;

    public double gCost;
    public double hCost;
    public double fCost() { return gCost + hCost; }

    public Node parent;

    public Node(int gridX, int gridY, Translation2d fieldPos, double distFromObj, double distanceCutoff,
            double robotWidth)
    {
        this.gridX = gridX;
        this.gridY = gridY;
        this.fieldPos = fieldPos;
        this.distFromObj = distFromObj - robotWidth/2d;
        this.driveable = (distFromObj) > 0;

        double cutoff = distanceCutoff;
        if (distFromObj < cutoff) // if the node is near a wall, scale distanceWeight from 0-1 based on how close it is
            distanceWeight = MathUtil.interpolate(1, 0, distFromObj / cutoff);
        else distanceWeight = 0;
    }
}
