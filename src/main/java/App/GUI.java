package App;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import java.io.IOException;
import java.util.List;


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

    private String[] options = {"Municipality", "Voter", "Parties"};
    private Object viewType;
    private String[] header;
    private String[][] tData;
    private int[] yearsShown = {2018, 2014, 2010}; //Initialized to the "standard" set of elections that are shown


    private void pullDataMunicipality(){
        viewType = comboBox1.getItemAt(comboBox1.getSelectedIndex()); //This is only needed to intialize viewType, and is thus only needed in municipality as it's shwon first
        header = new String[yearsShown.length + 1];
        header[0] = pm.header[0];
        for(int i = 1; i < yearsShown.length+1; i++) {
            header[i] = Integer.toString(yearsShown[i-1]);
        }

        tData = new String[pm.getMuni().size()][18];

        int i = 0;
        int z = 1;
        for(Municipality muni : pm.getMuni()){
            tData[i][0] = Integer.toString(muni.getID());
            for(Integer year : yearsShown){
                tData[i][z] = Double.toString(muni.getElectionParticipationByYear(year));
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
        /*
        header = mp.header;
        tData = new String[mp.parties.size()][6];
        int i=0;
        int z=0;
        for(Party p: mp.parties){
            tData[i][z] = p.getName();
            z++;
            tData[i][z] =Integer.toString(p.getAggregateMandate(2018));
            z++;
            tData[i][z] =Integer.toString(p.getAggregateMandate(2014));
            z++;
            tData[i][z] =Integer.toString(p.getAggregateMandate(2010));
            z++;
            tData[i][z] =Integer.toString(p.getAggregateMandate(2006));
            z++;
            tData[i][z] =Integer.toString(p.getAggregateMandate(2004));
            z=0;
            i++;
        }
         */
        header = new String[yearsShown.length + 1];
        header[0] = mp.header[0];
        for(int i = 1; i < yearsShown.length+1; i++) {
            header[i] = Integer.toString(yearsShown[i-1]);
        }

        tData = new String[mp.getParties().size()][18];

        int i = 0;
        int z = 1;
        for(Party party : mp.getParties()){
            tData[i][0] = party.getName();
            for(Integer year : yearsShown){
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
                YearSelect ys = new YearSelect("test");

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
            ControlHost.setLayout(new GridLayout(3,1));

            final String[] years = {"2018", "2014", "2010", "2006", "2002", "1998", "1994", "1991", "1988", "1985", "1982", "1979", "1976", "1973"};

            JList ListYears = new JList(years);
            ListYears.setVisibleRowCount(8);
            JScrollPane jcp = new JScrollPane(ListYears);
            ControlHost.add(jcp);
            ControlHost.add(label);
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
            this.add(jb);

            jb.setPreferredSize(new Dimension(10,10));
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

            this.setLocation(600,300);
            this.setSize(400,400);
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

    /**
     *Initializes the GUI
     */
    public void view(){
        frame.setContentPane(new GUI().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation(400,200);
        frame.pack();
        frame.setVisible(true);
    }
}