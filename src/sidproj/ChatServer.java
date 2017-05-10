package sidproj;

import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer {
    private static ArrayList<ChatHandler> onlineUsers = new ArrayList<ChatHandler>();

    public static void main(String[] args) {
        ArrayList<ChatHandler> AllHandlers = new ArrayList<ChatHandler>();
        try {
            ServerSocket s = new ServerSocket(3796);

            for (; ; ) {
                Socket incoming = s.accept();
                ChatHandler chatHandler = new ChatHandler(incoming, AllHandlers);
                chatHandler.start();
                onlineUsers.add(chatHandler);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void removeFromOnlineUser(ChatHandler chatHandler) {
        onlineUsers.remove(chatHandler);
    }

    public static ArrayList<String> getOnlineUsers() {

        ArrayList<String> users = new ArrayList<String>();

        for (Iterator<ChatHandler> iterator = onlineUsers.iterator(); iterator.hasNext(); ) {
            ChatHandler chatHandler = iterator.next();
            users.add(chatHandler.toString());
        }
        return users;
    }
}

class ChatHandler extends Thread {
    public ChatHandler(Socket i, ArrayList<ChatHandler> h) {
        incoming = i;
        handlers = h;
        handlers.add(this);
        try {
            in = new ObjectInputStream(incoming.getInputStream());
            out = new ObjectOutputStream(incoming.getOutputStream());
        } catch (IOException ioe) {
            System.out.println("Could not create streams.");
        }
    }

    public synchronized void broadcast() {

        ChatHandler left = null;
        if (myObject.getMessage().equals("bye")) { // my client wants to leave
            done = true;
            handlers.remove(this);
            ChatServer.removeFromOnlineUser(this);
            System.out.println("Removed handler. Number of handlers: " + handlers.size());
        }
        for (ChatHandler handler : handlers) {
            ChatMessage cm = new ChatMessage();
            cm.setName(myObject.getName());
            cm.setMessage(myObject.getMessage());
            String data = myObject.getName() + ":" + myObject.getMessage();
            String fileName = handler.username + ".txt";
            WriteData.writeData(fileName, data);
            try {
                if (myObject.getMessage().contains("HAS ENTERED") && handler.username.equals(myObject.getName()) && !handler.history) {
                    handler.out.writeObject(ReadData.readData(fileName));
                    System.out.println("Chat Histroy:" + ReadData.readData(fileName));
                    handler.history = true;
                } else {
                    handler.out.writeObject("");
                }
                handler.out.writeObject(ChatServer.getOnlineUsers());
                handler.out.writeObject(cm);
                System.out.println(ChatServer.getOnlineUsers().size() + " " + ChatServer.getOnlineUsers().toString());
                System.out.println("Writing to handler outputstream: " + cm.getMessage());
            } catch (IOException ioe) {
                //one of the other handlers hung up
                left = handler; // remove that handler from the arraylist
            }
        }
        handlers.remove(left);

        System.out.println("Number of handlers: " + handlers.size());
    }

    public synchronized void sendCordinates(String values) {
        ChatHandler left = null;
        for (ChatHandler handler : handlers) {
            try {
                handler.out.writeObject("CANVAS");
                handler.out.writeObject(values);
            } catch (IOException ioe) {
                //one of the other handlers hung up
                left = handler; // remove that handler from the arraylist
            }
        }
        handlers.remove(left);

        System.out.println("Number of handlers: " + handlers.size());
    }

    public void run() {
        try {
            while (!done) {

                Object object = in.readObject();

                if (object instanceof String) {
                    String values = (String) in.readObject();
                    sendCordinates(values);
                } else {
                    myObject = (ChatMessage) object;
                    String username = (String) in.readObject();
                    String data = myObject.getName() + ":" + myObject.getMessage();
                    String fileName = myObject.getName() + ".txt";
                    System.out.println("Message read: " + myObject.getName() + myObject.getMessage());
                    this.username = username;
                    broadcast();
                }
            }
        } catch (IOException e) {
            if (e.getMessage().equals("Connection reset")) {
                System.out.println("A client terminated its connection.");
            } else {
                System.out.println("Problem receiving: " + e.getMessage());
            }
        } catch (ClassNotFoundException cnfe) {
            System.out.println(cnfe.getMessage());
        } finally {
            handlers.remove(this);
        }
    }

    public String toString() {
        return username;
    }

    ChatMessage myObject = null;
    private Socket incoming;

    boolean done = false;
    ArrayList<ChatHandler> handlers;

    ObjectOutputStream out;
    ObjectInputStream in;
    String username;
    boolean history;
}
