package com.pathfinding.DisplayClasses;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.plaf.basic.BasicRootPaneUI;

import com.pathfinding.MapClasses.GeneralMap;
import com.pathfinding.MapClasses.MapReader;
import com.pathfinding.MapClasses.MapMats.Location;
import com.pathfinding.MapClasses.MapMats.MapTile;
import com.pathfinding.MapClasses.MapMats.VerticalMoverTile;
import com.pathfinding.Pathfinder.Node;
import com.pathfinding.Pathfinder.PathFinder;

public class MapDisplayFrame {
    private JFrame frame;
    private int[] startCoords;
    private int[] endCoords;
    private TreeMap<String, Location> locMapping;
    private TreeMap<String, VerticalMoverTile> vmMapping;
    private TreeMap<String, String> colorMapping;
    private GeneralMap gMap;
    private MapTile[][][] grid;
    private InfoPanel infoPanel;
    private JPanel viewerPanel;
    private PathFinder p;
    private List<MapDisplay> floors;
    private String folderPath;
    private JScrollPane scroll;
    private boolean startPressed;
    private boolean endPressed;
    private JButton routeButton;

    public MapDisplayFrame(String folderPath) throws IOException{
        frame = new JFrame("Pathfinding Visualization");
        frame.setSize(1250, 750);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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

    public void setFolderPath(String folderPath){
        this.folderPath = folderPath;
    }

    public void guiSetup() {
        if (folderPath != null){
            try {
                startCoords = new int[3];
                endCoords = new int[3];
                startPressed = false;
                endPressed = false;
                MapReader reader = new MapReader();
                grid = reader.readImageMap(folderPath);
                locMapping = reader.getLocMapping();
                vmMapping = reader.getVMMapping();
                colorMapping = reader.getColorMapping();
                gMap = new GeneralMap(grid);
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

    private void createInfoPanel() {
        // create a color info panel
        infoPanel = new InfoPanel(startCoords, endCoords);
    }

    private void createviewerPanelPanel() {
        viewerPanel = new JPanel();
        viewerPanel.setLayout(new FlowLayout());
        viewerPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        Listener listener = new Listener();
        for (int i = 0; i < grid.length; i++) {
            System.out.println(i);
            JPanel tab = new JPanel();
            tab.setLayout(new BoxLayout(tab, BoxLayout.PAGE_AXIS));
            JLabel floor = new JLabel("Floor " + (i + 1));
            tab.add(floor);
            MapDisplay tempMapDisplay = new MapDisplay(grid[i], null, 500, 500, colorMapping, i);
            tempMapDisplay.setName("Floor " + (i + 1));
            tempMapDisplay.addMouseListener(listener);
            floors.add(tempMapDisplay);
            tab.add(tempMapDisplay);
            viewerPanel.add(tab);
        }
    }

    private class Listener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            MapDisplay source = (MapDisplay) e.getSource();
            int cellSize = source.getScale();

            int zValue = source.getZValue();
            int yValue = e.getY() / cellSize;
            int xValue = e.getX() / cellSize;

            System.out.println(e.getPoint());
            System.out.println(zValue + " " + xValue + " " + yValue);
            System.out.println(gMap.getTile(zValue, yValue, xValue).getTileType());

            if (SwingUtilities.isLeftMouseButton(e)) {
                if (gMap.getTile(zValue, yValue, xValue).isSelectable()) {
                    startCoords[0] = zValue;
                    startCoords[1] = yValue;
                    startCoords[2] = xValue;
                    infoPanel.setStartInfo(startCoords, gMap.getTile(zValue, yValue, xValue));
                    startPressed = true;
                }
            } else if (SwingUtilities.isRightMouseButton(e)) {
                if (gMap.getTile(zValue, yValue, xValue).isSelectable()) {
                    endCoords[0] = zValue;
                    endCoords[1] = yValue;
                    endCoords[2] = xValue;
                    infoPanel.setEndInfo(endCoords, gMap.getTile(zValue, yValue, xValue));
                    endPressed = true;
                }
            }
            if (startPressed && endPressed){
                routeButton.setEnabled(true);
            }
        }
    }

    private class Routing implements ActionListener {
        @Override
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
            Object x = new int[3];
            
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
