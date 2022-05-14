package com.baozi.pulsar.issue.ci539;

import java.math.BigDecimal;
import org.apache.avro.Conversion;
import org.apache.avro.Conversions;
import org.apache.avro.LogicalTypes;
import org.apache.avro.generic.GenericFixed;
import org.apache.pulsar.client.api.Consumer;
import org.apache.pulsar.client.api.Message;
import org.apache.pulsar.client.api.MessageListener;
import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.client.api.Schema;
import org.apache.pulsar.client.impl.schema.JSONSchema;

/**
 * @author baozi
 */
public class ProducerAvroDecimal {

    public static void main(String[] args) throws PulsarClientException {

        String serviceUrl = "pulsara+tls://localhost:6650";
        String topic = "public/default/test_avro";
        PulsarClient client = PulsarClient.builder().serviceUrl(serviceUrl).build();

        for (int i = 0; i < 3; i++) {
            Venues venues = new Venues();
            venues.setPSETName("Test" + i);
            BigDecimal bigDecimal = new BigDecimal("1234567891234567891234567891.23");
            venues.setAddLiqFeeOrRebate(bigDecimal);
            Producer<Venues> producer = client.newProducer(Schema.AVRO(Venues.class)).topic(topic).create();
            producer.newMessage().value(venues).send();
        }

//        for (int i = 0; i < 3; i++) {
//            TestMessage testMsg = new TestMessage();
//            testMsg.setName("baozi" + i);
//            testMsg.setBigDecimal(new BigDecimal("1234567891123456789234567891234567891.23"));
////            testMsg.setBigDecimal(new BigDecimal("12.23"));
//            Producer<TestMessage> producer = client.newProducer(Schema.AVRO(TestMessage.class)).topic(topic).create();
//            producer.newMessage().value(testMsg).send();
//        }

//        Consumer<TestMessage> test =
//                client.newConsumer(Schema.AVRO(TestMessage.class)).topic(topic).subscriptionName("test").messageListener(
//                        new MessageListener<TestMessage>() {
//                            @Override
//                            public void received(Consumer<TestMessage> consumer, Message<TestMessage> msg) {
//                                System.out.println(msg.getValue().getBigDecimal());
//                            }
//                        }).subscribe();

        System.out.println("send ok");
        System.exit(0);
    }

    static class TestMsg {
        private String name;
        private String value;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    static class TestMessage {
        private String name;
        @org.apache.avro.reflect.AvroSchema("{ \"type\": \"bytes\", \"logicalType\": \"decimal\", \"precision\": 30, "
                + "\"scale\": 2 }")
        private BigDecimal bigDecimal;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public BigDecimal getBigDecimal() {
            return bigDecimal;
        }

        public void setBigDecimal(BigDecimal bigDecimal) {
            this.bigDecimal = bigDecimal;
        }
    }
}
