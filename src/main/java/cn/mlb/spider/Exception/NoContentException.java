package cn.mlb.spider.Exception;

public class NoContentException extends Exception {

	/**
	 * 
	 */
	public NoContentException() {
		super();

	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public NoContentException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);

	}

	/**
	 * @param message
	 * @param cause
	 */
	public NoContentException(String message, Throwable cause) {
		super(message, cause);

	}

	/**
	 * @param message
	 */
	public NoContentException(String message) {
		super(message);

	}

	/**
	 * @param cause
	 */
	public NoContentException(Throwable cause) {
		super(cause);

	}

}
