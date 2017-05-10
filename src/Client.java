import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] arg) {
        try {


            DataObject loginObject = new DataObject();

            loginObject.setMessage("Did you get this?");

            System.out.println("Message sent : " + loginObject.getMessage());

            Socket socketToServer = new Socket("127.0.0.1", 3000);

            ObjectOutputStream myOutputStream =
                    new ObjectOutputStream(socketToServer.getOutputStream());

            ObjectInputStream myInputStream =
                    new ObjectInputStream(socketToServer.getInputStream());

            myOutputStream.writeObject(loginObject);

            loginObject = (DataObject) myInputStream.readObject();

            System.out.println("Messaged received : " + loginObject.getMessage());

            myOutputStream.close();

            myInputStream.close();

            socketToServer.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
