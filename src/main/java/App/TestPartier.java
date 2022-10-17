package App;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void makePartyList(String str) throws Exception {  // Turn the JSON String into Data
        JSONParser parser = new JSONParser();
        JSONObject jObj = (JSONObject) parser.parse(str);
        JSONArray results = (JSONArray) jObj.get("data");
        for (Object i : results) {
            JSONArray keys = (JSONArray) ((JSONObject)i).get("key");
            JSONArray values = (JSONArray) ((JSONObject)i).get("values");
            String region = (String)keys.get(0);
            String party = (String)keys.get(1);
            int year = Integer.parseInt((String)keys.get(2));
            double mandate;
            try {
                mandate = Double.parseDouble((String)values.get(0));
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
