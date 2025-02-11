package com.pathfinding.MapClasses;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.pathfinding.MapClasses.MapMats.Entrance;
import com.pathfinding.MapClasses.MapMats.Location;
import com.pathfinding.MapClasses.MapMats.LocationTile;
import com.pathfinding.MapClasses.MapMats.MapTile;
import com.pathfinding.MapClasses.MapMats.Time;
import com.pathfinding.MapClasses.MapMats.VerticalMoverTile;
import com.pathfinding.MapClasses.MapMats.WallTile;

/**
 * Class that handles constructing maps from a given folder
 * @author Joshua Ni
 * @author Justin Ely
 */
public class MapReader {
    //Maps that store key information from the Json File
    private Map<String, Location> locMapping;
    private Map<String, VerticalMoverTile> vmMapping;
    private Map<String, String> colorMapping;

    /**
     * Constructs 3D array of MapTiles from a given folder
     * @param folderPath
     * @return 3D array of MapTiles representing the multi-level building
     * @throws JsonProcessingException
     * @throws IOException
     */
    public MapTile[][][] readImageMap(String folderPath) throws JsonProcessingException, IOException {
        File mall = new File(folderPath + "\\Floors");
        File[] floorPlans = mall.listFiles();
        int depth = floorPlans.length;
        BufferedImage image = ImageIO.read(floorPlans[0]);
        int width = image.getWidth();
        int height = image.getHeight();

        MapTile[][][] gMap = new MapTile[depth][height][width];
        File infoMapping = new File(folderPath + "\\floorInfo.json");
        ObjectMapper mapper = new ObjectMapper();
        JsonNode info = mapper.readTree(infoMapping);
        JsonNode locations = info.get("LocMapping");
        JsonNode vMovers = info.get("VMmapping");

        locCreation(locations);
        vmCreation(vMovers);
        colorMapping = new HashMap<>();

        JsonNode colorMap = info.get("ColorMapping");

        //Mapping each pixel's color to an object
        for (int z = 0; z < depth; z++) {
            BufferedImage currentFloor = ImageIO.read(floorPlans[z]);
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int rgb = currentFloor.getRGB(x, y);
                    String hex = Integer.toHexString(rgb & 0xffffff);
                    while (hex.length() < 6) {
                        hex = "0" + hex;
                    }
                    hex = hex.toUpperCase();
                    String tileType = colorMap.get(hex).textValue();
                    if (tileType.equals("wall")) {
                        gMap[z][y][x] = new WallTile();
                    } else if (tileType.equals("blank")) {
                        gMap[z][y][x] = new MapTile();
                    } else if (tileType.contains(" ") && tileType.substring(0, tileType.indexOf(" ")).equals("ent")) {
                        gMap[z][y][x] = new Entrance(locMapping.get(tileType.substring(tileType.indexOf(" ") + 1)));
                    } else if (tileType.contains(" ") && tileType.substring(0, tileType.indexOf(" ")).equals("vm")) {
                        gMap[z][y][x] = vmMapping.get(tileType.substring(tileType.indexOf(" ") + 1));
                    } else {
                        gMap[z][y][x] = new LocationTile(locMapping.get(tileType));
                    }
                    
                    //Storing String ID to Hexadecimal color code information in colorMapping, entrances will be colored the same as location tiles
                    // if (!tileType.contains(" ") && !colorMapping.containsKey(tileType)){
                    //     String key = "";
                    //     if (tileType.equals("wall")){
                    //         key = "wall";
                    //     }
                    //     else if (tileType.equals("blank")){
                    //         key = "blank";
                    //     }
                    //     else{
                    //         key = locMapping.get(tileType).getName();
                    //         // System.out.println(key);
                    //     }
                    //     colorMapping.put(key, hex);
                    // }
                    if (!colorMapping.containsKey(tileType)){
                            String key = "";
                            if (tileType.equals("wall")){
                                key = "wall";
                            }
                            else if (tileType.equals("blank")){
                                key = "blank";
                            }
                            else{
                                if (tileType.contains(" ")){
                                        colorMapping.put(tileType, hex);
                                    }
                                else{
                                    key = locMapping.get(tileType).getName();
                                }
                            }
                            colorMapping.put(key, hex);
                        }
                    /*Check for entrances that do not have location tiles associated with them (usually entrance of building) */
                    // if (tileType.contains(" ") && tileType.substring(0, tileType.indexOf(" ")).equals("ent") && !colorMapping.containsKey(tileType.substring(tileType.indexOf(" ") + 1))){
                    //     colorMapping.put(tileType.substring(tileType.indexOf(" ") + 1), hex);
                    //     System.out.println(colorMapping.get(tileType.substring(tileType.indexOf(" ") + 1)));
                    // }
                }
            }
        }
        return gMap;
    }

    /**
     * Returns map between VerticalMover String IDs and their object form
     * @return vmMapping
     */
    public Map<String, VerticalMoverTile> getVMMapping() {
        return vmMapping;
    }

    /**
     * Returns map between Location String IDs and their object form
     * @return locMapping
     */
    public Map<String, Location> getLocMapping() {
        return locMapping;
    }

    /**
     * Returns map between String IDs to Hexadecimal color
     * @return
     */
    public Map<String, String> getColorMapping() {
        return colorMapping;
    }

    /**
     * Creates locMapping array based on information read from the json file
     * @param locations JsonNode of Location information
     */
    private void locCreation(JsonNode locations) {
        locMapping = new HashMap<>();
        for (Iterator<String> it = locations.fieldNames(); it.hasNext();) {
            String locID = it.next();
            JsonNode loc = locations.get(locID);
            Location location = new Location(loc.get("name").asText(), new Time(loc.get("openTime").asText()),
                    new Time(loc.get("closeTime").asText()));
            locMapping.put(locID, location);
        }
    }

    /**
     * Creates vmMapping based on information read from the json file
     * @param vMovers JsonNode of VerticalMoverTile information
     * @throws IOException
     */
    private void vmCreation(JsonNode vMovers) throws IOException {
        vmMapping = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        ObjectReader reader = mapper.readerFor(new TypeReference<List<String>>() {});
        //creates new VerticalMoverTiles to make the objects exist
        for (Iterator<String> it = vMovers.fieldNames(); it.hasNext();) {
            String vmID = it.next();
            vmMapping.put(vmID, new VerticalMoverTile(null));
        }

        //Fills the existing objects with connectedTiles information
        for (Iterator<String> it = vMovers.fieldNames(); it.hasNext();) {
            String vmID = it.next();
            JsonNode vm = vMovers.get(vmID);
            ArrayList<String> connected = reader.readValue(vm.get("connectedTiles"));
            List<VerticalMoverTile> cTiles = new ArrayList<>();
            for (String cVMID : connected) {
                cTiles.add(vmMapping.get(cVMID));
            }
            vmMapping.get(vmID).setConnected(cTiles);
        }
    }
}
