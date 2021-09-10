/* Authors: Kim Kieu Pham, Briggs Richardson, Conner Erickson, Valeriia Starosek
 * Class ID:          70605 , CSE360 Tues 9:00 AM 
 * CSE360 Final Project
 * File Description: This file contains the Table Class 
 * declaration and definitions. Table class uses to visualize the 
 * content of the data using Jtable once the user load the file of data.
 * Along with the content of the data, the user also can load 
 * the attendance as a file, then this class will generate and add once more
 *  column to represent the attendance with the date as the header.
 */
import java.awt.Component;
import java.util.*;
import javax.swing.*;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.io.*;
import java.awt.Dimension;

public class Table extends JTable {
    private DefaultTableModel tableModel;
    public JTable createTable;

    /* Table is a function that receive two parameter
     *  (once is for the header of the table 
     *  and other is for the list of information for each column)
     *  and generate a table showing the content of the file with the information
     *   from the file that user using the load roster file into system.
     *
     *@param string[][] data , String[] headers
     *@return void
     */
    public Table(String[][] data, String[] headers) {
        tableModel = new DefaultTableModel(data, headers);
        setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        setModel(tableModel);
        setColumnWidths();
    }

    /*isCellEditable function will use to avoid the user from access and making change to the table
     * 
     * @param int
     * @return boolean
     */
    public boolean isCellEditable(int data, int columns) {
        return false;
    }

    /*setColumnWithds is the function that use to adjust the size of each column in the table due 
     * to the result of turning of the auto_resize_off of the table to prevent 
     * from auto resize inside the JScrollPane.
     * 
     * @param void
     * @return void
     */
    private void setColumnWidths() {
        for (int col = 0; col < getColumnCount(); col++) {
            getColumnModel().getColumn(col).setPreferredWidth(131);
        }
    }

    /*addAttendance function is use to add one more columns, which represent
     *  the attendance of student at specific date is an string variable for
     *  header and newAttandence is the list of how may minute 
     *  the students has attended in the meeting.
     *  
     *  @param String date, String[] newAttendance
     *  @return void
     */
    public void addAttendance(String date, String[] newAttendance) {
        tableModel.addColumn(date, newAttendance);
        setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        setModel(tableModel);
        setColumnWidths();
    }

    /*SaveContents is the function that taking the saveTo parameter as 
     * the File type then read and write the content of the table 
     * into a Table.csv file in order to save the content of the file that 
     * loaded from the user as the table format which includes 
     * the headers of the table.
     * 
     * @param File
     * @return void
     */

    public void saveContents(File saveTo) {
        try {
            FileWriter myWriter;
            if (saveTo == null) {
                myWriter = new FileWriter("Table.csv");
            } else {
                myWriter = new FileWriter(saveTo);
            }
            // Write table headers
            for (int col = 0; col < getColumnCount(); col++) {
                myWriter.write(getColumnName(col).toString());
                if (col != getColumnCount() - 1) {
                    myWriter.write(",");
                }
            }
            myWriter.write("\n");

            // Write table data
            for (int row = 0; row < getRowCount(); row++) {
                for (int col = 0; col < getColumnCount(); col++) {
                    myWriter.write(getValueAt(row, col).toString());
                    if (col != getColumnCount() - 1) {
                        myWriter.write(",");
                    }
                }
                myWriter.write("\n");
            }
            myWriter.close();
        } catch (IOException e) {
            System.out.println("Error writing to file");
            e.printStackTrace();
        }
    }
}
