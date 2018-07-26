package tic_tac_toe;

import java.awt.*;

public class Board {
	Cell[][] cells;
	int currentRow, currentCol;

	public Board() {
		cells = new Cell[GameMain.ROWS][GameMain.COLS];
		for (int row = 0; row < GameMain.ROWS; row++) {
			for (int col = 0; col < GameMain.COLS; col++) {
				cells[row][col] = new Cell(row, col);
			}
		}
	}

	public void init() {
		for (int row = 0; row < GameMain.ROWS; row++) {
			for (int col = 0; col < GameMain.COLS; col++) {
				cells[row][col].clear();
				;
			}
		}
	}

	public boolean isDraw() {
		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells.length; j++) {
				if (cells[i][j].content == Seed.EMPTY)
					return false;
			}
		}
		return true;
	}

	public boolean hasWon(Seed theSeed, int currentRow, int currentCol) {
		return ((cells[currentRow][0].content == theSeed && cells[currentRow][1].content == theSeed
				&& cells[currentRow][2].content == theSeed)
				|| (cells[0][currentCol].content == theSeed && cells[1][currentCol].content == theSeed
						&& cells[2][currentCol].content == theSeed)
				|| (currentRow == currentCol && cells[0][0].content == theSeed && cells[1][1].content == theSeed
						&& cells[2][2].content == theSeed)
				|| (currentRow + currentCol == 2 && cells[0][2].content == theSeed && cells[1][1].content == theSeed
						&& cells[2][0].content == theSeed));
	}

	public void paint(Graphics g) {
		g.setColor(Color.GRAY);
	      for (int row = 1; row < GameMain.ROWS; ++row) {
	         g.fillRoundRect(0, GameMain.CELL_SIZE * row - GameMain.GRID_WIDTH_HALF,
	               GameMain.CANVAS_WIDTH - 1, GameMain.GRID_WIDTH,
	               GameMain.GRID_WIDTH, GameMain.GRID_WIDTH);
	      }
	      for (int col = 1; col < GameMain.COLS; ++col) {
	         g.fillRoundRect(GameMain.CELL_SIZE * col - GameMain.GRID_WIDTH_HALF, 0,
	               GameMain.GRID_WIDTH, GameMain.CANVAS_HEIGHT - 1,
	               GameMain.GRID_WIDTH, GameMain.GRID_WIDTH);
	      }
	 
	      for (int row = 0; row < GameMain.ROWS; ++row) {
	         for (int col = 0; col < GameMain.COLS; ++col) {
	            cells[row][col].paint(g);  // ask the cell to paint itself
	         }
	      }
	}
}
