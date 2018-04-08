/*
 * ============================================================================
 * Project betoffice-openligadb Copyright (c) 2000-2018 by Andre Winkler. All
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

import org.apache.commons.lang.StringUtils;

import de.msiggi.sportsdata.webservices.Goal;
import de.winkler.betoffice.storage.Player;

/**
 * Builds a betoffice {@link Player}.
 *
 * @author Andre Winkler
 */
public class PlayerBuilder {

    public static Player build(Goal goal) {
        Player boPlayer = new Player();
        if (StringUtils.isEmpty(goal.getGoalGetterName())) {
            throw new IllegalArgumentException(
                    "GoalGetter name is empty: goalGetterId=["
                            + goal.getGoalGetterID() + "], goalId=["
                            + goal.getGoalID() + "]");
        }
                
        boPlayer.setName(normalize(goal.getGoalGetterName()));
        boPlayer.setOpenligaid(Long.valueOf(goal.getGoalGetterID()));
        return boPlayer;
    }

    public static String normalize(String playerName) {
        String normalized = StringUtils.replace(playerName, "\r\n", " ");
        normalized = StringUtils.replace(normalized, "\r", " ");
        normalized = StringUtils.replace(normalized, "\n", " ");
        normalized = StringUtils.trimToNull(normalized);
        return normalized;
    }

}
