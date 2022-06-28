package model;

public abstract class Piece implements IPiece {
	
	private static final long serialVersionUID = -6873332819602761313L;
	protected Position position;
	protected Board board;
	
	public Piece(Position pos, Board board) {
		this.position = pos;
		this.board = board;
	}
	
	public Board getBoard() {
		return board;
	}
	public Position getPosition() {
		return position;
	}
	
	public void setPosition(Position pos) {
		this.position = pos;
	}
}
