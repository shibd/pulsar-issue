package com.baozi.pulsar.issue.i12091;

import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import org.apache.avro.Protocol;
import org.apache.pulsar.client.api.Consumer;
import org.apache.pulsar.client.api.Message;
import org.apache.pulsar.client.api.MessageListener;
import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.client.api.Schema;
import org.apache.pulsar.client.api.SubscriptionInitialPosition;
import org.apache.pulsar.client.api.SubscriptionType;
import org.apache.pulsar.client.api.TableView;
import org.apache.pulsar.client.api.schema.KeyValueSchema;
import org.apache.pulsar.client.impl.schema.JSONSchema;
import org.apache.pulsar.client.impl.schema.ProtobufNativeSchema;
import org.apache.pulsar.client.impl.schema.ProtobufSchema;

/**
 * https://github.com/apache/pulsar/issues/12091
 */
public class ProducerTest {

    public static void main(String[] args) throws PulsarClientException, InterruptedException {

        String serviceUrl = "pulsar://localhost:6650";
        String topic = "sample/ns1/my-topic4-partition-4";
        PulsarClient client = PulsarClient.builder().serviceUrl(serviceUrl).build();

        Producer<byte[]> producer = client.newProducer().topic(topic).create();


        TableView<String> tv = client.newTableViewBuilder(Schema.STRING)
                .topic("my-tableview")
                .create();


        producer.newMessage().deliverAfter(15000, TimeUnit.MILLISECONDS)
                .value("hello pulsar".getBytes()).send();
        Thread.sleep(20000);

        Producer<Map> mapProducer = client.newProducer(JSONSchema.of(Map.class)).topic(topic).create();


        producer.newMessage()
                .value("hello pulsar 1".getBytes()).send();


        Consumer<byte[]> subscribe = client.newConsumer()
                .topic(topic)
                .subscriptionName("my-subscribe")
                .ackTimeout(10, TimeUnit.SECONDS)
                .subscriptionType(SubscriptionType.Exclusive)
                .subscriptionInitialPosition(SubscriptionInitialPosition.Earliest)
                .messageListener(new MessageListener<byte[]>() {
                    @Override
                    public void received(Consumer<byte[]> consumer, Message<byte[]> msg) {
                        System.out.println(msg);
                    }
                })
                .subscribe();

        while (true) {
            Message<byte[]> receive = subscribe.receive();
            System.out.println(new String(receive.getValue()));
            subscribe.acknowledge(receive);
        }
    }
}
