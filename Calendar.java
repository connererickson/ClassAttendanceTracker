/**
* Authors:           Briggs Richardson, Conner Erickson, Kim Kieu Pham, 
*                    Valeriia Starosek 
* Class ID:          70605 , CSE360 Tues 9:00 AM 
* Assignment:        Final Project
* File Description:  This file contains the Calendar class, it is a customized
*                    JDialog box that contains GUI options for the user to 
*                    choose a date. It will assign the date chosen to a data
*                    member called date, which its accessor will be called
*                    upon by Source to obtain the date
*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * The Calendar class is a pop up JDialog that contains 3 JComboBox
 * drop down selections for the month, day, and year. The user will choose
 * their selections and click okay. From there, the date will be stored and
 * converted into an integer with the format yyyymmdd
 */
public class Calendar extends JDialog implements ActionListener {
     
    private static final String[] months = {"January", "February", "March",
        "April", "May", "June", "July", "August", "September", "October", 
        "November",  "December"};
    private static final String[] days = {"01", "02", "03", "04", "05", "06", "07",
        "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19",
        "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};
    private static final String[] years = {"2010", "2011", "2012", "2013",
        "2014", "2015", "2016", "2017", "2018", "2019", "2020", "2021", "2022",
        "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030"};

    private JButton okay;
    private int date;
    private JComboBox monthBox;
    private JComboBox dayBox;
    private JComboBox yearBox;

    /**
     * Constructor - Given its parent container, this sets up the
     * JDialog Box and adds its components to it for the user to 
     * select the date
     *
     * @param JFrame container
     * @return void
     */
    public Calendar(JFrame container) {
        super(container, "Date Picker", true);
        
        date = 20200101;
        monthBox = new JComboBox(months);
        monthBox.setSelectedItem(months[0]);
        dayBox = new JComboBox(days);
        dayBox.setSelectedItem(days[0]);
        yearBox = new JComboBox(years);
        yearBox.setSelectedItem(years[10]);

        JOptionPane optionPane = new JOptionPane("Please Select a Date",
                            JOptionPane.INFORMATION_MESSAGE,
                            JOptionPane.DEFAULT_OPTION,
                            null,
                            new Object[]{},
                            null);
        optionPane.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 10, 0, 10);

        optionPane.add(monthBox, gbc);
        optionPane.add(dayBox, gbc);
        optionPane.add(yearBox, gbc);

        okay = new JButton("ok");
        okay.addActionListener(this);
        optionPane.add(okay);

        setContentPane(optionPane);
        pack();
        setLocationRelativeTo(container);
        setVisible(true);
    }


    /**
     * Accessor - returns the date
     *
     * @param void
     * @return int
     */
    public int getDate() {
        return date;
    }


    /**
     * Converts a the parameters (integer month, strings day and year) to
     * an integer holding the date in the form yyyymmdd
     *
     * @param int month, String day, String year
     * @return int
     */
    public int convertStringDateToInt(int month, String day, String year) {
        int intDate = 0;

        int intDay = Integer.parseInt(day);
        int intYear = Integer.parseInt(year);
        
        intDate += intYear * 10000; // move yyyy four digits to the left
        intDate += month * 100;     // move mm two digits to the left
        intDate += intDay;          // add dd

        return intDate;
    }


    /**
     * actionPerformed, when the user selects the okay button, this
     * function will grab the values selected in the JComboBoxes and 
     * pass them into the converter function to obtain the date. It will
     * then dispose of the JDialog box
     *
     * @param ActionEvent event
     * @return void
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == okay) {
            String month = (String)monthBox.getSelectedItem();
            String day = (String)dayBox.getSelectedItem();
            String year = (String)yearBox.getSelectedItem();

            int monthNum = 0;
            for (int i = 0; i < 12; i++) {
                if (month.equals(months[i]))
                        monthNum = i+1; // initialize month accordingly
            }
            date = convertStringDateToInt(monthNum, day, year);
            
            dispose();
        }
    }

}
