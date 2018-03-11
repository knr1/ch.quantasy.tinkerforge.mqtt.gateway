/*
 * /*
 *  *   "TiMqWay"
 *  *
 *  *    TiMqWay(tm): A gateway to provide an MQTT-View for the Tinkerforge(tm) world (Tinkerforge-MQTT-Gateway).
 *  *
 *  *    Copyright (c) 2016 Bern University of Applied Sciences (BFH),
 *  *    Research Institute for Security in the Information Society (RISIS), Wireless Communications & Secure Internet of Things (WiCom & SIoT),
 *  *    Quellgasse 21, CH-2501 Biel, Switzerland
 *  *
 *  *    Licensed under Dual License consisting of:
 *  *    1. GNU Affero General Public License (AGPL) v3
 *  *    and
 *  *    2. Commercial license
 *  *
 *  *
 *  *    1. This program is free software: you can redistribute it and/or modify
 *  *     it under the terms of the GNU Affero General Public License as published by
 *  *     the Free Software Foundation, either version 3 of the License, or
 *  *     (at your option) any later version.
 *  *
 *  *     This program is distributed in the hope that it will be useful,
 *  *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  *     GNU Affero General Public License for more details.
 *  *
 *  *     You should have received a copy of the GNU Affero General Public License
 *  *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *  *
 *  *
 *  *    2. Licensees holding valid commercial licenses for TiMqWay may use this file in
 *  *     accordance with the commercial license agreement provided with the
 *  *     Software or, alternatively, in accordance with the terms contained in
 *  *     a written agreement between you and Bern University of Applied Sciences (BFH),
 *  *     Research Institute for Security in the Information Society (RISIS), Wireless Communications & Secure Internet of Things (WiCom & SIoT),
 *  *     Quellgasse 21, CH-2501 Biel, Switzerland.
 *  *
 *  *
 *  *     For further information contact <e-mail: reto.koenig@bfh.ch>
 *  *
 *  *
 */
package ch.quantasy.gateway.message.color;

import ch.quantasy.mqtt.gateway.client.message.annotations.AValidator;
import ch.quantasy.mqtt.gateway.client.message.annotations.Choice;
import ch.quantasy.mqtt.gateway.client.message.annotations.Range;
import com.tinkerforge.BrickletColor;

/**
 *
 * @author reto
 */
public class DeviceColorCallbackThreshold extends AValidator {

    @Choice(values = {"x", "o", "i", "<", ">"})
    public char option;
    @Range(from = 0, to = 65535)
    public int minR;
    @Range(from = 0, to = 65535)
    public int maxR;
    @Range(from = 0, to = 65535)
    public int minG;
    @Range(from = 0, to = 65535)
    public int maxG;
    @Range(from = 0, to = 65535)
    public int minB;
    @Range(from = 0, to = 65535)
    public int maxB;
    @Range(from = 0, to = 65535)
    public int minC;
    @Range(from = 0, to = 65535)
    public int maxC;

    public DeviceColorCallbackThreshold() {
    }

    public DeviceColorCallbackThreshold(char option, int minR, int maxR, int minG, int maxG, int minB, int maxB, int minC, int maxC) {
        this.option = option;
        this.minR = minR;
        this.maxR = maxR;
        this.minG = minG;
        this.maxG = maxG;
        this.minB = minB;
        this.maxB = maxB;
        this.minC = minC;
        this.maxC = maxC;
    }

    public DeviceColorCallbackThreshold(BrickletColor.ColorCallbackThreshold threshold) {
        this(threshold.option, threshold.minR, threshold.maxR, threshold.minG, threshold.maxG, threshold.minB, threshold.maxB, threshold.minC, threshold.maxC);
    }
}
