package echo;

import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;

public class EchoClientFrame extends Frame{
	public EchoClientFrame(){
		setSize(500,500);
		setTitle("My Test Frame");
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent we){
				System.exit(0);
			}		
		});
		MyClientPanel mcp = new MyClientPanel();
		add(mcp, BorderLayout.CENTER);
		setVisible(true);
	}
	public static void main(String[] args){
		new EchoClientFrame();
	}
}

class MyClientPanel extends Panel implements ActionListener, Runnable{
	TextField tf;
	TextArea ta;
	Button connect;
	PrintWriter pw;
	BufferedReader br;
	Socket s;
	Thread t;
	String temp;
	
	public MyClientPanel(){
		setLayout(new BorderLayout());
		tf = new TextField();
		tf.addActionListener(this);
		ta = new TextArea();
		add(tf, BorderLayout.NORTH);
		add(ta, BorderLayout.CENTER);
		
		connect = new Button("Connect");
		connect.addActionListener(this);
		Panel p = new Panel();
		p.add(connect);
		add(p, BorderLayout.SOUTH);
		

	}

	public void actionPerformed(ActionEvent ae){
		//when enter key is presses while tf has focus
		if(ae.getSource() == connect){
			try{
				s = new Socket("127.0.0.1", 3022);
				pw = new PrintWriter(s.getOutputStream(), true);
				br = new BufferedReader(new InputStreamReader(
					s.getInputStream()));
			}catch(UnknownHostException uhe){
				System.out.println(uhe.getMessage());
			}catch(IOException ioe){
				System.out.println(ioe.getMessage());
			}
			connect.setEnabled(false);
			
			t = new Thread(this);
			t.start();
		}else if(ae.getSource() == tf){
			String temp = tf.getText();
			pw.println(temp);
			tf.setText("");
		}
	}
	
	public void run(){
		try{
			while((temp = br.readLine()) != null){
				ta.append(temp + "\n");
			}
		}catch(IOException ioe){
			System.out.println(ioe.getMessage());
		}
	}
}