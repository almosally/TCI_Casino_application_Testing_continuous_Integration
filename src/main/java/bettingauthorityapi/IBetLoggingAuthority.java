package bettingauthorityapi;

import casino.bet.Bet;
import casino.bet.BetResult;
import casino.game.IBettingRound;
import models.BetID;
import models.BettingRoundID;
import models.GamingMachineID;
import models.GeneralID;

import java.util.Set;

/**
 * Used for logging all betting activity and player card information.
 */
public interface IBetLoggingAuthority {

    /**
     * this method logs when a PlayerCard has been handed out to a Gambler.
     *
     * <div>
     *     <strong>Expected behaviour:</strong>
     *     <ul>
     *         <li>Method is called when PlayerCard has been handed out.</li>
     *         <li>PlayerCard valid and is described by unique id</li>
     *         <li>Event is logged with timestamp</li>
     *     </ul>
     * </div>
     *
     * @param card - Id of the PlayerCard
     */
    public void handOutGamblingCard(GeneralID card);

    /**
     * this method logs the PlayerCard which is turned in by a Gambler.
     * All betID's on it are logged by the authority.
     * it's used for logging purposes by the betlogging authority.
     *
     * <div>
     *      <strong>Expected behaviour:</strong>
     *      <ul>
     *      <li>Method is called when PlayerCard has been handed in.</li>
     *      <li>PlayerCard is described by unique id</li>
     *      <li>Event is logged with timestamp</li>
     *      </ul>
     * </div>
     *
     * @param card - ID of PlayerCard
     * @param betsMade - List of Bets made
     */
    public void handInGamblingCard(GeneralID card, Set<BetID> betsMade);

    /**
     * this method logs when a BettingRound starts. It should be called before any submitted bets are added to
     * a betting round.
     * it's used for logging purposes by the betlogging authority.
     *
     * <div>
     *      <strong>Expected behaviour:</strong>
     *      <ul>
     *      <li>Method is called before any submitted bets are added to a betting round.</li>
     *      <li>PlayerCard is described by unique id</li>
     *      </ul>
     * </div>
     *
     * @param startingBettingRound - ID of PlayerCard
     */
    void startBettingRound(IBettingRound startingBettingRound);

    /**
     * this method logs an accepted bet by a BettingRound. It should be called:
     * after startBettingRound is called,
     * before endBettingRound is called
     *
     * it's used for logging purposes by the betlogging authority.
     *
     * <div>
     * <strong>Expected behaviour:</strong>
     * <ul>
     *     <li>Method is called after startBettingRound is called.</li>
     *     <li>Method is called before endBettingRound is called</li>
     * </ul>
     * </div>
     * @param acceptedBet - accepted Bet
     * @param bettingRoundID - ID of betting round
     * @param gamingMachineID - ID of gaming machine
     *
     */
    void addAcceptedBet(Bet acceptedBet, BettingRoundID bettingRoundID, GamingMachineID gamingMachineID);

    /**
     * this method logs when a BettingRound ends. It should be called after the winner of a betting round
     * has been decided. all information in the betting Round are logged by the authority.
     * it's used for logging purposes by the betlogging authority.

     * <div>
     * <strong>Expected behaviour:</strong>
     * <ul>
     *     <li>Method is called after the winner of a betting round has been decided.</li>
     * </ul>
     * </div>
     * @param endedBettingRound - BettingRound
     * @param result - BetResult
     */
    void endBettingRound(IBettingRound endedBettingRound, BetResult result);
}
