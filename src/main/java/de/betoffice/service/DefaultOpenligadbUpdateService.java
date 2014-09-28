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

import de.awtools.basic.LoggerFactory;
import de.betoffice.openligadb.OpenligadbRoundFinder;
import de.msiggi.sportsdata.webservices.Matchdata;
import de.winkler.betoffice.dao.RoundDao;
import de.winkler.betoffice.dao.SeasonDao;
import de.winkler.betoffice.dao.TeamDao;
import de.winkler.betoffice.storage.GameList;
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

    // -- openligadbRoundFinder -----------------------------------------------

    private OpenligadbRoundFinder openligadbRoundFinder;

    @Autowired
    public void setOpenligadbRoundFinder(
            OpenligadbRoundFinder _openligadbRoundFinder) {

        openligadbRoundFinder = _openligadbRoundFinder;
    }

    // ------------------------------------------------------------------------

    @Override
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
        } else {
            // The round is already there. May be i need an update here.
            try {
                Matchdata[] matches = openligadbRoundFinder.findMatches(season
                        .getChampionshipConfiguration()
                        .getOpenligaLeagueShortcut(), season
                        .getChampionshipConfiguration()
                        .getOpenligaLeagueSeason(), roundIndex + 1);

                for (Matchdata match : matches) {
                    Team boHomeTeam = findBoTeam(match.getIdTeam1());
                    Team boGuestTeam = findBoTeam(match.getIdTeam2());
                }
            } catch (RemoteException ex) {
                // TODO Auto-generated catch block
                ex.printStackTrace();
            }
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
