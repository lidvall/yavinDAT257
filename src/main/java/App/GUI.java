package App;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.awt.*;
import java.util.List;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;


public class GUI {
    ParticipationMunicipality pm = new ParticipationMunicipality();
    TestPartier mp = new TestPartier();

    JFrame frame = new JFrame("App");
    private JPanel mainPanel;
    private JComboBox comboBox1;
    private JScrollPane scrollPanel;
    private JTable table1;
    private JTextField textField1;
    private JButton yearsButton;
    private JButton graphButton;

    private String[] options = {"Municipality", "Voter", "Parties"};
    private Object viewType;
    private String[] header;
    private String[][] tData;
    private int[] yearsShown = {2018, 2014, 2010}; //Initialized to the "standard" set of elections that are shown
    private String searchPhrase = "";


    private void pullDataMunicipality(){
        viewType = comboBox1.getItemAt(comboBox1.getSelectedIndex()); //This is only needed to intialize viewType, and is thus only needed in municipality as it's shwon first
        header = new String[yearsShown.length + 1];
        header[0] = "Municipalities";
        for(int i = 1; i < yearsShown.length+1; i++) {
            header[i] = Integer.toString(yearsShown[i-1]);
        }
        List<Municipality> municipalities;
        if (!textField1.getText().equals("")) {
            ParticipationMunicipality.Autocompleter autocompleter = new ParticipationMunicipality.Autocompleter(pm.getMunicipalities());
            municipalities = autocompleter.allMatches(searchPhrase);
        } else {
            municipalities = pm.getMunicipalities();
        }
        tData = new String[municipalities.size()][18];

        int i = 0;
        int z = 1;
        for(Municipality m : municipalities){
            tData[i][0] = m.getName();
            for(Integer year : yearsShown){
                tData[i][z] = Double.toString(m.getElectionParticipationByYear(year));
                z++;
            }
            i++;
            z = 1;
        }
    }

    private void pullDataVoter(){
        header = new String[4];
        tData = new String[2][5];
        header[0] = "Voter";
        header[1] = "2018";
        header[2] = "2014";
        header[3] = "2010";

        tData[0][0] = "Anders";
        tData[0][1] = "V";
        tData[0][2] = "MP";
        tData[0][3] = "M";

        tData[1][0] = "Gustav";
        tData[1][1] = "S";
        tData[1][2] = "M";
        tData[1][3] = "S";
    }

    private void pullDataParties() {
        header = new String[yearsShown.length + 1];
        header[0] = "Parties";
        for(int i = 1; i < yearsShown.length+1; i++) {
            header[i] = Integer.toString(yearsShown[i-1]);
        }

        tData = new String[mp.getParties().size()][18];

        int i = 0;
        int z = 1;
        for(Party party : mp.getParties()){
            tData[i][0] = party.getName();
            for(Integer year : yearsShown){
                if(Double.toString(party.getAggregateMandate(year)).equals("0.0")) {
                    continue;
                }
                tData[i][z] = Double.toString(party.getAggregateMandate(year));
                z++;
            }
            i++;
            z = 1;
        }
    }

    /**
     * Initialization of the GUI
     */
    public GUI(){
        for(String option : options){
            comboBox1.addItem(option);
        }

        pullDataMunicipality();

        createTable(tData, header);

        comboBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewType = comboBox1.getItemAt(comboBox1.getSelectedIndex());
                pullData();
            }
        });
        yearsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                YearSelect ys = new YearSelect("Select years");

            }
        });
        graphButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Grapher graph = new Grapher("Chart");
                graph.viewGraph();
            }
        });
        textField1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchPhrase = textField1.getText();
                pullData();
            }
        });
    }

    private void pullData(){
        switch(viewType.toString()){
            case "Municipality":
                pullDataMunicipality();
                break;
            case "Voter":
                pullDataVoter();
                break;
            case "Parties":
                pullDataParties();
                break;
        }
        createTable(tData, header);

        table1.setAutoCreateRowSorter(true);
        table1.repaint();
    }

    private void createTable(String[][] tableData, String[]headerData){
        table1.setModel(new DefaultTableModel(tableData,headerData));
    }

    private class YearSelect extends JFrame {
        JLabel label = new JLabel();

        private YearSelect(String title) throws HeadlessException {
            super(title);

            setBounds(150, 150, 200, 200);
            Container ControlHost = getContentPane();
            ControlHost.setLayout(new GridLayout(2,1));
            JPanel north = new JPanel();
            JPanel south = new JPanel();
            south.setLayout(new GridLayout(2,1));
            ControlHost.add(north);
            ControlHost.add(south);

            final String[] years = {"2018", "2014", "2010", "2006", "2002", "1998", "1994", "1991", "1988", "1985", "1982", "1979", "1976", "1973"};

            JList ListYears = new JList(years);
            ListYears.setVisibleRowCount(8);
            JScrollPane jcp = new JScrollPane(ListYears);
            jcp.setPreferredSize(new Dimension(300,170));
            north.add(jcp);
            label.setSize(100,100);
            south.add(label);
            ListYears.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            ListYears.addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent e) {
                    String strYears = "";
                    List SelectedYears = ListYears.getSelectedValuesList();

                    for (int i = 0; i < SelectedYears.size(); i++)
                        strYears = strYears + SelectedYears.get(i) + ", ";
                    strYears = strYears.substring(0, strYears.length() - 1);
                    label.setText(strYears);
                }
            });

            JButton jb = new JButton("Select Years");
            jb.setBackground(Color.WHITE);
            south.setPreferredSize(new Dimension(50,50));
            south.add(jb);


            this.pack();

            jb.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    List SelectedYears = ListYears.getSelectedValuesList();
                    int[] temp = new int[SelectedYears.size()];
                    for (int i = 0; i < SelectedYears.size(); i++) {
                        temp[i] = Integer.parseInt((String) SelectedYears.get(i));
                    }
                    setYearsShown(temp);
                    shutDownYearSelect();
                }});

            this.setSize(400,400);
            this.setLocationRelativeTo(null);
            this.setVisible(true);

        }

        private void shutDownYearSelect(){
            pullData();
            this.dispose();
        }
    }

    private void setYearsShown(int[] input){
        yearsShown = input;
    }

    public class Grapher extends JFrame {
        ParticipationMunicipality pm = new ParticipationMunicipality();
        TestPartier mp = new TestPartier();

        //private final long serialVersionUID = 1L;

        public Grapher(String appTitle) {
            super(appTitle);

            // Create Dataset
            CategoryDataset dataset = createDataset();

            //Create chart
            if(viewType.toString() == "Municipality"){
                JFreeChart chart=ChartFactory.createLineChart(
                        "Graph", //Chart Title
                        "Year", // Category axis
                        "Election Participation", // Value axis
                        dataset,
                        PlotOrientation.VERTICAL,
                        false,true,false
                );
                ChartPanel panel=new ChartPanel(chart);
                CategoryPlot plot = chart.getCategoryPlot();
                NumberAxis range = (NumberAxis) plot.getRangeAxis();
                range.setRange(60,100);
                setContentPane(panel);
            }else if(viewType.toString() == "Parties"){
                JFreeChart chart=ChartFactory.createBarChart(
                        "Graph", //Chart Title
                        "Year", // Category axis
                        "Parliament seats", // Value axis
                        dataset,
                        PlotOrientation.VERTICAL,
                        true,true,false
                );

                //Colors from the political parties graphical profiles
                CategoryPlot plot = chart.getCategoryPlot();
                BarRenderer renderer = (BarRenderer) plot.getRenderer();
                Color m = new Color(13, 157, 219);
                Color c = new Color(17, 72, 56);
                Color fp = new Color(0, 106, 179);
                Color kd = new Color(0, 94, 161);
                Color mp = new Color(83, 160, 69);
                Color s = new Color(237, 27, 52);
                Color v = new Color(170, 28, 36);
                Color sd = new Color(221,221,0);
                renderer.setSeriesPaint(0, m);
                renderer.setSeriesPaint(1, c);
                renderer.setSeriesPaint(2, fp);
                renderer.setSeriesPaint(3, kd);
                renderer.setSeriesPaint(4, mp);
                renderer.setSeriesPaint(5, s);
                renderer.setSeriesPaint(6, v);
                renderer.setSeriesPaint(7, sd);

                ChartPanel panel=new ChartPanel(chart);
                setContentPane(panel);
            }

        }

        private CategoryDataset createDataset() {
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();

            int i = 0;
            int z = 1;
            int temp = 0;
            for(int k = 0; k < tData.length; k++){
                System.out.println("First " + i + ": " + tData[i][0]);
                for(Integer year : yearsShown){
                    System.out.println("fdsfds   " + tData[i][0]);
                    temp = (int) Double.parseDouble(tData[i][z]);
                    dataset.addValue(temp, tData[i][0], year);
                    System.out.println("Second " + z + ": " + tData[i][z]);
                    z++;
                }
                i++;
                z = 1;
            }
            return dataset;
        }

        public void viewGraph(){

            Grapher example=new Grapher("Bar Chart Window");
            example.setSize(800, 400);
            example.setLocationRelativeTo(null);
            example.setVisible(true);
        }
    }

    /**
     *Initializes the GUI
     */
    public void view(){
        frame.setContentPane(new GUI().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}