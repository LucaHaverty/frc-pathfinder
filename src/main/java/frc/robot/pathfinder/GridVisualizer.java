package frc.robot.pathfinder;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class GridVisualizer {
    /** Generates an image containing nodes from the grid
     * 
     * @param grid the grid to use
     * @return a BufferedImage containing pixels that represent specific nodes
     */
    private static BufferedImage generateGridVisual(NodeGrid grid) {
        BufferedImage image = new BufferedImage(grid.getWidth(), grid.getHeight(), BufferedImage.TYPE_INT_RGB);

        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                if (grid.getNodeAt(x, y).distanceToNearestObstacle > 0) {
                    image.setRGB(x, y, getIntFromColor(1, 0, 0));
                }
                else image.setRGB(x, y, getIntFromColor(1, 1, 1));
            }
        }
        return image;
    }

    /** Save a png image containing pixels that represent specific nodes to the output location
     * 
     * @param grid the grid to visualize
     * @param outputLocation the location to save to (include fileName.png)
     */
    public static void visualizeGridAsPNG(NodeGrid grid, String outputLocation) {
        BufferedImage image = generateGridVisual(grid);
        
        File outputfile = new File(outputLocation);
        try {
            ImageIO.write(image, "PNG", outputfile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Converts RGB values to an integer representing a color
     * 
     * @param Red the red component of the color (0-1)
     * @param Green the green component of the color (0-1)
     * @param Blue the blue component of the color (0-1)
     * @return an integer representing a color
    */
    public static int getIntFromColor(float Red, float Green, float Blue){
        int R = Math.round(255 * Red);
        int G = Math.round(255 * Green);
        int B = Math.round(255 * Blue);
    
        R = (R << 16) & 0x00FF0000;
        G = (G << 8) & 0x0000FF00;
        B = B & 0x000000FF;
    
        return 0xFF000000 | R | G | B;
    }
}
