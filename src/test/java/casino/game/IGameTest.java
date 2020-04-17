package casino.game;

import bettingauthorityapi.IBetTokenAuthority;
import casino.bet.Bet;
import casino.bet.MoneyAmount;
import casino.gamingmachine.IGamingMachine;
import exceptions.NoCurrentRoundException;
import models.BetID;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class IGameTest {
    private IBetTokenAuthority iBetTokenAuthority = mock(IBetTokenAuthority.class);
    private IGamingMachine gamingMachine = mock(IGamingMachine.class);
    private MoneyAmount moneyAmount = mock(MoneyAmount.class);
    private GameRule gameRule = mock(GameRule.class);
    private Game game = new Game(gameRule, iBetTokenAuthority);

    /**
     * this test should pass when the round start
     * start new betRound
     * this test will check method startBettingRound
     */
    @Test
    public void startBettingRound_StartBetRound_successfulStart() {
        //Arrange
        game = new Game(gameRule, iBetTokenAuthority);
        // Act
        game.startBettingRound();
        // Assert
        //verify(betLoggingAuthority).startBettingRound(any(BettingRound.class));
        //Cannot be tested because of that the betLoggingAuthority.startBettingRound is not implemented (Out of our scoop)
    }

    /**
     * checking the current bettinground and accept bet
     * check placeBet from current betting round with right amount
     * this test will check method boolean acceptBet and return true
     */
    @Test
    public void acceptBet_BetSuccessfullyAccepted_BetRoundAndBettingAreValid() {
        //Arrange
        game.startBettingRound();
        Bet bet = new Bet(new BetID(), moneyAmount);
        when(gamingMachine.placeBet(moneyAmount.getAmountInCents())).thenReturn(true);
        //Act
        //Assert
        assertTrue(game.acceptBet(bet, gamingMachine));
    }

    /**
     * checking the current bettinground Check for the bet if not valid
     * this test will check method boolean acceptBet NOT occur
     * Throw exception NoCurrentRoundException
     */
    @Test(expected = NoCurrentRoundException.class)
    public void acceptBet_CheckBet_betIsNotValid_ThrowException() {
        //Arrange
        //Mock
        Bet bet = mock(Bet.class);
        //Act
        game.acceptBet(bet, gamingMachine);
        //Assert
    }

    /**
     * check all is betting round successfully end and return true when all bets are made
     * this test will check method isBettingRoundFinished return true
     */
    @Test
    public void isBettingRoundFinished_endOFBettingRound_returnTrue() {
        //Arrange
        GameRule gameRule1 = new GameRule(iBetTokenAuthority);
        Game game = new Game(gameRule1, iBetTokenAuthority);
        gameRule1.setMaxBetPerRound(3);
        game.startBettingRound();
        //Mock
        Bet bet1 = new Bet(new BetID(), new MoneyAmount(20));
        Bet bet2 = new Bet(new BetID(), new MoneyAmount(20));
        Bet bet3 = new Bet(new BetID(), new MoneyAmount(30));
        //Mock
        IGamingMachine gamingMachine1 = mock(IGamingMachine.class);
        IGamingMachine gamingMachine2 = mock(IGamingMachine.class);
        IGamingMachine gamingMachine3 = mock(IGamingMachine.class);
        game.acceptBet(bet1, gamingMachine1);
        game.acceptBet(bet2, gamingMachine2);
        game.acceptBet(bet3, gamingMachine3);
        //Act
        boolean results = game.isBettingRoundFinished();
        //Assert
        Assert.assertFalse(results);
    }

    /**
     * check all is betting round not successfully end and return false when max bet not achived
     * this test will check method isBettingRoundFinished return false
     */
    @Test
    public void endOFBettingRound_returnFalse() {
        //Arrange
        GameRule gameRule2 = new GameRule(iBetTokenAuthority);
        gameRule2.setMaxBetPerRound(5);
        Game game = new Game(gameRule2, iBetTokenAuthority);
        game.startBettingRound();
        Bet bet1 = new Bet(new BetID(), moneyAmount);
        IGamingMachine gamingMachine1 = mock(IGamingMachine.class);
        game.acceptBet(bet1, gamingMachine1);
        //Act
        boolean result = game.isBettingRoundFinished();
        //Assert
        Assert.assertFalse(result);
    }
}
