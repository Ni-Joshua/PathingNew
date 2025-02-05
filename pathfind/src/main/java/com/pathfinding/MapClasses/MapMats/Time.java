package com.pathfinding.MapClasses.MapMats;

/**
 * Object that stores a time in mintes and
 * prints out time in hours and minutes
 */
public class Time {
    /**Length of time in mintues */
    private Integer minutes;

    /** Creates new Time in minutes */
    public Time(int minutes) {
        this.minutes = minutes;
    }

    /**Creates new time from input in hh:mm format */
    public Time(String minAndHours){
        if (minAndHours == null){
            minutes = null;
        }
        else{
            minutes = 0;
            minutes += Integer.parseInt(minAndHours.substring(0,minAndHours.indexOf(":")))*60;
            minutes += Integer.parseInt(minAndHours.substring(minAndHours.indexOf(":")+1));
        }
    }

    /**@return time in minutes */
    public Integer getTimeMinutes(){
        return minutes;
    }

    /** Change time in minutes */
    public void setTimeMinutes(int minutes) {
        this.minutes = minutes;
    }

    @Override
    /**@return time in hours and minutes */
    public String toString(){
        if (minutes == null){
            return "";
        }
        if (minutes%60 > 9){
            return "" + minutes/60 + ":" + minutes%60;
        } else {
            return "" + minutes / 60 + ":0" + minutes % 60;
        }
    }
}
