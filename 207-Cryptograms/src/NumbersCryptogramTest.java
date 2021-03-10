import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class NumbersCryptogramTest {
	
	//this makes sure that thes super class has been initualised, no need to test every function though
	@Test
	void checkphrasePassedthrough() {
		NumbersCryptogram crypt = new NumbersCryptogram("test");
		assertEquals("test", crypt.getPhrase());
	}
	
	//test to make sure it gets encrypted
	//as the encryption is random every time it can't be predicted
	//but we can make sure its not the same as the phrase
	@Test
	void LettersEncryption() {
		NumbersCryptogram crypt = new NumbersCryptogram("test");
		assertNotEquals("test", crypt.getEncrypted());
	}

}
