package App;


import java.io.IOException;

public class Run {
    static ElectionParticipationData epd;

    public static void viewRegionElectionParticipation() throws Exception {
        epd = new ElectionParticipationData();

        for (int j = 0; j < epd.valdeltagandeÅrRegionkod.size(); j++) {
            System.out.println(epd.valdeltagandeÅrRegionkod.get(j));
        }




    }

    public static void main(String[] args) throws Exception {
        viewRegionElectionParticipation();
    }
}
