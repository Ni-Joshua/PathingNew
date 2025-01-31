package com.pathfinding.HelperClasses.MapMats;

import java.util.List;

public class Location {
    private String name;
    private Time openTime;
    private Time closeTime;

    public Location(){
        name = null;
        openTime = null;
        closeTime = null;
    }
    public Location(String name, Time openTime, Time closeTime){
        this.name = name;
        this.openTime = openTime;
        this.closeTime = closeTime;
    }

    public String getName(){
        return name;
    }

    public Time getOpenTime(){
        return openTime;
    }
    public Time getCloseTime(){
        return closeTime;
    }

    @Override
    public String toString(){
        return name + ", Opens: " + openTime + ", Closes: " + closeTime;
    }
}
