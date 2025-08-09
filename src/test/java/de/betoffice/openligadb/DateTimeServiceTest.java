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

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import de.betoffice.storage.time.DateTimeProvider;

public class DateTimeServiceTest {

    @Test
    @DisplayName("OpenLigaDB parse match date and time")
    void dateTimeService() {
        DateTimeProvider dateTimeProvider = new DateTimeProviderDummy();
        DateTimeService dateTimeService = new DateTimeService(dateTimeProvider);

        ZonedDateTime zonedDateTime = dateTimeService.toDate("2020-10-02T20:30:00");
        assertThat(zonedDateTime.getZone()).isEqualTo(ZoneId.of("Europe/Berlin"));
        assertThat(zonedDateTime.getHour()).isEqualTo(20);
        assertThat(zonedDateTime.getMinute()).isEqualTo(30);
        assertThat(zonedDateTime.getSecond()).isEqualTo(0);
        assertThat(zonedDateTime.getYear()).isEqualTo(2020);
        assertThat(zonedDateTime.getMonth()).isEqualTo(Month.OCTOBER);
        assertThat(zonedDateTime.getDayOfMonth()).isEqualTo(2);
    }

    private class DateTimeProviderDummy implements DateTimeProvider {
        @Override
        public ZoneId defaultZoneId() {
            return ZoneId.of("Europe/Berlin");
        }

        @Override
        public ZonedDateTime currentDateTime() {
            return ZonedDateTime.of(2020, 10, 2, 20, 30, 0, 0, defaultZoneId());
        }
    }

}
