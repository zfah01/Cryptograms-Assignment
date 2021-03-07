import static org.junit.jupiter.api.Assertions.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GameTest {
	
	@Test
	void testOnStartup() throws IOException {
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
		Game game = new Game();
		game.onStartup();
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
		ByteArrayInputStream in = new ByteArrayInputStream("letters".getBytes());
		System.setIn(in);
		Scanner scan = new Scanner(System.in);
		Cryptogram crypto = game.decideCryptogram(scan);
		char firstLetter = crypto.getEncrypted().charAt(0);
		assertFalse(Character.isDigit(firstLetter));
	}
	
	//theres a lot of distincations between so better keep them seperate
	@Test
	void testDecideCryptogramforNumber() throws IOException {
		Game game = new Game();
		game.onStartup();
		ByteArrayInputStream in = new ByteArrayInputStream("numbers".getBytes());
		System.setIn(in);
		Scanner scan = new Scanner(System.in);
		Cryptogram crypto = game.decideCryptogram(scan);
		char firstLetter = crypto.getEncrypted().charAt(0);
		assertTrue(Character.isDigit(firstLetter));
	}
	
	@Test
	void testPrintEncryption() throws IOException {
		Game game = new Game();
		game.onStartup();
		//String input = "Not good answer";
		PrintStream standardout = System.out;
		ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outputStreamCaptor));
		Cryptogram crypto = new Cryptogram("test", "abca");
		game.printEncryption(crypto);
		String expected = crypto.getEncrypted() + "\nFrequencies\na b c d e f g h i j k l m n o p q r s t u v w x y z\n0 0 0 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 1 2 0 0 0 0 0 0";
		assertEquals(expected, outputStreamCaptor.toString().trim());
		System.setOut(standardout);
	}

}
