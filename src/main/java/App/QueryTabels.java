package App;

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
        URL requestURL = getClass().getResource(requestPath);
        Path filePath = Path.of(requestURL.toURI());
        String postRequest = Files.readString(filePath);
        HTTPClient httpClient = new HTTPClient();
        return httpClient.sendPost(url,postRequest).toString();
    }
}
