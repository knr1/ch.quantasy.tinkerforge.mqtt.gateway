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
package ch.quantasy.gateway.service.device.dualRelay;

import ch.quantasy.gateway.service.device.AbstractDeviceService;
import ch.quantasy.tinkerforge.device.dualRelay.DualRelayDevice;
import ch.quantasy.tinkerforge.device.dualRelay.DualRelayDeviceCallback;
import ch.quantasy.tinkerforge.device.dualRelay.DeviceMonoflopParameters;
import ch.quantasy.tinkerforge.device.dualRelay.DeviceSelectedState;
import ch.quantasy.tinkerforge.device.dualRelay.DeviceState;
import java.net.URI;
import org.eclipse.paho.client.mqttv3.MqttException;

/**
 *
 * @author reto
 */
public class DualRelayService extends AbstractDeviceService<DualRelayDevice, DualRelayServiceContract> implements DualRelayDeviceCallback {

    public DualRelayService(DualRelayDevice device, URI mqttURI) throws MqttException {
        super(mqttURI, device, new DualRelayServiceContract(device));
        publishDescription(getContract().INTENT_MONOFLOP, "relay: [1|2]\n state: [true|false]\n period: [0.." + Long.MAX_VALUE + "]");
        publishDescription(getContract().INTENT_SELECTED_STATE, "relay: [1|2]\n state: [true|false]\n");
        publishDescription(getContract().INTENT_STATE, "relay1: [true|false]\n relay2: [true|false]\n");
        publishDescription(getContract().EVENT_MONOFLOP_DONE, "timestamp: [0.." + Long.MAX_VALUE + "]\n value:\n  relay: [1|2]\n   state: [true|false]\n");
        publishDescription(getContract().STATUS_STATE, "relay1: [true|false]\n relay2: [true|false]\n");

    }

    @Override
    public void messageReceived(String string, byte[] payload) throws Exception {

        if (string.startsWith(getContract().INTENT_MONOFLOP)) {
            DeviceMonoflopParameters parameters = getMapper().readValue(payload, DeviceMonoflopParameters.class);
            getDevice().setMonoflop(parameters);
        }
        if (string.startsWith(getContract().INTENT_SELECTED_STATE)) {
            DeviceSelectedState parameters = getMapper().readValue(payload, DeviceSelectedState.class);
            getDevice().setSelectedState(parameters);
        }
        if (string.startsWith(getContract().INTENT_STATE)) {
            DeviceState parameters = getMapper().readValue(payload, DeviceState.class);
            getDevice().setState(parameters);
        }

    }

    @Override
    public void stateChanged(DeviceState state) {
        publishStatus(getContract().STATUS_STATE, state);
    }

    @Override
    public void monoflopDone(short relay, boolean state) {
        publishEvent(getContract().EVENT_MONOFLOP_DONE, new MonoflopDone(relay, state));

    }

    public static class MonoflopDone {

        protected short relay;
        protected boolean state;

        private MonoflopDone() {
        }

        public MonoflopDone(short relay, boolean state) {
            this.relay = relay;
            this.state = state;
        }

        public short getRelay() {
            return relay;
        }

        public boolean getState() {
            return state;
        }

    }

}
