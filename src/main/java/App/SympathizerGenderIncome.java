package App;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SympathizerGenderIncome extends QueryTabels {


    private final List<PartySurvey> partySurveys = new ArrayList<>();
    public List<PartySurvey> getPartySurveys() {
        return partySurveys;
    }
    private final String URL = "https://api.scb.se/OV0104/v1/doris/sv/ssd/START/ME/ME0201/ME0201B/Partisympati081";
    private final String PATH = "src/main/java/App/SurveyData.json";

    public SympathizerGenderIncome(){
        try {
            makePartySurveys(databaseToString(URL,PATH));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void makePartySurveys(String str) throws Exception {  // Turn the JSON String into Data
        DictReader dictReader = new DictReader();
        JSONParser parser = new JSONParser();
        JSONObject jObj = (JSONObject) parser.parse(str);
        JSONArray results = (JSONArray) jObj.get("data");

//{"values":["27.1"],"key":["1","0-20","m","2010M11"]}
       for (Object i : results) {
            JSONArray keys = (JSONArray) ((JSONObject)i).get("key");
            JSONArray values = (JSONArray) ((JSONObject)i).get("values");
            String Gender = ((String)keys.get(0));
            String Income = (String)keys.get(1);
            String party = (String)keys.get(2);
            String halfYear = (String)keys.get(3);
           if(party.equals("nyd")){
               continue;
           }
            double sympati;
            try {
                sympati = Double.parseDouble((String)values.get(0));
            } catch (NumberFormatException e) {
                sympati = 0;
            }
            PartySurvey temp = SurveyExists(new PartySurvey(Gender, Income, party));
            if (temp != null) {
                temp.addPartySympathizer(halfYear, sympati);
            } else {
                partySurveys.add(new PartySurvey(Gender, Income, party));
            }
        }
    }
    public PartySurvey SurveyExists(PartySurvey p) { // Check for Municipality in municipalities
        for (PartySurvey m : partySurveys) {
            if (p.equals(m)) {return m;}
        }
        return null;
    }

}
