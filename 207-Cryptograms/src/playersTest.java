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


		//player with the username "Harry" exists
		Players playerGameMapping = new Players();
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

		Players playerGameMapping = new Players();
		playerGameMapping.loadPlayers();
		Player player = Driver.loadPlayer("Harry", playerGameMapping);
	    assertEquals(0, player.getCryptogramsPlayed());
	    assertNotNull(player); //newPlayer has been created
	   
	}
	
	
	
}


