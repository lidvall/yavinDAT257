package App;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;


/**
 * @author johbirger, Lukas Wigren
 * @version 1
 * @since 2022-09-22
 * Unfinished
 */
public class QueryTabels {
    HTTPClient httpClient;
    Set<Municipality> municipalities = new HashSet<>();



    public QueryTabels(String url, String requestPath) throws Exception {
        String content = databaseToString(url, requestPath);
        testshowstuff(content);
    }

    private String databaseToString(String url, String requestPath) throws Exception {
        Path filePath = Path.of(requestPath);
        String postRequest = Files.readString(filePath);

        httpClient = new HTTPClient();
        return httpClient.sendPost(url,postRequest).toString();
    }


    /**
     * test if retrieval possible-yes
     * Next step: parse responseContent into correct datastructure.
     * @param str
     */
    private void makeMunicipalities(String str) throws Exception {
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
     * Checks if the Municipality with the given ID exists, and returns it
     * @param id    the id of a Municipality
     * @return  Municipality with that ID, null if it doesn't exist
     */
    private Municipality municipalityExists(int id) {
        for (Municipality m : municipalities) {
            if (m.getID()==id) {return m;}
        }
        return null;
    }

    public void testshowstuff(String str) throws Exception {
        makeMunicipalities(str);
        for (Municipality municipality : municipalities) {
            System.out.println(municipality.toString() +" - "+ municipality.getElectionParticipation());
        }
    }

}
