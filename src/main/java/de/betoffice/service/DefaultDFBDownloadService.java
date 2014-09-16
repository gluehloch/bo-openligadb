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

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.SystemUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.betoffice.dfb.DFBDataPumpWithSAX;
import de.betoffice.dfb.DFBDownloadManager;
import de.betoffice.dfb.DFBDownloadURLProvider;
import de.betoffice.dfb.DataPump;
import de.betoffice.dfb.MatchDownload;
import de.betoffice.dfb.MatchInfoPrepare;
import de.betoffice.dfb.MatchInfoStore;
import de.betoffice.dfb.ProxyProperties;
import de.betoffice.dfb.RoundInfo;
import de.winkler.betoffice.dao.SeasonDao;
import de.winkler.betoffice.storage.GameList;
import de.winkler.betoffice.storage.Season;

/**
 * DFB download and update service.
 *
 * @author by Andre Winkler, $LastChangedBy: andrewinkler $
 * @version $LastChangedRevision: 3750 $ $LastChangedDate: 2013-06-08 09:19:46 +0200 (Sat, 08 Jun 2013) $
 */
@Service("dfbDownloadService")
public class DefaultDFBDownloadService implements DFBDownloadService {

    // -- matchInfoPrepare ----------------------------------------------------

    private MatchInfoPrepare matchInfoPrepare;

    @Autowired
    public void setMatchInfoPrepare(MatchInfoPrepare matchInfoPrepare) {
        this.matchInfoPrepare = matchInfoPrepare;
    }

    // -- matchInfoStore ------------------------------------------------------

    private MatchInfoStore matchInfoStore;

    @Autowired
    public void setMatchInfoStore(MatchInfoStore matchInfoStore) {
        this.matchInfoStore = matchInfoStore;
    }

    // -- seasonDao -----------------------------------------------------------

    private SeasonDao seasonDao;

    @Autowired
    public void setSeasonDao(SeasonDao seasonDao) {
        this.seasonDao = seasonDao;
    }

    // ------------------------------------------------------------------------

    /**
     * Creates a {@link RoundInfo} from the DFB site.
     *
     * @param seasonId season id
     * @param roundIndex Index of the round (0..N-1).
     * @param directory Temp directory.
     * @return Information of some games for a round
     */
    @Override
    @Transactional(readOnly = true)
    public RoundInfo download(long seasonId, int roundIndex, File directory) {
        Season season = seasonDao.findById(seasonId);
        if (season == null) {
            throw new IllegalArgumentException();
        }

        String year = StringUtils.substring(season.getYear(), 0, 4);
        String round = Integer.toString(roundIndex + 1);
        if (roundIndex + 1 < 10) {
            round = "0" + round;
        }

        ProxyProperties proxyProperties = ProxyProperties.noProxy();
        DFBDownloadURLProvider urlProvider = new DFBDownloadURLProvider();
        MatchDownload matchDownload = new DFBDownloadManager(proxyProperties,
                urlProvider);

        File xmlFile = matchDownload.download(directory, year, round);

        DataPump dataPump = new DFBDataPumpWithSAX();
        RoundInfo roundInfo = dataPump.pumpDfb(xmlFile,
                matchDownload.getFileEncoding());

        if (roundIndex >= 0) {
        	matchInfoPrepare.analyzeRoundInfo(season, roundIndex, roundInfo);
        }

        return roundInfo;
    }

    /**
     * Creates a {@link RoundInfo} from the DFB site.
     *
     * @param seasonId season id
     * @param roundIndex Index of the round (0..N-1).
     * @return Information of some games for a round
     */
    @Override
    @Transactional(readOnly = true)
    public RoundInfo download(long seasonId, int roundIndex) {
    	File javaIoTmpDir = SystemUtils.getJavaIoTmpDir();
        return download(seasonId, roundIndex, javaIoTmpDir);
    }

    @Override
    @Transactional(readOnly = false)
    public GameList storeDownload(RoundInfo roundInfo) {
    	return matchInfoStore.storeRoundInfo(roundInfo);
    }

    @Override
    @Transactional(readOnly = false)
    public GameList downloadAndStore(long seasonId, int round) {
        RoundInfo roundInfo = download(seasonId, round);
        return storeDownload(roundInfo);
    }

	@Override
	@Transactional(readOnly = false)
	public GameList downloadAndStore(long seasonId, int round, File directory) {
        RoundInfo roundInfo = download(seasonId, round, directory);
        return storeDownload(roundInfo);
	}

}
