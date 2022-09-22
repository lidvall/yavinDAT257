package App;

import java.util.Comparator;
import java.util.HashMap;

/**
 * This class is used to store information about a municipality (Swedish: Kommun)
 * @author Lukas Wigren
 * @version 1.0
 * @since 2022-09-22
 */
public class Municipality {
    private final String name;
    private final int ID;
    private HashMap<Integer, Double> electionParticipation;
    
    /**
     * Constructor class, creates a Kommun class using its name and ID
     * @param name  name of the municipality
     * @param ID    ID of the municipality
     */
    Municipality(String name, int ID) {
        this.name = name;
        this.ID = ID;
        this.electionParticipation = new HashMap<>();
    }

    /**
     * Get function for ID
     * @return  the ID of the Municipality
     */
    public int getID()  {return ID;}
    /**
     * Add function for ElectionParticipation
     * @param year  the year
     * @param participation the participation in %
     */
    public void addElectionParticipation(int year, double participation) {
        this.electionParticipation.put(year, participation);
    }

    /**
     * Get function for Electionparticipation
     * @return  the HashMap of all ElectionParticipation values <Year, Participation>
     */
    public HashMap<Integer, Double> getElectionParticipation() {
        return electionParticipation;
    }

    /**
     * Get function for Electionparticipation by year
     * @param year  the year for the participation
     * @return  the participation for that year
     */
    public double getElectionParticipationByYear(int year) {
        try {
            return electionParticipation.get(year);
        }
        catch (NullPointerException ne){
            return 0;
        }
    }

    /**
     * Comparator two municipalities in case-insensitive lexicograpic order by name
     */
    public static final Comparator<Municipality> byNameOrder = (a,b) -> a.name.compareToIgnoreCase(b.name);

    /**
     * Comparator two municipalities in numeric order by ID
     */
    public static final Comparator<Municipality> byIDOrder = (a,b) -> Integer.compare(a.ID, b.ID);

    /**
     * toString function, returns the municipality as a String
     * @return  representing String of the class
     */
    @Override
    public String toString() {
        return "Name: "+name+"\nID: "+ID;
    }
}