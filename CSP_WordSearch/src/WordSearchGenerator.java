import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class WordSearchGenerator {

	public static final int MINWORDLENGTH = 4;
	public static final int MAXWORDLENGTH = 10;
	
	int gridSize;
	int numWords;
	
	GridState gridState;
	Dictionary dict;
	
	ArrayList<String> wordList;


	public WordSearchGenerator(int gridSize, int numWords) {
		this.gridSize = gridSize;
		this.numWords = numWords;
		this.dict = new Dictionary("word_list.txt");
		this.gridState = generateInitialState();
		this.wordList = this.gridState.getWordsLeft();
	}
	
	
	public boolean solve() {
		this.gridState = this.gridState.findSolution();
		if (this.gridState == null) return false;
		
		return true;
	}
	
	private GridState generateInitialState() {
		ArrayList<String> words = dict.getRandomWords(this.numWords, MINWORDLENGTH, MAXWORDLENGTH);
		
		heuristicSort(words);
		
		char[][] grid = new char[this.gridSize][this.gridSize];
		for (int y = 0; y < this.gridSize; y++) {
			for (int x = 0; x < this.gridSize; x++) {
				grid[y][x] = this.dict.getRandomLetter();
			}
		}
		int[][] used = new int[this.gridSize][this.gridSize];
		GridState initialState = new GridState(grid, used, words);
		return initialState;
	}
	
	private void heuristicSort(ArrayList<String> words) {
		Comparator c = new Comparator<String>() {
			public int compare(String s1, String s2) {
				return Integer.compare(s1.length(), s2.length());
			}
		};
		
		Collections.sort(words, c);
		Collections.reverse(words);
	}
	
	public void saveGrid(String fileName) {
		File file = new File(fileName);
		BufferedWriter bw = null;
		
		try {
			bw = new BufferedWriter(new FileWriter(file));
			for (int y = 0; y < this.gridSize; y++) {
				String line = "";
				for (int x = 0; x < this.gridSize; x++) {
					line +=  this.gridState.getGrid()[y][x] + "  ";
				}
				bw.write(line);
				bw.newLine();
			}
			
			bw.newLine();
			bw.write("Word List:\n");
			for (String word : this.wordList) {
				bw.write(word);
				bw.newLine();
			}
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}
	
	
	public static void main(String[] agrs) {
		WordSearchGenerator g = new WordSearchGenerator(20, 50);
		boolean solutionFound = g.solve();
		if (solutionFound) {
			System.out.println("Found Solution!");
			g.saveGrid("wordsearch.txt");
		} else {
			System.out.println("No Solution Found");
		}
	}
}
