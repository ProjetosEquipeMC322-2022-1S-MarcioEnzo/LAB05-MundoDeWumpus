package model;

import java.io.Serializable;

import exceptions.BoardException;

public class Board implements Serializable {
	private static final long serialVersionUID = 1764002491885376246L;
	private int rows, columns;
	private Piece[][] pieces;

	public Board() {
		rows = 8;
		columns = 8;
		pieces = new Piece[8][8];
	}

	public Board(int rows, int columns) throws BoardException {
		if (rows < 1 || columns < 1)
			throw new BoardException("The board must have at least one row and one column");
		this.rows = rows;
		this.columns = columns;
		pieces = new Piece[rows][columns];
	}

	public int getRows() {
		return rows;
	}

	public int getColumns() {
		return columns;
	}

	public Piece piece(int row, int column) {
		if (!positionExists(row, column))
			throw new BoardException("Position not on the board");
		return pieces[row][column];
	}

	public Piece piece(Position position) {
		if (!positionExists(position))
			throw new BoardException("Position not on the board");
		return pieces[position.getRow()][position.getColumn()];
	}

	public void placePiece(Piece piece, Position position) {
		if (thereIsAPiece(position))
			throw new BoardException("There is already a piece on position " + position);
		pieces[position.getRow()][position.getColumn()] = piece;
		piece.setPosition(position);;
	}

	public Piece removePiece(Position position) {
		if (!positionExists(position))
			throw new BoardException("Position not on the board");
		if (piece(position) == null)
			return null;
		Piece aux = piece(position);
		aux.setPosition(position);;
		pieces[position.getRow()][position.getColumn()] = null;
		return aux;
	}

	private boolean positionExists(int row, int column) {
		return row >= 0 && row < rows && column >= 0 && column < columns;
	}

	public boolean positionExists(Position position) {
		return positionExists(position.getRow(), position.getColumn());
	}

	public boolean thereIsAPiece(Position position) {
		if (!positionExists(position))
			throw new BoardException("Position not on the board");
		return piece(position) != null;
	}
	
	public boolean thereIsAPiece(int row, int column) {
		return thereIsAPiece(new Position(row, column));
	}

}
