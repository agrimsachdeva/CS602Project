import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class AddressBookClient {
    public static void main(String[] args) {

        AddressBookClient ab = new AddressBookClient();
        ab.start();
    }

    public void start() {
        //Create and set up the window.
        JFrame frame = new JFrame("Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        LoginClientPanel lcp = new LoginClientPanel(frame.getContentPane());

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public void startMemberFrame(ArrayList userList) {
        //Create and set up the window.
        JFrame frame = new JFrame("Member Portal");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        MemberPortalPanel mpp = new MemberPortalPanel(frame.getContentPane(), userList);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public void startAdminFrame(ArrayList userList) {
        //Create and set up the window.
        JFrame frame = new JFrame("Administrator Portal");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        AdminPortalPanel app = new AdminPortalPanel(frame.getContentPane(), userList);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public class AdminPortalPanel extends JPanel {

        final static String RECORDSPANEL = "Member Records";
        final static String UPDATEPANEL = "Update Your Record";
        final static String ADDMEMBER = "Add A Member";
        final static int extraWindowWidth = 100;

        JTable table;
        JScrollPane scrollPane;

        JLabel usernameLabel, passwordLabel, statusLabel;
        JTextField usernameText;
        JPasswordField passwordText;
        JButton loginButton;

        JLabel adminUsernameLabel, adminPasswordLabel, adminStatusLabel;
        JTextField adminUsernameText;
        JPasswordField adminPasswordText;
        JButton adminLoginButton;


        public AdminPortalPanel (Container pane, ArrayList<UserObject> userList) {
            JTabbedPane tabbedPane = new JTabbedPane();

            table = new JTable();
            scrollPane = new JScrollPane();

            table.setModel(new DefaultTableModel(
                    new Object[][]{

                    },
                    new String[]{
                            "Id", "Name", "Address", "Email", "Phone"
                    }
            ));

            scrollPane.setViewportView(table);

            addRowToTable(userList);

//            usernameLabel = new JLabel("Enter Username");
//            passwordLabel = new JLabel("Enter Password");
//            usernameText = new JTextField(20);
//            passwordText = new JPasswordField(20);
//            loginButton = new JButton("Login");
//            statusLabel = new JLabel("");


//            loginButton.addActionListener(new LoginButtonListener());

//            adminUsernameLabel = new JLabel("Enter Admin Username");
//            adminPasswordLabel = new JLabel("Enter Admin Password");
//            adminUsernameText = new JTextField(20);
//            adminPasswordText = new JPasswordField(20);
//            adminLoginButton = new JButton("Login");
//            adminStatusLabel = new JLabel("");

//            adminLoginButton.addActionListener(new AdminLoginButtonListener());


            //Create the "cards"
            JPanel memberCard = new JPanel() {
                //Make the panel wider than it really needs, so
                //the window's wide enough for the tabs to stay
                //in one row.
                public Dimension getPreferredSize() {
                    Dimension size = super.getPreferredSize();
                    size.width += extraWindowWidth;
                    return size;
                }
            };

            GroupLayout layout = new GroupLayout(memberCard);

            memberCard.setLayout(layout);

            layout.setHorizontalGroup(
                    layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                    .addGap(32, 32, 32)
                                    .addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                    .addContainerGap(61, Short.MAX_VALUE))
            );
            layout.setVerticalGroup(
                    layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                    .addContainerGap()
                                    .addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                    .addContainerGap(14, Short.MAX_VALUE))
            );

//            memberCard.add(usernameLabel);
//            memberCard.add(usernameText);
//            memberCard.add(passwordLabel);
//            memberCard.add(passwordText);
//            memberCard.add(loginButton, BOTTOM_ALIGNMENT);
//            memberCard.add(statusLabel);

            JPanel updateCard = new JPanel() {
                //Make the panel wider than it really needs, so
                //the window's wide enough for the tabs to stay
                //in one row.
                public Dimension getPreferredSize() {
                    Dimension size = super.getPreferredSize();
                    size.width += extraWindowWidth;
                    return size;
                }
            };

            JPanel addMemberCard = new JPanel() {
                //Make the panel wider than it really needs, so
                //the window's wide enough for the tabs to stay
                //in one row.
                public Dimension getPreferredSize() {
                    Dimension size = super.getPreferredSize();
                    size.width += extraWindowWidth;
                    return size;
                }
            };

//            adminCard.add(adminUsernameLabel);
//            adminCard.add(adminUsernameText);
//            adminCard.add(adminPasswordLabel);
//            adminCard.add(adminPasswordText);
//            adminCard.add(adminLoginButton);
//            adminCard.add(adminStatusLabel);

            tabbedPane.addTab(RECORDSPANEL, memberCard);
            tabbedPane.addTab(UPDATEPANEL, updateCard);
            tabbedPane.addTab(ADDMEMBER, addMemberCard);

            pane.add(tabbedPane);
        }

        public void addRowToTable(ArrayList<UserObject> userList) {
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            ArrayList<UserObject> list = userList;
            Object rowData[] = new Object[5];
            for (int i = 0; i < list.size(); i++) {
                rowData[0] = list.get(i).id;
                rowData[1] = list.get(i).name;
                rowData[2] = list.get(i).address;
                rowData[3] = list.get(i).email;
                rowData[4] = list.get(i).phone;
                model.addRow(rowData);
            }

        }



        public class LoginButtonListener implements ActionListener, Runnable {

            Thread t;
            ObjectOutputStream myOutputStream;
            ObjectInputStream myInputStream;
            LoginObject readLoginObject;

            @Override
            public void actionPerformed(ActionEvent e) {
//                try {
//                    LoginObject loginObject = new LoginObject();
//
//                    loginObject.setMessage("LoginCredentials");
//
//                    loginObject.setUsername(usernameText.getText().trim());
//
//                    loginObject.setPassword(String.valueOf(passwordText.getPassword()));
//
//                    Socket socketToServer = new Socket("127.0.0.1", 3000);
//
//                    myOutputStream =
//                            new ObjectOutputStream(socketToServer.getOutputStream());
//
//                    myInputStream =
//                            new ObjectInputStream(socketToServer.getInputStream());
//
//                    myOutputStream.writeObject(loginObject);
//
//                    readLoginObject = (LoginObject) myInputStream.readObject();
//
//                    if (readLoginObject.getMessage().equalsIgnoreCase("Authorized")) {
//                        System.out.println("User Authorized");
//
//                        startMemberFrame();
//                        //TODO: handle authenticated users, pass required
//                    }
//                    myOutputStream.close();
//
//                    myInputStream.close();
//
//                    socketToServer.close();
//
//                } catch (UnknownHostException uhe) {
//                    System.out.println(uhe.getMessage());
//                } catch (IOException ioe) {
//                    System.out.println(ioe.getMessage());
//                } catch (Exception ex) {
//                    ex.printStackTrace();
//                }
//
//                loginButton.setEnabled(false);
//
//                t = new Thread(this);
//                t.start();
            }

            @Override
            public void run() {
                try {
                    while (!(readLoginObject.getMessage()).equalsIgnoreCase("Authorized")) {
                        statusLabel.setText("Not Authorized");
                    }
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }
    }

    public class MemberPortalPanel extends JPanel {

        final static String RECORDSPANEL = "Member Records";
        final static String UPDATEPANEL = "Update Your Record";
        final static int extraWindowWidth = 100;

        JTable table;
        JScrollPane scrollPane;

        JLabel usernameLabel, passwordLabel, statusLabel;
        JTextField usernameText;
        JPasswordField passwordText;
        JButton loginButton;

        JLabel adminUsernameLabel, adminPasswordLabel, adminStatusLabel;
        JTextField adminUsernameText;
        JPasswordField adminPasswordText;
        JButton adminLoginButton;


        public MemberPortalPanel (Container pane, ArrayList<UserObject> userList) {
            JTabbedPane tabbedPane = new JTabbedPane();

            table = new JTable();
            scrollPane = new JScrollPane();

            table.setModel(new DefaultTableModel(
                    new Object[][]{

                    },
                    new String[]{
                            "Id", "Name", "Address", "Email", "Phone"
                    }
            ));

            scrollPane.setViewportView(table);

            addRowToTable(userList);

//            usernameLabel = new JLabel("Enter Username");
//            passwordLabel = new JLabel("Enter Password");
//            usernameText = new JTextField(20);
//            passwordText = new JPasswordField(20);
//            loginButton = new JButton("Login");
//            statusLabel = new JLabel("");


//            loginButton.addActionListener(new LoginButtonListener());

//            adminUsernameLabel = new JLabel("Enter Admin Username");
//            adminPasswordLabel = new JLabel("Enter Admin Password");
//            adminUsernameText = new JTextField(20);
//            adminPasswordText = new JPasswordField(20);
//            adminLoginButton = new JButton("Login");
//            adminStatusLabel = new JLabel("");

//            adminLoginButton.addActionListener(new AdminLoginButtonListener());


            //Create the "cards"
            JPanel memberCard = new JPanel() {
                //Make the panel wider than it really needs, so
                //the window's wide enough for the tabs to stay
                //in one row.
                public Dimension getPreferredSize() {
                    Dimension size = super.getPreferredSize();
                    size.width += extraWindowWidth;
                    return size;
                }
            };

            GroupLayout layout = new GroupLayout(memberCard);

            memberCard.setLayout(layout);

            layout.setHorizontalGroup(
                    layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                    .addGap(32, 32, 32)
                                    .addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                    .addContainerGap(61, Short.MAX_VALUE))
            );
            layout.setVerticalGroup(
                    layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                    .addContainerGap()
                                    .addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                    .addContainerGap(14, Short.MAX_VALUE))
            );

//            memberCard.add(usernameLabel);
//            memberCard.add(usernameText);
//            memberCard.add(passwordLabel);
//            memberCard.add(passwordText);
//            memberCard.add(loginButton, BOTTOM_ALIGNMENT);
//            memberCard.add(statusLabel);

            JPanel updateCard = new JPanel() {
                //Make the panel wider than it really needs, so
                //the window's wide enough for the tabs to stay
                //in one row.
                public Dimension getPreferredSize() {
                    Dimension size = super.getPreferredSize();
                    size.width += extraWindowWidth;
                    return size;
                }
            };

//            adminCard.add(adminUsernameLabel);
//            adminCard.add(adminUsernameText);
//            adminCard.add(adminPasswordLabel);
//            adminCard.add(adminPasswordText);
//            adminCard.add(adminLoginButton);
//            adminCard.add(adminStatusLabel);

            tabbedPane.addTab(RECORDSPANEL, memberCard);
            tabbedPane.addTab(UPDATEPANEL, updateCard);

            pane.add(tabbedPane);
        }

        public void addRowToTable(ArrayList<UserObject> userList) {
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            ArrayList<UserObject> list = userList;
            Object rowData[] = new Object[5];
            for (int i = 0; i < list.size(); i++) {
                rowData[0] = list.get(i).id;
                rowData[1] = list.get(i).name;
                rowData[2] = list.get(i).address;
                rowData[3] = list.get(i).email;
                rowData[4] = list.get(i).phone;
                model.addRow(rowData);
            }

        }



        public class LoginButtonListener implements ActionListener, Runnable {

            Thread t;
            ObjectOutputStream myOutputStream;
            ObjectInputStream myInputStream;
            LoginObject readLoginObject;

            @Override
            public void actionPerformed(ActionEvent e) {
//                try {
//                    LoginObject loginObject = new LoginObject();
//
//                    loginObject.setMessage("LoginCredentials");
//
//                    loginObject.setUsername(usernameText.getText().trim());
//
//                    loginObject.setPassword(String.valueOf(passwordText.getPassword()));
//
//                    Socket socketToServer = new Socket("127.0.0.1", 3000);
//
//                    myOutputStream =
//                            new ObjectOutputStream(socketToServer.getOutputStream());
//
//                    myInputStream =
//                            new ObjectInputStream(socketToServer.getInputStream());
//
//                    myOutputStream.writeObject(loginObject);
//
//                    readLoginObject = (LoginObject) myInputStream.readObject();
//
//                    if (readLoginObject.getMessage().equalsIgnoreCase("Authorized")) {
//                        System.out.println("User Authorized");
//
//                        startMemberFrame();
//                        //TODO: handle authenticated users, pass required
//                    }
//                    myOutputStream.close();
//
//                    myInputStream.close();
//
//                    socketToServer.close();
//
//                } catch (UnknownHostException uhe) {
//                    System.out.println(uhe.getMessage());
//                } catch (IOException ioe) {
//                    System.out.println(ioe.getMessage());
//                } catch (Exception ex) {
//                    ex.printStackTrace();
//                }
//
//                loginButton.setEnabled(false);
//
//                t = new Thread(this);
//                t.start();
            }

            @Override
            public void run() {
                try {
                    while (!(readLoginObject.getMessage()).equalsIgnoreCase("Authorized")) {
                        statusLabel.setText("Not Authorized");
                    }
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }
    }

    public class LoginClientPanel extends JPanel {

        final static String USERPANEL = "Member Login";
        final static String ADMINPANEL = "Administrator Login";
        final static int extraWindowWidth = 100;

        JLabel usernameLabel, passwordLabel, statusLabel;
        JTextField usernameText;
        JPasswordField passwordText;
        JButton loginButton;

        JLabel adminUsernameLabel, adminPasswordLabel, adminStatusLabel;
        JTextField adminUsernameText;
        JPasswordField adminPasswordText;
        JButton adminLoginButton;

        public LoginClientPanel(Container pane) {
            JTabbedPane tabbedPane = new JTabbedPane();

            usernameLabel = new JLabel("Enter Username");
            passwordLabel = new JLabel("Enter Password");
            usernameText = new JTextField(20);
            passwordText = new JPasswordField(20);
            loginButton = new JButton("Login");
            statusLabel = new JLabel("");


            loginButton.addActionListener(new LoginButtonListener());

            adminUsernameLabel = new JLabel("Enter Admin Username");
            adminPasswordLabel = new JLabel("Enter Admin Password");
            adminUsernameText = new JTextField(20);
            adminPasswordText = new JPasswordField(20);
            adminLoginButton = new JButton("Login");
            adminStatusLabel = new JLabel("");

            adminLoginButton.addActionListener(new AdminLoginButtonListener());


            //Create the "cards"
            JPanel memberCard = new JPanel() {
                //Make the panel wider than it really needs, so
                //the window's wide enough for the tabs to stay
                //in one row.
                public Dimension getPreferredSize() {
                    Dimension size = super.getPreferredSize();
                    size.width += extraWindowWidth;
                    return size;
                }
            };

            memberCard.add(usernameLabel);
            memberCard.add(usernameText);
            memberCard.add(passwordLabel);
            memberCard.add(passwordText);
            memberCard.add(loginButton, BOTTOM_ALIGNMENT);
            memberCard.add(statusLabel);

            JPanel adminCard = new JPanel() {
                //Make the panel wider than it really needs, so
                //the window's wide enough for the tabs to stay
                //in one row.
                public Dimension getPreferredSize() {
                    Dimension size = super.getPreferredSize();
                    size.width += extraWindowWidth;
                    return size;
                }
            };

            adminCard.add(adminUsernameLabel);
            adminCard.add(adminUsernameText);
            adminCard.add(adminPasswordLabel);
            adminCard.add(adminPasswordText);
            adminCard.add(adminLoginButton);
            adminCard.add(adminStatusLabel);

            tabbedPane.addTab(USERPANEL, memberCard);
            tabbedPane.addTab(ADMINPANEL, adminCard);

            pane.add(tabbedPane);
        }

        public class LoginButtonListener implements ActionListener, Runnable {

            Thread t;
            ObjectOutputStream myOutputStream;
            ObjectInputStream myInputStream;
            LoginObject readLoginObject;

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    LoginObject loginObject = new LoginObject();

                    loginObject.setMessage("LoginCredentials");

                    loginObject.setUsername(usernameText.getText().trim());

                    loginObject.setPassword(String.valueOf(passwordText.getPassword()));

                    Socket socketToServer = new Socket("127.0.0.1", 3000);

                    myOutputStream =
                            new ObjectOutputStream(socketToServer.getOutputStream());

                    myInputStream =
                            new ObjectInputStream(socketToServer.getInputStream());

                    myOutputStream.writeObject(loginObject);

                    readLoginObject = (LoginObject) myInputStream.readObject();

                    if (readLoginObject.getMessage().equalsIgnoreCase("Authorized")) {
                        System.out.println("User Authorized");

                        ArrayList<UserObject> userList = readLoginObject.getUserList();

                        startMemberFrame(userList);
                        //TODO: handle authenticated users, pass required
                    }
                    myOutputStream.close();

                    myInputStream.close();

                    socketToServer.close();

                } catch (UnknownHostException uhe) {
                    System.out.println(uhe.getMessage());
                } catch (IOException ioe) {
                    System.out.println(ioe.getMessage());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                loginButton.setEnabled(false);

                t = new Thread(this);
                t.start();
            }

            @Override
            public void run() {
                try {
                    while (!(readLoginObject.getMessage()).equalsIgnoreCase("Authorized")) {
                        statusLabel.setText("Not Authorized");
                        loginButton.setEnabled(true);

                    }
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }

        public class AdminLoginButtonListener implements ActionListener, Runnable {
            Thread t;
            ObjectOutputStream myOutputStream;
            ObjectInputStream myInputStream;
            LoginObject readLoginObject;

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    LoginObject loginObject = new LoginObject();

                    loginObject.setMessage("AdminLoginCredentials");

                    loginObject.setUsername(adminUsernameText.getText().trim());

                    loginObject.setPassword(String.valueOf(adminPasswordText.getPassword()));

                    Socket socketToServer = new Socket("127.0.0.1", 3000);

                    myOutputStream =
                            new ObjectOutputStream(socketToServer.getOutputStream());

                    myInputStream =
                            new ObjectInputStream(socketToServer.getInputStream());

                    myOutputStream.writeObject(loginObject);

                    readLoginObject = (LoginObject) myInputStream.readObject();

                    if (readLoginObject.getMessage().equalsIgnoreCase("Authorized")) {
                        System.out.println("User Authorized");

                        ArrayList<UserObject> userList = readLoginObject.getUserList();

                        startAdminFrame(userList);
                        //TODO: handle authenticated users, pass required
                    }
                    myOutputStream.close();

                    myInputStream.close();

                    socketToServer.close();

                } catch (UnknownHostException uhe) {
                    System.out.println(uhe.getMessage());
                } catch (IOException ioe) {
                    System.out.println(ioe.getMessage());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                adminLoginButton.setEnabled(false);

                t = new Thread(this);
                t.start();
            }

            @Override
            public void run() {
                try {
                    while (!(readLoginObject.getMessage()).equalsIgnoreCase("Authorized")) {
                        adminStatusLabel.setText("Not Authorized");
                        adminLoginButton.setEnabled(true);

                    }
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }
    }

}


