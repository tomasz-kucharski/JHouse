package eu.jhouse.android.mqtt;

import android.os.PowerManager;
import com.ibm.mqtt.MqttSimpleCallback;

/**
 * @author tomekk
 * @since 27.02.11, 23:30
 */
public class MQTTCallback implements MqttSimpleCallback{

    private PowerManager powerManager;

    public void setPowerManager(PowerManager powerManager) {
        this.powerManager = powerManager;
    }

    public void connectionLost() throws Exception {
        // we protect against the phone switching off while we're doing this
        //  by requesting a wake lock - we request the minimum possible wake
        //  lock - just enough to keep the CPU running until we've finished
        PowerManager.WakeLock wl = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "MQTT");
        wl.acquire();

        //
        // have we lost our data connection?
        //

        if (isOnline() == false)
        {
            connectionStatus = MQTTConnectionStatus.NOTCONNECTED_WAITINGFORINTERNET;

            // inform the app that we are not connected any more
            broadcastServiceStatus("Connection lost - no network connection");

            //
            // inform the user (for times when the Activity UI isn't running)
            //   that we are no longer able to receive messages
            notifyUser("Connection lost - no network connection",
                       "MQTT", "Connection lost - no network connection");

            //
            // wait until the phone has a network connection again, when we
            //  the network connection receiver will fire, and attempt another
            //  connection to the broker
        }
        else
        {
            //
            // we are still online
            //   the most likely reason for this connectionLost is that we've
            //   switched from wifi to cell, or vice versa
            //   so we try to reconnect immediately
            //

            connectionStatus = MQTTConnectionStatus.NOTCONNECTED_UNKNOWNREASON;

            // inform the app that we are not connected any more, and are
            //   attempting to reconnect
            broadcastServiceStatus("Connection lost - reconnecting...");

            // try to reconnect
            if (connectToBroker()) {
                subscribeToTopic(topicName);
            }
        }

        // we're finished - if the phone is switched off, it's okay for the CPU
        //  to sleep now
        wl.release();
    }

    @Override
    public void publishArrived(String s, byte[] bytes, int i, boolean b) throws Exception {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
