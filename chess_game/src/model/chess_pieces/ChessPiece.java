package model.chess_pieces;

import model.Board;
import model.Piece;
import model.Position;

public abstract class ChessPiece extends Piece {
	protected ChessColor color;
	private int moveCount;
	private static final long serialVersionUID = -1787783028825795810L;
	protected static String DIRETORIO = "C:\\MC322-equipe\\chess_game\\assets\\";
	
	public ChessPiece(Position pos, Board board) {
		super(pos, board);
		moveCount = 0;
	}
	
	public ChessPiece(Position pos, Board board, ChessColor color) {
		super(pos, board);
		this.color = color;
		moveCount = 0;
	}

	public ChessColor getColor() {
		return color;
	}

	public void setColor(ChessColor color) {
		this.color = color;
	}
	
	public int getMoveCount() {
		return moveCount;
	}

	public void increaseMoveCount() {
		moveCount++;
	}
	
	public void decreaseMoveCount() {
		moveCount--;
	}

	public boolean isThereOpponentPiece(Position pos) {
		return board.thereIsAPiece(pos) && ((ChessPiece) board.piece(pos)).getColor() != color;
	}
	
	public boolean isThereOpponentPiece(int row, int column) {
		return isThereOpponentPiece(new Position(row, column));
	}
	
	public String colorString() {
		return (color == ChessColor.WHITE)? "white" : "black";
	}
	

}
