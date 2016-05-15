/*
 *   "TiMqWay"
 *
 *    TiMqWay(tm): A gateway to provide an MQTT-View for the Tinkerforge(tm) world (Tinkerforge-MQTT-Gateway).
 *
 *    Copyright (c) 2015 Bern University of Applied Sciences (BFH),
 *    Research Institute for Security in the Information Society (RISIS), Wireless Communications & Secure Internet of Things (WiCom & SIoT),
 *    Quellgasse 21, CH-2501 Biel, Switzerland
 *
 *    Licensed under Dual License consisting of:
 *    1. GNU Affero General Public License (AGPL) v3
 *    and
 *    2. Commercial license
 *
 *
 *    1. This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Affero General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Affero General Public License for more details.
 *
 *     You should have received a copy of the GNU Affero General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 *    2. Licensees holding valid commercial licenses for TiMqWay may use this file in
 *     accordance with the commercial license agreement provided with the
 *     Software or, alternatively, in accordance with the terms contained in
 *     a written agreement between you and Bern University of Applied Sciences (BFH),
 *     Research Institute for Security in the Information Society (RISIS), Wireless Communications & Secure Internet of Things (WiCom & SIoT),
 *     Quellgasse 21, CH-2501 Biel, Switzerland.
 *
 *
 *     For further information contact <e-mail: reto.koenig@bfh.ch>
 *
 *
 */
package ch.quantasy.gateway.service;

/**
 *
 * @author reto
 */
public class ServiceContract {

    public final String ROOT;
    public final String ID;
    public final String ID_TOPIC;
    public final String BASE;
    public final String BASE_TOPIC;
    public final String STATUS;
    public final String STATUS_CONNECTION;
    public final String OFFLINE;
    public final String ONLINE;

    public final String EVENT;
    public final String INTENT;
    public final String DESCRIPTION;

    public ServiceContract(String base, String id) {
        ROOT = "TF";
        BASE = base;
        BASE_TOPIC = ROOT + "/" + BASE;
        ID = id;
        if (ID != null) {
            ID_TOPIC = BASE_TOPIC + "/" + ID;
        } else {
            ID_TOPIC = BASE_TOPIC;
        }

        EVENT = ID_TOPIC + "/event";
        INTENT = ID_TOPIC + "/intent";
        STATUS = ID_TOPIC + "/status";
        DESCRIPTION=BASE_TOPIC+"/description";

        STATUS_CONNECTION = STATUS + "/connection";
        OFFLINE = "offline";
        ONLINE = "online";

    }
    
}
