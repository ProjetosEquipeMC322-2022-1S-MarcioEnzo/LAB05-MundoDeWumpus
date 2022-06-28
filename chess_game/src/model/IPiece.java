package model;

import java.io.Serializable;

import javax.swing.ImageIcon;

public interface IPiece extends Serializable {
	public boolean[][] possibleMoves();
	public Board getBoard();
	
	public default boolean possibleMove(int row, int column) {
		return possibleMoves()[row][column];
	}
	public default boolean possibleMove(Position pos) {
		return possibleMove(pos.getRow(), pos.getColumn());
	}
	
	public Position getPosition();
	public default int getRow() {
		return getPosition().getRow();
	}
	
	public default int getColumn() {
		return getPosition().getColumn();
	}
	
	public default boolean isThereAnyPossibleMove() {
		boolean[][] mat = possibleMoves();
		int width = mat.length;
		int heigth = mat[0].length;
		for (int i = 0; i < width; i++) 
			for (int j = 0; j < heigth; j++)
				if (mat[i][j])
					return true;
		return false;
	}
	
	public ImageIcon image();
	
}
