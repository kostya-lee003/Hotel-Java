package com.company;

import java.util.ArrayList;

public class Janitor {
    String name;
    ArrayList<Room> rooms = new ArrayList<>(3);

    void cleanRoom(int room) {
        rooms.get(room).isCleaned = true;
    }

    Janitor(String name, ArrayList<Room> rooms) {
        this.rooms = rooms;
        this.name = name;
    }
}
