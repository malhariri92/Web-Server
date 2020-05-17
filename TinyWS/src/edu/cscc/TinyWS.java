package edu.cscc;

import java.io.IOException;
import java.net.*;

/**
 * TinyWS a simplistic Tiny Web Server
 * CSCI-2469 Java II Group Project
 * design and implement a tiny web server
 * date 20200406
 * @author Kelly Waddell
 * @author Mutasem Alhariri 
 * @author Robert Doult
 * @since 20200406
 * @version 1.5  
 */ 
public class TinyWS {

	private static int port;
	private static String defaultFolder;
	private static String defaultPage;

	/**
	 * Main routine - instantiate tiny web server, start listening for browser
	 * requests
	 */
	public static void main(String[] args) {
		TinyWS tiny = new TinyWS();
		tiny.listen();
	}

	/**
	 * Constructor - read and set configuration
	 */
	public TinyWS() {
		// creating a Config object
		Config config = new Config();
		// parses the string argument as a signed decimal integer
		// get a property value and process web server configuration
		port = Integer.parseInt(config.getProperty(Config.PORT));
		// returns a string containing the value of the property.
		defaultFolder = config.getProperty(Config.DEFAULTFOLDER);
		// returns a string containing the value of the property.
		defaultPage = config.getProperty(Config.DEFAULTPAGE);
		config.dumpProperties();
	}

	/**
	 * Listen on server socket
	 */
	public void listen() {
		Socket s = null;
		// creates a server socket, bound to the specified port
		// The option must be enabled prior to entering the blocking operation to have
		// effect
		try (ServerSocket ss = new ServerSocket(port)) {
			// sanity check printout let you know server is started
			System.out.println("GREAT NEWS! listen() is working...The server is started" +ss);
			// setSoTimeout() method enables or disables
			// the SO_TIMEOUT option with the given timeout value, in milliseconds
			// SocketException - if there is an error in the underlying protocol, such as a
			// TCP error.
			ss.setSoTimeout(0);
			// sanity check printout setSoTimeout
			System.out.println("GREAT NEWS! setSoTimeout(0) is working Timeout value: " + ss.getSoTimeout());
			// accept () this is a blocking call..server code will stop here and waiting,
			// listening on
			// assigned port for incoming client
			while (true) {
				// listens for a connection to be made to this socket and accepts it
				// the method blocks until a connection is made
				s = ss.accept();
				// Returns the address to which the socket is connected.
				log("Request from: " + s.getInetAddress().getCanonicalHostName());
				// RequestHandler - handle HTTP GET Requests(ignore anything else)
				RequestHandler handler = new RequestHandler(s);
				// processRequest() This method processes both GET and POST HTTP requests.
				handler.processRequest();
			}
			// if an I/O error occurs when waiting for a connection
		} catch (IOException ex) {
			// handle fatal error - print info and die
			fatalError(ex);
		} finally {
			try {
				// close the stream and release the resources that were busy in the stream
				s.close();
			} catch (IOException e) {
				fatalError(e);
			}
		}
	}

	/**
	 * Log web server requests
	 * 
	 * @param s - message to log
	 */
	public static void log(String s) {
		System.out.println(s);
	}

	/**
	 * Handle fatal error - print info and die
	 */
	public static void fatalError(String s) {
		handleError(s, null, true);
	}

	/**
	 * Handle fatal error - print info and die
	 */
	public static void fatalError(Exception e) {
		handleError(null, e, true);
	}

	/**
	 * Handle fatal / non-fatal errors
	 */
	public static void handleError(String s, Exception e, boolean isFatal) {
		if (s != null) {
			System.out.println(s);
		}
		if (e != null) {
			e.printStackTrace();
		}
		if (isFatal)
			System.exit(-1);
	}

	/**
	 * Get port configuration value
	 * 
	 * @return port number
	 */
	public static int getPort() {
		return port;
	}

	/**
	 * Get default HTML folder
	 * 
	 * @return folder
	 */
	public static String getDefaultFolder() {
		return defaultFolder;
	}

	/**
	 * Get name of default web page
	 * 
	 * @return default page
	 */
	public static String getDefaultPage() {
		return defaultPage;
	}
}