package App;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

public class TestPartier extends QueryTabels{

    List<Party> parties = new LinkedList<>();
    String URL = "https://api.scb.se/OV0104/v1/doris/sv/ssd/START/ME/ME0104/ME0104C/Riksdagsmandat";
    String PATH = "src/main/java/App/TestPost.json";

    public String header[] = {"Party", "2018", "2014","2010","2006"};

    public TestPartier() {
        try {
            //System.out.println(databaseToString(URL,PATH)); WORKS
            makePartyList(databaseToString(URL,PATH));
        } catch (Exception e) {}
    }


    private void makePartyList(String str) throws Exception {  // Turn the JSON String into Data
        JSONObject jObj = new JSONObject(str);
        JSONArray results = (JSONArray) jObj.get("data");
        for (Object i : results) {
            JSONObject j = (JSONObject) i;
            JSONArray keys = (JSONArray) j.get("key");
            JSONArray values = (JSONArray) j.get("values");
            String region = keys.getString(0);
            String party = keys.getString(1);
            int year = keys.getInt(2);
            double mandate;
            try {
                mandate = Double.parseDouble(values.getString(0));
            } catch (NumberFormatException e) {
                mandate = 0;
            }
            Party temp = getPartyObj(party);
            if (temp != null) {
                temp.addMandate(year,region,mandate);
            } else {
                parties.add(new Party(party));
            }
        }
    }

    /**
     * **Previously called partyExists**
     * Check for and if found, returns party object from list of parties.
     * @param name of party
     * @return party-object
     */

    public Party getPartyObj(String name) {
        for (Party m : parties) {
            if (name.equals(m.getName())) {return m;}
        }
        return null;
    }

    public List<Party> getParties(){
        return parties;
    }

    @Override
    public String toString() {
        return "Parties=" + parties;
    }

}
