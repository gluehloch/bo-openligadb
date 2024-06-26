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

/**
 * The service interface for the openligadb update service.
 * 
 * @author Andre Winkler
 */
public interface OpenligadbUpdateService {

    /**
     * Start round and match update from openligadb.
     *
     * @see              #createOrUpdateRound(long, int)
     *
     * @param seasonId   season id
     * @param roundIndex round index (0..N-1)
     */
    @Deprecated
    public void updateRound(long seasonId, int roundIndex);

    /**
     * Start round and match update from openligadb.
     *
     * @param seasonId   season id
     * @param roundIndex round index (0..N-1)
     */
    public void createOrUpdateRound(long seasonId, int roundIndex);

}
