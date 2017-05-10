
import java.io.*;
import java.net.*;
import java.sql.*;
import java.util.ArrayList;


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
            in =
                    new ObjectInputStream(incoming.getInputStream());

            out =
                    new ObjectOutputStream(incoming.getOutputStream());

            myObject = (DataObject) in.readObject();

            // AUTHENTICATING USERS
            if (myObject.getMessage().equals("LoginCredentials")) {
                LoginObject myLoginObject = (LoginObject) myObject;
                JdbcMysql connection = new JdbcMysql();

                String username = myLoginObject.getUsername();
                String password = myLoginObject.getPassword();
                System.out.println("Authenticating user: " + username + myLoginObject.getMessage());

                if (connection.memberAuthentication(username, password)) {
                    myLoginObject.setMessage("Authorized");
                    System.out.println("Message written: " + myLoginObject.getMessage());
                    myLoginObject.setUsername(username);
                    myLoginObject.setUserList(connection.fetchUsers());
                }

                out.writeObject(myLoginObject);
            }

            // AUTHENTICATING ADMINISTRATORS
            if (myObject.getMessage().equals("AdminLoginCredentials")) {
                LoginObject myLoginObject = (LoginObject) myObject;
                JdbcMysql connection = new JdbcMysql();

                String username = myLoginObject.getUsername();
                String password = myLoginObject.getPassword();
                System.out.println("Authenticating user: " + username + myLoginObject.getMessage());

                if (connection.adminAuthentication(username, password)) {
                    myLoginObject.setMessage("Authorized");
                    System.out.println("Message written: " + myLoginObject.getMessage());
                    myLoginObject.setUsername(username);
                    myLoginObject.setUserList(connection.fetchUsers());
                }

                out.writeObject(myLoginObject);
            }

            in.close();
            out.close();
            incoming.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    DataObject myObject = null;
    private Socket incoming;

    ObjectOutputStream out;
    ObjectInputStream in;

}

