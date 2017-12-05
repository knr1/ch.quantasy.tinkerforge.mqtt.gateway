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
package ch.quantasy.gateway.message.intent.laserRangeFinder;

import ch.quantasy.mqtt.gateway.client.message.annotations.AValidator;
import ch.quantasy.mqtt.gateway.client.message.annotations.Choice;
import ch.quantasy.mqtt.gateway.client.message.annotations.Range;
import ch.quantasy.mqtt.gateway.client.message.annotations.Ranges;
import com.tinkerforge.BrickletLaserRangeFinder;

/**
 *
 * @author reto
 */
public class DeviceConfiguration extends AValidator {

    @Range(from = 1, to = 255)
    private short aquisitionCount;

    private boolean quickTermination;
    @Range(from = 0, to = 255)
    private short thresholdValue;
    @Ranges(values = {
        @Range(from = 0, to = 0)
        ,@Range(from = 10, to = 500)})
    private int measurementFrequency;

    private DeviceConfiguration() {
    }

    public DeviceConfiguration(BrickletLaserRangeFinder.Configuration configuration) {
        this.aquisitionCount = configuration.acquisitionCount;
        this.quickTermination = configuration.enableQuickTermination;
        this.thresholdValue = configuration.thresholdValue;
        this.measurementFrequency = configuration.measurementFrequency;
    }

    public DeviceConfiguration(short aquisitionCount, boolean quickTermination, short thresholdValue, int measurementFrequency) {
        this.aquisitionCount = aquisitionCount;
        this.quickTermination = quickTermination;
        this.thresholdValue = thresholdValue;
        this.measurementFrequency = measurementFrequency;
    }

    public short getAquisitionCount() {
        return aquisitionCount;
    }

    public boolean getQuickTermination() {
        return quickTermination;
    }

    public short getThresholdValue() {
        return thresholdValue;
    }

    public int getMeasurementFrequency() {
        return measurementFrequency;
    }

    @Override
    public String toString() {
        return "DeviceConfiguration{" + "aquisitionCount=" + aquisitionCount + ", quickTermination=" + quickTermination + ", thresholdValue=" + thresholdValue + ", measurementFrequency=" + measurementFrequency + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + this.aquisitionCount;
        hash = 79 * hash + (this.quickTermination ? 1 : 0);
        hash = 79 * hash + this.thresholdValue;
        hash = 79 * hash + this.measurementFrequency;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DeviceConfiguration other = (DeviceConfiguration) obj;
        if (this.aquisitionCount != other.aquisitionCount) {
            return false;
        }
        if (this.quickTermination != other.quickTermination) {
            return false;
        }
        if (this.thresholdValue != other.thresholdValue) {
            return false;
        }
        if (this.measurementFrequency != other.measurementFrequency) {
            return false;
        }
        return true;
    }

}
