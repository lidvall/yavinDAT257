package App;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.ArrayList;
import java.util.Arrays;
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
        DictReader dictReader = new DictReader();
        JSONParser parser = new JSONParser();
        JSONObject jObj = (JSONObject) parser.parse(str);
        JSONArray results = (JSONArray) jObj.get("data");
        for (Object i : results) {
            JSONArray keys = (JSONArray) ((JSONObject)i).get("key");
            JSONArray values = (JSONArray) ((JSONObject)i).get("values");
            String region = (String)keys.get(0);
            String party = (String)keys.get(1);
            if(party.equals("NYD")){
                continue;
            }
            int year = Integer.parseInt((String)keys.get(2));
            double mandate;
            try {
                mandate = Double.parseDouble((String)values.get(0));
            } catch (NumberFormatException e) {
                mandate = 0;
            }
            String partyName = dictReader.getPartyName(party);
            Party temp = getPartyObj(partyName);
            if (temp != null) {
                temp.addMandate(year,region,mandate);
            } else {
                parties.add(new Party(partyName));
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

    /**
     * An inner class of ParticipationMunicipality used to search / autocomplete prefix
     */
    public static class Autocompleter {
        private  final Party[] dictionary;
        /**
         *  Constructor class for Autocompleter
         * @param dictionary    the list of Municipalities to look up from
         */
        public Autocompleter(List<Party> dictionary) {
            this.dictionary = dictionary.toArray(new Party[0]);
            sortDictionary();
        }
        private void sortDictionary() {
            Arrays.sort(dictionary, Party.byNameOrder);
        }

        /**
         * Gives how many matches said prefix has in the dictionary
         * @param prefix    the prefix to look after
         * @return  number of matches
         */
        public int numberOfMatches(String prefix) {
            Party key = new Party(prefix);
            int high = RangeBinarySearch.lastIndexOf(dictionary, key, Party.byPrefixOrder(prefix.length()));
            int low = RangeBinarySearch.firstIndexOf(dictionary, key, Party.byPrefixOrder(prefix.length()));
            if (high < 0) return 0;
            return 1 + high - low;
        }

        /**
         * Gives the list of matches with said prefix
         * @param prefix    the prefix to look after
         * @return  list of Municipalities matching with prefix
         */
        public List<Party> allMatches(String prefix) {
            Party key = new Party(prefix);
            int range = this.numberOfMatches(prefix);
            if (range == 0) return new ArrayList<>();
            int start = RangeBinarySearch.firstIndexOf(dictionary, key, Party.byPrefixOrder(prefix.length()));
            Party[] temp = new Party[range];
            System.arraycopy(dictionary, start, temp, 0, range);
            return Arrays.asList(temp);
        }
    }

    public List<Party> getParties(){
        return parties;
    }

    @Override
    public String toString() {
        return "Parties=" + parties;
    }
}
