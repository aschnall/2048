package game;

public class Board {
	
	Tile[][] squares;
	
	public Board() {
		this.squares = new Tile[4][4];
	}
	
	//checks the optimal row that a given tile should slide to when the up arrow is pressed
	//either occupies the highest empty space in the board or merges with a tile that has the same value
	public int optimalOpenRowUp(int col, int row, int value, boolean collided) {
		int optimalRowUp = row;
		for (int i = 3; i >= 0; i--) {
			if (this.squares[col][i] == null && i < row) {
				optimalRowUp = i;
			}
		}
		if (optimalRowUp > 0 && this.squares[col][optimalRowUp-1].getValue() == value && !collided) {
			optimalRowUp = optimalRowUp - 1;
		}
		
		return optimalRowUp;
	}
	
	//checks the optimal row that a given tile should slide to when the down arrow is pressed
	//either occupies the lowest empty space in the board or merges with a tile that has the same value
	public int optimalOpenRowDown(int col, int row, int value, boolean collided) {
		int optimalRowDown = row;
		for (int i = 0; i < 4; i++) {
			if (this.squares[col][i] == null && i > row) {
				optimalRowDown = i;
			}
		}
		if (optimalRowDown < 3 && this.squares[col][optimalRowDown+1].getValue() == value && !collided) {
			optimalRowDown = optimalRowDown + 1;
		}
		
		return optimalRowDown;
	}
	
	//checks the optimal column that a given tile should slide to when left arrow is pressed
	//either occupies the left most empty space in the board or merges with a tile that has the same value
	public int optimalOpenColLeft(int col, int row, int value, boolean collided) {
		int optimalColLeft = col;
		for (int i = 3; i >= 0; i--) {
			if (this.squares[i][row] == null && i < col) {
				optimalColLeft = i;
			}
		}
		if (optimalColLeft > 0 && this.squares[optimalColLeft-1][row].getValue() == value && !collided) {
			optimalColLeft = optimalColLeft - 1;
		}
		
		return optimalColLeft;
	}
	
	//checks the optimal column that a given tile should slide to when right arrow is pressed
	//either occupies the right most empty space in the board or merges with a tile that has the same value
	public int optimalOpenColRight(int col, int row, int value, boolean collided) {
		int optimalColRight = col;
		for (int i = 0; i < 4; i++) {
			if (this.squares[i][row] == null && i > col) {
				optimalColRight = i;
			}
		}
		if (optimalColRight < 3 && this.squares[optimalColRight+1][row].getValue() == value && !collided) {
			optimalColRight = optimalColRight + 1;
		}
		
		return optimalColRight;
	}

	//checks if the board is full of tiles
	public boolean isFull() {
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				if (this.squares[i][j] == null) {
					return false;
				}
			}
		}
		return true;
	}
	
	//checks if the game is over by using the optimal functions
	//if none of the tiles optimal row or cols change then the user loses
	public boolean isOver() {
		int count = 0;
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				int opColR = this.optimalOpenColRight(i, j, this.squares[i][j].getValue(), false);
				int opColL = this.optimalOpenColLeft(i, j, this.squares[i][j].getValue(), false);
				int opRowU = this.optimalOpenRowUp(i, j, this.squares[i][j].getValue(), false);
				int opRowD = this.optimalOpenRowDown(i, j, this.squares[i][j].getValue(), false);
				if (opColR == i && opColL == i && opRowU == j && opRowD == j) {
					count++;
				}
			}
		}
	
		if (count == 16) {
			return true;
		}
		return false;
	}
	
	//clears the board of all tiles
	public void clearBoard() {
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				this.squares[i][j] = null;
			}
		}
	}

}	
