package App;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI {

    ParticipationMunicipality pm = new ParticipationMunicipality();

    JFrame frame = new JFrame("App");
    private JPanel mainPanel;
    private JComboBox comboBox1;
    private JScrollPane scrollPanel;
    private JTable table1;
    private JTextField textField1;

    String[] options = {"Municipality", "Voter", "Something else"};
    String[] header;
    String[][] tData;

    private void pullDataMunicipality(){
        header = pm.header;
        tData = new String[pm.getMuni().size()][6];

        int i = 0;
        int z = 0;
        for(Municipality muni : pm.getMuni()){
            tData[i][z] = Integer.toString(muni.getID());
            z++;
            tData[i][z] =  Double.toString(muni.getElectionParticipationByYear(2018));
            z++;
            tData[i][z] = Double.toString(muni.getElectionParticipationByYear(2014));
            z++;
            tData[i][z] = Double.toString(muni.getElectionParticipationByYear(2010));
            z++;
            tData[i][z] = Double.toString(muni.getElectionParticipationByYear(2006));
            z=0;
            i++;
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

    public GUI() throws Exception {
        for(String option : options){
            comboBox1.addItem(option);
        }

        pullDataMunicipality();

        createTable(tData, header);

        comboBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object viewType = comboBox1.getItemAt(comboBox1.getSelectedIndex());
                //System.out.println(viewType);

                switch(viewType.toString()){
                    case "Municipality":
                        pullDataMunicipality();
                        break;
                    case "Voter":
                        pullDataVoter();
                        break;
                    case "Something else":
                        //a method call for something else
                        break;
                }
                createTable(tData, header);
                table1.repaint();
            }
        });
    }

    private void createTable(String[][] tableData, String[]headerData){
        table1.setModel(new DefaultTableModel(tableData,headerData));
    }

    public void view() throws Exception {
        frame.setContentPane(new GUI().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}