package pt.c40task.l05wumpus;

public class Buraco extends Componente {
	
	public Buraco(int row, int column) {
		this.row = row;
		this.column = column;
		geraBrisa();
	}

	@Override
	public char representacao() {
		return 'B';
	}

	private void geraBrisa() {
		if (row > 0) { 
			if (caverna.getSala(row - 1, column) != null)
				caverna.getSala(row - 1, column).setComponente(3, new Brisa());
			else {
				Sala sala = new Sala(row - 1, column);
				sala.setComponente(3, new Brisa());
				caverna.setSala(row - 1, column, sala);
			}
		}
		if (row < 3) {
			if (caverna.getSala(row + 1, column) != null)
				caverna.getSala(row + 1, column).setComponente(3, new Brisa());
			else {
				Sala sala = new Sala(row + 1, column);
				sala.setComponente(3, new Brisa());
				caverna.setSala(row + 1, column, sala);
			}
		}
		if (column > 0) {
			if (caverna.getSala(row, column - 1) != null)
				caverna.getSala(row, column - 1).setComponente(3, new Brisa());
			else {
				Sala sala = new Sala(row, column - 1);
				sala.setComponente(3, new Brisa());
				caverna.setSala(row, column - 1, sala);
			}
		}
		if (column < 3) {
			if (caverna.getSala(row, column + 1) != null)
				caverna.getSala(row, column + 1).setComponente(3, new Brisa());
			else {
				Sala sala = new Sala(row, column + 1);
				sala.setComponente(3, new Brisa());
				caverna.setSala(row, column + 1, sala);
			}
		}
	}
}
