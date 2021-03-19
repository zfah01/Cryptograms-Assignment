import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.junit.Test;

public class playersTest {
      
		Game game = new Game();   
	    Players players = new Players();
	
      
	@Test
	public void testLoadStats() {
        
		InputStream stdin = System.in;
		ByteArrayInputStream in = new ByteArrayInputStream("Harry".getBytes());
		Player player = players.findPlayer("Harry");
		System.setIn(in);
		game.loadPlayer();
		System.setIn(stdin);
		
       assertTrue(game.loadPlayer().equals(player));
       
	}
	/*
	@Test
	public void testErrorLoadStats() {
		InputStream stdin = System.in;
		ByteArrayInputStream in = new ByteArrayInputStream("Harry".getBytes());
		System.setIn(in);
		
		//File nonExistingFile = new File("blabla.txt");
		//assertThrows(FileNotFoundException.class,() ->);
	}

	@Test
	public void testErrorLoadStatsCreateNewPlayer() {

		InputStream stdin = System.in;
		ByteArrayInputStream in = new ByteArrayInputStream("test".getBytes());
		System.setIn(in);
		game.loadPlayer();
		System.setIn(stdin);
	
		assertThrows(IllegalArgumentException.class,() -> players.loadPlayers());
	}
	
	*/
	
}


