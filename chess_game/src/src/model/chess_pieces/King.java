package model.chess_pieces;

import javax.swing.ImageIcon;

import control.ChessMatch;
import model.Board;
import model.Position;

public class King extends ChessPiece {
	private static final long serialVersionUID = -7465175775202140451L;
	private ChessMatch chessMatch;

	public King(Position pos, Board board, ChessColor color, ChessMatch chessMatch) {
		super(pos, board, color);
		this.chessMatch = chessMatch;
	}

	private boolean canMove(Position position) {
		ChessPiece p = (ChessPiece) getBoard().piece(position);
		return p == null || p.getColor() != getColor();
	}
	
	private boolean testRookCastling(Position position) {
		ChessPiece p = (ChessPiece) getBoard().piece(position);
		return p != null && p instanceof Rook && p.getColor() == getColor() && p.getMoveCount() == 0;
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
		Position p = new Position(0, 0);

		// above
		p.setValues(position.getRow() - 1, position.getColumn());
		if (getBoard().positionExists(p) && canMove(p))
			mat[p.getRow()][p.getColumn()] = true;

		// below
		p.setValues(position.getRow() + 1, position.getColumn());
		if (getBoard().positionExists(p) && canMove(p))
			mat[p.getRow()][p.getColumn()] = true;

		// left
		p.setValues(position.getRow(), position.getColumn() - 1);
		if (getBoard().positionExists(p) && canMove(p))
			mat[p.getRow()][p.getColumn()] = true;

		// right
		p.setValues(position.getRow(), position.getColumn() + 1);
		if (getBoard().positionExists(p) && canMove(p))
			mat[p.getRow()][p.getColumn()] = true;

		// nw
		p.setValues(position.getRow() - 1, position.getColumn() - 1);
		if (getBoard().positionExists(p) && canMove(p))
			mat[p.getRow()][p.getColumn()] = true;

		// ne
		p.setValues(position.getRow() - 1, position.getColumn() + 1);
		if (getBoard().positionExists(p) && canMove(p))
			mat[p.getRow()][p.getColumn()] = true;

		// sw
		p.setValues(position.getRow() + 1, position.getColumn() - 1);
		if (getBoard().positionExists(p) && canMove(p))
			mat[p.getRow()][p.getColumn()] = true;

		// se
		p.setValues(position.getRow() + 1, position.getColumn() + 1);
		if (getBoard().positionExists(p) && canMove(p))
			mat[p.getRow()][p.getColumn()] = true;
		
		// #specialmove castling
		if (getMoveCount() == 0 && !chessMatch.getCheck()) {
					// #specialmove kingside rook
					Position posT1 = new Position(position.getRow(), position.getColumn() + 3);
		if (testRookCastling(posT1)) {
			Position p1 = new Position(position.getRow(), position.getColumn() + 1);
			Position p2 = new Position(position.getRow(), position.getColumn() + 2);
				if (getBoard().piece(p1) == null && getBoard().piece(p2) == null)
					mat[p2.getRow()][p2.getColumn()] = true;
					}
		// #specialmove queenside rook
			Position posT2 = new Position(position.getRow(), position.getColumn() - 4);
			if (testRookCastling(posT2)) {
				Position p1 = new Position(position.getRow(), position.getColumn() - 1);
				Position p2 = new Position(position.getRow(), position.getColumn() - 2);
				Position p3 = new Position(position.getRow(), position.getColumn() - 3);
				if (getBoard().piece(p1) == null && getBoard().piece(p2) == null && getBoard().piece(p3) == null)
					mat[p2.getRow()][p2.getColumn()] = true;
				}
		}
		return mat;
		
	}

	@Override
	public ImageIcon image() {
		String img = DIRETORIO + colorString() + "King.png";
		return new ImageIcon(img);
	}

}
