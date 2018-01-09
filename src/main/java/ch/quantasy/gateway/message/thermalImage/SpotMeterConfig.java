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
package ch.quantasy.gateway.message.thermalImage;

import ch.quantasy.mqtt.gateway.client.message.annotations.AValidator;
import ch.quantasy.mqtt.gateway.client.message.annotations.Range;

/**
 *
 * @author reto
 */
public class SpotMeterConfig extends AValidator {

    @Range(from = 0, to = 78)
    public int columnStart;
    @Range(from = 0, to = 58)
    public int rowStart;
    @Range(from = 1, to = 79)
    public int columnEnd;
    @Range(from = 1, to = 59)
    public int rowEnd;

    public SpotMeterConfig(int columnStart, int rowStart, int columnEnd, int rowEnd) {
        this.columnStart = columnStart;
        this.rowStart = rowStart;
        this.columnEnd = columnEnd;
        this.rowEnd = rowEnd;
    }

    public SpotMeterConfig() {
    }

    public SpotMeterConfig(int[] spotMeterStatistics) {
        if (spotMeterStatistics == null || spotMeterStatistics.length != 4) {
            throw new IllegalArgumentException();
        }
        this.columnStart = spotMeterStatistics[0];
        this.rowStart = spotMeterStatistics[1];
        this.columnEnd = spotMeterStatistics[2];
        this.rowEnd = spotMeterStatistics[3];
    }

}
