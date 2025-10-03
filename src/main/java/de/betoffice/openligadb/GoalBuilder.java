/*
 * ============================================================================
 * Project betoffice-openligadb Copyright (c) 2000-2022 by Andre Winkler. All
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

import de.betoffice.openligadb.json.OLDBGoal;
import de.betoffice.storage.season.GoalType;
import de.betoffice.storage.season.entity.GameResult;
import de.betoffice.storage.season.entity.Goal;

/**
 * Builds a {@link Goal} from an openligadb goal.
 *
 * @author Andre Winkler
 */
public class GoalBuilder {

    public static Goal build(OLDBGoal openligadbGoal) {
        Goal boGoal = new Goal();
        boGoal.setOpenligaid(Long.valueOf(openligadbGoal.getGoalID()));
        setMinuteOrDefault(openligadbGoal, boGoal);
        boGoal.setComment(openligadbGoal.getComment() == null ? null : openligadbGoal.getComment().toString());
        boGoal.setResult(new GameResult(openligadbGoal.getScoreTeam1(), openligadbGoal.getScoreTeam2()));

        if (openligadbGoal.getIsOvertime()) {
            boGoal.setGoalType(GoalType.OVERTIME);
        } else if (openligadbGoal.getIsOwnGoal()) {
            boGoal.setGoalType(GoalType.OWNGOAL);
        } else if (openligadbGoal.getIsPenalty()) {
            boGoal.setGoalType(GoalType.PENALTY);
        } else {
            boGoal.setGoalType(GoalType.REGULAR);
        }
        return boGoal;
    }

    public static Goal update(OLDBGoal openligadbGoal, Goal boGoal) {
    	setMinuteOrDefault(openligadbGoal, boGoal);
        boGoal.setComment(openligadbGoal.getComment() == null ? null : openligadbGoal.getComment().toString());
        boGoal.setResult(new GameResult(openligadbGoal.getScoreTeam1(), openligadbGoal.getScoreTeam2()));
        boGoal.setPlayer(null);
        return boGoal;
    }

    private static void setMinuteOrDefault(OLDBGoal oldbGoal, Goal boGoal) {
    	boGoal.setMinute(oldbGoal.getMatchMinute() == null ? -1 : oldbGoal.getMatchMinute());
    }
    
}
