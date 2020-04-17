package casino.game;

import bettingauthorityapi.BetToken;
import casino.bet.Bet;
import casino.cashier.ICashier;
import models.BettingRoundID;

import java.util.HashSet;
import java.util.Set;

public class BettingRound implements IBettingRound {

    private BettingRoundID bettingRoundID; //Casino
    private BetToken betToken; //Gambling athority
    private ICashier cashier;
    private Set<Bet> listOfBetsMadeByTheRound;

    public BettingRound(BettingRoundID bettingRoundID, BetToken betToken, ICashier cashier) {
        this.bettingRoundID = bettingRoundID;
        this.betToken = betToken;
        this.cashier = cashier;
        listOfBetsMadeByTheRound = new HashSet<Bet>();
    }

    public void setListOfBetsMadeByTheRound(Set<Bet> listOfBetsMadeByTheRound) {
        this.listOfBetsMadeByTheRound = listOfBetsMadeByTheRound;
    }

    @Override
    public BettingRoundID getBettingRoundID() {
        return bettingRoundID;
    }

    @Override
    public boolean placeBet(Bet bet) {
        if (bet.getMoneyAmount().getAmountInCents() > 0) {
            listOfBetsMadeByTheRound.add(bet);
            return true;
        }
        return false;
    }

    @Override
    public Set<Bet> getAllBetsMade() {
        return listOfBetsMadeByTheRound;
    }

    @Override
    public BetToken getBetToken() {
        return betToken;
    }

    @Override
    public int numberOFBetsMade() {
        return listOfBetsMadeByTheRound.size();
    }
}
