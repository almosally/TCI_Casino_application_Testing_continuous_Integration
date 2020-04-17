package casino.idfactory;

import models.GeneralID;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class IDComparableTest {

    private IDFactory idFactory = new IDFactory();

    /**
     * Created By: Kasper
     * Test IDComparableTest-1.0
     * IDs can be compared by timestamp
     */
    @Test
    public void afterCreatingTwoIDs_TheyCanBeComparedByTimestamp_True() throws InterruptedException {
        //arrange
        //act
        GeneralID id1 = idFactory.createId("BetID");
        // Sleep for 200ms so that timestamps does not match.
        Thread.sleep(200);
        GeneralID id2 = idFactory.createId("GeneralID");
        //assert
        // Assert that id1 is done before id2
        assertEquals(-1, id1.compareTo(id2));
    }
}
