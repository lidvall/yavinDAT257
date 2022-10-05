package App;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class is used to store a list of Municipalities and to sort and search
 * @author Lukas Wigren
 * @version 1.0
 * @since 2022-10-05
 */
public class ParticipationMunicipality extends QueryTabels {
    private final List<Municipality> municipalities = new ArrayList<>();
    private final String URL = "https://api.scb.se/OV0104/v1/doris/sv/ssd/START/ME/ME0104/ME0104D/ME0104T4";
    private final String PATH = "src/main/java/App/ElectionParticipation.json";
    /**
     * Constructor class for ParticipationMunicipality
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

    /**
     * Checks whether the municipality of said ID exists within the list of Municipalities
     * @param id    the ID of the Municipality being searched for
     * @return  the Municipality with that said ID
     */
    public Municipality municipalityExists(int id) { // Check for Municipality in municipalities
        for (Municipality m : municipalities) {
            if (m.getID()==id) {return m;}
        }
        return null;
    }

    /**
     * Getter function for the list of Municipalities
     * @return  list of Municipalities
     */
    public List<Municipality> getMunicipalities(){
        return municipalities;
    }

    /**
     * toString function
     * @return  String representing the ParticipationMunicipality class
     */
    @Override
    public String toString() {
        return "municipalities=" + municipalities;
    }

    /**
     * An inner class of ParticipationMunicipality used to search / autocomplete prefix
     */
    public static class Autocompleter {
        private  final Municipality[] dictionary;
        /**
         *  Constructor class for Autocompleter
         * @param dictionary    the list of Municipalities to look up from
         */
        public Autocompleter(List<Municipality> dictionary) {
            this.dictionary = dictionary.toArray(new Municipality[0]);
            sortDictionary();
        }
        private void sortDictionary() {
            Arrays.sort(dictionary, Municipality.byNameOrder);
        }

        /**
         * Gives how many matches said prefix has in the dictionary
         * @param prefix    the prefix to look after
         * @return  number of matches
         */
        public int numberOfMatches(String prefix) {
            Municipality key = new Municipality(prefix, -1);
            int high = RangeBinarySearch.lastIndexOf(dictionary, key, Municipality.byPrefixOrder(prefix.length()));
            int low = RangeBinarySearch.firstIndexOf(dictionary, key, Municipality.byPrefixOrder(prefix.length()));
            if (high < 0) return 0;
            return 1 + high - low;
        }

        /**
         * Gives the list of matches with said prefix
         * @param prefix    the prefix to look after
         * @return  list of Municipalities matching with prefix
         */
        public List<Municipality> allMatches(String prefix) {
            Municipality key = new Municipality(prefix, -1);
            int range = this.numberOfMatches(prefix);
            if (range == 0) return new ArrayList<>();
            int start = RangeBinarySearch.firstIndexOf(dictionary, key, Municipality.byPrefixOrder(prefix.length()));
            Municipality[] temp = new Municipality[range];
            System.arraycopy(dictionary, start, temp, 0, range);
            return Arrays.asList(temp);
        }
    }
}


