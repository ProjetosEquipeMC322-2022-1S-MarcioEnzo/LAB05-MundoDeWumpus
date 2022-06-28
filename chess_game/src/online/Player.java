package online;

import control.ChessMatch;
import model.chess_pieces.ChessColor;
import view.BoardGUI;

public class Player  {
	private BoardGUI gui;
	private ChessColor color;
	private ChessMatch chessMatch;
	
	Player () {
		
	}
	
	public ChessColor getColor() {
		return color;
	}
	
	public ChessMatch getChessMatch() {
		return chessMatch;
	}
	
	public void connect(ChessMatch chessMatch, ChessColor color) {
		this.chessMatch = chessMatch;
		this.color = color;
		gui = new BoardGUI(chessMatch, color);
	}
	
	public void connect(ChessMatch chessMatch, ChessColor color, boolean multiplayer) {
		this.chessMatch = chessMatch;
		this.color = color;
		gui = new BoardGUI(chessMatch, color, multiplayer);
	}
	
	public BoardGUI getGUI() {
		return gui;
	}
	
}
