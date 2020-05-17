package edu.cscc;

import java.io.*;
import java.net.Socket;


/**
 * RequestHandler.java – handles a request from a web browser
 * CSCI-2469 Java II Group Project
 * design and implement a tiny web server
 * date 20200406
 * @author Kelly Waddell
 * @author Mutasem Alhariri 
 * @author Robert Doult
 * @since 20200406
 * @version 1.5  
 */ 

/**
 * RequestHandler - handle HTTP GET Requests (ignore anything else)
 * 
 * @author student name
 */
public class RequestHandler {
	private Socket connection;

	/**
	 * Constructor
	 */
	public RequestHandler(Socket connection) {
		this.connection = connection;
	}

	/**
	 * Process an HTTP request
	 */
	public void processRequest() throws IOException {
		try {
			// creating an HttpRequest builder with the given URI.
			// HTTP request string to be parsed to allow break the data into smaller
			// elements
			// parse HTTP Requests(actually parse a small part of a GET Request: GET
			// [filepath])
			// modify the processRequest() method to call readRequest() and get the HTTP
			// request as a String
			HTTPRequest req = new HTTPRequest(readRequest());
			// create a response handler
			// processes an Http Response and returns some value corresponding to that
			// response.
			ResponseHandler rHandler = new ResponseHandler(req);
			// returns whether the send operation was successful or not
			// between the socket (the end point for communication between two machines)
			rHandler.sendResponse(connection);
		} catch (IOException e) {
			TinyWS.fatalError(e);
		} finally {
			// connection.close();
			TinyWS.log("Close connection");
		}
	}

	// Read an HTTP Request
	private String readRequest() throws IOException {
		// Set socket timeout to 500 milliseconds
		connection.setSoTimeout(500);
		int recbufsize = connection.getReceiveBufferSize();
		InputStream in = connection.getInputStream();
		InputStreamReader rdr = new InputStreamReader(in);
		BufferedReader brdr = new BufferedReader(rdr, recbufsize);
		StringBuilder reqBuf = new StringBuilder();
		char[] cbuf = new char[recbufsize];
		// used if statement
		// brdr.read(cbuf) comparison operator not equal to -1 ( if -1 then specified
		// char/substring is not found in the particular String)
		if (brdr.read(cbuf) != -1) {
			// appends the string representation of the char array argument to this
			// sequence.
			reqBuf.append(cbuf);
		}
		return reqBuf.toString();
	}

	public void dumpProcessRequest() {
		System.out.println("Got a request");
	}
}