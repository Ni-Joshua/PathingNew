package com.pathfinding;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
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
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import com.pathfinding.HelperClasses.GeneralMap;
import com.pathfinding.HelperClasses.MapMats.Location;
import com.pathfinding.HelperClasses.MapMats.MapTile;
import com.pathfinding.HelperClasses.MapMats.VerticalMoverTile;

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

    public void guiSetup(String folderPath) {
        MapReader reader = new MapReader();
        startCoords = new int[3];
        endCoords = new int[3];

        try {
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
                            //Need Find Better Implementation
                            // frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                            // guiSetup(fileChooser.getSelectedFile().getAbsolutePath());
                            break;
                    }
                }
            });

            grid = reader.readImageMap(folderPath);
            locMapping = reader.getLocMapping();
            vmMapping = reader.getVMMapping();
            colorMapping = reader.getColorMapping();
            gMap = new GeneralMap(grid);
            PathFinder p = new PathFinder(gMap);

            Location loc1 = locMapping.get("mdl");
            Location loc2 = locMapping.get("foodcourt");
            List<Node> pathFromAC = p.pathfind(loc1, loc2);

            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
            createInfoPanel();
            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new GridLayout());
            buttonPanel.add(infoPanel);
            JButton b = new JButton("Route");
            b.addActionListener(new Routing());
            buttonPanel.add(b);

            panel.add(buttonPanel);

            viewerPanel = createMapViewerPanel(pathFromAC, loc1, loc2);
            panel.add(viewerPanel);

            JScrollPane scroll = new JScrollPane(panel);
            frame.add(scroll);
            frame.setVisible(true);

        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public static void main(String[] args) {
        MapDisplayFrame x = new MapDisplayFrame();
        x.guiSetup("MapImages");
    }

    private void createInfoPanel() {
        // create a color info panel
        infoPanel = new InfoPanel(startCoords, endCoords);
    }

    private JPanel createMapViewerPanel(List<Node> pathFromAC, Location loc1, Location loc2) {
        JPanel mapViewer = new JPanel();
        mapViewer.setLayout(new FlowLayout());
        mapViewer.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        Listener listener = new Listener();
        for (int i = 0; i < grid.length; i++) {
            JPanel tab = new JPanel();
            tab.setLayout(new BoxLayout(tab, BoxLayout.PAGE_AXIS));
            JLabel floor = new JLabel("Floor " + (i + 1));
            tab.add(floor);
            MapDisplay tempMapDisplay;
            if (i == gMap.getCoords(loc1).get(0)[0] || i == gMap.getCoords(loc2).get(0)[0]) {
                tempMapDisplay = new MapDisplay(grid[i], pathFromAC, i, 500, 500, colorMapping, i);
            } else {
                tempMapDisplay = new MapDisplay(grid[i], null, i, 500, 500, colorMapping, i);
            }
            tempMapDisplay.setName("Floor " + (i + 1));
            tempMapDisplay.addMouseListener(listener);
            tab.add(tempMapDisplay);
            mapViewer.add(tab);
        }
        return mapViewer;
    }

    private class Listener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            MapDisplay source = (MapDisplay) e.getSource();
            int cellSize = source.getScale();

            int zValue = source.getZValue();
            int yValue = e.getY() / cellSize;
            int xValue = e.getX() / cellSize;

            // System.out.println(e.getPoint());
            // System.out.println(zValue + " " + xValue + " " + yValue);
            // System.out.println(gMap.getTile(zValue, yValue, xValue).getTileType());

            if (SwingUtilities.isLeftMouseButton(e)) {
                if (gMap.getTile(zValue, yValue, xValue).isSelectable()) {
                    startCoords[0] = zValue;
                    startCoords[1] = yValue;
                    startCoords[2] = xValue;
                    infoPanel.setStartInfo(startCoords, gMap.getTile(zValue, yValue, xValue));
                }
            } else if (SwingUtilities.isRightMouseButton(e)) {
                if (gMap.getTile(zValue, yValue, xValue).isSelectable()) {
                    endCoords[0] = zValue;
                    endCoords[1] = yValue;
                    endCoords[2] = xValue;
                    infoPanel.setEndInfo(endCoords, gMap.getTile(zValue, yValue, xValue));
                }
            }

        }
    }

    private class Routing implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            //Currently incomplete
            System.out.println("Routing");
        }
    }

}
