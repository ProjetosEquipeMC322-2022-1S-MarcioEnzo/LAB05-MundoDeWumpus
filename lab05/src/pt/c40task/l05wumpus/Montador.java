package pt.c40task.l05wumpus;

public class Montador {
	
	public static void imprimirJogo(Controle controle) {
		char[][] mat = Componente.caverna.matrizDeCaracteres();
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				System.out.print(mat[i][j]);
			}
			System.out.println();
		}
		System.out.println("Score: " + controle.getScore());
	}
	
	public static void construir(char m[][]) {
		Sala s;
		for(int i = 0;i < 4; i++) {
			for(int j = 0;j < 4; j++) {
				if (Componente.caverna.getSala(i, j) == null) {
					s = new Sala(i, j, m[i][j]);
					Componente.caverna.setSala(i, j, s);
					}
				}
		}
	}


}
