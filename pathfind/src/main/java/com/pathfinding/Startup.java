package com.pathfinding;

import com.pathfinding.DisplayClasses.MapDisplayFrame;

public class Startup {
    public static void main(String[] args) {
        try {
            MapDisplayFrame x = new MapDisplayFrame("MapImages");
            x.guiSetup();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
}
