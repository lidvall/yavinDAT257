package App;

import org.json.JSONArray;
import org.json.JSONObject;

import java.nio.file.Files;
import java.nio.file.Path;
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
    public List<Object> valdeltagandeÅrRegionkod = new ArrayList<>();
    Map<List<Integer>, List<Float>> fooMap = new HashMap<>();





    public ElectionParticipationData() throws Exception {
        String str = databaseToString();
        addToList(str);


    }

    private String databaseToString() throws Exception {
        //String url = "https://api.scb.se/OV0104/v1/doris/sv/ssd/START/ME/ME0105/ME0105C/ME0105T01z"

        String url = "https://api.scb.se/OV0104/v1/doris/sv/ssd/START/ME/ME0104/ME0104D/ME0104T4";
        //JSONParser parser = new JSONParser();
        Path filePath = Path.of("src/main/java/App/ElectionParticipation.json");

        String postRequest = Files.readString(filePath);

        httpClient = new HTTPClient();
        return httpClient.sendPost(url,postRequest).toString();
    }


    /**
     * test if retrieval possible-yes
     * Next step: parse responseContent into correct datastructure.
     * @param str
     */
    public void addToList(String str) throws Exception {

        jObj = new JSONObject(str);
        //System.out.println(jObj);

        JSONArray results =(JSONArray) jObj.get("data");

        for(int i = 0; i < results.length(); i++) {
            //System.out.println(results.get(i));
            valdeltagandeÅrRegionkod.add(results.get(i));
        }

        //System.out.println(q.responseContent.toString());
    }


    public void foo(List l){


        for (int i = 0; i < l.size(); i++) {
            Object entry = l.get(i);
            List<Float> value = new LinkedList<>();
            List<Integer> key = new LinkedList<>();
            String a,b,c,d,e;


            if(entry.toString().length()>54){
                a=entry.toString().substring(12,16);
                b=entry.toString().substring(19,23);
                c=entry.toString().substring(26,30);
                d=entry.toString().substring(41,45);
                e=entry.toString().substring(48,52);
                value.add(Float.valueOf(a));
                value.add(Float.valueOf(b));
                value.add(Float.valueOf(c));
                key.add(Integer.valueOf(d));
                key.add(Integer.valueOf(e));

                fooMap.put(key,value);
            }



        }

    }

}