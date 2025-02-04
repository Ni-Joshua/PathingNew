package com.pathfinding.HelperClasses;

import java.awt.datatransfer.SystemFlavorMap;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.pathfinding.HelperClasses.MapMats.Location;
import com.pathfinding.HelperClasses.MapMats.MapTile;
import com.pathfinding.HelperClasses.MapMats.VerticalMoverTile;

/**
 * The general map is the floorplan of a mall
 * The general map stores a 3D array of map tiles
 * Coordinates are represented with 1D arrays, with coordinates:
 * int[] coordinates = {z, y, x};
 * 
 * @author Joshua Ni
 * @author Justin Ely
 */
public class GeneralMap {
    /** Array of tiles that reperesents a phyical map of the mall */
    private MapTile[][][] map;
    /** Map of names of locations to their entrances */
    private Map<String, List<int[]>> nameToEntrances;
    /** Map of locations to their respective names */
    private Map<Location, String> locToName;
    /** Map of vertical movers to their respective coordinates on each floor */
    private Map<MapTile, int[]> moverCoords;

    /**
     * Initializes a general map with an array of map tiles
     * 
     * @param gMap
     */
    public GeneralMap(MapTile[][][] gMap) {
        map = gMap;
        locToName = new HashMap<>();
        moverCoords = new HashMap<>();
        constructCoords();
    }

    /**
     * Constructs all of the maps in the instance variables
     */
    private void constructCoords() {
        nameToEntrances = new HashMap<>();
        moverCoords = new HashMap<>();
        for (int z = 0; z < map.length; z++) {
            for (int y = 0; y < map[z].length; y++) {
                for (int x = 0; x < map[z][y].length; x++) {
                    // System.out.println(z + " " + y + " "+ x);
                    // Checking if tile is entrance
                    if (map[z][y][x].getTileType().equals("entrance")) {
                        Location loc = map[z][y][x].getLocation();
                        String name = locToName.get(loc);
                        if (name == null) { // Adds appropriate items to locToName
                            name = loc.getName();
                            locToName.put(loc, name);
                        }
                        List<int[]> currentCoords = nameToEntrances.get(name);
                        if (currentCoords == null) {
                            currentCoords = new LinkedList<>();
                        }
                        int[] coords = { z, y, x };
                        currentCoords.add(coords);
                        nameToEntrances.put(name, currentCoords); // Adds appropriate items to nameToEntrances

                    }
                    // Checking if tile is vertical mover
                    else if (map[z][y][x].getTileType().equals("verticalmover")) {
                        int[] coords = { z, y, x };
                        moverCoords.put(map[z][y][x], coords); // Adds appropriate items to moverCoords
                    }
                }
            }
        }
    }

    /**
     * Returns the tile at a coordinate
     * 
     * @param x x coordinate
     * @param y y coordinate
     * @param z z coordinate
     * @return tile at the coordinate
     */
    public MapTile getTile(int z, int y, int x) {
        return map[z][y][x];
    }

    /**
     * Returns array representing mall map
     * 
     * @return the mall map
     */
    public MapTile[][][] getMap() {
        return map;
    }

    /**
     * Returns the coordinates of the entrances of a location
     * 
     * @param l1 location
     * @return coordiantes of the entrances
     */
    public List<int[]> getCoords(Location l1) {
        return nameToEntrances.get(locToName.get(l1));
    }

    /**
     * Returns coordinates of the entrances of a location by name
     * 
     * @param name of locaiton
     * @return coordinates of entrances
     */
    public List<int[]> getCoords(String name) {
        return nameToEntrances.get(name);
    }

    /**
     * Returns coordinates of the locaitons where a vertical mover can be accessed
     * 
     * @param tile vertical mover
     * @return coordinates of connected vertical mover tiles
     */
    public int[] getMoverCoords(VerticalMoverTile tile) {
        return moverCoords.get(tile);
    }
}
