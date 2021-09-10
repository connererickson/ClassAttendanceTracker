/**
 * Authors:           Briggs Richardson, Conner Erickson, Kim Kieu Pham,
 *                    Valeriia Starosek
 * Class ID:          70605 , CSE360 Tues 9:00 AM
 * Assignment:        Final Project
 * File Description:  This file contains the Attendance class, a basic data type used in the attendance linked list.
 */

class Attendance {
    private String assuriteID;
    private int time;

    /**
     * constructor
     * @param id asurite id
     * @param givenTime given login info
     */
    public Attendance(String id, int givenTime) {
        assuriteID = id;
        time = givenTime;
    }

    /**
     * returns id
     * @return id
     */
    public String getID() {
        return assuriteID;
    }

    /**
     * returns time
     * @return time
     */
    public int getTime() {
        return time;
    }
}