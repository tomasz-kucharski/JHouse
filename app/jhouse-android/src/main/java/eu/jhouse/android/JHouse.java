package eu.jhouse.android;

import android.app.Activity;
import android.content.*;
import android.os.Bundle;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import eu.jhouse.android.mqtt.MQTTNotifier;
import eu.jhouse.android.mqtt.MQTTService;
import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;

import java.net.MalformedURLException;

/**
 * @author tomekk
 * @since 2010-10-17, 00:04:01
 */
public class JHouse extends RoboActivity {

    private MQTTService mqttservice;
    private BroadcastReceiver statusUpdateIntentReceiver;
    private BroadcastReceiver  messageIntentReceiver;

    @InjectView(R.id.button_device)
    private Button button;

    @InjectView(R.id.device_list)
    private TextView textView;

    private final int EDIT_ID = 1;
    private final int CLOSE_ID = 2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.main);

        final ServiceConnection connection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                Toast.makeText(JHouse.this, "Service connected", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                Toast.makeText(JHouse.this, "Service disconnected", Toast.LENGTH_SHORT).show();
            }
        };

        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(JHouse.this, "Trying to connect", Toast.LENGTH_SHORT).show();
                final Intent intent = new Intent(JHouse.this, MQTTService.class);
                bindService(intent,connection,Context.BIND_AUTO_CREATE);
            }
        });

        statusUpdateIntentReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Bundle notificationData = intent.getExtras();
                String newStatus = notificationData.getString(MQTTService.MQTT_STATUS_MSG);
                Toast.makeText(JHouse.this, "New status:"+newStatus, Toast.LENGTH_SHORT).show();

            }
        };
        registerReceiver(statusUpdateIntentReceiver, new IntentFilter(MQTTService.MQTT_STATUS_INTENT));

        messageIntentReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Bundle notificationData = intent.getExtras();
                String newTopic = notificationData.getString(MQTTService.MQTT_MSG_RECEIVED_TOPIC);
                String newData  = notificationData.getString(MQTTService.MQTT_MSG_RECEIVED_MSG);
                textView.append("TOPIC:"+newTopic+"\nData:"+newData+"\n");
            }
        };
        registerReceiver(messageIntentReceiver, new IntentFilter(MQTTService.MQTT_MSG_RECEIVED_INTENT));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(statusUpdateIntentReceiver);
        unregisterReceiver(messageIntentReceiver);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(Menu.NONE, EDIT_ID, Menu.NONE, "Edit Prefs")
                .setIcon(android.R.drawable.ic_menu_preferences)
                .setAlphabeticShortcut('p');
        menu.add(Menu.NONE, CLOSE_ID, Menu.NONE, "Close")
                .setIcon(android.R.drawable.ic_menu_agenda)
                .setAlphabeticShortcut('c');
        return(super.onCreateOptionsMenu(menu));
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case EDIT_ID:
                startActivity(new Intent(this, Preferences.class));
                return true;
            case CLOSE_ID:
                finish();
                return(true);
        }
        return(super.onOptionsItemSelected(item));
    }
}
