
public class Controler implements IControler{
	private String Data;
	private IPlayer player;
	

	public String setData() {
		Data = player.getComando();
		return Data;
	}
	
	public void connect(IPlayer p) {
		this.player = p;
	}
	
}
