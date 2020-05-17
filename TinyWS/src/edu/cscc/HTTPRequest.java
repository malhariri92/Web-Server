package edu.cscc;

/**
 * HTTPRequest - parse HTTP Requests (actually parse a small part of a GET
 * Request: GET [filepath])
 * CSCI-2469 Java II Group Project
 * design and implement a tiny web server
 * date 20200406
 * @author Kelly Waddell
 * @author Mutasem Alhariri 
 * @author Robert Doult
 * @since 20200406
 * @version 1.5  
 */ 

public class HTTPRequest {
	private String path; // path to file
	private boolean validRequest; // is request valid?

	/**
	 * Constructor
	 * 
	 * @param r - HTTP request string to be parsed
	 */
	public HTTPRequest(String r) {
		validRequest = parse(r);
	}

	/**
	 * Is the request valid
	 */
	public boolean isValidRequest() {
		return (validRequest);
	}

	/**
	 * Return file path for request
	 */
	public String getPath() {
		return (path);
	}

	/**
	 * Parse an HTTP request
	 */
	private boolean parse(String r) {
		// used if-else to check if HTTPRequest(String r) is null OR Returns true if,
		// and only if, length() is 0.
		if (r == null || r.isEmpty())
			return false;
		// split the string if a space, tab, newline, or question mark are encountered
		String[] arr = r.split("[ \\t\\n?]");
		// verify the first token is ‘GET’. The second token should be the file path.
		// compares this string to the specified object. The result is true if and only
		// if the argument is not null and is a String object that represents the same
		// sequence of characters as this object.
		if (arr.length < 2 || !"GET".equals(arr[0])) {
			// return true if the request is syntactically valid, false otherwise.
			return false;
		} else {			
			path = arr[1];
			return true;
		}
	}
}