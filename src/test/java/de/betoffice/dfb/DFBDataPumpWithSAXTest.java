/*
 * $Id: DFBDataPumpWithSAXTest.java 3836 2013-11-16 00:04:50Z andrewinkler $
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

import javax.sql.DataSource;

import org.apache.commons.io.IOUtils;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.betoffice.database.data.MySqlDatabasedTestSupport.DataLoader;
import de.winkler.betoffice.service.DatabaseMaintenanceService;
import de.winkler.betoffice.service.MasterDataManagerService;
import de.winkler.betoffice.service.SeasonManagerService;
import de.winkler.betoffice.storage.GameList;
import de.winkler.betoffice.storage.Group;
import de.winkler.betoffice.storage.GroupType;
import de.winkler.betoffice.storage.Season;
import de.winkler.betoffice.storage.enums.SeasonType;

/**
 * Example with the Java SAXParser.
 *
 * @author by Andre Winkler, $LastChangedBy: andrewinkler $
 * @version $LastChangedRevision: 3836 $ $LastChangedDate: 2013-11-08 22:47:50
 *          +0100 (Fr, 08 Nov 2013) $
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/betoffice-datasource.xml",
		"/betoffice-persistence.xml", "/test-mysql-piratestest.xml" })
public class DFBDataPumpWithSAXTest {

	private File userHomeDirectory;
	private File testFile;

	@Autowired
	protected DataSource dataSource;

	@Autowired
	protected MatchInfoPrepare matchInfoPrepare;

	@Autowired
	protected MatchInfoStore matchInfoStore;

	@Autowired
	protected SeasonManagerService seasonManagerService;

	@Autowired
	protected MasterDataManagerService masterDataManagerService;

	@Autowired
	protected DatabaseMaintenanceService databaseMaintenanceService;

	private DatabaseSetUpAndTearDown dsuatd;

	@Before
	public void setUp() throws Exception {
		dsuatd = new DatabaseSetUpAndTearDown(dataSource);
		dsuatd.setUp(DataLoader.MASTER_DATA);

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
		dsuatd.tearDown();
	}

	@Test
	public void testExternalDataPumpUtils() {
		Season season = new Season();
		season.setMode(SeasonType.LEAGUE);
		season.setName("Fussball Bundesliga 2009/2010");
		season.setYear("2009/2010");
		seasonManagerService.createSeason(season);
		GroupType bundesliga = masterDataManagerService
				.findGroupType("1. Liga");
		Group group = seasonManagerService.addGroupType(season, bundesliga);
		GameList round = seasonManagerService.addRound(season, new DateTime(
				2009, 8, 8, 0, 0), bundesliga);

		DFBDataPumpWithSAX externalDataPumpUtils = new DFBDataPumpWithSAX();
		RoundInfo roundInfo = externalDataPumpUtils.pumpDfb(testFile, "UTF-8");

		matchInfoPrepare.analyzeRoundInfo(season, 0, roundInfo);
		matchInfoStore.storeRoundInfo(roundInfo);

		assertThat(roundInfo.getBoSeasonId()).isEqualTo(season.getId());
		assertThat(roundInfo.getBoGroupId()).isEqualTo(group.getId());
		assertThat(roundInfo.getBoRoundId()).isEqualTo(round.getId());

		GameList matchList = seasonManagerService.findRound(season, 0);

		assertThat(matchList.size()).isEqualTo(9);
		assertThat(matchList.get(0)).isNotNull();

		DFBPumpValidation.assertMatchWolfsburgStuttgartIsOk(matchList.get(0));
		DFBPumpValidation.assertMatchDortmundKoeln(matchList.get(1));
	}

}
