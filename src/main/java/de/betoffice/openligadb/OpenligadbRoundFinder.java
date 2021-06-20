/*
 * ============================================================================
 * Project betoffice-openligadb Copyright (c) 2000-2020 by Andre Winkler. All
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

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import de.betoffice.openligadb.json.OLDBMatch;
import de.winkler.betoffice.util.LoggerFactory;

/**
 * Call the openligadb webservice to get all matches of a round.
 *
 * Example: {@code https://www.openligadb.de/api/getmatchdata/bl1}
 *
 * @author Andre Winkler
 */
@Component
public class OpenligadbRoundFinder {

    private static final Logger LOG = LoggerFactory.make();

    @Autowired
    private APIUrl apiUrl;

    @Autowired
    private RestTemplate restTemplate;

    // ------------------------------------------------------------------------

    /**
     * Find all matches of specfic league and group.
     *
     * @param leagueShortcut
     *            The openligadb league shortcut id.
     * @param leagueSeason
     *            The openligadb leagueSeason id.
     * @param groupOrderId
     *            The openligadb groupOrderId. The equivalent of betoffice round
     *            index. Betoffice round index has a range from 0 to N-1. The
     *            groupOrderId has a range from 1 to N.
     * @return An array of matches for this group. s
     * @throws OpenligadbConnectionException
     *             Connection and/or openligadb internal server problems
     */
    public OLDBMatch[] findMatches(String leagueShortcut, String leagueSeason, int groupOrderId)
            throws OpenligadbConnectionException {

        try {
            String url = apiUrl.getMatchData(leagueShortcut, leagueSeason, groupOrderId);
            OLDBMatch[] matches = restTemplate.getForObject(url, OLDBMatch[].class);
            return matches;
        } catch (Throwable ex) {
            LOG.error("There is a problem with the openligadb REST API. "
                    + "I catched a Exception: {}", ex.getMessage());
            throw new OpenligadbConnectionException(ex.getMessage(), ex);
        }
    }

}
