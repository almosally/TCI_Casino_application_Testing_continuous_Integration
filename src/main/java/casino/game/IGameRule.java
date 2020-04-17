package casino.game;

import casino.bet.Bet;
import casino.bet.BetResult;

import java.util.Set;

/**
 * interface for gamerules
 *
 * gamerules determine the amount of bets are maximum needed from BettingRounds
 *
 */
public interface IGameRule {

    /**
     * Determine the winner from a Set of Bets, using the random win value;
     * <div>
     *     <p>the gambling round ask the gambling authority for a random whole-number "RandomWinValue" to determine the winner</p>
     * </div>
     * @param randomWinValue
     * @param bets
     * @return whole-number value from (randomWinValue)
     */
    BetResult determineWinner(Integer randomWinValue, Set<Bet> bets);

    /**
     * return the maximum number of bets which are used in the calculation of the winner.
     *
     * @return
     */
    int getMaxBetsPerRound();

}
