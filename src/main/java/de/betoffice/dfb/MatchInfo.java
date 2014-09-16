/*
 * $Id: MatchInfo.java 3835 2013-11-15 19:01:41Z andrewinkler $
 * ============================================================================
 * Project betoffice-exchange
 * Copyright (c) 2000-2013 by Andre Winkler. All rights reserved.
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

import org.joda.time.DateTime;

/**
 * Holds the match infos from an external data source.
 * 
 * @author $Author: andrewinkler $
 * @version $Revision: 3835 $ $Date: 2013-11-15 20:01:41 +0100 (Fr, 15 Nov 2013) $
 */
public class MatchInfo {

    public enum Operation {
        INSERT, UPDATE, UNDEFINED
    }

    //
    // Data direct from the DFB service.
    //
    
    /**
     * This is the match id from the DFB site.
     */
    private String matchid;
    private String date;
    private String time;
    private String homeTeam;
    private String guestTeam;
    private int homeGoals = -1;
    private int guestGoals = -1;
    private int round;

    //
    // Transformed data.
    //
    
    /**
     * Store this
     */
    private boolean boEvaluate;
    private DateTime boDateTime;
    private Long boHomeTeamId;
    private Long boGuestTeamId;
    private Long boGameId;
    private Operation boOperation = Operation.UNDEFINED;

    /**
     * The ID from the DFB site. <b>Very important:</b>
     * <b>This is not the game id of the betoffice database!</b>
     *
     * @return the matchid
     */
    public String getMatchid() {
        return matchid;
    }

    /**
     * The ID from the DFB site. <b>Very important:</b>
     * <b>This is not the game id of the betoffice database!</b>
     * 
     * @param matchid the matchid to set
     */
    public void setMatchid(String matchid) {
        this.matchid = matchid;
    }

    /**
     * @return the date
     */
    public String getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * @return the time
     */
    public String getTime() {
        return time;
    }

    /**
     * @param time the time to set
     */
    public void setTime(String time) {
        this.time = time;
    }

    /**
     * @return the homeTeam
     */
    public String getHomeTeam() {
        return homeTeam;
    }

    /**
     * @param homeTeam the homeTeam to set
     */
    public void setHomeTeam(String homeTeam) {
        this.homeTeam = homeTeam;
    }

    /**
     * @return the guestTeam
     */
    public String getGuestTeam() {
        return guestTeam;
    }

    /**
     * @param guestTeam the guestTeam to set
     */
    public void setGuestTeam(String guestTeam) {
        this.guestTeam = guestTeam;
    }

    /**
     * @return the homeGoals
     */
    public int getHomeGoals() {
        return homeGoals;
    }

    /**
     * @param homeGoals the homeGoals to set
     */
    public void setHomeGoals(int homeGoals) {
        this.homeGoals = homeGoals;
    }

    /**
     * @return the guestGoals
     */
    public int getGuestGoals() {
        return guestGoals;
    }

    /**
     * @param guestGoals the guestGoals to set
     */
    public void setGuestGoals(int guestGoals) {
        this.guestGoals = guestGoals;
    }

    /**
     * @return the round
     */
    public int getRound() {
        return round;
    }

    /**
     * @param round the round to set
     */
    public void setRound(int round) {
        this.round = round;
    }

    /**
     * @return Returns <code>true</code>, if home and guest goals are set or
     *     are equal or greater than zero.
     */
    public boolean isFinished() {
        return ((getHomeGoals() >= 0) && (getGuestGoals() >= 0));
    }

    public String toString() {
        StringBuilder logMsg = new StringBuilder("Runde: ");
        logMsg.append(round).append(", Datum: ");
        logMsg.append(date);
        logMsg.append(", Zeit: ").append(time).append(", Heim: ");
        logMsg.append(homeTeam).append(", Gast: ");
        logMsg.append(guestTeam).append(", HTore: ");
        logMsg.append(homeGoals).append(", GTore: ");
        logMsg.append(guestGoals);
        return logMsg.toString();
    }

    // ------------------------------------------------------------------------

    /**
     * @return the boHomeTeam
     */
    public Long getBoHomeTeamId() {
        return boHomeTeamId;
    }

    /**
     * @param boHomeTeamId the boHomeTeam to set
     */
    public void setBoHomeTeamId(Long _boHomeTeamId) {
        boHomeTeamId = _boHomeTeamId;
    }

    /**
     * @return the boGuestTeam
     */
    public Long getBoGuestTeamId() {
        return boGuestTeamId;
    }

    /**
     * @param boGuestTeamId the boGuestTeam to set
     */
    public void setBoGuestTeamId(Long _boGuestTeamId) {
        boGuestTeamId = _boGuestTeamId;
    }

    /**
     * @return the boOperation
     */
    public Operation getBoOperation() {
        return boOperation;
    }

    /**
     * @param boOperation the boOperation to set
     */
    public void setBoOperation(Operation boOperation) {
        this.boOperation = boOperation;
    }

    /**
     * The game id of the betoffice database. This could be <code>null</code>,
     * if the game does not exist.
     *
     * @return the match
     */
    public Long getBoGameId() {
        return boGameId;
    }

    /**
     * The game if of the betoffice database. This could be <code>null</code>,
     * if the game does not exist.
     * 
     * @param _boGameId The game id
     */
    public void setBoGameID(Long _boGameId) {
        boGameId = _boGameId;
    }

    /**
     * 
     * @return the evaluate
     */
    public boolean isEvaluate() {
        return boEvaluate;
    }

    /**
     * @param evaluate the evaluate to set
     */
    public void setEvaluate(boolean evaluate) {
        this.boEvaluate = evaluate;
    }

    /**
     * @return date and time
     */
    public DateTime getBoDateTime() {
        return boDateTime;
    }

    /**
     * @param dateTime date and time
     */
    public void setBoDateTime(DateTime _boDateTime) {
        boDateTime = _boDateTime;
    }

}
