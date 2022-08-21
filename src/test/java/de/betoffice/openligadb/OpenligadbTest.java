/*
 * ============================================================================
 * Project betoffice-openligadb Copyright (c) 2000-2022 by Andre Winkler. All
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

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.web.client.RestTemplate;

import de.betoffice.openligadb.json.OLDBMatch;

/**
 * Team finder test.
 *
 * @author Andre Winkler
 */
@SpringJUnitConfig(locations = { "/betoffice-test-properties.xml", "/betoffice.xml" })
public class OpenligadbTest {
 
    @Autowired
    private OpenligadbRoundFinder openligadbRoundFinder;
    
    @Disabled
    @Test
    void testws() throws Exception {
        Result<OLDBMatch[],OpenligadbException> findMatches = openligadbRoundFinder.findMatches("uefa-em-2020", "2020", 1);

        findMatches.map(oldbMatches -> {
            for (OLDBMatch match : oldbMatches) {
                System.out.println("Match: " + match.getTeam1().getTeamName() + ":" +  match.getTeam2().getTeamName() + " " + match.getMatchResults().toString());
            }
            return Integer.valueOf(4711);
        });
    }

    @Test
    void testXxx() {
        APIUrl apiUrl = new APIUrl();
        apiUrl.setOpenligadbUrl("http://localhost:9001");
        RestTemplate restTemplate = new RestTemplate();
        OLDBMatch[] matches = restTemplate.getForObject(apiUrl.getMatchData("bl1", "2022", 1), OLDBMatch[].class);

        for (OLDBMatch match : matches) {
            System.out.println("Match: " + match.getTeam1().getTeamName() + ":" +  match.getTeam2().getTeamName() + " " + match.getMatchResults().toString());
        }
    }
    
}
