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

import java.time.LocalDate;
import java.util.Optional;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.betoffice.openligadb.json.OLDBGoal;
import de.betoffice.openligadb.json.OLDBMatch;
import de.winkler.betoffice.dao.GoalDao;
import de.winkler.betoffice.dao.LocationDao;
import de.winkler.betoffice.dao.MatchDao;
import de.winkler.betoffice.dao.PlayerDao;
import de.winkler.betoffice.dao.RoundDao;
import de.winkler.betoffice.dao.SeasonDao;
import de.winkler.betoffice.dao.TeamDao;
import de.winkler.betoffice.service.DateTimeProvider;
import de.winkler.betoffice.storage.Game;
import de.winkler.betoffice.storage.GameList;
import de.winkler.betoffice.storage.Goal;
import de.winkler.betoffice.storage.Group;
import de.winkler.betoffice.storage.Location;
import de.winkler.betoffice.storage.Player;
import de.winkler.betoffice.storage.Season;
import de.winkler.betoffice.storage.Team;
import de.winkler.betoffice.util.LoggerFactory;

/**
 * The default implementation of {@link OpenligadbUpdateService}.
 *
 * @author Andre Winkler
 */
@Service("openligadbUpdateService")
public class DefaultOpenligadbUpdateService implements OpenligadbUpdateService {

    private static final Logger LOG = LoggerFactory.make();

    @Autowired
    private TeamDao teamDao;
    
    @Autowired
    private SeasonDao seasonDao;

    @Autowired
    private RoundDao roundDao;

    @Autowired
    private MatchDao matchDao;

    @Autowired
    private OpenligadbRoundFinder openligadbRoundFinder;

    @Autowired
    private OpenligadbToBetofficeBuilder openligadbToBetofficeBuilder;

    @Autowired
    private LocationDao locationDao;

    @Autowired
    private PlayerDao playerDao;

    @Autowired
    private GoalDao goalDao;

    @Autowired
    private LocationSynchronize locationSynchronize;

    @Autowired
    private PlayerSynchronize playerSynchronize;

    @Autowired
    private DateTimeProvider dateTimeProvider;
    
    // ------------------------------------------------------------------------

    @Override
    @Transactional
    public void updateRound(long seasonId, int roundIndex) {
        createOrUpdateRound(seasonId, roundIndex);
    }

    @Override
    @Transactional
    public void createOrUpdateRound(long seasonId, int roundIndex) {
        LOG.info(
                "Start the openligadb update service for season id=[{}] and roundIndex=[{}]",
                seasonId, roundIndex);

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

        // --------------------------------------------------------------------
        // TODO This works only with a single group per season.
        // --------------------------------------------------------------------
        Group bundesliga = season.getGroups().iterator().next();
        Optional<GameList> roundAtIndex = roundDao.findRound(season, roundIndex);

        GameList roundUnderWork = roundAtIndex.isPresent() ? roundAtIndex.get()
                : createRound(season, bundesliga);

        // The round is persisted. May be i need an update here.
        OLDBMatch[] matches = null;
        try {
            matches = openligadbRoundFinder.findMatches(
                    season.getChampionshipConfiguration()
                            .getOpenligaLeagueShortcut(),
                    season.getChampionshipConfiguration()
                            .getOpenligaLeagueSeason(),
                    roundIndex + 1);
        } catch (OpenligadbConnectionException ex) {
            LOG.error("Aborting the update process! {}", ex.getMessage(), ex.getCause());
            return;
        }

        if (matches == null || matches.length == 0) {
            String error = String.format(
                    "Aborting the update process! "
                            + "No matches found for LeagueShortcut=[%s], LeagueSeason=[%s], groupOrderId=[%d]",
                    season.getChampionshipConfiguration()
                            .getOpenligaLeagueShortcut(),
                    season.getChampionshipConfiguration()
                            .getOpenligaLeagueSeason(),
                    roundIndex + 1);
            LOG.error(error);
            return;
        }

        if (roundUnderWork.getOpenligaid() == null) {
            roundUnderWork.setOpenligaid(Long.valueOf(matches[0].getGroup().getGroupID()));
        } else {
            long openligaGroupid = Long.valueOf(matches[0].getGroup().getGroupID());
            if (openligaGroupid != roundUnderWork.getOpenligaid()) {
                String error = String.format(
                        "Openligadb groupId=[%d] and the stored groupId of betoffice GameList [%d] are different.",
                        openligaGroupid, roundUnderWork.getOpenligaid());
                LOG.error(error);
                throw new IllegalStateException(error);
            }
        }

        locationSynchronize.sync(matches);
        playerSynchronize.sync(matches);

        if (matches.length == 0) {
            LOG.info(
                    "The openligadb has no matches for betoffice! "
                            + "season id=[{}], roundIndex=[{}]",
                    seasonId, roundIndex);
        }

        for (OLDBMatch match : matches) {
            Team boHomeTeam = findBoTeam(match.getTeam1().getTeamId());
            Team boGuestTeam = findBoTeam(match.getTeam2().getTeamId());

            Optional<Game> boMatch = matchDao.find(roundUnderWork, boHomeTeam, boGuestTeam);

            Game matchUnderWork = boMatch.isPresent()
                    ? updateMatch(match, boMatch.get())
                    : createMatch(bundesliga, roundUnderWork, match, boHomeTeam,
                            boGuestTeam);

            if (match.getLocation() != null) {
                Optional<Location> boLocation = locationDao.findByOpenligaid(match.getLocation().getLocationID());
    
                if (boLocation.isPresent()) {
                    matchUnderWork.setLocation(boLocation.get());
                } else {
                    Location unknwonLocation = locationDao.findById(Location.UNKNOWN_LOCATION_ID);
                    matchUnderWork.setLocation(unknwonLocation);
                }
            } else {
                Location unknwonLocation = locationDao.findById(Location.UNKNOWN_LOCATION_ID);
                matchUnderWork.setLocation(unknwonLocation);
            }

            for (OLDBGoal goal : match.getGoals()) {
                Optional<Goal> boGoal = goalDao.findByOpenligaid(goal.getGoalID());

                if (!boGoal.isPresent()) {
                    Goal goalUnderWork = GoalBuilder.build(goal);
                    Optional<Player> boPlayer = playerDao.findByOpenligaid(goal.getGoalGetterID());
                    goalUnderWork.setGame(matchUnderWork);
                    matchUnderWork.addGoal(goalUnderWork);
                    goalUnderWork.setPlayer(boPlayer.get());
                    goalDao.save(goalUnderWork);
                }
            }

            matchDao.save(matchUnderWork);
        }

        LocalDate bestRoundDate = roundUnderWork.findBestRoundDate();
        roundUnderWork.setDateTime(bestRoundDate.atTime(0, 0).atZone(dateTimeProvider.defaultZoneId()));
        roundDao.save(roundUnderWork);
    }

    private Game updateMatch(OLDBMatch match, Game matchUnderWork) {
        if (matchUnderWork.getOpenligaid() == null) {
            matchUnderWork.setOpenligaid(Long.valueOf(match.getMatchID()));
        } else if (matchUnderWork.getOpenligaid() != match.getMatchID().longValue()) {
            String error = String.format(
                    "Openligadb matchId=[%d] and stored matchId of betoffice game [%d] are different.",
                    match.getMatchID(), matchUnderWork.getOpenligaid());
            LOG.error(error);
            throw new IllegalStateException(error);
        }
        openligadbToBetofficeBuilder.updateGameDate(matchUnderWork, match);
        openligadbToBetofficeBuilder.updateGameResult(matchUnderWork, match);
        return matchUnderWork;
    }

    private Game createMatch(Group bundesliga, GameList roundUnderWork,
            OLDBMatch match, Team boHomeTeam, Team boGuestTeam) {

        Game newMatch = openligadbToBetofficeBuilder.buildGame(match, boHomeTeam, boGuestTeam);
        newMatch.setGroup(bundesliga);
        newMatch.setOpenligaid(Long.valueOf(match.getMatchID()));
        matchDao.save(newMatch);
        roundUnderWork.addGame(newMatch);
        openligadbToBetofficeBuilder.updateGameResult(newMatch, match);
        return newMatch;
    }

    private GameList createRound(Season season, Group bundesliga) {
        GameList newRound = new GameList();
        newRound.setGroup(bundesliga);
        newRound.setSeason(season);
        newRound.setDateTime(dateTimeProvider.currentDateTime()); // Placeholder. Will be set later...
        season.addGameList(newRound);
        roundDao.save(newRound);
        return newRound;
    }

    private Team findBoTeam(long openligaTeamId) {
        Optional<Team> boHomeTeam = teamDao.findByOpenligaid(openligaTeamId);
        if (!boHomeTeam.isPresent()) {
            String error = String.format(
                    "I did not a find a betoffice team for the openligadb team id [%d].",
                    openligaTeamId);
            LOG.error(error);
            throw new IllegalStateException(error);
        }
        return boHomeTeam.get();
    }

}