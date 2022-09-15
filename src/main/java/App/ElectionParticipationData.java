package App;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.*;


/**
 * @author johbirger
 * @version 1
 * @since 2022-09-14
 * @return List of Election Participation data
 * Unfinished see view
 */
public class ElectionParticipationData {

    List<String> municipalitiesList = new ArrayList<String>();
    HTTPClient httpClient;
    private JSONObject jObj;
    private String str;
    public List<Object> kommuner = new ArrayList<>();





    public ElectionParticipationData() throws Exception {

        addToList();


    }


    /**
     * test if retrieval possible-yes
     * Next step: parse responseContent into correct datastructure.
     */
    public void addToList() throws Exception {
        httpClient = new HTTPClient();
        str = httpClient.sendPost().toString();
        jObj = new JSONObject(str);
        System.out.println(jObj);

        JSONArray results =(JSONArray) jObj.get("data");

        for(int i = 0; i < results.length(); i++) {
            System.out.println(results.get(i));
            kommuner.add(results.get(i));
        }

        //System.out.println(q.responseContent.toString());
    }

}
