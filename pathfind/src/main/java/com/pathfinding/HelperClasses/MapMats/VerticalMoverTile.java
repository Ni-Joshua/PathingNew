package com.pathfinding.HelperClasses.MapMats;

import java.util.List;

/**
 * Vertical mover tiles are tiles that can move between layers, like elevators,
 * escalators, etc.
 * 
 * @author Joshua Ni
 * @author Justin Ely
 */
public class VerticalMoverTile extends MapTile {
    /** List of vertical movers that a vertical mover can access */
    private List<VerticalMoverTile> connectedTiles;

    /**
     * Initializes a new vertical mover tile with a list of connected tiles
     */
    public VerticalMoverTile(List<VerticalMoverTile> list) {
        super(false, true, "verticalmover");
        connectedTiles = list;
    }

    /**
     * Returns the tiles connected to a given vertical mover
     * 
     * @return connected tiles
     */
    @Override
    public List<VerticalMoverTile> getConnected() {
        return connectedTiles;
    }

    /**
     * Sets the connected tiels of the vertical mover
     * 
     * @param connected tiles to the vertical mover
     */
    @Override
    public void setConnected(List<VerticalMoverTile> connected) {
        connectedTiles = connected;
    }
}
