package App;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.*;

/**
 * Class that reads the JSON Dictionary that stores Municipality names
 * @version 1.0
 * @since 2022-10-15
 */
public class DictReader {
    private final String DICT = "src/main/java/App/dict.json";
    private JSONObject dictObject;
    public DictReader() {
        JSONParser parser = new JSONParser();
        try (FileReader reader = new FileReader(DICT)) {
            Object obj = parser.parse(reader);
            dictObject = (JSONObject) obj;
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the name of the Municipality by said ID
     * @param ID    the ID of the Municipality
     * @return  the name of the Municipality
     */
    public String getMunicipalityName(int ID) {
        JSONObject municipalities = (JSONObject)dictObject.get("Municipalities");
        return (String) municipalities.get(String.valueOf(ID));
    }

    /**
     * Gets the full name of the abbreviation of that said party
     * @param sf    the abbreviation
     * @return  the full name
     */
    public String getPartyName(String sf) {
        JSONObject parties = (JSONObject)dictObject.get("Parties");
        return (String) parties.get(sf);
    }
}
