/*
 * ============================================================================
 * Project betoffice-openligadb Copyright (c) 2000-2020 by Andre Winkler. All
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

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.winkler.betoffice.service.DateTimeProvider;

/**
 * Transforms date time from/to string. OpenligaDB supports two different time
 * formats:
 * <ul>
 * <li>"MatchDateTime": "2020-10-02T20:30:00"</li>
 * <li>"TimeZoneID": "W. Europe Standard Time"</li>
 * <li>"UTC": "2020-10-02T18:30:00Z"</li>
 * </ul>
 *
 * @author Andre Winkler
 */
@Component
public class DateTimeService {

    private static final DateTimeFormatter MATCH_DATETIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    private final DateTimeProvider dateTimeProvider;

    @Autowired
    public DateTimeService(DateTimeProvider dateTimeProvider) {
        this.dateTimeProvider = dateTimeProvider;
    }

    /**
     * Transforms a date-time string to ZonedDateTime. The expected TimeZone is Europe/Berlin.
     *
     * @param matchDateTime Expected TimeZone is 'Europe/Berlin' or 'W. Europe Standard Time'.
     * @return {@link ZonedDateTime}
     */
    public ZonedDateTime toDate(String matchDateTime) {
        ZoneId defaultZoneId = dateTimeProvider.defaultZoneId();
        LocalDateTime localDateTime = LocalDateTime.from(MATCH_DATETIME_FORMAT.parse(matchDateTime));
        ZonedDateTime zonedDateTime = ZonedDateTime.of(localDateTime, defaultZoneId);
        return zonedDateTime;
    }

    @Deprecated
    public static ZonedDateTime toZonedDateTime(Calendar calendar) {
        TimeZone timeZone = calendar.getTimeZone();
        ZoneId zone = timeZone.toZoneId();
        Date time = calendar.getTime();
        return time.toInstant().atZone(zone);
    }

}
