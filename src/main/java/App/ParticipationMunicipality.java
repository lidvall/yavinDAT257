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
 * @since 2022-10-04
 */
public class ParticipationMunicipality extends QueryTabels {
    private final List<Municipality> municipalities = new ArrayList<>();
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



    public Municipality municipalityExists(int id) { // Check for Municipality in municipalities
        for (Municipality m : municipalities) {
            if (m.getID()==id) {return m;}
        }
        return null;
    }
    public List<Municipality> getMuni(){
        return municipalities;
    }
    public List<Municipality> searchByName(String prefix) {
        Municipality temp = new Municipality(prefix, -1);
        return municipalities.subList(municipalities.indexOf(temp), municipalities.lastIndexOf(temp));
    }
    @Override
    public String toString() {
        return "municipalities=" + municipalities;
    }

    public static class Autocompleter {
        private  final Municipality[] dictionary;

        public Autocompleter(List<Municipality> dictionary) {
            this.dictionary = dictionary.toArray(new Municipality[0]);
            sortDictionary();
        }

        private void sortDictionary() {
            Arrays.sort(dictionary, Municipality.byNameOrder);
        }

        public int numberOfMatches(String prefix) {
            Municipality key = new Municipality(prefix, -1);
            int high = RangeBinarySearch.lastIndexOf(dictionary, key, Municipality.byPrefixOrder(prefix.length()));
            int low = RangeBinarySearch.firstIndexOf(dictionary, key, Municipality.byPrefixOrder(prefix.length()));
            if (high < 0) return 0;
            return 1 + high - low;
        }

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


