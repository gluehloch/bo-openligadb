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

package de.betoffice.service;

import java.rmi.RemoteException;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.awtools.basic.LoggerFactory;
import de.betoffice.openligadb.GoalBuilder;
import de.betoffice.openligadb.LocationBuilder;
import de.betoffice.openligadb.LocationSynchronize;
import de.betoffice.openligadb.OpenligadbRoundFinder;
import de.betoffice.openligadb.OpenligadbToBetofficeBuilder;
import de.betoffice.openligadb.PlayerBuilder;
import de.betoffice.openligadb.PlayerSynchronize;
import de.msiggi.sportsdata.webservices.Matchdata;
import de.winkler.betoffice.dao.GoalDao;
import de.winkler.betoffice.dao.LocationDao;
import de.winkler.betoffice.dao.MatchDao;
import de.winkler.betoffice.dao.PlayerDao;
import de.winkler.betoffice.dao.RoundDao;
import de.winkler.betoffice.dao.SeasonDao;
import de.winkler.betoffice.dao.TeamDao;
import de.winkler.betoffice.storage.Game;
import de.winkler.betoffice.storage.GameList;
import de.winkler.betoffice.storage.Goal;
import de.winkler.betoffice.storage.Location;
import de.winkler.betoffice.storage.Player;
import de.winkler.betoffice.storage.Season;
import de.winkler.betoffice.storage.Team;

/**
 * The default implementation of {@link OpenligadbUpdateService}.
 *
 * @author Andre Winkler
 */
@Service("openligadbService")
public class DefaultOpenligadbUpdateService implements OpenligadbUpdateService {

    private static final Logger LOG = LoggerFactory.make();

    // -- teamDao -------------------------------------------------------------

    private TeamDao teamDao;

    @Autowired
    public void setTeamDao(TeamDao _teamDao) {
        teamDao = _teamDao;
    }

    // -- seasonDao -----------------------------------------------------------

    private SeasonDao seasonDao;

    @Autowired
    public void setSeasonDao(SeasonDao _seasonDao) {
        seasonDao = _seasonDao;
    }

    // -- roundDao ------------------------------------------------------------

    private RoundDao roundDao;

    @Autowired
    public void setRoundDao(RoundDao _roundDao) {
        roundDao = _roundDao;
    }

    // -- matchDao ------------------------------------------------------------

    private MatchDao matchDao;

    @Autowired
    public void setMatchDao(MatchDao _matchDao) {
        matchDao = _matchDao;
    }

    // -- openligadbRoundFinder -----------------------------------------------

    private OpenligadbRoundFinder openligadbRoundFinder;

    @Autowired
    public void setOpenligadbRoundFinder(
            OpenligadbRoundFinder _openligadbRoundFinder) {

        openligadbRoundFinder = _openligadbRoundFinder;
    }

    // -- locationDao ---------------------------------------------------------

    private LocationDao locationDao;

    @Autowired
    public void setLocationDao(LocationDao _locationDao) {
        locationDao = _locationDao;
    }

    // -- playerDao -----------------------------------------------------------

    private PlayerDao playerDao;

    @Autowired
    public void setPlayerDao(PlayerDao _playerDao) {
        playerDao = _playerDao;
    }

    // -- goalDao -------------------------------------------------------------

    private GoalDao goalDao;

    @Autowired
    public void setGoalDao(GoalDao _goalDao) {
        goalDao = _goalDao;
    }

    // ------------------------------------------------------------------------

    @Override
    @Transactional
    public void updateRound(long seasonId, int roundIndex) {
        if (roundIndex < 0) {
            String error = "Round index to small!";
            LOG.error(error);
            throw new IllegalArgumentException(error);
        }

        Season season = seasonDao.findById(seasonId);
        if (season == null) {
            String error = String.format("No season defined for id=[%s]",
                    seasonId);
            LOG.error(error);
            throw new IllegalArgumentException(error);
        }

        GameList round = roundDao.findRound(season, roundIndex);
        if (round == null) {
            // There is no round with this index here. So it is time to create a
            // new round.
        }

        // The round is already there. May be i need an update here.
        try {
            Matchdata[] matches = openligadbRoundFinder.findMatches(
                    season.getChampionshipConfiguration()
                            .getOpenligaLeagueShortcut(), season
                            .getChampionshipConfiguration()
                            .getOpenligaLeagueSeason(), roundIndex + 1);

            new LocationSynchronize().sync(matches);
            new PlayerSynchronize().sync(matches);

            for (Matchdata match : matches) {
                Team boHomeTeam = findBoTeam(match.getIdTeam1());
                Team boGuestTeam = findBoTeam(match.getIdTeam2());

                Game boMatch = matchDao.find(round, boHomeTeam, boGuestTeam);

                if (boMatch == null) {
                    // INSERT
                    boMatch = OpenligadbToBetofficeBuilder.buildGame(match,
                            boHomeTeam, boGuestTeam);

                    OpenligadbToBetofficeBuilder.updateGameResult(boMatch,
                            match);

                    Location boLocation = locationDao.findByOpenligaid(match
                            .getLocation().getLocationID());
                    boMatch.setLocation(boLocation);

                    for (de.msiggi.sportsdata.webservices.Goal goal : match
                            .getGoals().getGoalArray()) {
                        Goal boGoal = goalDao
                                .findByOpenligaid(goal.getGoalID());
                        if (boGoal == null) {
                            boGoal = GoalBuilder.build(goal);
                        }

                        Player boPlayer = playerDao.findByOpenligaid(goal
                                .getGoalID());
                        boPlayer.getGoals().add(boGoal);
                        playerDao.save(boPlayer);
                    }

                } else {
                    // UPDATE
                    long openligaid = boMatch.getOpenligaid();
                }
            }
        } catch (RemoteException ex) {
            // TODO Auto-generated catch block
            ex.printStackTrace();
        }

    }

    private Team findBoTeam(long openligaTeamId) {
        Team boHomeTeam = teamDao.findByOpenligaid(openligaTeamId);
        if (boHomeTeam == null) {
            String error = String
                    .format("I did not a find a betoffice team for the openligadb team id [%d].",
                            openligaTeamId);
            LOG.error(error);
            throw new IllegalStateException(error);
        }
        return boHomeTeam;
    }

}
