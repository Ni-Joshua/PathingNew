package com.pathfinding;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GradientPaint;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.List;
import java.util.TreeMap;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.pathfinding.HelperClasses.MapMats.MapTile;

public class MapDisplay extends JPanel{
        // private static int[][][] grid;
    private MapTile[][] grid;
    private int zValue;
    private List<Node> path;
    private int xSize;
    private int ySize;
    private TreeMap<String, String> colorMapping;
    private int cellSize;
    
    public MapDisplay(MapTile[][] grid, List<Node> path,int xSize, int ySize, TreeMap<String, String> colorMapping, int zValue){
        this.grid = grid;
        this.path = path;
        this.xSize = xSize;
        this.ySize = ySize;
        this.colorMapping = colorMapping;
        this.zValue = zValue;
        super.setPreferredSize(new Dimension(xSize, ySize));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // int cellSize = 50;
        cellSize = Math.min(xSize/grid[0].length, ySize/grid.length);
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[y].length; x++) {
                MapTile tile = grid[y][x];
                String tileType = tile.getTileType();
                String hex = "";
                if (tileType.equals("location") || tileType.equals("entrance")){
                    hex = colorMapping.get(tile.getLocation().getName());
                }
                else if (tileType.equals("verticalmover")){
                    hex = "FFC0CB";
                }
                else {
                    hex = colorMapping.get(tileType);
                }

                int red = Integer.parseInt(hex.substring(0, 2), 16);
                int green = Integer.parseInt(hex.substring(2, 4), 16);
                int blue = Integer.parseInt(hex.substring(4, 6), 16);

                Color color = new Color(red, green, blue);
                g2d.setColor(color);
                g2d.fillRect(x * cellSize, y * cellSize, cellSize, cellSize);

                // g2d.setColor(Color.GRAY);
                // g2d.drawRect(x* cellSize, y * cellSize, cellSize, cellSize);
            }
        }
    
        if (path != null) {
            GradientPaint gradient;
            int colorStep = 255/path.size();
            g2d.setColor(new Color(0, 0, 255));
            g2d.setStroke(new BasicStroke(cellSize/2));
            for (int i = 0; i< path.size()-1; i++) {
                if (path.get(i).z == zValue && path.get(i+1).z == zValue){
                    gradient = new GradientPaint(path.get(i).x, path.get(i).y, new Color(0, i*colorStep, 255), path.get(i+1).x, path.get(i+1).y, new Color(0, (i+1)*colorStep, 255));
                    g2d.setPaint(gradient);
                    g2d.drawLine(path.get(i).x*cellSize + cellSize/2, path.get(i).y*cellSize + cellSize/2, path.get(i+1).x*cellSize + cellSize/2, path.get(i+1).y*cellSize + cellSize/2);
                    // g.fillRect(node.x * cellSize, node.y * cellSize, cellSize, cellSize);
                }   
            }
        }
    }

    public int getZValue(){
        return zValue;
    }

    public int getScale(){
        return cellSize;
    }

    public void setPath(List<Node> path){
        this.path = path;
    }

}
