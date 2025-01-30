package com.pathfinding.HelperClasses.MapMats;

public class LocationTile extends MapTile{
    private Location location;

    public LocationTile(String type, Location loc){
        super(true, true, type);
        location = loc;
    }

    
    public LocationTile(Location loc){
        super(true, true, "location");
        location = loc;
    }

    @Override
    public Location getLocation(){
        return location;
    }
}
