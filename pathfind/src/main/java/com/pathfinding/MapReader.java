package com.pathfinding;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.AccessFlag;
import java.util.TreeMap;

import javax.imageio.ImageIO;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pathfinding.HelperClasses.MapMats.Location;
import com.pathfinding.HelperClasses.MapMats.MapTile;

public class MapReader {
    public MapTile[][][] readImageMap(String folderPath) throws JsonProcessingException, IOException{
        File mall = new File(folderPath + "//Floors"); 
        File[] floorPlans = mall.listFiles(); 
        int depth = floorPlans.length;
        BufferedImage image = ImageIO.read(floorPlans[0]);
        int width = image.getWidth();
        int height = image.getHeight();

        MapTile[][][] map = new MapTile[depth][height][width];
        File infoMapping = new File(folderPath + "//floorInfo.json");
        ObjectMapper mapper = new ObjectMapper();
        JsonNode info = mapper.readTree(infoMapping);
        JsonNode x = info.get("LocMapping");
        System.out.println(x.get("ah"));
        // Location loc1 = mapper.treeToValue(info.get("ah"), Location.class);
        TreeMap<String, Location> locMapping = new TreeMap<>();
        
        System.out.println(mapper.treeToValue(info, Location.class));
        // System.out.println(info.get("ColorMapping"));
        // for (int y = 0; y < heght; y++) {
        //     for (int x = 0; x < width; x++) {
        //         int rgb = image.getRGB(x, y);
                // String hex = Integer.toHexString(rgb.getRGB() & 0xffffff);
                // while(hex.length() < 6){
                //     hex = "0" + hex;
                // }
        //         if (color.equals(Color.WHITE)) {
        //             map[y][x] = new MapTile();
        //         } else if (color.equals(Color.BLACK)) {
        //             map[y][x] = new WallTile();
        //         } else if (color.equals(Color.BLUE)) {
        //             map[y][x] = new VerticalMoverTile();
        //         } else if (color.equals(Color.GREEN)) {
        //             map[y][x] = new Entrance();
        //         } else if (color.equals(Color.RED)) {
        //             map[y][x] = new LocationTile();
        //         } else {
        //             map[y][x] = null;
        //         }
        //     }
        // }

        return map;
    }
    public static void main(String args[]){
        MapReader x = new MapReader();
        try{
            x.readImageMap("MapImages");
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }
}
