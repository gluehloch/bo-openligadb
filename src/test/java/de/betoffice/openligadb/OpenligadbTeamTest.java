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

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

import org.hibernate.SessionFactory;
import org.hibernate.jdbc.Work;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import de.betoffice.database.data.DeleteDatabase;
import de.dbload.Dbload;
import de.msiggi.sportsdata.webservices.ArrayOfGoal;
import de.msiggi.sportsdata.webservices.ArrayOfMatchResult;
import de.msiggi.sportsdata.webservices.ArrayOfTeam;
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
@Disabled
@SpringJUnitConfig(locations = { "/betoffice-test-properties.xml", "/betoffice.xml" })
public class OpenligadbTeamTest
        extends AbstractTransactionalJUnit4SpringContextTests {

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
        OLDBTeam[] teams = getTeamsByLeagueSaisonResult.getTeamArray();
        for (OLDBTeam team : teams) {
            Optional<de.winkler.betoffice.storage.Team> boTeam = masterDataManagerService
                    .findTeamByOpenligaid(team.getTeamID());
            assertThat(boTeam).isNotNull();

            System.out.println(
                    boTeam.get().getId() + " | " + boTeam.get().getName()
                            + " | " + boTeam.get().getLongName() + " | "
                            + boTeam.get().getTeamType().ordinal() + " | "
                            + boTeam.get().getOpenligaid());
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

        OpenligadbRoundFinder roundFinder = new OpenligadbRoundFinder();
        // Matchdata[] matchdataArray = roundFinder.findMatches(
        // "http://localhost:8088/mockSportsdataSoap12", "bl1", "2014", 1);
        Matchdata[] matchdataArray = roundFinder.findMatches("bl1", "2014", 1);

        assertThat(matchdataArray).hasSize(9);

        for (Matchdata matchdata : matchdataArray) {
            matchdata.getGroupID();
            matchdata.getIdTeam1();
            matchdata.getIdTeam2();
            matchdata.getMatchIsFinished();
            ArrayOfMatchResult matchResults = matchdata.getMatchResults();
            for (OLDBMatchResult matchResult : matchResults.getMatchResultArray()) {
                matchResult.getResultTypeId(); // 2: Nach 90 Minuten // 1: Nach
                                               // 45 Minuten
                matchResult.getPointsTeam1(); // Tore Heimmannschaft
                matchResult.getPointsTeam2(); // Tore Gastmannschaft
            }
            OLDBLocation location = matchdata.getLocation();
            location.getLocationID();

            ArrayOfGoal goals = matchdata.getGoals();
            for (OLDBGoal goal : goals.getGoalArray()) {
                de.winkler.betoffice.storage.Goal boGoal = new de.winkler.betoffice.storage.Goal();
                boGoal.getOpenligaid();

                goal.getGoalID();
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
