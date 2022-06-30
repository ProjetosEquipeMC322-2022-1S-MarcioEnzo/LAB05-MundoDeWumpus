package online;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import control.ChessMatch;
import model.chess_pieces.ChessColor;
import view.BoardGUI;
import view.Menu;

public class Jogar {
	private INetwork network;
	private int prioridade;
	private Player player;
	private boolean multiplayer;

	private State state = State.START_MENU;
	private JFrame gameFrame;

	public boolean getMultiplayer() {
		return multiplayer;
	}

	public void startMenu() {
		while (!estadoDeJogo()) {
			if (gameFrame == null) {
				gameFrame = new JFrame("Chess Game");
				gameFrame.getContentPane().setLayout(null);
				gameFrame.getContentPane().setBackground(Color.BLACK);
				gameFrame.setSize(600, 500);
				gameFrame.setResizable(false);
				gameFrame.setLocationRelativeTo(null);
				gameFrame.setVisible(true);
				Menu menu = new Menu();
				menu.render(gameFrame.getGraphics());
				gameFrame.addMouseListener(new MouseInput());
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void start() {
		if (gameFrame instanceof BoardGUI) {
			((BoardGUI) gameFrame).start();
		}
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public void setGameFrame(JFrame gameFrame) {
		this.gameFrame = gameFrame;
	}

	public void fecharServidor() {
		network.close();
	}

	public JFrame getGameFrame() {
		return gameFrame;
	}

	public void connect(INetwork network) {
		this.network = network;
	}

	public boolean estadoDeJogo() {
		return state == State.GAME;
	}

	public int decidir() {
		Random rand = new Random();
		prioridade = rand.nextInt(2) + 1;
		return prioridade;
	}

	public void server(int port) throws IOException {
		Network c = new Network(port);
		connect(c);
	}

	public void cliente(String ip, int port) throws IOException {
		Network c = new Network(ip, port);
		connect(c);
	}

	public void enviarPrioridade() throws IOException {
		network.send(prioridade);
	}

	public void enviarPartida(ChessMatch chessMatch) throws IOException {
		network.send(chessMatch);
	}

	public ChessMatch receberPartida() throws IOException {
		return (ChessMatch) network.read();
	}

	public void setarPrioridade() throws IOException {
		prioridade = (((Integer) network.read()) == 1) ? 2 : 1;
	}

	public int getPrioridade() {
		return prioridade;
	}
	
	public void atualizarPartida(ChessMatch chessMatch) {
		((BoardGUI) gameFrame).setChessMatch(chessMatch);
	}

	public static void go() {
		Jogar jogo = new Jogar();
		jogo.startMenu();

		if (jogo.getMultiplayer()) {
			String[] opcoes = { "Criar", "Entrar" };
			int option = JOptionPane.showOptionDialog(jogo.getGameFrame(),
					"Você quer criar uma sala ou quer entrar em uma", "Servidor", JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE, null, opcoes, opcoes[0]);
			if (option == JOptionPane.YES_OPTION) {
				try {
					jogo.server(6666);
					ChessColor color = (jogo.decidir() == 1) ? ChessColor.WHITE : ChessColor.BLACK;
					jogo.enviarPrioridade();
					Thread.sleep(200);
					ChessMatch chessMatch = new ChessMatch();
					jogo.enviarPartida(chessMatch);

					Player player = new Player();
					player.connect(chessMatch, color, true);
					jogo.setPlayer(player);
					jogo.getGameFrame().setVisible(false);
					jogo.setGameFrame(player.getGUI());
					jogo.start();
					while (!chessMatch.isCheckMate() && !chessMatch.checkTie()) {
						if (chessMatch.getCheck())
							JOptionPane.showMessageDialog(jogo.getGameFrame(), "Você está em xeque, cuidado!", "Xeque", JOptionPane.WARNING_MESSAGE);
						if (chessMatch != null && color == chessMatch.getCurrentPlayer()) {
							while (color == chessMatch.getCurrentPlayer()) {
								Thread.sleep(10);
							}
							jogo.enviarPartida(chessMatch);
						} else {
							chessMatch = jogo.receberPartida();
							jogo.atualizarPartida(chessMatch);
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					jogo.fecharServidor();
				}
			} else {
				String ip = JOptionPane.showInputDialog(jogo.getGameFrame(), "Digite o ip do servidor");
				try {
					jogo.cliente(ip, 6666);
					jogo.setarPrioridade();
					ChessColor color = (jogo.getPrioridade() == 1) ? ChessColor.WHITE : ChessColor.BLACK;
					ChessMatch chessMatch = jogo.receberPartida();

					Player player = new Player();
					player.connect(chessMatch, color, true);
					jogo.getGameFrame().setVisible(false);
					jogo.setGameFrame(player.getGUI());
					jogo.start();
					while (!chessMatch.isCheckMate() && !chessMatch.checkTie()) {
						if (chessMatch.getCheck())
							JOptionPane.showMessageDialog(jogo.getGameFrame(), "Você está em xeque, cuidado!", "Xeque", JOptionPane.WARNING_MESSAGE);
						if (chessMatch != null && color == chessMatch.getCurrentPlayer()) {
							while (color == chessMatch.getCurrentPlayer()) {
								Thread.sleep(10);
							}
							jogo.enviarPartida(chessMatch);
						} else {
							chessMatch = jogo.receberPartida();
							jogo.atualizarPartida(chessMatch);
							if (chessMatch.getCheck())
								JOptionPane.showMessageDialog(jogo.getGameFrame(), "Você está em xeque, cuidado!", "Xeque", JOptionPane.WARNING_MESSAGE);
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}	catch (InterruptedException e) {
					e.printStackTrace();
				}
				finally {
					jogo.fecharServidor();
				}
			}
		} else {
			ChessMatch chessMatch = new ChessMatch();
			String[] opcoes = {"Branco", "Preto"};
			int opcaoCor = JOptionPane.showOptionDialog(jogo.getGameFrame(), "Em qual ponto de vista você quer jogar? ",
					"Cor de treino", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcoes, opcoes[0]);
			ChessColor pov = (opcaoCor == JOptionPane.YES_OPTION) ? ChessColor.WHITE : ChessColor.BLACK;
			Player player = new Player();
			player.connect(chessMatch, pov);
			jogo.getGameFrame().setVisible(false);
			jogo.setGameFrame(player.getGUI());
			jogo.start();
			while (!chessMatch.isCheckMate() && !chessMatch.checkTie())
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			;
			jogo.getGameFrame().setVisible(false);
			System.out.println("Obrigado por jogar!");
		}
	}

	private class MouseInput implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {

		}

		@Override
		public void mousePressed(MouseEvent e) {
			int mx = e.getX();
			int my = e.getY();

			if (mx >= 250 && mx <= 350 && my >= 150 && my <= 200 && !estadoDeJogo()) {
				state = State.GAME;
			}

			else if (mx >= 250 && mx <= 350 && my >= 250 && my <= 300 && !estadoDeJogo()) {
				String[] opcoes = { "Sozinho", "multiplayer" };
				int option = JOptionPane.showOptionDialog(gameFrame,
						"Você quer treinar sozinho ou quer jogar com alguém ?", "Modo de jogo",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcoes, opcoes[0]);
				if (option == JOptionPane.YES_OPTION)
					multiplayer = false;
				else
					multiplayer = true;
			}

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent e) {

		}
	}
}
