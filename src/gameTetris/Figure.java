package gameTetris;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

public class Figure {

        private ArrayList<Block> figure = new ArrayList<Block>();
        private int[][] shape = new int[4][4];
        private int type, size, color;
        private int x = 3, y = 0; // starting left up corner
    	Random random = new Random();
    	final int[][][] SHAPES = {
    			{{0,0,0,0}, {1,1,1,1}, {0,0,0,0}, {0,0,0,0}, {4, 0x00f0f0}}, // I	
    			{{0,0,0,0}, {0,1,1,0}, {0,1,1,0}, {0,0,0,0}, {4, 0x00f0f0}}, // O
    			{{1,0,0,0}, {1,1,1,0}, {0,0,0,0}, {0,0,0,0}, {3, 0xf0a000}}, // J
    			{{0,0,1,0}, {1,1,1,0}, {0,0,0,0}, {0,0,0,0}, {3, 0xf0a000}}, // L
    			{{0,1,1,0}, {1,1,0,0}, {0,0,0,0}, {0,0,0,0}, {3, 0x00f000}}, // S
    			{{1,1,1,0}, {0,1,0,0}, {0,0,0,0}, {0,0,0,0}, {3, 0xa000f0}}, // T
    			{{1,1,0,0}, {0,1,1,0}, {0,0,0,0}, {0,0,0,0}, {3, 0xf00000}}, // Z
    	};

        public Figure() {
            type = random.nextInt(SHAPES.length);
            size = SHAPES[type][4][0];
            color = SHAPES[type][4][1];
            if (size == 4) y = -1;
            for (int i = 0; i < size; i++)
                System.arraycopy(SHAPES[type][i], 0, shape[i], 0, SHAPES[type][i].length);
            createFromShape();
        }
		
		void createFromShape() {
			for (int x = 0; x < size; x++)
				for (int y = 0; y < size; y++)
					if (shape[y][x] == 1) 
						figure.add(new Block(x + this.x, y + this.y));
		}
		void drop() {
			while (!isTouchGround()) stepDown();		
		}
		
		boolean isTouchWall(int direction) {
			for (Block block : figure) {
				if (direction == gameTetris.LEFT && (block.getX() == 0 || gameTetris.mine[block.getY()][block.getX() - 1] > 0))
					return true;
				if (direction == gameTetris.RIGHT && (block.getX() == gameTetris.FIELD_WIDTH-1 || gameTetris.mine[block.getY()][block.getX() + 1] > 0))
					return true;
			}
			return false;
		}

		void move(int direction) {
			if (!isTouchWall(direction)) {
				int dx = direction - 38;
				for (Block block : figure) block.setX(block.getX() + dx);
				x += dx;
			}
		}
		
		void rotate() {
			for (int i = 0; i < size/2; i++)
				for (int j = i; j < size-1-i; j++) {
					int tmp = shape[size-1-j][i];
					shape[size-1-j][i] = shape[size-1-i][size-1-j];
					shape[size-1-i][size-1-j] = shape[j][size-1-i];
					shape[j][size-1-i] = shape[i][j];
					shape[i][j] = tmp;
				}
			if (!isWrongPosition()) {
				figure.clear();
				createFromShape();
			}
		}
		
		private boolean isWrongPosition() {
			for (int x = 0; x < size; x++)
				for (int y = 0; y < size; y++)
					if (shape[y][x] == 1) {
						if (y + this.y < 0)
							return true;
						if (x + this.x < 0 || x + this.x > gameTetris.FIELD_WIDTH - 1)
							return true;
						if (gameTetris.mine[y + this.y][x + this.x] > 0)
							return true;
					}
			return false;
		}

		boolean isTouchGround() {
			for (Block block: figure) 
				if (gameTetris.mine[block.getY()+1][block.getX()] > 0) 
					return true;
			return false;
		}
		
		void leaveOnTheGround() {
			for (Block block: figure) gameTetris.mine[block.getY()][block.getX()] = color;
		}
		
		boolean isCrossGround() {
			for (Block block: figure) 
				if (gameTetris.mine[block.getY()][block.getX()] > 0) 
					return true;
			return false;

		}
		
		void stepDown() {
			for (Block block: figure) block.setY(block.getY() + 1);
			y++;
		}
		
		void paint(Graphics g) {
			for (Block block: figure) block.paint(g, color);
		}

}
