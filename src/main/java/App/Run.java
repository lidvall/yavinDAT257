package App;


public class Run {
    public static void main(String[] args) throws Exception {

        ParticipationMunicipality pm = new ParticipationMunicipality();
        TestPartier tp = new TestPartier();






        /*
        System.out.println(tp.getPartyObj("M").getAggregateMandate(2018));

        Scanner sc = new Scanner(System.in);
        System.out.println("Party(Acronym): " );
        String name = sc.nextLine();
        System.out.println("Region(VR#): " );
        String reg = sc.nextLine();
        System.out.println("Year(YYYY): " );
        int year = sc.nextInt();
        System.out.println(tp.partyExists(name).getMandateByRegionAndParty(reg,year));
         */

        /*
        for (Party parti: tp.getParties()){
            parti.readMandateRegionAndYear();
            //System.out.println(parti.getName()+" "+parti.getMandateByRegionAndParty("VR1","M",2018));
        }
        */

        //System.out.println(pm.getMuni());
        //(Municipality muni : pm.getMuni()){
        //    System.out.println("Hej" + muni.getElectionParticipationByYear(2018));
        //}

        //System.out.println(pm);
        GUI gui = new GUI();
        gui.view();
    }


}