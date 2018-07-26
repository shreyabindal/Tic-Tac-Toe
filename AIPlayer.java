package tic_tac_toe;

public abstract class AIPlayer {
	protected int ROWS = GameMain.ROWS;
	protected int COLS = GameMain.COLS;

	protected Cell[][] cells;
	protected Seed mySeed;
	protected Seed oppSeed;

	public AIPlayer(Board board) {
		this.cells = board.cells;
	}

	public void setSeed(Seed seed) {
		this.mySeed = seed;
		this.oppSeed = (seed == Seed.CROSS) ? Seed.NOUGHT : Seed.CROSS;
	}

	abstract int[] move();
}
