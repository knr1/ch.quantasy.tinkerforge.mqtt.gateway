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
package ch.quantasy.gateway.service.device.master;

import ch.quantasy.gateway.service.device.AbstractDeviceService;
import ch.quantasy.tinkerforge.device.master.MasterDevice;
import ch.quantasy.tinkerforge.device.master.MasterDeviceCallback;
import ch.quantasy.tinkerforge.device.master.StackCurrentCallbackThreshold;
import ch.quantasy.tinkerforge.device.master.StackVoltageCallbackThreshold;
import ch.quantasy.tinkerforge.device.master.USBVoltageCallbackThreshold;
import java.net.URI;
import org.eclipse.paho.client.mqttv3.MqttException;

/**
 *
 * @author reto
 */
public class MasterService extends AbstractDeviceService<MasterDevice, MasterServiceContract> implements MasterDeviceCallback {

    public MasterService(MasterDevice device, URI mqttURI) throws MqttException {
        super(mqttURI, device, new MasterServiceContract(device));
        addDescription(getContract().INTENT_DEBOUNCE_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        addDescription(getContract().INTENT_STACK_CURRENT_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        addDescription(getContract().INTENT_STATUS_LED_ENABLED, "[true|false]");
        addDescription(getContract().INTENT_STACK_CURRENT_CALLBACK_THRESHOLD, "[option: [x|o|i|<|>]\n min: [0..4095]\n max: [0..4095]");
        addDescription(getContract().INTENT_STACK_VOLTAGE_CALLBACK_PERIOD, "[" + Long.MIN_VALUE + ".." + Long.MAX_VALUE + "]");
        addDescription(getContract().INTENT_STACK_VOLTAGE_CALLBACK_THRESHOLD, "[option: [x|o|i|<|>]\n min: [0..4095]\n max: [0..4095]");
        addDescription(getContract().INTENT_USB_VOLTAGE_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");

        addDescription(getContract().EVENT_RESET, "[0.." + Long.MAX_VALUE + "]");
        addDescription(getContract().EVENT_STACK_VOLTAGE, "timestamp: [0.." + Long.MAX_VALUE + "]\n value: [" + Integer.MIN_VALUE + ".." + Integer.MAX_VALUE + "]");
        addDescription(getContract().EVENT_STACK_VOLTAGE_REACHED, "timestamp: [0.." + Long.MAX_VALUE + "]\n value: [" + Integer.MIN_VALUE + ".." + Integer.MAX_VALUE + "]");

        addDescription(getContract().EVENT_USB_VOLTAGE, "timestamp: [0.." + Long.MAX_VALUE + "]\n value: [" + Integer.MIN_VALUE + ".." + Integer.MAX_VALUE + "]");
        addDescription(getContract().EVENT_USB_VOLTAGE_REACHED, "timestamp: [0.." + Long.MAX_VALUE + "]\n value: [" + Integer.MIN_VALUE + ".." + Integer.MAX_VALUE + "]");

        addDescription(getContract().EVENT_STACK_CURRENT, "timestamp: [0.." + Long.MAX_VALUE + "]\n value: [0.." + Integer.MAX_VALUE + "]");
        addDescription(getContract().EVENT_STACK_CURRENT_REACHED, "timestamp: [0.." + Long.MAX_VALUE + "]\n value: [0.." + Integer.MAX_VALUE + "]");

        addDescription(getContract().STATUS_DEBOUNCE_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        addDescription(getContract().STATUS_STACK_CURRENT_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        addDescription(getContract().STATUS_STATUS_LED_ENABLED, "[true|false]");
        addDescription(getContract().STATUS_CURRENT_CALLBACK_THRESHOLD, "option: [x|o|i|<|>]\n min: [0..4095]\n max: [0..4095]");
        addDescription(getContract().STATUS_STACK_VOLTAGE_CALLBACK_PERIOD, "[" + Long.MIN_VALUE + ".." + Long.MAX_VALUE + "]");
        addDescription(getContract().STATUS_STACK_VOLTAGE_CALLBACK_THRESHOLD, "[option: [x|o|i|<|>]\n min: [0..4095]\n max: [0..4095]");

        addDescription(getContract().STATUS_USB_VOLTAGE_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");

    }

    @Override
    public void messageArrived(String string, byte[] payload) throws Exception {

        if (string.startsWith(getContract().INTENT_STATUS_LED_ENABLED)) {
            Boolean enabled = getMapper().readValue(payload, Boolean.class);
            getDevice().setEnableStatusLED(enabled);
        }

        if (string.startsWith(getContract().INTENT_DEBOUNCE_PERIOD)) {
            Long debouncePeriod = getMapper().readValue(payload, Long.class);
            getDevice().setDebouncePeriod(debouncePeriod);
        }
        if (string.startsWith(getContract().INTENT_STACK_CURRENT_CALLBACK_PERIOD)) {
            Long period = getMapper().readValue(payload, Long.class);
            getDevice().setStackCurrentCallbackPeriod(period);
        }
        if (string.startsWith(getContract().INTENT_STACK_CURRENT_CALLBACK_THRESHOLD)) {
            StackCurrentCallbackThreshold threshold = getMapper().readValue(payload, StackCurrentCallbackThreshold.class);
            getDevice().setStackCurrentCallbackThreshold(threshold);
        }
        if (string.startsWith(getContract().INTENT_STACK_VOLTAGE_CALLBACK_PERIOD)) {
            Long period = getMapper().readValue(payload, Long.class);
            getDevice().setStackVoltageCallbackPeriod(period);
        }
        if (string.startsWith(getContract().INTENT_STACK_VOLTAGE_CALLBACK_THRESHOLD)) {
            StackVoltageCallbackThreshold threshold = getMapper().readValue(payload, StackVoltageCallbackThreshold.class);
            getDevice().setStackVoltageCallbackThreshold(threshold);
        }
        if (string.startsWith(getContract().INTENT_USB_VOLTAGE_CALLBACK_PERIOD)) {
            Long period = getMapper().readValue(payload, Long.class);
            getDevice().setUSBVoltageCallbackPeriod(period);
        }
        if (string.startsWith(getContract().INTENT_USB_VOLTAGE_CALLBACK_THRESHOLD)) {
            USBVoltageCallbackThreshold threshold = getMapper().readValue(payload, USBVoltageCallbackThreshold.class);
            getDevice().setUSBVoltageCallbackThreshold(threshold);
        }
    }

    @Override
    public void debouncePeriodChanged(Long debouncePeriod) {
        addStatus(getContract().STATUS_DEBOUNCE_PERIOD, debouncePeriod);
    }

    @Override
    public void stackCurrentCallbackThresholdChanged(StackCurrentCallbackThreshold stackCurrentCallbackThreshold) {
        addStatus(getContract().STATUS_CURRENT_CALLBACK_THRESHOLD, stackCurrentCallbackThreshold);
    }

    @Override
    public void stackCurrentCallbackPeriodChanged(Long currentCallbackPeriod) {
        addStatus(getContract().STATUS_STACK_CURRENT_CALLBACK_PERIOD, currentCallbackPeriod);
    }

    @Override
    public void stackVoltageCallbackPeriodChanged(long voltageCallbackPeriod) {
        addStatus(getContract().STATUS_STACK_VOLTAGE_CALLBACK_PERIOD, voltageCallbackPeriod);
    }

    @Override
    public void stackVoltageCallbackThresholdChanged(StackVoltageCallbackThreshold voltageCallbackThreshold) {
        addStatus(getContract().STATUS_STACK_VOLTAGE_CALLBACK_THRESHOLD, voltageCallbackThreshold);
    }

    @Override
    public void usbVoltageCallbackPeriodChanged(long usbVoltageCallbackPeriod) {
        addStatus(getContract().STATUS_USB_VOLTAGE_CALLBACK_PERIOD, usbVoltageCallbackPeriod);
    }

    @Override
    public void USBVoltageCallbackThresholdChanged(USBVoltageCallbackThreshold usbVoltageCallbackThreshold) {
        addStatus(getContract().STATUS_USB_VOLTAGE_CALLBACK_THRESHOLD, usbVoltageCallbackThreshold);
    }

    @Override
    public void reset() {
        addEvent(getContract().EVENT_RESET, System.currentTimeMillis());
    }

    @Override
    public void statusLEDEnabledChanged(Boolean statusLEDEnabled) {
        addStatus(getContract().STATUS_LED_ENABLED, statusLEDEnabled);
    }

    @Override
    public void stackCurrent(int i) {
        addEvent(getContract().EVENT_STACK_CURRENT, new StackCurrentEvent(i));
    }

    @Override
    public void stackCurrentReached(int i) {
        addEvent(getContract().EVENT_STACK_CURRENT_REACHED, new StackCurrentEvent(i));
    }

    @Override
    public void stackVoltage(int i) {
        addEvent(getContract().EVENT_STACK_VOLTAGE, new StackVoltageEvent(i));
    }

    @Override
    public void stackVoltageReached(int i) {
        addEvent(getContract().EVENT_STACK_VOLTAGE_REACHED, new StackVoltageEvent(i));
    }

    @Override
    public void usbVoltage(int i) {
        addEvent(getContract().EVENT_USB_VOLTAGE, new USBVoltageEvent(i));
    }

    @Override
    public void usbVoltageReached(int i) {
        addEvent(getContract().EVENT_USB_VOLTAGE_REACHED, new USBVoltageEvent(i));
    }

    public static class StackCurrentEvent {

        protected long timestamp;
        protected int value;

        private StackCurrentEvent() {
        }

        public StackCurrentEvent(int value) {
            this(value, System.currentTimeMillis());
        }

        public StackCurrentEvent(int value, long timeStamp) {
            this.value = value;
            this.timestamp = timeStamp;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public int getValue() {
            return value;
        }

    }

    public static class StackVoltageEvent {

        protected long timestamp;
        protected int value;

        private StackVoltageEvent() {
        }

        public StackVoltageEvent(int value) {
            this(value, System.currentTimeMillis());
        }

        public StackVoltageEvent(int value, long timeStamp) {
            this.value = value;
            this.timestamp = timeStamp;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public int getValue() {
            return value;
        }

    }

    public static class USBVoltageEvent {

        protected long timestamp;
        protected int value;

        private USBVoltageEvent() {
        }

        public USBVoltageEvent(int value) {
            this(value, System.currentTimeMillis());
        }

        public USBVoltageEvent(int value, long timeStamp) {
            this.value = value;
            this.timestamp = timeStamp;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public int getValue() {
            return value;
        }

    }
}
