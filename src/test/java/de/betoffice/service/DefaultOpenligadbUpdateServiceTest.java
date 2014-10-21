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

package de.betoffice.service;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Test for {@link DefaultOpenligadbUpdateService}.
 *
 * @author Andre Winkler
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/betoffice-datasource.xml",
        "/betoffice-persistence.xml", "/test-mysql-piratestest.xml",
        "/openligadbupdateservice.xml" })
public class DefaultOpenligadbUpdateServiceTest {

    @Autowired
    private OpenligadbUpdateService openligadbUpdateService;

    @Autowired
    private DataSource dataSource;

    @Test
    @Rollback(false)
    public void testOpenligadbUpdateService() {
        openligadbUpdateService.updateRound(22, 0);
        openligadbUpdateService.updateRound(22, 1);
        openligadbUpdateService.updateRound(22, 2);
        openligadbUpdateService.updateRound(22, 3);
        openligadbUpdateService.updateRound(22, 4);
        openligadbUpdateService.updateRound(22, 5);
        openligadbUpdateService.updateRound(22, 6);
        openligadbUpdateService.updateRound(22, 7);
        openligadbUpdateService.updateRound(22, 8);
        openligadbUpdateService.updateRound(22, 9);
        openligadbUpdateService.updateRound(22, 10);
    }

}
