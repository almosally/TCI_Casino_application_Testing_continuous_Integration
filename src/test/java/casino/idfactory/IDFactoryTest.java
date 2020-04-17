package casino.idfactory;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import models.GeneralID;
import org.joda.time.DateTime;
import org.joda.time.DateTimeUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.UUID;

import static junitparams.JUnitParamsRunner.$;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.junit.Assert.*;

@RunWith(JUnitParamsRunner.class) // needed for parametrized tests
public class IDFactoryTest {

    private UUID uuid = UUID.randomUUID();
    private long dateNowMillis = new DateTime().getMillis();
    private IDFactory idFactory = new IDFactory();

    /**
     * Created By: Kasper
     * Always return the same time when querying current time
     */
    @Before
    public void init() {
        DateTimeUtils.setCurrentMillisFixed(dateNowMillis);
    }

    /**
     * Created By: Kasper
     * Test IDFactoryTest-1.0
     * After creating new ID, ID is described by UUID and TimeStamp
     */
    @Test
    public void newGeneralID_IsDescribedByUUIDAndTimeStamp() {
        //arrange
        //act
        GeneralID generalID = idFactory.createId("GeneralID");
        //assert
        assertThat(generalID.getUUID()).isInstanceOf(uuid.getClass());
        assertEquals(generalID.getTimestamp(), dateNowMillis);
    }

    /**
     * Created By: Kasper
     * Test IDFactoryTest-2.0
     * Timestamp of ID is DateTime.now()
     */
    @Test
    public void newGeneralID_TimestampIsNow_True() {
        //arrange
        //act
        GeneralID generalID = idFactory.createId("GeneralID");
        //assert
        assertEquals(generalID.getTimestamp(), dateNowMillis);
    }

    private Object[] getInvalidGameNames() {
        return $($("GeneralID"),
                $("generalid"),
                $("genERALid")
        );
    }

    /**
     * Created By: Kasper
     * Test IDFactoryTest-3.0
     * ID Type is Case insensitive
     * This is parameterized test
     */
    @Test
    @Parameters(method = "getInvalidGameNames")
    public void newGeneralIDType_CaseInsensitive_True(String type) {
        //arrange
        //act
        GeneralID generalID = idFactory.createId(type);
        //assert
        assertNotNull(generalID);
    }

    /**
     * Created By: Kasper
     * Test IDFactoryTest-4.0
     * ID must have Type, otherwise Null is returned
     */
    @Test
    public void newGeneralIDTypeNull_ReturnNull_True() {
        //arrange
        //act
        GeneralID generalID = idFactory.createId(null);
        //assert
        assertNull(generalID);
    }

    private Object[] getIDTypes() {
        return $(
                $("GeneralID"),
                $("CardID"),
                $("BetID"),
                $("BettingRoundID"),
                $("GamingMachineID")
        );
    }

    /**
     * Created By: Kasper
     * Test IDFactoryTest-5.0
     * CardID, BetID, BettingRoundID, GamingMachineID are instances of GeneralID
     * This is parameterized test
     */
    @Test
    @Parameters(method = "getIDTypes")
    public void newIdWithTypeX_IsInstanceOfGeneralID_True(String type) {
        //arrange
        //act
        GeneralID generalId = new GeneralID();
        GeneralID id = idFactory.createId(type);
        //assert
        assertThat(id).isInstanceOf(generalId.getClass());
    }

    /**
     * Created By: Kasper
     * Test IDFactoryTest-6.0
     * CardID, BetID, BettingRoundID, GamingMachineID are described by UUID and Timestamp
     * This is parameterized test
     */
    @Test
    @Parameters(method = "getIDTypes")
    public void idWithTypeX_IsDescribedByUUIDAndTimeStamp(String type) {
        //arrange
        //act
        GeneralID id = idFactory.createId(type);
        //assert
        assertThat(id.getUUID()).isInstanceOf(uuid.getClass());
        assertEquals(id.getTimestamp(), dateNowMillis);
    }
}
