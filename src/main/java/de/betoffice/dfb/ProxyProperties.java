/*
 * $Id: ProxyProperties.java 3750 2013-06-08 07:19:46Z andrewinkler $
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

import java.net.InetSocketAddress;
import java.net.Proxy;

import org.apache.commons.lang.StringUtils;
import org.dbunit.util.Base64;

/**
 * Verwaltet die Proxy Eigenschaften.
 *
 * @author by Andre Winkler, $LastChangedBy: andrewinkler $
 * @version $LastChangedRevision: 3750 $ $LastChangedDate: 2013-06-08 09:19:46 +0200 (Sa, 08 Jun 2013) $
 */
public final class ProxyProperties {

    private final boolean isActive;
    private final String hostName;
    private final int hostPort;
    private final String user;
    private final String password;

    private Proxy proxy;

    public static ProxyProperties noProxy() {
        return new ProxyProperties();
    }

    /**
     * Der Proxy ist ausgeschaltet. Das ist der Default-Fall.
     */
    private ProxyProperties() {
        this.isActive = false;
        this.hostName = null;
        this.hostPort = -1;
        this.user = null;
        this.password = null;
    }

    /**
     * Konstruktor
     *
     * @param isActive Proxy eingeschaltet?
     * @param hostName Die Host Adresse des Proxies.
     * @param hostPort Der Host Port des Proxies.
     * @param user Der User. Z.B.: domain\\user_name
	 * @param password Das Password.
	 */
    public ProxyProperties(final boolean isActive, final String hostName,
            final int hostPort, final String user, final String password) {

        this.isActive = isActive;
        this.hostName = hostName;
        this.hostPort = hostPort;
        this.user = user;
        this.password = password;
    }

    public boolean isActive() {
        return isActive;
    }

    public String getHostName() {
        return hostName;
    }

    public int getHostPort() {
        return hostPort;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    /**
     * Erstellt einen Proxy.
     *
     * @return Ein Proxy.
     */
    public Proxy createProxy() {
        if (proxy == null) {
            if (!isActive() || StringUtils.isEmpty(getHostName())) {
                proxy = Proxy.NO_PROXY;
            } else {
                proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(
                    getHostName(), getHostPort()));
            }
        }
        return proxy;
    }

    /**
     * Liefert den Identifikations-String zur Anmeldung an den Proxy.
     *
     * @return Identity.
     */
    public String identify() {
        StringBuilder sb = new StringBuilder(getUser());
        sb.append(":").append(getPassword());
        return Base64.encodeBytes(sb.toString().getBytes());
    }

}
