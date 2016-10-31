

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import javax.swing.*;


public class ChatApp extends DoodlePad {
   JTextArea incoming;
   JTextField outgoing,name,ip;
   String iptext;
   boolean typing;
   JButton connectButton;
   JScrollPane qscroller;
   BufferedReader reader;
   PrintWriter writer;
   public Socket sock;
   public boolean connected;
   public void go(){
       name = new JTextField("Name",15);
       ip = new JTextField("IP",15);
       connectButton = new JButton("Connect");
       JFrame frame = new JFrame("Better Than Edgar's App");
       JPanel mainPanel = new JPanel();
       JPanel panel1 = new JPanel();
       incoming = new JTextArea(15,50);
       incoming.setLineWrap(true);
       incoming.setWrapStyleWord(true);
       incoming.setEditable(false);
       qscroller = new JScrollPane(incoming);
       qscroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
       qscroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
       outgoing = new JTextField(20);
       JButton sendButton = new JButton("Send");
       sendButton.addActionListener(new B());
//       outgoing.addKeyListener(new C());
       connectButton.addActionListener(new Networking());
       mainPanel.add(qscroller);
       mainPanel.add(outgoing);
       mainPanel.add(sendButton);
       frame.getContentPane().add(BorderLayout.NORTH,panel1);
       frame.getContentPane().add(BorderLayout.CENTER,mainPanel);
       //Networking();
       panel1.add(name);
       panel1.add(ip);
       panel1.add(connectButton);
       Thread readerThread = new Thread(new IncomingReader());
       readerThread.start();
       frame.setSize(600,600);
       frame.setVisible(true);
       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       frame.getRootPane().setDefaultButton(sendButton);
       
   }
     class Networking implements ActionListener{
    	 @SuppressWarnings("deprecation")
		public void actionPerformed(ActionEvent ev){
       try{
            sock = new Socket(ip.getText().toString(),5000);            
            InputStreamReader streamReader = new InputStreamReader(sock.getInputStream());
            reader = new BufferedReader(streamReader);
            writer = new PrintWriter(sock.getOutputStream());
            incoming.append("Connected successfully!!!!!!!\n Enter Your name and start chatting \n");
            connectButton.hide();
            ip.hide();
            
            
        }
        catch(Exception ex){
            System.out.println("Nothing");
           incoming.append("Please Enter A Valid I.P\n");
        }
    	 }
   }
   class B implements ActionListener{
    public void actionPerformed(ActionEvent ev) {
        writer.println(name.getText()+ ":" + outgoing.getText());
        writer.flush();
        incoming.append(name.getText()+ ":" + outgoing.getText()+"\n");
        outgoing.setText("");
        outgoing.requestFocus();
    }
    }
   /*class C implements KeyListener{
	   public void keyTyped(KeyEvent e){
		   outgoing.setText("is Typing");
		   
	   }

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		//displayInfo(e,"Key Typed:");	
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		outgoing.setText("");
		outgoing.requestFocus();
	}
   }*/
    public class IncomingReader implements Runnable{
        public void run(){
            String message;
            try{
                while((message = reader.readLine()) != null){
                    System.out.println("read" + message);
                    incoming.append(message+ "\n");
                }
            }catch(Exception ex){
                        
                        }
        }
    }
    public static void main(String[] args){
        new ChatApp().go();
    }
}
