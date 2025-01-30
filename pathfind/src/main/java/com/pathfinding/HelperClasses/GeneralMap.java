package com.pathfinding.HelperClasses;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.pathfinding.HelperClasses.MapMats.Location;
import com.pathfinding.HelperClasses.MapMats.MapTile;
import com.pathfinding.HelperClasses.MapMats.VerticalMoverTile;

//Incomplete
public class GeneralMap {
    private MapTile[][][] map;
    private Map<String, List<int[]>> nameToEntrances;
    private Map<Location, String> locToName;
    private Map<MapTile, int[]> moverCoords;

    public GeneralMap(MapTile[][][] gMap){
        map = gMap;
        locToName = new HashMap<>();
        moverCoords = new HashMap<>();
        constructCoords();
    }

    private void constructCoords(){
        nameToEntrances = new HashMap<>();
        locToName = new HashMap<>();
        for (int z = 0; z < map.length; z++){
            for (int y = 0; y < map[z].length; y++){
                for (int x = 0; x < map[z][y].length; x++){
                    if (map[z][y][x].getTileType().equals("entrance")){
                        Location loc = map[z][y][x].getLocation();
                        String name = locToName.get(loc);
                        if (name == null){
                            name = loc.getName();
                            locToName.put(loc,name);
                        }
                        List<int[]> currentCoords = nameToEntrances.get(name);
                        if (currentCoords == null){
                            currentCoords = new LinkedList<>();
                        }
                        int[] coords = {z,y,x};
                        currentCoords.add(coords);
                        nameToEntrances.put(name, currentCoords);
                    }
                    else if(map[z][y][x].getTileType().equals("verticalmover")){
                        // int orient = map[z][y][x].getOrientation();
                        int[] coords = {z,y,x};
                        // switch(orient){
                        //     case 1:
                        //         coords[1]--;
                        //         break;
                        //     case 2:
                        //         coords[2]++;
                        //         break;
                        //     case 3:
                        //         coords[1]++;
                        //     case 4:
                        //         coords[2]--;
                        // }
                        moverCoords.put(map[z][y][x], coords);
                    }
                }
            }
        }
    }

    public MapTile getTile(int x, int y, int z){
        return map[x][y][z];
    }

    public MapTile[][][] getMap(){
        return map;
    }

    public List<int[]> getCoords(Location l1){
        return nameToEntrances.get(locToName.get(l1));
    }

    public List<int[]> getCoords(String name){
        return nameToEntrances.get(name);
    }

    public int[] getMoverCoords(VerticalMoverTile tile){
        return moverCoords.get(tile);
    }
}
