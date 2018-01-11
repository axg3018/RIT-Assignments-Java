/*
 *  HangmanServer.java
 *
 *  @version 1.0
 *
 */
/**
 *  @author Vidhathri Kota
 *  @author Arjun Gupta
 *
 */

import java.io.*;
import java.util.*;
import java.net.ServerSocket;
import java.net.Socket;
/**
 *
 * Server implementation of the hangman Game.
 * This program reads a dictionary text file, scans a word from it
 * at random. Information about number of letters, trials etc are
 * sent over to the client, where the game is actually being played.
 * Waits for the response from client and sends appropriate messages
 * 
 *
 */
public class HangmanServer {
  public static String fname;
  /**
   * word() - This method retrieves the nth word from the input file
   *
   * @param n - the random number 
   */
  public static String word( int n ){
    int counter = 0;
    String w = "";
    try{
      Scanner s = new Scanner( new File( fname ) );
      while( counter < n ){
        w = s.next();
        counter++;
        s.nextLine();
      }
      s.close();
    }
    catch( Exception e ){
      System.out.println( "method exception" );
    }
    return w;
  }
  /**
   * The main program.
   *
   * @param    args    command line arguments - port number, dictionary file
   */ 
  public static void main(String[] args) throws IOException {
    int counter = 0, i, n, flag=0, trials = 9, wrong=0, right=0;
    char ch, again='y';
    char[] guess; 
    String w1;
    if (args.length != 2) {
      System.err.println("Usage: java EchoServer <port number> <file.txt>");
      System.exit(1);
    }
    System.out.println("Server started. Listening on Port" );
    int portNumber = Integer.parseInt(args[0]);
    fname = args[1];
      try (ServerSocket serverSocket = new ServerSocket(portNumber);
        Socket clientSocket = serverSocket.accept();
	     PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
	     BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));) {
	  System.out.println("Client connected on port " + portNumber +". Servicing requests.");
	  Scanner s1 = new Scanner( new File( fname ) );
    while( s1.hasNextLine() ){        
      counter++;
      s1.nextLine();
    }
    int zee = 2, once = 0;
    String str="";
    while( again=='y' ){
        wrong=0; right=0;
        w1 = word( ( int )( Math.random()*( counter+1 ) ) );  //gets random word
        n = w1.length();
        guess = new char[n];  
        for( i = 0; i < n; i++ )
          guess[ i ]='*';
        str = "";
         System.out.println(w1);
        while( wrong < 9 && right < n ){   //loop for one word
          out.println(true);
          flag = 0;       
          for( i = 0; i < n; i++ )
             str = str + guess[ i ] + "  ";
          out.println(str);
          str="";
          out.println(wrong);
          ch = in.readLine().charAt(0);
          for( i = 0; i < n; i++ ){
            if( w1.charAt( i ) == ch ){

              guess[i] = ch;   
              flag = 1;
              right++;    //if character is present, flag =1, increment the right guesses

            }
          }
          if( flag == 0 ){    //character not found - a trial used
            out.println(zee-1);
            wrong++;   
			     if(trials - wrong == 0)
				      out.println( "Sorry, you lose. By the way the word was " +w1 );
			     else
				      out.println( "Nope. You have "+( trials-wrong )+" trials left." );
          }
          else if( right == n ){
            out.println(zee-1);
            out.println( "Congratulations! You won! : )" );
          }
          
          else
            out.println(zee);   
      
        }
        out.println(false);
        again = in.readLine().charAt(0);
        once = 1;
      }
	  
    
	} catch (IOException e) {
	    System.out.println("Exception caught when trying to listen on port " + portNumber + " or listening for a connection");
       	}
    }
}

