package casino;

import bettingauthorityapi.BetLoggingAuthority;
import bettingauthorityapi.BetTokenAuthority;
import casino.game.IGame;

import java.util.HashMap;

public class Casino implements ICasino {

    private BetLoggingAuthority betLoggingAuthority;
    private BetTokenAuthority betTokenAuthority;
    private HashMap<String, IGame> games = new HashMap<>();

    public Casino(BetLoggingAuthority betLoggingAuthority, BetTokenAuthority betTokenAuthority) {
        this.betLoggingAuthority = betLoggingAuthority;
        this.betTokenAuthority = betTokenAuthority;
    }

    public BetLoggingAuthority getBetLoggingAuthority() {
        return betLoggingAuthority;
    }

    public BetTokenAuthority getBetTokenAuthority() {
        return betTokenAuthority;
    }

    @Override
    public void addGame(String gameName, IGame gameToAdd) throws IllegalArgumentException {

        if (gameName == null || gameName.equals("")) {
            throw new IllegalArgumentException("Game Name can't be null or empty!");
        }

        this.games.put(gameName, gameToAdd);
    }

    @Override
    public IGame getGame(String name) {

        for (String gameName : games.keySet()) {
            if (gameName.equals(name)) {
                return games.get(gameName);
            }

        }

        return null;
    }

    @Override
    public HashMap<String, IGame> getListOfGames() {
        return this.games;
    }
}
