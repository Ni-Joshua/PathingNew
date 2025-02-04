package com.pathfinding;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.Arrays;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import com.pathfinding.HelperClasses.MapMats.MapTile;

public class InfoPanel extends JPanel{
    JLabel startCoordInfo;
    JLabel startTileInfo;
    JLabel endCoordInfo;
    JLabel endTileInfo;

    
    public InfoPanel(int[] startCoords, int[] endCoords){
        this.setLayout(new GridLayout(0,1));
        this.setBorder(new LineBorder(Color.black, 1));
        // temp.setLayout(new BorderLayout());
        this.add(new JLabel("Path Information: "));
        startCoordInfo = new JLabel("Start Coords: " + Arrays.toString(startCoords));
        startTileInfo = new JLabel("");
        endCoordInfo = new JLabel("End Coords: "+ Arrays.toString(endCoords));
        endTileInfo = new JLabel("");
        this.add(startCoordInfo);
        this.add(startTileInfo);
        this.add(endCoordInfo);
        this.add(endTileInfo);
    }

    public void setStartInfo(int[] startCoords, MapTile tile){
        startCoordInfo.setText("Start Coords: " + Arrays.toString(startCoords));
        switch(tile.getTileType()){
            case "location":
            case "entrance":
                startTileInfo.setText("Name: " + tile.getLocation().getName() + ", Opening Time: " + tile.getLocation().getOpenTime() + ", Closing Time: "+ tile.getLocation().getCloseTime());
                break;
            case "verticalmover":
                startTileInfo.setText("Vertical Mover");
                break;
            default:
                startTileInfo.setText("");
        }
    }

    public void setEndInfo(int[] endCoords, MapTile tile){
        endCoordInfo.setText("End Coords: " + Arrays.toString(endCoords));
        switch(tile.getTileType()){
            case "location":
            case "entrance":
                endTileInfo.setText("Name: " + tile.getLocation().getName() + ", Opening Time: " + tile.getLocation().getOpenTime() + ", Closing Time: "+ tile.getLocation().getCloseTime());
                break;
            case "verticalmover":
                endTileInfo.setText("Vertical Mover");
                break;
            default:
                endTileInfo.setText("");
        }
    }
}
