package com.pathfinding;

import java.util.Objects;

/**
 * Nodes that represent points on the grid
 * Helper class for A* algorithm, since tile coordinates are hard to access
 * 
 * @author Justin Ely
 * @author Joshua Ni
 */
public class Node {
    /** Coordinates of the node */
    int x, y, z;
    /** Heuristic cost to start and end nodes */
    double startCost, endCost;
    /** Previous node in path of nodes */
    Node parent;

    /** Constructs new node with coordinates */
    public Node(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Returns total heuristic cost of node
     * 
     * @return start + end heuristic cost
     */
    public double getTotalCost() {
        return startCost + endCost;
    }

    /**
     * Checks equality of two nodes by comparing x and y coordinates
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Node node = (Node) obj;
        return x == node.x && y == node.y;
    }

    /**
     * Hash method for uses in hashmaps (default hash)
     */
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
