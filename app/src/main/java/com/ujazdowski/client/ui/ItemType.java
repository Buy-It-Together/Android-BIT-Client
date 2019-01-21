package com.ujazdowski.client.ui;

public enum ItemType {
    LOCATION(0),
    MESSAGE(1);

    private int type;

    ItemType(int type) {
        this.type = type;
    }

    public static ItemType forValue(int i) {
        return i == 0 ? LOCATION : MESSAGE;
    }

    public int getType() {
        return type;
    }
}
