package eu.jhouse.server.device;

import eu.jhouse.server.NetworkException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

/**
 * @author tomekk
 * @since 2010-10-01, 23:44:30
 */
public class OutputSwitchDevice extends SwitchDevice<Actor> {

    private static final Logger log = LoggerFactory.getLogger(OutputSwitchDevice.class);

    private static final String PIO_ALL = "PIO.ALL";

    private static final int NUMBER_OF_ACTORS = 8;

    //todo  should be generalized to all kind of actor devices, don't know how
    private CommunicationWriter connectionWriter;

    public void setConnectionWriter(CommunicationWriter connectionWriter) {
        this.connectionWriter = connectionWriter;
    }

    public OutputSwitchDevice() {
        super(NUMBER_OF_ACTORS);
    }

    @Override
    public void initOnStartup() throws NetworkException {
        read();
    }

    @Override
    public void read() throws NetworkException {
        String stateString =  networkConnector.read(PIO_ALL);
        boolean[] values = convertToBooleanArray(stateString);
        for (int i=0; i<values.length; i++) {
            units.get(i).setEnabled(values[i]);
        }
        sendInitializationNotificationEventToBus();
    }

    private void sendInitializationNotificationEventToBus() {
        Set<Actor> actorSet = new HashSet<Actor>(units);
        getBus().dispatch(new ActorsChangedEvent(actorSet));
    }

    public void sendSynchronizeRequest() {
        connectionWriter.push(this);
    }

    @Override
    public void write() throws NetworkException {
        networkConnector.write(PIO_ALL, convertUnitsValueToString());
    }

    public String convertUnitsValueToString() {
        StringBuffer buf = new StringBuffer();
        for (Actor actor : units) {
            buf.append(actor.isEnabled() ? "1," : "0,");
        }
        buf.deleteCharAt(buf.length()-1);
        return buf.toString();
    }

}
