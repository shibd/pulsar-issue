package com.baozi.pulsar.issue.i12091;

import java.util.Map;
import org.apache.pulsar.client.api.Schema;
import org.apache.pulsar.client.impl.schema.JSONSchema;
import org.apache.pulsar.common.schema.KeyValue;
import org.apache.pulsar.common.schema.SchemaInfo;

/**
 * @author baozi
 */
public class Test {

    public static void main(String[] args) {

//        Schema<People> avro = Schema.JSON(People.class);
//        System.out.println(avro.getSchemaInfo());
//        People people = new People();
//        people.name = "Jerry";
//        people.sex = "male";
//        Address address = new Address();
//        address.country = "China";
//        address.city = "Shanghai";
//        people.address = address;
//
//        byte[] encode = avro.encode(people);
//
//        System.out.println(new String(encode));

        Schema<KeyValue<KeyClass, ValueClass>> keyValueSchema = Schema.KeyValue(KeyClass.class, ValueClass.class);

        KeyClass keyClass = new KeyClass();
        ValueClass valueClass = new ValueClass();
        keyClass.key = "testk";
        valueClass.value = "testv";

        KeyValue<KeyClass, ValueClass> kv = new KeyValue<>(keyClass, valueClass);

        byte[] encode = keyValueSchema.encode(kv);

        SchemaInfo schemaInfo = keyValueSchema.getSchemaInfo();
        System.out.println(schemaInfo.getSchemaDefinition());

    }

    static class KeyClass {
        String key;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }
    }

    static class ValueClass {
        String value;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    static class People {
        String name;
        String sex;
        Address address;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public Address getAddress() {
            return address;
        }

        public void setAddress(Address address) {
            this.address = address;
        }
    }

    static class Address {
        String country;
        String city;

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }
    }

}
