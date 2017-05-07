import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] arg) {
        try {


            DataObject myObject = new DataObject();

            myObject.setMessage("Did you get this?");

            System.out.println("Message sent : " + myObject.getMessage());

            Socket socketToServer = new Socket("127.0.0.1", 3000);

            ObjectOutputStream myOutputStream =
                    new ObjectOutputStream(socketToServer.getOutputStream());

            ObjectInputStream myInputStream =
                    new ObjectInputStream(socketToServer.getInputStream());

            myOutputStream.writeObject(myObject);

            myObject = (DataObject) myInputStream.readObject();

            System.out.println("Messaged received : " + myObject.getMessage());

            myOutputStream.close();

            myInputStream.close();

            socketToServer.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
