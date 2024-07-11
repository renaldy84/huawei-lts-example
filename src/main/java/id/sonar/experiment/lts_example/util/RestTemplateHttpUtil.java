package id.sonar.experiment.lts_example.util;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.UnknownHttpStatusCodeException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class RestTemplateHttpUtil {

	@Autowired
	private RestTemplate restTemplate;
	
	public ResponseEntity<String> postRestTemplate(String url, String body, Map<String, String> headers) {
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
	public ResponseEntity<String> postJsonTemplate(String url, String body, Map<String, String> headers) {
		HttpHeaders header = new HttpHeaders();
		for (Map.Entry<String, String> entry : headers.entrySet()) {
			header.set(entry.getKey(), entry.getValue());
		}
		header.setContentType(MediaType.APPLICATION_JSON);
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
	/**
	 * 
	 * @param <T>
	 * @param url
	 * @param body
	 * @param headers
	 * @param clazz
	 * @return Return null if failed
	 */
	public <T>  ResponseEntity<T> postRestTemplate(String url, String body, Map<String, String> headers, Class<T> clazz) {
		HttpHeaders header = new HttpHeaders();
		for (Map.Entry<String, String> entry : headers.entrySet()) {
			header.set(entry.getKey(), entry.getValue());
		}
		HttpEntity<String> entity = new HttpEntity<String>(body, header);

		ResponseEntity<T> response = null;
		try {
			response = restTemplate.postForEntity(url, entity, clazz);
			LOGGER.trace("{}",response.getBody());
		} catch (HttpClientErrorException | HttpServerErrorException | UnknownHttpStatusCodeException e) {
			LOGGER.error(e.getMessage(), e);
			LOGGER.error("ERROR Response: {}", e.getResponseBodyAsString());
			LOGGER.error("ERROR Status {}:{}", e.getRawStatusCode(), e.getStatusText());
		}

		return response;
	}
	
	public ResponseEntity<String> sendPostFormRequest(String url, Map<String, String> formdata,Map<String, String> headers) {
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
		} catch (HttpClientErrorException | HttpServerErrorException | UnknownHttpStatusCodeException e) {
			LOGGER.error(e.getMessage(), e);
			LOGGER.error("ERROR Response: {}", e.getResponseBodyAsString());
			response = ResponseEntity.status(e.getRawStatusCode()).body(e.getResponseBodyAsString());
		}

		return response;
	}
}
