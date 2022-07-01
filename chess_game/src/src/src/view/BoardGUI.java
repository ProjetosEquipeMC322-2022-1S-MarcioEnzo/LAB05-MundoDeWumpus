package view;

import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import control.ChessMatch;
import exceptions.GameException;
import model.Position;
import model.chess_pieces.ChessColor;
import model.chess_pieces.ChessPiece;
import online.IJogo;

public class BoardGUI extends JFrame implements IBoardGameGUI {

	private static final long serialVersionUID = 4901681158820749119L;
	private Container panel;
	private ChessColor pov;
	private ChessMatch chessMatch;
	private static Color colorWhite = Color.WHITE, colorBlack = Color.BLACK, colorGreen = Color.GREEN;
	private JButton[][] squares;
	private boolean multiplayer;
	private ChessPiece selectedPiece;
	private IJogo jogo;

	public BoardGUI(ChessMatch chessMatch, ChessColor pov) {
		super("Chess game");
		panel = getContentPane();
		selectedPiece = null;
		this.chessMatch = chessMatch;
		this.pov = pov;
		multiplayer = false;
		squares = new JButton[8][8];
		panel.setLayout(new GridLayout(8, 8));
		ButtonHandler buttonHandler = new ButtonHandler();
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				squares[i][j] = new JButton();
				squares[i][j].setBackground(((i + j) % 2 == 0) ? colorWhite : colorBlack);
				squares[i][j].addActionListener(buttonHandler);
				panel.add(squares[i][j]);
				
			}
		}
	}

	public BoardGUI(ChessMatch chessMatch, ChessColor pov, boolean multiplayer) {
		super("Chess game");
		panel = getContentPane();
		selectedPiece = null;
		this.chessMatch = chessMatch;
		this.pov = pov;
		this.multiplayer = multiplayer;
		squares = new JButton[8][8];
		panel.setLayout(new GridLayout(8, 8));
		ButtonHandler buttonHandler = new ButtonHandler(); //classe interna que age como Observer
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				squares[i][j] = new JButton();
				squares[i][j].setBackground(((i + j) % 2 == 0) ? colorWhite : colorBlack);
				squares[i][j].addActionListener(buttonHandler);
				panel.add(squares[i][j]);
				
			}
		}
	}
	
	public void connect(IJogo jogo) {
		this.jogo = jogo;
	}

	public void setChessMatch(ChessMatch chessMatch) {
		this.chessMatch = chessMatch;
		fillBoard();
	}

	public void start() {
		setSize(520, 520);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
		fillBoard();
	}

	@Override
	public void fillBoard() {
		if (pov == ChessColor.WHITE) {
			for (int i = 0; i < 8; i++) {
				for (int j = 0; j < 8; j++) {
					if (chessMatch.getPieces()[i][j] != null)
						squares[i][j].setIcon(chessMatch.getPieces()[i][j].image());
					else
						squares[i][j].setIcon(null);
				}
			}
		} else {
			for (int i = 0; i < 8; i++)
				for (int j = 0; j < 8; j++)
					if (chessMatch.getPieces()[i][j] != null)
						squares[Math.abs(7 - i)][Math.abs(7 - j)].setIcon(chessMatch.getPieces()[i][j].image());
					else
						squares[Math.abs(7 - i)][Math.abs(7 - j)].setIcon(null);
		}
	}

	private class ButtonHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (multiplayer && pov != chessMatch.getCurrentPlayer())
				return;
			Position clickedPosition = getEventPosition(e);
			ChessPiece clickedPiece = chessMatch.getPieces()[clickedPosition.getRow()][clickedPosition.getColumn()];
			if (selectedPiece == null && clickedPiece != null && clickedPiece.getColor() == chessMatch.getCurrentPlayer()) {
				selectedPiece = clickedPiece;
				showPossibleMoves();
			} else if (selectedPiece != null && clickedPiece != null && clickedPiece.getColor() == chessMatch.getCurrentPlayer()) {
				flushPossibleMoves();
				selectedPiece = clickedPiece;
				showPossibleMoves();
			} else if (selectedPiece != null && selectedPiece.possibleMove(clickedPosition)) {
				flushPossibleMoves();
				try {
					chessMatch.peformChessMove(selectedPiece.getPosition(), clickedPosition);
					if (chessMatch.getPromoted() != null) {
						String[] possibilidades = { "Queen", "Rook", "Bishop", "Knight" };
						String o = (String) JOptionPane.showInputDialog(null, "Escolha sua promoção", "Peça promovida",
								JOptionPane.PLAIN_MESSAGE, null, possibilidades, "Queen");
						if (!o.equals("Knight"))
							chessMatch.replacePromotedPiece(o.substring(0, 1));
						else
							chessMatch.replacePromotedPiece("N");

					}
					fillBoard();
					selectedPiece = null;
					if (multiplayer)
						try {
							notifyGame();
						}
						catch (IOException exception) {
							exception.printStackTrace();
						}
				} catch (GameException exception) {
					JOptionPane.showMessageDialog(null, exception.getMessage(), "Chess Warning",
							JOptionPane.WARNING_MESSAGE);
				}
			}
		}

		private Position getEventPosition(ActionEvent e) {
			if (pov == ChessColor.WHITE) {
				for (int i = 0; i < 8; i++) {
					for (int j = 0; j < 8; j++) {
						if (squares[i][j] == e.getSource())
							return new Position(i, j);
					}
				}
			} else {
				for (int i = 0; i < 8; i++) {
					for (int j = 0; j < 8; j++) {
						if (squares[i][j] == e.getSource())
							return new Position(Math.abs(7 - i), Math.abs(7 - j));
					}
				}
			}
			return null;
		}
	}

	@Override
	public void showPossibleMoves() {
		if (pov == ChessColor.WHITE) {
			for (int i = 0; i < 8; i++)
				for (int j = 0; j < 8; j++)
					if (selectedPiece.possibleMove(i, j))
						squares[i][j].setBackground(colorGreen);
		} else {
			for (int i = 0; i < 8; i++)
				for (int j = 0; j < 8; j++)
					if (selectedPiece.possibleMove(i, j))
						squares[Math.abs(7 - i)][Math.abs(7 - j)].setBackground(colorGreen);
		}
	}

	@Override
	public void flushPossibleMoves() {
		if (pov == ChessColor.WHITE) {
			for (int i = 0; i < 8; i++)
				for (int j = 0; j < 8; j++)
					if (selectedPiece.possibleMove(i, j))
						squares[i][j].setBackground(((i + j) % 2 == 0) ? colorWhite : colorBlack);
		} else {
			for (int i = 0; i < 8; i++)
				for (int j = 0; j < 8; j++)
					if (selectedPiece.possibleMove(i, j))
						squares[Math.abs(7 - i)][Math.abs(7 - j)]
								.setBackground(((i + j) % 2 == 0) ? colorWhite : colorBlack);
		}

	}

	@Override
	public void notifyGame() throws IOException {
		jogo.enviarPartida(chessMatch);
	}

}
