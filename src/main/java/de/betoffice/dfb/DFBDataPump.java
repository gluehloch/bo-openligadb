/*
 * $Id: DFBDataPump.java 3750 2013-06-08 07:19:46Z andrewinkler $
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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;

import de.awtools.basic.LoggerFactory;

/**
 * Startet den Download der DFB Spieltagsdaten und legt anhand dieser Daten
 * einen Spieltag in der Betoffice Datenbank an.
 * 
 * @author by Andre Winkler, $LastChangedBy: andrewinkler $
 * @version $LastChangedRevision: 3750 $ $LastChangedDate: 2013-06-08 09:19:46 +0200 (Sa, 08 Jun 2013) $
 */
public class DFBDataPump implements DataPump {

    private final Logger log = LoggerFactory.make();

    public RoundInfo pumpDfb(final File xmlFile,
            final String encoding) {

        RoundInfo roundInfo = new RoundInfo();

        try {
            String text = IOUtils.toString(new FileInputStream(xmlFile),
                    encoding);
            Document document = DocumentHelper.parseText(text);

            List<?> termine = document.selectNodes("//ergx/termin");
            for (Object termin : termine) {
                Element terminNode = (Element) termin;
                String matchid = terminNode.element("spielid").getText();
                String date = terminNode.element("datum").getText();
                String time = terminNode.element("zeit").getText();
                String homeTeam = terminNode.element("teama").getText();
                String guestTeam = terminNode.element("teamb").getText();
                String homeGoals = terminNode.element("punkte_a").getText();
                String guestGoals = terminNode.element("punkte_b").getText();
                String spieltag = terminNode.element("spieltag").getText();

                MatchInfo matchInfo = new MatchInfo();
                matchInfo.setMatchid(matchid);
                matchInfo.setDate(date);
                matchInfo.setTime(time);
                matchInfo.setHomeTeam(homeTeam);
                matchInfo.setGuestTeam(guestTeam);
                try {
                    matchInfo.setHomeGoals(Integer.parseInt(homeGoals));
                } catch (NumberFormatException ex) {
                    matchInfo.setHomeGoals(-1);
                }
                try {
                    matchInfo.setGuestGoals(Integer.parseInt(guestGoals));
                } catch (NumberFormatException ex) {
                    matchInfo.setGuestGoals(-1);
                }
                try {
                    matchInfo.setRound(Integer.parseInt(spieltag));
                } catch (NumberFormatException ex) {
                    matchInfo.setRound(-1);
                }
                roundInfo.add(matchInfo);

                if (log.isDebugEnabled()) {
                    log.debug(matchInfo.toString());
                }
            }
        } catch (IOException ex) {
            log.error("IOException", ex);
            throw new RuntimeException(ex);
        } catch (DocumentException ex) {
            log.error("DocumentException", ex);
            throw new RuntimeException(ex);
        }

        return roundInfo;
    }

}
