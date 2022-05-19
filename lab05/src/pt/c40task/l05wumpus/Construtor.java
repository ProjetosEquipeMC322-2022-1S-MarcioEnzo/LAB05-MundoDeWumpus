package pt.c40task.l05wumpus;

public class Construtor {	
	public void Construir(char m[][]) {
		Sala s;
		for(int i = 1;4 >= i; i++) {
			for(int j = 1;4 >= j; j++) {
				s = new Sala(i, j, m[i][j], Componente.caverna);
				Componente.caverna.setSala(i, j, s);
				}
		} 
	}
}
