package game;

import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;

import javax.swing.JPanel;
import javax.swing.Timer;


public class MyPanel extends JPanel implements KeyListener {

	private final int PANEL_WIDTH = 500;
	private final int PANEL_HEIGHT = 500;
	private boolean gameOverLoss = false;
	private boolean gameOverWin = false;
	private boolean canMove = true;
	private boolean collidedUp = false;
	private boolean collidedDown = false;
	private boolean collidedLeft = false;
	private boolean collidedRight = false;
	private Board board;
	private int score = 0;
	
	
	public MyPanel() {
		this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
		this.setBackground(Color.white);
		this.board = new Board();
		addKeyListener(this);
		setFocusable(true);
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.BLACK);
		//draw the board
		g2d.drawRect(50, 50, 400, 400);
		for (int i = 1; i < 4; i++) {
			g2d.drawLine(i*100+50, 50, i*100+50, PANEL_HEIGHT-50);
			g2d.drawLine(PANEL_WIDTH-50, i*100+50, 50, i*100+50);
		}
		
		//draw the score
		g2d.drawString("Score: " + score, 375, 25);
		
		//draw the tiles in play - each a different color based on the value
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				if (board.squares[i][j] != null) {
					switch (board.squares[i][j].getValue()) {
						case 2: 
							g2d.setColor(new Color(238,228,218));
							break;
						case 4: 
							g2d.setColor(new Color(238,225,201));
							break;
						case 8: 
							g2d.setColor(new Color(243,178,122));
							break;
						case 16: 
							g2d.setColor(new Color(246,150,100));
							break;
						case 32: 
							g2d.setColor(new Color(255,124,95));
							break;
						case 64: 
							g2d.setColor(new Color(247,95,59));
							break;
						case 128: 
							g2d.setColor(new Color(237,208,115));
							break;
						case 256: 
							g2d.setColor(new Color(237,204,98));
							break;
						case 512: 
							g2d.setColor(new Color(237,201,109));
							break;
						case 1024: 
							g2d.setColor(new Color(237,197,63));
							break;
						case 2048: 
							g2d.setColor(new Color(237,197,63));
							break;
					}
					g2d.fillRect((i*100)+50, (j*100)+50, 100, 100);
					if (board.squares[i][j].getValue() == 2 || board.squares[i][j].getValue() == 4) {
						g2d.setColor(new Color(119,110,101));
					} else {
						g2d.setColor(new Color(249,246,242));
					}
					g2d.setFont(new Font("Arial", Font.BOLD, 20));
					g2d.drawString("" + board.squares[i][j].getValue(), (i*100)+40 + ((((i*100)+150) - ((i*100)+50))/2), (j*100)+55 + ((((j*100)+150) - ((j*100)+50))/2));
				}
			}
		}
		
		//display messages after user either wins or loses
		if (gameOverLoss) {
			g2d.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
			g2d.drawString("You Lose", 225, 20);
			g2d.drawString("Press space to play again.", 175, 40);
		}
		if (gameOverWin) {
			g2d.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
			g2d.drawString("You Win!", 225, 20);
			g2d.drawString("Press space to play again.", 175, 40);
		}
	}	

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if (!gameOverLoss && !gameOverWin) {
			if (key == KeyEvent.VK_UP) {
				for (int i = 0; i < 4; i++) {
					collidedUp = false;
					for (int j = 0; j < 4; j++) {
						if (board.squares[i][j] != null) {
							int newRow = board.optimalOpenRowUp(i, j, board.squares[i][j].getValue(), collidedUp);
							if (newRow < j && board.squares[i][newRow] == null) {
								board.squares[i][newRow] = board.squares[i][j];
								board.squares[i][j] = null;
								canMove = true;
								collidedUp = false;
							} else if (newRow < j && board.squares[i][newRow] != null) {
								board.squares[i][newRow].setValue(2*board.squares[i][j].getValue());
								board.squares[i][j] = null;
								canMove = true;
								score += board.squares[i][newRow].getValue();
								collidedUp = true;
							}
						}
					}
				}
			}
			if (key == KeyEvent.VK_DOWN) {
				for (int i = 0; i < 4; i++) {
					collidedDown = false;
					for (int j = 3; j >= 0; j--) {
						if (board.squares[i][j] != null) {
							int newRow = board.optimalOpenRowDown(i, j, board.squares[i][j].getValue(), collidedDown);
							if (newRow > j && board.squares[i][newRow] == null) {
								board.squares[i][newRow] = board.squares[i][j];
								board.squares[i][j] = null;
								canMove = true;
								collidedDown = false;
							} else if (newRow > j && board.squares[i][newRow] != null) {
								board.squares[i][newRow].setValue(2*board.squares[i][j].getValue());
								board.squares[i][j] = null;
								canMove = true;
								score += board.squares[i][newRow].getValue();
								collidedDown = true;
							}
						}
					}
				}
			}
			if (key == KeyEvent.VK_LEFT) {
				for (int i = 0; i < 4; i++) {
					collidedLeft = false;
					for (int j = 0; j < 4; j++) {
						if (board.squares[j][i] != null) {
							int newCol = board.optimalOpenColLeft(j, i, board.squares[j][i].getValue(), collidedLeft);
							if (newCol < j && board.squares[newCol][i] == null) {
								board.squares[newCol][i] = board.squares[j][i];
								board.squares[j][i] = null;
								canMove = true;
								collidedLeft = false;
							} else if (newCol < j && board.squares[newCol][i] != null) {
								board.squares[newCol][i].setValue(2*board.squares[j][i].getValue());
								board.squares[j][i] = null;
								canMove = true;
								score += board.squares[newCol][i].getValue();
								collidedLeft = true;
							}
						}
					}
				}
			}
			if (key == KeyEvent.VK_RIGHT) {
				for (int i = 0; i < 4; i++) {
					collidedRight = false;
					for (int j = 3; j >= 0; j--) {
						if (board.squares[j][i] != null) {
							int newCol = board.optimalOpenColRight(j, i, board.squares[j][i].getValue(), collidedRight);
							if (newCol > j && board.squares[newCol][i] == null) {
								board.squares[newCol][i] = board.squares[j][i];
								board.squares[j][i] = null;
								canMove = true;
								collidedLeft = false;
							} else if (newCol > j && board.squares[newCol][i] != null) {
								board.squares[newCol][i].setValue(2*board.squares[j][i].getValue());
								board.squares[j][i] = null;
								canMove = true;
								score += board.squares[newCol][i].getValue();
								collidedRight = true;
							}
						}
					}
				}
			}
		}
		
		//restart game
		if (key == KeyEvent.VK_SPACE && gameOverWin || key == KeyEvent.VK_SPACE && gameOverLoss) {
			board.clearBoard();
			gameOverWin = false;
			gameOverLoss = false;
			canMove = true;
			score = 0;
		}
		
		//generating random tile to add into board after each arrow press
		int randomRow = (int) (Math.random() * 4);
		int randomCol = (int) (Math.random() * 4);
		if (canMove) {
			while (board.squares[randomCol][randomRow] != null) {
				randomRow = (int) (Math.random() * 4);
				randomCol = (int) (Math.random() * 4);
			}
			if (Math.random() <= 0.8) {
				Tile tile = new Tile(2);
				board.squares[randomCol][randomRow] = tile;
			} else {
				Tile tile = new Tile(4);
				board.squares[randomCol][randomRow] = tile;
			}
		}

		//checking if a user has won or lost
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				if (board.squares[i][j] != null) {
					if (board.squares[i][j].getValue() == 2048) {
						gameOverWin = true;
					}	
				}
				if (board.isFull() && board.isOver() ) {
					gameOverLoss = true;
				}
			}
		}
		canMove = false;
		repaint();
		
	}
	
	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyReleased(KeyEvent e) {}

}