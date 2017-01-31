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
package ch.quantasy.tinkerforge.device.io16;

import com.tinkerforge.BrickletIO16;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author reto
 */
public class DeviceConfiguration implements Comparable<DeviceConfiguration>{

    private char port;
    private short pin;
    private char direction;
    private boolean value;

    private DeviceConfiguration() {
    }

    public DeviceConfiguration(char port, short pin, char direction, boolean value) {
        this.port = port;
        this.pin = pin;
        this.direction = direction;
        this.value = value;
    }

    public char getDirection() {
        return direction;
    }

    public char getPort() {
        return port;
    }

    public short getPin() {
        return pin;
    }

    public boolean getValue() {
        return value;
    }

    public String getPortPin() {
        return "" + port + "" + pin;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + this.port;
        hash = 47 * hash + this.pin;
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
        if (this.port != other.port) {
            return false;
        }
        if (this.pin != other.pin) {
            return false;
        }
        return true;
    }

    public static List<DeviceConfiguration> getDeviceConfiguration(BrickletIO16.PortConfiguration configuration) {
        List<DeviceConfiguration> deviceConfigurationList=new LinkedList<>();
        for(int i=0;i<8;i++){
            deviceConfigurationList.add(new DeviceConfiguration(configuration.getPort(), (short)i,(((configuration.directionMask&(1<<i))>>>i)==1?'i':'o') , ((configuration.valueMask&(1<<i))>>>i)==1));
        }
        return deviceConfigurationList;
    }
    
    @Override
    public int compareTo(DeviceConfiguration o) {
        return ((port<<4)+pin)-((o.port<<4)+pin);
    }

}
