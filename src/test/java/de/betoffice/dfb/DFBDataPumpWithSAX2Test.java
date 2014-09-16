/*
 * $Id: DFBDataPumpWithSAX2Test.java 3837 2013-11-16 18:33:07Z andrewinkler $
 * ============================================================================
 * Project betoffice-exchange
 * Copyright (c) 2000-2013 by Andre Winkler. All rights reserved.
 * ============================================================================
 *          GNU GENERAL PUBLIC LICENSE
 *  TERMS AND CONDITIONS FOR COPYING, DISTRIBUTION AND MODIFICATION
 *
 *   This program is free software; you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation; either version 2 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program; if not, write to the Free Software
 *   Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 */

package de.betoffice.dfb;

import static org.fest.assertions.Assertions.assertThat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.SQLException;

import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.betoffice.service.DFBDownloadService;

/**
 * Example with the Java SAXParser.
 *
 * @author by Andre Winkler, $LastChangedBy: andrewinkler $
 * @version $LastChangedRevision: 3837 $ $LastChangedDate: 2013-11-08 22:47:50
 *          +0100 (Fr, 08 Nov 2013) $
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/betoffice-datasource.xml",
		"/betoffice-persistence.xml", "/test-mysql-piratestest.xml" })
public class DFBDataPumpWithSAX2Test {

	private File userHomeDirectory;
	private File testFile;

	@Autowired
	protected DFBDownloadService dfbDownloadService;

	@Before
	public void setUp() throws Exception {
		String userHome = System.getProperty("user.home");
		userHomeDirectory = new File(userHome + "/.betoffice/test");
		userHomeDirectory.mkdir();

		InputStream is = this.getClass().getResourceAsStream("blm_e_01_09.xml");
		testFile = new File(userHomeDirectory, "blm_e_01_09.xml");
		FileOutputStream fos = new FileOutputStream(testFile);
		IOUtils.copy(is, fos);
		IOUtils.closeQuietly(fos);
	}

	@After
	public void tearDown() throws SQLException {
	}

	@Test
	public void testExternalDataPumpUtils() {
		DFBDataPumpWithSAX externalDataPumpUtils = new DFBDataPumpWithSAX();
		RoundInfo roundInfo = externalDataPumpUtils.pumpDfb(testFile, "UTF-8");

		assertThat(roundInfo.getMatchInfos()).hasSize(9);
		MatchInfo match1 = roundInfo.getMatchInfos().get(0);
		assertThat(match1.getHomeTeam()).isEqualTo("VfL Wolfsburg");
		assertThat(match1.getGuestTeam()).isEqualTo("VfB Stuttgart");
		assertThat(match1.getHomeGoals()).isEqualTo(2);
		assertThat(match1.getGuestGoals()).isEqualTo(0);

		MatchInfo match2 = roundInfo.getMatchInfos().get(1);
		assertThat(match2.getHomeTeam()).isEqualTo("Borussia Dortmund");
		assertThat(match2.getGuestTeam()).isEqualTo("1. FC Köln");
		assertThat(match2.getHomeGoals()).isEqualTo(1);
		assertThat(match2.getGuestGoals()).isEqualTo(0);

		MatchInfo match3 = roundInfo.getMatchInfos().get(2);
		assertThat(match3.getHomeTeam()).isEqualTo("1. FC Nürnberg");
		assertThat(match3.getGuestTeam()).isEqualTo("Schalke 04");
		assertThat(match3.getHomeGoals()).isEqualTo(1);
		assertThat(match3.getGuestGoals()).isEqualTo(2);
	}

}
