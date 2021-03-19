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
		game.decideCryptogram(myScan); //this had to be a seperate method otherwise it couldn't find it from if statements
		game.printEncryption();
		String responce;
		System.out.println("Please enter a username: ");
		responce = myScan.nextLine();
		Player player = new Player(0, 0, 0, 0, 0, responce);
		while (true) {
			System.out.println("What would you like to do? enter the number");
			System.out.println("(1) make guess    (2) remove guess (3) give up (4) leave");
			responce = myScan.nextLine();
			if(responce.equals("1")) {
				game.enterLetter(player); //this exists in another branch
			} else if (responce.equals("2")) {
			
				game.undoLetter(); //this also exists in another branch
			} else if (responce.equals("3")) {
				game.giveUp();
				game.decideCryptogram(myScan);
				game.printEncryption();
			} else if (responce.equals("4")) {
				System.out.print("Do you want to save your profile? y/n");
				responce = myScan.nextLine();
				if(responce.equals("y")){
					game.callSavePlayer(player);
				}
				break;
			} else {
				System.out.println("I'm sorry that doesn't seem to be a valid input, please try again");
			}
		}
	}
}


