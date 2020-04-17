package casino.game;

import casino.bet.Bet;
import casino.gamingmachine.IGamingMachine;
import exceptions.NoCurrentRoundException;

/**
 * represents a game in a casino.
 * bets can be made on the current betting round.
 * Keeps track of all gamingmachines which made bets on the game during a betting round.
 * determines the winner of a game using the used IGameRule.
 * notifies all gaming machines of the outcome of a betting round.
 * Games need to be overseen by a BettingAuthority.
 */
public interface IGame {

    /**
     * create and start a new BettingRound.
     * when called when a current bettinground is active: the current bettinground ends and a new
     * bettinground is created, which becomes the current bettinground.
     *
     */
    void startBettingRound();

    /**
     * Accept a bet on the current betting round.
     *
     * log relevant information for the betloggingauthority.
     *
     * @param bet the bet to be made on the betting round
     * @param gamingMachine gamingmachine which places bet on this game.
     * @return true when bet is accepted by the game, otherwise false.
     * @throws NoCurrentRoundException when no BettingRound is currently active.
     */
    boolean acceptBet(Bet bet, IGamingMachine gamingMachine) throws NoCurrentRoundException;


    /**
     * Calculate the winner using the gamerules.
     * Let the gamingMachine update the winner's amount at the bank teller
     *
     * log relevant information for the betloggingauthority.
     */
    void determineWinner();

    /**
     * determine if the right number of bets are done (determined by gamerules) to be able to
     * calculate a winner.
     * @return true if all necessary bets are made in the betting round, otherwise false
     */
    boolean isBettingRoundFinished();
}
