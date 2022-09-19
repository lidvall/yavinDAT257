package App;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;


/**
 * @author johbirger
 * @version 1
 * @since 2022-09-14
 * @return data views
 * Unfinished
 */
public class QueryTabels {

    HTTPClient httpClient;
    private JSONObject jObj;
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
        jObj = new JSONObject(str);
        JSONArray results = (JSONArray) jObj.get("data");
        for (Object i : results) {
            JSONObject j = (JSONObject) i;
            JSONArray key = (JSONArray) j.get("key");
            JSONArray values = (JSONArray) j.get("values");
            Municipality mun = new Municipality("NA", key.getInt(0));
            try{
                mun.addElectionParticipation(key.getInt(1),values.getDouble(0));
            }
            catch (JSONException je){
                mun.addElectionParticipation(key.getInt(1),0);
            }
            municipalities.add(mun);
        }
    }


    public void testshowstuff(String str) throws Exception {

        makeMunicipalities(str);
        for (Municipality municipality : municipalities) {
            System.out.println(municipality.toString() +" - "+ municipality.getElectionParticipation());

        }

    }

}
