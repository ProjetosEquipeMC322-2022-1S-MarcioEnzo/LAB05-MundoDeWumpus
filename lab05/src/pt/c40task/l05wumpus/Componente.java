package pt.c40task.l05wumpus;

public abstract class Componente {
	protected int row, column;
	protected static Caverna caverna;

	public Componente(int row, int column) {
		if (row >= 0 && row < 4 && column >= 0 && column < 4) {
			this.row = row;
			this.column = column;
		}
		else
			throw new GameException("Posição (" + row + "," + column + ") inválida" );
	}

	public abstract char representacao();
}
