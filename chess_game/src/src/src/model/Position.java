package model;

import java.io.Serializable;

import exceptions.BoardException;

public class Position implements Serializable {
	private static final long serialVersionUID = -1268778742952325653L;
	private int row, column;
	
	public Position(int row, int column) {
		if (row < 0 || column < 0)
			throw new BoardException("Invalid position");
		this.row = row;
		this.column = column;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}
	
	public void setValues(int row, int column) {
		setRow(row);
		setColumn(column);
	}
	

}
