package fr.demo.exception;

/**
 * Custom Exception.
 * 
 * @author wahid.gazzah
 * @since 1.0.0
 */
public class CustomException extends Exception {
	
	private static final long serialVersionUID = -1687246851733435248L;
	
	public CustomException(String message) {
		super(message);
	}

}
