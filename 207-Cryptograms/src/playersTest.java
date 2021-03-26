import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

import org.junit.Test;

public class playersTest {
      
		Game game = new Game();   
	    Players players = new Players("playerFile.txt");
		
	@Test
	public void testLoadStats() {

		//player with the username "Harry" exists
		Players playerGameMapping = new Players("playerFile.txt");
		playerGameMapping.loadPlayers();
		Player player = Driver.loadPlayer("Harry", playerGameMapping);
		player.printPlayerStats();
        assertEquals(24.6, player.getAccuracy()); //checks if player has accuracy of 24.6

	}
	
/*	@Test(expected = FileNotFoundException.class)
	public void testErrorLoadStats() {
		InputStream stdin = System.in;
		//player with the username "Harry" exists
		ByteArrayInputStream in = new ByteArrayInputStream("Harry".getBytes());
		System.setIn(in);
		game.loadPlayer();
		System.setIn(stdin);
		
		
		assertThrows(FileNotFoundException.class,() -> { 
		players.loadPlayers();
		});
	} */

	@Test
	public void testCreateNewPlayer() {

		Players playerGameMapping = new Players("playerFile.txt");
		playerGameMapping.loadPlayers();
		Player player = Driver.loadPlayer("Harry", playerGameMapping);
	    assertEquals(5, player.getCryptogramsPlayed());
	    assertNotNull(player); //newPlayer has been created
	   
	}
	
	@Test
	public void testFile() {
		Game game = new Game();
		Cryptogram cryp = new LettersCryptogram("testing");
		Player player = new Player(0, 0, 0, 0, 0, "name");
		game.establishCrypt(cryp);
		Players playerGameMapping = new Players("playerFile.txt");
		//playerGameMapping.loadPlayers();
		//playerGameMapping.savePlayer("y", "name");

		boolean fileExists = player.getPlayerFile().exists();
		assertTrue(fileExists);
	}
	

	@Test
	public void testFileWrites() throws FileNotFoundException {
		//Game game = new Game();
		//Cryptogram cryp = new LettersCryptogram("testing");
		Player player = new Player(0, 0, 0, 0, 0, "name");
		String saveUser;
		//game.establishCrypt(cryp);
		Players playerGameMapping = new Players("playertest.txt");
		//playerGameMapping.loadPlayers();
		playerGameMapping.addPlayer(player);
		playerGameMapping.savePlayer("y", "name");
		File file = new File("playertest.txt");
		Scanner reading = new Scanner(file);
		saveUser = reading.next().trim();
		assertEquals(saveUser, player.getUsername());
	}

}


