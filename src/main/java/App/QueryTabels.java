package App;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;


/**
 * @author johbirger, Lukas Wigren
 * @version 1.0
 * @since 2022-10-05
 */
public class QueryTabels {
    public String databaseToString(String url, String requestPath) throws Exception {
        Path filePath = Path.of(requestPath);
        String postRequest = Files.readString(filePath);
        HTTPClient httpClient = new HTTPClient();
        return httpClient.sendPost(url,postRequest).toString();
    }
}
