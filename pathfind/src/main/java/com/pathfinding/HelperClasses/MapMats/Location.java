package com.pathfinding.HelperClasses.MapMats;

/**
 * Locations are places in the mall with names, such as stores, restrooms, etc.
 * 
 * @author Joshua Ni
 * @author Justin Ely
 */
public class Location {
    /** Name of the location */
    private String name;
    /** Time the location opens */
    private Time openTime;
    /** Time the location closes */
    private Time closeTime;

    /**
     * Initializes new location with certain parameters
     * 
     * @param name      name of location
     * @param openTime  opening time
     * @param closeTime closing time
     */
    public Location(String name, Time openTime, Time closeTime) {
        this.name = name;
        this.openTime = openTime;
        this.closeTime = closeTime;
    }

    /**
     * Rturns name of location
     * 
     * @return name of location
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the opening time of the location
     * 
     * @return opening time of the location
     */
    public Time getOpenTime() {
        return openTime;
    }

    /**
     * Returns closing time of the location
     * 
     * @return closing time of the location
     */
    public Time getCloseTime() {
        return closeTime;
    }
}
