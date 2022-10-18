package App;
import java.util.Comparator;
import java.util.HashMap;
/**
 * This class is used to store information about party sympathy repeated on a half year basis.
 * @author Erik Hillestad
 * @version 1.0
 * @since 2022-10-14
 */

public class PartySurvey {
        private final String gender;
        private final String income;
        private final String party;
        private final HashMap<String, Double> partySympathizer;

    public PartySurvey(String gender, String Income, String Party) {
        this.gender = gender;
        this.income = Income;
        this.party = Party;
        this.partySympathizer = new HashMap<>();
    }
    public String getGender()  {return gender;}
    public String getIncome() {return income;}

    public void addPartySympathizer(String halfYear, Double PartySympathiz) {
        this.partySympathizer.put(halfYear, PartySympathiz);
        //Collections.sort(PartySympathizer);
    }

    public Double getPartySympathizerbyHalfYear(String halfYear) {
        try {
            return partySympathizer.get(halfYear);
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
    private String getGenderPrefix(int len) {
        return len >= this.gender.length() ? this.gender : this.gender.substring(0, len);
    }
    private String getIncomePrefix(int len) {
        return len >= this.income.length() ? this.income : this.income.substring(0, len);
    }
    private String getPartyPrefix(int len) {
        return len >= this.party.length() ? this.party : this.party.substring(0, len);
    }
    public static final Comparator<PartySurvey> byAllAttributes = (a, b) -> a.toString().compareToIgnoreCase(b.toString());
    public static final Comparator<PartySurvey> byGenderOrder = (a, b) -> a.gender.compareToIgnoreCase(b.gender);
    public static final Comparator<PartySurvey> byIncomeOrder = (a, b) -> a.income.compareToIgnoreCase(b.income);
    public static final Comparator<PartySurvey> byPartyOrder = (a, b) -> a.party.compareToIgnoreCase(b.party);
    public static Comparator<PartySurvey> byPrefixGenderOrder(int k) {
      return (o1, o2) -> {
          String a = o1.getGenderPrefix(k);
          String b = o2.getGenderPrefix(k);
          return a.compareToIgnoreCase(b);
      };
    }
    public static Comparator<PartySurvey> byPrefixIncomeOrder(int k) {
      return (o1, o2) -> {
          String a = o1.getIncomePrefix(k);
          String b = o2.getIncomePrefix(k);
          return a.compareToIgnoreCase(b);
      };
    }
    public static Comparator<PartySurvey> byPrefixPartyOrder(int k) {
      return (o1, o2) -> {
          String a = o1.getPartyPrefix(k);
          String b = o2.getPartyPrefix(k);
          return a.compareToIgnoreCase(b);
      };
    }

    @Override
    public String toString() {
        return gender + " | " + income + " | " + party + ":";
    }

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if(o == null|| o.getClass()!= this.getClass()){return false;}
        PartySurvey obj = (PartySurvey) o;

        if (!(this.income.equals(obj.income))){return false;}

        if (!(this.gender.equals(obj.gender))){return false;}

        if (!(this.party.equals(obj.party))){return false;}

        return true;
    }
}
