package casino.game;

import bettingauthorityapi.IBetTokenAuthority;
import casino.bet.Bet;
import casino.bet.BetResult;
import casino.bet.MoneyAmount;
import exceptions.BetNotExceptedException;

import java.util.Set;

public class GameRule implements IGameRule {

    private int maxBetPerRound;

    private IBetTokenAuthority betTokenAuthority;

    public GameRule(IBetTokenAuthority betTokenAuthority) {
        this.betTokenAuthority = betTokenAuthority;
    }

    public IBetTokenAuthority getBetTokenAuthority() {
        return betTokenAuthority;
    }

    @Override
    public BetResult determineWinner(Integer randomWinValue, Set<Bet> bets) {
        if (bets.isEmpty()) {
            throw new BetNotExceptedException();
        }
        while (randomWinValue > bets.size()) {
            randomWinValue = -bets.size();
        }

        Bet winningBet = null;
        int credits = 0;

        int currentIndex = 0;
        for (Bet bet : bets) {

            if (currentIndex == randomWinValue) {
                winningBet = bet;
            }
            credits += bet.getMoneyAmount().getAmountInCents();
            currentIndex++;
        }

        return new BetResult(winningBet, new MoneyAmount(credits));
    }

    @Override
    public int getMaxBetsPerRound() {
        if (maxBetPerRound > 0) {
            return maxBetPerRound;
        }
        throw new BetNotExceptedException();
    }

    public int setMaxBetPerRound(int num) {
        return maxBetPerRound = num;
    }
}
