package com.pathfinding.MapClasses.MapMats;

/**
 * LocationTiles represent certain named locations on the general map
 * 
 * @author Joshua Ni
 * @author Justin Ely
 */
public class LocationTile extends MapTile {
    /** Location that the location tile refers to */
    private Location location;

    /**
     * Instantiates new location tile with a given location and type of tile
     * 
     * @param type type of location
     * @param loc  location that the locationtile refers to
     */
    public LocationTile(String type, Location loc) {
        super(true, true, type);
        location = loc;
    }

    /**
     * Instantiates new location tile with a given locaation
     * 
     * @param loc
     */
    public LocationTile(Location loc) {
        super(true, true, "location");
        location = loc;
    }

    /**
     * returns location that a locationtile points to
     * 
     * @return location
     */
    @Override
    public Location getLocation() {
        return location;
    }
}
