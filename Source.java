/**
* Authors:           Briggs Richardson, Conner Erickson, Kim Kieu Pham, 
*                    Valeriia Starosek 
* Class ID:          70605 , CSE360 Tues 9:00 AM 
* Assignment:        Final Project
* File Description:  This file contains the Source class declaration and
*                    definitions. This is the controller of the program.
*                    Further details on it can be found below.
*/

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.util.Observable;
import java.io.*;

/**
 * The Source class is the controller of the program. It has its functions
 * called from the actionPerformed method in main, and will obtain the 
 * necessary input (such as date or files). Since Source is an Observable,
 * it tells all of its observers (ContentPanel) that it has updated information,
 * and then the observers (ContentPanel), calls accesssors from this function
 * to execute the functionality of the event
 */
public class Source extends Observable {
    private File roster;
    private File attendance;
    private File saveFile;
    private int date;
    private JFrame container;
    private boolean rosterLoaded;
    private boolean attendanceLoaded;

    /**
     * memChanged is a status variable (holding the values 1-4)
     * It is used from the Observers to determine which event was called
     * The static variables below are the events, and further increase
     * code readability in the Observer classes
     */
    private int memChanged;
    public static final int LOAD_ROSTER = 1;
    public static final int LOAD_ATTENDANCE = 2;
    public static final int SAVE = 3;
    public static final int PLOT_DATA = 4;
    

    /**
     * Constructor - Initializes status variables to false initially,
     * and sets up the parent container to allow for centered dialog boxes
     *
     * @param JFrame givenContainer
     * @return void
     */
    public Source(JFrame givenContainer) {
        container = givenContainer;
        rosterLoaded = false;
        attendanceLoaded = false;
    }


    /**
     * Asks the user to select a roster file, for which it then sets
     * using JFileChooser. ContentPanel will know this function was called,
     * and will then call the accessor function, getting the loaded roster file
     *
     * @param void
     * @return void
     */
    public void loadRoster() {
        if (rosterLoaded == false) {
            roster = selectFile();

            if (roster != null) {
                if (isValidRosterFile(roster)) {
                    rosterLoaded = true;
                    memChanged = 1;   
                    setChanged();
                    notifyObservers();
                } else { 
                    JOptionPane.showMessageDialog(container,
                            "Error: Not a valid roster file");
                }
            }
        } else {
            JOptionPane.showMessageDialog(container, "Error: Roster already loaded");
        }
    }


    /**
     * Asks the user to select an attendance file, for which it then sets
     * using JFileChooser. ContentPanel will know this function was called,
     * and will then call the accessor function, getting the loaded attendance 
     * file
     *
     * @param void
     * @return void
     */
    public void loadAttendance() {
        if (rosterLoaded == true) {
            attendance = selectFile();
            if (attendance != null) {
                if (isValidAttendanceFile(attendance)) {
                    attendanceLoaded = true;
                    Calendar cal = new Calendar(container);
                    date = cal.getDate();

                    memChanged = 2;   
                    setChanged();
                    notifyObservers();
                } else {
                    JOptionPane.showMessageDialog(container, 
                            "Error: Not a valid attendance file");
                }
            }
        } else {
            JOptionPane.showMessageDialog(container, "Error: Load a Roster first"); 
        }
    }


    /**
     * This function is called when the save JMenu button is clicked. It
     * simply sets memChanged and notifies the observer of the event
     * If a roster hasn't been loaded, then there is nothing to save
     *
     * @param void
     * @return void
     */
    public void save() {
        if (rosterLoaded == true) {
            String helpMsg = "Either select a file to save the table contents "
                + "to\n" + "Or cancel to save to the default: Table.csv\n";
            JOptionPane.showMessageDialog(container, helpMsg);

            saveFile = selectFile();

            memChanged = 3;   
            setChanged();
            notifyObservers();
        } else {
            JOptionPane.showMessageDialog(container, "Error: No data to save");
        }
    }


    /**
     * This function is called when the plot JMenu button is clicked. It
     * simply sets memChanged and notifies the observer of the event
     * If an attendance file hasn't been loaded, then there is nothing to plot
     *
     * @param void
     * @return void
     */
    public void plotPanel() {
        if (attendanceLoaded == true) {
            memChanged = 4;   
            setChanged();
            notifyObservers();
        } else { 
            JOptionPane.showMessageDialog(container, "Error: No data to plot");
        }
    }


    /**
     * This function is called when the user clicks on the JButton about. It
     * pops up a JOptionPane message box displaying the names of all the team
     * members
     *
     * @param void
     * @return void
     */
    public void about() {
        String teamInfo = "Team information:\n" +
                    " Briggs Richardson\n" +
                    " Kim Kieu Pham\n" +
                    " Conner Erickson\n" + 
                    " Valeriia Starosek <vstarose@asu.edu>";
        JOptionPane.showMessageDialog(container, teamInfo);
    }


    /**
     * This function uses JFileChooser to allow the user to select a file.
     * This function will set a variable, returnFile, to either a selected
     * file or null if not selected and return it
     *
     * @param void
     * @return File
     */
    private File selectFile() {
        File returnFile = null;

        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV files", "csv");
        fileChooser.setFileFilter(filter);
        int returnVal = fileChooser.showOpenDialog(container);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            returnFile = fileChooser.getSelectedFile();
        }
        else {
            returnFile = null;
        }
        return returnFile;
    }


    /** validates that the given File is a proper csv roster file
     *
     * @param File
     * @return boolean (true if valid, false if not)
     */
    private boolean isValidRosterFile(File givenFile) {
        boolean isValid = true;
        String row;
        try (BufferedReader filereader = new BufferedReader(new FileReader(givenFile))) {
            while ((row = filereader.readLine()) != null) {
                int numCols = 0;
                for (int c = 0; c < row.length(); c++) {
                    if (row.charAt(c) == ',') 
                        ++numCols;
                }
                if (numCols != 5) {
                    isValid = false;
                    break;
                }
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isValid;
    }


    /** validates that the given File is a proper csv attendance file
     *
     * @param File
     * @return boolean (true if valid, false if not)
     */
    private boolean isValidAttendanceFile(File givenFile) {
        boolean isValid = true;
        String row;
        try (BufferedReader filereader = new BufferedReader(new FileReader(givenFile))) {
            while ((row = filereader.readLine()) != null) {
                int numCols = 0;
                for (int c = 0; c < row.length(); c++) {
                    if (row.charAt(c) == ',') 
                        ++numCols;
                }
                if (numCols != 1) {
                    isValid = false;
                    break;
                }
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isValid;
    }


    /**
     * Accessor method - returns attendance File
     *
     * @param void
     * @return File
     */
    public File getAttendance() {
        return attendance;
    }


    /**
     * Accessor method - returns roster File
     *
     * @param void
     * @return File
     */
    public File getRoster() {
        return roster;
    }


    /**
     * Accessor method - returns roster File
     *
     * @param void
     * @return File
     */
    public File getSaveFile() {
        return saveFile;
    }


    /**
     * Accessor method - returns date (as an integer)
     *
     * @param void
     * @return int
     */
    public int getDate() {
        return date;
    }


    /**
     * Accessor method - returns memChanged (status for event recently called)
     *
     * @param void
     * @return int
     */
    public int getMemChanged() {
        return memChanged;
    }
}
