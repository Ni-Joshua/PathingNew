package com.pathfinding.HelperClasses.MapMats;

import java.util.List;

/**
 * Map Tiles are tiles on a mall map, which can by locations, entrances, etc.
 * 
 * @author Joshua Ni
 * @author Justin Ely
 */
public class MapTile {
    /** Can you walk through the tile? */
    private boolean passable;
    /** Can you pathfind to the location? */
    private boolean selectable;
    /** Name of the type of the tile */
    private String tileType;

    /**
     * Initializes new empty map tile
     */
    public MapTile() {
        passable = true;
        selectable = true;
        tileType = "blank";
    }

    /**
     * Initializes new tile with certain properties
     * 
     * @param passable
     * @param selectable
     * @param tileType
     */
    public MapTile(boolean passable, boolean selectable, String tileType) {
        this.passable = passable;
        this.selectable = selectable;
        this.tileType = tileType;
    }

    /**
     * Returns whether a tile is passable
     * 
     * @return whether tile is passable
     */
    public boolean isPassable() {
        return passable;
    }

    /**
     * Returns whether a tile is selectable
     * 
     * @return whether tile is selectable
     */
    public boolean isSelectable() {
        return selectable;
    }

    /**
     * Returns tile type
     * 
     * @return tyle of tile
     */
    public String getTileType() {
        return tileType;
    }

    /**
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     * 
     * ALL OF THE METHODS BELOW ARE TO BE IMPLEMENTED IN CHILD CLASSES
     * THE METHODS MAKE POLYMORPHISM POSSIBLE
     * 
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     */

    public Location getLocation() {
        return null;
    }

    public List<VerticalMoverTile> getConnected() {
        return null;
    }

    public void setConnected(List<VerticalMoverTile> connected) {
    }
}
