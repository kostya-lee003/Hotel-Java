package com.company;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

/**
 * The model of real world hotel service and its workflow using ArrayList
 * @see java.util.ArrayList
 * @see com.company.Client
 * @see com.company.Room
 * @see com.company.Janitor
 */
public class Hotel {

    public Service service;

    Hotel(int balance, ArrayList<Janitor> janitors, ArrayList<Room> rooms) {
        this.service = new Service(janitors, rooms, balance);
    }
}

class Service {

    private ArrayList<Janitor> janitors;
    private ArrayList<Room> rooms;
    private ArrayList<Client> clients = new ArrayList<>();
    File file;

    /**
     * Balance of hotel
     */
    private int balance = 0;

    /**
    * How many times client needs to rent room in order to become VIP
     */
    private int becomesVIP = 3;

    /**
     * Sale (in percentage) for room if client is VIP
     */
    private int sale = 20;

    /**
     * How much client need to pay to rent a room
     */
    public static final int roomCost = 400;


    ArrayList<Client> getClients() {
        return clients;
    }

    Service(ArrayList<Janitor> janitors, ArrayList<Room> rooms, int balance) {
        this.janitors = janitors;
        this.rooms = rooms;
        this.balance = balance;
        file = new File("clients.txt");
    }

    /**
     * Registers client into room by:
     * <ul>
     *     <li> Checking if client is already facilitated
     *     <li> Checking if client is VIP (means rented a room more than 3 times)
     *     <li> Checking if client has enough money to pay for renting
     *     <li> Putting client's cash into bank's balance
     *     <li> Finding room that is not yet occupied
     *     <li> Finding janitor who will clean this room if it's not sanitized
     * </ul>
     * @param client
     */
    public void registerClient(Client client) {
        if (client.isFacilitated) {
            System.out.println("Client " + client.getName() + " is already facilitated");
            return;
        }

        if (isVIP(client)) {
            VIPPay(client);
        } else {
            if (client.getBalance() < roomCost) {
                System.out.println(client.getName() + " did not pay!");
                return;
            }
            pay(client);
        }

        int roomNumber = findEmptyRoom();
        if (roomNumber == -1) { return; }

        if (!rooms.get(roomNumber).isCleaned) {
            Room room = findRoom(roomNumber);
            if (room == null) { return; }
        }
        facilitateRoom(roomNumber, client);
        clients.add(client);
    }

    /**
     * Removes client from room
     * @param client
     */
    public void removeClient(Client client) {
        if (client == null) { return; }

        System.out.println(client.getName() + " is leaving room number " + (client.getRoom().number + 1));
        int roomNumber = client.getRoom().number;
        rooms.get(roomNumber).isEmpty = true;
        rooms.get(roomNumber).isCleaned = false;
        client.isFacilitated = false;
        clients.remove(client);
    }

    /**
     * Transfers client's cash to hotel's balance (with sale)
     * @param client
     */
    private void VIPPay(Client client) {
        this.balance += roomCost * ((100 - sale) / 100);
        client.vipPay(sale);
        System.out.println(client.getName() + " is VIP and paid with " + sale + "% sale");
    }

    /**
     * Transfers client's cash to hotel's balance
     * @param client
     */
    private void pay(Client client) {
        this.balance += roomCost;
        client.pay();
        System.out.println(client.getName() + " paid!");
    }

    /**
     *  Finds janitor for room and returns this already cleaned room.
     * @param roomNum
     * @return
     */
    Room findRoom(int roomNum) {
        for (Janitor janitor : janitors) {
            int janitorIndexRoomNum = 0;
            for (Room room : janitor.rooms) {
                if (room.number == roomNum) {
                    janitor.cleanRoom(janitorIndexRoomNum);
                    return rooms.get(roomNum);
                }
                janitorIndexRoomNum++;
            }
        }
        return null;
    }

    /**
     * Facilitates client to room
     * @param roomNum
     * @param client
     */
    private void facilitateRoom(int roomNum, Client client) {
        System.out.println("Facilitating new client (" + client.getName() + ") to room number " + (roomNum + 1));
        rooms.get(roomNum).isEmpty = false;
        client.isFacilitated = true;
        client.setRoom(rooms.get(roomNum));
        client.rentedRoom();
    }

    /**
     * Returns empty room number
     * @return
     */
    int findEmptyRoom() {
        for (Room room : rooms) {
            if (room.isEmpty) {
                return room.number;
            }
        }
        System.out.println("All rooms are occupied!");
        return -1;
    }

    /**
     * Checks if client has been facilitated more than 3 times, if not returns false
     * @param client
     * @return
     */
    boolean isVIP(Client client) {
        if (client.getNumberOfRents() > becomesVIP) {
            client.VIP = true;
            return client.VIP;
        }
        return client.VIP;
    }

    /**
     * Checks if there's client with the same name
     * @param name
     * @return
     */
    boolean checkNameDuplication(String name) {
        for (Client c : clients) {
            if (c.getName().toLowerCase().equals(name.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Prints out all clients containing input name
     * @param name
     */
    void searchFor(String name) {
        System.out.println("List of registered clients with name: " + name);
        int i = 1;
        String sName = name.toLowerCase();
        for (Client client : clients) {
            if (client.getName().toLowerCase().contains(sName)) {
                System.out.println(i++ + ") " + client.getName() + " (in room " + (client.getRoom().number + 1) +")");
            }
        }
    }

    /**
     * Prints out all clients with VIP status
     */
    void filterByVIP() {
        System.out.println("List of VIP clients: ");
        boolean VIPlist = false;
        for (Client client : clients) {
            if (isVIP(client)) {
                System.out.println(client.getName() + " in room " + (client.getRoom().number + 1));
                VIPlist = true;
            }
        }
        if (!VIPlist) {
            System.out.println("* No VIP clients yet");
        }
    }

    /**
     * Lists all registered clients
     */
    public void listAllClients() {
        if (clients.isEmpty()) {
            System.out.println("No clients yet*");
        }

        int i = 1;
        for (Client client : clients) {
            System.out.println(i++ + ") " + client.getName());
        }
    }

    /**
     * Finds client by input name
     * @param name
     * @return
     */
    Client findClient(String name) {
        for (Client client : clients) {
            if (client.getName().toLowerCase(Locale.ROOT).equals(name.toLowerCase(Locale.ROOT))) {
                System.out.println("Found client " + client.getName());
                return client;
            }
        }
        return null;
    }

    /**
     * Prints out client's name and room
     * @param client
     */
    public void viewClient(Client client) {
        System.out.println("");
        System.out.println("Name: " + client.getName());
        System.out.println("Room Nō: " + (client.getRoom().number + 1));
        System.out.println("");
    }

    public void saveClientsToFile() throws IOException {
        FileWriter writer = new FileWriter(file);
        writer.write(clients.toString());
        writer.flush();
        writer.close();
    }
}
