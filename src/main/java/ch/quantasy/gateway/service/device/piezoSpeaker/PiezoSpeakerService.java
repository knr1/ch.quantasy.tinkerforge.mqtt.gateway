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
package ch.quantasy.gateway.service.device.piezoSpeaker;

import ch.quantasy.gateway.service.device.AbstractDeviceService;
import ch.quantasy.tinkerforge.device.piezoSpeaker.BeepParameter;
import ch.quantasy.tinkerforge.device.piezoSpeaker.MorseCodeParameter;
import ch.quantasy.tinkerforge.device.piezoSpeaker.PiezoSpeakerDevice;
import ch.quantasy.tinkerforge.device.piezoSpeaker.PiezoSpeakerDeviceCallback;
import java.net.URI;
import org.eclipse.paho.client.mqttv3.MqttException;

/**
 *
 * @author reto
 */
public class PiezoSpeakerService extends AbstractDeviceService<PiezoSpeakerDevice, PiezoSpeakerServiceContract> implements PiezoSpeakerDeviceCallback {

    public PiezoSpeakerService(PiezoSpeakerDevice device, URI mqttURI) throws MqttException {
        super(mqttURI, device, new PiezoSpeakerServiceContract(device));
        addDescription(getContract().INTENT_BEEP, "duration: [0..4294967295]\n frequency: [585..7100]");
        addDescription(getContract().INTENT_MORSE, "string: [.|-| |]_60\n frequency: [585..7100]");
        addDescription(getContract().INTENT_CALIBRATE, "[true|false]");
        addDescription(getContract().EVENT_CALIBRATED, "timestamp: [0.." + Long.MAX_VALUE + "]");
        addDescription(getContract().EVENT_BEEP_FINISHED, "timestamp: [0.." + Long.MAX_VALUE + "]");
        addDescription(getContract().EVENT_BEEP_STARTED, "timestamp: [0.." + Long.MAX_VALUE + "]");
        addDescription(getContract().EVENT_CALIBRATED, "timestamp: [0.." + Long.MAX_VALUE + "]");
        addDescription(getContract().EVENT_MORSE_FINISHED, "timestamp: [0.." + Long.MAX_VALUE + "]");
        addDescription(getContract().EVENT_MORSE_STARTED, "timestamp: [0.." + Long.MAX_VALUE + "]");
    }

    @Override
    public void messageReceived(String string, byte[] payload) throws Exception {
        if (string.startsWith(getContract().INTENT_BEEP)) {
            BeepParameter beepParameter = getMapper().readValue(payload, BeepParameter.class);
            getDevice().beep(beepParameter);
        }

        if (string.startsWith(getContract().INTENT_MORSE)) {
            MorseCodeParameter morseCodeParameter = getMapper().readValue(payload, MorseCodeParameter.class);
            getDevice().morse(morseCodeParameter);
        }

        if (string.startsWith(getContract().INTENT_CALIBRATE)) {
            Boolean calibrate = getMapper().readValue(payload, Boolean.class);
            getDevice().calibrate(calibrate);
        }
    }

    @Override
    public void beepInvoked(BeepParameter beepParameter) {
        addEvent(getContract().EVENT_BEEP_STARTED, System.currentTimeMillis());
    }

    @Override
    public void morseCodeInvoked(MorseCodeParameter morseCodeParameter) {
        addEvent(getContract().EVENT_MORSE_STARTED, System.currentTimeMillis());
    }

    @Override
    public void calibrationInvoked() {
        addEvent(getContract().EVENT_CALIBRATED, System.currentTimeMillis());
    }

    @Override
    public void beepFinished() {
        addEvent(getContract().EVENT_BEEP_FINISHED, System.currentTimeMillis());
    }

    @Override
    public void morseCodeFinished() {
        addEvent(getContract().EVENT_MORSE_FINISHED, System.currentTimeMillis());
    }

}
