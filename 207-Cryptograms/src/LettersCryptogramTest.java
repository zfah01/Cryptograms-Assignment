import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
class LettersCryptogramTest {
	
	//this makes sure that thes super class has been initualised, no need to test every function though
	@Test
	void checkphrasePassedthrough() {
		LettersCryptogram crypt = new LettersCryptogram("test");
		assertEquals("test", crypt.getPhrase());
	}
	
	//test to make sure it gets encrypted
	//as the encryption is random every time it can't be predicted
	//but we can make sure its not the same as the phrase
	@Test
	void LettersEncryption() {
		LettersCryptogram crypt = new LettersCryptogram("test");
		assertNotEquals("test", crypt.getEncrypted());
	}
}
