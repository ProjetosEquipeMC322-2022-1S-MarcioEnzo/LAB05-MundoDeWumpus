package control;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import exceptions.GameException;
import model.Board;
import model.Piece;
import model.Position;
import model.chess_pieces.Bishop;
import model.chess_pieces.ChessColor;
import model.chess_pieces.ChessPiece;
import model.chess_pieces.ChessPosition;
import model.chess_pieces.King;
import model.chess_pieces.Knight;
import model.chess_pieces.Pawn;
import model.chess_pieces.Queen;
import model.chess_pieces.Rook;

public class ChessMatch implements Serializable {
	private static final long serialVersionUID = -5775185194128291429L;
	
	private int turn;
	private ChessColor currentPlayer;
	private Board board;

	private boolean check;
	private boolean checkMate;
	private ChessPiece enPassantVulnerable;
	private ChessPiece promoted;

	private List<ChessPiece> piecesOnTheBoard = new ArrayList<>();
	private List<ChessPiece> capturedPieces = new ArrayList<>();

	public ChessMatch() {
		board = new Board();
		turn = 1;
		currentPlayer = ChessColor.WHITE;
		initialSetup();
	}

	public int getTurn() {
		return turn;
	}

	public ChessColor getCurrentPlayer() {
		return currentPlayer;
	}

	public Board getBoard() {
		return board;
	}

	public boolean getCheck() {
		return check;
	}

	public boolean isCheckMate() {
		return checkMate;
	}

	public ChessPiece getEnPassantVulnerable() {
		return enPassantVulnerable;
	}

	public ChessPiece getPromoted() {
		return promoted;
	}

	public List<ChessPiece> getCapturedPieces() {
		return capturedPieces;
	}

	public ChessPiece[][] getPieces() {
		ChessPiece[][] mat = new ChessPiece[8][8];
		for (int i = 0; i < 8; i++)
			for (int j = 0; j < 8; j++)
				mat[i][j] = (ChessPiece) board.piece(i, j);
		return mat;
	}

	public boolean[][] possibleMoves(Position sourcePosition) throws GameException {
		validateSourcePosition(sourcePosition);
		return board.piece(sourcePosition).possibleMoves();
	}

	public boolean[][] possibleMoves(ChessPosition sourcePosition) throws GameException {
		return possibleMoves(sourcePosition.toPosition());
	}

	public void peformChessMove(Position source, Position target) throws GameException {
		validateSourcePosition(source);
		validateTargetPosition(source, target);
		Piece capturedPiece = makeMove(source, target);

		if (testCheck(currentPlayer)) {
			undoMove(source, target, capturedPiece);
			throw new GameException("You can't put yourself on check");
		}

		ChessPiece movedPiece = (ChessPiece) board.piece(target);

		// # specialmove promotion
		promoted = null;
		if (movedPiece instanceof Pawn)
			if ((currentPlayer == ChessColor.WHITE && target.getRow() == 0)
					|| (currentPlayer == ChessColor.BLACK && target.getRow() == 7)) {
				promoted = movedPiece;
				promoted = replacePromotedPiece("Q");
			}

		check = testCheck(opponent(currentPlayer));

		if (testCheckMate(opponent(currentPlayer)))
			checkMate = true;
		else
			nextTurn();

		// #specialmove en passant
		enPassantVulnerable = (movedPiece instanceof Pawn && Math.abs(target.getRow() - source.getRow()) == 2)
				? movedPiece
				: null;

	}

	public ChessPiece replacePromotedPiece(String type) {
		if (promoted == null)
			throw new IllegalStateException("There is no piece to be promoted");
		if (!type.equals("B") && !type.equals("N") && !type.equals("R") && !type.equals("Q"))
			return promoted;

		Position pos = promoted.getPosition();
		Piece p = board.removePiece(pos);
		piecesOnTheBoard.remove(p);

		ChessPiece newPiece = newPiece(pos, type, promoted.getColor());
		board.placePiece(newPiece, pos);
		piecesOnTheBoard.add(newPiece);

		return newPiece;
	}

	private ChessPiece newPiece(Position pos, String type, ChessColor color) {
		if (type.equals("B"))
			return new Bishop(pos, board, color);
		if (type.equals("N"))
			return new Knight(pos, board, color);
		if (type.equals("Q"))
			return new Queen(pos, board, color);
		return new Rook(pos, board, color);
	}

	private Piece makeMove(Position source, Position target) {
		ChessPiece p = (ChessPiece) board.removePiece(source);
		p.increaseMoveCount();
		ChessPiece capturedPiece = (ChessPiece) board.removePiece(target);
		board.placePiece(p, target);

		if (capturedPiece != null) {
			piecesOnTheBoard.remove(capturedPiece);
			capturedPieces.add(capturedPiece);
		}

		// #specialmove castling kingside
		if (p instanceof King && target.getColumn() == source.getColumn() + 2) {
			Position sourceT = new Position(source.getRow(), source.getColumn() + 3);
			Position targetT = new Position(source.getRow(), source.getColumn() + 1);
			ChessPiece rook = (ChessPiece) board.removePiece(sourceT);
			rook.increaseMoveCount();
			board.placePiece(rook, targetT);
		}

		// #specialmove castling queenside
		if (p instanceof King && target.getColumn() == source.getColumn() - 2) {
			Position sourceT = new Position(source.getRow(), source.getColumn() - 4);
			Position targetT = new Position(source.getRow(), source.getColumn() - 1);
			ChessPiece rook = (ChessPiece) board.removePiece(sourceT);
			rook.increaseMoveCount();
			board.placePiece(rook, targetT);
		}

		// #specialmove en passant
		if (p instanceof Pawn) {
			if (source.getColumn() != target.getColumn() && capturedPiece == null) {
				Position pawnPosition;
				if (p.getColor() == ChessColor.WHITE)
					pawnPosition = new Position(target.getRow() + 1, target.getColumn());
				else
					pawnPosition = new Position(target.getRow() - 1, target.getColumn());

				capturedPiece = (ChessPiece) board.removePiece(pawnPosition);
				capturedPieces.add(capturedPiece);
				piecesOnTheBoard.remove(capturedPiece);
			}
		}

		return capturedPiece;
	}

	private void undoMove(Position source, Position target, Piece capturedPiece) {
		ChessPiece p = (ChessPiece) board.removePiece(target);
		p.decreaseMoveCount();
		board.placePiece(p, source);

		if (capturedPiece != null) {
			board.placePiece(capturedPiece, target);
			capturedPieces.remove(capturedPiece);
			piecesOnTheBoard.add((ChessPiece) capturedPiece);
		}

		// #specialmove castling kingside
		if (p instanceof King && target.getColumn() == source.getColumn() + 2) {
			Position sourceT = new Position(source.getRow(), source.getColumn() + 3);
			Position targetT = new Position(source.getRow(), source.getColumn() + 1);
			ChessPiece rook = (ChessPiece) board.removePiece(targetT);
			rook.decreaseMoveCount();
			board.placePiece(rook, sourceT);
		}

		// #specialmove castling queenside
		if (p instanceof King && target.getColumn() == source.getColumn() - 2) {
			Position sourceT = new Position(source.getRow(), source.getColumn() - 4);
			Position targetT = new Position(source.getRow(), source.getColumn() - 1);
			ChessPiece rook = (ChessPiece) board.removePiece(targetT);
			rook.decreaseMoveCount();
			board.placePiece(rook, sourceT);
		}

		// #specialmove en passant
		if (p instanceof Pawn) {
			if (source.getColumn() != target.getColumn() && capturedPiece == enPassantVulnerable) {
				ChessPiece pawn = (ChessPiece) board.removePiece(target);
				Position pawnPosition;
				if (p.getColor() == ChessColor.WHITE)
					pawnPosition = new Position(3, target.getColumn());
				else
					pawnPosition = new Position(4, target.getColumn());
				board.placePiece(pawn, pawnPosition);
			}
		}
	}

	private void validateSourcePosition(Position sourcePosition) throws GameException {
		if (!board.thereIsAPiece(sourcePosition))
			throw new GameException("Não há peça nessa posição");
		if ((((ChessPiece) board.piece(sourcePosition)).getColor()) != currentPlayer)
			throw new GameException("Essa peça não é sua");
		if (!board.piece(sourcePosition).isThereAnyPossibleMove())
			throw new GameException("Não há movimentos possíveis para essa peça");
	}
	
	private void validateTargetPosition(Position source, Position target) {
		if (!board.piece(source).possibleMove(target))
			throw new GameException("Posição alvo inválida");
	}
	
	private ChessColor opponent(ChessColor color) {
		return (color == ChessColor.WHITE)? ChessColor.BLACK : ChessColor.WHITE;
	}
	
	private void nextTurn() {
		turn++;
		currentPlayer = opponent(currentPlayer);
	}
	
	private King king(ChessColor color) {
		List<ChessPiece> piecesOfColor = piecesOnTheBoard.stream().filter(x -> x.getColor() == color).collect(Collectors.toList());
		for (ChessPiece p : piecesOfColor) {
			if (p instanceof King)
				return (King) p;
		}
		throw new GameException("Não há o rei da cor " + color);
	}
	
	private boolean testCheck(ChessColor color) {
		Position kingPosition = king(currentPlayer).getPosition();
		List<ChessPiece> opponentPieces = piecesOnTheBoard.stream().filter(x -> x.getColor() != color).collect(Collectors.toList());
		for (ChessPiece p : opponentPieces) {
			if (p.possibleMove(kingPosition))
				return true;
		}
		return false;
	}
	
	public boolean checkTie() {
		if (piecesOnTheBoard.size() == 2)
			return true;
		else {
			List<ChessPiece> piecesOfColor = piecesOnTheBoard.stream().filter(x -> x.getColor() == currentPlayer).collect(Collectors.toList());
			for (ChessPiece p : piecesOfColor) {
				if (p.isThereAnyPossibleMove())
					return false;
			}
			return true;
		}
	}
	
	private boolean testCheckMate(ChessColor color) {
		boolean ans = true;
		if (!testCheck(color))
			ans = false;
		else {
			List<ChessPiece> piecesOfColor = piecesOnTheBoard.stream().filter(x -> x.getColor() == color).collect(Collectors.toList());
			for (ChessPiece p : piecesOfColor){
				for (int i = 0; i < 8; i++) {
					for (int j = 0; j < 8; j++) {
						if (p.possibleMove(i, j)) {
							Piece capturedPiece = makeMove(p.getPosition(), new Position(i, j));
							if (!testCheck(color))
								ans = false;
							undoMove(p.getPosition(), new Position(i, j), capturedPiece);
						}
					}
				}
			}
		}
		return ans;
	}
	private void placeNewPiece(int row, int column, ChessPiece piece) {
		board.placePiece(piece, new Position(row, column));
		piecesOnTheBoard.add(piece);
	}

	private void initialSetup() {
		placeNewPiece(7, 4, new King(new Position(7, 4), board, ChessColor.WHITE, this));
		placeNewPiece(7, 3, new Queen(new Position(7, 3), board, ChessColor.WHITE));
		placeNewPiece(7, 0, new Rook(new Position(7, 0),board, ChessColor.WHITE));
		placeNewPiece(7, 7, new Rook(new Position (7, 7),board, ChessColor.WHITE));
		placeNewPiece(7, 2, new Bishop(new Position(7, 2), board, ChessColor.WHITE));
		placeNewPiece(7, 5, new Bishop(new Position(7, 5), board, ChessColor.WHITE));
		placeNewPiece(7, 1, new Knight(new Position(7, 1), board, ChessColor.WHITE));
		placeNewPiece(7, 6, new Knight(new Position(7, 6), board, ChessColor.WHITE));

		placeNewPiece(0, 4, new King(new Position(0, 4), board, ChessColor.BLACK, this));
		placeNewPiece(0, 3, new Queen(new Position(0, 3), board, ChessColor.BLACK));
		placeNewPiece(0, 0, new Rook(new Position(0, 0), board, ChessColor.BLACK));
		placeNewPiece(0, 7, new Rook(new Position(0, 7), board, ChessColor.BLACK));
		placeNewPiece(0, 2, new Bishop(new Position (0, 2), board, ChessColor.BLACK));
		placeNewPiece(0, 5, new Bishop(new Position(0, 5), board, ChessColor.BLACK));
		placeNewPiece(0, 1, new Knight(new Position(0, 1), board, ChessColor.BLACK));
		placeNewPiece(0, 6, new Knight(new Position(0, 6), board, ChessColor.BLACK));

		for (int i = 0; i < board.getColumns(); i++) {
			placeNewPiece(1, i, new Pawn(new Position(1, i), board, ChessColor.BLACK, this));
			placeNewPiece(6, i, new Pawn(new Position(6, i), board, ChessColor.WHITE, this));
		}

	}

}
