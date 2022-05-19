package pt.c40task.l05wumpus;

public class Wumpus extends Componente {

	public Wumpus(int row, int column) {
		super(row, column);
		this.CriarFedor();
	}
	
	public void CriarFedor() {
		Sala s;
		s = new Sala(row + 1, column, '_', Componente.caverna);
		s.setComponente(2, new Fedor(row + 1, column));
		Componente.caverna.setSala(row + 1, column, s);
		s = new Sala(row - 1, column, '_', Componente.caverna);
		s.setComponente(2, new Fedor(row - 1, column));
		Componente.caverna.setSala(row - 1, column, s);
		s = new Sala(row, column + 1, '_', Componente.caverna);
		s.setComponente(2, new Fedor(row, column + 1));
		Componente.caverna.setSala(row , column + 1, s);
		s = new Sala(row, column - 1, '_', Componente.caverna);
		s.setComponente(2, new Fedor(row, column - 1));
		Componente.caverna.setSala(row , column - 1, s);
	}
	
	
	@Override
	public char representacao() {
		return 'W';
	}
}
