package pt.c40task.l05wumpus;

public class Wumpus extends Componente {
	
	public Wumpus(int row, int column) {
		this.row = row;
		this.column = column;
		geraFedor();
	}

	@Override
	public char representacao() {
		return 'W';
	}
	
	private void geraFedor() {
		if (row > 0) { 
			if (caverna.getSala(row - 1, column) != null)
				caverna.getSala(row - 1, column).setComponente(2, new Fedor());
			else {
				Sala sala = new Sala(row - 1, column);
				sala.setComponente(2, new Fedor());
				caverna.setSala(row - 1, column, sala);
			}
		}
		if (row < 3) {
			if (caverna.getSala(row + 1, column) != null)
				caverna.getSala(row + 1, column).setComponente(2, new Fedor());
			else {
				Sala sala = new Sala(row + 1, column);
				sala.setComponente(2, new Fedor());
				caverna.setSala(row + 1, column, sala);
			}
		}
		if (column > 0) {
			if (caverna.getSala(row, column - 1) != null)
				caverna.getSala(row, column - 1).setComponente(2, new Fedor());
			else {
				Sala sala = new Sala(row, column - 1);
				sala.setComponente(2, new Fedor());
				caverna.setSala(row, column - 1, sala);
			}
		}
		if (column < 3) {
			if (caverna.getSala(row, column + 1) != null)
				caverna.getSala(row, column + 1).setComponente(2, new Fedor());
			else {
				Sala sala = new Sala(row, column + 1);
				sala.setComponente(2, new Fedor());
				caverna.setSala(row, column + 1, sala);
			}
		}
	}

}
