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
package ch.quantasy.gateway.service.device.piezoSpeaker;

import ch.quantasy.gateway.service.device.AbstractDeviceService;
import ch.quantasy.tinkerforge.device.piezoSpeaker.BeepParameter;
import ch.quantasy.tinkerforge.device.piezoSpeaker.MorseCodeParameter;
import ch.quantasy.tinkerforge.device.piezoSpeaker.PiezoSpeakerDevice;
import ch.quantasy.tinkerforge.device.piezoSpeaker.PiezoSpeakerDeviceCallback;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 *
 * @author reto
 */
public class PiezoSpeakerService extends AbstractDeviceService<PiezoSpeakerDevice, PiezoSpeakerServiceContract> implements PiezoSpeakerDeviceCallback {

    public PiezoSpeakerService(PiezoSpeakerDevice device, URI mqttURI) throws MqttException {
        super(device, new PiezoSpeakerServiceContract(device), mqttURI);
        addDescription(getServiceContract().INTENT_BEEP, "duration: [0..4294967295]\n frequency: [585..7100]");
        addDescription(getServiceContract().INTENT_MORSE, "string: [.|-| |]_60\n frequency: [585..7100]");
        addDescription(getServiceContract().INTENT_CALIBRATE, "[true|false]");
        addDescription(getServiceContract().EVENT_CALIBRATED, "timestamp: [0.." + Long.MAX_VALUE + "]");
        addDescription(getServiceContract().EVENT_BEEP_FINISHED, "timestamp: [0.." + Long.MAX_VALUE + "]");
        addDescription(getServiceContract().EVENT_BEEP_STARTED, "timestamp: [0.." + Long.MAX_VALUE + "]");
        addDescription(getServiceContract().EVENT_CALIBRATED, "timestamp: [0.." + Long.MAX_VALUE + "]");
        addDescription(getServiceContract().EVENT_MORSE_FINISHED, "timestamp: [0.." + Long.MAX_VALUE + "]");
        addDescription(getServiceContract().EVENT_MORSE_STARTED, "timestamp: [0.." + Long.MAX_VALUE + "]");
    }

    @Override
    public void messageArrived(String string, MqttMessage mm) {
        byte[] payload = mm.getPayload();
        if (payload == null) {
            return;
        }
        try {
            if (string.startsWith(getServiceContract().INTENT_BEEP)) {
                BeepParameter beepParameter = getMapper().readValue(payload, BeepParameter.class);
                getDevice().beep(beepParameter);
            }
        } catch (Exception ex) {
            Logger.getLogger(PiezoSpeakerService.class
                    .getName()).log(Level.SEVERE, null, ex);
            return;
        }
        try {
            if (string.startsWith(getServiceContract().INTENT_MORSE)) {
                MorseCodeParameter morseCodeParameter = getMapper().readValue(payload, MorseCodeParameter.class);
                getDevice().morse(morseCodeParameter);
            }
        } catch (Exception ex) {
            Logger.getLogger(PiezoSpeakerService.class
                    .getName()).log(Level.SEVERE, null, ex);
            return;
        }
        try {
            if (string.startsWith(getServiceContract().INTENT_CALIBRATE)) {
                Boolean calibrate = getMapper().readValue(payload, Boolean.class);
                getDevice().calibrate(calibrate);
            }
        } catch (Exception ex) {
            Logger.getLogger(PiezoSpeakerService.class
                    .getName()).log(Level.SEVERE, null, ex);
            return;
        }

    }

    @Override
    public void beepInvoked(BeepParameter beepParameter) {
        addEvent(getServiceContract().EVENT_BEEP_STARTED, System.currentTimeMillis());
    }

    @Override
    public void morseCodeInvoked(MorseCodeParameter morseCodeParameter) {
        addEvent(getServiceContract().EVENT_MORSE_STARTED, System.currentTimeMillis());
    }

    @Override
    public void calibrationInvoked() {
        addStatus(getServiceContract().EVENT_CALIBRATED, System.currentTimeMillis());
    }

    @Override
    public void beepFinished() {
        addEvent(getServiceContract().EVENT_BEEP_FINISHED, System.currentTimeMillis());
    }

    @Override
    public void morseCodeFinished() {
        addEvent(getServiceContract().EVENT_MORSE_FINISHED, System.currentTimeMillis());
    }

}
