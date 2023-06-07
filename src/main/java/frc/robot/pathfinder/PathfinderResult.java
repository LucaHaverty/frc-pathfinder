package frc.robot.pathfinder;

import java.util.ArrayList;

import edu.wpi.first.math.geometry.Translation2d;

public class PathfinderResult {
    public final boolean pathFound;
    public final ArrayList<Translation2d> simplifiedPath;
    public final ArrayList<Node> path;

    public PathfinderResult(boolean pathFound, ArrayList<Translation2d> simplifiedPath, ArrayList<Node> path) {
        this.pathFound = pathFound;
        this.simplifiedPath = simplifiedPath;
        this.path = path;
    }
}
