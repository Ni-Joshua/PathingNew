package com.pathfinding.HelperClasses.MapMats;

import java.util.List;

public class MapTile {
    private boolean passable;
    private boolean selectable;
    private String tileType;

    public MapTile(){
        passable = true;
        selectable = true;
        tileType = "blank";
    }
    public MapTile(boolean pass, boolean selec, String type){
        passable = pass;
        selectable = selec;
        tileType = type;
    }

    public boolean isPassable(){
        return passable;
    }

    public boolean isSelectable(){
        return selectable;
    }

    public String getTileType(){
        return tileType;
    }

    //temporary fix
    public Location getLocation(){
        return null;
    }

    public List<VerticalMoverTile> getConnected(){
        return null;
    }

    public int getOrientation(){
        return 0;
    }

    public void setConnected(List<VerticalMoverTile> connected){
    }
}
