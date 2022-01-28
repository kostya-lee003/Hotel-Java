package com.company;

public class Client {
    private int balance;
    private final String name;
    private Room room;
    public static boolean VIP;
    boolean isFacilitated;
    private int numberOfRents = 0;

    void pay() {
        this.balance -= Service.roomCost;
    }

    void vipPay(int sale) {
        this.balance -= (Service.roomCost * ((100 - sale) / 100));
    }

    void rentedRoom() {
        this.numberOfRents++;
    }

    int getNumberOfRents() {
        return numberOfRents;
    }

    int getBalance() {
        return balance;
    }

    String getName() {
        return name;
    }

    Room getRoom() {
        return room;
    }

    void setRoom(Room room) {
        this.room = room;
    }

    Client(String name, int balance) {
        this.name = name;
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "Client{" +
                "name='" + name + '\'' +
                ", room=" + room +
                ", numberOfRents=" + numberOfRents +
                '}';
    }
}
