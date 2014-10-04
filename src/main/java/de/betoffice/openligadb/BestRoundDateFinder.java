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

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.msiggi.sportsdata.webservices.Matchdata;

/**
 * Find the best round date.
 *
 * @author Andre Winkler
 */
public class BestRoundDateFinder {

    /**
     * Find the best round date of an array of matches.
     *
     * @param matches
     *            the matches
     * @return the best round date
     */
    public Date findBestRoundDate(Matchdata[] matches) {
        List<Date> matchDates = new ArrayList<>();
        for (Matchdata matchdata : matches) {
            matchDates.add(matchdata.getMatchDateTime().getTime());
        }

        return findBestDate(matchDates);
    }

    /**
     * Find the best round date of an array of dates.
     *
     * @param matcheDates
     *            the dates
     * @return the best round date
     */
    public Date findBestDate(List<Date> matchDates) {
        Map<Date, Integer> dates = new HashMap<>();

        for (Date date : matchDates) {
            if (!dates.containsKey(date)) {
                dates.put(date, Integer.valueOf(0));
            }

            Integer value = dates.get(date);
            dates.put(date, value + 1);
        }

        Map.Entry<Date, Integer> bestDate = null;

        Set<Map.Entry<Date, Integer>> values = dates.entrySet();
        for (Map.Entry<Date, Integer> dateCount : values) {
            if (bestDate == null) {
                bestDate = dateCount;
            } else {
                if (dateCount.getValue() > bestDate.getValue()) {
                    bestDate = dateCount;
                }
            }
        }

        return bestDate.getKey();
    }

}
