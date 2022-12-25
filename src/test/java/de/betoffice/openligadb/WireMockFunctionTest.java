package de.betoffice.openligadb;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;

import de.betoffice.openligadb.json.OLDBMatch;

@WireMockTest(httpPort = 9096)
@SpringJUnitConfig(locations = { "/betoffice-test-properties.xml", "/betoffice.xml" })
class WireMockFunctionTest {

	private RestTemplate restTemplate;

	@BeforeEach
	void before() throws Exception {
		OpenLigaDbMock.prepare();
	}

	@Test
	void wireMockServerStartResetStop() throws Exception {
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
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		converter.setObjectMapper(objectMapper);

		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(0, converter);

		return restTemplate;
	}

}
