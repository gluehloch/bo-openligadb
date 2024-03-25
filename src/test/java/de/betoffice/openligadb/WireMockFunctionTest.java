/*
 * ============================================================================
 * Project betoffice-openligadb Copyright (c) 2000-2024 by Andre Winkler. All
 * rights reserved.
 * ============================================================================
 * GNU GENERAL PUBLIC LICENSE TERMS AND CONDITIONS FOR COPYING, DISTRIBUTION AND
 * MODIFICATION
 *
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place, Suite 330, Boston, MA 02111-1307 USA
 */

package de.betoffice.openligadb;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;

import de.betoffice.openligadb.json.OLDBMatch;

@WireMockTest(httpPort = 9096)
class WireMockFunctionTest extends AbstractSpringTestCase {

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
