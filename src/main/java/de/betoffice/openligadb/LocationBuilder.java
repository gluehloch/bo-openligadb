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

import de.betoffice.openligadb.json.OLDBMatch;
import de.betoffice.storage.season.entity.Location;

/**
 * Builds a betoffice {@link Location}.
 *
 * @author winkler
 */
public class LocationBuilder {

    /**
     * Create from an openligadb match a betoffice location object.
     *
     * @param  match An openligadb match
     * @return       a betoffice location
     */
    public static Location build(OLDBMatch match) {
        Location boLocation = new Location();
        boLocation.setOpenligaid(Long.valueOf(match.getLocation().getLocationID()));
        boLocation.setCity(match.getLocation().getLocationCity());
        boLocation.setName(match.getLocation().getLocationStadium());
        boLocation.setGeodat(null);
        return boLocation;
    }

}
