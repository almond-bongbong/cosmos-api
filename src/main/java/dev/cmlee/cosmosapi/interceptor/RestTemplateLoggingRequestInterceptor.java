package dev.cmlee.cosmosapi.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;

@Slf4j
public class RestTemplateLoggingRequestInterceptor implements ClientHttpRequestInterceptor {

	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
		URI uri = request.getURI();
		traceRequest(request, body);
		ClientHttpResponse response = execution.execute(request, body);
		traceResponse(response, uri); return response;
	}

	private void traceRequest(HttpRequest request, byte[] body) {
		StringBuilder reqLog = new StringBuilder();
		reqLog
				.append("[REQUEST] ")
				.append("Uri : ")
				.append(request.getURI())
				.append(", Method : ")
				.append(request.getMethod())
				.append(", Request Body : ")
				.append(new String(body, StandardCharsets.UTF_8));
		log.info(reqLog.toString());
	}

	private void traceResponse(ClientHttpResponse response, URI uri) throws IOException {
		StringBuilder resLog = new StringBuilder();
		resLog
				.append("[RESPONSE] ")
				.append("Uri : ")
				.append(uri)
				.append(", Status code : ")
				.append(response.getStatusCode())
				.append(", Response Body : ")
				.append(StreamUtils.copyToString(response.getBody(), StandardCharsets.UTF_8));

		log.info(resLog.toString());
	}
}
