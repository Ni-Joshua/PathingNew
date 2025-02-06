package com.pathfinding.DisplayClasses;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GradientPaint;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;

import javax.swing.JPanel;

import com.pathfinding.MapClasses.MapMats.MapTile;
import com.pathfinding.Pathfinder.Node;

/**
 * Class that handles displaying the map of a given floor
 * 
 * @author Joshua Ni
 * @author Justin Ely
 */
public class MapDisplay extends JPanel{
    //Map being displayed
    private MapTile[][] grid;
    private int zValue;
    //Path being plotted if necessary
    private List<Node> path;
    private int xSize;
    private int ySize;
    //TreeMap from tileType/name to hexadecimal color value
    private TreeMap<String, String> colorMapping;
    private int cellSize;
    private int[] startCoords;
    private int[] endCoords;
    
    /**
     * Constructs a display depending on given values
     * @param grid map being displayed
     * @param path path between start and end locations
     * @param xSize size in pixels of X direction
     * @param ySize size in pixels of Y direction
     * @param colorMapping map between tileType/Name to hexadecimal color value
     * @param zValue floor
     */
    public MapDisplay(MapTile[][] grid, List<Node> path,int xSize, int ySize, TreeMap<String, String> colorMapping, int zValue){
        this.grid = grid;
        this.path = path;
        this.xSize = xSize;
        this.ySize = ySize;
        this.colorMapping = colorMapping;
        this.zValue = zValue;
        super.setPreferredSize(new Dimension(xSize, ySize));
    }

    /**
     * Method that paints the map
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        cellSize = Math.min(xSize/grid[0].length, ySize/grid.length);
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[y].length; x++) {
                MapTile tile = grid[y][x];
                String tileType = tile.getTileType();
                String hex = "";
                //Chooses color depending on tile information
                switch (tileType) {
                    case "location":
                    case "entrance":
                        hex = colorMapping.get(tile.getLocation().getName());
                        break;
                    case "verticalmover":
                        hex = "FFC0CB";
                        break;
                    default:
                        hex = colorMapping.get(tileType);
                        break;
                }

                int red = Integer.parseInt(hex.substring(0, 2), 16);
                int green = Integer.parseInt(hex.substring(2, 4), 16);
                int blue = Integer.parseInt(hex.substring(4, 6), 16);

                Color color = new Color(red, green, blue);
                g2d.setColor(color);
                g2d.fillRect(x * cellSize, y * cellSize, cellSize, cellSize);
            }
        }
    
        //Draws the path that exists on the floor
        if (path != null) {
            GradientPaint gradient;
            int colorStep = 255/path.size();
            g2d.setColor(new Color(0, 0, 255));
            int pixWidth = (int) Math.round(cellSize/2.0);
            g2d.setStroke(new BasicStroke(pixWidth));
            for (int i = 0; i< path.size()-1; i++) {
                if (path.get(i).getZ() == zValue && path.get(i+1).getZ() == zValue){
                    // if (i == 0){
                    //     g2d.setPaint(new Color(0, 255, 0));
                    //     g2d.drawOval(path.get(i).getX()*cellSize - cellSize/2, path.get(i).getY()*cellSize - cellSize/2, cellSize, cellSize);
                    // }
                    // if (i == path.size()-2){
                    //     g2d.setPaint(new Color(255, 0, 0));
                    //     g2d.drawOval(path.get(i+1).getX()*cellSize - cellSize/2, path.get(i+1).getY()*cellSize - cellSize/2, cellSize, cellSize);
                    // }
                    gradient = new GradientPaint(path.get(i).getX(), path.get(i).getY(), new Color(0, i*colorStep, 255), path.get(i+1).getX(), path.get(i+1).getY(), new Color(0, (i+1)*colorStep, 255));
                    g2d.setPaint(gradient);
                    // g2d.drawLine(path.get(i).getX()*cellSize, path.get(i).getY()*cellSize, path.get(i+1).getX()*cellSize, path.get(i+1).getY()*cellSize);
                    g2d.drawLine(path.get(i).getX()*cellSize + pixWidth, path.get(i).getY()*cellSize + pixWidth, path.get(i+1).getX()*cellSize + pixWidth, path.get(i+1).getY()*cellSize + pixWidth);
                }   
            }
        }

        g2d.setStroke(new BasicStroke(cellSize/2));
        if (startCoords != null){
            g2d.setPaint(new Color(0, 255, 0));
            if (startCoords[0] == zValue){
                g2d.drawOval(startCoords[2]*cellSize, startCoords[1]*cellSize, cellSize, cellSize);
            }
        }

        if (endCoords != null){
            g2d.setPaint(new Color(255, 0, 0));
            if (endCoords[0] == zValue){
                g2d.drawOval(endCoords[2]*cellSize, endCoords[1]*cellSize, cellSize, cellSize);
            }
        }
    }

    /**
     * Returns the zValue
     * @return zValue
     */
    public int getZValue(){
        return zValue;
    }

    /**
     * Returns the scaling of the map
     * @return cellSize, how many pixels each cell represents
     */
    public int getScale(){
        return cellSize;
    }

    /**
     * Setting the path for a given map display
     * @param path the new full path
     */
    public void setPath(List<Node> path){
        this.path = path;
    }

    public void setStartCoords(int[] startCoords){
        this.startCoords = startCoords;
    }

    public void setEndCoords(int[] endCoords){
        this.endCoords = endCoords;
    }

}
