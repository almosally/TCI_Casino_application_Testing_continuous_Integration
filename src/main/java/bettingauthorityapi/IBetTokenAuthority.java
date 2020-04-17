package bettingauthorityapi;

import models.BettingRoundID;

/**
 * This interface creates BetTokens:
 * A BetToken consists of a unique number, and a timestamp when it was created.
 */
public interface IBetTokenAuthority {
    /**
     * BetToken consists of a unique number, and a timestamp when it was created.
     * @param bettingRoundID - BettingRoundID
     * @return token - a unique number, and a timestamp when it was created
     */
    BetToken getBetToken(BettingRoundID bettingRoundID);

    /**
     * Method provides a true random integer to determine the outcome of each betting round in a game.
     * @param betToken - BetToken
     * @return randomInteger - A true random integer
     */
    Integer getRandomInteger(BetToken betToken);
}
