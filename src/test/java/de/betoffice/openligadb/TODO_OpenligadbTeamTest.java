/*
 * ============================================================================
 * Project betoffice-openligadb Copyright (c) 2000-2024 by Andre Winkler. All
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

import java.sql.Connection;
import java.sql.SQLException;

import org.hibernate.SessionFactory;
import org.hibernate.jdbc.Work;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import de.betoffice.database.data.DeleteDatabase;
import de.dbload.Dbload;

/**
 * Checks, if all teams of 'Bundesliga 2014/2015' are able to find by their openliga ID.
 *
 * @author Andre Winkler
 */
@Disabled
public class TODO_OpenligadbTeamTest extends AbstractSpringTestCase {

    @Autowired
    private SessionFactory sessionFactory;

    public void prepareDatabase(final Class<?> clazz) {
        sessionFactory.getCurrentSession().doWork(new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {
                Dbload.read(connection, clazz);
            }
        });
    }

    @Test
    public void testFindAllTeams() throws Exception {
        sessionFactory.getCurrentSession().doWork(new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {
                DeleteDatabase.deleteDatabase(connection);
                Dbload.read(connection, this.getClass(), "bo_team.dat");
            }
        });
    }

    @Test
    public void testGetMatchesOfRound() throws Exception {
        sessionFactory.getCurrentSession().doWork(new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {
                DeleteDatabase.deleteDatabase(connection);
                Dbload.read(connection, this.getClass(),
                        "bo_bundesliga_2014.dat");
            }
        });
    }

}
