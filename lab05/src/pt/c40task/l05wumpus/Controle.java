package pt.c40task.l05wumpus;

public class Controle {
	private int score;
	private Hero hero;
	private char estado;

	public Controle(Hero hero) {
		score = 0;
		estado = 'P';
		this.hero = hero;
	}
	
	public int getScore() {
		return score;
	}
	
	public char getStatus() {
		return estado;
	}

	public static boolean validarCaverna() {
		int qtdeWumpus = 0, qtdeHeroi = 0, qtdeBuraco = 0, qtdeOuro = 0;

		for (int i = 0; i <= 3; i++) {
			for (int j = 0; j <= 3; j++) {
				if (Componente.caverna.getSala(i, j).getComponentes()[0] instanceof Wumpus)
					qtdeWumpus++;
				else if (Componente.caverna.getSala(i,j).getComponentes()[0] instanceof Buraco)
					qtdeBuraco++;
				else if (Componente.caverna.getSala(i,j).getComponentes()[0] instanceof Ouro)
					qtdeOuro++;
				if (Componente.caverna.getSala(i,j).getComponentes()[1] != null)
					qtdeHeroi++;
			}
		}
		return qtdeHeroi == 1 && qtdeWumpus == 1 && qtdeOuro == 1 && (qtdeBuraco == 2 || qtdeBuraco == 3);

	}
	
	public boolean vencedor() {
		if (hero.temOuro() && hero.getRow() == 0 && hero.getColumn() == 0)
			return true;
		return false;
	}
	
	public void adicionarPontos(int pontos) {
		this.score += pontos;
	}
	
	public void realizarComando(char comando) {
		if (comando == 'q') {
			quitGame();
			return;
		}
		hero.realizaAcao(comando);
		if (isAMove(comando)) {
			adicionarPontos(-15);
			if (hero.getSala().getComponentes()[0] instanceof Wumpus) {
				boolean sucesso = hero.enfrentaWumpus();
				if (!sucesso) {
					estado = 'L';
					adicionarPontos(-1000);
					quitGame();
					return;
				}
				else {
					adicionarPontos(500);
					System.out.println("O Wumpus está morto!");
				}
			}
			else if (hero.getSala().getComponentes()[0] instanceof Buraco) {
				adicionarPontos(-1000);
				System.out.println("AHHHHHHHHHHHhhhhhh.....");
				quitGame();
				return;
			}
			if (hero.flechaEquipada()) {
				hero.perdeFlecha();
				System.out.println("Droga! Acabei de perder a minha única flecha. Tomara que não encontre o Wumpus");
			}
			if (hero.getSala().getComponentes()[2] != null)
				System.out.println("Que catinga horrível é essa? Abriram uma vala?");
			if (hero.getSala().getComponentes()[3] != null)
				System.out.println("Sinto um vento frio, melhor tomar cuidado.");
		}
		if (comando == 'k')
			adicionarPontos(-100);
		if (vencedor()) {
			quitGame();
		}
	}
	
	public void estadoDeJogo() {
		System.out.println("score:" + " " + score);
		System.out.println("status:" + " " + estado);
	}
	
	public void quitGame() {
		this.estadoDeJogo();
		if(vencedor()) {
			adicionarPontos(1000);
			estado = 'W';
			System.out.println("Voce ganhou =D !!!");
		}
		else if(estado == 'L') {
			System.out.println("Voce perdeu =(...");
			return;
		}
		else {
			estado = 'Q';
			System.out.println("Volte sempre !");
		}
	}
	private boolean isAMove(char comando) {
		return comando == 'w' || comando == 'a' || comando == 's' || comando == 'd';
	}
}
