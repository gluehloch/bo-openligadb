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

package de.betoffice.openligadb.todo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;

import de.betoffice.openligadb.DefaultOpenligadbUpdateService;
import de.betoffice.openligadb.OpenligadbUpdateService;
import de.betoffice.wrapper.api.BetofficeApi;
import de.betoffice.wrapper.api.GroupTypeRef;
import de.betoffice.wrapper.api.SeasonRef;
import de.betoffice.wrapper.api.TeamRef;
import de.winkler.betoffice.storage.enums.SeasonType;
import de.winkler.betoffice.storage.enums.TeamType;

/**
 * Test case for {@link DefaultOpenligadbUpdateService}.
 */
@WireMockTest(httpPort = 9096)
@SpringJUnitConfig(locations = { "/betoffice-test-properties.xml", "/betoffice.xml" })
class OpenligadbSeasonTest {

    private static final ZonedDateTime DATE_15_09_2010 = ZonedDateTime
            .of(LocalDateTime.of(LocalDate.of(2010, 9, 15), LocalTime.of(0, 0)), ZoneId.of("Europe/Berlin"));
    private static final ZonedDateTime DATE_08_09_2010 = ZonedDateTime
            .of(LocalDateTime.of(LocalDate.of(2010, 9, 8), LocalTime.of(0, 0)), ZoneId.of("Europe/Berlin"));
    private static final ZonedDateTime DATE_01_09_2010 = ZonedDateTime
            .of(LocalDateTime.of(LocalDate.of(2010, 9, 9), LocalTime.of(0, 0)), ZoneId.of("Europe/Berlin"));

    private TeamRef rwe;
    private TeamRef schalke;
    private TeamRef burghausen;
    private TeamRef hsv;

    private GroupTypeRef bundesliga_1;

    private SeasonRef buli_2010;

    @Autowired
    private BetofficeApi betofficeApi;

    @Autowired
    private OpenligadbUpdateService openligadbUpdateService;

    @BeforeEach
    void before() throws Exception {
        // OpenLigaDbMock.prepare();
        // openligadbUpdateService.a
        // openligadbRoundFinder.setApiUrl(OpenLigaDbMock.prepareApiUrl());
    }

    @Test
    void updateMatchDay() {
        createSeason();
        // openligadbUpdateService.createOrUpdateRound();
    }

    private SeasonRef createSeason() {
        GroupTypeRef bundesligaRef = betofficeApi.createGroupType("1. Bundesliga").result();

        rwe        = betofficeApi.createTeam("RWE", "Rot-Weiss-Essen", TeamType.DFB).result();
        schalke    = betofficeApi.createTeam("S04", "Schalke 04", TeamType.DFB).result();
        burghausen = betofficeApi.createTeam("Wacker", "Wacker Burghausen", TeamType.DFB).result();
        hsv        = betofficeApi.createTeam("HSV", "Hamburger SV", TeamType.DFB).result();

        buli_2010 = betofficeApi.createSeason("Bundesliga 2010/2011", "2010/2011", SeasonType.LEAGUE, TeamType.DFB).result();

        betofficeApi.addGroup(buli_2010, bundesliga_1);

        betofficeApi.addTeam(buli_2010, bundesliga_1, hsv);
        betofficeApi.addTeam(buli_2010, bundesliga_1, schalke);
        betofficeApi.addTeam(buli_2010, bundesliga_1, burghausen);
        betofficeApi.addTeam(buli_2010, bundesliga_1, rwe);

        betofficeApi.addRound(buli_2010, bundesliga_1, DATE_01_09_2010);
        betofficeApi.addRound(buli_2010, bundesliga_1, DATE_08_09_2010);
        betofficeApi.addRound(buli_2010, bundesliga_1, DATE_15_09_2010);

        return buli_2010;
    }

}
