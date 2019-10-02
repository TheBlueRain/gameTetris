package gameTetris;

/**
 * @author TheBlueRain
 *
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class gameTetris extends JFrame{

	final String TITLE_OF_PROGRAM = "Tetris";
	final static int BLOCK_SIZE = 25;
	final static int ARC_RADIUS = 6;
	final static int FIELD_WIDTH = 10;
	final static int FIELD_HEIGHT = 18;
	final int START_LOCATION = 100;
	final int FIELD_DX = 7;
	final int FIELD_DY = 26;
	final static int LEFT = 37;
	final int UP = 38;
	final static int RIGHT = 39;
	final int DOWN = 40;
	final int SHOW_DELAY = 350;
	final int[] SCORES = {100, 300, 700, 1500};
	
	int gameScore = 0;
	static int[][] mine = new int[FIELD_HEIGHT+1][FIELD_WIDTH];
	JFrame frame;
	Canvas canvasPanel = new Canvas();
	static Figure figure;
	static boolean gameOver = false;
		
	public static void main(String[] args) {
		new gameTetris().go();
	}

	void go() {
		figure = new Figure();
		frame = new JFrame(TITLE_OF_PROGRAM);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(FIELD_WIDTH * BLOCK_SIZE + 2*FIELD_DX, FIELD_HEIGHT * BLOCK_SIZE + 2*FIELD_DY-FIELD_DY/2);
		frame.setLocation(START_LOCATION, START_LOCATION);
		frame.setResizable(false);
		
		canvasPanel.setBackground(Color.black);
		
		frame.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (!gameOver) {
					if (e.getKeyCode() == DOWN) figure.drop();
					if (e.getKeyCode() == UP) figure.rotate();
					if (e.getKeyCode() == LEFT || e.getKeyCode() == RIGHT) figure.move(e.getKeyCode());
				}
				canvasPanel.repaint();
			}
		});
		frame.getContentPane().add(BorderLayout.CENTER, canvasPanel);		
		frame.setVisible(true);
		
		Arrays.fill(mine[FIELD_HEIGHT], 1);
		
		while (!gameOver) {
			try {
				Thread.sleep(SHOW_DELAY);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			canvasPanel.repaint();
			if (figure.isTouchGround()) {
				figure.leaveOnTheGround();
				checkFilling();
				figure = new Figure();
				gameOver = figure.isCrossGround();
			}
			else {
				figure.stepDown();
			}
		}
	}
	
	void checkFilling () {
		int row = FIELD_HEIGHT -1;
		int countFillRow = 0;
		while (row > 0) {
			int filled = 1;
			for (int col = 0; col < FIELD_WIDTH; col++)
				filled *= Integer.signum(mine[row][col]);
			if (filled > 0) {
				countFillRow++;
				for (int i = row; i > 0; i--)
					System.arraycopy(mine[i-1], 0, mine[i], 0, FIELD_WIDTH);
			}
			else {
				row--;
			}
		}
		if (countFillRow > 0) {
			gameScore += SCORES[countFillRow - 1];
			frame.setTitle(TITLE_OF_PROGRAM + " : "+gameScore);
		}
	}
	
}
