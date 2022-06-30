package exceptions;

public class BoardException extends RuntimeException {
	private static final long serialVersionUID = -1263300524620099378L;
	
	public BoardException(String msg) {
		super(msg);
	}
}
