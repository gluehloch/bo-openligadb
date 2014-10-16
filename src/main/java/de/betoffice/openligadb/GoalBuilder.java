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

import de.winkler.betoffice.storage.GameResult;
import de.winkler.betoffice.storage.Goal;
import de.winkler.betoffice.storage.enums.GoalType;

/**
 * Builds a {@link Goal} from an openligadb goal.
 *
 * @author Andre Winkler
 */
public class GoalBuilder {

    public static Goal build(
            de.msiggi.sportsdata.webservices.Goal openligadbGoal) {
        Goal boGoal = new Goal();
        boGoal.setOpenligaid(Long.valueOf(openligadbGoal.getGoalID()));
        boGoal.setMinute(openligadbGoal.getGoalMatchMinute());
        boGoal.setComment(openligadbGoal.getGoalComment());
        boGoal.setResult(new GameResult(openligadbGoal.getGoalScoreTeam1(),
                openligadbGoal.getGoalScoreTeam2()));

        if (openligadbGoal.getGoalOvertime()) {
            boGoal.setGoalType(GoalType.OVERTIME);
        } else if (openligadbGoal.getGoalOwnGoal()) {
            boGoal.setGoalType(GoalType.OWNGOAL);
        } else if (openligadbGoal.getGoalPenalty()) {
            boGoal.setGoalType(GoalType.PENALTY);
        } else {
            boGoal.setGoalType(GoalType.REGULAR);
        }
        return boGoal;
    }

}
