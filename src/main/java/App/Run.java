package App;


public class Run {
    public static void main(String[] args) throws Exception {
        ParticipationMunicipality pm = new ParticipationMunicipality();
        //System.out.println(pm.getMuni());
        //(Municipality muni : pm.getMuni()){
        //    System.out.println("Hej" + muni.getElectionParticipationByYear(2018));
        //}

        //System.out.println(pm);
        GUI gui = new GUI();
        gui.view();
    }
}


