import java.io.*;
import java.net.*;
import java.util.*;
import java.awt.*;
import javax.swing.*;

public class Server extends JFrame {
  // Text area for displaying contents
  private JTextArea jta = new JTextArea();
  
  public static void main(String[] args) {
    new Server();
  }
  
  public Server() {
    // Place text area on the frame
    String Output = "";
    setLayout(new BorderLayout());
    add(new JScrollPane(jta), BorderLayout.CENTER);
    
    setTitle("Server");
    setSize(500, 300);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true); // It is necessary to show the frame here!
    
    try {
      // Create a server socket
      ServerSocket serverSocket = new ServerSocket(8000);
      jta.append("Server started at " + new Date() + '\n');
      
      // Listen for a connection request
      Socket socket = serverSocket.accept();
      
      // Create data input and output streams
      DataInputStream inputFromClient = new DataInputStream(
      socket.getInputStream());
      DataOutputStream outputToClient = new DataOutputStream(
      socket.getOutputStream());
      
      while (true) {
        // Receive radius from the client
        int Num1 = inputFromClient.readInt();
        int firstNum = Num1;
        int Num2 = inputFromClient.readInt();
        //int s = 2;
        //int n = 100;
        while  (Num1<Num2)
        {
            boolean isPrime = true;
            for(int i=2;i<Num1;i++) 
            {
                if(Num1%i==0)
                {
                    isPrime = false;
                }
            }
            if (isPrime)
            {
                Output += Num1 + "\n";
            }
            
            Num1++;
        }
        
        // Send area back to the client
        outputToClient.writeChars(Output);
        
        jta.append("Input1 and Input2 received from client: " + firstNum + " " + Num2 + '\n');
        jta.append("Prime numbers found: " + Output + '\n');
      }
    }
    catch(IOException ex) {
      System.err.println(ex);
    }
  }
}
