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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;

import de.betoffice.openligadb.json.OLDBMatch;

/**
 * Team finder test.
 *
 * @author Andre Winkler
 */
@WireMockTest(httpPort = 9096)
public class OpenligadbWireMockTest extends AbstractSpringTestCase {

    @Autowired
    private OpenligadbRoundFinder openligadbRoundFinder;

    @Autowired
    private RestTemplate restTemplate;

	@BeforeEach
	void before() throws Exception {
		OpenLigaDbMock.prepare();
	}

    @Test
    void uefa_em2020_01() throws Exception {
        Result<OLDBMatch[], OpenligadbException> findMatches = openligadbRoundFinder.findMatches("uefa-em-2020", "2020",
                1);

        findMatches.map(oldbMatches -> {
            for (OLDBMatch match : oldbMatches) {
                System.out.println("Match: " + match.getTeam1().getTeamName() + ":" + match.getTeam2().getTeamName()
                        + " " + match.getMatchResults().toString());
            }
            return Integer.valueOf(4711);
        });
    }

    @Test
    void bundeliga2022_01_with_apiurl() throws Exception {
        openligadbRoundFinder.getApiUrl().setOpenligadbUrl("http://localhost:9096");
        Result<OLDBMatch[], OpenligadbException> findMatches = openligadbRoundFinder.findMatches("bl1", "2022", 1);

        findMatches.map(oldbMatches -> {
            for (OLDBMatch match : oldbMatches) {
                System.out.println("Match: " + match.getTeam1().getTeamName() + ":" + match.getTeam2().getTeamName()
                        + " " + match.getMatchResults().toString());
            }
            return Integer.valueOf(4711);
        });
    }
    
    @Test
    void bundesliga2022_01_with_apiurl_and_objectmapper() {
        APIUrl apiUrl = new APIUrl();
        apiUrl.setOpenligadbUrl("http://localhost:9096");

        ObjectMapper objectMapper = JsonMapper.builder().configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true).build();

        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(objectMapper);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(0, converter);

        OLDBMatch[] matches = restTemplate.getForObject(apiUrl.getMatchData("bl1", "2022", 1), OLDBMatch[].class);

        for (OLDBMatch match : matches) {
            System.out.println("Match: " + match.getTeam1().getTeamName() + ":" + match.getTeam2().getTeamName() + " "
                    + match.getMatchResults().toString());
        }
    }

    @Test
    void bundesliga2022_01() {
        OLDBMatch[] matches = restTemplate.getForObject("http://localhost:9096/getmatchdata/bl1/2022/1",
                OLDBMatch[].class);
        for (OLDBMatch match : matches) {
            System.out.println("Match: " + match.getTeam1().getTeamName() + ":" + match.getTeam2().getTeamName() + " "
                    + match.getMatchResults().toString());
        }
    }

    @Test
    void bundesliga2020_03() {
        OLDBMatch[] matches = restTemplate.getForObject("http://localhost:9096/getmatchdata/bl1/2020/3",
                OLDBMatch[].class);
        for (OLDBMatch match : matches) {
            System.out.println("Match: " + match.getTeam1().getTeamName() + ":" + match.getTeam2().getTeamName() + " "
                    + match.getMatchResults().toString());
        }
    }

}
