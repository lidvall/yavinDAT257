package App;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PartyTest {

    PartyRegionYearMandate pry,pry2,pry3,pry4,pry5;

    public PartyTest(){
        /*
        pry = new PartyRegionYearMandate("JohanPartiet","Göteborg",2022);

        //should return true
        pry2 = new PartyRegionYearMandate("JohanPartiet","Göteborg",2022);

        //should return false
        pry3 = new PartyRegionYearMandate("LukasPartiet","Göteborg",2022);
        pry4 = new PartyRegionYearMandate("JohanPartiet","Växjö",2022);
        pry5 = new PartyRegionYearMandate("JohanPartiet","Göteborg",2018);

         */
    }


/**
 * Tested equals function in Party - it and hashcode works.
 */

    @Test public void testEquals(){
        assertEquals(pry,pry2);
        assertNotEquals(pry,pry3);
        assertNotEquals(pry,pry4);
        assertNotEquals(pry,pry5);
    }

}