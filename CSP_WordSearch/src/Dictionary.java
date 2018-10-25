import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Dictionary {
	
	String word_list;
	Random rand;
	
	public Dictionary(String word_list) {
		this.word_list = "word_list.txt";
		rand = new Random();
	}
	
	public ArrayList<String> getRandomWords(int numWords, int minLength, int maxLength) {
		File file = new File(this.word_list);
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		ArrayList<String> dict = new ArrayList<String>();
		ArrayList<String> words = new ArrayList<String>();
		
		String st;
		try {
			while ((st = br.readLine()) != null) {
				if (st.length() <= maxLength && st.length() >= minLength) {
					dict.add(st.toUpperCase());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for (int i = 0; i < numWords; i++) {
			int choice = rand.nextInt(dict.size());
			words.add(dict.get(choice));
		}
		
		return words;
	}
	
	public char getRandomLetter() {
		char randomCharacter = (char)('A' + rand.nextInt(26));
		return randomCharacter;
	}
}
