package casino.game;

import bettingauthorityapi.IBetTokenAuthority;
import casino.bet.Bet;
import casino.bet.BetResult;
import casino.bet.MoneyAmount;
import casino.idfactory.IDFactory;
import exceptions.BetNotExceptedException;
import models.BetID;
import models.BettingRoundID;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class IGameRuleTest {
    private IBetTokenAuthority betTokenAuthority = mock(IBetTokenAuthority.class);
    private IDFactory idFactory = mock(IDFactory.class);
    private GameRule gameRule = new GameRule(betTokenAuthority);

    /**
     * Game Rule contains to the BetTokenAuthority
     * Check connection
     */
    @Test
    public void createGameRule_ContainsToBetTokenAuthority_True() {
        //arrange
        //act
        //assert
        assertEquals("Game Rule does not contain BetTokenAuthority", gameRule.getBetTokenAuthority(), betTokenAuthority);
    }

    /**
     * this test should be return the win bet at the end of the round
     * this test will check method determineWinner
     * checking random Int from betTokenAuthority
     */
    @Test
    public void determineWinner_returnTheWinner() {
        //Arrange
        BettingRoundID bettingRoundID = (BettingRoundID) idFactory.createId("BettingRoundID");
        Set<Bet> bets = new HashSet<>();

        Bet bet1 = new Bet(new BetID(), new MoneyAmount(50));
        Bet bet2 = new Bet(new BetID(), new MoneyAmount(50));
        bets.add(bet1);
        bets.add(bet2);
        //Act
        BetResult determineWinner = gameRule.determineWinner(betTokenAuthority.getRandomInteger(betTokenAuthority.getBetToken(bettingRoundID)), bets);
        //Assert
        assertNotNull("No Winner!", determineWinner);
    }

    /**
     * this will check the winner in the round
     * This test checking method: BetResult determineWinner return randomWinValue
     */
    @Test
    public void determineWinner_CheckWinnerInBetRound_WhereSuccessfulBetPlaced() {
        //Arrange
        //
        Bet winBet = new Bet(new BetID(), new MoneyAmount(50));
        Bet loseBet = new Bet(new BetID(), new MoneyAmount(50));

        Set<Bet> bets = new HashSet();
        bets.add(winBet);
        bets.add(loseBet);
        //Act
        BetResult actual = gameRule.determineWinner(gameRule.setMaxBetPerRound(2), bets);
        //Assert
        assertNotNull(actual);
    }

    @Test(expected = BetNotExceptedException.class)
    public void checkWinnerInBetRound_ThrowsException_WhereNoBetsPlaced() throws BetNotExceptedException {
        //Arrange
        Bet winBet = new Bet(new BetID(), new MoneyAmount(50));
        Set<Bet> bets = new HashSet();
        BetResult expected = new BetResult(winBet, new MoneyAmount(100));
        //Act
        BetResult actual = gameRule.determineWinner(0, bets);
        //Assert
    }

    /**
     * This test checking method: int getMaxBetsPerRound return MaxBet number
     */
    @Test
    public void getMaxBetsPerRound_checkTheMaxBetPlaced_returnNumber() {
        //Arrange
        //Act
        gameRule.setMaxBetPerRound(4);
        //Assert
        assertEquals("Invalid value for number of bets per round", 4, gameRule.getMaxBetsPerRound());
    }

    /**
     * This test checking method: int getMaxBetsPerRound No Bet placed return exception number
     */
    @Test(expected = BetNotExceptedException.class)
    public void checkTheMaxBetPlaced_ThrowException() throws BetNotExceptedException {
        //Arrange
        //Act
        gameRule.setMaxBetPerRound(0);
        //Assert
        assertThat(gameRule.getMaxBetsPerRound(), is(1));
    }
}
