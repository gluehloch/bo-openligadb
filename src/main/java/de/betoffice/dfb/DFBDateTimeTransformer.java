/*
 * $Id: DFBDateTimeTransformer.java 3825 2013-11-08 21:47:50Z andrewinkler $
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

package de.betoffice.dfb;

import java.text.ParseException;
import java.util.Locale;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.format.datetime.joda.DateTimeParser;
import org.springframework.stereotype.Component;

/**
 * Transforms the date and time informations of the DFB homepage.
 * 
 * @author $Author: andrewinkler $
 * @version $Revision: 3825 $ $Date: 2012-01-26 20:17:38 +0100 (Do, 26 Jan 2012)$
 */
@Component
public class DFBDateTimeTransformer implements DateTimeTransformer {

    @Override
    public DateTime toDateTime(final String date) throws ParseException {
        DateTimeFormatter dtf = DateTimeFormat.forPattern("dd.MM.YYYY");
        DateTimeParser dtp = new DateTimeParser(dtf);
        return dtp.parse(date, Locale.GERMANY);
    }

    /**
     * The DFB site prefers the following date format:
     * 
     * <pre>
     *   &lt;datum&gt;07.08.2009&lt;/datum&gt;
     *   &lt;zeit&gt;20.30&lt;/zeit&gt;
     * </pre>
     */
    @Override
    public DateTime toDateTime(final String date, final String time)
            throws ParseException {

        DateTimeFormatter dtf = DateTimeFormat.forPattern("dd.MM.YYYY HH.mm");
        DateTimeParser dtp = new DateTimeParser(dtf);
        return dtp.parse(date + " " + time, Locale.GERMANY);
    }

}
