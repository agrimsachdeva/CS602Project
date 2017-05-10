import java.sql.*;
import java.util.ArrayList;

class JdbcMysql {

    private String url = "sql.njit.edu";
    private String ucid = "as2936";    //your ucid
    private String dbpassword = "yeshiva57";    //your MySQL password

    public boolean memberAuthentication(String username, String password) {
        boolean authenticated = false;

        System.out.println("Starting test . . .");

        System.out.println("Loading driver . . .");
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (Exception e) {
            System.err.println("Unable to load driver.");
            e.printStackTrace();
        }
        System.out.println("Driver loaded.");
        System.out.println("Establishing connection . . . ");
        try {
            Connection conn;
            conn = DriverManager.getConnection("jdbc:mysql://" + url + "/" + ucid + "?user=" + ucid + "&password=" + dbpassword);

            System.out.println("Connection established.");
            System.out.println("Creating a Statement object . . . ");

            Statement stmt = conn.createStatement();
            System.out.println("Statement object created.");

            ResultSet rs = stmt.executeQuery("select * from memberlogin where username='" + username + "' and password='" + password + "'");
            int count = 0;
            while (rs.next()) {
                count++;
            }
            if (count > 0) {
                authenticated = true;
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException E) {
            System.out.println("SQLException: " + E.getMessage());
            System.out.println("SQLState:     " + E.getSQLState());
            System.out.println("VendorError:  " + E.getErrorCode());
        }

        return authenticated;
    }

    public boolean adminAuthentication(String username, String password) {
        boolean authenticated = false;

        System.out.println("Starting test . . .");

        System.out.println("Loading driver . . .");
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (Exception e) {
            System.err.println("Unable to load driver.");
            e.printStackTrace();
        }
        System.out.println("Driver loaded.");
        System.out.println("Establishing connection . . . ");
        try {
            Connection conn;
            conn = DriverManager.getConnection("jdbc:mysql://" + url + "/" + ucid + "?user=" + ucid + "&password=" + dbpassword);

            System.out.println("Connection established.");
            System.out.println("Creating a Statement object . . . ");

            Statement stmt = conn.createStatement();
            System.out.println("Statement object created.");

            ResultSet rs = stmt.executeQuery("select * from adminlogin where username='" + username + "' and password='" + password + "'");
            int count = 0;
            while (rs.next()) {
                count++;
            }
            if (count > 0) {
                authenticated = true;
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException E) {
            System.out.println("SQLException: " + E.getMessage());
            System.out.println("SQLState:     " + E.getSQLState());
            System.out.println("VendorError:  " + E.getErrorCode());
        }

        return authenticated;
    }

    public boolean addRecord(String name, String address, String email, String phone) {
        boolean added = true;

        System.out.println("Starting test . . .");

        System.out.println("Loading driver . . .");
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (Exception e) {
            System.err.println("Unable to load driver.");
            e.printStackTrace();
        }
        System.out.println("Driver loaded.");
        System.out.println("Establishing connection . . . ");
        try {
            Connection conn;
            conn = DriverManager.getConnection("jdbc:mysql://" + url + "/" + ucid + "?user=" + ucid + "&password=" + dbpassword);

            System.out.println("Connection established.");
            System.out.println("Creating a Statement object . . . ");

            Statement stmt = conn.createStatement();
            System.out.println("Statement object created.");

            stmt.execute("INSERT INTO `users` (name, address, email, phone) VALUES ('"+name+"','"+address+"','"+email+"','"+phone+"')");

            stmt.close();
            conn.close();
        } catch (SQLException E) {
            added = false;

            System.out.println("SQLException: " + E.getMessage());
            System.out.println("SQLState:     " + E.getSQLState());
            System.out.println("VendorError:  " + E.getErrorCode());
        }

        return added;
    }

    public boolean editRecord(String name, String address, String email, String phone) {
        boolean added = true;

        System.out.println("Starting test . . .");

        System.out.println("Loading driver . . .");
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (Exception e) {
            System.err.println("Unable to load driver.");
            e.printStackTrace();
        }
        System.out.println("Driver loaded.");
        System.out.println("Establishing connection . . . ");
        try {
            Connection conn;
            conn = DriverManager.getConnection("jdbc:mysql://" + url + "/" + ucid + "?user=" + ucid + "&password=" + dbpassword);

            System.out.println("Connection established.");
            System.out.println("Creating a Statement object . . . ");

            Statement stmt = conn.createStatement();
            System.out.println("Statement object created.");

            stmt.execute("update`users` set `address` = '"+address+"'  where `name` = '"+name+"'");
            stmt.execute("update`users` set `email` = '"+email+"'  where `name` = '"+name+"'");
            stmt.execute("update`users` set `phone` = '"+phone+"'  where `name` = '"+name+"'");

            stmt.close();
            conn.close();
        } catch (SQLException E) {
            added = false;

            System.out.println("SQLException: " + E.getMessage());
            System.out.println("SQLState:     " + E.getSQLState());
            System.out.println("VendorError:  " + E.getErrorCode());
        }

        return added;
    }


    public boolean addMember(String username, String password) {
        boolean added = true;

        System.out.println("Starting test . . .");

        System.out.println("Loading driver . . .");
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (Exception e) {
            System.err.println("Unable to load driver.");
            e.printStackTrace();
        }
        System.out.println("Driver loaded.");
        System.out.println("Establishing connection . . . ");
        try {
            Connection conn;
            conn = DriverManager.getConnection("jdbc:mysql://" + url + "/" + ucid + "?user=" + ucid + "&password=" + dbpassword);

            System.out.println("Connection established.");
            System.out.println("Creating a Statement object . . . ");

            Statement stmt = conn.createStatement();
            System.out.println("Statement object created.");

            stmt.execute("INSERT INTO `memberlogin` (username, password) VALUES ('"+username+"','"+password+"')");

            stmt.close();
            conn.close();
        } catch (SQLException E) {
            added = false;

            System.out.println("SQLException: " + E.getMessage());
            System.out.println("SQLState:     " + E.getSQLState());
            System.out.println("VendorError:  " + E.getErrorCode());
        }

        return added;
    }


    public ArrayList<UserObject> fetchUsers() {

        ArrayList<UserObject> userList = new ArrayList<>();

        System.out.println("Starting test . . .");

        System.out.println("Loading driver . . .");
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (Exception e) {
            System.err.println("Unable to load driver.");
            e.printStackTrace();
        }
        System.out.println("Driver loaded.");
        System.out.println("Establishing connection . . . ");
        try {
            Connection conn;
            conn = DriverManager.getConnection("jdbc:mysql://" + url + "/" + ucid + "?user=" + ucid + "&password=" + dbpassword);

            System.out.println("Connection established.");
            System.out.println("Creating a Statement object . . . ");

            Statement stmt = conn.createStatement();
            System.out.println("Statement object created.");

            ResultSet rs = stmt.executeQuery("select * from users");

            while(rs.next()) {
                UserObject temp = new UserObject(rs.getInt("id"), rs.getString("name"), rs.getString("address"), rs.getString("email"),rs.getString("phone"));
                userList.add(temp);
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException E) {
            System.out.println("SQLException: " + E.getMessage());
            System.out.println("SQLState:     " + E.getSQLState());
            System.out.println("VendorError:  " + E.getErrorCode());
        }

        return userList;
    }

    public ArrayList<UserObject> searchByName(String searchTerm) {

        ArrayList<UserObject> userList = new ArrayList<>();

        System.out.println("Starting test . . .");

        System.out.println("Loading driver . . .");
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (Exception e) {
            System.err.println("Unable to load driver.");
            e.printStackTrace();
        }
        System.out.println("Driver loaded.");
        System.out.println("Establishing connection . . . ");
        try {
            Connection conn;
            conn = DriverManager.getConnection("jdbc:mysql://" + url + "/" + ucid + "?user=" + ucid + "&password=" + dbpassword);

            System.out.println("Connection established.");
            System.out.println("Creating a Statement object . . . ");

            Statement stmt = conn.createStatement();
            System.out.println("Statement object created.");

            ResultSet rs = stmt.executeQuery("SELECT * FROM users WHERE name LIKE '%" + searchTerm + "%'");

            while(rs.next()) {
                UserObject temp = new UserObject(rs.getInt("id"), rs.getString("name"), rs.getString("address"), rs.getString("email"),rs.getString("phone"));
                userList.add(temp);
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException E) {
            System.out.println("SQLException: " + E.getMessage());
            System.out.println("SQLState:     " + E.getSQLState());
            System.out.println("VendorError:  " + E.getErrorCode());
        }

        return userList;
    }





    public void test() {
        System.out.println("Starting test . . .");

        System.out.println("Loading driver . . .");
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (Exception e) {
            System.err.println("Unable to load driver.");
            e.printStackTrace();
        }
        System.out.println("Driver loaded.");
        System.out.println("Establishing connection . . . ");
        try {
            Connection conn;
            conn = DriverManager.getConnection("jdbc:mysql://" + url + "/" + ucid + "?user=" + ucid + "&password=" + dbpassword);

            System.out.println("Connection established.");
            System.out.println("Creating a Statement object . . . ");

            Statement stmt = conn.createStatement();
            System.out.println("Statement object created.");
            stmt.executeUpdate("DROP TABLE IF EXISTS user");
            System.out.println("Old table dropped (if it existed).");

            System.out.println("Creating a table . . .");
            stmt.executeUpdate("CREATE TABLE user(" +
                    "idnum INT NOT NULL AUTO_INCREMENT, " +
                    "PRIMARY KEY(idnum), " +
                    "userid VARCHAR(100) UNIQUE NOT NULL, " +
                    "fullname VARCHAR(100) NOT NULL, " +
                    "email VARCHAR(100) NOT NULL, " +
                    "notes TEXT)");
            System.out.println("Table created.");

            System.out.println("Inserting data in table . . .");

            stmt.executeUpdate("INSERT INTO user (userid, fullname, email, notes) " +
                    "VALUES(\"john\", \"john smith\", \"john@njit.edu\", \"blah, blah . . \")");
            stmt.executeUpdate("INSERT INTO user (userid, fullname, email, notes) " +
                    "VALUES(\"jane\", \"jane doe\", \"jane@njit.edu\", \"blah, blah . . \")");
            stmt.executeUpdate("INSERT INTO user (userid, fullname, email, notes) " +
                    "VALUES(\"zip\", \"zip zippy\", \"zippy@njit.edu\", \"blah, blah . . \")");

            System.out.println("Inserted data.");

            System.out.println("Querying table  . . . ");

            ResultSet rs = stmt.executeQuery("SELECT * from user");
            while (rs.next()) {
                System.out.print(rs.getString("idnum") + "\t" + rs.getString("userid") + "\t" + rs.getString("fullname") + "\t" +
                        rs.getString("email") + "\t" + rs.getString("notes"));
                System.out.println();
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException E) {
            System.out.println("SQLException: " + E.getMessage());
            System.out.println("SQLState:     " + E.getSQLState());
            System.out.println("VendorError:  " + E.getErrorCode());
        }
    }
}
