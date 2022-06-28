package model.chess_pieces;

import javax.swing.ImageIcon;

import control.ChessMatch;
import model.Board;
import model.Position;

public class Pawn extends ChessPiece {
	private static final long serialVersionUID = -8104323997924876260L;
	private ChessMatch chessMatch;

	public Pawn(Position pos, Board board, ChessColor color, ChessMatch chessMatch) {
		super(pos, board, color);
		this.chessMatch = chessMatch;
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];

		Position p = new Position(0, 0);

		if (getColor() == ChessColor.WHITE) {
			p.setValues(position.getRow() - 1, position.getColumn());

			// above
			if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
				p.setRow(p.getRow() - 1);
				if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) && getMoveCount() == 0)
					mat[p.getRow()][p.getColumn()] = true;
			}

			// nw
			p.setValues(position.getRow() - 1, position.getColumn() - 1);
			if (getBoard().positionExists(p) && isThereOpponentPiece(p))
				mat[p.getRow()][p.getColumn()] = true;

			// ne
			p.setColumn(position.getColumn() + 1);
			if (getBoard().positionExists(p) && isThereOpponentPiece(p))
				mat[p.getRow()][p.getColumn()] = true;

			// #specialmove en passant white
			if (position.getRow() == 3) {
				Position left = new Position(3, position.getColumn() - 1);
				if (getBoard().positionExists(left) && isThereOpponentPiece(left)
						&& getBoard().piece(left) == chessMatch.getEnPassantVulnerable())
					mat[2][left.getColumn()] = true;

				Position right = new Position(3, position.getColumn() + 1);
				if (getBoard().positionExists(right) && isThereOpponentPiece(right)
						&& getBoard().piece(right) == chessMatch.getEnPassantVulnerable())
					mat[2][right.getColumn()] = true;
			}

		} else {
			p.setValues(position.getRow() + 1, position.getColumn());

			// above
			if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
				p.setRow(p.getRow() + 1);
				if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) && getMoveCount() == 0)
					mat[p.getRow()][p.getColumn()] = true;
			}

			// nw
			p.setValues(position.getRow() + 1, position.getColumn() + 1);
			if (getBoard().positionExists(p) && isThereOpponentPiece(p))
				mat[p.getRow()][p.getColumn()] = true;

			// ne
			p.setColumn(position.getColumn() - 1);
			if (getBoard().positionExists(p) && isThereOpponentPiece(p))
				mat[p.getRow()][p.getColumn()] = true;

			// #specialmove en passant black
			if (position.getRow() == 4) {
				Position left = new Position(4, position.getColumn() + 1);
				if (getBoard().positionExists(left) && isThereOpponentPiece(left)
						&& getBoard().piece(left) == chessMatch.getEnPassantVulnerable())
					mat[5][left.getColumn()] = true;

				Position right = new Position(4, position.getColumn() - 1);
				if (getBoard().positionExists(right) && isThereOpponentPiece(right)
						&& getBoard().piece(right) == chessMatch.getEnPassantVulnerable())
					mat[5][right.getColumn()] = true;
			}
		}
		return mat;
	}
	
	@Override
	public ImageIcon image() {
		String img = DIRETORIO + colorString() + "Pawn.png";
		return new ImageIcon(img);
	}

}
