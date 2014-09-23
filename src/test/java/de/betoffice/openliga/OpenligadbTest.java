package de.betoffice.openliga;

import java.rmi.RemoteException;

import org.apache.axis2.AxisFault;
import org.junit.Test;

import de.betoffice.openligadb.SportsdataStub;
import de.dbload.Dbload;
import de.msiggi.sportsdata.webservices.ArrayOfGroup;
import de.msiggi.sportsdata.webservices.ArrayOfTeam;
import de.msiggi.sportsdata.webservices.GetAvailGroupsDocument;
import de.msiggi.sportsdata.webservices.GetAvailGroupsDocument.GetAvailGroups;
import de.msiggi.sportsdata.webservices.GetAvailGroupsResponseDocument;
import de.msiggi.sportsdata.webservices.GetAvailGroupsResponseDocument.GetAvailGroupsResponse;
import de.msiggi.sportsdata.webservices.GetTeamsByLeagueSaisonDocument;
import de.msiggi.sportsdata.webservices.GetTeamsByLeagueSaisonDocument.GetTeamsByLeagueSaison;
import de.msiggi.sportsdata.webservices.GetTeamsByLeagueSaisonResponseDocument;
import de.msiggi.sportsdata.webservices.Group;
import de.msiggi.sportsdata.webservices.Team;

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
    

    @Test
    public void testTeamsOfSeason() throws RemoteException {
        Dbload.read(connection, clazz);
        
        SportsdataStub stub = new SportsdataStub("http://localhost:8088/mockSportsdataSoap12");
        GetTeamsByLeagueSaisonDocument getTeamsByLeagueSaison42 = GetTeamsByLeagueSaisonDocument.Factory.newInstance();
        GetTeamsByLeagueSaison getTeamsByLeagueSaison = GetTeamsByLeagueSaison.Factory.newInstance();
        getTeamsByLeagueSaison.setLeagueSaison("2014");
        getTeamsByLeagueSaison.setLeagueShortcut("bl1");
        getTeamsByLeagueSaison42.setGetTeamsByLeagueSaison(getTeamsByLeagueSaison);
        GetTeamsByLeagueSaisonResponseDocument teamsByLeagueSaison = stub.getTeamsByLeagueSaison(getTeamsByLeagueSaison42);
        
        ArrayOfTeam getTeamsByLeagueSaisonResult = teamsByLeagueSaison.getGetTeamsByLeagueSaisonResponse().getGetTeamsByLeagueSaisonResult();
        Team[] teams = getTeamsByLeagueSaisonResult.getTeamArray();
        for (Team team : teams) {
            System.out.println(team.getTeamID() + ": " + team.getTeamName()); 
        }
    }

}
