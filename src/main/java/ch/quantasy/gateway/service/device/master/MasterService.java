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

    }

    @Override
    public void messageReceived(String string, byte[] payload) throws Exception {

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
        publishStatus(getContract().STATUS_DEBOUNCE_PERIOD, debouncePeriod);
    }

    @Override
    public void stackCurrentCallbackThresholdChanged(StackCurrentCallbackThreshold stackCurrentCallbackThreshold) {
        publishStatus(getContract().STATUS_CURRENT_CALLBACK_THRESHOLD, stackCurrentCallbackThreshold);
    }

    @Override
    public void stackCurrentCallbackPeriodChanged(Long currentCallbackPeriod) {
        publishStatus(getContract().STATUS_STACK_CURRENT_CALLBACK_PERIOD, currentCallbackPeriod);
    }

    @Override
    public void stackVoltageCallbackPeriodChanged(long voltageCallbackPeriod) {
        publishStatus(getContract().STATUS_STACK_VOLTAGE_CALLBACK_PERIOD, voltageCallbackPeriod);
    }

    @Override
    public void stackVoltageCallbackThresholdChanged(StackVoltageCallbackThreshold voltageCallbackThreshold) {
        publishStatus(getContract().STATUS_STACK_VOLTAGE_CALLBACK_THRESHOLD, voltageCallbackThreshold);
    }

    @Override
    public void usbVoltageCallbackPeriodChanged(long usbVoltageCallbackPeriod) {
        publishStatus(getContract().STATUS_USB_VOLTAGE_CALLBACK_PERIOD, usbVoltageCallbackPeriod);
    }

    @Override
    public void USBVoltageCallbackThresholdChanged(USBVoltageCallbackThreshold usbVoltageCallbackThreshold) {
        publishStatus(getContract().STATUS_USB_VOLTAGE_CALLBACK_THRESHOLD, usbVoltageCallbackThreshold);
    }

    @Override
    public void reset() {
        publishEvent(getContract().EVENT_RESET, System.currentTimeMillis());
    }

    @Override
    public void statusLEDEnabledChanged(Boolean statusLEDEnabled) {
        publishStatus(getContract().STATUS_LED_ENABLED, statusLEDEnabled);
    }

    @Override
    public void stackCurrent(int i) {
        publishEvent(getContract().EVENT_STACK_CURRENT, i);
    }

    @Override
    public void stackCurrentReached(int i) {
        publishEvent(getContract().EVENT_STACK_CURRENT_REACHED, i);
    }

    @Override
    public void stackVoltage(int i) {
        publishEvent(getContract().EVENT_STACK_VOLTAGE, i);
    }

    @Override
    public void stackVoltageReached(int i) {
        publishEvent(getContract().EVENT_STACK_VOLTAGE_REACHED, i);
    }

    @Override
    public void usbVoltage(int i) {
        publishEvent(getContract().EVENT_USB_VOLTAGE, i);
    }

    @Override
    public void usbVoltageReached(int i) {
        publishEvent(getContract().EVENT_USB_VOLTAGE_REACHED, i);
    }

}
