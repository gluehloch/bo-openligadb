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

import de.msiggi.sportsdata.webservices.ArrayOfMatchResult;
import de.msiggi.sportsdata.webservices.MatchResult;
import de.msiggi.sportsdata.webservices.Matchdata;
import de.winkler.betoffice.storage.Game;
import de.winkler.betoffice.storage.GameResult;
import de.winkler.betoffice.storage.Team;

/**
 * Creates from openligadb data a betoffice player.
 *
 * @author Andre Winkler
 */
public class OpenligadbToBetofficeBuilder {

    public static Game buildGame(Matchdata match, Team boHomeTeam,
            Team boGuestTeam) {

        Game boMatch = new Game();
        boMatch.setDateTime(match.getMatchDateTime().getTime());
        boMatch.setHomeTeam(boHomeTeam);
        boMatch.setGuestTeam(boGuestTeam);

        return boMatch;
    }

    public static Game updateGameDate(Game game, Matchdata match) {
        game.setDateTime(match.getMatchDateTime().getTime());
        return game;
    }
    
    /**
     * Updates the match result.
     *
     * @param game
     *            The betoffice match
     * @param matchData
     *            The openligadb match
     * @return The updated betoffice match
     */
    public static Game updateGameResult(Game game, Matchdata matchData) {
        game.setPlayed(matchData.getMatchIsFinished());

        ArrayOfMatchResult matchResults = matchData.getMatchResults();
        for (MatchResult matchResult : matchResults.getMatchResultArray()) {
            switch (matchResult.getResultTypeId()) {
            case 1: // nach 45 Minuten
                GameResult result = new GameResult(
                        matchResult.getPointsTeam1(),
                        matchResult.getPointsTeam2());
                game.setHalfTimeGoals(result);
                break;
            case 2: // nach 90 Minuten
                game.setResult(matchResult.getPointsTeam1(),
                        matchResult.getPointsTeam2());
                break;
            default: // Undefined!
                break;
            }
        }

        return game;
    }

}
