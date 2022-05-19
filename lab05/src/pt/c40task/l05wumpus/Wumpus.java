package pt.c40task.l05wumpus;

public class Wumpus extends Componente {

	public Wumpus(int row, int column) {
		super(row, column);
		this.CriarFedor();
	}
	
	public void CriarFedor() {
		Sala s;
		s = new Sala(row + 1, column, 'f', Componente.caverna);
		Componente.caverna.setSala(row + 1, column, s);
		s = new Sala(row - 1, column, 'f', Componente.caverna);
		Componente.caverna.setSala(row - 1, column, s);
		s = new Sala(row, column + 1, 'f', Componente.caverna);
		Componente.caverna.setSala(row , column + 1, s);
		s = new Sala(row, column - 1, 'f', Componente.caverna);
	}
	
	
	@Override
	public char representacao() {
		return 'W';
	}
}
