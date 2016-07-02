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
package ch.quantasy.gateway.service.device.realTimeClock;

import ch.quantasy.gateway.service.device.DeviceServiceContract;
import ch.quantasy.tinkerforge.device.TinkerforgeDeviceClass;
import ch.quantasy.tinkerforge.device.realTimeClock.RealTimeClockDevice;

/**
 *
 * @author reto
 */
public class RealTimeClockServiceContract extends DeviceServiceContract {

    public final String DATE_TIME;
    public final String OFFSET;
    public final String SET;
    private final String INTENT_DATE_TIME;
    public final String INTENT_DATE_TIME_SET;
    public final String STATUS_DATE_TIME;
    public final String INTENT_OFFSET;
    public final String STATUS_OFFSET;
    public final String ALARM;
    public final String INTENT_ALARM;
    public final String STATUS_ALARM;
    public final String EVENT_ALARM;
    public final String CALLBACK_PERIOD;
    public final String INTENT_DATE_TIME_CALLBACK_PERIOD;
    public final String STATUS_DATE_TIME_CALLBACK_PERIOD;
    public final String EVENT_DATE_TIME;

    public RealTimeClockServiceContract(RealTimeClockDevice device) {
        this(device.getUid(), TinkerforgeDeviceClass.getDevice(device.getDevice()).toString());
    }

    public RealTimeClockServiceContract(String id, String device) {
        super(id, device);
        DATE_TIME = "dateTime";
        OFFSET = "offset";
        SET="set";
        
        

        INTENT_DATE_TIME = INTENT + "/" + DATE_TIME;
        INTENT_DATE_TIME_SET=INTENT_DATE_TIME+"/"+SET;
        INTENT_OFFSET = INTENT + "/" + OFFSET;
        STATUS_DATE_TIME = STATUS + "/" + DATE_TIME;
        STATUS_OFFSET = STATUS + "/" + OFFSET;

        ALARM="alarm";
        STATUS_ALARM=STATUS +"/"+ALARM;
        EVENT_ALARM=EVENT+"/"+ALARM;
        INTENT_ALARM=INTENT+"/"+ALARM;
        
        CALLBACK_PERIOD="callbackPeriod";
        STATUS_DATE_TIME_CALLBACK_PERIOD=STATUS_DATE_TIME+"/"+CALLBACK_PERIOD;
        INTENT_DATE_TIME_CALLBACK_PERIOD=INTENT_DATE_TIME+"/"+CALLBACK_PERIOD;
        
        EVENT_DATE_TIME=EVENT+"/"+DATE_TIME;
        
        
        
    }
}
