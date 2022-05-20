package pt.c40task.l05wumpus;

public abstract class Componente {
	protected int row, column;
	public static Caverna caverna;
	
	public abstract char representacao();
	
	public int getRow() {
		return row;
	}
	
	public int getColumn() {
		return column;
	}
}
