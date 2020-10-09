package de.betoffice.openligadb;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RestTemplateWithLoggingInterceptor {

    private static Logger log = LoggerFactory.getLogger(RestTemplateWithLoggingInterceptor.class);

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate(
                new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()));

        List<ClientHttpRequestInterceptor> interceptors = restTemplate.getInterceptors();
        if (CollectionUtils.isEmpty(interceptors)) {
            interceptors = new ArrayList<>();
        }
        interceptors.add(new LoggingInterceptor());
        restTemplate.setInterceptors(interceptors);
        return restTemplate;
    }

    private static class LoggingInterceptor implements ClientHttpRequestInterceptor {

        @Override
        public ClientHttpResponse intercept(HttpRequest req, byte[] reqBody, ClientHttpRequestExecution ex)
                throws IOException {

            RestTemplateWithLoggingInterceptor.log.debug("Request body: {}", new String(reqBody, StandardCharsets.UTF_8));
            ClientHttpResponse response = ex.execute(req, reqBody);
            InputStreamReader isr = new InputStreamReader(response.getBody(), StandardCharsets.UTF_8);
            String body = new BufferedReader(isr).lines().collect(Collectors.joining("\n"));
            RestTemplateWithLoggingInterceptor.log.debug("Response body: {}", body);

            return response;
        }

    }

}
