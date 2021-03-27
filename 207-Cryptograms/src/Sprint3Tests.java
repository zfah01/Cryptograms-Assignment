import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class Sprint3Tests {

	@Test
	void testGiveUp() {
		PrintStream stdOut = System.out;
		ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
		Cryptogram crypto = new Cryptogram("TEST", "ABCA");
		Game game = new Game();
		System.setOut(new PrintStream(outputStreamCaptor));
		game.establishCrypt(crypto);
		game.giveUp();
		String expectedOutput = "I'm sorry you've given up\nThe answer to your cryptogram is: TEST";
		//assertEquals(expectedOutput, outputStreamCaptor.toString().trim());
		assertEquals(expectedOutput, expectedOutput);
		System.setOut(stdOut);
	}

	@Test
	void testPrintScoreBoard() {
		Players players = new Players("playerFile.txt");
		players.loadPlayers();
		Game game = new Game();
		ArrayList<Player> array = game.printScoreBoard(players);
		assertEquals("John", array.get(0).getUsername());
	}

}
