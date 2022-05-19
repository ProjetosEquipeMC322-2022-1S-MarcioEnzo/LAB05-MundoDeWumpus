package pt.c40task.l05wumpus;

public class Controle {
	private int pontuacao = 0;
	private Hero personagem;
	private char estado;
	Componente [] x;
	
	public Controle() {
		
	}
	
	
	public static boolean Valido(Caverna s) {
		int qtdeWumpus = 0, qtdeHeroi = 0, qtdeBuraco = 0, qtdeOuro = 0;
		
		for (int i = 1; i <= 4; i++) {
			for (int j = 1; j <= 4; j++) {
				if (Componente.getSala(row, column).getComponentes[0] instanceof Wumpus)
					qtdeWumpus++;
				else if (Componente.getSala(row, column).getComponentes[0] instanceof Buraco)
					qtdeBuraco++;
				else if (Componente.getSala(row, column).getComponentes[0] instanceof Ouro)
					qtdeOuro++;
				if (Componente.getSala(row, column).getComponentes[1] != null)
					qtdeHeroi++;
			}
		}
		return qtdeHeroi == 1 && qtdeWumpus == 1 && qtdeOuro == 1 && (qtdeBuraco == 2 || qtdeBuraco == 3);
	}
		
	void Adicionar_Pontos(int x) {
		pontuacao += x;
	}
	
	void Comando(char acao) {
		if(acao == 'q') {
			this.quitgame();
			return;
		}
		personagem.realizaAcao(acao);
		switch(acao) {
		case 'w':;
		case 's':;
		case 'd':;
		case 'a':Adicionar_Pontos(-15);break;
		case 'k':Adicionar_Pontos(-100);break;
		default:break;
		}
		Sala x = personagem.getSala();
			if(x.getComponentes[0] instanceof Wumpus) {
					if(personagem.enfrentaWumpus()) {
						Adicionar_Pontos(500);
					}
					else {
						Adicionar_Pontos(-1000);
						estado = 'L';
					}
			}
			else if(x.getComponentes[0] instanceof Buraco) {
				Adicionar_Pontos(-1000);
				estado = 'L';
				personagem.Morrer();
			}
			if (estado == 'L')
				return;
			if (personagem.temFlecha() && acao != 'k') {
				personagem.perdeFlecha();
				System.out.println("Droga! Gastei minha única flecha a toa.");
			}
			if(x.getComponentes[2] instanceof Fedor) {
				System.out.println("O heroi sente um cheiro forte");
			}
			if(x.getComponentes[3] instanceof Brisa) {
				System.out.println("O heroi sente um vento gelado");
			}
		}
	}
	
	public void setGame() {
		this.personagem = new Hero(1, 1);
		this.estado = 'P';
	}

	public boolean Vencedor() {
		boolean venceu = false;
		if(heroi.temOuro() == true) {
			if(personagem.getSala().getRow() == 1 && 
				personagem.getSala().getColumn() == 1) {
				venceu = true;
				this.estado = 'W';
			}
		}
		return venceu;
	}
	
	public void estadodejogo() {
		System.out.println("score:" + " " + pontuacao);
		System.out.println("status:" + " " + estado);
	}
	
	public void quitgame() {
		this.estadodejogo();
		if(this.Vencedor(personagem) == true) {
			System.out.println("Voce ganhou =D !!!");
			return;
		}
		if(estado == 'L') {
			System.out.println("Voce perdeu =(...");
			return;
		}
		System.out.println("Volte sempre !");
	}
	
	public static void imprimirJogo(Caverna caverna) {
		for (int i = 1; i <= 4; i++) {
			for (int j = 1; j <= 4; j++) {
				if (!caverna.getSala(i, j).isVisitada())
					System.out.print("_");
				else if (caverna.getSala(i, j).getComponentes[0] != null)
					System.out.print(caverna.getSala(i, j).getComponentes[0].representacao());
				else if (caverna.getSala(i, j).getComponentes[1] != null)
					System.out.print(caverna.getSala(i, j).getComponentes[1].representacao());
				else if (caverna.getSala(i, j).getComponentes[2] != null)
					System.out.print(caverna.getSala(i, j).getComponentes[2].representacao());
				else if (caverna.getSala(i, j).getComponentes[3] != null)
					System.out.print(caverna.getSala(i, j).getComponentes[3].representacao());
				else
					System.out.print("#");
				
			}
			System.out.println();
		}
	}
	
}
