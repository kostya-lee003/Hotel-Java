package com.company;

public class Room {
    int number;
    boolean isCleaned = false;
    boolean isEmpty = true;

    Room(int number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "Room{" +
                "number=" + number +
                '}';
    }
}
