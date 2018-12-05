package com.example.ethiril.a15068126_mobile_dev_1cwk50_app.mqtt.subscriber;

import android.app.Activity;
import android.util.Log;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class Subscriber {

    public static final String BROKER_URL = "tcp://broker.hivemq.com:1883";
    // localhost at IPv4 For testing with mobile device
    public static final String sensorServerURL = "http://192.168.1.9:8080/15068126_Mobile_Dev_1CWK50_Server/SensorServerDB";
    public static final String userID = "15068126";
    public static final  String clientId = userID + "-subAndroid";
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

    public static void start(Activity activity, String[] rooms) {
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
