package com.pathfinding;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.AccessFlag;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.imageio.ImageIO;
import javax.lang.model.util.ElementScanner14;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.pathfinding.HelperClasses.GeneralMap;
import com.pathfinding.HelperClasses.MapMats.Entrance;
import com.pathfinding.HelperClasses.MapMats.Location;
import com.pathfinding.HelperClasses.MapMats.LocationTile;
import com.pathfinding.HelperClasses.MapMats.MapTile;
import com.pathfinding.HelperClasses.MapMats.Time;
import com.pathfinding.HelperClasses.MapMats.VerticalMoverTile;
import com.pathfinding.HelperClasses.MapMats.WallTile;

public class MapReader {
    private TreeMap<String, Location> locMapping;
    private TreeMap<String, VerticalMoverTile> vmMapping;

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

        JsonNode colorMap = info.get("ColorMapping");

        for (int z = 0; z < depth; z++) {
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    BufferedImage currentFloor = ImageIO.read(floorPlans[z]);
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
                    // System.out.println(z + " " + y + " " + x + ": " + tileType);
                }
            }
        }
        return gMap;
    }

    public TreeMap<String, VerticalMoverTile> getVMMapping() {
        return vmMapping;
    }

    public TreeMap<String, Location> getLocMapping() {
        return locMapping;
    }

    private void locCreation(JsonNode locations) {
        locMapping = new TreeMap<>();
        for (Iterator<String> it = locations.fieldNames(); it.hasNext();) {
            String locID = it.next();
            JsonNode loc = locations.get(locID);
            Location location = new Location(loc.get("name").asText(), new Time(loc.get("openTime").asText()),
                    new Time(loc.get("closeTime").asText()));
            locMapping.put(locID, location);
        }
    }

    private void vmCreation(JsonNode vMovers) throws IOException {
        vmMapping = new TreeMap<>();
        ObjectMapper mapper = new ObjectMapper();
        ObjectReader reader = mapper.readerFor(new TypeReference<List<String>>() {
        });
        for (Iterator<String> it = vMovers.fieldNames(); it.hasNext();) {
            String vmID = it.next();
            vmMapping.put(vmID, new VerticalMoverTile(null));
        }

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
