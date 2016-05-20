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
package ch.quantasy.gateway.service.device.dualButton;

import ch.quantasy.gateway.service.device.AbstractDeviceService;
import ch.quantasy.tinkerforge.device.dualButton.DualButtonDevice;
import ch.quantasy.tinkerforge.device.dualButton.DualButtonDeviceCallback;
import ch.quantasy.tinkerforge.device.dualButton.DeviceLEDState;
import ch.quantasy.tinkerforge.device.dualButton.DeviceSelectedLEDStateParameters;
import ch.quantasy.tinkerforge.device.dualButton.LEDState;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 *
 * @author reto
 */
public class DualButtonService extends AbstractDeviceService<DualButtonDevice, DualButtonServiceContract> implements DualButtonDeviceCallback {

    public DualButtonService(DualButtonDevice device,URI mqttURI) throws MqttException {
        super(device, new DualButtonServiceContract(device),mqttURI);
        
        addDescription(getServiceContract().INTENT_LED_STATE, "leftLED: [AutoToggleOn|AutoToggleOff|On|Off]\n rightLED: [AutoToggleOn|AutoToggleOff|On|Off] ");
        addDescription(getServiceContract().INTENT_SELECTED_LED_STATE, "led: [AutoToggleOn|AutoToggleOff|On|Off]");
        
        
        addDescription(getServiceContract().EVENT_STATE_CHANGED, "timestamp: [0.." + Long.MAX_VALUE + "]\n led1: [AutoToggleOn|AutoToggleOff|On|Off]\n led2: [AutoToggleOn|AutoToggleOff|On|Off]\n \n led2: [AutoToggleOn|AutoToggleOff|On|Off]\n  switch1: [0|1]\n switch2: [0|1]");
        addDescription(getServiceContract().STATUS_LED_STATE, "led1: [AutoToggleOn|AutoToggleOff|On|Off]\n led2: [AutoToggleOn|AutoToggleOff|On|Off]");
        
    }

    @Override
    public void messageArrived(String string, MqttMessage mm)  {
        byte[] payload = mm.getPayload();
        if (payload == null) {
            return;

        }
        try{
        if (string.startsWith(getServiceContract().INTENT_SELECTED_LED_STATE)) {
            DeviceSelectedLEDStateParameters parameters = getMapper().readValue(payload, DeviceSelectedLEDStateParameters.class);
            getDevice().setSelectedLEDState(parameters);
        }
        if (string.startsWith(getServiceContract().INTENT_LED_STATE)) {
            DeviceLEDState parameters = getMapper().readValue(payload, DeviceLEDState.class);
            getDevice().setLEDState(parameters);
        }
        } catch (Exception ex) {
            Logger.getLogger(DualButtonService.class
                    .getName()).log(Level.SEVERE, null, ex);
            return;
        }

    }

    @Override
    public void ledStateChanged(DeviceLEDState state) {
        addStatus(getServiceContract().STATUS_LED_STATE, state);
    }

    @Override
    public void stateChanged(short s, short s1, short s2, short s3) {
        addEvent(getServiceContract().EVENT_STATE_CHANGED, new StateChangedEvent(s, s1, s2, s3));
    }

    public static class StateChangedEvent {

        protected long timestamp;
        protected LEDState led1;
        protected LEDState led2;
        protected short switch1;
        protected short swicht2;

        private StateChangedEvent() {
        }
public StateChangedEvent(short switch1, short switch2, short led1, short led2) {
            this(LEDState.getLEDStateFor(led1), LEDState.getLEDStateFor(led1), switch1, switch2, System.currentTimeMillis());
        }
        public StateChangedEvent(short switch1, short switch2, LEDState led1, LEDState led2) {
            this(led1, led2, switch1, switch2, System.currentTimeMillis());
        }

        public StateChangedEvent(LEDState led1, LEDState led2, short switch1, short switch2, long timeStamp) {
            this.led1 = led1;
            this.led2 = led2;
            this.switch1 = switch1;
            this.swicht2 = switch2;
            this.timestamp = timeStamp;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public LEDState getLed1() {
            return led1;
        }

        public LEDState getLed2() {
            return led2;
        }

        public short getSwitch1() {
            return switch1;
        }

        public short getSwicht2() {
            return swicht2;
        }

    }

}
