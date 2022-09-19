package App;


import java.io.IOException;

public class Run {
    static ElectionParticipationData epd;

    public static void viewRegionElectionParticipation() throws Exception {
        epd = new ElectionParticipationData();

       /*
        for (int j = 0; j < epd.valdeltagandeÅrRegionkod.size(); j++) {

            System.out.println(epd.valdeltagandeÅrRegionkod.get(j));
        }
*/
        epd.foo(epd.valdeltagandeÅrRegionkod);



        //System.out.println(epd.fooMap.keySet(2584,2018));






    }

    public static void main(String[] args) throws Exception {
        viewRegionElectionParticipation();

    }
}
