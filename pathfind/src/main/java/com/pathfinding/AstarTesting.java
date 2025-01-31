package com.pathfinding;

import com.pathfinding.HelperClasses.MapMats.*;
import com.pathfinding.HelperClasses.*;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class AstarTesting{
    public static void main(String[] args) {
        Location mall = new Location("Mall", new Time("6:00"), new Time("20:00"));
        Location ah = new Location ("AH", null, null);
        Location bh = new Location("BH", null, null);
        Location ch = new Location("CH", null, null);
        VerticalMoverTile ev2 = new VerticalMoverTile(null);
        List<VerticalMoverTile> l1 = new LinkedList<>();
        l1.add(ev2);
        VerticalMoverTile ev1 = new VerticalMoverTile(l1);

        // grid = new int[][] {
        //     {0, 0, 0, 0, 0},
        //     {0, 1, 1, 1, 0},
        //     {0, 1, 1, 1, 0},
        //     {0, 1, 1, 1, 0},
        //     {0, 1, 1, 1, 0},
        //     {0, 1, 1, 1, 0},
        //     {0, 0, 0, 0, 0}
        // };

        MapTile[][][] grid = new MapTile[][][] {
            {{new WallTile(), new WallTile(),  new WallTile(), new WallTile(), new WallTile()},
            {new LocationTile(ch), new Entrance(ch), new MapTile(), new MapTile(), new WallTile()},
            {new LocationTile(ch), new WallTile(), new MapTile(), new MapTile(), new WallTile()},
            {new LocationTile(ch), new WallTile(), ev2, new MapTile(), new WallTile()},
            {new LocationTile(ch), new Entrance(ch), new MapTile(), new MapTile(), new WallTile()},
            {new LocationTile(ch), new WallTile(), new MapTile(), new MapTile(), new WallTile()},
            {new WallTile(), new WallTile(),  new WallTile(), new WallTile(), new WallTile()}},

            {{new WallTile(), new LocationTile(ah),  new LocationTile(ah), new LocationTile(ah), new WallTile()},
            {new WallTile(), new WallTile(), new Entrance(ah), new WallTile(), new WallTile()},
            {new WallTile(), new MapTile(), new MapTile(), new MapTile(), new WallTile()},

            {new Entrance(mall), new MapTile(), ev1, new MapTile(), new Entrance(mall)},
            {new WallTile(), new MapTile(), new MapTile(), new MapTile(), new WallTile()},
            {new WallTile(), new WallTile(), new Entrance(bh), new WallTile(), new WallTile()},
            {new WallTile(), new LocationTile(bh),  new LocationTile(bh), new LocationTile(bh), new WallTile()}},};


        // path = p.findPath(grid, start, end);

        // if (path.isEmpty()) {
        //     System.out.println("No path found.");
        // } else {
        //     System.out.println("Path:");
        //     for (Node node : path) {
        //         System.out.println("(" + node.y + ", " + node.x + ")");
        //     }
        // }
        
        GeneralMap testMap = new GeneralMap(grid);
        PathFinder p = new PathFinder(testMap);

        List<Node> pathFromAC = p.pathfind(ah,ch);

        JFrame frame = new JFrame("A* Pathfinding Visualization");
        frame.setSize(1000, 1000);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JTabbedPane panels = new JTabbedPane();

        for (int i = grid.length - 1; i > -1; i--){
            JPanel tab = new JPanel();
            if (i == testMap.getCoords(ah).get(0)[0] || i == testMap.getCoords(ch).get(0)[0]){
                tab.add(new MapDisplay(grid[i], pathFromAC, i, 500, 500));
            } else {
                tab.add(new MapDisplay(grid[i], null, i, 500, 500));
            }
            
            // System.out.println(Arrays.deepToString(grid[i]));
            panels.addTab("Floor " + (grid.length-i), tab);
        }
        frame.add(panels);
        frame.setVisible(true);
        }
        
}