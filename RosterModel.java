/**
 * Authors:           Briggs Richardson, Conner Erickson, Kim Kieu Pham,
 *                    Valeriia Starosek
 * Class ID:          70605 , CSE360 Tues 9:00 AM
 * Assignment:        Final Project
 * File Description:  This file contains the RosterModel class, the main class of the Model part of the
 * Model-View-Controller architecture. This manipulates the data structures viewed by the table and plot.
 */

import javax.annotation.processing.Filer;
import java.io.*;
import javax.swing.JFrame;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class RosterModel {
    private ArrayList<Student> roster = new ArrayList<>();
    private ArrayList<ArrayList<Integer>> attendance = new ArrayList<>();
    private JFrame container;

    public RosterModel(JFrame container) {
        this.container = container;
    }

    /**
     * Takes a File object and adds information from file into ArrayList of roster.
     *
     * @param file - A .csv File object.
     * @throws IOException
     */
    public void updateRoster(File file) {
        try (BufferedReader filereader = new BufferedReader(new FileReader(file))) {
            ArrayList<String> missingID = new ArrayList<>();
            ArrayList<String> missingField = new ArrayList<>();
            String row;
            roster.add(0, null);
            int i = 1;
            while ((row = filereader.readLine()) != null) {

                String[] data = row.split(",", -1);

                if (data[5].equals("")) { // Missing ASURITE
                    missingID.add(row);
                    continue;
                }

                Student student = new Student(data[0], data[1], data[2],
                        data[3],data[4],data[5]);
                for (int fieldIX = 0; fieldIX < 4; fieldIX++) {
                    if (data[fieldIX].equals(""))
                        missingField.add(row);
                }

                roster.add(i, student);
                i++;
            }
            if (missingID.size() > 0 || missingField.size() > 0) {
                String errorMessage = "";
                if (missingField.size() > 0) {
                    errorMessage += "There are missing fields in the following "
                        + "lines\n\n";
                    for (int errorIX = 0; errorIX < missingField.size(); errorIX++) {
                        errorMessage += missingField.get(errorIX) + "\n";
                    }
                    errorMessage += "\n\n";
                }
                if (missingID.size() > 0) {
                    errorMessage += "There are missing assurite ID fields"
                     + " in lines:\n\n";
                    for (int errorIX = 0; errorIX < missingID.size(); errorIX++) {
                        errorMessage += missingID.get(errorIX) + "\n";
                    }
                    errorMessage += "\nThe CSV rows were skipped\n";
                    errorMessage += "Please fix the file and restart the program!\n";
                }
                JOptionPane.showMessageDialog(container, errorMessage);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Takes a .csv file and converts information into the attendance arraylist.
     *
     * @param date - The date of attendance being recorded.
     * @param file - The .csv file being read from.
     * @throws IOException
     */
    public void updateAttendance(int date, File file) {
        try (BufferedReader filereader = new BufferedReader(new FileReader(file))) {
            String row;
            int totalTime;
            int numLoaded = 0;
            
            ArrayList<Attendance> fileData = new ArrayList<Attendance>();
            ArrayList<Integer> dateAttendance = new ArrayList<Integer>();
            ArrayList<String> errors = new ArrayList<>();
            ArrayList<Attendance> additionalAttendees = new ArrayList<>();

            dateAttendance.add(0, date);

            // Store file content in an arraylist for further processing
            int i = 0;
            while ((row = filereader.readLine()) != null) {
                String[] data = row.split(",", -1);

                if (data[0].equals("") || data[1].equals("")) { // Missing field
                    errors.add(row);
                    continue;
                }

                Attendance studentAttendance = new Attendance(
                        data[0], Integer.parseInt(data[1]));

                // Check if attendee's asurite exists in roster
                boolean found = false;
                for (int rostIX = 1; rostIX < roster.size(); rostIX++) {
                   if (data[0].equals(roster.get(rostIX).getID())) {
                        found = true;
                   }
                }
                if (found == false) {
                    additionalAttendees.add(studentAttendance);
                }

                fileData.add(i, studentAttendance);
                i++;
           }

           for (int dateIndex = 1; dateIndex < roster.size(); dateIndex++) {
               totalTime = 0;
               boolean wasFound = false;
               for (int fileIndex = 0; fileIndex < fileData.size(); fileIndex++) {
                   if (fileData.get(fileIndex).getID().equals(roster.get(dateIndex).getID())) {
                       wasFound = true;
                       totalTime += fileData.get(fileIndex).getTime();
                   }
               }
               dateAttendance.add(dateIndex, totalTime);
               if (wasFound == true) {
                   ++numLoaded;
               }
           }
           attendance.add(dateAttendance);

           String resultMessage = "Data loaded for " + numLoaded + " users in "
               + "the roster.\n\n";
           resultMessage += additionalAttendees.size() + " additional "
               + "attendee was found:\n\n";
           for (int addIX = 0; addIX < additionalAttendees.size(); addIX++) {
                resultMessage += additionalAttendees.get(addIX).getID() + 
                    ", connected for " + additionalAttendees.get(addIX).getTime()
                    + " minutes\n";
           }
           JOptionPane.showMessageDialog(container, resultMessage);

           if (errors.size() > 0) {
               String errorMessage = "There is a missing field in the rows:\n";
               for (int errorIX = 0; errorIX < errors.size(); errorIX++) {
                   errorMessage += errors.get(errorIX) + "\n";
               }
               errorMessage += "The CSV row(s) was/were skipped\n";
               JOptionPane.showMessageDialog(container, errorMessage);
           }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns a string version of the roster.
     *
     * @return Roster string 2-d array
     */
    public String[][] getRoster() {
        String[][] export = new String[roster.size() - 1][6];

        //add code to turn roster into string[][]
        for (int i = 0; i < roster.size() - 1; i++) {
            export[i] = roster.get(i+1).stringify(); }

        return export;
    }

    /**
     * Returns a string version of the attendance.
     *
     * @return Attendance string 2d array.
     */
    public String[][] getAttendance() {
        String[][] export = new String[2][roster.size() - 1];
        export[0][0] = stringifyDate(attendance.get(attendance.size() - 1).get(0));
        for (int i = 0; i < export[1].length; i++) {
            export[1][i] = Integer.toString(attendance.get(attendance.size() - 1).get(i+1));
        }

        return export;
    }

    /**
     * Returns a string with the date information
     * @param IntegerDate date information as integer
     * @return string version of date info
     */
    private String stringifyDate(Integer IntegerDate) {
        String date = IntegerDate.toString();
        String day = date.substring(6);
        if (day.charAt(0) == '0')
            day = day.substring(1);
        String month = date.substring(4, 6);
        if (month.equals("01")) 
            month = "Jan";
        else if (month.equals("02"))
            month = "Feb";
        else if (month.equals("03"))
            month = "Mar";
        else if (month.equals("04"))
            month = "Apr";
        else if (month.equals("05"))
            month = "May";
        else if (month.equals("06"))
            month = "Jun";
        else if (month.equals("07"))
            month = "Jul";
        else if (month.equals("08"))
            month = "Aug";
        else if (month.equals("09"))
            month = "Sep";
        else if (month.equals("10"))
            month = "Oct";
        else if (month.equals("11"))
            month = "Nov";
        else 
            month = "Dec";
        
        return month + " " + day;
    }
    
    /**
     * returns attendance list as list of objects for plot class
     * @return attendance list
     */
    public ArrayList<ArrayList<Object>> getPlotAttendance(){
        ArrayList<ArrayList<Object>> list = new ArrayList<ArrayList<Object>>();
        for(int i = 0; i < attendance.size(); i++){
            list.add(new ArrayList<>());

            for(int j = 0; j < attendance.get(i).size(); j++){
                list.get(i).add(attendance.get(i).get(j));
            }

            list.get(i).set(0, stringifyDate(attendance.get(i).get(0)));
        }

        return list;
    }
}
