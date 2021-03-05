import java.io.IOException;
import java.util.Scanner;

public class Driver {

	public static void main(String[] args) {
		Game game = new Game();
		try {
			game.onStartup();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		Scanner myScan = new Scanner(System.in);
		System.out.println("Welcome to Cryptogram!!");
		System.out.println("Would you like a numbers or letters cryptogram?");
		String type = myScan.nextLine();
		if (type.equals("letters")) {
			Cryptogram crypto = game.createLetters();
		}
	}

}
