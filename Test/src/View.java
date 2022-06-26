
public class View implements IView{
	private IJogo jogo;
	private String[] menu;
	private int [][] tabuleiro;
	
	View() {
		this.menu = new String[4];
		menu[0] = "Jogo Iniciado";
		menu[1] = "Duas opcoes existem";
		menu[2] = "criar : digite o port da sala";
		menu[3] = "entrar : digite (ip, port)";
		tabuleiro = new int[3][3];
		for(int i = 0;3 > i; i++) {
			for(int j = 0; 3 > j; j++) {
				tabuleiro[i][j] = 0;
			}
		}
	}
	
	public void connect(IJogo jogo) {
		this.jogo = jogo;
	}

	public void printarCampo() {
		for(int i = 0;3 > i; i++) {
			for(int j = 0; 3 > j; j++) {
				System.out.print(tabuleiro[i][j] + (j != 2 ? " " : "\n"));
			}
		}
		System.out.println("= = = = = = = = =");
	}

	public void printarMenu() {
		for(int i = 0;4 > i; i ++) {
			System.out.println(menu[i]);
		}
	}

	public void printarOrdem() {
		System.out.println(jogo.decidir());
	}
	
	public void realizarModificacao() {
		int [] a = jogo.action();
		tabuleiro[a[0]][a[1]] = a[2];
	}
	
	public void printarInterface() {
		System.out.println("Insira seu comando");		
	}

}
