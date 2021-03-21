import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CryptogramTest {
/* 
 * Im using the cryptogram class itself because all the functions are in it
 * and you can't predict the encryption as its randomised every time
 * */
	Cryptogram crypto;
	@BeforeAll
	void setup() {
		crypto = new Cryptogram("test", "abca");
	}
	@Test
	void testGetEncrypted() {
		assertEquals("abca", crypto.getEncrypted());
	}

	@Test
	void testGetPhrase() {
		assertEquals("test", crypto.getPhrase());
	}

	@Test
	void testGetFrequency() {
		//frequency doesn't properly work yet but wasn't required this sprint
	}
	
	@Test
	void getEncryptedArrayList() {	
		ArrayList<String> testlist = new ArrayList<>();
		testlist.add("a");
		testlist.add("b");
		testlist.add("c");
		testlist.add("a");
		assertEquals(testlist, crypto.getEncryptedArrayList());
	}
	
	@Test
	void getPhraseArrayList() {
		ArrayList<String> testlist = new ArrayList<>();
		testlist.add("t");
		testlist.add("e");
		testlist.add("s");
		testlist.add("t");
		assertEquals(testlist, crypto.getPhraseArrayList());
	}
	@Test
	void testCreateMap() {
		//the create map works if both the arraylists are equal to what the parameters are
		ArrayList<String> phrase = new ArrayList<>();
		ArrayList<String> encryp = new ArrayList<>();
		//all the adds on one line to save space
		phrase.add("t"); phrase.add("e"); phrase.add("s"); phrase.add("t");
		encryp.add("a"); encryp.add("b"); encryp.add("c"); encryp.add("a");
		assertEquals(phrase, crypto.getPhraseArrayList());
		assertEquals(encryp, crypto.getEncryptedArrayList());
	}

	@Test
	void testGetLetter() {
		assertEquals("e", crypto.getLetter("b"));
		assertEquals("t", crypto.getLetter("a")); //2 instances of the same letter to make sure it works still
	}

}
