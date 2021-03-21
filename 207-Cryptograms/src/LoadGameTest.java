import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import com.google.gson.JsonObject;
import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;

class LoadGameTest {

    @Test
    void testCreateLoadedGame_given_json_returns_game_instance_letter() {
        Gson gson = new Gson();
        JsonObject gameJson1 = new JsonObject();
        gameJson1.addProperty("player", "Saeed");
        gameJson1.addProperty("guesses", "[\"T\",\"U\",\"U\"]");
        gameJson1.addProperty("crypt", "[\"Q\",\"W\",\"E\"]");
        gameJson1.addProperty("crypt2", "[\"Q\",\"W\",\"R\"]");
        gameJson1.addProperty("values", "[\"Q\",\"W\",\"C\"]");
        gameJson1.addProperty("valuePlaces", "[1,2,3]");
        gameJson1.addProperty("answer", "[\"A\",\"B\",\"C\"]");
        gameJson1.addProperty("mapped", 5);
        gameJson1.addProperty("time", "2021/03/15 12:30:42");
        Cryptogram cryptogram = new LettersCryptogram("phrase");
        gameJson1.addProperty("cryptogram", gson.toJson(cryptogram));

        Game returnedGame = Driver.createLoadedGame(gameJson1);
        List<String> guessesExpected = asList("T", "U", "U");
        assertArrayEquals(guessesExpected.toArray(), returnedGame.guesses.toArray());
        assertEquals(5, returnedGame.mapped);
        assertTrue(returnedGame.getCryptogram() instanceof LettersCryptogram);
    }

    @Test
    void testCreateLoadedGame_given_json_returns_game_instance_number() {
        Gson gson = new Gson();
        JsonObject gameJson1 = new JsonObject();
        gameJson1.addProperty("player", "Saeed");
        gameJson1.addProperty("guesses", "[\"T\",\"U\",\"U\"]");
        gameJson1.addProperty("crypt", "[\"Q\",\"W\",\"E\"]");
        gameJson1.addProperty("crypt2", "[\"Q\",\"W\",\"R\"]");
        gameJson1.addProperty("values", "[\"Q\",\"W\",\"C\"]");
        gameJson1.addProperty("valuePlaces", "[1,2,3]");
        gameJson1.addProperty("answer", "[\"A\",\"B\",\"C\"]");
        gameJson1.addProperty("mapped", 5);
        gameJson1.addProperty("time", "2021/03/15 12:30:42");
        Cryptogram cryptogram = new NumbersCryptogram("phrase");
        gameJson1.addProperty("cryptogram", gson.toJson(cryptogram));

        Game returnedGame = Driver.createLoadedGame(gameJson1);
        List<String> guessesExpected = asList("T", "U", "U");
        assertArrayEquals(guessesExpected.toArray(), returnedGame.guesses.toArray());
        assertEquals(5, returnedGame.mapped);
        assertTrue(returnedGame.getCryptogram() instanceof NumbersCryptogram);
    }

}
