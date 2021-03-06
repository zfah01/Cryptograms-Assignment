import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

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
		fail("Not yet implemented");
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
