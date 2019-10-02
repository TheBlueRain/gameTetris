package gameTetris;

import java.awt.Color;
import java.awt.Graphics;

public class Block {
	
		private int x, y;
		
		public Block(int x, int y) {
			setX(x);
			setY(y);
		}
		
		void setX(int x) {
			this.x = x;
		}
		
		void setY(int y) {
			this.y = y;
		}
		
		int getX() {
			return x;
		}
		
		int getY() {
			return y;
		}
		
		void paint(Graphics g, int color) {
			g.setColor(new Color(color));
			g.drawRoundRect(x*gameTetris.BLOCK_SIZE+1, y*gameTetris.BLOCK_SIZE+1, gameTetris.BLOCK_SIZE-2, gameTetris.BLOCK_SIZE-2, gameTetris.ARC_RADIUS, gameTetris.ARC_RADIUS);
		}

}
