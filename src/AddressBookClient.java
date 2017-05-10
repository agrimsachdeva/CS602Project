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

    public void startMemberFrame(ArrayList userList, String user) {
        //Create and set up the window.
        JFrame frame = new JFrame("Member Portal");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        MemberPortalPanel mpp = new MemberPortalPanel(frame.getContentPane(), userList, user);

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
        final static String ADDPANEL = "Add A Record";
        final static String ADDMEMBER = "Add A Member";
        final static String UPDATEMEMBER = "Update Any Record By Name";

        final static int extraWindowWidth = 100;

        JTable table;
        JScrollPane scrollPane;
        JButton refreshButton;
        JButton searchButton;
        JTextField searchTerm;


        JLabel nameLabel, addressLabel, emailLabel, phoneLabel, updateLabel;
        JTextField nameText, addressText, emailText, phoneText;
        JButton updateButton;

        JLabel addMemberNameLabel, addMemberPasswordLabel, statusLabel;
        JTextField addMemberNameText, addMemberPasswordText;
        JButton addMember;

        JLabel nameLabel1, addressLabel1, emailLabel1, phoneLabel1, updateLabel1;
        JTextField nameText1, addressText1, emailText1, phoneText1;
        JButton updateButton1;


        public AdminPortalPanel(Container pane, ArrayList<UserObject> userList) {
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
            refreshButton = new JButton("Refresh");
            refreshButton.addActionListener(new RefreshButtonListener());


            searchTerm = new JTextField("", 10);
            searchButton = new JButton("Search");

            searchButton.addActionListener(new SearchButtonListener());

            layout.setHorizontalGroup(
                    layout.createSequentialGroup()
                            .addGroup(layout.createSequentialGroup()
                                    .addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                    )
                            .addGroup(layout.createSequentialGroup()
                            .addComponent(refreshButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addComponent(searchTerm, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addComponent(searchButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                    )
            );
            layout.setVerticalGroup(
                    layout.createSequentialGroup()
                            .addGroup(layout.createSequentialGroup()
                                    .addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                    )
                            .addGroup(layout.createSequentialGroup()
                                    .addComponent(refreshButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(searchTerm, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(searchButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            )
            );


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

            GridBagLayout panelGridBagLayout = new GridBagLayout();
            panelGridBagLayout.columnWidths = new int[]{86, 86, 0};
            panelGridBagLayout.rowHeights = new int[]{20, 20, 20, 20, 20, 0};
            panelGridBagLayout.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
            panelGridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
            updateCard.setLayout(panelGridBagLayout);


            //LABEL AND FIELD FOR NAME
            nameLabel = new JLabel("Name");
            GridBagConstraints gridBagConstraintForNameLabel = new GridBagConstraints();
            gridBagConstraintForNameLabel.fill = GridBagConstraints.BOTH;
            gridBagConstraintForNameLabel.insets = new Insets(0, 0, 5, 5);
            gridBagConstraintForNameLabel.gridx = 0;
            gridBagConstraintForNameLabel.gridy = 0;
            updateCard.add(nameLabel, gridBagConstraintForNameLabel);

            nameText = new JTextField();
            GridBagConstraints gridBagConstraintForNameTextField = new GridBagConstraints();
            gridBagConstraintForNameTextField.fill = GridBagConstraints.BOTH;
            gridBagConstraintForNameTextField.insets = new Insets(0, 0, 5, 0);
            gridBagConstraintForNameTextField.gridx = 1;
            gridBagConstraintForNameTextField.gridy = 0;
            updateCard.add(nameText, gridBagConstraintForNameTextField);
            nameText.setColumns(10);

            //LABEL AND FIELD FOR ADDRESS
            addressLabel = new JLabel("Address");
            GridBagConstraints gridBagConstraintForAddressLabel = new GridBagConstraints();
            gridBagConstraintForAddressLabel.fill = GridBagConstraints.BOTH;
            gridBagConstraintForAddressLabel.insets = new Insets(0, 0, 5, 5);
            gridBagConstraintForAddressLabel.gridx = 0;
            gridBagConstraintForAddressLabel.gridy = 1;
            updateCard.add(addressLabel, gridBagConstraintForAddressLabel);

            addressText = new JTextField();
            GridBagConstraints gridBagConstraintForAddressTextField = new GridBagConstraints();
            gridBagConstraintForAddressTextField.fill = GridBagConstraints.BOTH;
            gridBagConstraintForAddressTextField.insets = new Insets(0, 0, 5, 0);
            gridBagConstraintForAddressTextField.gridx = 1;
            gridBagConstraintForAddressTextField.gridy = 1;
            updateCard.add(addressText, gridBagConstraintForAddressTextField);
            addressText.setColumns(10);

            //LABEL AND FIELD FOR EMAIL
            emailLabel = new JLabel("EMail");
            GridBagConstraints gridBagConstraintForEMailLabel = new GridBagConstraints();
            gridBagConstraintForEMailLabel.fill = GridBagConstraints.BOTH;
            gridBagConstraintForEMailLabel.insets = new Insets(0, 0, 5, 5);
            gridBagConstraintForEMailLabel.gridx = 0;
            gridBagConstraintForEMailLabel.gridy = 2;
            updateCard.add(emailLabel, gridBagConstraintForEMailLabel);

            emailText = new JTextField();
            GridBagConstraints gridBagConstraintForEMailTextField = new GridBagConstraints();
            gridBagConstraintForEMailTextField.fill = GridBagConstraints.BOTH;
            gridBagConstraintForEMailTextField.insets = new Insets(0, 0, 5, 0);
            gridBagConstraintForEMailTextField.gridx = 1;
            gridBagConstraintForEMailTextField.gridy = 2;
            updateCard.add(emailText, gridBagConstraintForEMailTextField);
            emailText.setColumns(10);

            //LABEL AND FIELD FOR PHONE
            phoneLabel = new JLabel("Phone");
            GridBagConstraints gridBagConstraintForPhoneLabel = new GridBagConstraints();
            gridBagConstraintForPhoneLabel.fill = GridBagConstraints.BOTH;
            gridBagConstraintForPhoneLabel.insets = new Insets(0, 0, 5, 5);
            gridBagConstraintForPhoneLabel.gridx = 0;
            gridBagConstraintForPhoneLabel.gridy = 3;
            updateCard.add(phoneLabel, gridBagConstraintForPhoneLabel);

            phoneText = new JTextField();
            GridBagConstraints gridBagConstraintForPhoneTextField = new GridBagConstraints();
            gridBagConstraintForPhoneTextField.fill = GridBagConstraints.BOTH;
            gridBagConstraintForPhoneTextField.insets = new Insets(0, 0, 5, 0);
            gridBagConstraintForPhoneTextField.gridx = 1;
            gridBagConstraintForPhoneTextField.gridy = 3;
            updateCard.add(phoneText, gridBagConstraintForPhoneTextField);
            phoneText.setColumns(10);

            //LABEL AND FIELD FOR ADD RECORD BUTTON
            updateButton = new JButton("Add A Record");

            updateCard.add(updateButton);

            updateButton.addActionListener(new UpdateButtonListener());

            //LABEL AND FIELD FOR ADD RECORD STATUS
            updateLabel = new JLabel("*****status******");
            GridBagConstraints gridBagConstraintForUpdateLabel = new GridBagConstraints();
            gridBagConstraintForUpdateLabel.fill = GridBagConstraints.BOTH;
            gridBagConstraintForUpdateLabel.insets = new Insets(0, 0, 5, 5);
            gridBagConstraintForUpdateLabel.gridx = 0;
            gridBagConstraintForUpdateLabel.gridy = 5;
            updateCard.add(updateLabel, gridBagConstraintForUpdateLabel);

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

            addMemberCard.setLayout(panelGridBagLayout);

            //LABEL AND FIELD FOR MEMBER USERNAME
            addMemberNameLabel = new JLabel("New Member Username");
            GridBagConstraints gridBagConstraintForNewMemberUserName = new GridBagConstraints();
            gridBagConstraintForNewMemberUserName.fill = GridBagConstraints.BOTH;
            gridBagConstraintForNewMemberUserName.insets = new Insets(0, 0, 5, 5);
            gridBagConstraintForNewMemberUserName.gridx = 0;
            gridBagConstraintForNewMemberUserName.gridy = 0;
            addMemberCard.add(addMemberNameLabel, gridBagConstraintForNewMemberUserName);

            addMemberNameText = new JTextField();
            GridBagConstraints gridBagConstraintForNewMemberTextField = new GridBagConstraints();
            gridBagConstraintForNewMemberTextField.fill = GridBagConstraints.BOTH;
            gridBagConstraintForNewMemberTextField.insets = new Insets(0, 0, 5, 0);
            gridBagConstraintForNewMemberTextField.gridx = 1;
            gridBagConstraintForNewMemberTextField.gridy = 0;
            addMemberCard.add(addMemberNameText, gridBagConstraintForNewMemberTextField);
            addMemberNameText.setColumns(10);

            //LABEL AND FIELD FOR MEMBER PASSWORD
            addMemberPasswordLabel = new JLabel("New Member Password");
            GridBagConstraints gridBagConstraintForNewMemberPassword = new GridBagConstraints();
            gridBagConstraintForNewMemberPassword.fill = GridBagConstraints.BOTH;
            gridBagConstraintForNewMemberPassword.insets = new Insets(0, 0, 5, 5);
            gridBagConstraintForNewMemberPassword.gridx = 0;
            gridBagConstraintForNewMemberPassword.gridy = 1;
            addMemberCard.add(addMemberPasswordLabel, gridBagConstraintForNewMemberPassword);

            addMemberPasswordText = new JTextField();
            GridBagConstraints gridBagConstraintForNewMemberPasswordTextField = new GridBagConstraints();
            gridBagConstraintForNewMemberPasswordTextField.fill = GridBagConstraints.BOTH;
            gridBagConstraintForNewMemberPasswordTextField.insets = new Insets(0, 0, 5, 0);
            gridBagConstraintForNewMemberPasswordTextField.gridx = 1;
            gridBagConstraintForNewMemberPasswordTextField.gridy = 1;
            addMemberCard.add(addMemberPasswordText, gridBagConstraintForNewMemberPasswordTextField);
            addMemberPasswordText.setColumns(10);

            //LABEL AND FIELD FOR ADD MEMBER STATUS
            statusLabel = new JLabel("*****status******");
            GridBagConstraints gridBagConstraintForAddLabel = new GridBagConstraints();
            gridBagConstraintForAddLabel.fill = GridBagConstraints.BOTH;
            gridBagConstraintForAddLabel.insets = new Insets(0, 0, 5, 5);
            gridBagConstraintForAddLabel.gridx = 0;
            gridBagConstraintForAddLabel.gridy = 5;
            addMemberCard.add(statusLabel, gridBagConstraintForAddLabel);

            addMember = new JButton("Add New Member");

            addMemberCard.add(addMember);

            addMember.addActionListener(new AddButtonListener());


            JPanel updateCard1 = new JPanel() {
                //Make the panel wider than it really needs, so
                //the window's wide enough for the tabs to stay
                //in one row.
                public Dimension getPreferredSize() {
                    Dimension size = super.getPreferredSize();
                    size.width += extraWindowWidth;
                    return size;
                }
            };

            GridBagLayout panelGridBagLayout1 = new GridBagLayout();
            panelGridBagLayout1.columnWidths = new int[]{86, 86, 0};
            panelGridBagLayout1.rowHeights = new int[]{20, 20, 20, 20, 20, 0};
            panelGridBagLayout1.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
            panelGridBagLayout1.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
            updateCard1.setLayout(panelGridBagLayout1);


            //LABEL AND FIELD FOR NAME
            nameLabel1 = new JLabel("Name");
            GridBagConstraints gridBagConstraintForNameLabel1 = new GridBagConstraints();
            gridBagConstraintForNameLabel1.fill = GridBagConstraints.BOTH;
            gridBagConstraintForNameLabel1.insets = new Insets(0, 0, 5, 5);
            gridBagConstraintForNameLabel1.gridx = 0;
            gridBagConstraintForNameLabel1.gridy = 0;
            updateCard1.add(nameLabel1, gridBagConstraintForNameLabel1);

            nameText1 = new JTextField();
            GridBagConstraints gridBagConstraintForAddressTextField1 = new GridBagConstraints();
            gridBagConstraintForAddressTextField1.fill = GridBagConstraints.BOTH;
            gridBagConstraintForAddressTextField1.insets = new Insets(0, 0, 5, 0);
            gridBagConstraintForAddressTextField1.gridx = 1;
            gridBagConstraintForAddressTextField1.gridy = 0;
            updateCard1.add(nameText1, gridBagConstraintForAddressTextField1);
            nameText1.setColumns(10);


            //LABEL AND FIELD FOR ADDRESS
            addressLabel1 = new JLabel("Address");
            GridBagConstraints gridBagConstraintForAddressLabel1 = new GridBagConstraints();
            gridBagConstraintForAddressLabel1.fill = GridBagConstraints.BOTH;
            gridBagConstraintForAddressLabel1.insets = new Insets(0, 0, 5, 5);
            gridBagConstraintForAddressLabel1.gridx = 0;
            gridBagConstraintForAddressLabel1.gridy = 1;
            updateCard1.add(addressLabel1, gridBagConstraintForAddressLabel1);

            addressText1 = new JTextField();
            GridBagConstraints gridBagConstraintForAddressTextField2 = new GridBagConstraints();
            gridBagConstraintForAddressTextField2.fill = GridBagConstraints.BOTH;
            gridBagConstraintForAddressTextField2.insets = new Insets(0, 0, 5, 0);
            gridBagConstraintForAddressTextField2.gridx = 1;
            gridBagConstraintForAddressTextField2.gridy = 1;
            updateCard1.add(addressText1, gridBagConstraintForAddressTextField2);
            addressText1.setColumns(10);

            //LABEL AND FIELD FOR EMAIL
            emailLabel1 = new JLabel("EMail");
            GridBagConstraints gridBagConstraintForEMailLabel1 = new GridBagConstraints();
            gridBagConstraintForEMailLabel1.fill = GridBagConstraints.BOTH;
            gridBagConstraintForEMailLabel1.insets = new Insets(0, 0, 5, 5);
            gridBagConstraintForEMailLabel1.gridx = 0;
            gridBagConstraintForEMailLabel1.gridy = 2;
            updateCard1.add(emailLabel1, gridBagConstraintForEMailLabel1);

            emailText1 = new JTextField();
            GridBagConstraints gridBagConstraintForEMailTextField1 = new GridBagConstraints();
            gridBagConstraintForEMailTextField1.fill = GridBagConstraints.BOTH;
            gridBagConstraintForEMailTextField1.insets = new Insets(0, 0, 5, 0);
            gridBagConstraintForEMailTextField1.gridx = 1;
            gridBagConstraintForEMailTextField1.gridy = 2;
            updateCard1.add(emailText1, gridBagConstraintForEMailTextField1);
            emailText1.setColumns(10);

            //LABEL AND FIELD FOR PHONE
            phoneLabel1 = new JLabel("Phone");
            GridBagConstraints gridBagConstraintForPhoneLabel1 = new GridBagConstraints();
            gridBagConstraintForPhoneLabel1.fill = GridBagConstraints.BOTH;
            gridBagConstraintForPhoneLabel1.insets = new Insets(0, 0, 5, 5);
            gridBagConstraintForPhoneLabel1.gridx = 0;
            gridBagConstraintForPhoneLabel1.gridy = 3;
            updateCard1.add(phoneLabel1, gridBagConstraintForPhoneLabel1);

            phoneText1 = new JTextField();
            GridBagConstraints gridBagConstraintForPhoneTextField1 = new GridBagConstraints();
            gridBagConstraintForPhoneTextField1.fill = GridBagConstraints.BOTH;
            gridBagConstraintForPhoneTextField1.insets = new Insets(0, 0, 5, 0);
            gridBagConstraintForPhoneTextField1.gridx = 1;
            gridBagConstraintForPhoneTextField1.gridy = 3;
            updateCard1.add(phoneText1, gridBagConstraintForPhoneTextField1);
            phoneText1.setColumns(10);

            //LABEL AND FIELD FOR ADD RECORD BUTTON
            updateButton1 = new JButton("Update Matching Record");

            updateCard1.add(updateButton1);

            updateButton1.addActionListener(new UpdateAnyButtonListener());

            //LABEL AND FIELD FOR ADD RECORD STATUS
            updateLabel1 = new JLabel("*****status******");
            GridBagConstraints gridBagConstraintForUpdateLabel1 = new GridBagConstraints();
            gridBagConstraintForUpdateLabel1.fill = GridBagConstraints.BOTH;
            gridBagConstraintForUpdateLabel1.insets = new Insets(0, 0, 5, 5);
            gridBagConstraintForUpdateLabel1.gridx = 0;
            gridBagConstraintForUpdateLabel1.gridy = 5;
            updateCard1.add(updateLabel1, gridBagConstraintForUpdateLabel1);



            tabbedPane.addTab(RECORDSPANEL, memberCard);
            tabbedPane.addTab(ADDPANEL, updateCard);
            tabbedPane.addTab(ADDMEMBER, addMemberCard);
            tabbedPane.addTab(UPDATEMEMBER, updateCard1);

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

        public void clearTable(JTable table) {
            DefaultTableModel dtm = (DefaultTableModel) table.getModel();
            dtm.setRowCount(0);
        }

        public class SearchButtonListener implements ActionListener, Runnable {

            Thread t;
            ObjectOutputStream myOutputStream;
            ObjectInputStream myInputStream;
            LoginObject readLoginObject;

            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    LoginObject loginObject = new LoginObject();

                    loginObject.setMessage("Search");

                    loginObject.setSearchTerm(searchTerm.getText());

                    Socket socketToServer = new Socket("127.0.0.1", 3000);

                    myOutputStream =
                            new ObjectOutputStream(socketToServer.getOutputStream());

                    myInputStream =
                            new ObjectInputStream(socketToServer.getInputStream());

                    myOutputStream.writeObject(loginObject);

                    readLoginObject = (LoginObject) myInputStream.readObject();

                    if (readLoginObject.getMessage().equalsIgnoreCase("Found")) {
                        System.out.println("Found");

                        clearTable(table);
                        addRowToTable(readLoginObject.getUserList());
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

                t = new Thread(this);
                t.start();
            }

            @Override
            public void run() {
                try {
                    while (!(readLoginObject.getMessage()).equalsIgnoreCase("Success")) {
                    }
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }

        public class RefreshButtonListener implements ActionListener, Runnable {

            Thread t;
            ObjectOutputStream myOutputStream;
            ObjectInputStream myInputStream;
            LoginObject readLoginObject;

            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    LoginObject loginObject = new LoginObject();

                    loginObject.setMessage("Refresh");

                    Socket socketToServer = new Socket("127.0.0.1", 3000);

                    myOutputStream =
                            new ObjectOutputStream(socketToServer.getOutputStream());

                    myInputStream =
                            new ObjectInputStream(socketToServer.getInputStream());

                    myOutputStream.writeObject(loginObject);

                    readLoginObject = (LoginObject) myInputStream.readObject();

                    if (readLoginObject.getMessage().equalsIgnoreCase("Success")) {
                        System.out.println("Refreshed");

                        clearTable(table);
                        addRowToTable(readLoginObject.getUserList());
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

                t = new Thread(this);
                t.start();
            }

            @Override
            public void run() {
                try {
                    while (!(readLoginObject.getMessage()).equalsIgnoreCase("Success")) {
                    }
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }

        public class AddButtonListener implements ActionListener, Runnable {

            Thread t;
            ObjectOutputStream myOutputStream;
            ObjectInputStream myInputStream;
            MemberObject readMemberObject;

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    MemberObject memberObject = new MemberObject();

                    memberObject.setMessage("AddMember");

                    System.out.println(memberObject.getMessage());

                    memberObject.setUsername(addMemberNameText.getText().trim());
                    memberObject.setPassword(addMemberPasswordText.getText().trim());

                    Socket socketToServer = new Socket("127.0.0.1", 3000);

                    myOutputStream =
                            new ObjectOutputStream(socketToServer.getOutputStream());

                    myInputStream =
                            new ObjectInputStream(socketToServer.getInputStream());

                    myOutputStream.writeObject(memberObject);

                    readMemberObject = (MemberObject) myInputStream.readObject();

                    if (readMemberObject.getMessage().equalsIgnoreCase("Added")) {
                        System.out.println("Updated Successfully");
                        statusLabel.setText("Updated Successfully");
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


                t = new Thread(this);
                t.start();
            }

            @Override
            public void run() {
                try {
                    while (!(readMemberObject.getMessage()).equalsIgnoreCase("Added")) {
                        statusLabel.setText("*****status*****");
                    }
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }

        public class UpdateButtonListener implements ActionListener, Runnable {

            Thread t;
            ObjectOutputStream myOutputStream;
            ObjectInputStream myInputStream;
            UserObject readUserObject;

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    UserObject userObject = new UserObject();

                    userObject.setMessage("AddRecord");

                    System.out.println(userObject.getMessage());

                    userObject.setName(nameText.getText().trim());
                    userObject.setAddress(addressText.getText().trim());
                    userObject.setEmail(emailText.getText().trim());
                    userObject.setPhone(phoneText.getText().trim());

                    Socket socketToServer = new Socket("127.0.0.1", 3000);

                    myOutputStream =
                            new ObjectOutputStream(socketToServer.getOutputStream());

                    myInputStream =
                            new ObjectInputStream(socketToServer.getInputStream());

                    myOutputStream.writeObject(userObject);

                    readUserObject = (UserObject) myInputStream.readObject();

                    if (readUserObject.getMessage().equalsIgnoreCase("Added")) {
                        System.out.println("Updated Successfully");
                        updateLabel.setText("Updated Successfully");
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


                t = new Thread(this);
                t.start();
            }

            @Override
            public void run() {
                try {
                    while (!(readUserObject.getMessage()).equalsIgnoreCase("Added")) {
                        updateLabel.setText("*****status*****");
                    }
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }

        public class UpdateAnyButtonListener implements ActionListener, Runnable {

            Thread t;
            ObjectOutputStream myOutputStream;
            ObjectInputStream myInputStream;
            UserObject readUserObject;

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    UserObject userObject = new UserObject();

                    userObject.setMessage("EditAnyRecord");

                    System.out.println(userObject.getMessage());

                    userObject.setName(nameText1.getText().trim());
                    userObject.setAddress(addressText1.getText().trim());
                    userObject.setEmail(emailText1.getText().trim());
                    userObject.setPhone(phoneText1.getText().trim());

                    Socket socketToServer = new Socket("127.0.0.1", 3000);

                    myOutputStream =
                            new ObjectOutputStream(socketToServer.getOutputStream());

                    myInputStream =
                            new ObjectInputStream(socketToServer.getInputStream());

                    myOutputStream.writeObject(userObject);

                    readUserObject = (UserObject) myInputStream.readObject();

                    if (readUserObject.getMessage().equalsIgnoreCase("Added")) {
                        System.out.println("Updated Successfully");
                        updateLabel1.setText("Updated Successfully");
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


                t = new Thread(this);
                t.start();
            }

            @Override
            public void run() {
                try {
                    while (!(readUserObject.getMessage()).equalsIgnoreCase("Added")) {
                        updateLabel1.setText("*****status*****");
                    }
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }
    }

    public class MemberPortalPanel extends JPanel {

        String user;

        final static String RECORDSPANEL = "Member Records";
        final static String UPDATEMEMBER = "Update Your Record";
        final static int extraWindowWidth = 100;

        JLabel nameLabel, addressLabel, emailLabel, phoneLabel, updateLabel;
        JTextField addressText, emailText, phoneText;
        JButton updateButton;

        JTable table;
        JScrollPane scrollPane;

        JButton searchButton;
        JTextField searchTerm;
        JButton refreshButton;

        public MemberPortalPanel(Container pane, ArrayList<UserObject> userList, String user) {
            this.user = user;

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

            refreshButton = new JButton("Refresh");
            refreshButton.addActionListener(new RefreshButtonListener());

            searchTerm = new JTextField("", 10);
            searchButton = new JButton("Search");

            searchButton.addActionListener(new SearchButtonListener());

            layout.setHorizontalGroup(
                    layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                    .addGap(32, 32, 32)
                                    .addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(refreshButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(searchTerm, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(searchButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                    .addContainerGap(61, Short.MAX_VALUE))
            );
            layout.setVerticalGroup(
                    layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                    .addContainerGap()
                                    .addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(refreshButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(searchTerm, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(searchButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                    .addContainerGap(14, Short.MAX_VALUE))
            );


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

            GridBagLayout panelGridBagLayout = new GridBagLayout();
            panelGridBagLayout.columnWidths = new int[]{86, 86, 0};
            panelGridBagLayout.rowHeights = new int[]{20, 20, 20, 20, 20, 0};
            panelGridBagLayout.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
            panelGridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
            updateCard.setLayout(panelGridBagLayout);


            //LABEL AND FIELD FOR NAME
            nameLabel = new JLabel(user);
            GridBagConstraints gridBagConstraintForNameLabel = new GridBagConstraints();
            gridBagConstraintForNameLabel.fill = GridBagConstraints.BOTH;
            gridBagConstraintForNameLabel.insets = new Insets(0, 0, 5, 5);
            gridBagConstraintForNameLabel.gridx = 0;
            gridBagConstraintForNameLabel.gridy = 0;
            updateCard.add(nameLabel, gridBagConstraintForNameLabel);


            //LABEL AND FIELD FOR ADDRESS
            addressLabel = new JLabel("Address");
            GridBagConstraints gridBagConstraintForAddressLabel = new GridBagConstraints();
            gridBagConstraintForAddressLabel.fill = GridBagConstraints.BOTH;
            gridBagConstraintForAddressLabel.insets = new Insets(0, 0, 5, 5);
            gridBagConstraintForAddressLabel.gridx = 0;
            gridBagConstraintForAddressLabel.gridy = 1;
            updateCard.add(addressLabel, gridBagConstraintForAddressLabel);

            addressText = new JTextField();
            GridBagConstraints gridBagConstraintForAddressTextField = new GridBagConstraints();
            gridBagConstraintForAddressTextField.fill = GridBagConstraints.BOTH;
            gridBagConstraintForAddressTextField.insets = new Insets(0, 0, 5, 0);
            gridBagConstraintForAddressTextField.gridx = 1;
            gridBagConstraintForAddressTextField.gridy = 1;
            updateCard.add(addressText, gridBagConstraintForAddressTextField);
            addressText.setColumns(10);

            //LABEL AND FIELD FOR EMAIL
            emailLabel = new JLabel("EMail");
            GridBagConstraints gridBagConstraintForEMailLabel = new GridBagConstraints();
            gridBagConstraintForEMailLabel.fill = GridBagConstraints.BOTH;
            gridBagConstraintForEMailLabel.insets = new Insets(0, 0, 5, 5);
            gridBagConstraintForEMailLabel.gridx = 0;
            gridBagConstraintForEMailLabel.gridy = 2;
            updateCard.add(emailLabel, gridBagConstraintForEMailLabel);

            emailText = new JTextField();
            GridBagConstraints gridBagConstraintForEMailTextField = new GridBagConstraints();
            gridBagConstraintForEMailTextField.fill = GridBagConstraints.BOTH;
            gridBagConstraintForEMailTextField.insets = new Insets(0, 0, 5, 0);
            gridBagConstraintForEMailTextField.gridx = 1;
            gridBagConstraintForEMailTextField.gridy = 2;
            updateCard.add(emailText, gridBagConstraintForEMailTextField);
            emailText.setColumns(10);

            //LABEL AND FIELD FOR PHONE
            phoneLabel = new JLabel("Phone");
            GridBagConstraints gridBagConstraintForPhoneLabel = new GridBagConstraints();
            gridBagConstraintForPhoneLabel.fill = GridBagConstraints.BOTH;
            gridBagConstraintForPhoneLabel.insets = new Insets(0, 0, 5, 5);
            gridBagConstraintForPhoneLabel.gridx = 0;
            gridBagConstraintForPhoneLabel.gridy = 3;
            updateCard.add(phoneLabel, gridBagConstraintForPhoneLabel);

            phoneText = new JTextField();
            GridBagConstraints gridBagConstraintForPhoneTextField = new GridBagConstraints();
            gridBagConstraintForPhoneTextField.fill = GridBagConstraints.BOTH;
            gridBagConstraintForPhoneTextField.insets = new Insets(0, 0, 5, 0);
            gridBagConstraintForPhoneTextField.gridx = 1;
            gridBagConstraintForPhoneTextField.gridy = 3;
            updateCard.add(phoneText, gridBagConstraintForPhoneTextField);
            phoneText.setColumns(10);

            //LABEL AND FIELD FOR ADD RECORD BUTTON
            updateButton = new JButton("Update Your Record");

            updateCard.add(updateButton);

            updateButton.addActionListener(new UpdateButtonListener());

            //LABEL AND FIELD FOR ADD RECORD STATUS
            updateLabel = new JLabel("*****status******");
            GridBagConstraints gridBagConstraintForUpdateLabel = new GridBagConstraints();
            gridBagConstraintForUpdateLabel.fill = GridBagConstraints.BOTH;
            gridBagConstraintForUpdateLabel.insets = new Insets(0, 0, 5, 5);
            gridBagConstraintForUpdateLabel.gridx = 0;
            gridBagConstraintForUpdateLabel.gridy = 5;
            updateCard.add(updateLabel, gridBagConstraintForUpdateLabel);



            tabbedPane.addTab(RECORDSPANEL, memberCard);
            tabbedPane.addTab(UPDATEMEMBER, updateCard);

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

        public void clearTable(JTable table) {
            DefaultTableModel dtm = (DefaultTableModel) table.getModel();
            dtm.setRowCount(0);
        }

        public class SearchButtonListener implements ActionListener, Runnable {

            Thread t;
            ObjectOutputStream myOutputStream;
            ObjectInputStream myInputStream;
            LoginObject readLoginObject;

            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    LoginObject loginObject = new LoginObject();

                    loginObject.setMessage("Search");

                    loginObject.setSearchTerm(searchTerm.getText());

                    Socket socketToServer = new Socket("127.0.0.1", 3000);

                    myOutputStream =
                            new ObjectOutputStream(socketToServer.getOutputStream());

                    myInputStream =
                            new ObjectInputStream(socketToServer.getInputStream());

                    myOutputStream.writeObject(loginObject);

                    readLoginObject = (LoginObject) myInputStream.readObject();

                    if (readLoginObject.getMessage().equalsIgnoreCase("Found")) {
                        System.out.println("Found");

                        clearTable(table);
                        addRowToTable(readLoginObject.getUserList());
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

                t = new Thread(this);
                t.start();
            }

            @Override
            public void run() {
                try {
                    while (!(readLoginObject.getMessage()).equalsIgnoreCase("Success")) {
                    }
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }

        public class RefreshButtonListener implements ActionListener, Runnable {

            Thread t;
            ObjectOutputStream myOutputStream;
            ObjectInputStream myInputStream;
            LoginObject readLoginObject;

            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    LoginObject loginObject = new LoginObject();

                    loginObject.setMessage("Refresh");


                    Socket socketToServer = new Socket("127.0.0.1", 3000);

                    myOutputStream =
                            new ObjectOutputStream(socketToServer.getOutputStream());

                    myInputStream =
                            new ObjectInputStream(socketToServer.getInputStream());

                    myOutputStream.writeObject(loginObject);

                    readLoginObject = (LoginObject) myInputStream.readObject();

                    if (readLoginObject.getMessage().equalsIgnoreCase("Success")) {
                        System.out.println("Refreshed");

                        clearTable(table);
                        addRowToTable(readLoginObject.getUserList());
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

                t = new Thread(this);
                t.start();
            }

            @Override
            public void run() {
                try {
                    while (!(readLoginObject.getMessage()).equalsIgnoreCase("Success")) {
                    }
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }

        public class UpdateButtonListener implements ActionListener, Runnable {

            Thread t;
            ObjectOutputStream myOutputStream;
            ObjectInputStream myInputStream;
            UserObject readUserObject;

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    UserObject userObject = new UserObject();

                    userObject.setMessage("UpdateRecord");

                    System.out.println(userObject.getMessage());

                    userObject.setName(user);
                    userObject.setAddress(addressText.getText().trim());
                    userObject.setEmail(emailText.getText().trim());
                    userObject.setPhone(phoneText.getText().trim());

                    Socket socketToServer = new Socket("127.0.0.1", 3000);

                    myOutputStream =
                            new ObjectOutputStream(socketToServer.getOutputStream());

                    myInputStream =
                            new ObjectInputStream(socketToServer.getInputStream());

                    myOutputStream.writeObject(userObject);

                    readUserObject = (UserObject) myInputStream.readObject();

                    if (readUserObject.getMessage().equalsIgnoreCase("Updated")) {
                        System.out.println("Updated Successfully");
                        updateLabel.setText("Updated Successfully");
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


                t = new Thread(this);
                t.start();
            }

            @Override
            public void run() {
                try {
                    while (!(readUserObject.getMessage()).equalsIgnoreCase("Added")) {
                        updateLabel.setText("Updated Successfully");
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
                        String user = readLoginObject.getUsername();

                        startMemberFrame(userList, user);
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

                loginButton.setEnabled(true);

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

                adminLoginButton.setEnabled(true);

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


