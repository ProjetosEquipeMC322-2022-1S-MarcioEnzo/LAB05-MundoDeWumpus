package pt.c40task.l05wumpus;

public class Wumpus extends Componente {

	public Wumpus(int row, int column) {
		super(row, column);
	}

	@Override
	public char representacao() {
		return 'W';
	}
}
