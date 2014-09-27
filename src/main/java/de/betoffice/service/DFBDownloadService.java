/*
 * $Id: DFBDownloadManager.java 3750 2013-06-08 07:19:46Z andrewinkler $
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

package de.betoffice.service;

import java.io.File;

import org.springframework.stereotype.Service;

import de.betoffice.dfb.RoundInfo;
import de.winkler.betoffice.storage.GameList;

/**
 * DFB download and update service.
 *
 * @author by Andre Winkler, $LastChangedBy: andrewinkler $
 * @version $LastChangedRevision: 3750 $ $LastChangedDate: 2013-06-08 09:19:46 +0200 (Sat, 08 Jun 2013) $
 */
public interface DFBDownloadService {

    /**
     * Start a download from the DFB site and stores the result to {@link RoundInfo}.
     * No data will be persisted!
     *
     * @param seasonId season id
     * @param roundIndex round index (0..N-1)
     * @return the extracted information
     */
    public RoundInfo download(long seasonId, int roundIndex);

    /**
     * Creates a {@link RoundInfo} from the DFB site.
     *
     * @param seasonId season id
     * @param roundIndex Index of the round (0..N-1).
     * @param directory Temp directory.
     * @return Information of some games for a round
     */
    public RoundInfo download(long seasonId, int roundIndex, File directory);

    /**
     * Store the downloaded DFB data.
     *
     * @param roundInfo the round information
     * @return the created or updated round
     */
    public GameList storeDownload(RoundInfo roundInfo);

    /**
     * Starts the download and stores the data.
     *
     * @param seasonId season ID
     * @param round Round number (0..N-1)
     * @return The updated or created round.
     */
    public GameList downloadAndStore(long seasonId, int round);

    /**
     * Download and store data from the DFB site.
     *
     * @param seasonId season ID
     * @param round round number (0..N-1)
     * @param directory temp directory
     * @return The updated or created round
     */
    public GameList downloadAndStore(long seasonId, int round, File directory);

}
