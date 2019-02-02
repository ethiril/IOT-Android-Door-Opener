package com.example.ethiril.a15068126_mobile_dev_1cwk50_app.mqtt.subscriber;

import android.util.Log;

import com.example.ethiril.a15068126_mobile_dev_1cwk50_app.MainActivity;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class Subscriber {

    private static final String BROKER_URL = "tcp://broker.hivemq.com:1883";
    private static final String userID = "15068126";
    private static final  String clientId = userID + "-subAndroid";
    private static MqttClient mqttClient;

    public Subscriber() {

        try {
            MemoryPersistence persistence = new MemoryPersistence();
            mqttClient = new MqttClient(BROKER_URL, clientId, persistence);
        } catch (MqttException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static void start(MainActivity activity, String[] rooms) {
        try {
            mqttClient.setCallback(new SubscriberCallback(activity));
            mqttClient.connect();
            //Subscribe to all subtopics of home
            String topics = "";
            Log.d("DEBUG:", "room.length: " + rooms.length);
            for (int i = 0; i < rooms.length; i++) {
                mqttClient.subscribe("/" + rooms[i], 1);
                topics = topics + "/" + rooms[i] + ", ";
            }
            Log.d("LOG DEBUG", "Subscriber is now listening to these topics: " + topics);
        } catch (MqttException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

}
