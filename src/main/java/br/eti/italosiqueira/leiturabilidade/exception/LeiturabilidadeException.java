package br.eti.italosiqueira.leiturabilidade.exception;

public class LeiturabilidadeException extends Exception {

	/**
	 * Classe básica para as exceções do pacote.
	 */
	private static final long serialVersionUID = 1L;

	public LeiturabilidadeException() {
		super();
	}

	public LeiturabilidadeException(String message) {
		super(message);
	}

	public LeiturabilidadeException(Throwable cause) {
		super(cause);
	}

	public LeiturabilidadeException(String message, Throwable cause) {
		super(message, cause);
	}

	public LeiturabilidadeException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
