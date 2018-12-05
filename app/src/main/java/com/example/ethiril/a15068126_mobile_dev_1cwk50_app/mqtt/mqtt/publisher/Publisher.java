package com.example.ethiril.a15068126_mobile_dev_1cwk50_app.mqtt.mqtt.publisher;

import android.util.Log;

import com.example.ethiril.a15068126_mobile_dev_1cwk50_app.MainActivity;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.eclipse.paho.client.mqttv3.persist.MqttDefaultFilePersistence;

public class Publisher {

    public static final String BROKER_URL = "tcp://broker.hivemq.com:1883";
    // localhost at IPv4 For testing with mobile device
    public static final String sensorServerURL = "http://192.168.1.9:8080/15068126_Mobile_Dev_1CWK50_Server/SensorServerDB";
    public static final String userID = "15068126" + "-android";
    private static MqttClient client;
    public Publisher() {

        try {
            MemoryPersistence persistence = new MemoryPersistence();
            client = new MqttClient(BROKER_URL, userID, persistence);
            // create mqtt session
            MqttConnectOptions options = new MqttConnectOptions();
            options.setCleanSession(false);
            options.setWill(client.getTopic(userID + "/LWT"), "Defaulted".getBytes(), 0, false);
            client.connect(options);
        } catch (MqttException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static void publishJson(String json, String topic) {
        final MqttTopic jsonTopic = client.getTopic(topic);
        try {
            jsonTopic.publish(new MqttMessage(json.getBytes()));
        } catch (MqttException e1) {
            Log.d("LOG DEBUG", e1.getMessage());
        }
        Log.d("LOG DEBUG", "Publishing Data. Topic: " + jsonTopic.getName() + " Message: " + json);
    }

}
