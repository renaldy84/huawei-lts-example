package id.sonar.experiment.lts_example.util;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.UnknownHttpStatusCodeException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HttpUtil {
	private static RestTemplate restTemplate = new RestTemplate();

	public static String requestPostJson(String url, String body, Map<String, String> headers) {
		HttpPost httpPost = new HttpPost(url);
		if (!ObjectUtils.isEmpty(headers)) {
			for (Map.Entry<String, String> data : headers.entrySet())
				httpPost.addHeader(data.getKey(), data.getValue());
		}
		StringEntity request = new StringEntity(body, Charset.forName("UTF-8"));
		String bodyAsString = "";
		httpPost.setEntity(request);
		try (CloseableHttpResponse response = HttpClients.createDefault().execute(httpPost)) {
			bodyAsString = EntityUtils.toString(response.getEntity());
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		}
		return bodyAsString;
	}
	
	public static ResponseEntity<String> postRestTemplate(String url, String body, Map<String, String> headers) {
		HttpHeaders header = new HttpHeaders();
		for (Map.Entry<String, String> entry : headers.entrySet()) {
			header.set(entry.getKey(), entry.getValue());
		}
		HttpEntity<String> entity = new HttpEntity<String>(body, header);

		ResponseEntity<String> response = null;
		try {
			response = restTemplate.postForEntity(url, entity, String.class);
			LOGGER.trace(response.getBody());
		} catch (HttpClientErrorException | HttpServerErrorException | UnknownHttpStatusCodeException e) {
			LOGGER.error(e.getMessage(), e);
			LOGGER.error("ERROR Response: {}", e.getResponseBodyAsString());
			response = ResponseEntity.status(e.getRawStatusCode()).body(e.getResponseBodyAsString());
		}

		return response;
	}
	
	public static String sendPostFormRequest(String url, Map<String, String> formdata,Map<String, String> headers) {
		String responseBody = StringUtil.EMPTY;
		HttpHeaders httpheaders = new HttpHeaders();
		httpheaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		for (Map.Entry<String, String> header : headers.entrySet()) {
			httpheaders.add(header.getKey(), header.getValue());
		}
		
		MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
		for (Map.Entry<String, String> data : formdata.entrySet()) {
			map.add(data.getKey(), data.getValue());
		}

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, httpheaders);
		
		ResponseEntity<String> response = null;
		try {
			response = restTemplate.postForEntity( url, request , String.class );
			responseBody = response.getBody();
		} catch (HttpClientErrorException | HttpServerErrorException | UnknownHttpStatusCodeException e) {
			responseBody = e.getResponseBodyAsString();
		}

		return responseBody;
	}
	
	public static ResponseEntity<String> postRestTemplateFormRequest(String url, Map<String, String> formdata,Map<String, String> headers) {
		HttpHeaders httpheaders = new HttpHeaders();
		httpheaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		for (Map.Entry<String, String> header : headers.entrySet()) {
			httpheaders.add(header.getKey(), header.getValue());
		}
		
		MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
		for (Map.Entry<String, String> data : formdata.entrySet()) {
			map.add(data.getKey(), data.getValue());
		}

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, httpheaders);
		
		ResponseEntity<String> response = null;
		try {
			response = restTemplate.postForEntity( url, request , String.class );
			LOGGER.trace(response.getBody());
		} catch (HttpClientErrorException | HttpServerErrorException | UnknownHttpStatusCodeException e) {
			LOGGER.error(e.getMessage(), e);
			LOGGER.error("ERROR Response: {}", e.getResponseBodyAsString());
			response = ResponseEntity.status(e.getRawStatusCode()).body(e.getResponseBodyAsString());
		}

		return response;
	}
	
}
