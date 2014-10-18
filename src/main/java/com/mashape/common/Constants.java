package com.mashape.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Created by yxzhao on 10/17/14.
 */
public class Constants {

    public enum Fileds {
        Title("TITLE"),
        Content("CONTENT"),
        Done("DONE"),
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
