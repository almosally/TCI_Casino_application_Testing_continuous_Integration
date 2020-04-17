package casino;

import casino.game.IGame;

import java.util.HashMap;

public interface ICasino {
    /**
     * method to add a named game to the casino
     * <p>Expected Behaviour: Game is described by name and Game</p>
     *
     *
     * @param gameToAdd
     */
    void addGame(String gameName, IGame gameToAdd);

    /**
     * method to get a game in a casino
     * <p>
     *
     *
     * @return
     */
    IGame getGame(String name);

    /**
     * check if bet is valid.
     *
     * Team agreed to only user ICashier method for checking if Bet is Valid.
     *
     * @param card       card which makes the bet
     * @param betToCheck bet to check if it's possible to make using this card.
     * @return true when bet is possible, otherwise false
     */
//    boolean checkIfBetIsValid(IPlayerCard card, Bet betToCheck);


    /**
     * get all games in casino
     * @return all games in casino
     */
    HashMap<String, IGame> getListOfGames();

}
