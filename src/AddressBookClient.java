import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

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

    public void startMemberFrame() {
        //Create and set up the window.
        JFrame frame = new JFrame("Member Portal");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
//        MemberPortalPanel mpp = new MemberPortalPanel(frame.getContentPane());

        //Display the window.
        frame.pack();
        frame.setVisible(true);
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

                        startMemberFrame();
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
                    }
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }
    }

    public class AdminLoginButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            //TODO: implement admin listener

        }
    }
}

