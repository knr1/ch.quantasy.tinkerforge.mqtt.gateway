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
package ch.quantasy.tinkerforge.device.realtimeClock;

import ch.quantasy.tinkerforge.device.generic.GenericDevice;
import ch.quantasy.tinkerforge.stack.TinkerforgeStackAddress;
import com.tinkerforge.BrickletPiezoSpeaker;
import com.tinkerforge.BrickletRealTimeClock;

import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class RealtimeClockDevice extends GenericDevice<BrickletRealTimeClock, RealtimeClockDeviceCallback> {

    private DateTimeParameter dateTimeParameter;
    private Byte offset;

    public RealtimeClockDevice(TinkerforgeStackAddress address, BrickletRealTimeClock device) throws NotConnectedException, TimeoutException {
        super(address, device);
    }

    @Override
    protected void addDeviceListeners() {
        if (dateTimeParameter != null) {
            this.setDateTime(dateTimeParameter);
        }
        if (offset != null) {
            this.setOffset(offset);
        }

    }

    @Override
    protected void removeDeviceListeners() {

    }

    public void setDateTime(DateTimeParameter dateTimeParameter) {
        try {
            getDevice().setDateTime(dateTimeParameter.getYear(), dateTimeParameter.getMonth(), dateTimeParameter.getDay(), dateTimeParameter.getHour(), dateTimeParameter.getMinute(), dateTimeParameter.getSecond(), dateTimeParameter.getCentisecond(), dateTimeParameter.getWeekday().getValue());
            this.dateTimeParameter = new DateTimeParameter(getDevice().getDateTime());
            super.getCallback().dateTimeChanged(this.dateTimeParameter);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(RealtimeClockDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setOffset(Byte offset) {
        try {
            getDevice().setOffset(offset);
            this.offset = getDevice().getOffset();
            super.getCallback().offsetChanged(this.offset);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(RealtimeClockDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
