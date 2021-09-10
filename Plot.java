/**
 * Authors:           Briggs Richardson, Conner Erickson, Kim Kieu Pham,
 *                    Valeriia Starosek
 * Class ID:          70605 , CSE360 Tues 9:00 AM
 * Assignment:        Final Project
 * File Description:  This file contains the Plot class, it creates a window with scatter plot
 *                    that visualizes student attendance data by date series.
 */
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This is a window with plotted attendance data
 *
 * @author Valeriia Starosek vstarose@asu.edu
 */
public class Plot extends JDialog {
    private List<ArrayList<Object>> attendance;

    public final static double TEN_PERCENT_ATTENDANCE = 75.0 / 10;

    /**
     * Creates Plot object, sets its size and location
     * Creates dataset, chart, adds chart panel, and makes the plot visible
     *
     * @param attendance This is the plot data
     * @param container This is the parent window
     */
    public Plot(List<ArrayList<Object>> attendance, JFrame container){
        super(container, "Data Plot", true);
        this.attendance = attendance;
        setSize(800, 600);
        setLocation(100, 100);

        XYDataset dataset = createDataset();

        JFreeChart chart = ChartFactory.createScatterPlot(
                "",
                "X-Axis", "Count", dataset);

        XYPlot plot = (XYPlot)chart.getPlot();
        plot.setBackgroundPaint(new Color(255,228,196));

        ChartPanel panel = new ChartPanel(chart);
        setContentPane(panel);

        setVisible(true);
    }

    /**
     * Converts raw attendance data to XYDataset
     *
     * @return Returns dataset for JFreeChart
     */
    private XYDataset createDataset() {
        XYSeriesCollection dataset = new XYSeriesCollection();

        if(attendance != null){
            for(ArrayList<Object> date: attendance){
                if(date != null){
                    XYSeries series = createSeries(date);
                    dataset.addSeries(series);
                }
            }
        }

        return dataset;
    }

    /**
     * Converts raw attendance data for one day to XYSeries
     *
     * @param date This is the day for which attendance is given
     * @return Returns series for one day
     */
     static XYSeries createSeries(List<Object> date){

        XYSeries series = new XYSeries((String)date.get(0));

//        bins that attendance data for one day is grouped into
        int tenPercent = 0;
        int twentyPercent = 0;
        int thirtyPercent = 0;
        int fortyPercent = 0;
        int fiftyPercent = 0;
        int sixtyPercent = 0;
        int seventyPercent = 0;
        int eightyPercent = 0;
        int ninetyPercent = 0;
        int hundredPercent = 0;

//        traverse attendance list and group attendance data into bins
        for(int student = 1; student < date.size(); student++){
            int studentsAttendance = (Integer)date.get(student);

            if(studentsAttendance > 0 && studentsAttendance <= TEN_PERCENT_ATTENDANCE){
                tenPercent++;
            }else if(studentsAttendance > TEN_PERCENT_ATTENDANCE && studentsAttendance <= TEN_PERCENT_ATTENDANCE * 2){
                twentyPercent++;
            }else if(studentsAttendance > TEN_PERCENT_ATTENDANCE * 2 && studentsAttendance <= TEN_PERCENT_ATTENDANCE * 3){
                thirtyPercent++;
            }else if(studentsAttendance > TEN_PERCENT_ATTENDANCE * 3 && studentsAttendance <= TEN_PERCENT_ATTENDANCE * 4){
                fortyPercent++;
            }else if(studentsAttendance > TEN_PERCENT_ATTENDANCE * 4 && studentsAttendance <= TEN_PERCENT_ATTENDANCE * 5){
                fiftyPercent++;
            }else if(studentsAttendance > TEN_PERCENT_ATTENDANCE * 5 && studentsAttendance <= TEN_PERCENT_ATTENDANCE * 6){
                sixtyPercent++;
            }else if(studentsAttendance > TEN_PERCENT_ATTENDANCE * 6 && studentsAttendance <= TEN_PERCENT_ATTENDANCE * 7){
                seventyPercent++;
            }else if(studentsAttendance > TEN_PERCENT_ATTENDANCE * 7 && studentsAttendance <= TEN_PERCENT_ATTENDANCE * 8){
                eightyPercent++;
            }else if(studentsAttendance > TEN_PERCENT_ATTENDANCE * 8 && studentsAttendance <= TEN_PERCENT_ATTENDANCE * 9){
                ninetyPercent++;
            }else if(studentsAttendance > TEN_PERCENT_ATTENDANCE * 9 && studentsAttendance <= TEN_PERCENT_ATTENDANCE * 10){
                hundredPercent++;
            }
        }

//        add data bins to the series
        series.add(10, tenPercent);
        series.add(20, twentyPercent);
        series.add(30, thirtyPercent);
        series.add(40, fortyPercent);
        series.add(50, fiftyPercent);
        series.add(60, sixtyPercent);
        series.add(70, seventyPercent);
        series.add(80, eightyPercent);
        series.add(90, ninetyPercent);
        series.add(100, hundredPercent);

        return series;
    }
}
