/*
 * $Id: MatchDownload.java 3825 2013-11-08 21:47:50Z andrewinkler $
 * ============================================================================
 * Project betoffice-exchange
 * Copyright (c) 2000-2013 by Andre  All rights reserved.
 * ============================================================================
 *          GNU GENERAL PUBLIC LICENSE
 *  TERMS AND CONDITIONS FOR COPYING, DISTRIBUTION AND MODIFICATION
 *
 *   This program is free software; you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation; either version 2 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program; if not, write to the Free Software
 *   Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 */

package de.betoffice.dfb;

import java.text.ParseException;
import java.util.Iterator;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.awtools.basic.LoggerFactory;
import de.betoffice.dfb.MatchInfo.Operation;
import de.winkler.betoffice.dao.GroupDao;
import de.winkler.betoffice.dao.MatchDao;
import de.winkler.betoffice.dao.RoundDao;
import de.winkler.betoffice.dao.TeamAliasDao;
import de.winkler.betoffice.storage.Game;
import de.winkler.betoffice.storage.GameList;
import de.winkler.betoffice.storage.Group;
import de.winkler.betoffice.storage.Season;
import de.winkler.betoffice.storage.Team;


/**
 * Prepares the match informations before saving.
 *
 * @author by Andre Winkler, $LastChangedBy: andrewinkler $
 * @version $LastChangedRevision: 3825 $ $LastChangedDate: 2013-11-08 22:47:50 +0100 (Fri, 08 Nov 2013) $
 */
@Component
public class MatchInfoPrepare {

    private final Logger log = LoggerFactory.make();

    private final DFBDateTimeTransformer dateTimeTransformer = new DFBDateTimeTransformer();

    // ------------------------------------------------------------------------
    // -- The beans
    // ------------------------------------------------------------------------

    // -- teamAliasDao --------------------------------------------------------

    private TeamAliasDao teamAliasDao;

    @Autowired
    public void setTeamAliasDao(TeamAliasDao teamAliasDao) {
        this.teamAliasDao = teamAliasDao;
    }

    // -- matchDao ------------------------------------------------------------

    private MatchDao matchDao;

    @Autowired
    public void setMatchDao(MatchDao matchDao) {
        this.matchDao = matchDao;
    }

    // -- groupDao ------------------------------------------------------------

    private GroupDao groupDao;

    @Autowired
    public void setGroupDao(GroupDao groupDao) {
        this.groupDao = groupDao;
    }

    // -- roundDao ------------------------------------------------------------

    private RoundDao roundDao;

    @Autowired
    public void setRoundDao(RoundDao roundDao) {
        this.roundDao = roundDao;
    }

    // ------------------------------------------------------------------------

    /**
     * Analyze the external match data and make an offer of the next persistence
     * step.
     *
     * @param season The season.
     * @param roundNr The round number (0..size-1).
     * @param roundInfo The external round informations.
     */
    public void analyzeRoundInfo(final Season season, final int roundNr,
            final RoundInfo roundInfo) {

        // Bundesliga has only one group: Die 1. Bundesliga!
        Group bundesliga = groupDao.findBySeason(season).get(0);
        GameList round = roundDao.findRound(season, roundNr);

        if (round == null) {
            // The round has to be created!
            roundInfo.setBoRoundId(null);
        } else {
            // The round exists...
            roundInfo.setBoRoundId(round.getId());
        }
        roundInfo.setBoRoundNr(roundNr);
        roundInfo.setBoGroupId(bundesliga.getId());
        roundInfo.setBoSeasonId(season.getId());

        for (Iterator<MatchInfo> i = roundInfo.iterator(); i.hasNext();) {
            analyzeMatchInfo(i.next(), round);
        }
    }

    /**
     * Analyze match and associate domain objects.
     *
     * @param matchInfo Match/game infos
     * @param round The associated round. May be null, if it must be created.
     */
    private void analyzeMatchInfo(final MatchInfo matchInfo,
            final GameList round) {

        try {
            DateTime dateTime = dateTimeTransformer.toDateTime(
                    matchInfo.getDate(), matchInfo.getTime());
            matchInfo.setBoDateTime(dateTime);

            Team boHomeTeam = findTeamByAliasName(matchInfo.getHomeTeam());
            Team boGuestTeam = findTeamByAliasName(matchInfo.getGuestTeam());

            matchInfo.setBoHomeTeamId(boHomeTeam.getId());
            matchInfo.setBoGuestTeamId(boGuestTeam.getId());

            if (round == null) {
                // Everything will be created (Round and games).
                matchInfo.setBoOperation(Operation.INSERT);
            } else {
                Game match = matchDao.find(round, boHomeTeam, boGuestTeam);

                if (match == null) {
                    matchInfo.setBoOperation(Operation.INSERT);
                } else {
                    matchInfo.setBoGameID(match.getId());
                    matchInfo.setBoOperation(Operation.UPDATE);
                }
            }
        } catch (ParseException ex) {
            log.warn("Unable to prepare match info: '{}'", matchInfo);
            matchInfo.setBoOperation(Operation.UNDEFINED);
        } catch (TeamNotFoundException ex) {
            log.warn("Unable to prepare the match info: '{}'", matchInfo);
            matchInfo.setBoOperation(Operation.UNDEFINED);
        } catch (RoundNotFoundException ex) {
            log.warn("Unable to prepare the match info: '{}'", matchInfo);
            matchInfo.setBoOperation(Operation.UNDEFINED);
        }
    }

    private Team findTeamByAliasName(final String aliasName) {
        Team team = teamAliasDao.findByAliasName(aliasName);

        if (team == null) {
            log.warn("Find no team for alias name '{}'", aliasName);
            throw new TeamNotFoundException(aliasName);
        }

        if (log.isInfoEnabled()) {
            log.info("Alias team name: '{}', Find a team for: '{}'", aliasName,
                    team);
        }

        return team;
    }

}
