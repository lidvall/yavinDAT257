package App;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class SympathizerGenderIncome extends QueryTabels {


    private final List<PartySurvey> partySurveys = new ArrayList<>();
    public List<PartySurvey> getPartySurveys() {
        return partySurveys;
    }
    private final String URL = "https://api.scb.se/OV0104/v1/doris/sv/ssd/START/ME/ME0201/ME0201B/Partisympati081";
    private final String PATH = "SurveyData.json";
    //private final String PATH = "src/main/java/App/SurveyData.json";
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
            String gender = ((String)keys.get(0));
            String income = (String)keys.get(1);
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
            String partyName = dictReader.getPartyName(party.toUpperCase());
            String genderString = switch (gender) {
               case "1" -> "Male";
               case "2" -> "Female";
               default -> "Total";
           };
            PartySurvey temp = SurveyExists(new PartySurvey(genderString, income, partyName));
            if (temp != null) {
                temp.addPartySympathizer(halfYear, sympati);
            } else {
                temp = new PartySurvey(genderString, income, partyName);
                temp.addPartySympathizer(halfYear, sympati);
                partySurveys.add(temp);
            }
        }
    }
    public PartySurvey SurveyExists(PartySurvey p) { // Check for Municipality in municipalities
        for (PartySurvey m : partySurveys) {
            if (p.equals(m)) {return m;}
        }
        return null;
    }
    public static class Autocompleter {
        private  final PartySurvey[] dictionary;
        public Autocompleter(List<PartySurvey> dictionary) {
            this.dictionary = dictionary.toArray(new PartySurvey[0]);
        }
        private void sortDictionary(Comparator<PartySurvey> comparator) {
            Arrays.sort(dictionary, comparator);
        }

        public int numberOfMatches(String prefix, Comparator<PartySurvey> comparator) {
            PartySurvey key = new PartySurvey(prefix, prefix, prefix);
            int high = RangeBinarySearch.lastIndexOf(dictionary, key, comparator);
            int low = RangeBinarySearch.firstIndexOf(dictionary, key, comparator);
            if (high < 0) return 0;
            return 1 + high - low;
        }

        public List<PartySurvey> allMatchesGender(String prefix) {
            sortDictionary(PartySurvey.byGenderOrder);
            PartySurvey key = new PartySurvey(prefix, prefix, prefix);
            int range = this.numberOfMatches(prefix, PartySurvey.byPrefixGenderOrder(prefix.length()));
            if (range == 0) return new ArrayList<>();
            int start = RangeBinarySearch.firstIndexOf(dictionary, key, PartySurvey.byPrefixGenderOrder(prefix.length()));
            PartySurvey[] temp = new PartySurvey[range];
            System.arraycopy(dictionary, start, temp, 0, range);
            return Arrays.asList(temp);
        }
        public List<PartySurvey> allMatchesIncome(String prefix) {
            sortDictionary(PartySurvey.byIncomeOrder);
            PartySurvey key = new PartySurvey(prefix, prefix, prefix);
            int range = this.numberOfMatches(prefix, PartySurvey.byPrefixIncomeOrder(prefix.length()));
            if (range == 0) return new ArrayList<>();
            int start = RangeBinarySearch.firstIndexOf(dictionary, key, PartySurvey.byPrefixIncomeOrder(prefix.length()));
            PartySurvey[] temp = new PartySurvey[range];
            System.arraycopy(dictionary, start, temp, 0, range);
            return Arrays.asList(temp);
        }
        public List<PartySurvey> allMatchesParty(String prefix) {
            sortDictionary(PartySurvey.byPartyOrder);
            PartySurvey key = new PartySurvey(prefix, prefix, prefix);
            int range = this.numberOfMatches(prefix, PartySurvey.byPrefixPartyOrder(prefix.length()));
            if (range == 0) return new ArrayList<>();
            int start = RangeBinarySearch.firstIndexOf(dictionary, key, PartySurvey.byPrefixPartyOrder(prefix.length()));
            PartySurvey[] temp = new PartySurvey[range];
            System.arraycopy(dictionary, start, temp, 0, range);
            return Arrays.asList(temp);
        }

        public List<PartySurvey> allMatches(String prefix) {
            List<PartySurvey> genderResult = allMatchesGender(prefix);
            List<PartySurvey> incomeResult = allMatchesIncome(prefix);
            List<PartySurvey> partyResult = allMatchesParty(prefix);
            genderResult.removeAll(incomeResult);
            genderResult.removeAll(partyResult);
            incomeResult.removeAll(partyResult);
            genderResult.addAll(incomeResult);
            genderResult.addAll(partyResult);
            genderResult.sort(PartySurvey.byAllAttributes);
            return genderResult;
        }
    }
}
