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
package ch.quantasy.tinkerforge.device.accelerometer;

import com.tinkerforge.BrickletAccelerometer;



/**
 *
 * @author reto
 */
public class DeviceAccelerationCallbackThreshold {
    
    private char option;
    private short minX;
    private short maxX;
    private short minY;
    private short maxY;
    private short minZ;
    private short maxZ;

    public DeviceAccelerationCallbackThreshold() {
    }

    public DeviceAccelerationCallbackThreshold(char option, short minX, short maxX, short minY, short maxY, short minZ, short maxZ) {
        this.option = option;
        this.minX = minX;
        this.maxX = maxX;
        this.minY = minY;
        this.maxY = maxY;
        this.minZ = minZ;
        this.maxZ = maxZ;
    }

   

    public DeviceAccelerationCallbackThreshold(BrickletAccelerometer.AccelerationCallbackThreshold threshold) {
        this(threshold.option,threshold.minX,threshold.maxX,threshold.minY, threshold.maxY, threshold.minZ, threshold.maxZ);
    }

    
    public char getOption() {
        return option;
    }

    public short getMaxX() {
        return maxX;
    }

    public short getMaxY() {
        return maxY;
    }

    public short getMaxZ() {
        return maxZ;
    }

    public short getMinX() {
        return minX;
    }

    public short getMinY() {
        return minY;
    }

    public short getMinZ() {
        return minZ;
    }
    
    
    
    
}
