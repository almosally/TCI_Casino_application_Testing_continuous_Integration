package casino.gamingmachine;

import casino.bet.Bet;
import casino.bet.BetResult;
import casino.bet.MoneyAmount;
import casino.cashier.ICashier;
import casino.cashier.IPlayerCard;
import casino.game.IGame;
import casino.idfactory.IDFactory;
import exceptions.NoPlayerCardException;
import models.BetID;
import models.GamingMachineID;

public class GamingMachine implements IGamingMachine {

    private GamingMachineID id;
    private IPlayerCard playerCard;
    private ICashier cashier;
    private IGame game;
    private Bet openBet;

    public GamingMachine(ICashier cashier, IGame game) {
        IDFactory idFactory = new IDFactory();
        this.id = (GamingMachineID) idFactory.createId("GamingMachineID");
        this.cashier = cashier;
        this.game = game;
        this.openBet = null;
    }

    @Override
    public boolean placeBet(long amountInCents) throws NoPlayerCardException {
        if (this.playerCard == null) {
            throw new NoPlayerCardException();
        }

        if (amountInCents <= 0) {
            throw new IllegalArgumentException("Amount must be larger than 0");
        }

        // GamingMachine can have only one open bet at a time.
        if (this.openBet != null) {
            return false;
        }

        // Generate new BetID
        BetID betId = this.playerCard.generateNewBetID();
        // Generate new MoneyAmount
        MoneyAmount moneyAmount = new MoneyAmount(amountInCents);
        // Create new Bet
        Bet bet = new Bet(betId, moneyAmount);

        // Only accept bets validated by Cashier.
        if (!this.cashier.checkIfBetIsValid(playerCard, bet)) {
            return false;
        }

        //Assign self to variable and pass to IGame
        IGamingMachine thisMachine = this;

        // Verify that Game accepts the bet
        if (!this.game.acceptBet(bet, thisMachine)) {
            return false;
        }

        // Finally, store Bet to GamingMachine
        this.openBet = bet;

        return true;
    }

    @Override
    public void acceptWinner(BetResult winResult) throws IllegalArgumentException {

        if (winResult.getWinningBet() != this.openBet) {
            throw new IllegalArgumentException("Winning Bet is not same as in this GamingMachine!");
        }

        this.cashier.addAmount(this.playerCard, winResult.getAmountWon());

        this.openBet = null;
    }

    @Override
    public GamingMachineID getGamingMachineID() {
        return this.id;
    }

    @Override
    public void connectCard(IPlayerCard card) {
        this.playerCard = card;

    }

    public IPlayerCard getPlayerCard() throws NoPlayerCardException {

        if (this.playerCard == null) {
            throw new NoPlayerCardException();
        }

        return this.playerCard;
    }

    public Bet getOpenBet() {
        return openBet;
    }

}
