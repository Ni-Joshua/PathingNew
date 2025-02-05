package com.pathfinding.DisplayClasses;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.Arrays;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import com.pathfinding.MapClasses.MapMats.MapTile;

/**
 * JPanel Class that displays the information about the starting and ending coords
 * 
 * @author Joshua Ni
 * @author Justin Ely
 */
public class InfoPanel extends JPanel{
    JLabel startCoordInfo;
    JLabel startTileInfo;
    JLabel endCoordInfo;
    JLabel endTileInfo;
    
    /**
     * Constructs a panel providing information about the start and end coords selected by the user
     * @param startCoords start coordinates selected
     * @param endCoords end coordinates located
     */
    public InfoPanel(int[] startCoords, int[] endCoords){
        this.setLayout(new GridLayout(0,1));
        this.setBorder(new LineBorder(Color.black, 1));
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

    /**
     * Updates labels providing information about the start coordinates
     * @param startCoords selected starting coordinates
     * @param tile MapTile at the starting coordinates
     */
    public void setStartInfo(int[] startCoords, MapTile tile){
        startCoordInfo.setText("Start Coords: " + Arrays.toString(startCoords));
        // Updates text depending on type of MapTile
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

    /**
     * Updates labels providing information about the end coordinates
     * @param endCoords selected end coordinates
     * @param tile MapTile at the end coordinates
     */
    public void setEndInfo(int[] endCoords, MapTile tile){
        endCoordInfo.setText("End Coords: " + Arrays.toString(endCoords));
        switch(tile.getTileType()){
            //Updates text depending on type of MapTile
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
