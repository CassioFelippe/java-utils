package com.forte.utils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpRequest.Builder;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
    Executa requisições http
 */
public class HttpRequestHelper {
	
	/**
	 * @param method GET, POST, PUT
	 * @param address URI
	 */
	public static HttpResponse<String> run(final String method, final String address) {
		return run(method, address, "{}", new HashMap<>());
	}

	/**
	 * @param method GET, POST, PUT
	 * @param address URI
	 * @param headers MAP
	 */
	public static HttpResponse<String> run(final String method, final String address, final Map<String, String> headers) {
		return run(method, address, "{}", headers);
	}
	
	/**
	 * @param method GET, POST, PUT
	 * @param address URI
	 * @param body JSON
	 */
	public static HttpResponse<String> run(final String method, final String address, final String body) {
		return run(method, address, body, new HashMap<>());
		
	}
	
	/**
	 * @param method GET, POST, PUT
	 * @param address URI
	 * @param body JSON
	 * @param headers MAP
	 */
	public static HttpResponse<String> run(final String method, final String address, final String body, final Map<String, String> headers) {
		URI uri = null;
		try {
			uri = new URI(address);
		} catch (final URISyntaxException e) {
			e.printStackTrace();
		}
		final Builder request = HttpRequest.newBuilder(uri);
		
		request.method(method, BodyPublishers.ofString(body, Charset.forName("UTF-8")));

		for (final Map.Entry<String, String> entry : headers.entrySet()) {
			request.header(entry.getKey(), entry.getValue());
		}
		
		try {
			return HttpClient.newHttpClient().send(request.build(), BodyHandlers.ofString());
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
