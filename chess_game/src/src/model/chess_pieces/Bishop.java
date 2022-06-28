package model.chess_pieces;

import javax.swing.ImageIcon;

import model.Board;
import model.Position;

public class Bishop extends ChessPiece {
	private static final long serialVersionUID = 7832901711183412392L;

	public Bishop(Position pos, Board board) {
		super(pos, board);
	}

	public Bishop(Position pos, Board board, ChessColor color) {
		super(pos, board, color);
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean[8][8];
		Position pos = new Position(getRow(), getColumn());
		// nw
		pos.setValues(getRow() - 1, getColumn() - 1);
		while (pos.getRow() >= 0 && pos.getColumn() >= 0 && !board.thereIsAPiece(pos)) {
			mat[pos.getRow()][pos.getColumn()] = true;
			pos.setValues(pos.getRow() - 1, pos.getColumn() - 1);
		}
		if (pos.getRow() >= 0 && pos.getColumn() >= 0 && isThereOpponentPiece(pos))
			mat[pos.getRow()][pos.getColumn()] = true;
		// ne
		pos.setValues(getRow() - 1, getColumn() + 1);
		while (pos.getRow() >= 0 && pos.getColumn() < 8 && !board.thereIsAPiece(pos)) {
			mat[pos.getRow()][pos.getColumn()] = true;
			pos.setValues(pos.getRow() - 1, pos.getColumn() + 1);
		}
		if (pos.getRow() >= 0 && pos.getColumn() < 8 && isThereOpponentPiece(pos))
			mat[pos.getRow()][pos.getColumn()] = true;
		// sw
		pos.setValues(getRow() + 1, getColumn() - 1);
		while (pos.getRow() < 8 && pos.getColumn() >= 0 && !board.thereIsAPiece(pos)) {
			mat[pos.getRow()][pos.getColumn()] = true;
			pos.setValues(pos.getRow() + 1, pos.getColumn() - 1);
		}
		if (pos.getRow() < 8 && pos.getColumn() >= 0 && isThereOpponentPiece(pos))
			mat[pos.getRow()][pos.getColumn()] = true;
		// se
		pos.setValues(getRow() + 1, getColumn() + 1);
		while (pos.getRow() < 8 && pos.getColumn() < 8 && !board.thereIsAPiece(pos)) {
			mat[pos.getRow()][pos.getColumn()] = true;
			pos.setValues(pos.getRow() + 1, pos.getColumn() + 1);
		}
		if (pos.getRow() < 8 && pos.getColumn() < 8 && isThereOpponentPiece(pos))
			mat[pos.getRow()][pos.getColumn()] = true;
		return mat;
	}
	
	@Override
	public ImageIcon image() {
		String img = DIRETORIO + colorString() + "Bishop.png";
		return new ImageIcon(img);
	}

}
