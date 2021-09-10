/**
* Authors:           Briggs Richardson, Conner Erickson, Kim Kieu Pham, 
*                    Valeriia Starosek 
* Class ID:          70605 , CSE360 Tues 9:00 AM 
* Assignment:        Final Project
* File Description:  This file contains the ContentPanel class, the JPanel
*                    on the JFrame that displays the Table. This customized
*                    JPanel is an observer, and responds to events in the GUI
*                    through the controller class. From there, it passes the
*                    necessary information to the model, table, and plot
*                    in order to display the appropriate information on the 
*                    panel
*/


import javax.swing.*;
import java.awt.Graphics;
import java.util.*;
import java.io.*;
import java.awt.GridLayout;
import java.awt.Dimension;


/**
 * ContentPanel is a JPanel that observes Source. It has relationships
 * with Roster, Plot, and Table in order to send the appropriate information
 * from the controller to these classes. It will then display the table
 * in its panel
 */
public class ContentPanel extends JPanel implements Observer {

    private RosterModel model;
    private Plot plot;
    private Table data;
    private JFrame container;


    /**
     * Constructor - initializes the model, sets the Panel's layout, and
     * sets the container for the panel to the JFrame
     *
     * @param JFrame container
     * @return void
     */
    public ContentPanel(JFrame container) {
        this.container = container;
        model = new RosterModel(container);
        setLayout(new GridLayout(1, 1));
    }


    /**
     * update - function called when the Observable (Source) notifies its
     * Observers (this). This function identifies the event that occurred
     * by getting the memChanged value from the Source, and then calls
     * the appropriate function to send data, retrieve data, and/or display
     * date to the panel
     *
     * @param Observable o (Source), Object arg
     * @return void
     */
    public void update(Observable o, Object arg) {
        Source src = (Source) o;
        int memChanged = src.getMemChanged();

        if (memChanged == Source.LOAD_ROSTER) {
            model.updateRoster(src.getRoster());
            createTable();
        }else if (memChanged == Source.LOAD_ATTENDANCE) {
            model.updateAttendance(src.getDate(), src.getAttendance());
            String[][] attendance = model.getAttendance();
            data.addAttendance(attendance[0][0], attendance[1]);
        }else if (memChanged == Source.SAVE) {
            data.saveContents(src.getSaveFile());
        }else if (memChanged == Source.PLOT_DATA) {
            plot = new Plot(model.getPlotAttendance(), container);
        }
        repaint();
    }


    /**
     * createTable initializes table headers, initializes the JTable,
     * and sets it up within a JScrollPane that will act as the only
     * component in the JPanel (taking up the entire size of the panel)
     *
     * @param void
     * @return void
     */
    public void createTable() {
        if (data == null) {
            String[] headers = {"ID", "First Name", "Last Name",
                "Program and Plan", "Academic Level", "ASURITE"};
            data = new Table(model.getRoster(), headers);
            //data.setFillsViewportHeight(true);
            JScrollPane scrollPane = new JScrollPane(data,
                    JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                    JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            add(scrollPane);
            validate();
        }

    }
}
