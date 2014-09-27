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

import java.rmi.RemoteException;

import org.apache.commons.lang.StringUtils;

import de.msiggi.sportsdata.webservices.ArrayOfMatchdata;
import de.msiggi.sportsdata.webservices.GetMatchdataByGroupLeagueSaisonDocument;
import de.msiggi.sportsdata.webservices.GetMatchdataByGroupLeagueSaisonDocument.GetMatchdataByGroupLeagueSaison;
import de.msiggi.sportsdata.webservices.GetMatchdataByGroupLeagueSaisonResponseDocument;
import de.msiggi.sportsdata.webservices.GetMatchdataByGroupLeagueSaisonResponseDocument.GetMatchdataByGroupLeagueSaisonResponse;
import de.msiggi.sportsdata.webservices.Matchdata;

/**
 * Call the openligadb webservice to get all matches of a round.
 * 
 * @author Andre Winkler
 */
public class RoundFinder {

    /**
     * Find all matches of specfic league and group.
     * 
     * @param webserviceUrl
     *            The URL of openligadb´s websevice. If empty then take the
     *            default URL.
     * @param leagueShortcut
     *            The openligadb league shortcut id.
     * @param leagueSeason
     *            The openligadb leagueSeason id.
     * @param groupOrderID
     *            The openligadb groupOrderId. The equivalent of betoffice round
     *            index. Betoffice round index has a range from 0 to N-1. The
     *            groupOrderId has a range from 1 to N.
     * @return An array of matches for this group.
     * @throws RemoteException
     */
    public Matchdata[] findMatches(String webserviceUrl, String leagueShortcut,
            String leagueSeason, int groupOrderId) throws RemoteException {

        SportsdataStub stub = new SportsdataStub(webserviceUrl);
        if (StringUtils.isEmpty(webserviceUrl)) {
            stub = new SportsdataStub();
        } else {
            stub = new SportsdataStub(webserviceUrl);
        }

        GetMatchdataByGroupLeagueSaisonDocument getMatchdataByGroupLeagueSaison2 = GetMatchdataByGroupLeagueSaisonDocument.Factory
                .newInstance();
        GetMatchdataByGroupLeagueSaison addNewGetMatchdataByGroupLeagueSaison = getMatchdataByGroupLeagueSaison2
                .addNewGetMatchdataByGroupLeagueSaison();

        addNewGetMatchdataByGroupLeagueSaison.setLeagueShortcut(leagueShortcut);
        addNewGetMatchdataByGroupLeagueSaison.setLeagueSaison(leagueSeason);
        addNewGetMatchdataByGroupLeagueSaison.setGroupOrderID(groupOrderId);

        GetMatchdataByGroupLeagueSaisonResponseDocument matchdataByGroupLeagueSaison = stub
                .getMatchdataByGroupLeagueSaison(getMatchdataByGroupLeagueSaison2);
        GetMatchdataByGroupLeagueSaisonResponse getMatchdataByGroupLeagueSaisonResponse = matchdataByGroupLeagueSaison
                .getGetMatchdataByGroupLeagueSaisonResponse();
        ArrayOfMatchdata getMatchdataByGroupLeagueSaisonResult = getMatchdataByGroupLeagueSaisonResponse
                .getGetMatchdataByGroupLeagueSaisonResult();
        Matchdata[] matchdataArray = getMatchdataByGroupLeagueSaisonResult
                .getMatchdataArray();

        return matchdataArray;
    }

}
