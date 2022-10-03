package App;


public class Run {
    public static void main(String[] args) throws Exception {
        ParticipationMunicipality pm = new ParticipationMunicipality();
        TestPartier tp = new TestPartier();
        for (Party parti: tp.getParties()){
            System.out.println(parti.getName()+" "+parti.getMandateByRegionAndYear(1987,"VR1"));
        }

        //System.out.println(pm.getMuni());
        //(Municipality muni : pm.getMuni()){
        //    System.out.println("Hej" + muni.getElectionParticipationByYear(2018));
        //}

        //System.out.println(pm);
        GUI gui = new GUI();
        gui.view();
    }
}


