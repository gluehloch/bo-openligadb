/*
 * $Id: DFBDateTimeTransformerTest.java 3837 2013-11-16 18:33:07Z andrewinkler $
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

import java.util.Locale;

import org.joda.time.DateTime;
import org.junit.Test;

/**
 * Test for class {@link DFBDateTimeTransformer}.
 * 
 * @author $Author: andrewinkler $
 * @version $Revision: 3837 $ $Date: 2013-06-08 09:19:46 +0200 (Sa, 08 Jun 2013)
 *          $
 */
public class DFBDateTimeTransformerTest {

	@Test
	public void testDFBDateTransformer() throws Exception {
		DFBDateTimeTransformer transformer = new DFBDateTimeTransformer();
		DateTime dateTime = transformer.toDateTime("24.03.1971");

		assertThat(dateTime).isEqualTo(new DateTime(1971, 3, 24, 0, 0));
	}

	@Test
	public void testDFBDateTimeTransformer() throws Exception {
		DFBDateTimeTransformer transformer = new DFBDateTimeTransformer();
		DateTime dateTime = transformer.toDateTime("24.03.1971", "20.15");

		assertThat(dateTime).isEqualTo(new DateTime(1971, 3, 24, 20, 15));
	}

	@Test
	public void testDateTimeFormatter() {
		DateTime dateTime = new DateTime(1971, 3, 24, 20, 15);
		assertThat("24.03.1971 20:15").isEqualTo(
				dateTime.toString("dd.MM.yyyy HH:mm", Locale.GERMANY));
	}

}
