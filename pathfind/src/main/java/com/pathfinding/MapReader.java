package com.pathfinding;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Arrays;

import javax.imageio.ImageIO;

import com.pathfinding.HelperClasses.MapMats.*;

public class MapReader {

    public MapTile[][][] readImageMap(String folderPath){
        File mall = new File(folderPath + "//Floors"); 
        ObjectMapper mapper = new ObjectMapper();

        // File[] info = mall.listFiles(); 

        // int depth = info.length;
        // BufferedImage image = ImageIO.read(info[0]);
        // int width = image.getWidth();
        // int height = image.getHeight();

        // MapTile[][][] map = new MapTile[height][width];
        MapTile[][][] map = new MapTile[0][0][0];

        // for (int y = 0; y < height; y++) {
        //     for (int x = 0; x < width; x++) {
        //         int rgb = image.getRGB(x, y);
        //         Color color = new Color(rgb);

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
        File f = new File("Pathing\\MapImages"); 
        File[] files = f.listFiles(); 
        MapReader x = new MapReader();
        x.readImageMap("Pathing\\MapImages");
    }
}
