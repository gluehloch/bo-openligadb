/*
 * ============================================================================
 * Project betoffice-openligadb Copyright (c) 2000-2024 by Andre Winkler. All
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

import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.betoffice.openligadb.json.OLDBMatch;
import de.betoffice.openligadb.json.OLDBMatchResult;
import de.winkler.betoffice.storage.Game;
import de.winkler.betoffice.storage.GameResult;
import de.winkler.betoffice.storage.Team;

/**
 * Creates from openligadb data a betoffice player.
 *
 * @author Andre Winkler
 */
@Component
public class OpenligadbToBetofficeBuilder {

    private final DateTimeService dateTimeService;

    @Autowired
    public OpenligadbToBetofficeBuilder(DateTimeService dateTimeService) {
        this.dateTimeService = dateTimeService;
    }

    public Game buildGame(OLDBMatch match, Team boHomeTeam, Team boGuestTeam) {
        Game boMatch = new Game();

        ZonedDateTime zdt = dateTimeService.toDate(match.getMatchDateTime());

        boMatch.setDateTime(zdt);
        boMatch.setHomeTeam(boHomeTeam);
        boMatch.setGuestTeam(boGuestTeam);

        return boMatch;
    }

    public Game updateGameDate(Game game, OLDBMatch match) {
        game.setDateTime(dateTimeService.toDate(match.getMatchDateTime()));
        return game;
    }

    /**
     * Updates the match result.
     *
     * @param  game      The betoffice match
     * @param  matchData The openligadb match
     * @return           The updated betoffice match
     */
    public Game updateGameResult(Game game, OLDBMatch matchData) {
        game.setPlayed(matchData.getMatchIsFinished());

        List<OLDBMatchResult> matchResults = matchData.getMatchResults();
        for (OLDBMatchResult matchResult : matchResults) {
            final GameResult result = GameResult.of(matchResult.getPointsTeam1(), matchResult.getPointsTeam2());
            switch (matchResult.getResultTypeID()) {
            case 1: // nach 45 Minuten
                game.setHalfTimeGoals(result);
                break;
            case 2: // nach 90 Minuten
                game.setResult(result);
                break;
            case 3: // ??? TODO Unbekannt
                break;
            case 4: // Nach Verlängerung
                game.setOverTimeGoals(result);
                game.setKo(true);
                break;
            case 5: // Nach Elfmeterschiessen
                game.setPenaltyGoals(result);
                game.setKo(true);
                break;
            default: // Undefined!
                break;
            }
        }

        return game;
    }

}
