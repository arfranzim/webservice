package br.com.franzim.webservice.exceptions;

public class DAOException extends RuntimeException {

	private static final long serialVersionUID = 6609027859897299023L;
	
	private int code;
	
	public DAOException(String message, int code) {
		super(message);
		this.code = code;
	}

	public int getCode() {
		return code;
	}
	
}
