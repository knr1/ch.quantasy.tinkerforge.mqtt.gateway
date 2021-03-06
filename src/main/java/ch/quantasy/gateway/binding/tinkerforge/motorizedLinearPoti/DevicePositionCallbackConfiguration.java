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
package ch.quantasy.gateway.binding.tinkerforge.motorizedLinearPoti;

import ch.quantasy.mqtt.gateway.client.message.annotations.AValidator;
import ch.quantasy.mqtt.gateway.client.message.annotations.Choice;
import ch.quantasy.mqtt.gateway.client.message.annotations.Period;
import ch.quantasy.mqtt.gateway.client.message.annotations.Range;
import com.tinkerforge.BrickletMotorizedLinearPoti;

/**
 *
 * @author reto
 */
public class DevicePositionCallbackConfiguration extends AValidator {

    @Period
    private long period;
    private boolean valueHasToChange;
    @Choice(values = {"x", "o", "i", "<", ">"})
    private char option;
    @Range(from = 0, to = 100)
    private int min;
    @Range(from = 0, to = 100)
    private int max;

    public DevicePositionCallbackConfiguration() {
    }

    public DevicePositionCallbackConfiguration(long period, boolean valueHasToChange, char option, int min, int max) {
        this.period = period;
        this.valueHasToChange = valueHasToChange;
        this.option = option;
        this.min = min;
        this.max = max;
    }

    public DevicePositionCallbackConfiguration(BrickletMotorizedLinearPoti.PositionCallbackConfiguration configuration) {
        this(configuration.period, configuration.valueHasToChange, configuration.option, configuration.min, configuration.max);
    }

    public int getMax() {
        return max;
    }

    public int getMin() {
        return min;
    }

    public char getOption() {
        return option;
    }

    public long getPeriod() {
        return period;
    }

    public boolean valueHasToChange() {
        return valueHasToChange;
    }

}
