package casino.game;

import bettingauthorityapi.BetLoggingAuthority;
import bettingauthorityapi.BetToken;
import bettingauthorityapi.IBetTokenAuthority;
import casino.bet.Bet;
import casino.cashier.Cashier;
import casino.cashier.ICashier;
import casino.gamingmachine.IGamingMachine;
import casino.idfactory.IDFactory;
import exceptions.NoCurrentRoundException;
import models.BettingRoundID;

import java.util.HashSet;
import java.util.Set;

public class Game implements IGame {

    private IBettingRound currentBettingRound;
    private GameRule gameRule;
    private IBetTokenAuthority betTokenAuthority;
    private BetLoggingAuthority betLoggingAuthority;
    private ICashier cashier;
    private BetToken betToken;
    private Set<IGamingMachine> setGamingMachines;


    public Game(GameRule gameRule, IBetTokenAuthority betTokenAuthority) {
        this.gameRule = gameRule;
        this.betTokenAuthority = betTokenAuthority;
        this.betLoggingAuthority = new BetLoggingAuthority();
        this.cashier = new Cashier(betLoggingAuthority);
        setGamingMachines = new HashSet<IGamingMachine>();
    }

    @Override
    public void startBettingRound() {
        IDFactory idFactory = new IDFactory();
        BettingRoundID bettingRoundID = (BettingRoundID) idFactory.createId("BettingRoundID");
        betToken = new BetToken(bettingRoundID);
        currentBettingRound = new BettingRound(bettingRoundID, betToken, cashier);
        betLoggingAuthority.startBettingRound(currentBettingRound);
    }

    @Override
    public boolean acceptBet(Bet bet, IGamingMachine gamingMachine) throws NoCurrentRoundException {
        if (currentBettingRound == null) {
            throw new NoCurrentRoundException();
        }
        return true;
    }

    @Override
    public void determineWinner() {
        int randomInteger = betTokenAuthority.getRandomInteger(betToken);
        gameRule.determineWinner(randomInteger, currentBettingRound.getAllBetsMade());
    }

    @Override
    public boolean isBettingRoundFinished() {
        int numberOFBetsMade = currentBettingRound.numberOFBetsMade();
        int maxNumOfBets = gameRule.getMaxBetsPerRound();
        int randomInteger = betTokenAuthority.getRandomInteger(betToken);

        if (numberOFBetsMade == maxNumOfBets) {
            betLoggingAuthority.endBettingRound(
                    currentBettingRound,
                    gameRule.determineWinner(randomInteger, currentBettingRound.getAllBetsMade())
            );
            return true;
        }
        return false;
    }
}
