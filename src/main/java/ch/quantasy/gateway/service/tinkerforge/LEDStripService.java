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
package ch.quantasy.gateway.service.tinkerforge;

import ch.quantasy.gateway.binding.tinkerforge.ledStrip.LEDStripServiceContract;
import ch.quantasy.gateway.binding.tinkerforge.ledStrip.LagingEvent;
import ch.quantasy.gateway.binding.tinkerforge.ledStrip.RenderedEvent;
import ch.quantasy.gateway.service.tinkerforge.AbstractDeviceService;
import org.eclipse.paho.client.mqttv3.MqttException;
import ch.quantasy.tinkerforge.device.ledStrip.LEDStripDevice;
import ch.quantasy.tinkerforge.device.ledStrip.LEDStripDeviceCallback;
import ch.quantasy.gateway.binding.tinkerforge.ledStrip.LEDStripDeviceConfig;
import ch.quantasy.gateway.binding.tinkerforge.ledStrip.ConfigStatus;

import java.net.URI;

/**
 *
 * @author reto
 */
public class LEDStripService extends AbstractDeviceService<LEDStripDevice, LEDStripServiceContract> implements LEDStripDeviceCallback {

    public LEDStripService(LEDStripDevice device, URI mqttURI) throws MqttException {
        super(mqttURI, device, new LEDStripServiceContract(device));
    }

    @Override
    public void configurationChanged(LEDStripDeviceConfig config) {
        readyToPublishStatus(getContract().STATUS_CONFIG, new ConfigStatus(config));
    }

    @Override
    public void frameRendered(int remainingFramesInQueue) {
        readyToPublishEvent(getContract().EVENT_LEDs_RENDERED, new RenderedEvent(remainingFramesInQueue));

    }

    @Override
    public void isLaging() {
        readyToPublishEvent(getContract().EVENT_LAGING, new LagingEvent(true));
    }
}
