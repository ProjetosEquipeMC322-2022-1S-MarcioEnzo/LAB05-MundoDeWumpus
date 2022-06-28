package model.chess_pieces;

import javax.swing.ImageIcon;

import model.Board;
import model.Position;

public class Knight extends ChessPiece {

	private static final long serialVersionUID = -7074487160048554478L;

	public Knight(Position pos, Board board) {
		super(pos, board);
	}

	public Knight(Position pos, Board board, ChessColor color) {
		super(pos, board, color);
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean[8][8];
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				mat[i][j] = ((Math.abs(i - getRow()) == 1 && Math.abs(j - getColumn()) == 2)
						|| (Math.abs(i - getRow()) == 2 && Math.abs(j - getColumn()) == 1))
						&& (!board.thereIsAPiece(i, j) || isThereOpponentPiece(i, j));
			}
		}
		return mat;
	}
	
	@Override
	public ImageIcon image() {
		String img = DIRETORIO + colorString() + "Knight.png";
		return new ImageIcon(img);
	}

}
