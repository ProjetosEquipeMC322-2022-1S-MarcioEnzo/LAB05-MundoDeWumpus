package pt.c40task.l05wumpus;

public class Sala {
	private int row, column;
	private Componente[] componentes = new Componente[4];
	private boolean visitada;
	private Caverna caverna;
	
	
	public Sala(int row, int column, char componente, Caverna caverna) {
		this.row = row;
		this.column = column;
		this.caverna = caverna;
		
		visitada = (row == 1 && column == 1) ? true : false;
		if (componente == 'P') 
			componentes[1] = new Hero(row, column);
		else if (componente == 'O')
			componentes[0] = new Ouro(row, column);
		else if (componente == 'W')
			componentes[0] = new Wumpus(row, column);
		else if (componente == 'B')
			componentes[0] = new Buraco(row, column);
		else if (componente != '_')
			throw new GameException("Erro na criação da sala!");
	}

	public boolean isVisitada() {
		return visitada;
	}
	
	public void setVisitada(boolean visitada) {
		this.visitada = visitada;
	}

	public int getRow() {
		return row;
	}
	
	public int getColumn() {
		return column;
	}
	
	public Componente[] getComponentes() {
		return componentes;
	}
	
	public void setComponente(int posicao, Componente novo) {
		componentes[posicao] = novo;
	}
}
