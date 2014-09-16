/*
 * $Id: DFBDownloadManagerTest.java 3837 2013-11-16 18:33:07Z andrewinkler $
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
import java.net.UnknownHostException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;

import de.awtools.basic.LoggerFactory;

/**
 * Test für die Klasse {@link DFBDownloadManager}.
 * 
 * @author by Andre Winkler, $LastChangedBy: andrewinkler $
 * @version $LastChangedRevision: 3837 $ $LastChangedDate: 2013-06-08 09:19:46
 *          +0200 (Sa, 08 Jun 2013) $
 */
public class DFBDownloadManagerTest {

	private final Logger log = LoggerFactory.make();

	private File userHomeDirectory;
	private ProxyProperties proxyProperties;

	@Before
	public void setUp() throws Exception {
		String userHome = System.getProperty("user.home");
		userHomeDirectory = new File(userHome + "/.betoffice/test");

		String host = System.getProperty("http.proxyHost");
		int port = 0;
		try {
			port = Integer.parseInt(System.getProperty("http.proxyPort"));
		} catch (NumberFormatException ex) {
			port = 8080;
		}

		String user = System.getProperty("http.proxyUser");
		String pwd = System.getProperty("http.proxyPassword");

		boolean isActive = false;
		try {
			isActive = Boolean.parseBoolean(System
					.getProperty("http.proxyActive"));
		} catch (Exception ex) {
			isActive = false;
		}

		if (isActive) {
			proxyProperties = new ProxyProperties(isActive, host, port, user,
					pwd);
		} else {
			proxyProperties = ProxyProperties.noProxy();
		}

		boolean offline = Boolean.parseBoolean(System
				.getProperty("http.offline"));

		log.info("Host: {}, use proxy: {}, is offline: {}", new Object[] {
				host, isActive, offline });
	}

	@Test
	public void testDFBDownloadManager() {
		URLProvider urlProvider = new DFBDownloadURLProvider();
		DFBDownloadManager downloadManager = new DFBDownloadManager(
				proxyProperties, urlProvider);

		File downloadFile = null;
		boolean disbaleAssertion = false;
		try {
			downloadFile = downloadManager.download(userHomeDirectory, "2009",
					"01");
		} catch (Exception ex) {
			if (ex.getCause() instanceof UnknownHostException) {
				// Ok - no internet connection
				disbaleAssertion = true;
			} else {
				Assert.fail(ex.toString());
			}
		}

		if (!disbaleAssertion) {
			Assert.assertNotNull(downloadFile);
		}
	}

}
