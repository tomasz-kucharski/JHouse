package eu.jhouse.server.bus;

/**
 * @author tomekk
 * @since 2009-12-19 23:59:33
 */
public interface Command {
	public void execute(Object event);
}
