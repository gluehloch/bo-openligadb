/*
 * $Id: DFBDownloadManager.java 3825 2013-11-08 21:47:50Z andrewinkler $
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

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.net.UnknownHostException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;

import de.awtools.basic.LoggerFactory;

/**
 * Startet den Download von Spieldaten von den DFB Seiten.
 *
 * @author by Andre Winkler, $LastChangedBy: andrewinkler $
 * @version $LastChangedRevision: 3825 $ $LastChangedDate: 2013-11-08 22:47:50 +0100 (Fr, 08 Nov 2013) $
 */
public final class DFBDownloadManager implements MatchDownload {

    /** Zeichensatzkodierung der DFB Seiten. */
    private static final String ISO_8859_1 = "ISO-8859-1";

    private final Logger log = LoggerFactory.make();

    private final Logger downloadLog = org.slf4j.LoggerFactory
            .getLogger("de.winkler.betoffice.datapump.DFBDownloadManager.Payload");

    private final ProxyProperties proxyProperties;

    private final URLProvider urlProvider;

    /**
     * Konstruktor.
     *
     * @param _proxyProperties Die Proxy Eigenschaften.
     */
    public DFBDownloadManager(final ProxyProperties _proxyProperties,
            final URLProvider _urlProvider) {
        proxyProperties = _proxyProperties;
        urlProvider = _urlProvider;
    }

    /**
     * Zeichensatzkodierung der Datei.
     *
     * @return Zeichensatzkodierung der Datei.
     */
    public String getFileEncoding() {
        return ISO_8859_1;
    }

    /**
     * Download der XML Daten und Ablage der Daten im Dateisystem.
     *
     * @param directory In diesem Verzeichnis werden die Daten abgelegt.
     * @param year Der interessierte Jahrgang. Format: <code>YYYY</code>
     * @param roundNr Die Spieltagsnummer. Format: <code>##</code>. From 01 to 34.
     * @return Die Datei mit den Daten.
     */
    public File download(final File directory, final String year,
            final String roundNr) {

        BufferedReader reader = null;
        URL url = urlProvider.downloadUrl(year, roundNr);

        if (log.isInfoEnabled()) {
            log.info("Start download from url " + url);
        }

        HttpURLConnection uc = null;
        File file = null;

        try {
            Proxy proxy = proxyProperties.createProxy();

            uc = openConnection(url, proxy);
            activeProxy(uc);
            reader = openReader(url, uc);

            String xml = downloadXmlData(reader);

            file = createBackupFile(directory, year, roundNr);
            writeStringToFile(file, xml);

            downloadLog.info(xml);
        } catch (UnsupportedEncodingException ex) {
            log.error("Unsupported encoding", ex);
            throw new RuntimeException(ex);
        } catch (UnknownHostException ex) {
            log.error("Unable to connect to host with URL {}", url, ex);
            throw new RuntimeException(ex);
        } catch (IOException ex) {
            log.error("Unable to download from url " + url, ex);
            throw new RuntimeException(ex);
        } finally {
            if (uc != null) {
                uc.disconnect();
            }
        }

        return file;
    }

    private File createBackupFile(final File directory, final String year,
            final String roundNr) {

        File file;
        file = new File(directory, "export-" + year + "-" + roundNr + ".xml");
        return file;
    }

    private void writeStringToFile(File file, String xml) throws IOException {
        FileUtils.writeStringToFile(file, xml.toString(), getFileEncoding());
    }

    private String downloadXmlData(BufferedReader reader) throws IOException {
        return IOUtils.toString(reader);
    }

    private BufferedReader openReader(URL url, HttpURLConnection uc)
            throws UnsupportedEncodingException, IOException {

        return new BufferedReader(new InputStreamReader(uc.getInputStream(),
                getFileEncoding()));
    }

    private void activeProxy(HttpURLConnection uc) {
        if (proxyProperties.isActive() && proxyProperties.getUser() != null
                && proxyProperties.getPassword() != null) {

            uc.setRequestProperty("Proxy-Authorization", "Basic "
                    + proxyProperties.identify());
        }
    }

    private HttpURLConnection openConnection(URL url, Proxy proxy)
            throws IOException {

        return (HttpURLConnection) url.openConnection(proxy);
    }

}
