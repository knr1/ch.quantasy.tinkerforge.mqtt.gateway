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
package ch.quantasy.gateway.service.device.ledStrip;

import ch.quantasy.gateway.service.device.AbstractDeviceService;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import ch.quantasy.tinkerforge.device.led.LEDStripDevice;
import ch.quantasy.tinkerforge.device.led.LEDStripDeviceCallback;
import ch.quantasy.tinkerforge.device.led.LEDStripDeviceConfig;

/**
 *
 * @author reto
 */
public class LEDStripService extends AbstractDeviceService<LEDStripDevice, LEDStripServiceContract> implements LEDStripDeviceCallback {

    private short[][] leds;

    public LEDStripService(LEDStripDevice device) throws MqttException {
        super(device, new LEDStripServiceContract(device));
        addDescription(getServiceContract().INTENT_CONFIG, "chipType: [WS2801|WS2811|WS2812]\n frameDurationInMilliseconds: [0.." + Long.MAX_VALUE + "]\n clockFrequencyOfICsInHz: [10000..2000000]\n numberOfLEDs: [1..320]\n channelMapping: [rgb|rbg|grb|gbr|brg|bgr]");
        addDescription(getServiceContract().INTENT_LEDs, "[{{r,r,...,r}_numLEDs {g,g,...,g}_numLEDs {b,b,...,b}_numLEDs}_3]");

        addDescription(getServiceContract().STATUS_CONFIG, "chipType: [WS2801|WS2811|WS2812]\n frameDurationInMilliseconds: [0.." + Long.MAX_VALUE + "]\n clockFrequencyOfICsInHz: [10000..2000000]\n numberOfLEDs: [1..320]\n channelMapping: [rgb|rbg|grb|gbr|brg|bgr]");

    }

    @Override
    public void messageArrived(String string, MqttMessage mm) throws Exception {
        byte[] payload = mm.getPayload();
        if (payload == null) {
            return;
        }
        try {
            if (string.startsWith(getServiceContract().INTENT_CONFIG)) {
                LEDStripDeviceConfig config = getMapper().readValue(payload, LEDStripDeviceConfig.class
                );
                getDevice().setConfig(config);
            }
            if (string.startsWith(getServiceContract().INTENT_LEDs)) {
                leds = (getMapper().readValue(payload, short[][].class));
                getDevice().readyToPublish(this);
            }
        } catch (IOException ex) {
            Logger.getLogger(LEDStripService.class
                    .getName()).log(Level.SEVERE, null, ex);
            return;
        }
    }

    @Override
    public void configurationChanged(LEDStripDeviceConfig config) {
        addStatus(getServiceContract().STATUS_CONFIG, config);
    }

    @Override
    public short[][] getLEDsToPublish() {
        return leds;
    }
}
