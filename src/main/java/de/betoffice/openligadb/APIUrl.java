/*
 * ============================================================================
 * Project betoffice-openligadb Copyright (c) 2000-2020 by Andre Winkler. All
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

import org.springframework.stereotype.Component;

/**
 * Holds some URLs to access the OpenligaDB REST API.
 * 
 * Example: {@code https://www.openligadb.de/api/getmatchdata/bl1/2016/8}.
 * 
 * @author Andre Winkler
 */
@Component
public class APIUrl {

    /** Default URL Prefix. */
    private String openligadbUrl = "https://www.openligadb.de/api/";

    public void setOpenligadbUrl(String apiUrl) {
        this.openligadbUrl = apiUrl;
    }

    public String getOpenligadbUrl() {
        return openligadbUrl;
    }

    public String getMatchData(String openligadbShortcut, String year, int roundIndex) {
        StringBuilder sb = new StringBuilder(getOpenligadbUrl());
        sb.append("/getmatchdata/").append(openligadbShortcut).append("/").append(year).append("/").append(roundIndex);
        return sb.toString();
    }

}
