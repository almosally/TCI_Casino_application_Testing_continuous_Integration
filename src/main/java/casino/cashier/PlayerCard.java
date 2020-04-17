package casino.cashier;

import casino.bet.MoneyAmount;
import casino.idfactory.IDFactory;
import models.BetID;
import models.CardID;

import java.util.HashSet;
import java.util.Set;

public class PlayerCard implements IPlayerCard {

    private CardID cardID;
    private Set<BetID> betIDs = new HashSet<BetID>();
    private MoneyAmount moneyAmount;

    public PlayerCard(CardID cardID) {
        this.cardID = cardID;
    }

    @Override
    public Set<BetID> returnBetIDs() {
        return betIDs;
    }

    @Override
    public Set<BetID> returnBetIDsAndClearCard() {
        Set<BetID> oldBetIDs = null;
        oldBetIDs = returnBetIDs();

        betIDs.clear();

        return oldBetIDs;
    }

    @Override
    public BetID generateNewBetID() {
        IDFactory idFactory = new IDFactory();
        BetID betID = (BetID) idFactory.createId("BetID");

        betIDs.add(betID);

        return betID;
    }

    @Override
    public int getNumberOfBetIDs() {
        return betIDs.size();
    }

    @Override
    public CardID getCardID() {
        return cardID;
    }

    public MoneyAmount getMoneyAmount() {
        return moneyAmount;
    }

    public void setMoneyAmount(MoneyAmount moneyAmount) {
        this.moneyAmount = moneyAmount;
    }
}
