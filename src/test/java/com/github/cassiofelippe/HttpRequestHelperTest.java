package com.github.cassiofelippe;

import static com.github.cassiofelippe.HttpRequestHelper.run;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.net.http.HttpResponse;
import java.util.Collections;

import org.junit.Test;

public class HttpRequestHelperTest {

	@Test
    public void testGet() {
		final HttpResponse<String> response = run("GET", "https://github.com");

		assertEquals(200, response.statusCode());
		assertNotNull(response.body());
    }
	
	@Test
	public void testPost() {
		final HttpResponse<String> response = run("POST", "https://github.com", "{}");
		
		assertEquals(404, response.statusCode());
		assertNotNull(response.body());
	}

	@Test
    public void testHeaders() {
		final HttpResponse<String> response = run(
			"GET",
			"https://github.com",
			Collections.singletonMap("Content-Type", "application/json;charset=utf-8")
		);

		assertNotNull(response.body());
    }
	
	@Test
	public void testBodyAndHeaders() {
		final HttpResponse<String> response = run(
			"POST",
			"https://github.com",
			"{}",
			Collections.singletonMap("Content-Type", "application/json;charset=utf-8")
		);
		
		assertNotNull(response.body());
	}
}
