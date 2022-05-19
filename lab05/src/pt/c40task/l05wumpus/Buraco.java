package pt.c40task.l05wumpus;

public class Buraco extends Componente {

	public Buraco(int row, int column) {
		super(row, column);
		this.CriarBrisas();
	}
	
	public void CriarBrisas() {
		Sala s;
		s = new Sala(row + 1, column, 'b',Componente.caverna);
		caverna.setSala(row + 1, column, s);
		s = new Sala(row - 1, column, 'b', Componente.caverna);
		caverna.setSala(row - 1, column, s);
		s = new Sala(row, column + 1, 'b',Componente.caverna);
		caverna.setSala(row , column + 1, s);
		s = new Sala(row , column - 1, 'b', Componente.caverna);
		caverna.setSala(row, column - 1, s);
	}

	@Override
	public char representacao() {
		return 'B';
	}

}
