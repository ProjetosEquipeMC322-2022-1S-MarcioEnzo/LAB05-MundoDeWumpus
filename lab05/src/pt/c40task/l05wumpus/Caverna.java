package pt.c40task.l05wumpus;

public class Caverna {
	private Sala [][] salas = new Sala[4][4];
	
	public Sala getSala(int row, int column) {
		return salas[row][column];
	}
}
