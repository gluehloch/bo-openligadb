/*
 * $Id: DFBDataPumpWithSAX.java 3750 2013-06-08 07:19:46Z andrewinkler $
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
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.slf4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import de.awtools.basic.LoggerFactory;

/**
 * Something without the dom4j parser.
 * 
 * @author by Andre Winkler, $LastChangedBy: andrewinkler $
 * @version $LastChangedRevision: 3750 $ $LastChangedDate: 2013-06-08 09:19:46 +0200 (Sa, 08 Jun 2013) $
 */
public class DFBDataPumpWithSAX implements DataPump {

    private final Logger log = LoggerFactory.make();

    private final RoundInfo roundInfo = new RoundInfo();

    private MatchInfo matchInfo;
    private Action action;

    public RoundInfo pumpDfb(final File xmlFile,
            final String encoding) {

        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();

            InputStream inputStream = new FileInputStream(xmlFile);
            Reader reader = new InputStreamReader(inputStream, encoding);

            InputSource inputSource = new InputSource(reader);
            inputSource.setEncoding(encoding);

            DefaultHandler handler = new MyDefaultHandler();
            saxParser.parse(inputSource, handler);

        } catch (IOException ex) {
            log.error("IOException", ex);
            throw new RuntimeException(ex);
        } catch (ParserConfigurationException ex) {
            log.error("DocumentException", ex);
            throw new RuntimeException(ex);
        } catch (SAXException ex) {
            log.error("DocumentException", ex);
            throw new RuntimeException(ex);
        }

        return roundInfo;
    }

    class MyDefaultHandler extends DefaultHandler {
        public void startElement(String uri, String localName, String qName,
                Attributes attributes) throws SAXException {

            log.debug("Start Element : {}", qName);

            if (qName.equals("termin")) {
                matchInfo = new MatchInfo();
            } else if (qName.equals("spielid")) {
                action = new SetSpieltid();
            } else if (qName.equals("datum")) {
                action = new SetDate();
            } else if (qName.equals("zeit")) {
                action = new SetTime();
            } else if (qName.equals("teama")) {
                action = new SetTeamA();
            } else if (qName.equals("teamb")) {
                action = new SetTeamB();
            } else if (qName.equals("punkte_a")) {
                action = new SetPunkteA();
            } else if (qName.equals("punkte_b")) {
                action = new SetPunkteB();
            } else if (qName.equals("spieltag")) {
                action = new SetSpieltag();
            } else {
                action = new DoNothing();
            }
        }

        public void endElement(String uri, String localName, String qName)
                throws SAXException {

            log.debug("End Element : {}", qName);

            if (qName.equals("termin")) {
                roundInfo.add(matchInfo);
            }
        }

        public void characters(char ch[], int start, int length)
                throws SAXException {

            String text = new String(ch, start, length);
            log.debug("Text: {}", text);

            action.execute(text);
        }

    };

    interface Action {
        void execute(String text);
    };

    class DoNothing implements Action {

        public void execute(String text) {
            // Do nothing!
        }
    }

    class SetSpieltid implements Action {

        public void execute(String text) {
            matchInfo.setMatchid(text);
        }

    }

    class SetDate implements Action {

        public void execute(String text) {
            matchInfo.setDate(text);
        }

    }

    class SetTime implements Action {

        public void execute(String text) {
            matchInfo.setTime(text);
        }

    }

    class SetTeamA implements Action {

        public void execute(String text) {
            matchInfo.setHomeTeam(text);
        }

    }

    class SetTeamB implements Action {

        public void execute(String text) {
            matchInfo.setGuestTeam(text);
        }

    }

    class SetPunkteA implements Action {

        public void execute(String text) {
            try {
                matchInfo.setHomeGoals(Integer.parseInt(text));
            } catch (NumberFormatException ex) {
                matchInfo.setHomeGoals(-1);
            }
        }

    }

    class SetPunkteB implements Action {

        public void execute(String text) {
            try {
                matchInfo.setGuestGoals(Integer.parseInt(text));
            } catch (NumberFormatException ex) {
                matchInfo.setGuestGoals(-1);
            }
        }

    }

    class SetSpieltag implements Action {

        public void execute(String text) {
            try {
                matchInfo.setRound(Integer.parseInt(text));
            } catch (NumberFormatException ex) {
                matchInfo.setRound(-1);
            }
        }

    }

}
