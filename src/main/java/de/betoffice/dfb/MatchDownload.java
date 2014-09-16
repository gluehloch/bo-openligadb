/*
 * $Id: MatchDownload.java 3825 2013-11-08 21:47:50Z andrewinkler $
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
 * Schnittstelle für die sogenannter 'Downloader'. Anhand der Parameter 
 * <code>year</code>, <code>roundNr</code> und dem Zielverzeichnis
 * <code>directory</code> wird der Anwendung ein Datendatei zur Verfügung
 * gestellt. 
 *
 * @author by Andre Winkler, $LastChangedBy: andrewinkler $
 * @version $LastChangedRevision: 3825 $ $LastChangedDate: 2013-11-08 22:47:50 +0100 (Fr, 08 Nov 2013) $
 */
public interface MatchDownload {

	/**
	 * Liefert das Encoding der runtergeladenen Datei.
	 *
	 * @return Das Zeichensatzkodierung der Datei.
	 */
	public String getFileEncoding();

	/**
	 * Liefert eine Datei mit Spieltagsdaten.
	 *
	 * @param directory Das Zielverzeichnis. Hier wird die Datei angelegt.
	 * @param year Jahrgang der Meisterschaft. (XXXX)
	 * @param roundNr Rundennummer der Meisterschaft. (01..N)
	 * @return Eine Datei.
	 */
	public File download(final File directory, final String year,
			final String roundNr);

}
