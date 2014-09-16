/*
 * $Id: DFBPumpValidation.java 3837 2013-11-16 18:33:07Z andrewinkler $
 * ============================================================================
 * Project betoffice-exchange
 * Copyright (c) 2000-2012 by Andre Winkler. All rights reserved.
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

import org.joda.time.DateTime;

import de.winkler.betoffice.storage.Game;

/**
 * Validates the games of DFB download file blm_e_01_09.xml.
 * 
 * @author by Andre Winkler, $LastChangedBy: andrewinkler $
 * @version $LastChangedRevision: 3837 $ $LastChangedDate: 2013-06-08 09:19:46
 *          +0200 (Sa, 08 Jun 2013) $
 */
public class DFBPumpValidation {

	/**
	 * Validates match 0: Wolfsburg - Stuttgart 2:0 2009-08-07 20:30 Uhr
	 * 
	 * @param game
	 *            The game to validate
	 */
	public static void assertMatchWolfsburgStuttgartIsOk(final Game match) {
		assertThat(match.getHomeTeam().getName()).isEqualTo("VfL Wolfsburg");
		assertThat(match.getGuestTeam().getName()).isEqualTo("VfB Stuttgart");
		assertThat(match.getResult().getHomeGoals()).isEqualTo(2);
		assertThat(match.getResult().getGuestGoals()).isEqualTo(0);

		DateTime dateTime = new DateTime(2009, 8, 7, 20, 30, 0);
		assertThat(match.getDateTime().getTime()).isEqualTo(
				dateTime.toDate().getTime());
	}

	/**
	 * Validates match 1: Köln - Dortmund 1:0 2009-08-08 15:30 Uhr
	 * 
	 * @param match
	 *            The game to validate
	 */
	public static void assertMatchDortmundKoeln(final Game match) {
		assertThat(match.getHomeTeam().getName())
				.isEqualTo("Borussia Dortmund");
		assertThat(match.getGuestTeam().getName()).isEqualTo("1.FC Köln");
		assertThat(match.getResult().getHomeGoals()).isEqualTo(1);
		assertThat(match.getResult().getGuestGoals()).isEqualTo(0);

		DateTime dateTime = new DateTime(2009, 8, 8, 15, 30, 0);
		assertThat(match.getDateTime().getTime()).isEqualTo(
				dateTime.toDate().getTime());
	}

}
