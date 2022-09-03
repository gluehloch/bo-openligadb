package de.betoffice.openligadb;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
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

	private CloseableHttpClient client;
	private RestTemplate restTemplate;

	@BeforeAll
	static void before() throws Exception {
		OpenLigaDbMock.prepare();
	}

	@Test
	void wireMockServerStartResetStop() throws Exception {
		client = prepareClient();

		String content = getContent("http://localhost:9096/getmatchdata/bl1/2022/1");
		System.out.println(content);

		APIUrl apiUrl = new APIUrl();
		apiUrl.setOpenligadbUrl("http://localhost:9096");

		restTemplate = prepareRestTemplate();

		getAndPrintMatches(apiUrl, "bl1", "2022", 1);
		getAndPrintMatches(apiUrl, "bl1", "2020", 3);
	}

	private void getAndPrintMatches(APIUrl apiUrl, String leagueShortCut, String year, int round) {
		OLDBMatch[] matches = restTemplate.getForObject(apiUrl.getMatchData(leagueShortCut, year, round),
				OLDBMatch[].class);
		for (OLDBMatch match : matches) {
			System.out.println("Match: " + match.getTeam1().getTeamName() + ":" + match.getTeam2().getTeamName() + " "
					+ match.getMatchResults().toString());
		}
	}

	private RestTemplate prepareRestTemplate() {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);

		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		converter.setObjectMapper(objectMapper);

		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(0, converter);

		return restTemplate;
	}

	private CloseableHttpClient prepareClient() {
		return HttpClientBuilder.create().useSystemProperties() // This must be enabled for auto proxy config
				.build();
	}

	private String getContent(String url) throws Exception {
		try (CloseableHttpResponse response = client.execute(new HttpGet(url))) {
			return EntityUtils.toString(response.getEntity());
		}
	}
}
