package pt.c40task.l05wumpus;

public class Caverna {
	public Sala [][] salas = new Sala[4][4];
		
	public Sala getSala(int row, int column) {
		return salas[row][column];
	}
	
	public void setSala(int row, int column, Sala sala) {
		salas[row][column] = sala;
	}
	
	public char[][] matrizDeCaracteres() {
		char[][] mat = new char[4][4];
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				if (!salas[i][j].isVisitada())
					mat[i][j] = '_';
				else if (salas[i][j].getComponentes()[0] != null)
					mat[i][j] = salas[i][j].getComponentes()[0].representacao();
				else if (salas[i][j].getComponentes()[1] != null)
					mat[i][j] = salas[i][j].getComponentes()[1].representacao();
				else if (salas[i][j].getComponentes()[2] != null)
					mat[i][j] = salas[i][j].getComponentes()[2].representacao();
				else if (salas[i][j].getComponentes()[3] != null)
					mat[i][j] = salas[i][j].getComponentes()[3].representacao();
				else
					mat[i][j] = '#';
			}
		}
		return mat;
	}
	
}
