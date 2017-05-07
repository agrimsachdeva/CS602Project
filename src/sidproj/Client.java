package sidproj;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class Client extends Thread implements ActionListener {

    ChatMessage myObject;
    boolean sendingdone = false, receivingdone = false;
    Scanner scan;
    Socket socketToServer;
    ObjectOutputStream myOutputStream;
    ObjectInputStream myInputStream;
    Frame f;
    TextField tf;
    TextArea ta, ta1, ta2;
    Thread listener;
    boolean first = true, kill = false;
    protected String name = "";
    static Button b1, b2;
    MyCanvas canvas;

    public Client() {

        Panel p1 = new Panel();
        //	p1.setLayout(new GridLayout(1,1)); 
        //	p2.setLayout(new GridLayout(2,1));
        ta1 = new TextArea(5, 40);
        //p2.add(ta, BorderLayout.CENTER);
        //f.add(p2);
        b1 = new Button("Connect");
        p1.add(b1);
        b1.addActionListener(this);
        b2 = new Button("Disconnect");
        p1.add(b2);
        b2.addActionListener(this);
        f = new Frame();
        f.setLayout(new GridLayout(3, 4));
        f.setSize(500, 600);
        f.setTitle("Chat Client");
        f.add(p1);

        /*
         * f.addWindowListener(new WindowAdapter(){ public void
         * windowClosing(WindowEvent we){ System.exit(0); } });
         */
        tf = new TextField(20);
        tf.setEditable(false);
        tf.addActionListener(this);
        f.add(tf, BorderLayout.SOUTH);
        ta = new TextArea(5, 40);
        f.add(ta, BorderLayout.CENTER);
        myObject = new ChatMessage();
        ta1 = new TextArea(5, 40);
        f.add(ta1, BorderLayout.CENTER);
        ta2 = new TextArea(5, 40);
        f.add(ta2, BorderLayout.CENTER);
        f.setVisible(true);
        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                kill = true;
                listener = null;
                System.gc();
                System.exit(0);
            }
        });
        canvas = new MyCanvas();
        p1.add(canvas);
    }

    public void actionPerformed(ActionEvent ae) {
        /*
         * myObject = new ChatMessage(); myObject.setMessage(tf.getText());
         * tf.setText(""); try{ myOutputStream.reset();
         * myOutputStream.writeObject(myObject); }catch(IOException ioe){
         * System.out.println(ioe.getMessage()); }
         */

        if (ae.getSource() == b1) {
            try {
                ta.append("Connecting.....");
                socketToServer = new Socket("127.0.0.1", 3796);

                myOutputStream = new ObjectOutputStream(
                        socketToServer.getOutputStream());

                myInputStream = new ObjectInputStream(
                        socketToServer.getInputStream());
                canvas.set(myOutputStream);
                ta.append("Connected!");
                ta.append("\n");
                ta.append("Enter Username");
                ta.append("\n");
                tf.setEditable(true);
                tf.requestFocus();
                listener = new Thread(this);
                listener.start();
                // start();
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        if (ae.getSource() == b2) {
            try {
                myOutputStream.writeObject(myObject);
                myOutputStream.writeObject("");
                myObject.setName(name);
                myObject.setMessage("bye");
                socketToServer.close();
                ta.append("Disconnected");
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        if (ae.getSource() == tf) {
            try {
                if (first) {
                    myObject.setName(tf.getText());
                    name = tf.getText();
                    f.setTitle(tf.getText());
                    myObject.setMessage("HAS ENTERED");
                    first = false;
                }
                else {
                    myObject.setName(name);
                    System.out.println(tf.getText());
                    myObject.setMessage(tf.getText());
                }
                myOutputStream.writeObject(myObject);
                myOutputStream.writeObject(name);
            }
            catch (IOException ex) {
                ex.printStackTrace();
                kill = true;
            }
            tf.setText("");
            return;
        }
        return;

    }

    public void run() {
		System.out.println("Listening for messages from server . . . ");
		try {
			while (!receivingdone) {
			    String chatHistroy = (String) myInputStream.readObject();
			    if(chatHistroy.equals("CANVAS")){
			        String cordinates = (String)myInputStream.readObject();
			        String cordi[] = cordinates.split(",");
			        int x1 = Integer.parseInt(cordi[0]);
			        int y1 = Integer.parseInt(cordi[1]);
			        int x2 = Integer.parseInt(cordi[2]);
			        int y2 = Integer.parseInt(cordi[3]);
			        canvas.draw(x1, y1, x2, y2);
			    }
			    else{
				ArrayList<String> users = (ArrayList<String>) myInputStream.readObject();
				myObject = (ChatMessage) myInputStream.readObject();
				// ta.append(myObject.getMessage() + "\n");
				String line = "";
				if (myObject.getMessage().equals("bye")) {
					line = myObject.getName() + " disconnected";
				} else {
					line = myObject.getName() + ": " + myObject.getMessage();
				}
				displayOnlineUsers(users,ta2);
				ta.append(line + "\n");
				ta1.append(chatHistroy);}
			}
		} catch (IOException ioe) {
			System.out.println("IOE: " + ioe.getMessage());
		} catch (ClassNotFoundException cnf) {
			System.out.println(cnf.getMessage());
		}
	}

    public static void displayOnlineUsers(ArrayList users, TextArea ta) {

        String string = "";
        for (int i = 0; i < users.size(); i++) {
            string = string + users.get(i) + "\n";
        }
        ta.setText(string);
    }

    public static void main(String[] arg) {

        Client c = new Client();

    }

    public class MyCanvas extends Canvas {

        private int lastX = 0, lastY = 0;
        private ObjectOutputStream outputStream;

        public MyCanvas() {
            setBackground(Color.WHITE);
            setSize(300, 300);
            addMouseListener(new PositionRecorder());
            addMouseMotionListener(new LineDrawer());
        }

        public void paint(Graphics g)
        {}

        private class LineDrawer extends MouseMotionAdapter {
            public void mouseDragged(MouseEvent event) {
                int x = event.getX();
                int y = event.getY();
                getGraphics().drawLine(lastX, lastY, x, y);
                try{
                    if(outputStream != null){
                outputStream.writeObject("CANVAS");
                outputStream.writeObject(lastX + "," + lastY + "," + x + "," + y);}}
                catch(IOException e){
                    System.out.println(e.getMessage());
                }
                record(x, y);
            }
        }

        private class PositionRecorder extends MouseAdapter {
            public void mouseEntered(MouseEvent event) {
                record(event.getX(), event.getY());
            }

            public void mousePressed(MouseEvent event) {
                record(event.getX(), event.getY());
            }
        }

        public void draw(int x1, int y1, int x2, int y2) {
            getGraphics().drawLine(x1, y1, x2, y2);
            record(x2, y2);
        }

        protected void record(int x, int y) {
            lastX = x;
            lastY = y;
        }

        public void set(ObjectOutputStream objectOutputStream) {
            outputStream = objectOutputStream;
        }
    }
}
