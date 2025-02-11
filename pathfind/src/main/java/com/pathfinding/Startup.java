package com.pathfinding;

import java.io.IOException;

import com.pathfinding.DisplayClasses.MapDisplayFrame;

public class Startup {
    public static void main(String[] args) throws IOException {
        // try {
            MapDisplayFrame x = new MapDisplayFrame(null);
            x.guiSetup();
        // } catch (Exception ex) {
        //     System.out.println(ex);
        // }
    }
}
