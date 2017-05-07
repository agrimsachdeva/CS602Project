
import java.io.*;
import java.net.*;

public class ThreadedDataObjectServer {
    public static void main(String[] args) {

        try {
            ServerSocket s = new ServerSocket(3000);

            for (; ; ) {
                Socket incoming = s.accept();
                new ThreadedDataObjectHandler(incoming).start();

            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}

class ThreadedDataObjectHandler extends Thread {
    public ThreadedDataObjectHandler(Socket i) {
        incoming = i;
    }

    public void run() {
        try {
            ObjectInputStream in =
                    new ObjectInputStream(incoming.getInputStream());

            ObjectOutputStream out =
                    new ObjectOutputStream(incoming.getOutputStream());

            myObject = (DataObject) in.readObject();

            System.out.println("Message read: " + myObject.getMessage());

            myObject.setMessage("Got it!");

            System.out.println("Message written: " + myObject.getMessage());

            out.writeObject(myObject);

            in.close();

            out.close();

            incoming.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    DataObject myObject = null;
    private Socket incoming;

}

