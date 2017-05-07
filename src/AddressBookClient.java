import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class AddressBookClient {
    public static void main(String[] arg) {

        AddressBookClient ab = new AddressBookClient();
        ab.start();

//        try {
//
//
//            DataObject myObject = new DataObject();
//
//            myObject.setMessage("Did you get this?");
//
//            System.out.println("Message sent : " + myObject.getMessage());
//
//            Socket socketToServer = new Socket("127.0.0.1", 3000);
//
//            ObjectOutputStream myOutputStream =
//                    new ObjectOutputStream(socketToServer.getOutputStream());
//
//            ObjectInputStream myInputStream =
//                    new ObjectInputStream(socketToServer.getInputStream());
//
//            myOutputStream.writeObject(myObject);
//
//            myObject = (DataObject) myInputStream.readObject();
//
//            System.out.println("Messaged received : " + myObject.getMessage());
//
//            myOutputStream.close();
//
//            myInputStream.close();
//
//            socketToServer.close();
//
//        } catch (Exception e) {
//            System.out.println(e);
//        }
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


    public class LoginClientPanel extends JPanel implements ActionListener, Runnable{

        final static String USERPANEL = "Member Login";
        final static String ADMINPANEL = "Administrator Login";
        final static int extraWindowWidth = 100;

        JLabel usernameLabel, passwordLabel;
        JTextField usernameText;
        JPasswordField passwordText;
        JButton loginButton;

        JLabel adminUsernameLabel, adminPasswordLabel;
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


            adminUsernameLabel = new JLabel("Enter Admin Username");
            adminPasswordLabel = new JLabel("Enter Admin Password");
            adminUsernameText = new JTextField(20);
            adminPasswordText = new JPasswordField(20);
            adminLoginButton = new JButton("Login");


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
            adminCard.add(adminLoginButton, CENTER_ALIGNMENT);

            tabbedPane.addTab(USERPANEL, memberCard);
            tabbedPane.addTab(ADMINPANEL, adminCard);

            pane.add(tabbedPane, BorderLayout.CENTER);
        }


        PrintWriter pw;
        BufferedReader br;
        Socket s;
        Thread t;
        String temp;

        public LoginClientPanel(){


//            tf = new TextField();
//            tf.addActionListener(this);
//            ta = new TextArea();
//            add(tf, BorderLayout.NORTH);
//            add(ta, BorderLayout.CENTER);
//
//            connect = new Button("Connect");
//            connect.addActionListener(this);
//            Panel p = new Panel();
//            p.add(connect);
//            add(p, BorderLayout.SOUTH);


        }

        public void actionPerformed(ActionEvent ae){
            //when enter key is presses while tf has focus
//            if(ae.getSource() == connect){
//                try{
//                    s = new Socket("127.0.0.1", 3022);
//                    pw = new PrintWriter(s.getOutputStream(), true);
//                    br = new BufferedReader(new InputStreamReader(
//                            s.getInputStream()));
//                }catch(UnknownHostException uhe){
//                    System.out.println(uhe.getMessage());
//                }catch(IOException ioe){
//                    System.out.println(ioe.getMessage());
//                }
//                connect.setEnabled(false);
//
//                t = new Thread(this);
//                t.start();
//            }else if(ae.getSource() == tf){
//                String temp = tf.getText();
//                pw.println(temp);
//                tf.setText("");
//            }
        }

        public void run(){
//            try{
//                while((temp = br.readLine()) != null){
//                    ta.append(temp + "\n");
//                }
//            }catch(IOException ioe){
//                System.out.println(ioe.getMessage());
//            }
        }
    }


    public class LoginButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {


            }
    }
}

