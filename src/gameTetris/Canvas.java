package gameTetris;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class Canvas extends JPanel{
	
	final int[][] GAME_OVER_MSG = {
			{0,1,1,0,0,0,1,1,0,0,0,1,0,1,0,0,0,1,1,0},
	        {1,0,0,0,0,1,0,0,1,0,1,0,1,0,1,0,1,0,0,1},
	        {1,0,1,1,0,1,1,1,1,0,1,0,1,0,1,0,1,1,1,1},
	        {1,0,0,1,0,1,0,0,1,0,1,0,1,0,1,0,1,0,0,0},
	        {0,1,1,0,0,1,0,0,1,0,1,0,1,0,1,0,0,1,1,0},
	        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
	        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
	        {0,1,1,0,0,1,0,0,1,0,0,1,1,0,0,1,1,1,0,0},
	        {1,0,0,1,0,1,0,0,1,0,1,0,0,1,0,1,0,0,1,0},
	        {1,0,0,1,0,1,0,1,0,0,1,1,1,1,0,1,1,1,0,0},
	        {1,0,0,1,0,1,1,0,0,0,1,0,0,0,0,1,0,0,1,0},
	        {0,1,1,0,0,1,0,0,0,0,0,1,1,0,0,1,0,0,1,0}};
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		for (int x = 0; x < gameTetris.FIELD_WIDTH; x++)
			for (int y = 0; y < gameTetris.FIELD_HEIGHT; y++)
				if (gameTetris.mine[y][x] > 0) {
					g.setColor(new Color(gameTetris.mine[y][x]));
					g.fill3DRect(x*gameTetris.BLOCK_SIZE+1, y*gameTetris.BLOCK_SIZE+1, gameTetris.BLOCK_SIZE-1, gameTetris.BLOCK_SIZE-1, true);
				}
		if (gameTetris.gameOver) {
			g.setColor(Color.white);
			for (int y = 0; y < GAME_OVER_MSG.length; y++)
				for (int x = 0; x < GAME_OVER_MSG[y].length; x++)
					if (GAME_OVER_MSG[y][x] == 1)
						g.fill3DRect(x*11+18, y*11+160, 10, 10, true);
		}
		else
			gameTetris.figure.paint(g);
	}
}
