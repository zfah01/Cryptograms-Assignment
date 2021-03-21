import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.google.gson.JsonObject;
import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;

class SaveGameTest {

    @Test
    void testGetGameAsJsonObject_dummy_game_returns_json_object() {
        Gson gson = new Gson();
        Player player = new Player("Saeed");
        ArrayList<String> guesses = new ArrayList<>(Arrays.asList("A", "B", "C"));
        ArrayList<String> crypt = new ArrayList<>(Arrays.asList("Q", "W", "E"));
        ArrayList<String> crypt2 = new ArrayList<>(Arrays.asList("Q", "W", "R"));
        ArrayList<String> values  = new ArrayList<>(Arrays.asList("Z", "X", "C"));
        ArrayList<Integer> valuePlaces  = new ArrayList<>(Arrays.asList(1, 2, 3));
        ArrayList<String> answer = new ArrayList<>(Arrays.asList("A", "S", "C"));
        int mapped = 5;
        Cryptogram cryptogram = new LettersCryptogram("phrase");
        LocalDateTime mockDateTimeNow = LocalDateTime.of(2021, 3, 15,12,30,42);
        Game game = new Game(guesses, crypt, crypt2, values, valuePlaces, answer, cryptogram, mapped);
        JsonObject json = game.getGameAsJsonObject(player, mockDateTimeNow);

        assertEquals("Saeed", json.get("player").getAsString());
        assertEquals("[\"A\",\"B\",\"C\"]", json.get("guesses").getAsString());
        assertEquals("[\"Q\",\"W\",\"E\"]", json.get("crypt").getAsString());
        assertEquals("[\"Q\",\"W\",\"R\"]", json.get("crypt2").getAsString());
        assertEquals("[\"Z\",\"X\",\"C\"]", json.get("values").getAsString());
        assertEquals("[1,2,3]", json.get("valuePlaces").getAsString());
        assertEquals("[\"A\",\"S\",\"C\"]", json.get("answer").getAsString());
        assertEquals(5, json.get("mapped").getAsInt());
        assertEquals("2021/03/15 12:30:42", json.get("time").getAsString());
        assertEquals("letters", gson.fromJson(json.get("cryptogram").getAsString(), Cryptogram.class).type);
    }

    @Test
    void testdeleteSameSavedGameFromSavedDataList_game_exists_remove_same_game(){
        JsonObject gameJson1 = new JsonObject();
        gameJson1.addProperty("player", "Saeed");
        gameJson1.addProperty("crypt", "[\"Q\",\"W\",\"E\"]");

        JsonObject gameJson2 = new JsonObject();
        gameJson2.addProperty("player", "Jack");
        gameJson2.addProperty("crypt", "[\"C\",\"P\",\"R\"]");

        JsonObject gameJson3 = new JsonObject();
        gameJson3.addProperty("player", "Saeed");
        gameJson3.addProperty("crypt", "[\"H\",\"W\",\"I\"]");

        List<JsonObject> savedList = new ArrayList<>();
        savedList.add(gameJson1);
        savedList.add(gameJson2);
        savedList.add(gameJson3);

        ArrayList<String> guesses = new ArrayList<>(Arrays.asList("A", "B", "C"));
        ArrayList<String> crypt = new ArrayList<>(Arrays.asList("Q", "W", "E"));
        ArrayList<String> crypt2 = new ArrayList<>(Arrays.asList("Q", "W", "R"));
        ArrayList<String> values  = new ArrayList<>(Arrays.asList("Z", "X", "C"));
        ArrayList<Integer> valuePlaces  = new ArrayList<>(Arrays.asList(1, 2, 3));
        ArrayList<String> answer = new ArrayList<>(Arrays.asList("A", "S", "C"));
        int mapped = 5;
        Cryptogram cryptogram = new LettersCryptogram("phrase");
        Game currentGame = new Game(guesses, crypt, crypt2, values, valuePlaces, answer, cryptogram, mapped);
        Player player = new Player("Saeed");
        currentGame.deleteSameSavedGameFromSavedDataList(savedList, player);

        assertEquals(2, savedList.size());
        assertEquals(gameJson2, savedList.get(0));
        assertEquals(gameJson3, savedList.get(1));

    }

    @Test
    void testdeleteSameSavedGameFromSavedDataList_game_not_exists_keep_same_list(){
        JsonObject gameJson1 = new JsonObject();
        gameJson1.addProperty("player", "Saeed");
        gameJson1.addProperty("crypt", "[\"Q\",\"W\",\"E\"]");

        JsonObject gameJson2 = new JsonObject();
        gameJson2.addProperty("player", "Jack");
        gameJson2.addProperty("crypt", "[\"C\",\"P\",\"R\"]");

        JsonObject gameJson3 = new JsonObject();
        gameJson3.addProperty("player", "Saeed");
        gameJson3.addProperty("crypt", "[\"H\",\"W\",\"I\"]");

        List<JsonObject> savedList = new ArrayList<>();
        savedList.add(gameJson1);
        savedList.add(gameJson2);
        savedList.add(gameJson3);

        ArrayList<String> guesses = new ArrayList<>(Arrays.asList("A", "B", "C"));
        ArrayList<String> crypt = new ArrayList<>(Arrays.asList("H", "W", "E"));
        ArrayList<String> crypt2 = new ArrayList<>(Arrays.asList("Q", "W", "R"));
        ArrayList<String> values  = new ArrayList<>(Arrays.asList("Z", "X", "C"));
        ArrayList<Integer> valuePlaces  = new ArrayList<>(Arrays.asList(1, 2, 3));
        ArrayList<String> answer = new ArrayList<>(Arrays.asList("A", "S", "C"));
        int mapped = 5;
        Cryptogram cryptogram = new LettersCryptogram("phrase");
        Game currentGame = new Game(guesses, crypt, crypt2, values, valuePlaces, answer, cryptogram, mapped);
        Player player = new Player("Saeed");
        currentGame.deleteSameSavedGameFromSavedDataList(savedList, player);

        assertEquals(3, savedList.size());
        assertEquals(gameJson1, savedList.get(0));
        assertEquals(gameJson2, savedList.get(1));
        assertEquals(gameJson3, savedList.get(2));

    }

    @Test
    void testSaveSavedGamesListToFile(){
        JsonObject gameJson1 = new JsonObject();
        gameJson1.addProperty("player", "Jack");
        gameJson1.addProperty("crypt", "[\"C\",\"P\",\"R\"]");

        JsonObject gameJson2 = new JsonObject();
        gameJson2.addProperty("player", "Saeed");
        gameJson2.addProperty("crypt", "[\"H\",\"W\",\"I\"]");

        List<JsonObject> savedList = new ArrayList<>();
        savedList.add(gameJson1);
        savedList.add(gameJson2);

        Gson mockGson = mock(Gson.class);
        FileWriter mockFileWriter = mock(FileWriter.class);
        //when(mockGson.toJson()).thenReturn(null);
    }
}
