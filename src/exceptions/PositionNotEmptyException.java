package exceptions;

public class PositionNotEmptyException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public PositionNotEmptyException() {
		super();
	}
	
	public PositionNotEmptyException(String msg) {
		super(msg);
	}

}
