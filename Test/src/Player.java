
import java.util.Scanner;

public class Player implements IPlayer{
	private IView view;
	private Scanner data;
	
	Player () {
		data = new Scanner(System.in);
	}
	
	public void connect(IView view) {
		this.view = view;
	}

	public String getComando() {
		view.printarInterface();
		return data.nextLine();
	}
	
}
