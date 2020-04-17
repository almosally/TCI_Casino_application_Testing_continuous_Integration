package casino.cashier;

import bettingauthorityapi.BetLoggingAuthority;
import casino.bet.Bet;
import casino.bet.MoneyAmount;
import casino.idfactory.IDFactory;
import exceptions.NoPlayerCardException;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import models.BetID;
import models.CardID;
import models.GeneralID;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static junitparams.JUnitParamsRunner.$;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(JUnitParamsRunner.class) // needed for parametrized tests
public class ICashierTest {

    private BetLoggingAuthority betLoggingAuthority = mock(BetLoggingAuthority.class);
    private IDFactory idFactory = mock(IDFactory.class);
    private IPlayerCard iPlayerCard = mock(IPlayerCard.class);
    private MoneyAmount moneyAmount = mock(MoneyAmount.class);
    private Cashier cashier = new Cashier(betLoggingAuthority);
    private PlayerCard playerCard;

    /**
     * Test ICashierTest-1.0
     * Cashier contains BetLoggingAuthority
     */
    @Test
    public void createCashier_CashierContainsBetLoggingAuthority_True() {
        // Arrange
        // Act
        // Assert
        assertEquals("Cashier does not contain BetLoggingAuthority", cashier.getBetLoggingAuthority(), betLoggingAuthority);
    }

    /**
     * Test ICashierTest-2.0
     * Distribute Cards / hand the card to the gambler
     * Cashier will distribute the Gambling Card to the Gambler Check if the method handOutGamblingCard from the BetLoggingAuthority is called.
     */
    @Test
    public void distributeGamblerCard_CheckIfHandOutGamblingCardCalled_PlayerCard() {
        // Arrange
        // Act
        cashier.distributeGamblerCard();
        // Assert
        verify(betLoggingAuthority).handOutGamblingCard(any(GeneralID.class));
    }

    /**
     * Test ICashierTest-2.0
     * Distribute Cards / hand the card to the gambler
     * Check if the playerCard is valid
     */
    @Test
    public void distributeGamblerCard_CheckIfPlayerCardIsValid_True() {
        // Arrange
        // Generate 10 cards and distribute them in  order to test if the id is uniq when a real values are exert
        distributeCardsForTest(10);

        CardID cardID = (CardID) idFactory.createId("CardID");
        IPlayerCard newPlayerCard = new PlayerCard(cardID);
        List<IPlayerCard> playerCards = cashier.getDistributedCards();
        // Act
        // Assert
        assertFalse("Invalid Player Card", playerCards.contains(newPlayerCard));
    }

    /**
     * Test ICashierTest-3.0
     * Distribute Cards / hand the card to the gambler
     * Check if the PlayerCardID is Unique
     */
    @Test
    public void distributeGamblerCard_CheckIfPlayerCardDescribedByUniqueId_True() {
        // Arrange
        distributeCardsForTest(12);
        CardID cardID = (CardID) idFactory.createId("CardID");
        iPlayerCard = new PlayerCard(cardID);
        // Act
        // Assert
        assertNotEquals(cashier.distributeGamblerCard(), iPlayerCard.getCardID());
    }

    /**
     * Test ICashierTest-4.0
     * Distribute Cards / hand the card to the gambler
     * Check out if the cashier handed Out Gambling Card
     */
    @Test
    public void distributeGamblerCard_CheckOutIfCardHasBeenDistributed_PlayerCard() {
        // Arrange
        iPlayerCard = cashier.distributeGamblerCard();
        IPlayerCard distributedGamblerCard = cashier.getDistributedCards().get(0);
        // Act
        // Assert
        assertEquals(iPlayerCard, distributedGamblerCard);
    }

    /**
     * Test ICashierTest-5.0
     * Return Cards / gambler return the card to the cashier
     * Gambler return the Gambling Card to the Cashier Check if handOutGamblingCard is called
     */
    @Test
    public void returnGamblerCard_IsHandOutGamblingCardCalled_True() {
        // Arrange
        iPlayerCard = cashier.distributeGamblerCard();
        iPlayerCard.generateNewBetID();
        // Act
        cashier.returnGamblerCard(iPlayerCard);
        // Assert
        verify(betLoggingAuthority).handInGamblingCard(any(GeneralID.class), anySet());
    }

    /**
     * Test ICashierTest-5.1
     * Return Cards / gambler return the card to the cashier
     * Gambler return the Gambling Card to the Cashier Check if card has been returned
     */
    @Test
    public void returnGamblerCard_IsCardHaasBeenReturned_True() {
        // Arrange
        iPlayerCard = cashier.distributeGamblerCard();
        List<IPlayerCard> playerCards = cashier.getDistributedCards();
        // Act
        cashier.returnGamblerCard(iPlayerCard);
        // Assert
        assertFalse("Player Card not returned yet", playerCards.contains(iPlayerCard));
    }

    /**
     * Test ICashierTest-6.0
     * Return Cards / gambler return the card to the cashier
     * Gambler return the Gambling Card Check if the Money in the card reset to zero
     */
    @Test
    public void returnGamblerCard_MoneyInCardZero_True() {
        // Arrange
        playerCard = (PlayerCard) cashier.distributeGamblerCard();
        playerCard.setMoneyAmount(moneyAmount);
        // Act
        cashier.returnGamblerCard(playerCard);
        // Assert
        assertEquals(playerCard.getMoneyAmount().getAmountInCents(), 0);
    }

    /**
     * Test ICashierTest-7.0
     * Return Cards / gambler return the card to the cashier
     * Gambler return the Gambling Card Check if the betIDs in the card reset to zero
     */
    @Test
    public void returnGamblerCard_BetsIdInCardZero_True() {
        // Arrange
        playerCard = (PlayerCard) cashier.distributeGamblerCard();
        // Act
        cashier.returnGamblerCard(playerCard);
        // Assert
        assertEquals(playerCard.getNumberOfBetIDs(), 0);
    }

    /**
     * Test ICashierTest-8.0
     * Validation a bets
     * Check if money for bet is valid
     */
    @Test
    public void checkIfBetIsValid_MoneyInPlayCardEqualOrMoreThanMoneyForBet_True() {
        // Arrange
        BetID betID = (BetID) idFactory.createId("BetID");
        Bet bet = new Bet(betID, moneyAmount);
        playerCard = (PlayerCard) cashier.distributeGamblerCard();
        playerCard.setMoneyAmount(moneyAmount);
        // Act
        // Assert
        assertTrue(cashier.checkIfBetIsValid(playerCard, bet));
    }

    /**
     * Test ICashierTest-9.0
     * Add Money
     * Add positive amount of money
     */
    @Test
    public void addAmount_AddMoneyToThePlayerCard_True() {
        // Arrange
        playerCard = (PlayerCard) cashier.distributeGamblerCard();
        // Act
        cashier.addAmount(playerCard, moneyAmount);
        // Assert
        assertEquals(playerCard.getMoneyAmount(), moneyAmount);
    }

    private Object[] addValidMoneyAmount() {
        return $($(5000000),
                $(600),
                $(999)
        );
    }

    /**
     * Test ICashierTest-9.1
     * Add Money
     * Add valid amount of money
     *
     * @param amount Amount Of Money to add to the playerCard
     */
    @Test
    @Parameters(method = "addValidMoneyAmount")
    public void addAmount_AddValidMoneyToThePlayerCardUsingParameter_True(long amount) {
        // Arrange
        playerCard = (PlayerCard) cashier.distributeGamblerCard();
        moneyAmount = new MoneyAmount(amount);
        // Act
        cashier.addAmount(playerCard, moneyAmount);
        // Assert
        assertEquals(playerCard.getMoneyAmount(), moneyAmount);
    }

    /**
     * Test ICashierTest-10.0
     * Add Money
     * Add negative amount of money
     */
    @Test(expected = IllegalArgumentException.class)
    public void addAmount_AddNegativeAmountOfMoneyToThePlayerCard_ThrowIllegalArgumentException() {
        // Arrange
        playerCard = (PlayerCard) cashier.distributeGamblerCard();
        moneyAmount = new MoneyAmount(-10);
        // Act
        cashier.addAmount(playerCard, moneyAmount);
        // Assert
    }

    /**
     * Test ICashierTest-11.0
     * Add Money
     * Add amount of money to an invalid PlayCard
     */
    @Test(expected = NoPlayerCardException.class)
    public void addAmount_AddMoneyToAnInvalidPlayerCard_ThrowIllegalArgumentException() {
        // Arrange
        // Act
        cashier.addAmount(playerCard, moneyAmount);
        // Assert
    }

    /**
     * Generate and distribute some gambler card for test purpose
     * In Order to have some values to compare our test with when needed.
     *
     * @param cards represents Gambler cards
     */
    private void distributeCardsForTest(int cards) {
        int i = 0;
        while (i < cards) {
            cashier.distributeGamblerCard();
            i++;
        }
    }
}
