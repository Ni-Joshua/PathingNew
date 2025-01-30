package com.pathfinding;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.List;

import javax.swing.JPanel;

import com.pathfinding.HelperClasses.MapMats.MapTile;

public class MapDisplay extends JPanel{
        // private static int[][][] grid;
    private MapTile[][] grid;
    private List<Node> path;
    private int xSize;
    private int ySize;
    private int layer;
    
    public MapDisplay(MapTile[][] grid, List<Node> path, int layer, int xSize, int ySize){
        this.grid = grid;
        this.path = path;
        this.layer = layer;
        this.xSize = xSize;
        this.ySize = ySize;
        super.setPreferredSize(new Dimension(xSize, ySize));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        // int cellSize = 50;
        int cellSize = Math.min(xSize/grid[0].length, ySize/grid.length);
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[y].length; x++) {
                if (grid[y][x].getTileType().equals("wall")) {
                    g2d.setColor(Color.BLACK);
                } else if (grid[y][x].getTileType().equals("verticalmover")){
                    g2d.setColor(Color.pink);
                } else if (grid[y][x].getTileType().equals("location")){
                    g2d.setColor(Color.red);
                } else if (grid[y][x].getTileType().equals("entrance")){
                    g2d.setColor(Color.green);
                }else {
                    g2d.setColor(Color.WHITE);
                }
                System.out.println(x*cellSize + " " + y*cellSize);
                g2d.fillRect(x * cellSize, y * cellSize, cellSize, cellSize);
                g2d.setColor(Color.GRAY);
                g2d.drawRect(x* cellSize, y * cellSize, cellSize, cellSize);
            }
        }
    
        if (path != null) {
            // int colorStep = 
            g2d.setColor(Color.BLUE);
            g2d.setStroke(new BasicStroke(2.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL));
            for (int i = 0; i< path.size()-1; i++) {
                if (path.get(i).z == layer){
                    g2d.drawLine(path.get(i).x*cellSize + cellSize/2, path.get(i).y*cellSize + cellSize/2, path.get(i+1).x*cellSize + cellSize/2, path.get(i+1).y*cellSize + cellSize/2);
                    // g.fillRect(node.x * cellSize, node.y * cellSize, cellSize, cellSize);
                }   
            }
        }
    }
}
