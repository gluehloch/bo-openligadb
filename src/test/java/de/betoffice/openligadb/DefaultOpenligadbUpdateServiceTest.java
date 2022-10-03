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
@SpringJUnitConfig(locations = { "/betoffice-dev-properties.xml", "/betoffice.xml" })
public class DefaultOpenligadbUpdateServiceTest {

	@Autowired
	private BetofficeApi betofficeApi;
	
	@Autowired
	private OpenligadbUpdateService openligadbUpdateService;

	@BeforeEach
	void before() throws Exception {
		OpenLigaDbMock.prepare();
		// openligadbUpdateService.a
		// openligadbRoundFinder.setApiUrl(OpenLigaDbMock.prepareApiUrl());
	}

	@Test
	void updateMatchDay() {
		openligadbUpdateService.createOrUpdateRound(33, 0);
	}

}
