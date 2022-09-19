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
 * @return List of Election Participation data
 * Unfinished see view
 */
public class ElectionParticipationData {

    //List<String> municipalitiesList = new ArrayList<String>();
    HTTPClient httpClient;
    private JSONObject jObj;
    Set<Municipality> municipalities = new HashSet<>();
    Municipality mun;
    Set<Integer> municipalityCodes = new HashSet<>();





    public ElectionParticipationData() throws Exception {
        String str = databaseToString();
        //addToList(str);
        testshowstuff(str);

    }

    private String databaseToString() throws Exception {

        String url = "https://api.scb.se/OV0104/v1/doris/sv/ssd/START/ME/ME0104/ME0104D/ME0104T4";

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

    private void makeMunicipalityList(String str) throws Exception {
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

    /*
    public void addToList(String str) throws Exception {

        jObj = new JSONObject(str);
        JSONArray results =(JSONArray) jObj.get("data");
        for (Object i : results ){
            JSONObject j = (JSONObject) i;
            JSONArray values = (JSONArray) j.get("values");
            JSONArray key = (JSONArray) j.get("key");

           try{
               municipalities.add(new Municipality("NA",key.getInt(0)));
               Municipality mun = municipalities.get(municipalities.size()-1);
               //riksdagsval
               mun.addElectionParticipation(key.getInt(1),values.getDouble(0));
               //mun.addElectionParticipation(key.getInt(1),values.getDouble(1));
               //mun.addElectionParticipation(key.getInt(1),values.getDouble(2));

           }
           catch (JSONException je){
               mun = new Municipality("NA",key.getInt(0));
               mun.addElectionParticipation(key.getInt(1),0);
               //mun.addElectionParticipation(key.getInt(1),0);
               //mun.addElectionParticipation(key.getInt(1),0);
           }

        }

        //System.out.println(q.responseContent.toString());
    }
*/
    public void testshowstuff(String str) throws Exception {
        makeMunicipalityList(str);


        for (Municipality municipality : municipalities) {
            //System.out.println(municipality.getElectionParticipationByYear(2014));
            System.out.println(municipality.toString() +" - "+ municipality.getElectionParticipation());
        }


        //System.out.println(municipalityCodes);
        //System.out.println(municipalities.toString());
        //System.out.println(municipalities.get(3).getElectionParticipation());

        //System.out.println(municipalities.get(3).getElectionParticipationByYear(2018));


        //System.out.println(mun.getElectionParticipationByYear(2018));

    }

}
