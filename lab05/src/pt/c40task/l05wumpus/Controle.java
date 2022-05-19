package pt.c40task.l05wumpus;

public class Controle {
	private int pontuacao = 0;
	private Hero personagem;
	private char estado;
	Componente [] x;
	
	boolean Valido(Caverna s) {
		int z = 0;
		for(int i = 1;4 >= i;i++) {
			for(int j = 1;4 >= j;j++) {
				x = s.getSala(i, j).getComponentes();
				for(int t = 0;3 >= t; t++) {
					if(x[t].representacao() == 'b'){
						z++;
						break;
				};
				}
			}
		}
		
		return 3 >= z && z >= 2;
	}
		
	void Adicionar_Pontos(int x) {
		pontuacao += x;
	}
	
	void Comando(char acao) {
		if(acao == 'q') {
			this.quitgame();
		}
		personagem.realizaAcao(acao);
		switch(acao) {
		case 'w':
		case 's':
		case 'd':
		case 'a':Adicionar_Pontos(-15);break;
		case 'k':Adicionar_Pontos(-100);break;
		default:break;
		}
		if(personagem.isVivo() != true) {
			Adicionar_Pontos(-1000);
			estado = 'L';
		}
		else if(personagem.isOrgulhoso() == true) {
			Adicionar_Pontos(+500);
		}
		Componente [] x = personagem.getSala().getComponentes();
		for(int t = 0; 3 >= t; t++) {
			if(x[t].representacao() == 'b') {
				System.out.println("O heroi sente um vento frio");
			}
			else if(x[t].representacao() == 'f') {
				System.out.println("O heroi sente um cheiro forte");
			}
			else if(x[t].representacao() == 'B') {
				Adicionar_Pontos(-1000);
				estado = 'L';
				personagem.Matar();
			}
		}
	}
	
	public void setGame() {
		this.personagem = new Hero(1, 1);
		this.estado = 'P';
	}

	public boolean Vencedor(Hero heroi) {
		boolean venceu = false;
		if(heroi.temOuro() == true) {
			if(heroi.getSala().getRow() == 1 && 
				heroi.getSala().getColumn() == 1) {
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
	
	
	
}
