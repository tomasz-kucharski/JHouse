package eu.jhouse.server;

/**
 * @author tomekk
 * @since 2010-10-10, 23:03:36
 */
public class NetworkException extends Exception {

	public NetworkException(String message, Throwable e) {
		super(message, e);
	}
}
