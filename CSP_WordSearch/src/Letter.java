import java.util.ArrayList;

public class Letter {
	
	char character;
	ArrayList<String> wordsUsed;
	
	public Letter(char character, ArrayList<String> wordsUsed) {
		this.character = character;
		this.wordsUsed = wordsUsed;
	}
	
	
	public addWord(String word) {
		this.wordsUsed.add(word);
	}
	
	public removeWord(String word) {
		
	}
}
