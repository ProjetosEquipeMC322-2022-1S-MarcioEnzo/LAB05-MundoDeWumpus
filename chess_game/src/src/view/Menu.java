package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Graphics2D;

public class Menu implements IMenu {
	
	private Rectangle playButton = new Rectangle(250, 150, 100, 50);
	private Rectangle gameModeButton = new Rectangle(250, 250, 100, 50);

	@Override
	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		
		Font font0 = new Font("arial", Font.BOLD, 50);
		g.setFont(font0);
		g.setColor(Color.WHITE);
		g.drawString("Crazy Chess!", 150, 100);
		
		Font font1 = new Font("arial", Font.BOLD, 30);
		g.setFont(font1);
		
		g.drawString("Start", playButton.x + 15, playButton.y + 32);
		g2d.draw(playButton);
		g.drawString("Mode", gameModeButton.x + 15, gameModeButton.y + 32);
		g2d.draw(gameModeButton);
	}

}
