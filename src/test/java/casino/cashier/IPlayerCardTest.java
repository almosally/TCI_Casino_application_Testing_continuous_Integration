package casino.cashier;

import casino.idfactory.IDFactory;
import models.BetID;
import models.CardID;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class IPlayerCardTest {
    private IDFactory idFactory = mock(IDFactory.class);
    private CardID cardID = (CardID) idFactory.createId("CardID");
    private IPlayerCard playerCard = new PlayerCard(cardID);
    private Set<BetID> betIDs = new HashSet<BetID>();

    /**
     * Test IPlayerCardTest-1.0
     * PlayerCard contains a list of BitId's
     * Return BetIDs
     */
    @Test
    public void returnBetIDs_GetListOfBetId_ListOfIds() {
        // Arrange
        // Act
        generateBetTorTesting(10);
        // Assert
        assertThat(betIDs, is(playerCard.returnBetIDs()));
    }

    /**
     * Test IPlayerCardTest-2.0
     * PlayerCard contains a list of BitId's, after returning the list the list will be cleaned
     */
    @Test
    public void returnBetIDsAndClearCard_GetListOfBetIdAndCleanCard_ListOfIds() {
        // Arrange
        // Act
        playerCard.returnBetIDsAndClearCard();
        // Assert
        assertEquals(0, playerCard.getNumberOfBetIDs());
    }

    /**
     * Test IPlayerCardTest-3.0
     * PlayCard will generate new bet ID
     * Add the ID to the BetIDs List
     */
    @Test
    public void generateNewBetID_GenerateNewBetIdAndAddTheIdToTheBetIDsList_BetID() {
        // Arrange
        // Act
        BetID betID = playerCard.generateNewBetID();
        //Assert
        assertTrue(playerCard.returnBetIDs().contains(betID));
    }

    /**
     * Test IPlayerCardTest-4.0
     * return how many bet ids in the card
     */
    @Test
    public void getNumberOfBetIDs_ReturnNumberOfBitIds_IntegerNumber() {
        // Arrange
        // Act
        // Assert
        assertThat(playerCard.getNumberOfBetIDs(), equalTo(0));
    }

    /**
     * Test IPlayerCardTest-5.0
     * return the playCard Id
     */
    @Test
    public void getCardID_ReturnPlayCardId_CardId() {
        // Arrange
        // Act
        // Assert
        assertThat(playerCard.getCardID(), equalTo(cardID));
    }

    /**
     * Test IPlayerCardTest-6.0
     * PlayCard will generate new bet ID
     * check if betID unique
     */
    @Test
    public void generateNewBetID_CheckIdBetIDUnique_BetID() {
        // Arrange
        // Act
        BetID betID = playerCard.generateNewBetID();
        //Assert
        assertNotEquals(betID, playerCard.getNumberOfBetIDs());
    }

    /**
     * Test IPlayerCardTest-7.0
     * PlayerCard contains a list of BitId's, after generating number of bets, check if the
     * least will return the same number that have been generated
     * Check if list not null
     */
    @Test
    public void getNumberOfBetIDs_BetIdsIsNotNull_ListOfIds() {
        // Arrange
        // Act
        generateBetTorTesting(7);
        // Assert
        assertThat(playerCard.getNumberOfBetIDs(), notNullValue());
    }

    /**
     * Test IPlayerCardTest-8.0
     * PlayerCard contains a list of BitId's, after generating number of bets, check if the
     * least will return the same number that have been generated
     * Check if list is 7
     */
    @Test
    public void getNumberOfBetIDs_BetIdsContains7IDs_ListOfIds() {
        // Arrange
        // Act
        generateBetTorTesting(7);
        // Assert
        assertThat(playerCard.getNumberOfBetIDs(), is(7));
    }

    /**
     * Generate number of bets and add them to the BetIDs for test purpose
     * In Order to have some values to compare our test with when needed.
     *
     * @param num represents how many Bets will be generated and added to the BetIDs set
     */
    private void generateBetTorTesting(int num) {
        int i = 0;
        while (i < num) {
            BetID betID = playerCard.generateNewBetID();
            betIDs.add(betID);
            i++;
        }
    }
}
