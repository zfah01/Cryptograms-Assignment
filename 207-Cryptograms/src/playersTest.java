import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;


import org.junit.Test;

public class playersTest {
      
		Game game = new Game();   
	    Players players = new Players();
		
	@Test
	public void testLoadStats() {

		InputStream stdin = System.in;
		//player with the username "Harry" exists
		ByteArrayInputStream in = new ByteArrayInputStream("Harry".getBytes());
		System.setIn(in);
		Player test = game.loadPlayer();
		game.printPlayerStats();
		System.setIn(stdin);
        assertTrue(test.getAccuracy() == 24.6); //checks if player has accuracy of 24.6
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

		InputStream stdin = System.in;
		// player with the name "test" does not exist
		ByteArrayInputStream in = new ByteArrayInputStream("test".getBytes()); 
		System.setIn(in);
		Player newPlayer = game.loadPlayer();
		game.printPlayerStats();
		System.setIn(stdin);
	    assertTrue(newPlayer.getCryptogramsPlayed() == 0);
	    assertNotNull(newPlayer); //newPlayer has been created
	   
	}
	
	
	
}


