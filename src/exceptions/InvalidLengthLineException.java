package exceptions;

public class InvalidLengthLineException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InvalidLengthLineException(String message) {
		super(message);
	}

}
