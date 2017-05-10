
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

            // ADDING RECORDS
            if (myObject.getMessage().equals("AddRecord")) {
                UserObject myUserObject = (UserObject) myObject;
                JdbcMysql connection = new JdbcMysql();

                String name = myUserObject.getName();
                String address = myUserObject.getAddress();
                String email = myUserObject.getEmail();
                String phone = myUserObject.getPhone();

                System.out.println("Adding user: " + name);

                if (connection.addRecord(name, address, email, phone)) {
                    myUserObject.setMessage("Added");
                    System.out.println("Message written: " + myUserObject.getMessage());
//                    myUserObject.setUsername(username);
//                    myUserObject.setUserList(connection.fetchUsers());
                }

                out.writeObject(myUserObject);
            }

            // ADDING MEMBERS
            if (myObject.getMessage().equals("AddMember")) {
                MemberObject myUserObject = (MemberObject) myObject;
                JdbcMysql connection = new JdbcMysql();

                String username = myUserObject.getUsername();
                String password = myUserObject.getPassword();

                System.out.println("Adding member: " + username);

                if (connection.addMember(username, password)) {
                    myUserObject.setMessage("Added");
                    System.out.println("Message written: " + myUserObject.getMessage());
//                    myUserObject.setUsername(username);
//                    myUserObject.setUserList(connection.fetchUsers());
                }

                out.writeObject(myUserObject);
            }

            // REFRESHING
            if (myObject.getMessage().equals("Refresh")) {
                LoginObject myLoginObject = (LoginObject) myObject;
                JdbcMysql connection = new JdbcMysql();

                System.out.println("REFRESHING");

                myLoginObject.setUserList(connection.fetchUsers());
                myLoginObject.setMessage("Success");
                out.writeObject(myLoginObject);
            }

            // SEARCH
            if (myObject.getMessage().equals("Search")) {
                LoginObject myLoginObject = (LoginObject) myObject;
                JdbcMysql connection = new JdbcMysql();

                System.out.println("REFRESHING");
                String searchTerm = myLoginObject.getSearchTerm();

                myLoginObject.setUserList(connection.searchByName(searchTerm));
                myLoginObject.setMessage("Found");
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

