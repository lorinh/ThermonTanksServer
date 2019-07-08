package com.example.lorin.thermontanks;

import java.util.ArrayList;

public class ClientManager {
    private ArrayList<Packet> clients;

    public ClientManager() {
        clients = new ArrayList<Packet>();
    }

    //Updates client when it connects to the server. Returns if it's the first time the client is connecting.
    public boolean updateClient(Packet packet) {
        int clientPosition = findClient(packet.ClientId);
        if (clientPosition >= 0) {
            clients.set(clientPosition, packet);
            return false;
        } else {
            clients.add(packet);
            return true;
        }
    }


    //Finds the packet position in clients.
    private int findClient(String id) {
        int counter = 0;
        for (Packet x : clients) {
            if (x.ClientId == id) {
                return counter;
            }
            counter++;
        }
        return -1;
    }

    //Returns a packet from clients
    public Packet grabPacket(String ClientID) {
        int clientPosition = findClient(ClientID);
        return clients.get(clientPosition);
    }

    //Returns the information needed to be given to the client
    public Packet getPacket(String ClientID) {
        Packet dataPacket = new Packet();
        dataPacket.otherPositions = new Vector2[clients.size()-1];
        int counter = 0;
        for (Packet x : clients) {
            if (x.ClientId != ClientID) {
                dataPacket.otherPositions[counter] = x.selfPosition;
                counter++;
            }
        }
        return dataPacket;
    }

}
