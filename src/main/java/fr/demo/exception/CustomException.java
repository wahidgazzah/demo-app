package fr.demo.exception;

public class CustomException extends Exception {
	
	private static final long serialVersionUID = -1687246851733435248L;
	
	public CustomException(String message) {
		super(message);
	}

}
