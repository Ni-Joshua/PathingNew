package com.pathfinding.Pathfinder;

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
    private int x, y, z;
    /** Heuristic cost to start and end nodes */
    private double startCost, endCost;
    /** Previous node in path of nodes */
    private Node parent;

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

    /**
     * Returns x value
     * @return x value
     */
    public int getX(){
        return x;
    }

    /**
     * Returns y value
     * @return y value
     */
    public int getY(){
        return y;
    }

    /**
     * Returns z value
     * @return z value
     */
    public int getZ(){
        return z;
    }

    /**
     * Returns start heuristic cost of the node
     * @return startCost
     */
    public double getStartCost(){
        return startCost;
    }
    /**
     * Returns end heuristic cost of the node
     * @return endCost
     */
    public double setEndCost(){
        return endCost;
    }

    /**
     * Returns parent of the node
     * @return parent
     */
    public Node getParent(){
        return parent;
    }

    /**
     * Sets the startCost to a given value
     * @param startCost
     */
    public void setStartCost(double startCost){
        this.startCost = startCost;
    }
    /**
     * Sets the endCost to a given value
     * @param endCost
     */
    public void setEndCost(double endCost){
        this.endCost = endCost;
    }

    /**
     * Sets the parent node to a given value
     * @param parent
     */
    public void setParent(Node parent){
        this.parent = parent;
    }


}
