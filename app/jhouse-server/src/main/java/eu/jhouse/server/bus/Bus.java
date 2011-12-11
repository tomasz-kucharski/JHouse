package eu.jhouse.server.bus;

/**
 * @author tomekk
 * @since 2010-10-04, 00:44:07
 */
public interface Bus {
    void dispatch(Object event);
}
