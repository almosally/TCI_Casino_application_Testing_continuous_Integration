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
 * The other functionality is the BetLoggingAuthority, which is used for logging all betting round and gambler card information. The bet logging has the following behaviours:
 *
 */
public class BetLoggingAuthority implements IBetLoggingAuthority {

    /**
     * this method logs when a PlayerCard has been handed out to a Gambler.
     *
     * it's used for logging purposes by the betlogging authority.
     *
     */
    @Override
    public void handOutGamblingCard(GeneralID card){
        // timestamp and log
        // NOT RELEVANT TO KNOW THIS

    }

    /**
     * this method logs the PlayerCard which is turned in by a Gambler.
     * All betID's on it are logged by the authority.
     *
     *
     * it's used for logging purposes by the betlogging authority.
     *
     */
    @Override
    public void handInGamblingCard(GeneralID card, Set<BetID> betsMade){
        // timestamp and log
        // NOT RELEVANT TO KNOW THIS

    }



    /**
     * this method logs when a BettingRound starts. It should be called before any submitted bets are added to
     * a betting round.
     *
     * it's used for logging purposes by the betlogging authority.
     *
     */
    @Override
    public void startBettingRound(IBettingRound startingBettingRound){
        // timestamp and log
        // NOT RELEVANT TO KNOW THIS

    }


    /**
     * this method logs an accepted bet by a BettingRound. It should be called:
     * after startBettingRound is called,
     * before endBettingRound is called
     *
     *
     * it's used for logging purposes by the betlogging authority.
     *
     */
    @Override
    public void addAcceptedBet(Bet acceptedBet, BettingRoundID bettingRoundID, GamingMachineID gamingMachineID){
        // NOT RELEVANT TO KNOW THIS

    }

    /**
     * this method logs when a BettingRound ends. It should be called after the winner of a betting round
     * has been decided. all information in the betting Round are logged by the authority.
     * <p>
     * it's used for logging purposes by the betlogging authority.
     *
     * @param endedBettingRound - IBettingRound
     * @param result - BetResult
     */
    @Override
    public void endBettingRound(IBettingRound endedBettingRound, BetResult result) {
        // timestamp and log
        // NOT RELEVANT TO KNOW THIS

    }



}
