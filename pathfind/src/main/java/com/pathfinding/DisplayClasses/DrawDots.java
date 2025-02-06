package com.pathfinding.DisplayClasses;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class DrawDots extends JPanel {
    private int x1;
    private int x2;
    private int y1;
    private int y2;
    private int cellSize;

    public DrawDots(int cellSize){
        this.cellSize = cellSize;
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        
        Graphics2D g2d = (Graphics2D) g;


        if (x1 != 0 && y1 != 0){
            g2d.setPaint(new Color(0, 255, 0));
            g2d.drawOval(x1, y1, cellSize, cellSize);
        } 

        if (x2 != 0 && y2 != 0){
            g2d.setPaint(new Color(0, 255, 0));
            g2d.drawOval(x2, y2, cellSize, cellSize);
        } 
    }

    public void setStartCoords(int x, int y){
        x1 = x;
        y1 = y;
    }

    public void setEndCoords(int x, int y){
        x2 = x;
        y2 = y;
    }
}
