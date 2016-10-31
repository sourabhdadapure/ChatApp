
import java.io.*;
import java.net.*;
import java.util.*;
public class ChatServer {
    ArrayList clientOutputStreams;
    public class ClientHandler implements Runnable{
        BufferedReader reader;
        Socket sock;
        public ClientHandler(Socket clientSocket){
            try{
                sock = clientSocket;
                InputStreamReader isReader = new InputStreamReader(sock.getInputStream());
                reader = new BufferedReader(isReader);
                
            }catch(Exception ex){
                
            }
        }
            public void run(){
                String message;
                try{
                    while((message = reader.readLine()) != null){
                        System.out.println("read "+ message );
                        tellEveryone(message);
                    }
                }catch(Exception ex){
                    
                }
            }
    }
public static void main(String[] args){
    new ChatServer().go();
    
}
public void go(){
    System.out.println("Started Server");
    clientOutputStreams = new ArrayList();
    try{
        ServerSocket serverSock = new ServerSocket(5000);
        while(true){
        Socket clientSocket = serverSock.accept();
        PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());
        clientOutputStreams.add(writer);
        Thread t = new Thread(new ClientHandler(clientSocket));
        t.start();
        System.out.println("Got A Connection");
    }
    }
    catch(Exception ex){
        
    }
}
public void tellEveryone(String message){
    Iterator it = clientOutputStreams.iterator();
    while(it.hasNext()){
        try{
//            String s = " abcd";
            PrintWriter writer = (PrintWriter) it.next();
//            writer = new PrintWriter("Chat.txt");
            writer.println(message);
            writer.flush();
        }catch(Exception ex){
            
        }
    }
}
}

