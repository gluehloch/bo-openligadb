/*
 * ============================================================================
 * Project betoffice-openligadb Copyright (c) 2000-2022 by Andre Winkler. All
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

import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;

/**
 * Create a mock for OpenLigaDb.
 *
 * @author Andre Winkler
 */
public class OpenLigaDbMock {

	private static final String DEFAULT_CHARSET = "UTF-8";
	private static final String MOCK_API_URL = "http://localhost:9096";

	public static void prepare() throws IOException {
		InputStream bundesliga_2022_01 = OpenLigaDbMock.class.getResourceAsStream("/bundesliga-2022-01.json");
		String bundesliga_2022_01_asJson = IOUtils.toString(bundesliga_2022_01, DEFAULT_CHARSET);

		InputStream bundesliga_2020_03 = OpenLigaDbMock.class.getResourceAsStream("/bundesliga-2020-03.json");
		String bundesliga_2020_03_asJson = IOUtils.toString(bundesliga_2020_03, DEFAULT_CHARSET);

		stubFor(get("/static-dsl").willReturn(ok()));
		stubFor(get("/getmatchdata/bl1/2022/1")
				.willReturn(ok(bundesliga_2022_01_asJson).withHeader("Content-Type", "application/json")));
		stubFor(get("/getmatchdata/bl1/2020/3")
				.willReturn(ok(bundesliga_2020_03_asJson).withHeader("Content-Type", "application/json")));		
	}

	public static APIUrl prepareApiUrl() {
		APIUrl apiUrl = new APIUrl();
		apiUrl.setOpenligadbUrl(MOCK_API_URL);
		return apiUrl;
	}
	
}
