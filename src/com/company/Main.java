package com.company;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {

        int capacity = 9;
        ArrayList<Room> rooms = new ArrayList<>(capacity);
        ArrayList<Janitor> janitors = new ArrayList<>(3);
        //ArrayList<Client> clients = new ArrayList<>();
        String names[] = {"Stefan", "Siri", "Tom"};

        for (int i = 0; i < capacity; i++) {
            rooms.add(new Room(i));
        }
        int roomNum = 0;
        for (String name : names) {
            ArrayList<Room> jRooms = new ArrayList<>(3);
            jRooms.add(rooms.get(roomNum++));
            jRooms.add(rooms.get(roomNum++));
            jRooms.add(rooms.get(roomNum++));
            janitors.add(new Janitor(name, jRooms));
        }

        Hotel hotel = new Hotel(0, janitors, rooms);
        View view = new View(hotel);

        // Saving to file
    }
}
