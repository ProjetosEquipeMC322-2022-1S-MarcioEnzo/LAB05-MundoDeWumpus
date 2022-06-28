package model.chess_pieces;
import exceptions.GameException;
import model.Position;

public class ChessPosition {
	private char column;
	private int row;
	
	public ChessPosition() {
		
	}
	
	public ChessPosition (char column, int row) {
		if (column < 'a' || column > 'h' || row < 1 || row > 8)
			throw new GameException("Invalid Chess Position");
		this.column = column;
		this.row = row;
	}
	
	public ChessPosition(Position position) {
		char column = (char) ('a' + position.getColumn());
		int row = position.getRow() + 1;
		this.row = row;
		this.column = column;
	}
	
	public ChessPosition(int row, int column) {
		if (column < 0 || column > 7 || row < 0 || column > 7)
			throw new GameException("Invalid Chess Position");
		this.row = row + 1;
		this.column = (char) ('a' + column);
	}

	public char getColumn() {
		return column;
	}

	public void setColumn(char column) {
		this.column = column;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}
	
	public Position toPosition() {
		return new Position(row - 1, (int) (column - 'a'));
	}
	
	public static ChessPosition toChessPosition(Position position) {
		return new ChessPosition(position);
	}
	
}
