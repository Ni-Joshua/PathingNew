// package com.pathfinding;

// import java.awt.BorderLayout;
// import java.awt.Color;
// import java.awt.FlowLayout;
// import java.awt.GridBagLayout;
// import java.awt.GridLayout;
// import java.util.Arrays;
// import java.util.List;
// import java.util.TreeMap;
// import java.util.concurrent.Flow;

// import javax.swing.BorderFactory;
// import javax.swing.BoxLayout;
// import javax.swing.JFrame;
// import javax.swing.JLabel;
// import javax.swing.JPanel;
// import javax.swing.JScrollPane;
// import javax.swing.JTabbedPane;
// import javax.swing.border.Border;
// import javax.swing.border.LineBorder;

// import com.pathfinding.HelperClasses.GeneralMap;
// import com.pathfinding.HelperClasses.MapMats.Location;
// import com.pathfinding.HelperClasses.MapMats.MapTile;
// import com.pathfinding.HelperClasses.MapMats.VerticalMoverTile;

// public class AstarTesting {
//     public static void main(String[] args) {
//         // Location mall = new Location("Mall", new Time("6:00"), new Time("20:00"));
//         // Location ah = new Location ("AH", null, null);
//         // Location bh = new Location("BH", null, null);
//         // Location ch = new Location("CH", null, null);
//         // VerticalMoverTile ev2 = new VerticalMoverTile(null);
//         // List<VerticalMoverTile> l1 = new LinkedList<>();
//         // l1.add(ev2);
//         // VerticalMoverTile ev1 = new VerticalMoverTile(l1);
//         // MapTile[][][] grid = new MapTile[][][] {
//         // {{new WallTile(), new WallTile(), new WallTile(), new WallTile(), new
//         // WallTile()},
//         // {new LocationTile(ch), new Entrance(ch), new MapTile(), new MapTile(), new
//         // WallTile()},
//         // {new LocationTile(ch), new WallTile(), new MapTile(), new MapTile(), new
//         // WallTile()},
//         // {new LocationTile(ch), new WallTile(), ev2, new MapTile(), new WallTile()},
//         // {new LocationTile(ch), new Entrance(ch), new MapTile(), new MapTile(), new
//         // WallTile()},
//         // {new LocationTile(ch), new WallTile(), new MapTile(), new MapTile(), new
//         // WallTile()},
//         // {new WallTile(), new WallTile(), new WallTile(), new WallTile(), new
//         // WallTile()}},

//         // {{new WallTile(), new LocationTile(ah), new LocationTile(ah), new
//         // LocationTile(ah), new WallTile()},
//         // {new WallTile(), new WallTile(), new Entrance(ah), new WallTile(), new
//         // WallTile()},
//         // {new WallTile(), new MapTile(), new MapTile(), new MapTile(), new
//         // WallTile()},

//         // {new Entrance(mall), new MapTile(), ev1, new MapTile(), new Entrance(mall)},
//         // {new WallTile(), new MapTile(), new MapTile(), new MapTile(), new
//         // WallTile()},
//         // {new WallTile(), new WallTile(), new Entrance(bh), new WallTile(), new
//         // WallTile()},
//         // {new WallTile(), new LocationTile(bh), new LocationTile(bh), new
//         // LocationTile(bh), new WallTile()}},};

//         MapReader reader = new MapReader();

//         MapTile[][][] grid = null;
//         try {
//             grid = reader.readImageMap("MapImages");
//             TreeMap<String, Location> locMapping = reader.getLocMapping();
//             TreeMap<String, VerticalMoverTile> vmMapping = reader.getVMMapping();
//             TreeMap<String, String> colorMapping = reader.getColorMapping();

//             GeneralMap testMap = new GeneralMap(grid);
//             PathFinder p = new PathFinder(testMap);
//             Location loc1 = locMapping.get("mdl");
//             Location loc2 = locMapping.get("foodcourt");

//             List<Node> pathFromAC = p.pathfind(loc1, loc2);

//             JFrame frame = new JFrame("A* Pathfinding Visualization");
//             frame.setSize(1250, 750);
//             frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//             // JTabbedPane panels = new JTabbedPane();

//             // for (int i = 0; i < grid.length; i++) {
//             //     JPanel tab = new JPanel();
//             //     tab.setLayout(new GridLayout(2,1));
//             //     // create the color information panel
//             //     JPanel colorInfoPanel = createColorInfoPanel();
//             //     tab.add(colorInfoPanel); 
//             //     if (i == testMap.getCoords(loc1).get(0)[0] || i == testMap.getCoords(loc2).get(0)[0]) {
//             //         tab.add(new MapDisplay(grid[i], pathFromAC, i, 500, 500, colorMapping));
//             //     } else {
//             //         tab.add(new MapDisplay(grid[i], null, i, 500, 500, colorMapping));
//             //     }

//             //     // System.out.println(Arrays.deepToString(grid[i]));
//             //     panels.addTab("Floor " + (i+1), tab);
//             // }
//             // frame.add(panels);
//             // frame.setVisible(true);
//             JPanel panel = new JPanel();
//             panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
//             MapDisplay temp = new MapDisplay(null, null, 0, 0, 0, null, 0);

//             JPanel infoPanel = createInfoPanel(temp);
//             panel.add(infoPanel);
//             JPanel t = new JPanel();
//             t.setLayout(new FlowLayout());
//             t.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
 
//             for (int i = 0; i < grid.length; i++) {
//                 // create the color information panel
//                 JPanel tab = new JPanel();
//                 tab.setLayout(new BoxLayout(tab, BoxLayout.PAGE_AXIS));
//                 JLabel floor = new JLabel("Floor " + (i+1));
//                 tab.add(floor);
//                 if (i == testMap.getCoords(loc1).get(0)[0] || i == testMap.getCoords(loc2).get(0)[0]) {
//                     tab.add(new MapDisplay(grid[i], pathFromAC, i, 300, 300, colorMapping, i));
//                 } else {
//                     tab.add(new MapDisplay(grid[i], null, i, 300, 300, colorMapping, i));
//                 }
//                 t.add(tab);
//                 // System.out.println(Arrays.deepToString(grid[i]));
//             }
//             panel.add(t);
//             JScrollPane scroll = new JScrollPane(panel);
//             frame.add(scroll);
//             frame.setVisible(true);

//         } catch (Exception e) {
//             System.out.println(e);
//         }

//         // path = p.findPath(grid, start, end);

//         // if (path.isEmpty()) {
//         // System.out.println("No path found.");
//         // } else {
//         // System.out.println("Path:");
//         // for (Node node : path) {
//         // System.out.println("(" + node.y + ", " + node.x + ")");
//         // }
//         // }

//     }

//     private static JPanel createInfoPanel(MapDisplay temp)
//   {
//     // create a color info panel
//     JPanel colorInfoPanel = new JPanel();
//     colorInfoPanel.setLayout(new FlowLayout());
//     colorInfoPanel.setBorder(new LineBorder(Color.black,1));

//     // create the sample color panel and label
//     JLabel colorLabel = new JLabel("Info: \n Start Coords: " +  Arrays.toString(temp.getStartCoords()) + "\n End Coords: " + Arrays.toString(temp.getEndCoords()));
//     colorInfoPanel.add(colorLabel);    
//     return colorInfoPanel; 
//   }

// }