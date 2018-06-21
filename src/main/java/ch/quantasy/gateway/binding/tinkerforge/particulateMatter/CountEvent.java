/*
 *   "TiMqWay"
 *
 *    TiMqWay(tm): A gateway to provide an MQTT-View for the Tinkerforge(tm) world (Tinkerforge-MQTT-Gateway).
 *
 *    Copyright (c) 2016 Bern University of Applied Sciences (BFH),
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
package ch.quantasy.gateway.binding.tinkerforge.particulateMatter;

import ch.quantasy.mqtt.gateway.client.message.AnEvent;
import ch.quantasy.mqtt.gateway.client.message.annotations.Range;

/**
 *
 * @author reto
 */
public class CountEvent extends AnEvent {

    @Range(from = 0, to = Integer.MAX_VALUE)
    public int greater03um;
    @Range(from = 0, to = Integer.MAX_VALUE)
    public int greater05um;
    @Range(from = 0, to = Integer.MAX_VALUE)
    public int greater10um;
    @Range(from = 0, to = Integer.MAX_VALUE)
    public int greater25um;
    @Range(from = 0, to = Integer.MAX_VALUE)
    public int greater50um;
    @Range(from = 0, to = Integer.MAX_VALUE)
    public int greater100um;

    public CountEvent() {
    }

    public CountEvent(int greater03um, int greater05um, int greater10um, int greater25um, int greater50um, int greater100um) {
        this.greater03um = greater03um;
        this.greater05um = greater05um;
        this.greater10um = greater10um;
        this.greater25um = greater25um;
        this.greater50um = greater50um;
        this.greater100um = greater100um;
    }

}
