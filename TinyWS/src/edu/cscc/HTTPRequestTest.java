package edu.cscc;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * CSCI-2469 Java II Group Project
 * design and implement a tiny web server
 * date 20200406
 * @author Kelly Waddell
 * @author Mutasem Alhariri 
 * @author Robert Doult
 * @since 20200406
 * @version 1.5  
 */ 

public class HTTPRequestTest {

	public static final String GOODREQUEST = "GET /path1/path2/index.html\nmore stuff\nmore stuff";
	public static final String BADREQUEST = "bogus";
	public static final String BAD = "";

	@Test
	public void testHTTPRequest1() {
		HTTPRequest request = new HTTPRequest(GOODREQUEST);
		assertTrue("Request processing failed", "/path1/path2/index.html".equals(request.getPath()));
		assertTrue("isValidRequest() failed", request.isValidRequest());
	}

	@Test
	public void testHTTPRequest2() {
		HTTPRequest request = new HTTPRequest(BADREQUEST);
		assertFalse("isValidRequest() failed while processing invalid request", request.isValidRequest());
	}
	
	@Test
	public void testHTTPRequest3() {
		HTTPRequest request1 = new HTTPRequest(BAD);
		assertFalse("isValidRequest() failed while processing invalid request", request1.isValidRequest());
	}
	
		@Test
	public void testHTTPRequest4() {
		HTTPRequest request1 = new HTTPRequest(null);
		assertFalse("isValidRequest() failed while processing invalid request", request1.isValidRequest());
	}
}