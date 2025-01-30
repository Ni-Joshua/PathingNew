package com.pathfinding.HelperClasses.MapMats;

import java.util.List;

public class Location {
    private String name;
    private Time openTime;
    private Time closeTime;

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
}
