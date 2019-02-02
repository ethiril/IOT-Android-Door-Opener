package com.example.ethiril.a15068126_mobile_dev_1cwk50_app.mqtt.mqtt.publisher;

import android.util.Log;



import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;


public class Publisher {

    private static final String BROKER_URL = "tcp://broker.hivemq.com:1883";
    private static final String userID = "15068126" + "-android";
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
