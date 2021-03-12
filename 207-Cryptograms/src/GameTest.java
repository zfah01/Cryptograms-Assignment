import static org.junit.jupiter.api.Assertions.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GameTest {
	@Test
	void testOnStartup() throws IOException {
		Game game = new Game();
		game.onStartup();
		ArrayList<String> test= new ArrayList<String>(); //arraylist with expected input
		test.add("THAT DOG WAS FOLLOWING ME ALL DAY");
		test.add("EVEN I HATE ME SOMETIMES");
		test.add("THE FLOWERS ARE BEAUTIFUL AND THEY SMELL NICE");
		test.add("JAVA PROGRAMMING IS EASIER THAN C PROGRAMMING");
		test.add("FAITH IS A STATE OF OPENNESS OR TRUST");
		test.add("PEOPLE LIVING DEEPLY HAVE NO FEAR OF DEATH");
		test.add("STUDY SMARTER NOT HARDER");
		test.add("TO BE HEALTHY DO YOUR EXERCISES");
		test.add("THE DOCTOR WAS BUSY TODAY");
		test.add("ALL THE ITEMS IN THE STORE SOLD OUT");
		test.add("ALL THE RESTAURANTS IN THE TOWN ARE CLOSED");
		test.add("IT IS COLD IN THE WINTER BUT IT IS WARM IN THE SUMMER");
		test.add("HASKELL IS PROGRAMMING LANGUAGE");
		test.add("WE WERE ALL HAPPY");
		test.add("I BOUGHT YOU A GIFT FOR YOUR BIRTHDAY");
		test.add("STAY INSIDE YOUR HOME TO BE SAFE");
		assertEquals(test, game.getPhrases());
	}

	/*
		@Test
		void testDecideCryptogramforWrong() throws IOException {
			
			Game game = new Game();
			//String input = "Not good answer";
			PrintStream standardout = System.out;
			ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
			System.setOut(new PrintStream(outputStreamCaptor));
		    ByteArrayInputStream in1 = new ByteArrayInputStream("not good answer".getBytes());
		    ByteArrayInputStream in2 = new ByteArrayInputStream("letters".getBytes()); //this breaks the infite loop
		    SequenceInputStream in = new SequenceInputStream(in1,in2);
		    System.setIn(in);
			Scanner scan = new Scanner(System.in);
			game.decideCryptogram(scan);
			assertEquals("Sorry that doesn't appear to be an option, would you like numbers or letters?", outputStreamCaptor.toString().trim());
			System.setOut(standardout);
			
		} */
	@Test
	void testDecideCryptogramforLetter() throws IOException {
		Game game = new Game();
		game.onStartup();
		Cryptogram cryp = new LettersCryptogram("Testing");
		Player player = new Player();
		game.establishCrypt(cryp);
		ByteArrayInputStream in = new ByteArrayInputStream("letters".getBytes());
		System.setIn(in);
		Scanner scan = new Scanner(System.in);
		game.decideCryptogram(scan);
		Cryptogram crypto = game.getCryptogram();
		char firstLetter = crypto.getEncrypted().charAt(0);
		assertFalse(Character.isDigit(firstLetter));
	}
	
	//theres a lot of distincations between so better keep them seperate
	@Test
	void testDecideCryptogramforNumber() throws IOException {
		Game game = new Game();
		game.onStartup();
		Cryptogram cryp = new LettersCryptogram("Testing");
		Player player = new Player();
		game.establishCrypt(cryp);
		ByteArrayInputStream in = new ByteArrayInputStream("numbers".getBytes());
		System.setIn(in);
		Scanner scan = new Scanner(System.in);
		game.decideCryptogram(scan);
		Cryptogram crypto = game.getCryptogram();
		char firstLetter = crypto.getEncrypted().charAt(0);
		assertTrue(Character.isDigit(firstLetter));
	}
	
	//this does work, just the print stream isn't an exact match
	@Test
	void testPrintEncryption() throws IOException {
		//String input = "Not good answer";
		Game game = new Game();
		game.onStartup();
		Cryptogram crypto = new Cryptogram("test", "abca");
		Player player = new Player();
		game.establishCrypt(crypto);
		PrintStream standardout = System.out;
		ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outputStreamCaptor));
		
		game.printEncryption();
		String expected = crypto.getEncrypted() + "\nFrequencies\na b c d e f g h i j k l m n o p q r s t u v w x y z\n0 0 0 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 1 2 0 0 0 0 0 0";
		assertEquals(expected, outputStreamCaptor.toString().trim());
		System.setOut(standardout);
	}
	
	/* The next 4 tests can only be run 1 at a time because they all use a scanner*/
	@Test
	void testguess() {
		Game game = new Game();
		Cryptogram cryp = new LettersCryptogram("Testing");
		Player player = new Player();
		game.establishCrypt(cryp);
		String guess = "t";//make sure this is what you put in the scanner when asked
		//the value entered should be the first letter displayed in terminal
		System.out.println(cryp.getEncrypted());
		game.enterLetter(player);
		assertEquals(guess, game.crypt.get(0));//check that guess has replaced where value was
		
	}
	
	@Test
	void testErrorMessage() {
		Game game = new Game();
		Cryptogram cryp = new LettersCryptogram("Testing");
		Player player = new Player();
		game.establishCrypt(cryp);
		game.guesses.add("a");
		game.values.add(game.crypt.get(0));
		game.valuePlaces.add(0);
		System.out.println(cryp.getEncrypted());
		game.enterLetter(player);//input a
		assertTrue(game.checkPrint);
	}
	
	@Test
	void testAlreadyMapped() throws IOException {
		Game game = new Game();
		Cryptogram cryp = new LettersCryptogram("Testing");
		Player player = new Player();
		game.establishCrypt(cryp);
		String guess = "t";//make sure this is what you put in the scanner when asked
		//the value entered should be the first letter displayed in terminal
		game.values.add(game.crypt.get(0));
		game.valuePlaces.add(0);
		System.out.println(cryp.getEncrypted());
		game.enterLetter(player);
		//enter y for test to pass
		assertEquals(guess, game.crypt.get(0));//check that guess has replaced where value was
		//assertEquals(value, values.get(0));
	}
	
	@Test
	void testNotComplete() {
		Game game = new Game();
		Cryptogram cryp = new LettersCryptogram("Testing");
		Player player = new Player();
		game.establishCrypt(cryp);
		game.mapped = 6;
		String guess = "g";
		game.values.add(game.crypt.get(0));
		game.values.add(game.crypt.get(1));
		game.values.add(game.crypt.get(2));
		game.values.add(game.crypt.get(4));
		game.values.add(game.crypt.get(5));
		game.valuePlaces.add(0);
		game.valuePlaces.add(1);
		game.valuePlaces.add(2);
		game.valuePlaces.add(4);
		game.valuePlaces.add(5);
		game.guesses.add("t");
		game.guesses.add("a");
		game.guesses.add("s");
		game.guesses.add("i");
		game.guesses.add("n");
		game.crypt.set(0, "t");
		game.crypt.set(1, "a");
		game.crypt.set(2, "s");
		game.crypt.set(3, "t");
		game.crypt.set(4, "i");
		game.crypt.set(5, "n");
		System.out.println(cryp.getEncrypted());
		System.out.println(game.crypt.size());
		game.enterLetter(player);
		assertTrue(player.getSolved() == 0);//checks that solved wasn't incremented
		
	}
	
	@Test
	public void testLetterWithoutGuess() {
	 Game game = new Game();
	 
	 Cryptogram cryp = new LettersCryptogram("test");
	 game.establishCrypt(cryp);
	 ArrayList<String> crypt = cryp.getEncryptedArrayList();
	 System.out.println(cryp.getEncrypted());
	 
	 	
	 String letter = crypt.get(0);
	 int guessedAt = 0;	
     
	 assertEquals(letter,crypt.get(guessedAt) );
	 game.undoLetter();
	 
	}
	
	@Test
	public void testLetterWithGuess() {
		Game game = new Game();
		Cryptogram cryp = new Cryptogram("test", "abca");
		game.establishCrypt(cryp);
		//this simulates a guess
		game.values.add(0, "a");
		game.valuePlaces.add(0);
		game.crypt.remove(0);
		game.crypt.add(0, "t");
		//type "a" here
		game.undoLetter();
		assertEquals(game.crypt.get(0), "a");
	}

}
