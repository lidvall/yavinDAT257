package App;

import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ParticipationMunicipality extends QueryTabels {
    private List<Municipality> municipalities = new ArrayList<>();
    private final String URL = "https://api.scb.se/OV0104/v1/doris/sv/ssd/START/ME/ME0104/ME0104D/ME0104T4";
    private final String PATH = "src/main/java/App/ElectionParticipation.json";

    /**
     * Constructor
     */
    public ParticipationMunicipality() {
        try {
            makeMunicipalities(databaseToString(URL,PATH));
        } catch (Exception e) {}
    }

    private void makeMunicipalities(String str) throws Exception {  // Turn the JSON String into Data
        JSONObject jObj = new JSONObject(str);
        JSONArray results = (JSONArray) jObj.get("data");
        for (Object i : results) {
            JSONObject j = (JSONObject) i;
            JSONArray keys = (JSONArray) j.get("key");
            JSONArray values = (JSONArray) j.get("values");
            int id = keys.getInt(0);
            int year = keys.getInt(1);
            double participation;
            try {
                participation = Double.parseDouble(values.getString(0));
            } catch (NumberFormatException e) {
                participation = 0;
            }
            Municipality temp = municipalityExists(id);
            if (temp != null) {
                temp.addElectionParticipation(year, participation);
            } else {
                municipalities.add(new Municipality("NA", id));
            }
        }
    }

    private Municipality municipalityExists(int id) { // Check for Municipality in municipalities
        for (Municipality m : municipalities) {
            if (m.getID()==id) {return m;}
        }
        return null;
    }

    @Override
    public String toString() {
        return "municipalities=" + municipalities;
    }
}
