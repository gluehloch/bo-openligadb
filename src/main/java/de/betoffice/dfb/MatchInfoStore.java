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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.awtools.basic.LoggerFactory;
import de.betoffice.dfb.MatchInfo.Operation;
import de.winkler.betoffice.dao.GroupDao;
import de.winkler.betoffice.dao.MatchDao;
import de.winkler.betoffice.dao.RoundDao;
import de.winkler.betoffice.dao.SeasonDao;
import de.winkler.betoffice.dao.TeamDao;
import de.winkler.betoffice.storage.Game;
import de.winkler.betoffice.storage.GameList;
import de.winkler.betoffice.storage.Group;
import de.winkler.betoffice.storage.Season;
import de.winkler.betoffice.storage.Team;

/**
 * Saves the match informations to the database.
 *
 * @author by Andre Winkler, $LastChangedBy: andrewinkler $
 * @version $LastChangedRevision: 3825 $ $LastChangedDate: 2013-11-08 22:47:50 +0100 (Fri, 08 Nov 2013) $
 */
@Component
public class MatchInfoStore {

    private final Logger log = LoggerFactory.make();

    private final DFBDateTimeTransformer dateTimeTransformer = new DFBDateTimeTransformer();

    // ------------------------------------------------------------------------
    // -- The beans
    // ------------------------------------------------------------------------

    // -- teamAliasDao --------------------------------------------------------

    private TeamDao teamDao;

    @Autowired
    public void setTeamDao(TeamDao teamDao) {
        this.teamDao = teamDao;
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

    // -- seasonDao -----------------------------------------------------------

    private SeasonDao seasonDao;

    @Autowired
    public void setSeasonDao(SeasonDao seasonDao) {
        this.seasonDao = seasonDao;
    }

    // ------------------------------------------------------------------------

    /**
     * Takes the external round infos and brings the infos to the database.
     *
     * @param roundInfo The external round informations.
     * @return The created or updated round
     */
    public GameList storeRoundInfo(final RoundInfo roundInfo) {
        Season season = seasonDao.findById(roundInfo.getBoSeasonId());
        Group bundesliga = groupDao.findById(roundInfo.getBoGroupId());

        GameList round = null;
        if (roundInfo.getBoRoundId() == null || roundInfo.getBoRoundId() == 0) {
            round = createRound(season, bundesliga, roundInfo.getBoRoundNr(),
                    roundInfo);
        } else {
            round = roundDao.findById(roundInfo.getBoRoundId());
        }

        for (Iterator<MatchInfo> i = roundInfo.iterator(); i.hasNext();) {
            storeMatchInfo(i.next(), round, bundesliga);
        }

        return round;
    }

    private void storeMatchInfo(final MatchInfo matchInfo,
            final GameList round, final Group group) {

        Team homeTeam = teamDao.findById(matchInfo.getBoHomeTeamId());
        Team guestTeam = teamDao.findById(matchInfo.getBoGuestTeamId());

        if (matchInfo.getBoOperation().equals(Operation.INSERT)) {

            log.info(
                    "Insert a new match with home '{}' and guest '{}'",
                    new Object[] { matchInfo.getBoHomeTeamId(),
                            matchInfo.getBoGuestTeamId() });

            Game game = new Game();
            game.setDateTime(matchInfo.getBoDateTime().toDate());
            game.setGroup(group);
            game.setHomeTeam(homeTeam);
            game.setGuestTeam(guestTeam);

            if (matchInfo.isFinished()) {
                game.setResult(matchInfo.getHomeGoals(),
                        matchInfo.getGuestGoals(), true);
            } else {
                game.setPlayed(false);
            }

            round.addGame(game);
            matchDao.save(game);

        } else if (matchInfo.getBoOperation().equals(Operation.UPDATE)) {

            log.info("Update a match with home '{}' and guest '{}'",
                    new Object[] { homeTeam, guestTeam });

            Game game = matchDao.findById(matchInfo.getBoGameId());
            game.setDateTime(matchInfo.getBoDateTime().toDate());

            if (matchInfo.isFinished()) {
                game.setResult(matchInfo.getHomeGoals(),
                        matchInfo.getGuestGoals());
                game.setPlayed(true);
            } else {
                game.setPlayed(false);
            }

            matchDao.update(game);

        } else if (matchInfo.getBoOperation().equals(Operation.UNDEFINED)) {

            log.warn("MatchInfo is not persisted: '{}'", matchInfo);

        } else {

            throw new IllegalStateException("Illegale MatchInfo_Operation.");

        }
    }

    private GameList createRound(final Season season, final Group group,
            final int roundNr, final RoundInfo roundInfo) {

        Map<String, List<String>> dates = new HashMap<String, List<String>>();

        for (Iterator<MatchInfo> i = roundInfo.iterator(); i.hasNext();) {
            MatchInfo matchInfo = i.next();
            if (!dates.containsKey(matchInfo.getDate())) {
                dates.put(matchInfo.getDate(), new ArrayList<String>());

            }
            dates.get(matchInfo.getDate()).add(matchInfo.getDate());
        }

        String bestDate = null;
        int maxSize = 0;

        for (List<String> dateList : dates.values()) {
            int size = dateList.size();
            if (size > maxSize) {
                maxSize = size;
                bestDate = dateList.get(0);
            }
        }

        DateTime bestDateTime;
        try {
            bestDateTime = dateTimeTransformer.toDateTime(bestDate);
        } catch (ParseException ex) {
            bestDateTime = null;

            log.warn("The best round date can not be parsed!", ex);
            if (roundInfo.getMatchInfos().size() > 0) {
                bestDateTime = roundInfo.getMatchInfos().get(0).getBoDateTime();
                log.warn("I take the date of the first match of the round: {}",
                        bestDateTime);
            }
        }

        GameList round = new GameList();
        round.setDateTime(bestDateTime.toDate());
        round.setGroup(group);
        season.addGameList(round);
        roundDao.save(round);

        return round;
    }

}
