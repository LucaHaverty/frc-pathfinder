package frc.robot.pathfinder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

import edu.wpi.first.math.geometry.Translation2d;

public class Pathfinder {
    public double distanceCutoff = 1;
    public double robotWidth = 0;
    public NodeGrid grid = null;

    public Pathfinder(double robotWidth) {
        this.robotWidth = robotWidth;
        grid = new NodeGrid(new Translation2d(0, 0), new Translation2d(10, 10), this);
    }

    public PathfinderResult FindPath(Translation2d currentPos, Translation2d goalPos) {
        Node start = grid.FindCloseNode(currentPos);
        Node end = grid.FindCloseNode(goalPos);

        boolean pathFound = false;
        ArrayList<Node> path = new ArrayList<Node>();
        ArrayList<Translation2d> simplifiedPath = new ArrayList<Translation2d>();

        ArrayList<Node> openSet = new ArrayList<Node>();
        HashSet<Node> closedSet = new HashSet<Node>();

        openSet.add(start);
        while (openSet.size() > 0)
        {
            Node currentNode = openSet.get(0);
            for (int i = 1; i < openSet.size(); i++)
            {
                if (openSet.get(i).fCost() < currentNode.fCost() || openSet.get(i).fCost() == currentNode.fCost() && openSet.get(i).hCost < currentNode.hCost)
                {
                    currentNode = openSet.get(i);
                }
            }

            openSet.remove(currentNode);
            closedSet.add(currentNode);

            if (currentNode == end)
            {
                path = RetracePath(start, end);

                simplifiedPath = SimplifyPath(path);

                simplifiedPath.set(0, currentPos);
                simplifiedPath.set(simplifiedPath.size()-1, goalPos);

                pathFound = true;
            }

            for (Node neighbor : grid.GetNeighbors(currentNode))
            {
                if (!neighbor.driveable || closedSet.contains(neighbor)) continue;
                double newMovementCostToNeighbor = currentNode.gCost + GetDistance(currentNode, neighbor);
                newMovementCostToNeighbor += neighbor.distanceWeight*10;
                if (newMovementCostToNeighbor < neighbor.gCost || !openSet.contains(neighbor))
                {
                    neighbor.gCost = newMovementCostToNeighbor;
                    neighbor.hCost = GetDistance(neighbor, end);
                    neighbor.parent = currentNode;
                    if (!openSet.contains(neighbor)) openSet.add(neighbor);
                }
            }
        }
        return new PathfinderResult(pathFound, simplifiedPath, path);
    }
    
    /** @return the path traced back from the end node */
    static ArrayList<Node> RetracePath(Node start, Node end)
    {
        ArrayList<Node> path = new ArrayList<Node>();
        Node currentNode = end;

        while (currentNode != start)
        {
            path.add(currentNode);
            currentNode = currentNode.parent;
        }
        path.add(start);
    
        Collections.reverse(path);
        return path;
    }

    /** @return a simplified version of the path that only contains turns */
    static ArrayList<Translation2d> SimplifyPath(ArrayList<Node> path)
    {
        ArrayList<Translation2d> waypoints = new ArrayList<Translation2d>();
        Translation2d oldDirection = new Translation2d();
        
        waypoints.add(path.get(0).worldPos);

        for (int i = 1; i < path.size()-1; i++)
        {

            Translation2d newDirection = path.get(i).worldPos.minus(path.get(i+1).worldPos);
            if (!newDirection.equals(oldDirection))
            {
                waypoints.add(path.get(i).worldPos);
                oldDirection = newDirection;
            }
        }
        waypoints.remove(path.get(1).worldPos);
        waypoints.add(path.get(path.size()-1).worldPos);

        return waypoints;
    }

    /** @return the smallest number of steps between nodeA and nodeB ignoring obstacles */
    static double GetDistance(Node nodeA, Node nodeB)
    {
        double distX = Math.abs(nodeA.gridX - nodeB.gridX);
        double distY = Math.abs(nodeA.gridY - nodeB.gridY);

        if (distX > distY)
            return Math.sqrt(2d) * distY + (distX - distY);
        else
            return Math.sqrt(2d) * distX + (distY - distX);
    }
}