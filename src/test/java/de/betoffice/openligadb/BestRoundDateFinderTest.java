/*
 * ============================================================================
 * Project betoffice-openligadb Copyright (c) 2000-2014 by Andre Winkler. All
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

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.Test;

import de.msiggi.sportsdata.webservices.Matchdata;

/**
 * Test of {@link BestRoundDateFinder}.
 *
 * @author Andre Winkler
 */
public class BestRoundDateFinderTest {

    @Test
    public void testBestRoundDateFinder() throws RemoteException {
        OpenligadbRoundFinder finder = new OpenligadbRoundFinder();
        Matchdata[] matches = finder.findMatches(
                "http://localhost:8088/mockSportsdataSoap12", "bl1", "2014", 1);
        BestRoundDateFinder dateFinder = new BestRoundDateFinder();
        Date bestDate = dateFinder.findBestRoundDate(matches);
        System.out.println("Best date: " + bestDate);
    }

    @Test
    public void testBestRoundDateFinder2() {
        DateTime DT0_2014_08_22_203000 = new DateTime(2014, 8, 22, 20, 30, 0);

        DateTime DT1_2014_08_23_153000 = new DateTime(2014, 8, 23, 15, 30, 0);
        DateTime DT2_2014_08_23_153000 = new DateTime(2014, 8, 23, 15, 30, 0);
        DateTime DT3_2014_08_23_153000 = new DateTime(2014, 8, 23, 15, 30, 0);
        DateTime DT4_2014_08_23_153000 = new DateTime(2014, 8, 23, 15, 30, 0);

        DateTime DT5_2014_08_24_160000 = new DateTime(2014, 8, 24, 16, 00, 0);
        DateTime DT6_2014_08_24_160000 = new DateTime(2014, 8, 24, 16, 00, 0);

        List<Date> dates = new ArrayList<>();
        dates.add(DT0_2014_08_22_203000.toDate());
        dates.add(DT1_2014_08_23_153000.toDate());
        dates.add(DT2_2014_08_23_153000.toDate());
        dates.add(DT3_2014_08_23_153000.toDate());
        dates.add(DT4_2014_08_23_153000.toDate());
        dates.add(DT5_2014_08_24_160000.toDate());
        dates.add(DT6_2014_08_24_160000.toDate());

        BestRoundDateFinder dateFinder = new BestRoundDateFinder();
        Date bestDate = dateFinder.findBestDate(dates);
        assertThat(bestDate.getTime(), equalTo(new DateTime(2014, 8, 23, 15,
                30, 0).toDate().getTime()));
    }

}
