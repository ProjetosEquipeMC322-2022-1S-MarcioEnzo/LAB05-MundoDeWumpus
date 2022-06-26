import java.io.IOException;

public class AppJogo {
	public static void main(String[] args) throws NumberFormatException, IOException {
		Player p;
		Controler c;
		View v;
		Jogo j;
		
		c = new Controler();
		p = new Player();
		v = new View();
		j = new Jogo();
		
		p.connect(v);
		c.connect(p);
		j.connect(c);
		j.connect(v);
		v.connect(j);
		
		v.printarMenu();
		j.receberComando();
		
		int port;
		String ip;
		
		ip = j.getComando();
		j.receberComando();
		port = Integer.parseInt(j.getComando());
		if(ip.charAt(0) == 'c') {
			j.Server(port);
			j.decidir();
			j.enviarPrioridade();
		}
		
		else {
			j.receberComando();
			ip = j.getComando();
			j.Cliente(ip, port);
			j.setarPrioridade();
		}
		
		j.getPrioridade();
		
		while(true) {
			j.comunicaPlayer();
		}	
	}
}
