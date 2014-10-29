package br.com.ediel.fabio.restaurante.delivery.manager;

public class ManagerException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3131663087962144827L;

	public ManagerException() {
		super();
	}

	public ManagerException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ManagerException(String message, Throwable cause) {
		super(message, cause);
	}

	public ManagerException(String message) {
		super(message);
	}

	public ManagerException(Throwable cause) {
		super(cause);
	}

}
