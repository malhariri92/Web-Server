package edu.cscc;

import java.io.*;
import java.net.*;

/**
 * ResponseHandler - send HTTP response
 * CSCI-2469 Java II Group Project
 * design and implement a tiny web server
 * date 20200406
 * @author Kelly Waddell
 * @author Mutasem Alhariri 
 * @author Robert Doult
 * @since 20200406
 * @version 1.5  
 */ 
public class ResponseHandler {
	private static final String NOT_FOUND_RESPONSE = "HTTP/1.0 404 NotFound\n" + "Server: TinyWS\n"
			+ "Content-type: text/plain\n\n" + "Requested file not found.";

	private static final String FORBIDDEN_RESPONSE = "HTTP/1.0 403 Forbidden\n" + "Server: TinyWS\n"
			+ "Content-type: text/plain\n\n" + "Requested action is forbidden.  So there!";

	private static final String HTTP_OK_HEADER = "HTTP/1.0 200 OK\n" + "Server: TinyWS\n" + "Content-type: ";

	private HTTPRequest request;
	private int responseCode;

	/**
	 * Constructor
	 */
	public ResponseHandler(HTTPRequest request) {
		this.request = request;
		responseCode = 404;
	}

	/**
	 * Send HTTP Response
	 */
	public void sendResponse(Socket connection) throws IOException {
		byte[] response = null;
		int sendbufsize = connection.getSendBufferSize();
		BufferedOutputStream out = new BufferedOutputStream(connection.getOutputStream(), sendbufsize);
		if (request.isValidRequest())
			response = getFile(request.getPath());
		if (response == null) {
			if (responseCode == 403) {
				response = FORBIDDEN_RESPONSE.getBytes();
			} else {
				response = NOT_FOUND_RESPONSE.getBytes();
			}
			TinyWS.log("\tRequest for file: " + request.getPath() + "  failed");
		}
		out.write(response, 0, response.length);
		out.flush();
		connection.close();
	}

	// Find requested file, assume Document Root is in html folder in project
	// directory
	private byte[] getFile(String path) {
		if (path == null) {
			responseCode = 404;
			return (null);
		}
		// Security check
		if (path.contains("..")) {
			responseCode = 403;
			return (null);
		}

		// Make path relative to current project directory
		if (path.startsWith("/"))
			path = TinyWS.getDefaultFolder() + path;
		else
			path = TinyWS.getDefaultFolder() + "/" + path;

		// If it's a directory - append index.html
		File f = new File(path);
		if (f.isDirectory()) {
			if (path.endsWith("/"))
				path = path + TinyWS.getDefaultPage();
			else
				path = path + "/" + TinyWS.getDefaultPage();
		}

		// Make sure file exists, is readable, and is non-zero length
		f = new File(path);
		if (!f.isFile()) {
			responseCode = 404;
			return (null);
		} else if (!f.canRead()) {
			responseCode = 403;
			return (null);
		}
		long filesize = f.length();
		if (filesize == 0) {
			responseCode = 404;
			return (null);
		}

		// Check MIME type for file
		String mimeType = getMimeType(path);
		if (mimeType == null) {
			responseCode = 403;
			return (null);
		}

		// Build headers
		String headers = HTTP_OK_HEADER + mimeType + "\n\n";

		// Get file as String
		byte[] buffer = readFile(f);
		if (buffer == null) {
			responseCode = 404;
			return (null);
		}

		// Combine headers and file buffer
		TinyWS.log("\tGot file: " + f + " (" + buffer.length + " bytes)");
		byte[] hbuf = headers.getBytes();
		byte[] outbuf = new byte[hbuf.length + buffer.length];
		System.arraycopy(hbuf, 0, outbuf, 0, hbuf.length);
		System.arraycopy(buffer, 0, outbuf, hbuf.length, buffer.length);
		responseCode = 200;
		return (outbuf);
	}

	// Read file, return byte array (null if error)
	private byte[] readFile(File f) {
		int len = (int) f.length();
		byte[] buffer = new byte[len];

		FileInputStream fin = null;
		try {
			fin = new FileInputStream(f);
			fin.read(buffer, 0, len);
		} catch (IOException e) {
			return (null);
		} finally {
			try {
				fin.close();
			} catch (Exception e) {
				/* ignore */
			}
		}
		return (buffer);
	}

	// Return mimetype based on file suffix (or null if error)
	private String getMimeType(String path) {
		// set mimeType assigned null it means there is no
		// underlying object of any kind
		String mimeType = null;
		// used if else statement to
		// reads the filename suffix and returns a String
		// containing the MIME type
		if (path.endsWith(".html")) {
			mimeType = "text/html";
		} else if (path.endsWith(".txt")) {
			mimeType = "text/plain";
		} else if (path.endsWith(".gif")) {
			mimeType = "image/gif";
		} else if (path.endsWith(".jpg")) {
			mimeType = "image/jpeg";
		}
		return mimeType;
	}
}