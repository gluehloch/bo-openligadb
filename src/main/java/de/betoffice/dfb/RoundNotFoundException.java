/*
 * $Id: RoundNotFoundException.java 3750 2013-06-08 07:19:46Z andrewinkler $
 * ============================================================================
 * Project betoffice-exchange
 * Copyright (c) 2000-2010 by Andre Winkler. All rights reserved.
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

import de.winkler.betoffice.storage.Season;

/**
 * The round of a season can´t be found.
 * 
 * @author $Author: andrewinkler $
 * @version $Revision: 3750 $ $Date: 2013-06-08 09:19:46 +0200 (Sa, 08 Jun 2013) $
 */
public class RoundNotFoundException extends RuntimeException {

    private static final long serialVersionUID = -656389637492469875L;

    private final Season season;

    private final int dfbRoundIndex;

    public RoundNotFoundException(final Season _season, final int _dfbRoundIndex) {
        season = _season;
        dfbRoundIndex = _dfbRoundIndex;
    }

    public Season getSeason() {
        return season;
    }

    public int getDfbRoundIndex() {
        return dfbRoundIndex;
    }
 
}
