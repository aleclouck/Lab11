import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Client extends JFrame {
  // Text field for receiving radius
  private JTextField jtf = new JTextField();
  private JTextField jtf2 = new JTextField();
  
  // Text area to display contents
  private JTextArea jta = new JTextArea();
  
  // IO streams
  private DataOutputStream toServer;
  private DataInputStream fromServer;
  
  public static void main(String[] args) {
    new Client();
  }
  
  public Client() {
    // Panel p to hold the label and text field
    JPanel p = new JPanel();
    p.setLayout(new BorderLayout());
    p.add(new JLabel("Enter Input1 then below enter input2"), BorderLayout.WEST);
    p.add(jtf, BorderLayout.CENTER);
    p.add(jtf2, BorderLayout.PAGE_END);
    jtf.setHorizontalAlignment(JTextField.RIGHT);
    
    setLayout(new BorderLayout());
    add(p, BorderLayout.NORTH);
    add(new JScrollPane(jta), BorderLayout.CENTER);
    
    jtf2.addActionListener(new Listener()); // Register listener
    
    setTitle("Client");
    setSize(500, 300);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true); // It is necessary to show the frame here!
    
    try {
      // Create a socket to connect to the server
      Socket socket = new Socket("localhost", 8000);
      // Socket socket = new Socket("xxx.xxx.xxx.xxx", 8000);
      // Socket socket = new Socket("yyy.edu", 8000);
      
      // Create an input stream to receive data from the server
      fromServer = new DataInputStream(
      socket.getInputStream());
      
      // Create an output stream to send data to the server
      toServer =
      new DataOutputStream(socket.getOutputStream());
    }
    catch (IOException ex) {
      jta.append(ex.toString() + '\n');
    }
  }
  
  private class Listener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      try {
        // Get the radius from the text field
        int Num1 = Integer.parseInt(jtf.getText().trim());
        int Num2 = Integer.parseInt(jtf2.getText().trim());
        // Send the radius to the server
        toServer.writeInt(Num1);
        toServer.writeInt(Num2);
        toServer.flush();
        
        // Get area from the server
        String Output = fromServer.readUTF();
        
        // Display to the text area
        jta.append("Input is " + Num1 +  ", " + Num2 + "\n");
        jta.append("Prime numbers received from the server is "
        + Output + '\n');
        System.out.println("test");
      }
      catch (IOException ex) {
        System.err.println(ex);
      }
    }
  }
}