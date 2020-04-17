package casino;

import bettingauthorityapi.BetLoggingAuthority;
import bettingauthorityapi.BetTokenAuthority;
import casino.game.IGame;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.HashMap;

import static junitparams.JUnitParamsRunner.$;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

@RunWith(JUnitParamsRunner.class) // needed for parametrized tests
public class ICasinoTest {

    @Mock
    private BetLoggingAuthority betLoggingAuthority;
    @Mock
    private BetTokenAuthority betTokenAuthority;
    @Mock
    private IGame myGame;

    private String gameName = "Roulette";
    private Casino myCasino = new Casino(betLoggingAuthority, betTokenAuthority);

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    /**
     * Created By: Kasper
     * Test ICasino-1.0
     * Casino contains both a BetLoggingAuthority and BetTokenAuthority
     */
    @Test
    public void createCasino_CasinoContainsBetLoggingAuthorityAndBetTokenAuthority_True() {
        //arrange
        //act
        Casino myCasino = new Casino(betLoggingAuthority, betTokenAuthority);
        //assert
        assertEquals("Casino does not contain BetLoggingAuthority", myCasino.getBetLoggingAuthority(), betLoggingAuthority);
        assertEquals("Casino does not contain BetTokenAuthority", myCasino.getBetTokenAuthority(), betTokenAuthority);
    }

    /**
     * Created By: Kasper
     * Test ICasino-2.0
     * After adding new Game, it can be found by its name.
     */
    @Test
    public void addGame_GetGameByName_ReturnSameGame() {
        //arrange
        //act
        myCasino.addGame(gameName, myGame);
        //assert
        assertEquals("Game in Casino is not same as expected.", myCasino.getGame(gameName), myGame);
    }

    /**
     * Created By: Kasper
     * Test ICasino-3.0
     * After adding new Game to Casino, List of Games is not empty.
     */
    @Test
    public void addNewGame_GetListOfGames_ListIsNotEmpty() {
        //arrange
        //act
        myCasino.addGame(gameName, myGame);
        //assert
        assertTrue("Game is not added to Casino.", myCasino.getListOfGames().size() > 0);
    }

    private Object[] getInvalidGameNames() {
        return $(
                $((Object) null),
                $("")
        );
    }

    /**
     * Created By: Kasper
     * Test ICasino-4.0
     * Game name cant be null or empty, otherwise throw IllegalArgumentException
     * This is parameterized test
     */
    @Test(expected = IllegalArgumentException.class)
    @Parameters(method = "getInvalidGameNames")
    public void addgameNameIsNullOrEmpty_ThrowIllegalArgumentException(String gameName) {
        //arrange
        //act
        myCasino.addGame(gameName, myGame);
        //assert
    }

    /**
     * Created By: Kasper
     * Test ICasino-5.0
     * After adding multiple games to Casino, you can retrieve copy of all games
     */
    @Test
    public void addMultipleGames_GetListOfGames_IsCopyOfCreatedGames() {
        //arrange
        HashMap<String, IGame> myGames = new HashMap<>();

        IGame game1 = mock(IGame.class);
        IGame game2 = mock(IGame.class);
        IGame game3 = mock(IGame.class);

        myGames.put("Game1", game1);
        myGames.put("Game2", game2);
        myGames.put("Game3", game3);

        //act
        myCasino.addGame("Game1", game1);
        myCasino.addGame("Game2", game2);
        myCasino.addGame("Game3", game3);

        //assert
        assertEquals("Amount of games is not same.", myCasino.getListOfGames(), myGames);
    }
}
