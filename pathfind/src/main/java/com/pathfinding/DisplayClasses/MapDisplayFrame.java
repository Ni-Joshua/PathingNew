package com.pathfinding.DisplayClasses;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import com.pathfinding.MapClasses.GeneralMap;
import com.pathfinding.MapClasses.MapReader;
import com.pathfinding.MapClasses.MapMats.Location;
import com.pathfinding.MapClasses.MapMats.MapTile;
import com.pathfinding.MapClasses.MapMats.VerticalMoverTile;
import com.pathfinding.Pathfinder.Node;
import com.pathfinding.Pathfinder.PathFinder;

public class MapDisplayFrame {
    private String folderPath;
    private int[] startCoords;
    private int[] endCoords;
    private Map<String, Location> locMapping;
    private Map<String, VerticalMoverTile> vmMapping;
    private Map<String, String> colorMapping;
    private GeneralMap gMap;
    private PathFinder p;
    private JFrame frame;
    private InfoPanel infoPanel;
    private JPanel viewerPanel;
    private JScrollPane scroll;
    private JButton routeButton;
    private List<MapDisplay> floors;
    private boolean startPressed;
    private boolean endPressed;

    /**
     * Constructs a new JFrame that will handle all the UI
     * @param folderPath folder path of map
     * @throws IOException
     */
    public MapDisplayFrame(String folderPath) throws IOException{
        frame = new JFrame("Multi-Level Mapper");
        frame.setSize(1250, 750);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Constructs the file opener
        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);
        JMenuItem mn = new JMenuItem("Choose Folder");
        menuBar.add(mn);
        mn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser(".");
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                switch (fileChooser.showOpenDialog(frame)) {
                    case JFileChooser.APPROVE_OPTION:
                        setFolderPath(fileChooser.getSelectedFile().getAbsolutePath());
                        if (scroll != null){
                            frame.remove(scroll);
                        }
                        guiSetup();
                        break;
                }
            }
        });
        this.folderPath = folderPath;
    }

    /**
     * Changes the displayed Map based on a new folderPath
     * @param folderPath path to the new folder
     */
    public void setFolderPath(String folderPath){
        this.folderPath = folderPath;
    }

    /**
     * Sets up all the UI and reads the map from the specified folder
     */
    public void guiSetup() {
        if (folderPath != null){
            try {
                startCoords = new int[3];
                endCoords = new int[3];
                startPressed = false;
                endPressed = false;
                MapReader reader = new MapReader();
                gMap = new GeneralMap(reader.readImageMap(folderPath));
                locMapping = reader.getLocMapping();
                vmMapping = reader.getVMMapping();
                colorMapping = reader.getColorMapping();
                p = new PathFinder(gMap);
                floors = new LinkedList<>();
    
                JPanel panel = new JPanel();
                panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
                createInfoPanel();
                JPanel buttonPanel = new JPanel();
                buttonPanel.setLayout(new GridLayout());
                buttonPanel.add(infoPanel);
                routeButton = new JButton("Route");
                routeButton.addActionListener(new Routing());
                routeButton.setEnabled(false);
                buttonPanel.add(routeButton);
    
                panel.add(buttonPanel);
    
                createviewerPanelPanel();
                panel.add(viewerPanel);
    
                scroll = new JScrollPane(panel);
                frame.add(scroll);
                frame.setVisible(true);
    
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        else {
            frame.setVisible(true);
        }

    }

    /**
     * Creates the panel displaying start and end coordinate information
     */
    private void createInfoPanel() {
        infoPanel = new InfoPanel(startCoords, endCoords);
    }

    /**
     * Creates the JPanel that displays all the floor maps
     */
    private void createviewerPanelPanel() {
        viewerPanel = new JPanel();
        viewerPanel.setLayout(new FlowLayout());
        viewerPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        Listener listener = new Listener();
        MapTile[][][] grid = gMap.getMap();
        for (int i = 0; i < grid.length; i++) {
            JPanel tab = new JPanel();
            tab.setLayout(new BoxLayout(tab, BoxLayout.PAGE_AXIS));
            JLabel floor = new JLabel("Floor " + (i + 1));
            tab.add(floor);
            MapDisplay tempMapDisplay = new MapDisplay(grid[i], null, Math.max(500, grid[i].length), Math.max(500, grid[i][0].length), colorMapping, i);
            tempMapDisplay.setName("Floor " + (i + 1));
            tempMapDisplay.addMouseListener(listener);
            floors.add(tempMapDisplay);
            tab.add(tempMapDisplay);

            viewerPanel.add(tab);
        }
    }

    /**
     * Class that handles mouse presses on the floor maps
     */
    private class Listener extends MouseAdapter {
        
        @Override
        /**
         * Handles mouse clicking on floor maps
         */
        public void mouseClicked(MouseEvent e) {
            MapDisplay source = (MapDisplay) e.getSource();
            int cellSize = source.getScale();

            int zValue = source.getZValue();
            int yValue = e.getY() / cellSize;
            int xValue = e.getX() / cellSize;

            if (SwingUtilities.isLeftMouseButton(e)) {
                //sets start location
                if (gMap.getTile(zValue, yValue, xValue).isSelectable()) {
                    startCoords[0] = zValue;
                    startCoords[1] = yValue;
                    startCoords[2] = xValue;
                    infoPanel.setStartInfo(startCoords, gMap.getTile(zValue, yValue, xValue));
                    startPressed = true;
                    for (MapDisplay floor: floors){
                        floor.setStartCoords(startCoords);
                        floor.repaint();
                    }
                    
                }
            } else if (SwingUtilities.isRightMouseButton(e)) {
                //sets end location
                if (gMap.getTile(zValue, yValue, xValue).isSelectable()) {
                    endCoords[0] = zValue;
                    endCoords[1] = yValue;
                    endCoords[2] = xValue;
                    infoPanel.setEndInfo(endCoords, gMap.getTile(zValue, yValue, xValue));
                    endPressed = true;
                    for (MapDisplay floor: floors){
                        floor.setEndCoords(endCoords);
                        floor.repaint();
                    }
                }
            }
            //For enabling the button after an initial start and end location are specified
            if (startPressed && endPressed){
                routeButton.setEnabled(true);
            }
        }
    }

    /**
     * Class for handling the routing button
     */
    private class Routing implements ActionListener {
        @Override
        /**
         * Handles the route button press 
         */
        public void actionPerformed(ActionEvent e) {
            int zValueS = startCoords[0];
            int yValueS = startCoords[1];
            int xValueS = startCoords[2];

            int zValueE = endCoords[0];
            int yValueE = endCoords[1];
            int xValueE = endCoords[2];

            boolean isStartLoc = false;
            boolean isEndLoc = false;

            MapTile startTile = gMap.getTile(zValueS, yValueS, xValueS);
            MapTile endTile = gMap.getTile(zValueE, yValueE, xValueE);

            switch (startTile.getTileType()){
                case "location":
                case "entrance":
                    isStartLoc = true;
                    break;
                default:
                    break;
            }

            switch (endTile.getTileType()){
                case "location":
                case "entrance":
                    isEndLoc = true;
                    break;
                default:
                    break;
            }
            
            //Routes depending on if the start and end locations are tied to an entrance
            List<Node> pathFromAC;
            if (isStartLoc && isEndLoc){
                pathFromAC = p.pathfind(startTile.getLocation(), endTile.getLocation());
            }
            else if (isStartLoc && !isEndLoc){
                pathFromAC = p.pathfind(startTile.getLocation(), endCoords);
            }
            else if (!isStartLoc && isEndLoc){
                pathFromAC = p.pathfind(startCoords, endTile.getLocation());
            }
            else {
                pathFromAC = p.pathfind(startCoords, endCoords);
            }

            for (MapDisplay floor : floors){
                floor.setPath(pathFromAC);
                floor.repaint();
            }
        }
    }

}
