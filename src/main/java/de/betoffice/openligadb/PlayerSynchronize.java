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

import java.util.Optional;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.msiggi.sportsdata.webservices.Goal;
import de.msiggi.sportsdata.webservices.Matchdata;
import de.winkler.betoffice.dao.PlayerDao;
import de.winkler.betoffice.storage.Player;
import de.winkler.betoffice.util.LoggerFactory;

/**
 * Synchronize the players
 *
 * @author Andre Winkler
 */
@Component
public class PlayerSynchronize {

    private static final Logger LOG = LoggerFactory.make();

    // ------------------------------------------------------------------------

    private PlayerDao playerDao;

    @Autowired
    public void setPlayerDao(PlayerDao _playerDao) {
        playerDao = _playerDao;
    }

    // ------------------------------------------------------------------------

    public void sync(Matchdata[] matches) {
        for (Matchdata match : matches) {
            sync(match);
        }
    }

    public void sync(Matchdata match) {
        LOG.info("Player/Goalgetter sync: {} - {}",
                new Object[] { match.getNameTeam1(), match.getNameTeam2() });

        for (Goal goal : match.getGoals().getGoalArray()) {

            if (goal.getGoalGetterID() == 0) {

                LOG.info(
                        "The goal with openligadb ID=[{}] does not define a goalgetter. "
                                + "The goalgetter is undefined/unknown.",
                        goal.getGoalGetterID());

            } else if (StringUtils.isEmpty(goal.getGoalGetterName())) {

                LOG.info(
                        "The goal with openligadb ID=[{}] contains empty values. "
                                + " name=[{}]",
                        goal.getGoalGetterID(), goal.getGoalGetterName());

                Player player = new Player();
                player.setName("TODO: Find my name!");
                player.setVorname("John");
                player.setOpenligaid(Long.valueOf(goal.getGoalGetterID()));
                playerDao.save(player);

            } else {

                Optional<Player> player = playerDao
                        .findByOpenligaid(goal.getGoalGetterID());

                if (!player.isPresent()) {

                    Player newPlayer = PlayerBuilder.build(goal);
                    playerDao.save(newPlayer);

                } else if (!isEqual(player.get(), goal)) {

                    LOG.error(
                            "Player problem: Openligadb goalgetter name[{}] and "
                                    + "betoffice player name[{}] are different. "
                                    + "Problem found at openligadb goal [{}]",
                            new Object[] { goal.getGoalGetterName(),
                                    player.get().getName(), goal.getGoalID() });

                }
            }
        }
    }

    private boolean isEqual(Player boPlayer, Goal goal) {
        return StringUtils.equalsIgnoreCase(boPlayer.getName(),
                goal.getGoalGetterName());
    }

}
