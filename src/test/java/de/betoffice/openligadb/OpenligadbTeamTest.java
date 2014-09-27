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

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.sql.Connection;
import java.sql.SQLException;

import org.hibernate.SessionFactory;
import org.hibernate.jdbc.Work;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.betoffice.database.data.DeleteDatabase;
import de.dbload.Dbload;
import de.msiggi.sportsdata.webservices.ArrayOfGoal;
import de.msiggi.sportsdata.webservices.ArrayOfMatchResult;
import de.msiggi.sportsdata.webservices.ArrayOfMatchdata;
import de.msiggi.sportsdata.webservices.ArrayOfTeam;
import de.msiggi.sportsdata.webservices.GetMatchdataByGroupLeagueSaisonDocument;
import de.msiggi.sportsdata.webservices.GetMatchdataByGroupLeagueSaisonDocument.GetMatchdataByGroupLeagueSaison;
import de.msiggi.sportsdata.webservices.GetMatchdataByGroupLeagueSaisonResponseDocument;
import de.msiggi.sportsdata.webservices.GetMatchdataByGroupLeagueSaisonResponseDocument.GetMatchdataByGroupLeagueSaisonResponse;
import de.msiggi.sportsdata.webservices.GetTeamsByLeagueSaisonDocument;
import de.msiggi.sportsdata.webservices.GetTeamsByLeagueSaisonDocument.GetTeamsByLeagueSaison;
import de.msiggi.sportsdata.webservices.GetTeamsByLeagueSaisonResponseDocument;
import de.msiggi.sportsdata.webservices.Goal;
import de.msiggi.sportsdata.webservices.Location;
import de.msiggi.sportsdata.webservices.MatchResult;
import de.msiggi.sportsdata.webservices.Matchdata;
import de.msiggi.sportsdata.webservices.Team;
import de.winkler.betoffice.service.MasterDataManagerService;

/**
 * Checks, if all teams of 'Bundesliga 2014/2015' are find by their openliga ID.
 * 
 * @author Andre Winkler
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/betoffice-datasource.xml",
        "/betoffice-persistence.xml", "/test-mysql-piratestest.xml" })
public class OpenligadbTeamTest extends
        AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private MasterDataManagerService masterDataManagerService;

    public void prepareDatabase(final Class<?> clazz) {
        // deleteDatabase();
        sessionFactory.getCurrentSession().doWork(new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {
                Dbload.read(connection, clazz);
            }
        });
    }

    @Test
    public void testFindAllTeams() throws Exception {
        sessionFactory.getCurrentSession().doWork(new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {
                DeleteDatabase.deleteDatabase(connection);
                Dbload.read(connection, this.getClass(), "bo_team.dat");
            }
        });

        SportsdataStub stub = new SportsdataStub(
                "http://localhost:8088/mockSportsdataSoap12");
        GetTeamsByLeagueSaisonDocument getTeamsByLeagueSaison42 = GetTeamsByLeagueSaisonDocument.Factory
                .newInstance();
        GetTeamsByLeagueSaison getTeamsByLeagueSaison = GetTeamsByLeagueSaison.Factory
                .newInstance();
        getTeamsByLeagueSaison.setLeagueSaison("2014");
        getTeamsByLeagueSaison.setLeagueShortcut("bl1");
        getTeamsByLeagueSaison42
                .setGetTeamsByLeagueSaison(getTeamsByLeagueSaison);
        GetTeamsByLeagueSaisonResponseDocument teamsByLeagueSaison = stub
                .getTeamsByLeagueSaison(getTeamsByLeagueSaison42);

        ArrayOfTeam getTeamsByLeagueSaisonResult = teamsByLeagueSaison
                .getGetTeamsByLeagueSaisonResponse()
                .getGetTeamsByLeagueSaisonResult();
        Team[] teams = getTeamsByLeagueSaisonResult.getTeamArray();
        for (Team team : teams) {
            de.winkler.betoffice.storage.Team boTeam = masterDataManagerService
                    .findTeamByOpenligaid(team.getTeamID());
            assertThat(boTeam, notNullValue());

            System.out.println(boTeam.getId() + " | " + boTeam.getName()
                    + " | " + boTeam.getLongName() + " | "
                    + boTeam.getTeamType().ordinal() + " | "
                    + boTeam.getOpenligaid());
        }
    }

    @Test
    public void testGetMatchesOfRound() throws Exception {
        sessionFactory.getCurrentSession().doWork(new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {
                DeleteDatabase.deleteDatabase(connection);
                Dbload.read(connection, this.getClass(),
                        "bo_bundesliga_2014.dat");
            }
        });

        SportsdataStub stub = new SportsdataStub(
                "http://localhost:8088/mockSportsdataSoap12");
        GetMatchdataByGroupLeagueSaisonDocument getMatchdataByGroupLeagueSaison2 = GetMatchdataByGroupLeagueSaisonDocument.Factory
                .newInstance();
        GetMatchdataByGroupLeagueSaison addNewGetMatchdataByGroupLeagueSaison = getMatchdataByGroupLeagueSaison2
                .addNewGetMatchdataByGroupLeagueSaison();
        addNewGetMatchdataByGroupLeagueSaison.setGroupOrderID(1);
        addNewGetMatchdataByGroupLeagueSaison.setGroupOrderID(2014);
        addNewGetMatchdataByGroupLeagueSaison.setLeagueShortcut("bl1");
        GetMatchdataByGroupLeagueSaisonResponseDocument matchdataByGroupLeagueSaison = stub
                .getMatchdataByGroupLeagueSaison(getMatchdataByGroupLeagueSaison2);
        GetMatchdataByGroupLeagueSaisonResponse getMatchdataByGroupLeagueSaisonResponse = matchdataByGroupLeagueSaison
                .getGetMatchdataByGroupLeagueSaisonResponse();
        ArrayOfMatchdata getMatchdataByGroupLeagueSaisonResult = getMatchdataByGroupLeagueSaisonResponse
                .getGetMatchdataByGroupLeagueSaisonResult();
        Matchdata[] matchdataArray = getMatchdataByGroupLeagueSaisonResult
                .getMatchdataArray();
        for (Matchdata matchdata : matchdataArray) {
            matchdata.getGroupID();
            matchdata.getIdTeam1();
            matchdata.getIdTeam2();
            matchdata.getMatchIsFinished();
            ArrayOfMatchResult matchResults = matchdata.getMatchResults();
            for (MatchResult matchResult : matchResults.getMatchResultArray()) {
                matchResult.getResultTypeId(); // 2: Nach 90 Minuten // 1: Nach 45 Minuten
                matchResult.getPointsTeam1(); // Tore Heimmannschaft
                matchResult.getPointsTeam2(); // Tore Gastmannschaft
            }
            Location location = matchdata.getLocation();
            ArrayOfGoal goals = matchdata.getGoals();
            for (Goal goal : goals.getGoalArray()) {
                goal.getGoalComment();
                goal.getGoalMatchMinute();
                goal.getGoalGetterName();
                goal.getGoalOvertime();
                goal.getGoalOwnGoal();
                goal.getGoalPenalty();
                goal.getGoalScoreTeam1(); // Tore Heimmanschaft
                goal.getGoalScoreTeam2(); // Tore Gastmannschaft
            }
        }
    }

}
