package casino.gamingmachine;

import bettingauthorityapi.BetLoggingAuthority;
import bettingauthorityapi.BetTokenAuthority;
import bettingauthorityapi.IBetTokenAuthority;
import casino.bet.Bet;
import casino.bet.BetResult;
import casino.bet.MoneyAmount;
import casino.cashier.Cashier;
import casino.cashier.ICashier;
import casino.cashier.IPlayerCard;
import casino.game.Game;
import casino.game.GameRule;
import casino.game.IGame;
import casino.idfactory.IDFactory;
import exceptions.BetNotExceptedException;
import exceptions.NoCurrentRoundException;
import exceptions.NoPlayerCardException;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import models.GamingMachineID;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junitparams.JUnitParamsRunner.$;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(JUnitParamsRunner.class) // needed for parametrized tests
public class IGamingMachineTest {

    private BetLoggingAuthority betLogAuth = mock(BetLoggingAuthority.class);
    private IBetTokenAuthority betTokenAuth = mock(BetTokenAuthority.class);
    private GameRule gameRule = mock(GameRule.class);
    private IDFactory idFactory = mock(IDFactory.class);
    private IGame game;
    private GamingMachine myGamingMachine;
    private ICashier cashier;
    private IPlayerCard playerCard;
    private long betAmount = 2000;
    private MoneyAmount bigMoneyAmount = new MoneyAmount(5000);
    private MoneyAmount smallMoneyAmount = new MoneyAmount(1000);


    @Before
    public void init() {

        /**
         * For each test, let Game create BettingRound, so that Bets can be stored.
         */
        game = new Game(gameRule, betTokenAuth);
        game.startBettingRound();

        /**
         * For each test, let Cashier create PlayerCard.
         * In tests we can control how much money PlayerCard has.
         */
        cashier = new Cashier(betLogAuth);
        myGamingMachine = new GamingMachine(cashier, game);
        playerCard = cashier.distributeGamblerCard();
    }

    /**
     * Created By: Kasper
     * Test IGamingMachineTest-1.0
     * GamingMachineID is created by IDFactory
     */
    @Test
    public void newGamingMachine_IdIsCreatedByIDFactory_True() {
        //arrange
        GamingMachineID gamingMachineID = (GamingMachineID) idFactory.createId("GamingMachineID");
        //act
        //assert
        //Verify that GamingMachineID is called actually GamingMachineID
        when(myGamingMachine.getGamingMachineID()).thenReturn(gamingMachineID);
    }

    /**
     * Created By: Kasper
     * Test IGamingMachineTest-2.0
     * GamingMachineID are unique
     */
    @Test
    public void newGamingMachine_ReturnUniqueGamingMachineID_True() {
        //arrange
        GamingMachine anotherGamingMachine = new GamingMachine(cashier, game);
        //act
        //assert
        assertNotEquals(myGamingMachine.getGamingMachineID(), anotherGamingMachine.getGamingMachineID());
    }

    /**
     * Created By: Kasper
     * Test IGamingMachineTest-3.0
     * After creating GamingMachine, there's no PlayerCard connected throw NoPlayerCardException
     */
    @Test(expected = NoPlayerCardException.class)
    public void newGamingMachine_GetPlayerCard_ThrowNoPlayerCardException() {
        //arrange
        //act
        myGamingMachine.getPlayerCard();
        //assert
    }

    /**
     * Created By: Kasper
     * Test IGamingMachineTest-4.0
     * After Player connect PlayerCard to machine, GamingMachine have PlayerCard
     */
    @Test
    public void connectCard_GetPlayerCard_True() {
        //arrange
        //act
        myGamingMachine.connectCard(playerCard);
        //assert
        assertNotNull(myGamingMachine.getPlayerCard());
    }

    /**
     * Created By: Kasper
     * Test IGamingMachineTest-5.0
     * After creating GamingMachine, there's no open Bets stored
     */
    @Test
    public void newGamingMachine_GetOpenBets_Empty() {
        //arrange
        //act
        //assert
        assertNull(myGamingMachine.getOpenBet());
    }

    /**
     * Created By: Kasper
     * Test IGamingMachineTest-6.0
     * Before Bet can be placed, PlayerCard must be connected, if not throw NoPlayerCardException
     */
    @Test(expected = NoPlayerCardException.class)
    public void placeBet_GamingMachineHasNoPlayerCard_ThrowNoPlayerCardException() {
        //arrange
        //act
        myGamingMachine.placeBet(betAmount);
        //assert
    }

    private Object[] getInvalidAmounts() {
        return $($((Object) null),
                $(0),
                $(-1)
        );
    }

    /**
     * Created By: Kasper
     * Test IGamingMachineTest-7.0
     * If Bet amount is not larger than 0 or it is null, throw IllegalArgument Exception
     * This is parameterized test
     */
    @Test(expected = IllegalArgumentException.class)
    @Parameters(method = "getInvalidAmounts")
    public void placeBet_BetAmountNotLargerThan0OrNull_ThrowIllegalArgumentException(long amount) {
        //arrange
        //act
        myGamingMachine.connectCard(playerCard);
        myGamingMachine.placeBet(amount);
        //assert
    }

    /**
     * Created By: Kasper
     * Test IGamingMachineTest-8.0
     * After placing bet, PlayerCard amount is checked from Cashier.
     */
    @Test
    public void placeBet_CheckThatPlayerCardHasEnoughMoney_MethodIsCalled() {
        //arrange
        cashier.addAmount(playerCard, bigMoneyAmount);

        // Mock classes for simple test
        Cashier cashier = mock(Cashier.class);
        GamingMachine myGamingMachine = new GamingMachine(cashier, game);

        //act
        myGamingMachine.connectCard(playerCard);
        myGamingMachine.placeBet(betAmount);
        //assert
        verify(cashier).checkIfBetIsValid(any(IPlayerCard.class), any(Bet.class));
    }

    /**
     * Created By: Kasper
     * Test IGamingMachineTest-9.0
     * Store Bet to GamingMachine only if Cashier accepts bet
     **/
    @Test(expected = BetNotExceptedException.class)
    public void placeBet_CashierDoesNotExceptBet_BetIsNotStored_Throw_BetNotExceptedException() {
        //arrange
        cashier.addAmount(playerCard, smallMoneyAmount);
        //act
        myGamingMachine.connectCard(playerCard);
        myGamingMachine.placeBet(betAmount);
        //assert
        // If Cashier.AcceptBet() returns false Bet is not stored to GamingMachine
        assertNull(myGamingMachine.getOpenBet());
    }

    /**
     * Created By: Kasper
     * Test IGamingMachineTest-10.0
     * After placing bet, Bet is stored to GamingMachine
     */
    @Test
    public void placeBet_GetOpenBets_NotEmpty() {
        //arrange
        cashier.addAmount(playerCard, bigMoneyAmount);
        //act
        myGamingMachine.connectCard(playerCard);
        myGamingMachine.placeBet(betAmount);
//        assert
        assertNotNull(myGamingMachine.getOpenBet());
    }

    /**
     * Created By: Kasper
     * Test IGamingMachineTest-11.0
     * GamingMachine can have only one open bet at a time.
     */
    @Test
    public void placeBet_GamingMachineAlreadyHaveABet_Prevent() {
        //arrange
        cashier.addAmount(playerCard, bigMoneyAmount);
        //act
        myGamingMachine.connectCard(playerCard);
        myGamingMachine.placeBet(betAmount);
        //assert
        // Try to add another bet to machine, should return false.
        assertFalse(myGamingMachine.placeBet(betAmount));
    }

    /**
     * Created By: Kasper
     * Test IGamingMachineTest-12.0
     * After placing Bet, Bet is submitted to GamingMachine
     */
    @Test
    public void placeBet_SubmitToGamingMachine_True() {
        //arrange
        cashier.addAmount(playerCard, bigMoneyAmount);

        //Mock Game class for simplified test
        Game game = mock(Game.class);
        GamingMachine myGamingMachine = new GamingMachine(cashier, game);
        //act
        myGamingMachine.connectCard(playerCard);
        myGamingMachine.placeBet(betAmount);
        //assert
        verify(game).acceptBet(any(Bet.class), any(IGamingMachine.class));
    }

    /**
     * Created By: Kasper
     * Test IGamingMachineTest-13.0
     * If Game class does not accept Bet, it is not stored to GamingMachine. .
     */
    @Test(expected = NoCurrentRoundException.class)
    public void placeBet_GameDoesNotExceptBet_BetIsNotStored_Throw_NoCurrentRoundException() {
        //arrange
        cashier.addAmount(playerCard, bigMoneyAmount);

        // Make new Game class, but dont start BettingRound
        Game game = new Game(gameRule, betTokenAuth);
        GamingMachine myGamingMachine = new GamingMachine(cashier, game);

        //act
        myGamingMachine.connectCard(playerCard);
        myGamingMachine.placeBet(betAmount);
        //assert
        // If Game.AcceptBet() returns false Bet is not stored to GamingMachine
        assertNull(myGamingMachine.getOpenBet());
    }

    /**
     * Created By: Kasper
     * Test IGamingMachineTest-14.0
     * After accepting the winner, all open bet is cleared from machine.
     */
    @Test
    public void acceptWinner_GetOpenBets_IsEmpty() {
        //arrange
        MoneyAmount moneyAmount = mock(MoneyAmount.class);
        cashier.addAmount(playerCard, bigMoneyAmount);
        //act
        myGamingMachine.connectCard(playerCard);
        myGamingMachine.placeBet(betAmount);
        BetResult winningBetResult = new BetResult(myGamingMachine.getOpenBet(), moneyAmount);

        myGamingMachine.acceptWinner(winningBetResult);

        //assert
        assertNull(myGamingMachine.getOpenBet());
    }

    /**
     * Created By: Kasper
     * Test IGamingMachineTest-15.0
     * If winning bet is not same as Bet what was made in GamingMachine throw IllegalArgumentException
     */
    @Test(expected = IllegalArgumentException.class)
    public void acceptWinner_WinningBetIsNotSameAsPlacedBet_ThrowIllegalArgument() {
        //arrange
        cashier.addAmount(playerCard, bigMoneyAmount);
        Bet winningBet = mock(Bet.class);
        MoneyAmount moneyAmount = mock(MoneyAmount.class);
        //Create BetResult which should win
        BetResult winningBetResult = new BetResult(winningBet, moneyAmount);

        //act
        myGamingMachine.connectCard(playerCard);
        myGamingMachine.placeBet(betAmount);
        myGamingMachine.acceptWinner(winningBetResult);
        //assert
    }

    /**
     * Created By: Kasper
     * Test IGamingMachineTest-15.0
     * When the winner has made his bet in this machine: let the Cashier update the amount.
     */
    @Test
    public void acceptWinner_AmountInPlayerCardIsUpdatedByCashier_MethodIsCalled() {
        //arrange
        cashier.addAmount(playerCard, bigMoneyAmount);
        MoneyAmount moneyAmount = mock(MoneyAmount.class);

        // Mock classes for simple test
        Cashier cashier = mock(Cashier.class);
        GamingMachine myGamingMachine = new GamingMachine(cashier, game);

        //act
        myGamingMachine.connectCard(playerCard);
        myGamingMachine.placeBet(betAmount);

        BetResult winningBetResult = new BetResult(myGamingMachine.getOpenBet(), moneyAmount);

        myGamingMachine.acceptWinner(winningBetResult);
        //assert
        verify(cashier).addAmount(myGamingMachine.getPlayerCard(), moneyAmount);
    }
}
