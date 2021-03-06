import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CryptogramTest {

	@Test
	void testGetEncrypted() {
		Cryptogram crypto = new Cryptogram("test", "abca");
		assertEquals("abca", crypto.getEncrypted());
	}

	@Test
	void testGetPhrase() {
		Cryptogram crypto = new Cryptogram("test", "abca");
		assertEquals("test", crypto.getPhrase());
	}

	@Test
	void testGetFrequency() {
		//frequency doesn't properly work yet but wasn't required this sprint
	}
	
	@Test
	void getEncryptedArrayList() {	
		Cryptogram crypto = new Cryptogram("test", "abca");
		ArrayList<String> testlist = new ArrayList<String>();
		testlist.add("a");
		testlist.add("b");
		testlist.add("c");
		testlist.add("a");
		assertEquals(testlist, crypto.getEncryptedArrayList());
	}
	
	@Test
	void getPhraseArrayList() {
		
	}
	@Test
	void testCreateMap() {
		fail("Not yet implemented");
	}

	@Test
	void testGetLetter() {
		fail("Not yet implemented");
	}

}
