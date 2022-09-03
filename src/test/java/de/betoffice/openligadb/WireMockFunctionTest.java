package de.betoffice.openligadb;

import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;

import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;

import de.betoffice.openligadb.json.OLDBMatch;

@WireMockTest(httpPort = 9096)
@SpringJUnitConfig(locations = { "/betoffice-test-properties.xml", "/betoffice.xml" })
public class WireMockFunctionTest {

	CloseableHttpClient client;

	@Test
    void wireMockServerStartResetStop() throws Exception {
	    client = HttpClientBuilder.create()
	    	      .useSystemProperties() // This must be enabled for auto proxy config
	    	      .build();
		InputStream resourceAsStream = this.getClass().getResourceAsStream("/bundesliga-2022-01.json");
		String responseAsString = IOUtils.toString(resourceAsStream, "UTF-8");
		
		// UrlPattern urlPattern = UrlPattern.fromOneOf(null, null, null, null);
        // StubMapping stubMapping = options(urlPattern).withPort(8089).build();
        
        stubFor(get("/static-dsl").willReturn(ok()));
        stubFor(get("/getmatchdata/bl1/2022/1").willReturn(ok(responseAsString).withHeader("Content-Type", "application/json")));

        //WireMockServer wireMockServer = new WireMockServer();
        //wireMockServer.start();
        String content = getContent("http://localhost:9096/getmatchdata/bl1/2022/1");
        System.out.println(content);

        APIUrl apiUrl = new APIUrl();
        apiUrl.setOpenligadbUrl("http://localhost:9001");

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);

        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(objectMapper);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(0, converter);        
        
        OLDBMatch[] matches = restTemplate.getForObject("http://localhost:9096/getmatchdata/bl1/2022/1",
                OLDBMatch[].class);
        for (OLDBMatch match : matches) {
            System.out.println("Match: " + match.getTeam1().getTeamName() + ":" + match.getTeam2().getTeamName() + " "
                    + match.getMatchResults().toString());
        }        
        
        //wireMockServer.resetAll();
        //wireMockServer.stop();
    }

	private String getContent(String url) throws Exception {
		try (CloseableHttpResponse response = client.execute(new HttpGet(url))) {
			return EntityUtils.toString(response.getEntity());
		}
	}
}
