package App;


import org.json.*;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public class HTTPClient{


    /**
     * Likely does not work since i decoupled paramters from class
     * @throws Exception
     */
    private void testHTTPClient() throws Exception {
        HTTPClient obj = new HTTPClient();

        //System.out.println("Testing 1 - Send Http GET request");
        //obj.sendGet();

        System.out.println("Testing 2 - Send Http POST request");
        obj.sendPost("https://httpbin.org/post","");

    }


    public StringBuilder sendGet() throws Exception {

        String url = "";

        HttpURLConnection httpClient =
                (HttpURLConnection) new URL(url).openConnection();

        // optional default is GET
        httpClient.setRequestMethod("GET");

        //add request header
        httpClient.setRequestProperty("User-Agent", "Mozilla/5.0");

        int responseCode = httpClient.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);
        StringBuilder response = new StringBuilder();

        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(httpClient.getInputStream()))) {

            String line;

            while ((line = in.readLine()) != null) {
                response.append(line);
            }

            return response;

        }

    }

    public StringBuilder sendPost(String url, String postRequest) throws Exception {
        HttpsURLConnection httpClient = (HttpsURLConnection) new URL(url).openConnection();

        //add reuqest header
        httpClient.setRequestMethod("POST");
        httpClient.setRequestProperty("User-Agent", "Mozilla/5.0");
        httpClient.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

        // Send post request
        httpClient.setDoOutput(true);
        try (DataOutputStream wr = new DataOutputStream(httpClient.getOutputStream())) {
            wr.writeBytes(postRequest);
            wr.flush();
        }

        int responseCode = httpClient.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + url);
        //System.out.println("Post parameters : " + urlParameters);
        System.out.println("Response Code : " + responseCode);

        StringBuilder response = new StringBuilder();

        try (BufferedReader in = new BufferedReader(
            new InputStreamReader(httpClient.getInputStream()))) {
            String line;
            while ((line = in.readLine()) != null) {
                response.append(line);
            }
            //System.out.println(response);
            return response;
            //print result
        }
    }

}