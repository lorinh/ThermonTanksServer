package com.example.lorin.thermontanks;

import java.util.Base64;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

//SERVER

public class Main {
    private static ServerSocket serverSocket;
    private static Socket clientSocket;
    private static BufferedReader bufferedReader;
    private static String inputLine;

    private static ClientManager clientManager;

    private static final int PORT = 25565;



    public static void main(String[] args) {
        clientManager = new ClientManager();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                AI();
            }
        });
        thread.start();


        try {
            serverSocket = new ServerSocket(PORT);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        while (true) {
            acceptConnections();
        }
    }

    public static void AI() {

        Packet AI1 = new Packet();
        AI1.selfPosition = new Vector2(500,500);
        AI1.ClientId = "AI1";
        clientManager.updateClient(AI1);

        double i = 0;
        while (true) {
            i+= 25;
            AI1.selfPosition.x = (float) Math.cos(i/500)*500+500;
            AI1.selfPosition.y = (float) Math.sin(i/500)*500+500;
            try {
                Thread.sleep(100);
            } catch(Exception e) {
                //ignore
            }
        }
    }

    public static void acceptConnections() {
        String ip = null;

        try {
            Socket clientSocketNew = serverSocket.accept();
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    handleConnection(clientSocketNew);
                }
            });
            t.start();
        }
        catch(IOException e) {
            System.out.println("Error accepting socket from client!");
            System.out.println(e);
        }
    }

    public static void handleConnection(Socket clientSocket) {
        System.out.print("Handling connection...");
        try {
            String ip = clientSocket.getInetAddress().getHostName();

            bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            while ((inputLine = bufferedReader.readLine()) != null) {
                Packet packet = decodeObject(inputLine);
                packet.ClientId = ip;
                if (clientManager.updateClient(packet)) {
                    System.out.println("User " + ip + " connected");
                }
            }
            respond(ip);
        } catch(IOException e) {
            System.out.println("Error decoding information from client!");
            System.out.println(e);
        }
    }

    public static Packet decodeObject(String encodedObject) {
        Packet packet = null;
        try {
            Base64.Decoder decoder = Base64.getDecoder();
            byte b[] = decoder.decode(encodedObject.getBytes());
            ByteArrayInputStream bi = new ByteArrayInputStream(b);
            ObjectInputStream si = new ObjectInputStream(bi);
            packet = (Packet) si.readObject();
        } catch (Exception e) {
            System.out.println("Error decoding packet!");
            e.printStackTrace();
        }
        return packet;
    }

    public static void respond(String ip) {
        Socket socket;
        PrintWriter printWriter;
        try
        {
            socket = new Socket(ip,PORT);
            printWriter = new PrintWriter(socket.getOutputStream(),true);
            String encodedObject = encodeObject(clientManager.getPacket(ip));
            printWriter.println(encodedObject);
            socket.close();
        }
        catch(Exception e)
        {
            System.out.println("Error responding to client!");
            e.printStackTrace();
        }
    }

    public static String encodeObject(Object obj) {
        String stringObj = null;
        try {
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            ObjectOutputStream so = new ObjectOutputStream(bo);
            so.writeObject(obj);
            so.flush();
            Base64.Encoder encode = Base64.getEncoder();
            stringObj = new String(encode.encode(bo.toByteArray()));
        } catch (Exception e) {
            System.out.println("Error encoding packet!");
            e.printStackTrace();
        }
        return stringObj;
    }

    public static void print(String string) {
        System.out.println(string);
    }
}

/*
User 10.118.100.15 connected
Error responding to client!
java.net.ConnectException: Connection timed out: connect
        at java.base/java.net.DualStackPlainSocketImpl.connect0(Native Method)
        at java.base/java.net.DualStackPlainSocketImpl.socketConnect(DualStackPlainSocketImpl.java:79)
        at java.base/java.net.AbstractPlainSocketImpl.doConnect(AbstractPlainSocketImpl.java:400)
        at java.base/java.net.AbstractPlainSocketImpl.connectToAddress(AbstractPlainSocketImpl.java:243)
        at java.base/java.net.AbstractPlainSocketImpl.connect(AbstractPlainSocketImpl.java:225)
        at java.base/java.net.PlainSocketImpl.connect(PlainSocketImpl.java:148)
        at java.base/java.net.SocksSocketImpl.connect(SocksSocketImpl.java:402)
        at java.base/java.net.Socket.connect(Socket.java:591)
        at java.base/java.net.Socket.connect(Socket.java:540)
        at java.base/java.net.Socket.<init>(Socket.java:436)
        at java.base/java.net.Socket.<init>(Socket.java:213)
        at com.example.lorin.thermontanks.Main.respond(Main.java:111)
        at com.example.lorin.thermontanks.Main.acceptConnections(Main.java:88)
        at com.example.lorin.thermontanks.Main.main(Main.java:43)
Error responding to client!
java.net.ConnectException: Connection refused: connect
        at java.base/java.net.DualStackPlainSocketImpl.connect0(Native Method)
        at java.base/java.net.DualStackPlainSocketImpl.socketConnect(DualStackPlainSocketImpl.java:79)
        at java.base/java.net.AbstractPlainSocketImpl.doConnect(AbstractPlainSocketImpl.java:400)
        at java.base/java.net.AbstractPlainSocketImpl.connectToAddress(AbstractPlainSocketImpl.java:243)
        at java.base/java.net.AbstractPlainSocketImpl.connect(AbstractPlainSocketImpl.java:225)
        at java.base/java.net.PlainSocketImpl.connect(PlainSocketImpl.java:148)
        at java.base/java.net.SocksSocketImpl.connect(SocksSocketImpl.java:402)
        at java.base/java.net.Socket.connect(Socket.java:591)
        at java.base/java.net.Socket.connect(Socket.java:540)
        at java.base/java.net.Socket.<init>(Socket.java:436)
        at java.base/java.net.Socket.<init>(Socket.java:213)
        at com.example.lorin.thermontanks.Main.respond(Main.java:111)
        at com.example.lorin.thermontanks.Main.acceptConnections(Main.java:88)
        at com.example.lorin.thermontanks.Main.main(Main.java:43)


        java.net.ConnectException: Connection timed out: connect
        at java.base/java.net.DualStackPlainSocketImpl.connect0(Native Method)
        at java.base/java.net.DualStackPlainSocketImpl.socketConnect(DualStackPlainSocketImpl.java:79)
        at java.base/java.net.AbstractPlainSocketImpl.doConnect(AbstractPlainSocketImpl.java:400)
        at java.base/java.net.AbstractPlainSocketImpl.connectToAddress(AbstractPlainSocketImpl.java:243)
        at java.base/java.net.AbstractPlainSocketImpl.connect(AbstractPlainSocketImpl.java:225)
        at java.base/java.net.PlainSocketImpl.connect(PlainSocketImpl.java:148)
        at java.base/java.net.SocksSocketImpl.connect(SocksSocketImpl.java:402)
        at java.base/java.net.Socket.connect(Socket.java:591)
        at java.base/java.net.Socket.connect(Socket.java:540)
        at java.base/java.net.Socket.<init>(Socket.java:436)
        at java.base/java.net.Socket.<init>(Socket.java:213)
        at com.example.lorin.thermontanks.Main.respond(Main.java:116)
        at com.example.lorin.thermontanks.Main.acceptConnections(Main.java:88)
        at com.example.lorin.thermontanks.Main.main(Main.java:43)

*/