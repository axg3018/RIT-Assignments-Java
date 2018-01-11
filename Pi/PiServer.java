/*
 *  PiServer.java
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
import java.net.ServerSocket;
import java.net.Socket;
/**
 *
 * Server implementation of the PiEvenOdd program
 * Takes the character read by client, converts to integer
 * and updates the even - odd count and prints the result
 *
 */
public class PiServer {
  public static void main(String[] args) throws IOException {
    if (args.length < 1) {
      System.err.println("Usage: java EchoServer <port number>");
      System.exit(1);
    }
    System.out.println("Server started. Listening on Port" );
    int portNumber = Integer.parseInt(args[0]);
      try (ServerSocket serverSocket = new ServerSocket(portNumber);
        Socket clientSocket = serverSocket.accept();
	PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
	BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));) {
	  System.out.println("Client connected on port " + portNumber +". Servicing requests.");
	  String inputLine;
    int dig, even = 0, odd = 0;
    System.out.println("Client is "+in.readLine());
	  while ((inputLine = in.readLine()) != null) {
          
          dig = Integer.parseInt(inputLine);
          if(dig %2 == 0)
            even++;
          else
            odd++;         
	  }
    System.out.println("Results available for the client! Thanks bye.");
	System.out.println("Even count: "+even+"\nOdd count: "+odd+"\nOdd/Even: "+(double)odd/even);
    //out.println("Even count: "+even+"\nOdd count: "+odd+"\nOdd/Even: "+(double)odd/even);
	} catch (IOException e) {
	    System.out.println("Exception caught when trying to listen on port " + portNumber + " or listening for a connection");
       	}
    }
}

