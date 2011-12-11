package eu.jhouse.server.device;

import eu.jhouse.server.NetworkException;

/**
 * @author tomekk
 * @since 2010-10-10, 22:43:05
 */
public interface CommunicationWriter {

    /**
     * Add device to awaiting queue
     * @param device
     */
    void push(Device device);

    /**
     * Flush queue
     */
    void flush() throws NetworkException;

    /**
     * Clear queue, should invoked after initialization
     */
    void clear();
}
