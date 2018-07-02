/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calenderapp;

import java.util.*;
import java.awt.Container;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Alec_Stasinski
 */
public class CalenderApp {

   
    static JLabel lblMonth, lblYear;
    static JButton btnPrev, btnNext;
    static JTable tblCalendar;
    static JComboBox cmbYear;
    static JFrame frmMain;
    static Container pane;
    static DefaultTableModel mtblCalendar; //Table model
    static JScrollPane stblCalendar; //The scrollpane
    static JPanel pnlCalendar; //The panel
    static int realDay, realMonth, realYear, currentMonth, currentYear;
    
    public static void main(String[] args) {
        // TODO code application logic here
        
        try {UIManager.setLookAndFeel
        (UIManager.getSystemLookAndFeelClassName());}
        catch (ClassNotFoundException e) {}
        catch (InstantiationException e) {}
        catch (IllegalAccessException e) {}
        catch (UnsupportedLookAndFeelException e) {}
        
        lblMonth = new JLabel ("January");
        frmMain = new JFrame("Calender application");
        lblYear = new JLabel ("Change Year");
        cmbYear = new JComboBox();
        btnPrev = new JButton("<<");
        btnNext = new JButton(">>");
        mtblCalendar = new DefaultTableModel();
        tblCalendar = new JTable(mtblCalendar); //table of above model
        stblCalendar = new JScrollPane(tblCalendar); //scrollpane for above 
        //table
        pnlCalendar = new JPanel(null); //create the panel
        
        frmMain.setSize(400, 400); //width and height
        pane = frmMain.getContentPane();
        pane.setLayout(null);
        
        frmMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pnlCalendar.setBorder(BorderFactory.createTitledBorder("Calendar"));
        
        //add all the controls
        pane.add(pnlCalendar);
        pnlCalendar.add(lblMonth);
        pnlCalendar.add(lblYear);
        pnlCalendar.add(cmbYear);
        pnlCalendar.add(btnPrev);
        pnlCalendar.add(btnNext);
        pnlCalendar.add(stblCalendar); //by adding the scrollpane we 
        //automatically added the table contane and also the model
        
        //set bounds for all the controls utilizing the set bound x y width
        //height
        pnlCalendar.setBounds(0,0,320,335);
        //get preferred sie of a omponent to be allugned with the calendar
        lblMonth.setBounds(160-lblMonth.getPreferredSize().width/2,25,100,25);
        lblYear.setBounds(10,305,80,20);
        cmbYear.setBounds(230,25,50,25);
        btnPrev.setBounds(10,25,50,25);
        btnNext.setBounds(260,25,50,25);
        stblCalendar.setBounds(10,50,300,250);
        
        frmMain.setResizable(true); //not resizable
        frmMain.setVisible(true); //frame visible
        
        //get real month/year
        GregorianCalendar cal = new GregorianCalendar();
        realDay = cal.get(GregorianCalendar.DAY_OF_MONTH);
        realMonth = cal.get(GregorianCalendar.MONTH);
        realYear = cal.get(GregorianCalendar.YEAR);
        currentMonth = realMonth;
        currentYear = realYear;
        
        //add headers
        String [] headers = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        for (int i = 0; i<7; i++)
        {
            mtblCalendar.addColumn(headers[i]); //alk elements as column headers
        }
        
        //gives a white background to the calendar
        tblCalendar.getParent().setBackground(tblCalendar.getBackground());
        
        //not allow rezing of the header or reordering
        tblCalendar.getTableHeader().setResizingAllowed(false);
        tblCalendar.getTableHeader().setReorderingAllowed(false);
        
        //be able to select one cell at a time 
        tblCalendar.setColumnSelectionAllowed(true);
        tblCalendar.setRowSelectionAllowed(true);
        tblCalendar.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        //creates 7x6 calendar
        tblCalendar.setRowHeight(38);
        mtblCalendar.setColumnCount(7);
        mtblCalendar.setRowCount(6);
        
        //populating the combp boxfrom 100 years in the past to 100
        //years in the future
        for(int i = realYear - 100; i <= realYear + 100; i++)
        {
            cmbYear.addItem(String.valueOf(i));
        }
        
        refreshCalendar(realMonth, realYear); //calls refresh calendar
        
        }//end of class
    
    public static void refreshCalendar(int month, int year)
    {
        String [] months = {"January", "February", "March", "April", "May",
            "June", "July", "August", "September", "October", "November",
            "December"};
        int nod, som; //number of days, start of month
        
        //enable months
        btnPrev.setEnabled(true);
        btnNext.setEnabled(true);
        
        if(month == 0 && year <= realYear-10)
        {
            btnPrev.setEnabled(false); //too early
        }
        if(month == 11 && year >= realYear + 100)
        {
            btnNext.setEnabled(false); //too late
        }
    
        lblMonth.setText(months[month]); //refresh the month lable at top
        lblMonth.setBounds(160-lblMonth.getPreferredSize().width/2,
                25, 180, 25); //re-align table with calendar
        cmbYear.setSelectedItem(String.valueOf(year));
        //select the correct year in the combo box
        
        //get first day of month and number of days
        GregorianCalendar cal = new GregorianCalendar(year, month, 1);
        nod = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
        som = cal.get(GregorianCalendar.DAY_OF_WEEK);
        
        //clear table cause refresh calendar is called more then once
        for (int i = 0; i<6; i++)
        {
            for (int j = 0; j<7; j++)
            {
                mtblCalendar.setValueAt(null, i, j);
            }
        }
        
        //draw the calendar
        for (int i = 0; i <= nod; i++)
        {
            int row = new Integer((i+som-2)/7);
            int column = (i+som-2)%7;
            mtblCalendar.setValueAt(i, row, column);
        }
        
    }
    
}
