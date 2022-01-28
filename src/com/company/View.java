package com.company;

import java.io.IOException;
import java.util.Scanner;

public class View {
    Scanner scanner = new Scanner(System.in);
    Hotel hotel;
    Service service;
    View(Hotel hotel) throws IOException {
        this.hotel = hotel;
        this.service = hotel.service;
        while (true) {
            System.out.println("""
                                    
                    1) List all clients
                    2) New client
                    3) Delete client
                    4) Search for client with name
                    5) Filter clients
                    6) Save clients to file
                                    
                    """);
            String option = enterOption();
            receiveOption(option);
        }
    }

    private String enterOption() {
        System.out.print("Option: ");
        return scanner.nextLine();
    }

    private void receiveOption(String option) throws IOException {
        Client client = null;
        switch (option) {
            case "1":
                System.out.println("List of registered clients: ");
                service.listAllClients();
                break;
            case "2":
                 String name = enterName();
                 if (service.checkNameDuplication(name)) {
                     System.out.println("Client " + client.getName() + " already exists");
                     return;
                 }
                System.out.print("Enter client's balance: ");
                String balance = scanner.nextLine();
                client = new Client(name, Integer.parseInt(balance));
                service.registerClient(client);
                break;
            case "3":
                System.out.print("Remove client with name: ");
                String n3 = scanner.nextLine();
                service.removeClient(service.findClient(n3));
                break;
            case "4":
                System.out.print("Client's name: ");
                String n4 = scanner.nextLine();
                client = service.findClient(n4);
                service.viewClient(client);
                break;
            case "5":
                System.out.println("Filter all clients by: ");
                System.out.println("""
                        
                        1) Name
                        2) VIP status
                        (Type '0' to go back)
                        
                        """);
                filterClients(enterOption());
                break;
            case "6":
                System.out.println("Saving all clients to file");
                service.saveClientsToFile();

            default:
                System.out.println("Enter one of given options...");
        }
    }

    private void filterClients(String option) {
        switch (option) {
            case "1":
                System.out.print("Filter clients by name: ");
                String searchedName = scanner.nextLine();
                if (!service.getClients().contains(service.findClient(searchedName))) {
                    System.out.println("No matches for name " + searchedName);
                    return;
                }
                service.searchFor(searchedName);
                break;

            case "2":
                service.filterByVIP();
                break;

            case "0":
                return;

            default:
                System.out.println("Enter one of the given options...");
        }
    }

    private String enterName() {
        System.out.print("Enter client name: ");
        return scanner.nextLine();
    }
}
