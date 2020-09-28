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

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import de.msiggi.sportsdata.webservices.ArrayOfGroup;
import de.msiggi.sportsdata.webservices.GetAvailGroupsDocument;
import de.msiggi.sportsdata.webservices.GetAvailGroupsDocument.GetAvailGroups;
import de.msiggi.sportsdata.webservices.GetAvailGroupsResponseDocument;
import de.msiggi.sportsdata.webservices.GetAvailGroupsResponseDocument.GetAvailGroupsResponse;
import de.msiggi.sportsdata.webservices.Group;

/**
 * Team finder test.
 *
 * @author Andre Winkler
 */
@SpringJUnitConfig(locations = { "/betoffice-test-properties.xml", "/betoffice.xml" })
public class OpenligadbTest {

    @Disabled
    @Test
    public void testws() throws Exception {
        SportsdataStub stub = new SportsdataStub();

        GetAvailGroups getAvailGroups = GetAvailGroups.Factory.newInstance();
        getAvailGroups.setLeagueSaison("2014");
        getAvailGroups.setLeagueShortcut("bl1");

        GetAvailGroupsDocument getAvailGroupsDocument = GetAvailGroupsDocument.Factory
                .newInstance();
        getAvailGroupsDocument.setGetAvailGroups(getAvailGroups);

        GetAvailGroupsResponseDocument getAvailGroupsResponseDocument = stub
                .getAvailGroups(getAvailGroupsDocument);

        GetAvailGroupsResponse getAvailGroupsResponse = getAvailGroupsResponseDocument
                .getGetAvailGroupsResponse();
        ArrayOfGroup getAvailGroupsResult = getAvailGroupsResponse
                .getGetAvailGroupsResult();

        Group[] groupArray = getAvailGroupsResult.getGroupArray();

        for (Group group : groupArray) {
            System.out.println("Group: " + group.getGroupID() + ", " + group.getGroupName());
        }
    }

}
