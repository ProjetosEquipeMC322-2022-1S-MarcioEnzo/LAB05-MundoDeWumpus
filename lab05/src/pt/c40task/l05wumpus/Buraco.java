package pt.c40task.l05wumpus;

public class Buraco extends Componente {

	public Buraco(int row, int column) {
		super(row, column);
		this.CriarBrisas();
	}
	
	public void CriarBrisas() {
		Sala s;
		s = new Sala(row + 1, column, '_', Componente.caverna);
		s.setComponente(3, new Brisa(row + 1, column));
		Componente.caverna.setSala(row + 1, column, s);
		s = new Sala(row - 1, column, '_', Componente.caverna);
		s.setComponente(3, new Brisa(row - 1, column));
		Componente.caverna.setSala(row - 1, column, s);
		s = new Sala(row, column + 1, '_', Componente.caverna);
		s.setComponente(3, new Brisa(row, column + 1));
		Componente.caverna.setSala(row , column + 1, s);
		s = new Sala(row, column - 1, '_', Componente.caverna);
		s.setComponente(3, new Brisa(row, column - 1));
		Componente.caverna.setSala(row , column - 1, s);
	}

	@Override
	public char representacao() {
		return 'B';
	}

}
