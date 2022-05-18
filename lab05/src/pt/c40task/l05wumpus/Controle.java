package pt.c40task.l05wumpus;

public class Controle implements Construtor {
	private int pontuacao = 0;
	private Hero personagem;
	Componente [] x;
	
	boolean Valido(Caverna s) {
		int z = 0;
		for(int i = 1;2 >= i;i++) {
			for(int j = 1; 2 >= j;j++) {
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
		personagem.realizaAcao(acao);
		Componente [] x = personagem.getSala().getComponentes();
		for(int t = 0; 3 >= t; t++) {
			if(x[t].representacao() == 'B') {
				System.out.println("O heroi sente um vento frio");
			}
			else if(x[t].representacao() == 'f') {
				System.out.println("O heroi sente um cheiro forte");
			}
			else if(x[t].representacao() == 'b') {
				System.out.println("Voce perdeu!\n");
			}
		}
	}
	
	public void setGame() {
		this.personagem = new Hero(0, 0);
	}
	
	public void Construir(char m[][]) {
		Sala s;
		for(int i = 0;3 >= i; i++) {
			for(int j = 0;3 >= j; j++) {
				s = new Sala(i, j, m[i][j], personagem.caverna);
				personagem.caverna.setSala(i, j, s);
				if(m[i][j] == 'b') {
					s = new Sala(i + 1, j, 'B', personagem.caverna);
					personagem.caverna.setSala(i + 1, j, s);
					s = new Sala(i - 1, j, 'B', personagem.caverna);
					personagem.caverna.setSala(i - 1, j, s);
					s = new Sala(i, j + 1, 'B', personagem.caverna);
					personagem.caverna.setSala(i , j + 1, s);
					s = new Sala(i, j - 1, 'B', personagem.caverna);
				}
				else if (m[i][j] == 'w') {
					s = new Sala(i + 1, j, 'f', personagem.caverna);
					personagem.caverna.setSala(i + 1, j, s);
					s = new Sala(i - 1, j, 'f', personagem.caverna);
					personagem.caverna.setSala(i - 1, j, s);
					s = new Sala(i, j + 1, 'f', personagem.caverna);
					personagem.caverna.setSala(i , j + 1, s);
					s = new Sala(i, j - 1, 'f', personagem.caverna);
				}
			} 
		}
	}
	public void AtualizarCaverna() {
	boolean x = personagem.getSala().isVisitada();
	personagem.getSala().setVisitada(x ^ true);
	}
	
}
