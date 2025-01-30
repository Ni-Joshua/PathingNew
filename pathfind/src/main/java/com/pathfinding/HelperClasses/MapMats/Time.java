package com.pathfinding.HelperClasses.MapMats;

/**
 * Object that stores a time in mintes and
 * prints out time in hours and minutes
 */
public class Time {
    /**Length of time in mintues */
    private int minutes;

    /**Creates new Time in minutes */
    public Time(int minutes){
        this.minutes = minutes;
    }

    /**Creates new time from input in mm:ss format */
    public Time(String minAndHours){
        minutes += Integer.parseInt(minAndHours.substring(0,minAndHours.indexOf(":")))*60;
        minutes += Integer.parseInt(minAndHours.substring(minAndHours.indexOf(":")+1));
    }

    /**@return time in minutes */
    public int getTimeMinutes(){
        return minutes;
    }

    /**Change time in minutes */
    public void setTimeMinutes(int minutes){
        this.minutes = minutes;
    }

    /**@return time in hours and minutes */
    public String toString(){
        if (minutes%60 > 9){
            return "" + minutes/60 + ":" + minutes%60;
        } else {
            return "" + minutes/60 + ":0" + minutes%60;
        }
    }
}

