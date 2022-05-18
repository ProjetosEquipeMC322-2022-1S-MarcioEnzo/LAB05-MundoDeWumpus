package pt.c40task.l05wumpus;

public class Buraco extends Componente {

	public Buraco(int row, int column) {
		super(row, column);
	}

	@Override
	public char representacao() {
		return 'b';
	}

}
