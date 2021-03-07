public class Player {
	private int totalGuesses;
	private int correctGuesses;
	private int solved;
	public Player() {
		totalGuesses = 0;
		correctGuesses = 0;
		solved = 0;
	}
	
		public int getSolved() {
		return solved;
	}
	
	public void addTotalGuesses() {
		totalGuesses ++;
	}
	
	public void addCorrectGuesses() {
		correctGuesses++;
	}
	
	public void addSolved() {
		solved++;
	}
}
