package com.example.ethiril.a15068126_mobile_dev_1cwk50_app.mqtt.subscriber;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.ethiril.a15068126_mobile_dev_1cwk50_app.MainActivity;
import com.example.ethiril.a15068126_mobile_dev_1cwk50_app.R;
import com.example.ethiril.a15068126_mobile_dev_1cwk50_app.Sensor;
import com.google.gson.Gson;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class SubscriberCallback extends MainActivity implements MqttCallback {
    static Gson gson = new Gson();
    public final String DOOR_OPENED = "door_opened";
    public NotificationChannel doorChannel;
    private MainActivity activity;


    public SubscriberCallback(MainActivity activity) {
        this.activity = activity;
    }

    @Override
    public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
        Log.d("LOG DEBUG", "Message Arrived. Topic: " + topic + mqttMessage.toString());
        final String messageStr = mqttMessage.toString();

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Sensor sensor = gson.fromJson(messageStr, Sensor.class);
                Log.d("LOG DEBUG", "Notification sent to UI with tag: " + sensor.getTagID());
                addNotification(sensor.getTagID());
            }
        });

        if ((topic + "/LWT").equals(topic)) {
            Log.d("LOG DEBUG", "No Longer Active");
        }
    }


    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        // no-op
    }

    @Override
    public void connectionLost(Throwable throwable) {
        // no-op
    }

    public void addNotification(String tagid) {
        createNotificationChannel();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(activity.getApplicationContext(), DOOR_OPENED)
                .setSmallIcon(R.drawable.ic_lock_open_white_24dp)
                .setContentTitle("Door notification")
                .setContentText("Tag: " + tagid + " has attempted to open the door!");

        NotificationManager manager = (NotificationManager) activity.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());

    }

    public void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            doorChannel = new NotificationChannel(DOOR_OPENED, "Door Opened", NotificationManager.IMPORTANCE_HIGH);
            doorChannel.setDescription("Door notification");
            NotificationManager manager = activity.getSystemService(NotificationManager.class);
            manager.createNotificationChannel(doorChannel);
        }
    }

}
