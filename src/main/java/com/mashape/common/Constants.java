package com.mashape.common;


import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonValue;

/**
 * Created by yxzhao on 10/17/14.
 */
public class Constants {

    public enum Fileds {
        TITLE("TITLE"),
        CONTENT("CONTENT"),
        DONE("DONE"),
        ID("ID");

        private final String value;

        private Fileds(final String value) {
            this.value = value;
        }

        @JsonValue
        public String value() {
            return value.toLowerCase();
        }

        @JsonCreator
        public static Fileds forValue(final String str) {
            return Fileds.valueOf(str.toUpperCase());
        }

        @Override
        public String toString() {
            return this.name().toLowerCase();
        }

    }
}
