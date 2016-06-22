package org.spartan.model.util;

import java.util.function.Consumer;

import org.apache.http.HttpHeaders;
import org.apache.http.entity.ContentType;

import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.async.Callback;
import com.mashape.unirest.http.exceptions.UnirestException;

public class UnirestQueryBuilder {

	/**
	 * The gson thingy
	 */
	private static final Gson gson = new Gson();

	/**
	 * 
	 * @param remote
	 * @param body
	 */
	public static HttpResponse<JsonNode> post(String remote, Object body) throws UnirestException {
		return Unirest.post(remote).header(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType()).body(gson.toJson(body)).asJson();
	}

	/**
	 * 
	 * @param remote
	 * @param body
	 * @param type
	 * @return
	 * @throws UnirestException
	 */
	public static <T> T post(String remote, Object body, Class<T> type) throws UnirestException {
		return gson.fromJson(post(remote, body).getBody().toString(), type);
	}

	/**
	 * 
	 * @param remote
	 * @param body
	 */
	public static void asyncPost(String remote, Object body, Runnable run) throws UnirestException {
		Unirest.post(remote).header(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType()).body(gson.toJson(body)).asJsonAsync(new Callback<JsonNode>() {
			
			@Override
			public void failed(UnirestException e) {
				e.printStackTrace();
			}
			
			@Override
			public void completed(HttpResponse<JsonNode> response) {
				run.run();
			}
			
			@Override
			public void cancelled() {
				System.out.println("canceled");
			}

		});
	}

	/**
	 * 
	 * @param remote
	 * @param body
	 * @param type
	 * @return
	 * @throws UnirestException
	 */
	public static <T> void asyncPost(String remote, Object body, Class<T> type, Consumer<T> consumer) throws UnirestException {
		Unirest.post(remote).header(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType()).body(gson.toJson(body)).asJsonAsync(new Callback<JsonNode>() {
			
			@Override
			public void failed(UnirestException e) {
				e.printStackTrace();
			}
			
			@Override
			public void completed(HttpResponse<JsonNode> response) {
				consumer.accept(gson.fromJson(response.getBody().toString(), type));
			}
			
			@Override
			public void cancelled() {}
			
		});
	}

}
