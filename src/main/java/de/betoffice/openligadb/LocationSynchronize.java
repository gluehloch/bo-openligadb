/*
 * ============================================================================
 * Project betoffice-openligadb Copyright (c) 2000-2023 by Andre Winkler. All
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

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.betoffice.openligadb.json.OLDBMatch;
import de.winkler.betoffice.dao.LocationDao;
import de.winkler.betoffice.storage.Location;
import de.winkler.betoffice.util.LoggerFactory;

/**
 * Synchronize locations.
 *
 * @author Andre Winkler
 */
@Component
public class LocationSynchronize {

    private static final Logger LOG = LoggerFactory.make();

    // ------------------------------------------------------------------------

    private LocationDao locationDao;

    @Autowired
    public void setLocationDao(LocationDao _locationDao) {
        locationDao = _locationDao;
    }

    // ------------------------------------------------------------------------

    public void sync(OLDBMatch[] matches) {
        for (OLDBMatch match : matches) {
            sync(match);
        }
    }

    public void sync(OLDBMatch match) {
        LOG.info("Location sync: {}:{}", new Object[] { match.getTeam1().getTeamName(), match.getTeam2().getTeamName() });

        if (match.getLocation() == null || match.getLocation().getLocationID() == 0) {

            LOG.info(
                    "The match with openligadb ID=[{}] does not define a knwon "
                            + "location. The location is undefined/unknown.",
                    match.getMatchID());

        } else if (match.getLocation().getLocationStadium() == null
                || match.getLocation().getLocationCity() == null) {

            LOG.info(
                    "The location with openligadb ID=[{}] contains null values. "
                            + " city=[{}], stadium=[{}]",
                    match.getLocation().getLocationID(),
                    match.getLocation().getLocationCity(),
                    match.getLocation().getLocationStadium());
        } else {

            Optional<Location> location = locationDao
                    .findByOpenligaid(match.getLocation().getLocationID());

            if (!location.isPresent()) {

                Location matchLocation = LocationBuilder.build(match);
                locationDao.persist(matchLocation);

            } else if (!isEqual(location.get(), match)) {

                LOG.info(
                        "Location problem: openligadb ID=[{}], name=[{}], city=[{}] is not equal to "
                                + "betoffice location name=[{}], city=[{}].",
                        match.getLocation().getLocationID(),
                        match.getLocation().getLocationStadium(),
                        match.getLocation().getLocationCity(),
                        location.get().getName(),
                        location.get().getCity());
            }
        }
    }

    private boolean isEqual(Location boLocation, OLDBMatch match) {
        boolean name = StringUtils.equalsIgnoreCase(boLocation.getName(),
                match.getLocation().getLocationStadium());
        boolean city = StringUtils.equalsIgnoreCase(boLocation.getCity(),
                match.getLocation().getLocationCity());

        return name && city;
    }

}
