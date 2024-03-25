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

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;

import de.betoffice.openligadb.json.OLDBMatch;

@WireMockTest(httpPort = 9096)
public class OpenligadbRoundFinderWireMockTest extends AbstractSpringTestCase {

    @Autowired
    private OpenligadbRoundFinder openligadbRoundFinder;

    @BeforeEach
    void before() throws Exception {
        OpenLigaDbMock.prepare();
        openligadbRoundFinder.setApiUrl(OpenLigaDbMock.prepareApiUrl());
    }

    @Test
    void openLigaDbUpdate() throws Exception {
        Result<OLDBMatch[], OpenligadbException> matches = openligadbRoundFinder.findMatches("bl1", "2022", 1);

        matches.map(oldbMatches -> {
            for (OLDBMatch match : oldbMatches) {
                System.out.println("Match: " + match.getTeam1().getTeamName() + ":" + match.getTeam2().getTeamName()
                        + " " + match.getMatchResults().toString());
            }

            assertThat(oldbMatches).hasSize(9);
            assertThat(oldbMatches[0].getTeam1().getTeamName()).isEqualTo("Eintracht Frankfurt");
            assertThat(oldbMatches[0].getTeam2().getTeamName()).isEqualTo("FC Bayern München");

            assertThat(oldbMatches[1].getTeam1().getTeamName()).isEqualTo("FC Augsburg");
            assertThat(oldbMatches[1].getTeam2().getTeamName()).isEqualTo("SC Freiburg");

            assertThat(oldbMatches[2].getTeam1().getTeamName()).isEqualTo("VfL Bochum");
            assertThat(oldbMatches[2].getTeam2().getTeamName()).isEqualTo("1. FSV Mainz 05");

            assertThat(oldbMatches[3].getTeam1().getTeamName()).isEqualTo("Borussia Mönchengladbach");
            assertThat(oldbMatches[3].getTeam2().getTeamName()).isEqualTo("TSG 1899 Hoffenheim");

            assertThat(oldbMatches[4].getTeam1().getTeamName()).isEqualTo("1. FC Union Berlin");
            assertThat(oldbMatches[4].getTeam2().getTeamName()).isEqualTo("Hertha BSC");

            assertThat(oldbMatches[5].getTeam1().getTeamName()).isEqualTo("VfL Wolfsburg");
            assertThat(oldbMatches[5].getTeam2().getTeamName()).isEqualTo("Werder Bremen");

            assertThat(oldbMatches[6].getTeam1().getTeamName()).isEqualTo("Borussia Dortmund");
            assertThat(oldbMatches[6].getTeam2().getTeamName()).isEqualTo("Bayer Leverkusen");

            assertThat(oldbMatches[7].getTeam1().getTeamName()).isEqualTo("VfB Stuttgart");
            assertThat(oldbMatches[7].getTeam2().getTeamName()).isEqualTo("RB Leipzig");

            assertThat(oldbMatches[8].getTeam1().getTeamName()).isEqualTo("1. FC Köln");
            assertThat(oldbMatches[8].getTeam2().getTeamName()).isEqualTo("FC Schalke 04");

            return 0;
        }).orElseThrow();
    }

}
