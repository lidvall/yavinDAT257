package App;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;


/**
 * @author johbirger
 * @version 1
 * @since 2022-09-14
 * @return List of Election Participation data
 * Unfinished see view
 */
public class ElectionParticipationData {

    List<String> municipalitiesList = new ArrayList<String>();
    Query q;

    public ElectionParticipationData() throws IOException {
        q = new Query();
    }

    /**
     * test if retrieval possible-yes
     * Next step: parse responseContent into correct datastructure.
     */
    public void view() {
        System.out.println(q.responseContent.toString());
    }

}
