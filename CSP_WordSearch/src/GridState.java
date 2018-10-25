import java.util.ArrayList;
import java.util.Random;

public class GridState {
	
	char[][] grid;
	int[][] used;
	ArrayList<String> wordsLeft;
	Random rand;
	
	
	public GridState(GridState state) {
		this.grid = copyArray(state.getGrid());
		this.used = copyArray(state.getUsed());
		this.wordsLeft = copyArray(state.getWordsLeft());
		this.rand = new Random();
	}

	public GridState(char[][] initialGrid, int[][] used, ArrayList<String> wordsLeft) {
		this.grid = initialGrid;
		this.used = used;
		this.wordsLeft = wordsLeft;
		this.rand = new Random();
	}
	
	
	public GridState findSolution() {
		
		//if (this.numWordsLeft() == 0) return this;
		
		ArrayList<GridState> nextStates = getNextStates();
		
		if (nextStates.size() == 0) return null;
		
		for (GridState state : nextStates) {
			
			if (state.numWordsLeft() == 0) {
				return state;
			}
			
			GridState solution = state.findSolution();
			
			if (solution != null) return solution;
		}
		
		return null;
	}
	
	public ArrayList<GridState> getNextStates() {
		ArrayList<GridState> states = new ArrayList<GridState>();
		int gridSize = this.grid.length;
		
		Point[] pointList = new Point[gridSize*gridSize];	
		int i = 0;
		for (int y = 0; y < gridSize; y++) {
			for (int x = 0; x < gridSize; x++) {
				pointList[i++] = new Point(x, y);
			}
		}
		
		Point[] directions = {new Point(-1, -1), new Point(0, -1), new Point(0, 1), new Point(1, 0), new Point(-1, 0), new Point(-1, 1), new Point(1, -1), new Point(1, 1)};
		
		for (String word : this.wordsLeft) {
			Point[] pointsShuffled = shufflePointArray(pointList);
			for (Point start : pointsShuffled) {
				Point[] directionsShuffled = shufflePointArray(directions);
				for (Point dir : directionsShuffled) {
					GridState state = placeWord(word, start, dir);
					if (state != null) {
						states.add(state);
					}
				}
			}
		}
		
		return states;
	}
	
	
	private GridState placeWord(String word, Point start, Point dir) {
		
		GridState state = new GridState(this);

		Point location = start;
		
		for (char c : word.toCharArray()) {
			int x = location.getX();
			int y = location.getY();
			if (x < 0 || x >= this.grid.length || y < 0 || y >= this.grid.length) return null;
			
			if (state.getGrid()[y][x] == c) {
				state.getUsed()[y][x] += 1;
			}
			else {
				if (state.getUsed()[y][x] == 0) {
					state.getGrid()[y][x] = c;
					state.getUsed()[y][x] += 1;
				} else {
					return null;
				}
			}
			location.setX(location.getX() + dir.getX());
			location.setY(location.getY() + dir.getY());
		}
		state.getWordsLeft().remove(word);
		return state;
	}
	
	private char[][] copyArray(char[][] arr) {
		char[][] newGrid = new char[arr.length][arr.length];
		
		for (int y = 0; y < arr.length; y++) {
			for (int x = 0; x < arr.length; x++) {
				newGrid[y][x] = arr[y][x];
			}
		}
		
		return newGrid;
	}
	
	private int[][] copyArray(int[][] arr) {
		int[][] newGrid = new int[arr.length][arr.length];
		
		for (int y = 0; y < arr.length; y++) {
			for (int x = 0; x < arr.length; x++) {
				newGrid[y][x] = arr[y][x];
			}
		}
		
		return newGrid;
	}
	
	private ArrayList<String> copyArray(ArrayList<String> array) {
		ArrayList<String> newArray = new ArrayList<String>();//(array);
		
		for (String s : array) {
			newArray.add(s);
		}
		return newArray;
	}
	
	private Point[] shufflePointArray(Point[] array){
 
		for (int i=0; i<array.length; i++) {
		    int randomPosition = this.rand.nextInt(array.length);
		    Point temp = array[i];
		    array[i] = array[randomPosition];
		    array[randomPosition] = temp;
		}
 
		return array;
	}
	
	public char[][] getGrid() {
		return this.grid;
	}
	
	public int numWordsLeft() {
		return wordsLeft.size();
	}
	
	public int[][] getUsed() {
		return this.used;
	}
	
	public ArrayList<String> getWordsLeft() {
		return this.wordsLeft;
	}
}
