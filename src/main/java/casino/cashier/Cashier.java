package casino.cashier;

import bettingauthorityapi.BetLoggingAuthority;
import casino.bet.Bet;
import casino.bet.MoneyAmount;
import casino.idfactory.IDFactory;
import exceptions.BetNotExceptedException;
import exceptions.NoPlayerCardException;
import models.BetID;
import models.CardID;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Cashier implements ICashier {

    private BetLoggingAuthority betLoggingAuthority;
    private IDFactory idFactory = new IDFactory();
    private List<IPlayerCard> distributedCards;
    private PlayerCard playerCard;
    private CardID cardID;

    public Cashier(BetLoggingAuthority betLoggingAuthority) {
        this.betLoggingAuthority = betLoggingAuthority;
        this.distributedCards = new ArrayList<>();
    }

    @Override
    public IPlayerCard distributeGamblerCard() {
        cardID = (CardID) idFactory.createId("CardID");
        IPlayerCard playerCard = new PlayerCard(cardID);

        betLoggingAuthority.handOutGamblingCard(cardID);
        this.distributedCards.add(playerCard);

        return playerCard;
    }

    @Override
    public void returnGamblerCard(IPlayerCard card) {
        resetAmount(card);
        cardID = card.getCardID();
        Set<BetID> betIDS = null;
        betIDS = card.returnBetIDsAndClearCard();
        distributedCards.remove(card);

        betLoggingAuthority.handInGamblingCard(cardID, betIDS);
    }

    @Override
    public boolean checkIfBetIsValid(IPlayerCard card, Bet betToCheck) throws BetNotExceptedException {
        playerCard = getPlayerCard(card);

        long playerHas = playerCard.getMoneyAmount().getAmountInCents();
        long betAmount = betToCheck.getMoneyAmount().getAmountInCents();
        if (playerHas < betAmount) {
            throw new BetNotExceptedException();
        } else {
            return true;
        }
    }

    @Override
    public void addAmount(IPlayerCard card, MoneyAmount amount) throws IllegalArgumentException {
        if (amount.getAmountInCents() < 0) {
            throw new IllegalArgumentException("Positive Amount Required!");
        }
        playerCard = getPlayerCard(card);
        playerCard.setMoneyAmount(amount);
    }

    public void resetAmount(IPlayerCard card) {
        getPlayerCard(card).setMoneyAmount(new MoneyAmount(0));
    }

    private PlayerCard getPlayerCard(IPlayerCard card) throws NoPlayerCardException {
        for (IPlayerCard iPlayerCard : distributedCards) {
            if (iPlayerCard.equals(card)) {
                return (PlayerCard) iPlayerCard;
            }
        }
        throw new NoPlayerCardException();
    }

    public List<IPlayerCard> getDistributedCards() {
        return distributedCards;
    }

    public BetLoggingAuthority getBetLoggingAuthority() {
        return betLoggingAuthority;
    }
}
