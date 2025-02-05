package com.pathfinding.MapClasses.MapMats;

/**
 * Entrances are entrances tiles to certain locations or the mall
 * 
 * @author Joshua Ni
 * @author Justin Ely
 */
public class Entrance extends LocationTile {
    /** Initializes new entrance with a location */
    public Entrance(Location loc) {
        super("entrance", loc);
    }
}
