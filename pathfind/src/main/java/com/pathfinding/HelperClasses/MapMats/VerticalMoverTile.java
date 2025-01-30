package com.pathfinding.HelperClasses.MapMats;

import java.util.List;

public class VerticalMoverTile extends MapTile {
    private List<VerticalMoverTile> connectedTiles;
    private int orientation;

    public VerticalMoverTile(List<VerticalMoverTile> list, int orientation){
        super(false, true, "verticalmover");
        connectedTiles = list;
        this.orientation = orientation; //1: North, 2: East, 3: South, 4: West
    }

    @Override
    public List<VerticalMoverTile> getConnected(){
        return connectedTiles;
    }
    @Override
    public void setConnected(List<VerticalMoverTile> connected){
        connectedTiles = connected;
    }
    
    @Override
    public int getOrientation(){
        return orientation;
    }
}
