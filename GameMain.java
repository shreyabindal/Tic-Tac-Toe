package tic_tac_toe;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class GameMain extends JPanel {
	public static final int ROWS = 3;
	public static final int COLS = 3;
	public static final String TITLE = "Tic Tac Toe";

	public static final int CELL_SIZE = 100;
	public static final int CANVAS_WIDTH = CELL_SIZE * COLS;
	public static final int CANVAS_HEIGHT = CELL_SIZE * ROWS;
	public static final int GRID_WIDTH = 8;
	public static final int GRID_WIDTH_HALF = GRID_WIDTH / 2;

	public static final int CELL_PADDING = CELL_SIZE / 6;
	public static final int SYMBOL_SIZE = CELL_SIZE - CELL_PADDING * 2;
	public static final int SYMBOL_STROKE_WIDTH = 8;

	private Board board;
	private GameState currentState;
	private Seed currentPlayer;
	private JLabel statusBar;

	public GameMain() {

		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int mouseX = e.getX();
				int mouseY = e.getY();

				int colSelected = mouseX / CELL_SIZE;
				int rowSelected = mouseY / CELL_SIZE;

				if (currentState == GameState.PLAYING) {
					if (rowSelected >= 0 && rowSelected < ROWS && colSelected >= 0 & colSelected < COLS
							&& board.cells[rowSelected][colSelected].content == Seed.EMPTY) {
						board.cells[rowSelected][colSelected].content = currentPlayer;
						updateGame(currentPlayer, rowSelected, colSelected);
						currentPlayer = (currentPlayer == Seed.CROSS) ? Seed.NOUGHT : Seed.CROSS;
						if (currentState == GameState.PLAYING) {
							AIPlayerMinimax aiPlayer = new AIPlayerMinimax(board);
							aiPlayer.setSeed(Seed.NOUGHT);
							int[] result = aiPlayer.move();
							board.cells[result[0]][result[1]].content = currentPlayer;
							updateGame(currentPlayer, result[0], result[1]);
							currentPlayer = (currentPlayer == Seed.CROSS) ? Seed.NOUGHT : Seed.CROSS;
						}
					}
				} else {
					initGame();
				}
				repaint();
			}
		});

		this.statusBar = new JLabel("           ");
		this.statusBar.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
		this.statusBar.setBorder(BorderFactory.createEmptyBorder(2, 5, 4, 5));
		this.statusBar.setOpaque(true);
		this.statusBar.setBackground(Color.LIGHT_GRAY);

		this.setLayout(new BorderLayout());
		this.add(statusBar, BorderLayout.PAGE_END);
		this.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT + 30));

		board = new Board();
		initGame();
	}

	public void initGame() {
		for (int row = 0; row < ROWS; row++) {
			for (int col = 0; col < COLS; col++) {
				board.cells[row][col].content = Seed.EMPTY;
			}
		}
		this.currentState = GameState.PLAYING;
		this.currentPlayer = Seed.CROSS;
	}

	public void updateGame(Seed seed, int row, int col) {
		if (this.board.hasWon(seed, row, col)) {
			this.currentState = (this.currentPlayer == Seed.CROSS) ? GameState.CROSS_WON : GameState.NOUGHT_WON;
		} else if (this.board.isDraw()) {
			this.currentState = GameState.DRAW;
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		this.setBackground(Color.WHITE);
		board.paint(g);
		if (currentState == GameState.PLAYING) {
			this.statusBar.setForeground(Color.BLACK);
			if (this.currentPlayer == Seed.CROSS) {
				this.statusBar.setText("X's turn");
			} else {
				this.statusBar.setText("O's turn");
			}
		} else if (this.currentState == GameState.CROSS_WON) {
			this.statusBar.setForeground(Color.RED);
			this.statusBar.setText("X Won! Click to play again");
		} else if (this.currentState == GameState.NOUGHT_WON) {
			this.statusBar.setForeground(Color.RED);
			this.statusBar.setText("O Won! Click to play again");
		} else if (this.currentState == GameState.DRAW) {
			this.statusBar.setForeground(Color.RED);
			this.statusBar.setText("It's a Draw! Click to play again");
		}

	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				JFrame frame = new JFrame(TITLE);
				frame.setContentPane(new GameMain());
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.pack();
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
			}
		});
	}
}
