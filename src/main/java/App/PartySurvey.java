package App;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
/**
 * This class is used to store information about party sympathy repeated on a half year basis.
 * @author Erik Hillestad
 * @version 1.0
 * @since 2022-10-14
 */

public class PartySurvey {
        private final String Gender;
        private final String Income;
        private final String Party;
        private final HashMap<String, Double> PartySympathizer;

    public PartySurvey(String Gender, String Income, String Party) {
        this.Gender = Gender;
        this.Income = Income;
        this.Party = Party;
        this.PartySympathizer = new HashMap<>();
    }
    public String getGender()  {return Gender;}
    public String getIncome() {return Income;}

    public void addPartySympathizer(String halfYear, Double PartySympathiz) {
        this.PartySympathizer.put(halfYear, PartySympathiz);
        //Collections.sort(PartySympathizer);
    }

    public Double getPartySympathizerbyHalfYear(String halfYear) {
        try {
            return PartySympathizer.get(halfYear);
        } catch (NullPointerException ne) {
            return null;
        }
    }

    public Double getPartySympathizerByYear(int year){
        try{
            return (getPartySympathizerbyHalfYear(Integer.toString(year) + "M11") + getPartySympathizerbyHalfYear(Integer.toString(year) + "M05"))/2;
        }  catch (NullPointerException ne) {
            return null;
        }
    }

    @Override
    public String toString() {
        String p = "";
        String g = switch (Gender) {
            case "1" -> "Man";
            case "2" -> "Female";
            default -> "Total";
        };

        DictReader dr = new DictReader();
        p = dr.getPartyName(Party.toUpperCase());

        return g + " | " + Income + " | " + p + ":";
        //return "Parti: " + Party + "\n Gender: " + Gender + "\n Income: " + Income + PartySympathizer;
    }

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if(o == null|| o.getClass()!= this.getClass()){return false;}
        PartySurvey obj = (PartySurvey) o;

        if (!(this.Income.equals(obj.Income))){return false;}

        if (!(this.Gender.equals(obj.Gender))){return false;}

        if (!(this.Party.equals(obj.Party))){return false;}

        return true;
    }
}
