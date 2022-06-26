import java.io.IOException;
import java.util.Random;

public class Jogo implements IJogo {
	private INetwork network;
	private IControler controler;
	private IView view;
	private boolean jogando = false;
	private int prioridade;
	private String comando;
	
	public void connect(INetwork network) {
		this.network = network;
	}
	
	public void connect(IControler controler) {
		this.controler = controler;
	}
	
	public void connect(IView view) {
		this.view = view;
	}
	
	public boolean estadoDeJogo() {
		return jogando;
	}
	
	public int decidir() {
		Random rand = new Random();
		prioridade = rand.nextInt(2) + 1;
		return prioridade;
	}
	
	Jogo() {
		jogando = true;
		decidir();
	}
	
	public void Server(int port) throws IOException {
		Network c = new Network(port);
		connect(c);
	}
	
	public void Cliente(String ip,int port) throws IOException {
		Network c = new Network(ip, port);
		connect(c);
	}
	
	public void receberComando() {
		comando = controler.setData();
	}
	
	public String getComando() {
		return comando;
	}
	
	public int[] action() {
		int i = comando.charAt(0) - '1';
		int j = comando.charAt(1) - '1';
		int k =  comando.charAt(2) - '0';
		int [] v = new int[3];
		v[0] = i;v[1] = j;v[2] = k;
		return v;
	}
	
	public void comunicaPlayer() throws IOException {
		if(prioridade != 1) {
			comando = network.read();
			view.realizarModificacao();
			view.printarCampo();
			receberComando();
			view.realizarModificacao();
			network.send(comando);
			view.printarCampo();
			return;
		}
		receberComando();
		view.realizarModificacao();
		network.send(comando);
		view.printarCampo();
		prioridade = 2;
	}
	
	public void enviarPrioridade() throws IOException {
		network.send(prioridade);
	}
	
	public void setarPrioridade() throws IOException {
		comando = network.read();
		prioridade = Integer.parseInt(comando) == 1 ? 2 : 1;
	}
	
	public void getPrioridade() {
		System.out.println(prioridade);
	}
}
