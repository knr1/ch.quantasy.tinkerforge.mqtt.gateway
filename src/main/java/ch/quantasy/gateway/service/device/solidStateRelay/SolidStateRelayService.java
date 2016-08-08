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
package ch.quantasy.gateway.service.device.solidStateRelay;

import ch.quantasy.gateway.service.device.AbstractDeviceService;
import ch.quantasy.tinkerforge.device.solidState.DeviceMonoflopParameters;
import ch.quantasy.tinkerforge.device.solidState.SolidStateRelayDevice;
import ch.quantasy.tinkerforge.device.solidState.SolidStateRelayDeviceCallback;
import java.net.URI;
import org.eclipse.paho.client.mqttv3.MqttException;

/**
 *
 * @author reto
 */
public class SolidStateRelayService extends AbstractDeviceService<SolidStateRelayDevice, SolidStateRelayServiceContract> implements SolidStateRelayDeviceCallback {

    public SolidStateRelayService(SolidStateRelayDevice device, URI mqttURI) throws MqttException {
        super(mqttURI, device, new SolidStateRelayServiceContract(device));
        addDescription(getServiceContract().INTENT_MONOFLOP, "state: [true|false]\n period: [0.." + Long.MAX_VALUE + "]");
        addDescription(getServiceContract().INTENT_STATE, "[true|false]");
        addDescription(getServiceContract().EVENT_MONOFLOP_DONE, "timestamp: [0.." + Long.MAX_VALUE + "]\n state: [true|false]");
        addDescription(getServiceContract().STATUS_STATE, "[true|false]");

    }

    @Override
    public void messageArrived(String string, byte[] payload) throws Exception {

        if (string.startsWith(getServiceContract().INTENT_MONOFLOP)) {
            DeviceMonoflopParameters parameters = getMapper().readValue(payload, DeviceMonoflopParameters.class);
            getDevice().setMonoflop(parameters);
        }

        if (string.startsWith(getServiceContract().INTENT_STATE)) {
            Boolean parameters = getMapper().readValue(payload, Boolean.class);
            getDevice().setState(parameters);
        }
    }

    @Override
    public void stateChanged(Boolean state) {
        addStatus(getServiceContract().STATUS_STATE, state);
    }

    @Override
    public void monoflopDone(boolean state) {
        addEvent(getServiceContract().EVENT_MONOFLOP_DONE, new MonoflopDoneEvent(state));
    }

    public static class MonoflopDoneEvent {

        protected long timestamp;
        protected boolean state;

        private MonoflopDoneEvent() {
        }

        public MonoflopDoneEvent(boolean state) {
            this(state, System.currentTimeMillis());
        }

        public MonoflopDoneEvent(boolean state, long timeStamp) {
            this.state = state;
            this.timestamp = timeStamp;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public boolean getState() {
            return state;
        }

    }

}
