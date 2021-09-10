/**
* Authors:           Briggs Richardson, Conner Erickson, Kim Kieu Pham, 
*                    Valeriia Starosek 
* Class ID:          70605 , CSE360 Tues 9:00 AM 
* Assignment:        Final Project
* File Description:  This file contains the World class, the main driver of
*                    the Project. World is a JFrame. It is the bottom-most 
*                    container of the GUI
*/

import javax.swing.*;
import java.awt.event.*;

/**
 * The World class is the JFrame window of the GUI. It contains all other
 * GUI components. It acts as the main driver of the program, initiating
 * the objects that will work together. It contains a JMenuBar that has
 * the functionality of the program, a ContentPanel for the view of the 
 * program, and a Source that will act as the controller between the model
 * and the view. Also, an actionlistener to respond to JMenuBar events
 */
public class World extends JFrame implements ActionListener {
    private Source source;
    private JButton about;
    private JMenuItem load;
    private JMenuItem addAttendance;
    private JMenuItem save;
    private JMenuItem plotData;
    private ContentPanel contentPanel;

    /**
     * Constructor - initializes the private data members and initiates
     * JFrame properties, and sets up and establishes relationships between data
     * members and functionalities
     *
     * @param void
     * @return void
     */
    public World() {
        /* Set JFrame properties */
        setTitle("CSE360 Final Project");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 800);

        /* Create and set up the JMenuBar */
        JMenuBar menuBar = new JMenuBar();
        JMenu file = new JMenu("File");
        
        load = new JMenuItem("Load a Roster");
        load.addActionListener(this);
        file.add(load);
        file.addSeparator();

        addAttendance = new JMenuItem("Add Attendance");
        addAttendance.addActionListener(this);
        file.add(addAttendance);
        file.addSeparator();

        save = new JMenuItem("Save");
        save.addActionListener(this);
        file.add(save);
        file.addSeparator();

        plotData = new JMenuItem("Plot Data");
        plotData.addActionListener(this);
        file.add(plotData);

        about = new JButton("About");
        about.addActionListener(this);
        about.setOpaque(true);
        about.setContentAreaFilled(false);
        about.setBorderPainted(false);
        about.setFocusable(false);

        menuBar.add(file);
        menuBar.add(about);

        setJMenuBar(menuBar);

        /* Set up observable observer relationships */
        source = new Source(this);
        ContentPanel contentPanel = new ContentPanel(this);
        source.addObserver(contentPanel);
        add(contentPanel);

        /* Set the entire JFrame and all of its components to visible */
        setVisible(true);
    }

    /**
     * The main function. This executes and creates an instance of World
     *
     * @param String[] args
     * @return void
     */
    public static void main (String[] args) {
        World myWorld = new World();
    }

    /**
     * ActionListener function that executes when an event occurs.
     * actionPerformed calls the appropriate function to respond to a
     * specific functionality button that is clicked on within the JMenuBar
     *
     * @param event: ActionEvent
     * @return void
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == load) {
            source.loadRoster();
        } else if (event.getSource() == addAttendance) {
            source.loadAttendance();
        } else if (event.getSource() == save) {
            source.save();
        } else if (event.getSource() == plotData) {
            source.plotPanel();
        } else { 
            source.about();
        }
    }
}
