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
package ch.quantasy.gateway.service.device.realtimeClock;

import ch.quantasy.gateway.service.device.AbstractDeviceService;
import ch.quantasy.tinkerforge.device.realtimeClock.DateTimeParameter;
import ch.quantasy.tinkerforge.device.realtimeClock.RealtimeClockDevice;
import ch.quantasy.tinkerforge.device.realtimeClock.RealtimeClockDeviceCallback;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 *
 * @author reto
 */
public class RealtimeClockService extends AbstractDeviceService<RealtimeClockDevice, RealtimeClockServiceContract> implements RealtimeClockDeviceCallback {

    public RealtimeClockService(RealtimeClockDevice device, URI mqttURI) throws MqttException {
        super(mqttURI, device, new RealtimeClockServiceContract(device));
        addDescription(getServiceContract().INTENT_DATE_TIME, "year: [2000..2099]\n month: [1..12]\n day: [1..31]\n hour: [0..23]\n minute: [0..59]\n second: [0..59]\n centisecond: [0..9]\n weekday: [monday|tuesday|wednesday|thursday|friday|saturday|sunday]");
        addDescription(getServiceContract().STATUS_DATE_TIME, "year: [2000..2099]\n month: [1..12]\n day: [1..31]\n hour: [0..23]\n minute: [0..59]\n second: [0..59]\n centisecond: [0..9]\n weekday: [monday|tuesday|wednesday|thursday|friday|saturday|sunday]");
        addDescription(getServiceContract().INTENT_OFFSET, "[-128..127]");
        addDescription(getServiceContract().STATUS_OFFSET, "[-128..127]");

    }

    @Override
    public void messageArrived(String string, MqttMessage mm) {
        byte[] payload = mm.getPayload();
        if (payload == null) {
            return;
        }
        try {
            if (string.startsWith(getServiceContract().INTENT_DATE_TIME)) {
                DateTimeParameter parameter = getMapper().readValue(payload, DateTimeParameter.class);
                getDevice().setDateTime(parameter);
            }

            if (string.startsWith(getServiceContract().INTENT_OFFSET)) {
                Byte morseCodeParameter = getMapper().readValue(payload, Byte.class);
                getDevice().setOffset(morseCodeParameter);
            }
        } catch (Exception ex) {
            Logger.getLogger(RealtimeClockService.class
                    .getName()).log(Level.SEVERE, null, ex);
            return;
        }

    }

    @Override
    public void dateTimeChanged(DateTimeParameter dateTimeParameter) {
        addStatus(getServiceContract().STATUS_DATE_TIME, dateTimeParameter);
    }

    @Override
    public void offsetChanged(byte offset) {
        addStatus(getServiceContract().STATUS_OFFSET, offset);
    }

    
}
