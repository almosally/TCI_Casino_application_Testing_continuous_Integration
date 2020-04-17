package casino.gamingmachine;

import casino.bet.BetResult;
import casino.cashier.IPlayerCard;
import exceptions.NoPlayerCardException;
import models.GamingMachineID;

public interface IGamingMachine {
    /**
     * <div>
     * <strong>Expected behaviour:</strong>
     * <ul>
     *     <li>Before new bet is placed, it is checked from Bank Teller that PlayerCard has enough money</li>
     *     <li>Credit of the card is only known by Bank Teller and GamingMachine</li>
     *     <li>Bet amount must be larger than 0 and not null</li>
     *     <li>Bet amount is long.</li>
     *     <li>Given PlayerCard is valid, if not throw NoPlayerCardException</li>
     *     <li>Amount of bet is decreased from PlayerCard</li>
     *     <li>Only GamingMachine submits bets to the game</li>
     * </ul>
     * </div>
     *
     * @param amountInCents - Amount of bets in cents
     * @return boolean
     * @throws NoPlayerCardException - Given PlayerCard is not valid
     */
    boolean placeBet(long amountInCents) throws NoPlayerCardException;

    /**
     * Accept the BetResult from the winner. clear all open bets on this machine.
     * when the winner has made his bet in this machine: let the Cashier update the amount.
     *
     * <div>
     * <strong>Expected behaviour:</strong>
     * <ul>
     *     <li>clear all open bets on this machine</li>
     *     <li> when the winner has made his bet in this machine: let the Cashier update the amount.</li>
     *     <li> If BetResult is not same Bet as in GamingMachine, throw IllegalArgumentException.</li>
     * </ul>
     * </div>
     *
     * @param winResult - BetResult
     */
    void acceptWinner(BetResult winResult) throws IllegalArgumentException;

    /**
     * <div>
     * <strong>Expected behaviour:</strong>
     * <ul>
     *     <li>Gaming machines are described by unique IDs</li>
     * </ul>
     * </div>
     * getter
     *
     * @return gamingmachineID
     */
    GamingMachineID getGamingMachineID();

    /**
     * <div>
     * <strong>Expected behaviour:</strong>
     * <ul>
     *     <li>Player can place bet by using a his PlayerCard</li>
     * </ul>
     * </div>
     *
     * @param card - IPlayerCard
     */
    void connectCard(IPlayerCard card);
}
