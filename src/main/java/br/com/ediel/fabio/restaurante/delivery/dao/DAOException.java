package br.com.ediel.fabio.restaurante.delivery.dao;

public class DAOException extends Exception {

	/**
	 * version 1.0 
	 */
	private static final long serialVersionUID = 5153843057866890613L;

	public DAOException() {
	}

	public DAOException(String message) {
		super(message);
	}

	public DAOException(Throwable cause) {
		super(cause);
	}

	public DAOException(String message, Throwable cause) {
		super(message, cause);
	}

	public DAOException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
