package tic_tac_toe;

import java.util.ArrayList;

public class AIPlayerMinimax extends AIPlayer {

	public AIPlayerMinimax(Board board) {
		super(board);
	}

	@Override
	int[] move() {
		int[] result = this.minimax(6, mySeed);
		return new int[] { result[1], result[2] };
	}

	private int[] minimax(int depth, Seed myPlayer) {
		ArrayList<int[]> nextMoves = generateMoves();

		int bestScore = (myPlayer == mySeed) ? Integer.MIN_VALUE : Integer.MAX_VALUE;
		int bestRow = -1;
		int bestCol = -1;

		if (nextMoves.isEmpty() || depth == 0) {
			bestScore = evaluate();
		} else {
			for (int[] move : nextMoves) {
				cells[move[0]][move[1]].content = myPlayer;
				if (myPlayer == mySeed) { // maximising Player
					int currentScore = this.minimax(depth - 1, this.oppSeed)[0];
					if (currentScore > bestScore) {
						bestScore = currentScore;
						bestRow = move[0];
						bestCol = move[1];
					}
				} else {
					int currentScore = this.minimax(depth - 1, this.mySeed)[0];
					if (currentScore < bestScore) {
						bestScore = currentScore;
						bestRow = move[0];
						bestCol = move[1];
					}
				}
				cells[move[0]][move[1]].content = Seed.EMPTY;
			}
		}
//		if (myPlayer == mySeed) {
//			bestScore -= depth;
//		} else {
//			bestScore += depth;
//		}
		return new int[] { bestScore, bestRow, bestCol };
	}

	public ArrayList<int[]> generateMoves() {
		ArrayList<int[]> nextMoves = new ArrayList<>();

		if (hasWon(this.mySeed) || hasWon(this.oppSeed)) {
			return nextMoves;
		}

		for (int row = 0; row < ROWS; row++) {
			for (int col = 0; col < COLS; col++) {
				if (cells[row][col].content == Seed.EMPTY) {
					nextMoves.add(new int[] { row, col });
				}
			}
		}

		return nextMoves;
	}

	public int evaluate() {
		int score = 0;
		score += evaluateLine(0, 0, 0, 1, 0, 2);
		score += evaluateLine(1, 0, 1, 1, 1, 2);
		score += evaluateLine(2, 0, 2, 1, 2, 2);
		score += evaluateLine(0, 0, 1, 0, 2, 0);
		score += evaluateLine(0, 1, 1, 1, 2, 1);
		score += evaluateLine(0, 2, 1, 2, 2, 2);
		score += evaluateLine(0, 0, 1, 1, 2, 2);
		score += evaluateLine(0, 2, 1, 1, 2, 0);
		return score;
	}

	private int evaluateLine(int row1, int col1, int row2, int col2, int row3, int col3) {
		int score = 0;

		if (this.cells[row1][col1].content == this.mySeed) {
			score = 1;
		} else {
			score = -1;
		}

		if (this.cells[row2][col2].content == this.mySeed) {
			if (score == 1) {
				score = 10;
			} else if (score == -1) {
				score = 0;
			} else {
				score = 1;
			}
		} else {
			if (score == 1) {
				score = 0;
			} else if (score == -1) {
				score = -10;
			} else {
				score = -1;
			}
		}

		if (this.cells[row3][col3].content == this.mySeed) {
			if (score > 0) {
				score *= 10;
			} else if (score < 0) {
				score = 0;
			} else {
				score = 1;
			}
		} else {
			if (score < 0) {
				score *= 10;
			} else if (score > 0) {
				score = 0;
			} else {
				score = 1;
			}
		}
		return score;
	}

	private int[] winningPatterns = { 0b111000000, 0b000111000, 0b000000111, // rows
			0b100100100, 0b010010010, 0b001001001, // columns
			0b100010001, 0b001010100 }; // diagonals

	private boolean hasWon(Seed thePlayer) {
		int pattern = 0b000000000;
		for (int row = 0; row < ROWS; row++) {
			for (int col = 0; col < COLS; col++) {
				if (cells[row][col].content == thePlayer) {
					pattern = pattern | (1 << (row * ROWS + col));
				}
			}
		}

		for (int winningPattern : winningPatterns) {
			if ((pattern & winningPattern) == winningPattern)
				return true;
		}
		return false;
	}

}
