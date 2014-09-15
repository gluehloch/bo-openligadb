package de.betoffice.openliga;

import java.rmi.RemoteException;

import org.junit.Test;

import de.betoffice.openligadb.SportsdataStub;
import de.msiggi.sportsdata.webservices.ArrayOfGroup;
import de.msiggi.sportsdata.webservices.GetAvailGroupsDocument;
import de.msiggi.sportsdata.webservices.GetAvailGroupsDocument.GetAvailGroups;
import de.msiggi.sportsdata.webservices.GetAvailGroupsResponseDocument;
import de.msiggi.sportsdata.webservices.GetAvailGroupsResponseDocument.GetAvailGroupsResponse;
import de.msiggi.sportsdata.webservices.Group;

public class OpenligadbTest {

    @Test
    public void testws() throws RemoteException {
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
            System.out.println("Group: " + group.getGroupID() + ", "
                    + group.getGroupName());
        }
    }
}
