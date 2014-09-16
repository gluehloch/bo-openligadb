/*
 * $Id: DataPump.java 3750 2013-06-08 07:19:46Z andrewinkler $
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

import java.io.File;

/**
 * The data import interface..
 * 
 * @author by Andre Winkler, $LastChangedBy: andrewinkler $
 * @version $LastChangedRevision: 3750 $ $LastChangedDate: 2013-06-08 09:19:46 +0200 (Sa, 08 Jun 2013) $
 */
public interface DataPump {

    /**
     * Read the XML data from <code>xmlFile</code>, create and return a {@link RoundInfo}
     * information object.
     *
     * @param xmlFile The XML data as a file.
     * @param encoding The file encoding.
     * @return {@link RoundInfo}
     */
    RoundInfo pumpDfb(final File xmlFile, final String encoding);

}