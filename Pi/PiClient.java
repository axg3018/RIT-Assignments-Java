/*
 *  PiClient.java
 *
 *  @version 1.0
 *
 */
/**
 *  @author Vidhathri Kota
 *  @author Arjun Gupta
 *
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.io.FileInputStream;
/**
 *
 * Client implementation of the PiEvenOdd program
 * Reads each digit from the file and sends to the server
 *
 */
public class PiClient {
  /**
   * The main program.
   *
   * @param    args    command line arguments - host name, port number, pi.txt
   */ 
  public static void main(String[] args) throws IOException {
    if (args.length != 3) {
      System.err.println("Usage: java EchoClient <host name> <port number> <pi file>");
      System.exit(1);
    }
    String hostName = args[0];
    int portNumber = Integer.parseInt(args[1]);
    try (Socket echoSocket = new Socket(hostName, portNumber);
      PrintWriter out = new PrintWriter(echoSocket.getOutputStream());
      BufferedReader in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
      BufferedReader fileIn = new BufferedReader(new InputStreamReader(new FileInputStream(args[2])));) {
      char ch;
      int eachDigit;
      System.out.println("Reading file "+args[2]);
      out.println("reading file "+args[2]);
      while ((eachDigit = fileIn.read()) > 0) {
        ch = (char)eachDigit;
        if(Character.isDigit(ch))
          out.println(ch);
      }
		//System.out.println("Count:");
		//System.out.println(in.readLine());
    }catch (UnknownHostException e) {
       System.err.println("Don't know about host " + hostName);
       System.exit(1);
    }catch (IOException e) {
       System.err.println("Couldn't get I/O for the connection to " + hostName);
       System.exit(1);
    } 
  }
}

