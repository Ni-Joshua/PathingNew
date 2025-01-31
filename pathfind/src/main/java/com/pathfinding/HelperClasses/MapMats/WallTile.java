package com.pathfinding.HelperClasses.MapMats;

/**
 * Wall tiles are walls in the mall that cannot be passed
 * 
 * @author Joshua Ni
 * @author Justin Ely
 */
public class WallTile extends MapTile {
    /**
     * Initializes a new maptile that is impassable and not selectable
     */
    public WallTile() {
        super(false, false, "wall");
    }
}
