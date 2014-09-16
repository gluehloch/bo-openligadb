/*
 * $Id: DFBDownloadURLProvider.java 3825 2013-11-08 21:47:50Z andrewinkler $
 * ============================================================================
 * Project betoffice-exchange
 * Copyright (c) 2000-2012 by Andre Winkler. All rights reserved.
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

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Provides an URL for the {@link DFBDownloadManager}. Here the DFB site
 * is the master URL.
 *
 * @author by Andre Winkler, $LastChangedBy: andrewinkler $
 * @version $LastChangedRevision: 3825 $ $LastChangedDate: 2013-11-08 22:47:50 +0100 (Fr, 08 Nov 2013) $
 */
public class DFBDownloadURLProvider implements URLProvider {

    /**
     * Creates a download URL.
     * 
     * @param year the year of the season (XXXX).
     * @param roundNr the round nr (01..N).
     */
    @Override
    public URL downloadUrl(final String year, final String roundNr) {
        String url = null;
        try {
            url = createURL(year, roundNr);
            return new URL(url);
        } catch (MalformedURLException ex) {
            throw new RuntimeException(
                    "Unable to create a well defined DFB download URL: " + url,
                    ex);
        }
    }

    /**
     * Creates an URL to query the DFB XML informations. Example:
     * <pre>
     * http://www.dfb.de/bliga/bundes/archiv/2009/xml/blm_e_01_09.xml
     * </pre>
     *
     * @param year Interested year. Format: <code>YYYY</code>
     * @param roundNr Round number. Format: <code>##</code>.
     * @return the URL.
     */
    private String createURL(final String year, final String roundNr)
            throws MalformedURLException {

        StringBuilder sb = new StringBuilder(
                "http://www.dfb.de/bliga/bundes/archiv/");
        sb.append(year).append("/xml/blm_e_").append(roundNr).append("_")
                .append(year.substring(2, 4)).append(".xml");

        return sb.toString();
    }

}
