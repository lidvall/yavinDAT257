package App;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class gui {
    String url = "https://api.scb.se/OV0104/v1/doris/sv/ssd/START/ME/ME0104/ME0104D/ME0104T4";
    String requestPath = "src/main/java/App/ElectionParticipation.json";
    QueryTabels qt = new QueryTabels(url,requestPath);
    Municipality muni = new Municipality("Göteborg",1000);


    JFrame frame = new JFrame("App");
    private JPanel mainPanel;
    private JComboBox comboBox1;
    private JScrollPane scrollPanel;
    private JTable table1;
    private JTextField textField1;

    String dataType = "";

    String[] options = {"Municipality", "Voter"};

    String header1[] = {"Municipality", "2018", "2014","2010","2006"};
    String header2[] = {"Voter", "2018", "2014","2010"};
    String tData1[][] = {{"Göteborg", "87%", "90%","67%"},
                            {"Lerum", "84%", "50%","97%"},
                            {"Stockholm", "12%", "13%","99%"}};
    String tData2[][] = {{"Anders", "V", "MP","SD","AFS"},
            {"Gustav", "S", "S","M","KD"},
            {"Berra", "SD", "SD","V","KD"}};

    public gui() throws Exception {
        for(String option : options){
            comboBox1.addItem(option);
        }
        createTable(tData1, header1);

        comboBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object viewType = comboBox1.getItemAt(comboBox1.getSelectedIndex());
                System.out.println(viewType);
                if(viewType.equals("Voter")) {
                    System.out.println("JAJJAMEN");
                    createTable(tData1, header1);
                }else{
                    createTable(tData2, header2);
                }
                table1.repaint();
            }
        });
    }

    private void createTable(String[][] tableData, String[]headerData){
        table1.setModel(new DefaultTableModel(tableData,headerData));
    }

    public void view() throws Exception {
        frame.setContentPane(new gui().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation(600,300);
        frame.pack();
        frame.setVisible(true);
    }
}
