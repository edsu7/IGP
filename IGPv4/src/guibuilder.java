import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.border.EmptyBorder;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.ui.RefineryUtilities;

import com.toedter.calendar.JCalendar;

public class guibuilder extends JFrame {
    private JPanel contentPane;
    // private JTextField txtChoosDirectoryHere;
    String filepath;
    private ButtonGroup buttonGroupGraph = new ButtonGroup();
    public guibuilder() throws MalformedURLException {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1000, 550);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);

        // ImageIcon imgThisImg = new ImageIcon(PicURL));

        // jLabel2.setIcon(imgThisImg);

        JScrollPane scrollPane = new JScrollPane();
        contentPane.add(scrollPane, BorderLayout.CENTER);

        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        scrollPane.setViewportView(tabbedPane);

        JPanel Help = new JPanel();
        tabbedPane.addTab("Front", null, Help, null);
        SpringLayout sl_Help = new SpringLayout();
        Help.setLayout(sl_Help);

        JTextArea textAreaHelp = new JTextArea(
                "\t\t     Instructions On How To Operate\n "
                        + "\t\tthe SunPillar Graphical User Interface\n\n\n"
                        + "    There are four tabs located at the top to navigate through the various services provided\n"
                        + " by the GUI. The first tab is the 'Home' tab and it will contain instructions on how to use\n "
                        + " the GUI\n\n\n."
                        + "    The second tab is the 'Query Data' tab and it will allow the user to retrieve and display\n"
                        + " the specified data. The user must set the appropriate date by using the calendars on\n"
                        + " the left hand side. The first calendar is used to set the start date and the second \n"
                        + " calendar is used to set the end date. After selecting the dates the user must press \n"
                        + " the 'Display Results' button to to have the appropriate table appear on the right hand\n"
                        + " side. The user can then use the scrollbars that appear around the table to navigate  \n"
                        + " through the results. On this tab the user is also capable of selecting an appropriate \n"
                        + " data to enter data into the database. This file MUST be the  output file from the\n"
                        + " SunPillar weather machine. Below the output screen there are a list of radiobuttons\n"
                        + " the user can use to select specific columns that they would like to view.\n\n\n"
                        + "     The third tab is the 'Calculate Risk Index' tab and it will allow the user to\n"
                        + " determine the risk index associated with each day between the corresponding\n"
                        + " interval. To select the start date the top calendar on the left hand side\n"
                        + " must be used and to select the end date the bottom calendar on the left hand\n"
                        + " side must be used. Once the dates have been selected the user must press the\n"
                        + " 'Calculate' button to generate the risk index for the appropriate date.\n\n\n"
                        + "     The fourth tab is the 'Generate Graphs' tab which will display the appropriate\n"
                        + " data in a line graph. For example this will allow the user to visualize the results\n"
                        + " generated in the 'Calculate Risk Index' tab. To generate a graph the user must select\n"
                        + " the appropriate start and end dates. Once the dates have been selected the user must\n"
                        + " select the appropriate graph they wish to display by choosing one of the buttons in\n"
                        + " the bottom right corner. To generate a graph of the risk index the user would have to\n"
                        + " select the risk index option.\n\n\n"
                        + " \t\t What is a Risk Index?\n\n\n"
                        + "     The risk index that this program generates is an indication of how likely it is\n"
                        + " for crops to develop Powdery Mildew. A very high number, i.e anything over 70, indicates\n"
                        + " a high chance of developing Powdery Mildew. A low number indicates a very small chance of\n"
                        + " developing Powdery Mildew."

        );
        JScrollPane helpPane = new JScrollPane(textAreaHelp);
        sl_Help.putConstraint(SpringLayout.NORTH, helpPane, 38,
                SpringLayout.NORTH, Help);
        sl_Help.putConstraint(SpringLayout.WEST, helpPane, 10,
                SpringLayout.WEST, Help);
        sl_Help.putConstraint(SpringLayout.SOUTH, helpPane, -10,
                SpringLayout.SOUTH, Help);
        sl_Help.putConstraint(SpringLayout.EAST, helpPane, 534,
                SpringLayout.WEST, Help);
        textAreaHelp.setEditable(false);
        Help.add(helpPane);
        URL url = new URL("file:img/logo.png");
        ImageIcon imgThisImg = new ImageIcon(url);
        JLabel logo = new JLabel(imgThisImg);
        sl_Help.putConstraint(SpringLayout.NORTH, logo, 160, SpringLayout.NORTH,
                Help);
        sl_Help.putConstraint(SpringLayout.WEST, logo, 105, SpringLayout.EAST,
                textAreaHelp);
        // logo.setIcon(imgThisImg);
        Help.add(logo);

        JLabel title = new JLabel("Sun Pillar Powdery Mildew Risk Index");
        sl_Help.putConstraint(SpringLayout.NORTH, title, 10, SpringLayout.NORTH,
                Help);
        sl_Help.putConstraint(SpringLayout.WEST, title, 140, SpringLayout.WEST,
                Help);
        Help.add(title);

        JPanel Main = new JPanel();
        tabbedPane.addTab("Data", null, Main, null);
        SpringLayout sl_Main = new SpringLayout();
        Main.setLayout(sl_Main);

        JButton btnRetrieve = new JButton("Retrieve File");
        btnRetrieve.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                JButton open = new JButton();
                JFileChooser fc = new JFileChooser();
                fc.setCurrentDirectory(new java.io.File("."));
                fc.setDialogTitle("Select appropriate data file");
                if (fc.showOpenDialog(open) == JFileChooser.APPROVE_OPTION) {
                }
                filepath = fc.getSelectedFile().getAbsolutePath();
                CSVloader csv = new CSVloader();
                CSVloader.loadCSVFromFile(filepath);
                // database.insertData(rawData);
                // System.out.println(filepath);
            }
        });
        Main.add(btnRetrieve);

        JButton btnDataQuery = new JButton("Run");
        sl_Main.putConstraint(SpringLayout.NORTH, btnDataQuery, 0,
                SpringLayout.NORTH, btnRetrieve);
        Main.add(btnDataQuery);

        JCalendar calDataStartDate = new JCalendar();
        sl_Main.putConstraint(SpringLayout.WEST, btnRetrieve, 0,
                SpringLayout.WEST, calDataStartDate);
        sl_Main.putConstraint(SpringLayout.WEST, calDataStartDate, 10,
                SpringLayout.WEST, Main);
        sl_Main.putConstraint(SpringLayout.EAST, calDataStartDate, 200,
                SpringLayout.WEST, Main);
        Main.add(calDataStartDate);

        JLabel start = new JLabel("start date");
        sl_Main.putConstraint(SpringLayout.NORTH, calDataStartDate, 6,
                SpringLayout.SOUTH, start);
        sl_Main.putConstraint(SpringLayout.SOUTH, calDataStartDate, 159,
                SpringLayout.SOUTH, start);
        sl_Main.putConstraint(SpringLayout.NORTH, start, 10, SpringLayout.NORTH,
                Main);
        sl_Main.putConstraint(SpringLayout.WEST, start, 72, SpringLayout.WEST,
                Main);
        Main.add(start);

        JCalendar calDataEndDate = new JCalendar();
        sl_Main.putConstraint(SpringLayout.EAST, btnDataQuery, 0,
                SpringLayout.EAST, calDataEndDate);
        sl_Main.putConstraint(SpringLayout.NORTH, btnRetrieve, 6,
                SpringLayout.SOUTH, calDataEndDate);
        sl_Main.putConstraint(SpringLayout.WEST, calDataEndDate, 0,
                SpringLayout.WEST, calDataStartDate);
        Main.add(calDataEndDate);

        JLabel end = new JLabel("end date");
        sl_Main.putConstraint(SpringLayout.NORTH, calDataEndDate, 6,
                SpringLayout.SOUTH, end);
        sl_Main.putConstraint(SpringLayout.NORTH, end, 6, SpringLayout.SOUTH,
                calDataStartDate);
        sl_Main.putConstraint(SpringLayout.WEST, end, 82, SpringLayout.WEST,
                Main);
        Main.add(end);

        JScrollPane scrollPane_1 = new JScrollPane();
        sl_Main.putConstraint(SpringLayout.NORTH, scrollPane_1, 10,
                SpringLayout.NORTH, Main);
        sl_Main.putConstraint(SpringLayout.WEST, scrollPane_1, 6,
                SpringLayout.EAST, btnDataQuery);
        sl_Main.putConstraint(SpringLayout.SOUTH, scrollPane_1, 391,
                SpringLayout.NORTH, Main);
        sl_Main.putConstraint(SpringLayout.EAST, scrollPane_1, -10,
                SpringLayout.EAST, Main);
        Main.add(scrollPane_1);

        JTextArea textOutputData = new JTextArea();
        textOutputData.setEditable(false);
        scrollPane_1.setViewportView(textOutputData);

        JPanel RiskIndex = new JPanel();
        tabbedPane.addTab("RiskIndex", null, RiskIndex, null);
        SpringLayout sl_RiskIndex = new SpringLayout();
        RiskIndex.setLayout(sl_RiskIndex);

        JLabel label_1 = new JLabel("start date");
        sl_RiskIndex.putConstraint(SpringLayout.WEST, label_1, 83,
                SpringLayout.WEST, RiskIndex);
        RiskIndex.add(label_1);

        JCalendar calRiskStartDate = new JCalendar();
        sl_RiskIndex.putConstraint(SpringLayout.NORTH, calRiskStartDate, 30,
                SpringLayout.NORTH, RiskIndex);
        sl_RiskIndex.putConstraint(SpringLayout.SOUTH, label_1, -6,
                SpringLayout.NORTH, calRiskStartDate);
        sl_RiskIndex.putConstraint(SpringLayout.WEST, calRiskStartDate, 10,
                SpringLayout.WEST, RiskIndex);
        RiskIndex.add(calRiskStartDate);

        JLabel label_2 = new JLabel("end date");
        sl_RiskIndex.putConstraint(SpringLayout.NORTH, label_2, 6,
                SpringLayout.SOUTH, calRiskStartDate);
        sl_RiskIndex.putConstraint(SpringLayout.WEST, label_2, 0,
                SpringLayout.WEST, label_1);
        RiskIndex.add(label_2);

        JCalendar calRiskEndDate = new JCalendar();
        sl_RiskIndex.putConstraint(SpringLayout.NORTH, calRiskEndDate, 6,
                SpringLayout.SOUTH, label_2);
        sl_RiskIndex.putConstraint(SpringLayout.WEST, calRiskEndDate, 0,
                SpringLayout.WEST, calRiskStartDate);
        RiskIndex.add(calRiskEndDate);

        JButton btnRiskIndexQuery = new JButton("Run");
        sl_RiskIndex.putConstraint(SpringLayout.NORTH, btnRiskIndexQuery, 6,
                SpringLayout.SOUTH, calRiskEndDate);
        sl_RiskIndex.putConstraint(SpringLayout.EAST, btnRiskIndexQuery, 0,
                SpringLayout.EAST, calRiskStartDate);
        RiskIndex.add(btnRiskIndexQuery);

        JScrollPane scrollPane_2 = new JScrollPane();
        sl_RiskIndex.putConstraint(SpringLayout.NORTH, scrollPane_2, 0,
                SpringLayout.NORTH, label_1);
        sl_RiskIndex.putConstraint(SpringLayout.WEST, scrollPane_2, 6,
                SpringLayout.EAST, calRiskStartDate);
        sl_RiskIndex.putConstraint(SpringLayout.SOUTH, scrollPane_2, 0,
                SpringLayout.SOUTH, btnRiskIndexQuery);
        sl_RiskIndex.putConstraint(SpringLayout.EAST, scrollPane_2, -10,
                SpringLayout.EAST, RiskIndex);
        RiskIndex.add(scrollPane_2);

        JTextArea textOutputRiskIndex = new JTextArea();
        scrollPane_2.setViewportView(textOutputRiskIndex);
        textOutputRiskIndex.setEditable(false);

        JRadioButton rdbtnDataDailyRain = new JRadioButton("Daily Rain");
        sl_Main.putConstraint(SpringLayout.NORTH, rdbtnDataDailyRain, 18,
                SpringLayout.SOUTH, scrollPane_1);
        Main.add(rdbtnDataDailyRain);

        JRadioButton rdbtnDataHourlyRain = new JRadioButton("Hourly Rain");
        sl_Main.putConstraint(SpringLayout.NORTH, rdbtnDataHourlyRain, 0,
                SpringLayout.NORTH, rdbtnDataDailyRain);
        sl_Main.putConstraint(SpringLayout.WEST, rdbtnDataHourlyRain, 6,
                SpringLayout.EAST, rdbtnDataDailyRain);
        Main.add(rdbtnDataHourlyRain);

        JRadioButton rdbtnDataWindSpeed = new JRadioButton("Wind Speed");
        sl_Main.putConstraint(SpringLayout.NORTH, rdbtnDataWindSpeed, 0,
                SpringLayout.NORTH, rdbtnDataDailyRain);
        sl_Main.putConstraint(SpringLayout.WEST, rdbtnDataWindSpeed, 6,
                SpringLayout.EAST, rdbtnDataHourlyRain);
        Main.add(rdbtnDataWindSpeed);

        JRadioButton rdbtnDataHourlyGust = new JRadioButton("Hourly Gust");
        sl_Main.putConstraint(SpringLayout.NORTH, rdbtnDataHourlyGust, 0,
                SpringLayout.NORTH, rdbtnDataDailyRain);
        sl_Main.putConstraint(SpringLayout.WEST, rdbtnDataHourlyGust, 6,
                SpringLayout.EAST, rdbtnDataWindSpeed);
        Main.add(rdbtnDataHourlyGust);

        JRadioButton rdbtnDailyGust = new JRadioButton("Daily Gust");
        sl_Main.putConstraint(SpringLayout.NORTH, rdbtnDailyGust, 0,
                SpringLayout.NORTH, rdbtnDataDailyRain);
        sl_Main.putConstraint(SpringLayout.WEST, rdbtnDailyGust, 6,
                SpringLayout.EAST, rdbtnDataHourlyGust);
        Main.add(rdbtnDailyGust);

        JRadioButton rdbtnDataCwd = new JRadioButton("CWD");
        sl_Main.putConstraint(SpringLayout.NORTH, rdbtnDataCwd, 0,
                SpringLayout.NORTH, rdbtnDataDailyRain);
        sl_Main.putConstraint(SpringLayout.WEST, rdbtnDataCwd, 6,
                SpringLayout.EAST, rdbtnDailyGust);
        Main.add(rdbtnDataCwd);

        JRadioButton rdbtnDataPressure = new JRadioButton("Pressure");
        sl_Main.putConstraint(SpringLayout.NORTH, rdbtnDataPressure, 6,
                SpringLayout.SOUTH, rdbtnDataDailyRain);
        sl_Main.putConstraint(SpringLayout.WEST, rdbtnDataPressure, 0,
                SpringLayout.WEST, rdbtnDataDailyRain);
        Main.add(rdbtnDataPressure);

        JRadioButton rdbtnDataHumidity = new JRadioButton("Humidity");
        sl_Main.putConstraint(SpringLayout.NORTH, rdbtnDataHumidity, 0,
                SpringLayout.NORTH, rdbtnDataPressure);
        sl_Main.putConstraint(SpringLayout.WEST, rdbtnDataHumidity, 0,
                SpringLayout.WEST, rdbtnDataHourlyRain);
        Main.add(rdbtnDataHumidity);

        JRadioButton rdbtnDataTemperature = new JRadioButton("Temperature");
        sl_Main.putConstraint(SpringLayout.NORTH, rdbtnDataTemperature, 0,
                SpringLayout.NORTH, rdbtnDataPressure);
        sl_Main.putConstraint(SpringLayout.WEST, rdbtnDataTemperature, 0,
                SpringLayout.WEST, rdbtnDataWindSpeed);
        Main.add(rdbtnDataTemperature);

        JRadioButton rdbtnDataHmt = new JRadioButton("HMT");
        sl_Main.putConstraint(SpringLayout.NORTH, rdbtnDataHmt, 0,
                SpringLayout.NORTH, rdbtnDataPressure);
        sl_Main.putConstraint(SpringLayout.WEST, rdbtnDataHmt, 0,
                SpringLayout.WEST, rdbtnDataCwd);
        Main.add(rdbtnDataHmt);

        JRadioButton rdbtnDataLattitude = new JRadioButton("Latitude");
        sl_Main.putConstraint(SpringLayout.NORTH, rdbtnDataLattitude, 6,
                SpringLayout.SOUTH, rdbtnDataHourlyGust);
        sl_Main.putConstraint(SpringLayout.WEST, rdbtnDataLattitude, 0,
                SpringLayout.WEST, rdbtnDataHourlyGust);
        Main.add(rdbtnDataLattitude);

        JRadioButton rdbtnDataLongitude = new JRadioButton("Longitude");
        sl_Main.putConstraint(SpringLayout.NORTH, rdbtnDataLongitude, 6,
                SpringLayout.SOUTH, rdbtnDataHourlyGust);
        sl_Main.putConstraint(SpringLayout.WEST, rdbtnDataLongitude, 0,
                SpringLayout.WEST, rdbtnDailyGust);
        Main.add(rdbtnDataLongitude);

        JRadioButton rdbtnDataLt = new JRadioButton("LT");
        sl_Main.putConstraint(SpringLayout.NORTH, rdbtnDataLt, 6,
                SpringLayout.SOUTH, rdbtnDataLongitude);
        sl_Main.putConstraint(SpringLayout.WEST, rdbtnDataLt, 0,
                SpringLayout.WEST, rdbtnDataLongitude);
        Main.add(rdbtnDataLt);

        JCheckBox chckbxDataSelectAll = new JCheckBox("Select All");
        sl_Main.putConstraint(SpringLayout.WEST, rdbtnDataDailyRain, 6,
                SpringLayout.EAST, chckbxDataSelectAll);
        sl_Main.putConstraint(SpringLayout.NORTH, chckbxDataSelectAll, 18,
                SpringLayout.SOUTH, scrollPane_1);
        sl_Main.putConstraint(SpringLayout.WEST, chckbxDataSelectAll, 50,
                SpringLayout.WEST, scrollPane_1);
        Main.add(chckbxDataSelectAll);
        chckbxDataSelectAll.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (chckbxDataSelectAll.isSelected()) {
                    rdbtnDataDailyRain.setSelected(true);
                    rdbtnDataHourlyRain.setSelected(true);
                    rdbtnDataWindSpeed.setSelected(true);
                    rdbtnDataHourlyGust.setSelected(true);
                    rdbtnDailyGust.setSelected(true);
                    rdbtnDataCwd.setSelected(true);
                    rdbtnDataPressure.setSelected(true);
                    rdbtnDataTemperature.setSelected(true);
                    rdbtnDataHmt.setSelected(true);
                    rdbtnDataLattitude.setSelected(true);
                    rdbtnDataLongitude.setSelected(true);
                    rdbtnDataHumidity.setSelected(true);
                    rdbtnDataLt.setSelected(true);
                } else {
                    rdbtnDataDailyRain.setSelected(false);
                    rdbtnDataHourlyRain.setSelected(false);
                    rdbtnDataWindSpeed.setSelected(false);
                    rdbtnDataHourlyGust.setSelected(false);
                    rdbtnDailyGust.setSelected(false);
                    rdbtnDataCwd.setSelected(false);
                    rdbtnDataPressure.setSelected(false);
                    rdbtnDataTemperature.setSelected(false);
                    rdbtnDataHmt.setSelected(false);
                    rdbtnDataLattitude.setSelected(false);
                    rdbtnDataLongitude.setSelected(false);
                    rdbtnDataHumidity.setSelected(false);
                    rdbtnDataLt.setSelected(false);
                }
            }
        });

        JPanel Graphs = new JPanel();
        tabbedPane.addTab("Graphs", null, Graphs, null);
        SpringLayout sl_Graphs = new SpringLayout();
        Graphs.setLayout(sl_Graphs);

        JLabel GraphStartlabel = new JLabel("start date");
        sl_Graphs.putConstraint(SpringLayout.NORTH, GraphStartlabel, 10,
                SpringLayout.NORTH, Graphs);
        sl_Graphs.putConstraint(SpringLayout.WEST, GraphStartlabel, 79,
                SpringLayout.WEST, Graphs);
        Graphs.add(GraphStartlabel);

        JLabel GraphEndLabel = new JLabel("end date");
        sl_Graphs.putConstraint(SpringLayout.EAST, GraphEndLabel, 0,
                SpringLayout.EAST, GraphStartlabel);
        Graphs.add(GraphEndLabel);

        JCalendar GraphStartCalendar = new JCalendar();
        sl_Graphs.putConstraint(SpringLayout.WEST, GraphStartCalendar, 10,
                SpringLayout.WEST, Graphs);
        sl_Graphs.putConstraint(SpringLayout.NORTH, GraphEndLabel, 6,
                SpringLayout.SOUTH, GraphStartCalendar);
        sl_Graphs.putConstraint(SpringLayout.NORTH, GraphStartCalendar, 6,
                SpringLayout.SOUTH, GraphStartlabel);
        Graphs.add(GraphStartCalendar);

        JCalendar GraphEndCalendar = new JCalendar();
        sl_Graphs.putConstraint(SpringLayout.NORTH, GraphEndCalendar, 6,
                SpringLayout.SOUTH, GraphEndLabel);
        sl_Graphs.putConstraint(SpringLayout.EAST, GraphEndCalendar, 0,
                SpringLayout.EAST, GraphStartCalendar);
        Graphs.add(GraphEndCalendar);

        JButton btnGraphRun = new JButton("Run");
        sl_Graphs.putConstraint(SpringLayout.NORTH, btnGraphRun, 6,
                SpringLayout.SOUTH, GraphEndCalendar);
        sl_Graphs.putConstraint(SpringLayout.EAST, btnGraphRun, 0,
                SpringLayout.EAST, GraphStartCalendar);
        Graphs.add(btnGraphRun);

        JScrollPane GraphScrollPane_3 = new JScrollPane();
        sl_Graphs.putConstraint(SpringLayout.NORTH, GraphScrollPane_3, 10,
                SpringLayout.NORTH, Graphs);
        sl_Graphs.putConstraint(SpringLayout.WEST, GraphScrollPane_3, 6,
                SpringLayout.EAST, GraphStartCalendar);
        sl_Graphs.putConstraint(SpringLayout.SOUTH, GraphScrollPane_3, 391,
                SpringLayout.NORTH, Graphs);
        sl_Graphs.putConstraint(SpringLayout.EAST, GraphScrollPane_3, -10,
                SpringLayout.EAST, Graphs);
        Graphs.add(GraphScrollPane_3);

        JRadioButton rdbtnGraphDailyRain = new JRadioButton("Daily Rain");
        sl_Graphs.putConstraint(SpringLayout.NORTH, rdbtnGraphDailyRain, 18,
                SpringLayout.SOUTH, GraphScrollPane_3);
        sl_Graphs.putConstraint(SpringLayout.EAST, rdbtnGraphDailyRain, 150,
                SpringLayout.EAST, GraphStartCalendar);
        Graphs.add(rdbtnGraphDailyRain);
        buttonGroupGraph.add(rdbtnGraphDailyRain);
        rdbtnGraphDailyRain.setSelected(true);

        JRadioButton rdbtnGraphHourlyRain = new JRadioButton("Hourly Rain");
        sl_Graphs.putConstraint(SpringLayout.NORTH, rdbtnGraphHourlyRain, 0,
                SpringLayout.NORTH, rdbtnGraphDailyRain);
        sl_Graphs.putConstraint(SpringLayout.WEST, rdbtnGraphHourlyRain, 6,
                SpringLayout.EAST, rdbtnGraphDailyRain);
        Graphs.add(rdbtnGraphHourlyRain);
        buttonGroupGraph.add(rdbtnGraphHourlyRain);

        JRadioButton rdbtnGraphWindSpeed = new JRadioButton("Wind Speed");
        sl_Graphs.putConstraint(SpringLayout.NORTH, rdbtnGraphWindSpeed, 0,
                SpringLayout.NORTH, rdbtnGraphDailyRain);
        sl_Graphs.putConstraint(SpringLayout.WEST, rdbtnGraphWindSpeed, 6,
                SpringLayout.EAST, rdbtnGraphHourlyRain);
        Graphs.add(rdbtnGraphWindSpeed);
        buttonGroupGraph.add(rdbtnGraphWindSpeed);

        JRadioButton rdbtnGraphHourlyGust = new JRadioButton("Hourly Gust");
        sl_Graphs.putConstraint(SpringLayout.NORTH, rdbtnGraphHourlyGust, 0,
                SpringLayout.NORTH, rdbtnGraphDailyRain);
        sl_Graphs.putConstraint(SpringLayout.WEST, rdbtnGraphHourlyGust, 6,
                SpringLayout.EAST, rdbtnGraphWindSpeed);
        Graphs.add(rdbtnGraphHourlyGust);
        buttonGroupGraph.add(rdbtnGraphHourlyGust);

        JRadioButton rdbtnGraphDailyGust = new JRadioButton("Daily Gust");
        sl_Graphs.putConstraint(SpringLayout.NORTH, rdbtnGraphDailyGust, 0,
                SpringLayout.NORTH, rdbtnGraphDailyRain);
        sl_Graphs.putConstraint(SpringLayout.WEST, rdbtnGraphDailyGust, 6,
                SpringLayout.EAST, rdbtnGraphHourlyGust);
        Graphs.add(rdbtnGraphDailyGust);
        buttonGroupGraph.add(rdbtnGraphDailyGust);

        JRadioButton rdbtnGraphCwd = new JRadioButton("CWD");
        sl_Graphs.putConstraint(SpringLayout.NORTH, rdbtnGraphCwd, 0,
                SpringLayout.NORTH, rdbtnGraphDailyRain);
        sl_Graphs.putConstraint(SpringLayout.WEST, rdbtnGraphCwd, 6,
                SpringLayout.EAST, rdbtnGraphDailyGust);
        Graphs.add(rdbtnGraphCwd);
        buttonGroupGraph.add(rdbtnGraphCwd);

        JRadioButton rdbtnGraphIndex = new JRadioButton("Risk Index");
        sl_Graphs.putConstraint(SpringLayout.NORTH, rdbtnGraphIndex, 0,
                SpringLayout.NORTH, rdbtnGraphCwd);
        sl_Graphs.putConstraint(SpringLayout.WEST, rdbtnGraphIndex, 6,
                SpringLayout.EAST, rdbtnGraphCwd);
        Graphs.add(rdbtnGraphIndex);
        buttonGroupGraph.add(rdbtnGraphIndex);

        JRadioButton rdbtnGraphPressure = new JRadioButton("Pressure");
        sl_Graphs.putConstraint(SpringLayout.NORTH, rdbtnGraphPressure, 6,
                SpringLayout.SOUTH, rdbtnGraphDailyRain);
        sl_Graphs.putConstraint(SpringLayout.WEST, rdbtnGraphPressure, 0,
                SpringLayout.WEST, rdbtnGraphDailyRain);
        Graphs.add(rdbtnGraphPressure);
        buttonGroupGraph.add(rdbtnGraphPressure);

        JRadioButton rdbtnGraphHumidity = new JRadioButton("Humidity");
        sl_Graphs.putConstraint(SpringLayout.NORTH, rdbtnGraphHumidity, 0,
                SpringLayout.NORTH, rdbtnGraphPressure);
        sl_Graphs.putConstraint(SpringLayout.WEST, rdbtnGraphHumidity, 0,
                SpringLayout.WEST, rdbtnGraphHourlyRain);
        Graphs.add(rdbtnGraphHumidity);
        buttonGroupGraph.add(rdbtnGraphHumidity);

        JRadioButton rdbtnGraphTemperature = new JRadioButton("Temperature");
        sl_Graphs.putConstraint(SpringLayout.NORTH, rdbtnGraphTemperature, 0,
                SpringLayout.NORTH, rdbtnGraphPressure);
        sl_Graphs.putConstraint(SpringLayout.WEST, rdbtnGraphTemperature, 0,
                SpringLayout.WEST, rdbtnGraphWindSpeed);
        Graphs.add(rdbtnGraphTemperature);
        buttonGroupGraph.add(rdbtnGraphTemperature);

        JRadioButton rdbtnGraphHmt = new JRadioButton("HMT");
        sl_Graphs.putConstraint(SpringLayout.NORTH, rdbtnGraphHmt, 0,
                SpringLayout.NORTH, rdbtnGraphPressure);
        sl_Graphs.putConstraint(SpringLayout.WEST, rdbtnGraphHmt, 0,
                SpringLayout.WEST, rdbtnGraphCwd);
        Graphs.add(rdbtnGraphHmt);
        buttonGroupGraph.add(rdbtnGraphHmt);

        JRadioButton rdbtnGraphLattitude = new JRadioButton("Latitude");
        sl_Graphs.putConstraint(SpringLayout.NORTH, rdbtnGraphLattitude, 6,
                SpringLayout.SOUTH, rdbtnGraphHourlyGust);
        sl_Graphs.putConstraint(SpringLayout.WEST, rdbtnGraphLattitude, 0,
                SpringLayout.WEST, rdbtnGraphHourlyGust);
        Graphs.add(rdbtnGraphLattitude);
        buttonGroupGraph.add(rdbtnGraphLattitude);

        JRadioButton rdbtnGraphLongitude = new JRadioButton("Longitude");
        sl_Graphs.putConstraint(SpringLayout.NORTH, rdbtnGraphLongitude, 6,
                SpringLayout.SOUTH, rdbtnGraphHourlyGust);
        sl_Graphs.putConstraint(SpringLayout.WEST, rdbtnGraphLongitude, 0,
                SpringLayout.WEST, rdbtnGraphDailyGust);
        Graphs.add(rdbtnGraphLongitude);
        buttonGroupGraph.add(rdbtnGraphLongitude);

        JRadioButton rdbtnGraphLt = new JRadioButton("LT");
        sl_Graphs.putConstraint(SpringLayout.NORTH, rdbtnGraphLt, 0,
                SpringLayout.NORTH, rdbtnGraphPressure);
        sl_Graphs.putConstraint(SpringLayout.WEST, rdbtnGraphLt, 0,
                SpringLayout.WEST, rdbtnGraphIndex);
        Graphs.add(rdbtnGraphLt);
        buttonGroupGraph.add(rdbtnGraphLt);

        JRadioButton[] selectedDataElements = {rdbtnDataDailyRain,
                rdbtnDataHourlyRain, rdbtnDataWindSpeed, rdbtnDataHourlyGust,
                rdbtnDailyGust, rdbtnDataCwd, rdbtnDataPressure,
                rdbtnDataHumidity, rdbtnDataTemperature, rdbtnDataHmt,
                rdbtnDataLt, rdbtnDataLongitude, rdbtnDataLongitude};
        JRadioButton[] selectedGraphElements = {rdbtnGraphDailyRain,
                rdbtnGraphHourlyRain, rdbtnGraphWindSpeed, rdbtnGraphHourlyGust,
                rdbtnDailyGust, rdbtnGraphCwd, rdbtnGraphPressure,
                rdbtnGraphHumidity, rdbtnGraphTemperature, rdbtnGraphHmt,
                rdbtnGraphLt, rdbtnGraphLongitude, rdbtnGraphLongitude};
        int[] selectedNumbers = {2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14};
        String[] selectedNames = {"dailyrn", "hourlyrn", "ws", "hourgust",
                "daygust", "cwd", "kpa", "hmd", "tmp", "hmt", "lt", "lat",
                "lon"};

        btnGraphRun.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Date dateStart = GraphStartCalendar.getDate();
                Date dateEnd = GraphEndCalendar.getDate();
                if (dateStart.after(dateEnd)) {
                    JOptionPane pane = new JOptionPane();
                    pane.setMessage("Invalid dates selected");
                    JDialog d = pane.createDialog(null, "Date Error");
                    d.setVisible(true);
                } else {
                    Database database = new Database();
                    String title = "Risk Index";
                    String yaxis = "Risk Index";
                    String xaxis = "Time Stamps";
                    Integer[] returnValue = new Integer[2];
                    returnValue[0] = 28;

                    if (rdbtnGraphIndex.isSelected()) {

                    } else {
                        for (int i = 0; i < selectedGraphElements.length
                                - 1; i++) {
                            if (selectedGraphElements[i].isSelected()) {
                                title = selectedNames[i];
                                yaxis = selectedNames[i];
                                returnValue[1] = selectedNumbers[i];
                                System.out.println(title + "we found a winner");
                            }
                        }
                    }
                    // textOutputData.setText(database.printData(dateStart,
                    // dateEnd));
                    /*
                     * JTable tableDataOutput = new
                     * JTable(database.printData(dateStart,
                     * dateEnd),database.getColNames());
                     * scrollPane_1.setViewportView(tableDataOutput);
                     * tableDataOutput.setEnabled(false);
                     */
                    JFreeChart lineChart = ChartFactory.createLineChart(title,
                            xaxis, yaxis + " value",
                            database.createDataset(dateStart, dateEnd, yaxis,
                                    returnValue),
                            PlotOrientation.VERTICAL, true, true, false);
                    ChartPanel chartPanel = new ChartPanel(lineChart);
                    chartPanel
                            .setPreferredSize(new java.awt.Dimension(560, 367));
                    GraphScrollPane_3.setViewportView(chartPanel);
                }
            }
        });

        final JTable tableRiskOutput;
        final JTable tableDataOutput;

        btnRiskIndexQuery.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Date dateStart = calRiskStartDate.getDate();
                Date dateEnd = calRiskEndDate.getDate();
                RiskIndex index = new RiskIndex();
                if (dateStart.after(dateEnd)) {
                    JOptionPane pane = new JOptionPane();
                    pane.setMessage("Invalid dates selected");
                    JDialog d = pane.createDialog(null, "Date Error");
                    d.setVisible(true);
                } else {
                    JTable tableRiskOutput = new JTable(index.calculateIndex(
                            index.getBlaeser(dateStart, dateEnd), dateEnd),
                            index.getColNames());;
                    scrollPane_2.setViewportView(tableRiskOutput);
                    tableRiskOutput.setEnabled(false);
                    /*
                     * tableOutput = new
                     * JTable(database.printData(selected),database.getColNames(
                     * selected));
                     * scrollPaneTextOutput1a3.setViewportView(tableOutput);
                     */
                }
                // Object[][] joe =index.getData(dateStart, dateEnd);
                /*
                 * { textOutputRiskIndex.setText(index.calculateIndex(dateStart,
                 * dateEnd)); } catch (ParseException e1) { // TODO
                 * Auto-generated catch block e1.printStackTrace(); }
                 */
            }
        });

        btnDataQuery.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Date dateStart = calDataStartDate.getDate();
                Date dateEnd = calDataEndDate.getDate();
                if (dateStart.after(dateEnd)) {
                    JOptionPane pane = new JOptionPane();
                    pane.setMessage("Invalid dates selected");
                    JDialog d = pane.createDialog(null, "Date Error");
                    d.setVisible(true);
                } else {
                    Database database = new Database();
                    database.connectDatabase();
                    List<Integer> chosenNumbers = new ArrayList<Integer>();
                    chosenNumbers.add(28);
                    List<String> chosenNames = new ArrayList<String>();
                    chosenNames.add("TimeStamp");
                    for (int i = 0; i < selectedDataElements.length - 1; i++) {
                        if (selectedDataElements[i].isSelected()) {
                            chosenNumbers.add(selectedNumbers[i]);
                            chosenNames.add(selectedNames[i]);
                        }
                    }
                    Integer[] numberArguements = new Integer[chosenNumbers
                            .size()];
                    chosenNumbers.toArray(numberArguements);
                    String[] nameArguements = new String[chosenNames.size()];
                    chosenNames.toArray(nameArguements);
                    // textOutputData.setText(database.printData(dateStart,
                    // dateEnd));
                    JTable tableDataOutput = new JTable(database
                            .printData(dateStart, dateEnd, numberArguements),
                            nameArguements);
                    scrollPane_1.setViewportView(tableDataOutput);
                    tableDataOutput.setEnabled(false);
                }
            }
        });

    }
}
