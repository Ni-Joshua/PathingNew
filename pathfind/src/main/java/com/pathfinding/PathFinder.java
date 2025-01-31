package com.pathfinding;

import com.pathfinding.HelperClasses.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.TreeMap;

import com.pathfinding.HelperClasses.MapMats.*;

/**
 * Object that pathfinds between two points on a given GeneralMap
 * Makes use of the A* pathfinding algorithm to find a path
 * 
 * @author Justin Ely
 * @author Joshua Ni
 */
public class PathFinder {
    /** Map that the pathfinder pathfinds on */
    private GeneralMap map;
    /** Directions that the pathfinder can move */
    private static final int[][] DIRECTIONS = { // Possible movement directions
            { 0, 1 }, { 1, 0 }, { 0, -1 }, { -1, 0 }// , {1, 1}, {1, -1}, {-1, 1}, {-1, -1} these are for diagonal
                                                    // movement
    };

    /**
     * Instantiates new pathfinder with a given map
     * 
     * @param map General Map
     */
    public PathFinder(GeneralMap map) {
        this.map = map;
    }

    /**
     * Pathfind between two locations
     * 
     * @param L1 location 1
     * @param L2 location 2
     * @return list of nodes which is a path between two locations
     */
    public List<Node> pathfind(Location L1, Location L2) {
        int[] entranceStart = ((LinkedList<int[]>) map.getCoords(L1)).get(0);
        int[] entranceEnd = ((LinkedList<int[]>) map.getCoords(L2)).get(0);
        return pathfind(entranceStart, entranceEnd);
    }

    /**
     * Pathfind between two locations by their names
     * 
     * @param S1 location name 1
     * @param S2 location name 2
     * @return list of nodes which is a path between two locations
     */
    public List<Node> pathfind(String S1, String S2) {
        int[] entranceStart = ((ArrayList<int[]>) map.getCoords(S1)).get(0);
        int[] entranceEnd = ((ArrayList<int[]>) map.getCoords(S2)).get(0);
        return pathfind(entranceStart, entranceEnd);
    }

    /**
     * Finds a path between two coordinate positions (3D)
     * Returns list of nodes representing the path between the positions
     * 
     * @param pos1 starting position
     * @param pos2 ending position
     * @return list of nodes representing the path
     */
    private List<Node> pathfind(int[] pos1, int[] pos2) {
        List<Node> totalPath = new ArrayList<>();

        if (pos1[0] != pos2[0]) {
            VerticalMoverTile mover = findNearestVerticalMover(pos1);
            int[] end1Pos = findAppropriateVerticalMoverPosition(mover, pos1);
            totalPath.addAll(findPathByLayer(new Node(pos1[2], pos1[1], pos1[0]),
                    new Node(end1Pos[2], end1Pos[1], pos1[0]), pos1[0]));

            int[] start2Pos = findAppropriateVerticalMoverPosition(mover, pos2);
            totalPath.addAll(findPathByLayer(new Node(start2Pos[2], start2Pos[1], pos2[0]),
                    new Node(pos2[2], pos2[1], pos2[0]), pos2[0]));
        } else {
            totalPath.addAll(
                    findPathByLayer(new Node(pos1[2], pos1[1], pos1[0]), new Node(pos2[2], pos2[1], pos1[0]), pos1[0]));
        }

        return totalPath;
    }

    /**
     * Finds the closest vertical mover to a current position
     * 
     * @param pos current position
     * @return nearest vertical mover on the same floor
     */
    private VerticalMoverTile findNearestVerticalMover(int[] pos) {
        TreeMap<Double, VerticalMoverTile> moversDistances = new TreeMap<Double, VerticalMoverTile>();
        MapTile[][] currentLayer = map.getMap()[pos[0]];
        for (int i = 0; i < currentLayer.length; i++) {
            for (int j = 0; j < currentLayer[i].length; j++) {
                if (currentLayer[i][j].getTileType().equals("verticalmover")) {
                    moversDistances.put(distance(new Node(pos[2], pos[1], pos[0]), new Node(j, i, pos[0])),
                            (VerticalMoverTile) currentLayer[i][j]);
                }
            }
        }
        return moversDistances.get(moversDistances.firstKey());
    }

    // private int findNearestEntranceIndex(int[] pos, Location loc){
    // List<int[]> entrancecoords = (ArrayList<int[]>) map.getCoords(loc);

    // }

    /**
     * Find the position of a vertical mover chain on the floor of a certain
     * position
     * 
     * @param v   vertical mover
     * @param pos floor that we need to find the vertical mover for
     * @return the position of the vertical mover on the floor of pos
     */
    private int[] findAppropriateVerticalMoverPosition(VerticalMoverTile v, int[] pos) {
        List<VerticalMoverTile> connected = v.getConnected();
        connected.add(v);
        int[] correct = new int[3];
        for (VerticalMoverTile t : connected) {
            correct = map.getMoverCoords(t);
            if (correct[0] == pos[0]) {
                break;
            }
        }
        return correct;
    }

    /**
     * Pathfinds between two points on a 2D layer via the use of the A* algorithm
     * 
     * @param start starting node
     * @param end   ending node
     * @param layer the 2D layer of the map that we are pathfinding on
     * @return list of nodes that represents the path between the two points
     */
    private List<Node> findPathByLayer(Node start, Node end, int layer) {
        PriorityQueue<Node> unexplored = new PriorityQueue<>(Comparator.comparingDouble(Node::getTotalCost));
        Set<Node> explored = new HashSet<>();

        start.startCost = 0;
        start.endCost = heuristic(start, end);
        unexplored.add(start);

        while (!unexplored.isEmpty()) {
            Node current = unexplored.poll();

            if (current.equals(end)) {
                return reconstructPath(current);
            }

            explored.add(current);

            for (int[] direction : DIRECTIONS) {
                int newX = current.x + direction[0];
                int newY = current.y + direction[1];

                if (isValid(map.getMap()[layer], newX, newY, end) && !explored.contains(new Node(newX, newY, layer))) {
                    Node neighbor = new Node(newX, newY, layer);

                    double tentativeStartCost = current.startCost + distance(current, neighbor);

                    if (tentativeStartCost < neighbor.startCost || !unexplored.contains(neighbor)) {
                        neighbor.startCost = tentativeStartCost;
                        neighbor.endCost = heuristic(neighbor, end);
                        neighbor.parent = current;

                        if (!unexplored.contains(neighbor)) {
                            unexplored.add(neighbor);
                        }
                    }
                }
            }
        }

        return Collections.emptyList(); // No path found
    }

    /**
     * Calculates heuristic between two nodes (Manhattan distance)
     * 
     * @param a node 1
     * @param b node 2
     * @return manhattan distance between the nodes
     */
    private static double heuristic(Node a, Node b) {
        return Math.abs(a.y - b.y) + Math.abs(a.x - b.x); // Manhattan distance
    }

    /**
     * Calculates euclidean distance between two nodes
     * 
     * @param a node 1
     * @param b node 2
     * @return Euclidean distance between the nodes
     */
    private static double distance(Node a, Node b) {
        return Math.sqrt(Math.pow(a.y - b.y, 2) + Math.pow(a.x - b.x, 2)); // Euclidean distance
    }

    /**
     * Checks if a node can be moved to based on the following conditions:
     * The node has non-negative coordinates
     * The node is in bounds
     * The node is passable or the node is a vertical mover tile and is the end tile
     * 
     * @param grid 2D grid
     * @param x    x coordinate of node
     * @param y    y coordinate of node
     * @param end  ending node
     * @return boolean of whether the node can be moved to
     */
    private static boolean isValid(MapTile[][] grid, int x, int y, Node end) {
        return x >= 0 && y >= 0 && x < grid[0].length && y < grid.length && (grid[y][x].isPassable()
                || (grid[y][x].getTileType().equals("verticalmover") && (new Node(x, y, 0)).equals(end)));
    }

    /**
     * Reconstructs the path that the A* algorithm took using parent nodes
     * 
     * @param end final node
     * @return list of nodes representing the paht
     */
    private static List<Node> reconstructPath(Node end) {
        List<Node> path = new ArrayList<>();
        Node current = end;

        while (current != null) {
            path.add(current);
            current = current.parent;
        }

        Collections.reverse(path);
        return path;
    }
}