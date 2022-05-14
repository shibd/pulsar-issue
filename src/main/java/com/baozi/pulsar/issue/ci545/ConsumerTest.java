package com.baozi.pulsar.issue.ci545;

import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.apache.pulsar.client.api.Consumer;
import org.apache.pulsar.client.api.Message;
import org.apache.pulsar.client.api.MessageListener;
import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.client.api.Schema;
import org.apache.pulsar.client.api.SubscriptionInitialPosition;
import org.apache.pulsar.client.api.SubscriptionType;

/**
 * @author baozi
 */
public class ConsumerTest {

    public static void main(String[] args) throws PulsarClientException, InterruptedException {
        String serviceUrl = "pulsar://localhost:6650";
        String topic = "test-block";
        PulsarClient client = PulsarClient.builder().serviceUrl(serviceUrl).build();

        Consumer<String> subscribe = client.newConsumer(Schema.STRING)
                .topic(topic)
                .subscriptionName("block-sub")
                .subscriptionType(SubscriptionType.Shared)
                .receiverQueueSize(1000)
                .ackTimeout(10, TimeUnit.SECONDS)
                .subscriptionInitialPosition(SubscriptionInitialPosition.Latest)
                .messageListener((consumer, msg) -> {
                    System.out.println("start block" + msg);
                    try {
                        Thread.sleep(1000000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                })
                .subscribe();
        while (true) {
        }
    }
}
