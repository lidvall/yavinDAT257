package App;

import java.util.HashMap;
import java.util.HashSet;

public class Party {
    //String url = "https://api.scb.se/OV0104/v1/doris/sv/ssd/START/ME/ME0104/ME0104C/Riksdagsmandat";

    private String parti;
    private HashSet<String> RegionYear;
    private HashMap<PartyRegionYear, Double> regionMandate;

    public Party(String p){
        this.parti=p;
        this.RegionYear = new HashSet<>();
        this.regionMandate = new HashMap<PartyRegionYear, Double>();
    }


    public String getName() {
        return parti;
    }

    public void addMandate(int year, String region, double mandate) {
        //this.partiKretsList.add();
        this.regionMandate.put(new PartyRegionYear(getName(),region,year),mandate);

    }
    /**
     * Get function for Electionparticipation by year
     * @param year  the year for the participation
     * @return  the participation for that year
     */
    public double getMandateByRegionAndYear(int year,String region) {
        try {
            return regionMandate.get(new PartyRegionYear(parti,region,year));
        }
        catch (NullPointerException ne){

            return 0;
        }
    }
}
///issue lies here, cannot target based on this class
class PartyRegionYear {
    String parti;
    String krets;
    int year;

    public PartyRegionYear(String p, String r, int y) {
        this.parti = p;
        this.krets = r;
        this.year = y;
    }

    public String getKrets() {
        return krets;
    }

    public String getParti() {
        return parti;
    }

    public int getYear() {
        return year;
    }

    @Override public String toString(){
        return year +" "+ parti +" "+ krets;
    }


}
