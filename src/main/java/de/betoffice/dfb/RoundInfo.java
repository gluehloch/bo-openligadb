/*
 * $Id: RoundInfo.java 3835 2013-11-15 19:01:41Z andrewinkler $
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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Holds a list of {@link MatchInfo} objects associated with a round and a
 * season.
 * 
 * @author $Author: andrewinkler $
 * @version $Revision: 3835 $ $Date: 2013-11-15 20:01:41 +0100 (Fr, 15 Nov 2013) $
 */
public class RoundInfo {

    private final List<MatchInfo> matchInfos = new ArrayList<MatchInfo>();

    public void add(final MatchInfo matchInfo) {
        matchInfos.add(matchInfo);
    }

    public Iterator<MatchInfo> iterator() {
        return matchInfos.iterator();
    }

    public List<MatchInfo> getMatchInfos() {
        return matchInfos;
    }
 
    // ------------------------------------------------------------------------

    private Long boSeasonId;

    public Long getBoSeasonId() {
        return boSeasonId;
    }

    public void setBoSeasonId(Long _boSeasonId) {
        boSeasonId = _boSeasonId;
    }

    // ------------------------------------------------------------------------
    
    private String seasonName;
    
    public String getSeasonName() {
        return seasonName;
    }
    
    public void setSeasonName(String _seasonName) {
        seasonName = _seasonName;
    }

    // ------------------------------------------------------------------------

    private String seasonYear;
    
    public String getSeasonYear() {
        return seasonYear;
    }

    public void setSeasonYear(String _seasonYear) {
        seasonYear = _seasonYear;
    }
    
    // ------------------------------------------------------------------------

    private Long boRoundId;

    public Long getBoRoundId() {
        return boRoundId;
    }

    public void setBoRoundId(Long _boRoundId) {
        boRoundId = _boRoundId;
    }

    // ------------------------------------------------------------------------
    
    /** The betoffice round number starting from 0 to N-1. */
    private int boRoundNr;
    
    /**
     * The betoffice round number starting from 0 to N-1. 
     *
     * @return The betoffice round number
     */
    public int getBoRoundNr() {
        return boRoundNr;
    }
    
    /**
     * The betoffice round number starting from 0 to N-1. 
     *
     * @param _boRoundNr The betoffice round number
     */
    public void setBoRoundNr(int _boRoundNr) {
        boRoundNr = _boRoundNr;
    }

    // ------------------------------------------------------------------------

    private Long boGroupId;

    public Long getBoGroupId() {
        return boGroupId;
    }

    public void setBoGroupId(Long _boGroupId) {
        boGroupId = _boGroupId;
    }

}
