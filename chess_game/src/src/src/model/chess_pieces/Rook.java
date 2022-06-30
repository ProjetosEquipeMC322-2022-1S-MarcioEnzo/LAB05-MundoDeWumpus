package model.chess_pieces;

import javax.swing.ImageIcon;

import model.Board;
import model.Position;

public class Rook extends ChessPiece {
	private static final long serialVersionUID = -607608020568474993L;

	public Rook(Position position, Board board, ChessColor color) {
		super(position, board, color);
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean[8][8];
		Position pos = new Position(getRow(), getColumn());
		// above
		pos.setRow(getRow() - 1);
		while (pos.getRow() >= 0 && !board.thereIsAPiece(pos)) {
			mat[pos.getRow()][pos.getColumn()] = true;
			pos.setRow(pos.getRow() - 1);
		}
		if (pos.getRow() >= 0 && isThereOpponentPiece(pos))
			mat[pos.getRow()][pos.getColumn()] = true;

		// south
		pos.setRow(getRow() + 1);
		while (pos.getRow() < 8 && !board.thereIsAPiece(pos)) {
			mat[pos.getRow()][pos.getColumn()] = true;
			pos.setRow(pos.getRow() + 1);
		}

		if (pos.getRow() < 8 && isThereOpponentPiece(pos))
			mat[pos.getRow()][pos.getColumn()] = true;

		// east
		pos.setValues(getRow(), getColumn() + 1);
		while (pos.getColumn() < 8 && !board.thereIsAPiece(pos)) {
			mat[pos.getRow()][pos.getColumn()] = true;
			pos.setColumn(pos.getColumn() + 1);
		}
		if (pos.getColumn() < 8 && isThereOpponentPiece(pos))
			mat[pos.getRow()][pos.getColumn()] = true;

		// west
		pos.setColumn(getColumn() - 1);
		while (pos.getColumn() >= 0 && !board.thereIsAPiece(pos)) {
			mat[pos.getRow()][pos.getColumn()] = true;
			pos.setColumn(pos.getColumn() - 1);
		}
		if (pos.getColumn() >= 0 && isThereOpponentPiece(pos))
			mat[pos.getRow()][pos.getColumn()] = true;
		return mat;
	}
	
	@Override
	public ImageIcon image() {
		String img = DIRETORIO + colorString() + "Rook.png";
		return new ImageIcon(img);
	}

}
