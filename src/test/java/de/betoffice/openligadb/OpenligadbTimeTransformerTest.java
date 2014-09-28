/*
 * ============================================================================
 * Project betoffice-openligadb Copyright (c) 2000-2014 by Andre Winkler. All
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

import static org.fest.assertions.Assertions.assertThat;

import java.util.Calendar;
import java.util.Locale;

import org.apache.xmlbeans.XmlCalendar;
import org.joda.time.DateTime;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Test for {@link OpenligadbTimeTransformer}.
 *
 * @author Andre Winkler
 */
public class OpenligadbTimeTransformerTest {

    @Test
    public void testTimeTransformer() throws Exception {
        OpenligadbTimeTransformer tt = new OpenligadbTimeTransformer();
        DateTime dt = tt.toDateTime("2014-08-22T20:30:00");
        assertThat(dt).isEqualTo(new DateTime(2014, 8, 22, 20, 30));
    }

    @Ignore
    @Test
    public void testJavaUtilCalendar() {
        Calendar calendar = new XmlCalendar();
        DateTime dt = new DateTime(2014, 8, 22, 20, 30);
        calendar = dt.toCalendar(Locale.GERMANY);
        System.out.println(calendar);
        System.out.println(dt);
        DateTime dt2 = new DateTime(calendar);
        System.out.println(dt2);
    }

}
