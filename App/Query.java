package App;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.Buffer;

/**
 * @author johbirger
 * @version 1
 * @since 2022-09-14
 * @param
 * @return List containing data from Sveriges Kommuner och Regioner on Election Participation Ratios per Municipality
 *  Time spent - 2h
 */

public class Query {
    private URL url;
    private BufferedReader reader;
    private String line;
    private int connectionStatus;
    private static HttpURLConnection connection;
    public StringBuffer responseContent = new StringBuffer();


    public Query() throws IOException {
        url = new URL("https://catalog.skl.se/rowstore/dataset/03c90666-e53c-49af-bde1-8336fc59dff6/json");
        connect();
        getContent();
    }

    /**
     * Connect to the URL sets response code.
     * @throws IOException
     */
    private void connect() throws IOException {
        connection = (HttpURLConnection)url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(10000);
        connection.setReadTimeout(10000);
        connectionStatus = connection.getResponseCode();
        System.out.println(connectionStatus);
    }

    /**
     * if connection status is accepted - appends each line to responsecontent object. Then disconnects.
     * @throws IOException
     */
    private void getContent() throws IOException {
        if (connectionStatus>299){
            reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
            while((line = reader.readLine()) != null){
                responseContent.append(line);
            }
            reader.close();
        }else{
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            while((line = reader.readLine()) != null) {
                responseContent.append(line);
            }
            reader.close();
        }
        connection.disconnect();
    }

}
