package App;

import java.util.*;

//time spent 6 hours

public class Party<PRYKey> {
    //String url = "https://api.scb.se/OV0104/v1/doris/sv/ssd/START/ME/ME0104/ME0104C/Riksdagsmandat";

    private String parti;
    private HashMap<String, PartyRegionYearMandate> PRYM;


    public Party(String p){
        this.parti=p;
        this.PRYM = new HashMap<String, PartyRegionYearMandate>();
    }

    public String getName() {
        return parti;
    }

    public void addMandate(int year, String region, double mandate) {
        String key = region+parti+year;
        PRYM.put(key,new PartyRegionYearMandate(parti,region,year,mandate));
    }

    /**
     *
     * @param year
     * @return integer of aggregate mandates over all regions in a given year (for the party-object calling the function)
     */
    public int getAggregateMandate(int year){
        int mandate=0;
        for (int i = 0; i < 30; i++) {
            String key = "VR"+i+""+parti+year;
            try{
                mandate += PRYM.get(key).mandate;
            }
            catch (NullPointerException e){
                mandate +=0;
            }
        }
        return mandate;
    }

    public double getMandateChange(int year){
        double mandate_new = getAggregateMandate(year);
        double mandate_old=getAggregateMandate(year-4);
        if (mandate_old == 0){
            return 0;
        }

        return mandate_new/mandate_old;
    }

    /**
     * Get function for mandate by region and Year
     * @param region, the region of interest
     * @param year, the year of interest
     * @return  the mandate for that year and region (for the party calling the method)
     */
    public double getMandateByRegionAndParty(String region, int year) {
        try {
            String key = region+parti+year;
            return PRYM.get(key).mandate;
        }
        catch (NullPointerException ne){
            return 0;
        }
    }

    /**
     * intended for testing purposes, shows how results are retreivable.
     */
    public void readMandateRegionAndYear(){
        PRYM.forEach((String,PartyRegionYearMandate)->
                System.out.println(PartyRegionYearMandate.parti+" "+
                        PartyRegionYearMandate.year+" "+ PartyRegionYearMandate.krets+" "+
                        PartyRegionYearMandate.mandate));

    }
}


/**
 * **Currently over-complicated i think equals and hashcode method can be default, previously attempted to have each parameter as identifyer.**
 * Stores information
 *
 */
class PartyRegionYearMandate {
    String parti;
    String krets;
    int year;
    double mandate;

    public PartyRegionYearMandate(String p, String r, int y, double m) {
        this.parti = p;
        this.krets = r;
        this.year = y;
        this.mandate = m;
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

    public double getMandate() {
        return mandate;
    }

    @Override public boolean equals(Object o){
        if (this == o) return true;
        if(o == null|| o.getClass()!= this.getClass()){return false;}
        PartyRegionYearMandate obj = (PartyRegionYearMandate) o;

        if (!(this.parti.equals(obj.parti))){return false;}

        if (!(this.krets.equals(obj.krets))){return false;}

        return this.year == (obj.year);
    }

    @Override public int hashCode(){
        int hash=1;
        char p= getParti().charAt(0);
        char r = getKrets().charAt(0);
        char r2= getKrets().charAt(1);
        hash = hash * 31 + getYear();
        hash = hash * 31 + Character.getNumericValue(p);
        hash = hash *31 + Character.getNumericValue(r)+Character.getNumericValue(r2);
        return hash;
    }


    @Override public String toString(){
        return year +" "+ parti +" "+ krets;
    }

}
