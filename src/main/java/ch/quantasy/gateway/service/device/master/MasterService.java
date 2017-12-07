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

import ch.quantasy.gateway.message.event.master.ResetEvent;
import ch.quantasy.gateway.message.event.master.StackCurrentEvent;
import ch.quantasy.gateway.message.event.master.StackVoltageEvent;
import ch.quantasy.gateway.message.event.master.USBVoltageEvent;
import ch.quantasy.gateway.message.intent.master.MasterIntent;
import ch.quantasy.gateway.service.device.AbstractDeviceService;
import ch.quantasy.tinkerforge.device.master.MasterDevice;
import ch.quantasy.tinkerforge.device.master.MasterDeviceCallback;
import ch.quantasy.gateway.message.intent.master.StackCurrentCallbackThreshold;
import ch.quantasy.gateway.message.intent.master.StackVoltageCallbackThreshold;
import ch.quantasy.gateway.message.intent.master.USBVoltageCallbackThreshold;
import ch.quantasy.gateway.message.status.master.CurrentCallbackPeriodStatus;
import ch.quantasy.gateway.message.status.master.CurrentCallbackThresholdStatus;
import ch.quantasy.gateway.message.status.master.DebouncePeriodStatus;
import ch.quantasy.gateway.message.status.master.StackVoltageCallbackPeriodStatus;
import ch.quantasy.gateway.message.status.master.StackVoltageCallbackThresholdStatus;
import ch.quantasy.gateway.message.status.master.StatusLEDStatus;
import ch.quantasy.gateway.message.status.master.UsbVoltageCallbackPeriodStatus;
import ch.quantasy.gateway.message.status.master.UsbVoltageCallbackThresholdStatus;
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
    public void debouncePeriodChanged(Long debouncePeriod) {
        readyToPublishStatus(getContract().STATUS_DEBOUNCE_PERIOD, new DebouncePeriodStatus(debouncePeriod));
    }

    @Override
    public void stackCurrentCallbackThresholdChanged(StackCurrentCallbackThreshold stackCurrentCallbackThreshold) {
        readyToPublishStatus(getContract().STATUS_CURRENT_CALLBACK_THRESHOLD, new CurrentCallbackThresholdStatus(stackCurrentCallbackThreshold));
    }

    @Override
    public void stackCurrentCallbackPeriodChanged(Long currentCallbackPeriod) {
        readyToPublishStatus(getContract().STATUS_STACK_CURRENT_CALLBACK_PERIOD, new CurrentCallbackPeriodStatus(currentCallbackPeriod));
    }

    @Override
    public void stackVoltageCallbackPeriodChanged(long voltageCallbackPeriod) {
        readyToPublishStatus(getContract().STATUS_STACK_VOLTAGE_CALLBACK_PERIOD, new StackVoltageCallbackPeriodStatus(voltageCallbackPeriod));
    }

    @Override
    public void stackVoltageCallbackThresholdChanged(StackVoltageCallbackThreshold voltageCallbackThreshold) {
        readyToPublishStatus(getContract().STATUS_STACK_VOLTAGE_CALLBACK_THRESHOLD, new StackVoltageCallbackThresholdStatus(voltageCallbackThreshold));
    }

    @Override
    public void usbVoltageCallbackPeriodChanged(long usbVoltageCallbackPeriod) {
        readyToPublishStatus(getContract().STATUS_USB_VOLTAGE_CALLBACK_PERIOD, new UsbVoltageCallbackPeriodStatus(usbVoltageCallbackPeriod));
    }

    @Override
    public void USBVoltageCallbackThresholdChanged(USBVoltageCallbackThreshold usbVoltageCallbackThreshold) {
        readyToPublishStatus(getContract().STATUS_USB_VOLTAGE_CALLBACK_THRESHOLD, new UsbVoltageCallbackThresholdStatus(usbVoltageCallbackThreshold));
    }

    @Override
    public void reset() {
        readyToPublishEvent(getContract().EVENT_RESET, new ResetEvent());
    }

    @Override
    public void statusLEDEnabledChanged(Boolean statusLEDEnabled) {
        readyToPublishStatus(getContract().STATUS_LED_ENABLED, new StatusLEDStatus(statusLEDEnabled));
    }

    @Override
    public void stackCurrent(int i) {
        readyToPublishEvent(getContract().EVENT_STACK_CURRENT, new StackCurrentEvent(i));
    }

    @Override
    public void stackCurrentReached(int i) {
        readyToPublishEvent(getContract().EVENT_STACK_CURRENT_REACHED, new StackCurrentEvent(i));
    }

    @Override
    public void stackVoltage(int i) {
        readyToPublishEvent(getContract().EVENT_STACK_VOLTAGE, new StackVoltageEvent(i));
    }

    @Override
    public void stackVoltageReached(int i) {
        readyToPublishEvent(getContract().EVENT_STACK_VOLTAGE_REACHED, new StackVoltageEvent(i));
    }

    @Override
    public void usbVoltage(int i) {
        readyToPublishEvent(getContract().EVENT_USB_VOLTAGE, new USBVoltageEvent(i));
    }

    @Override
    public void usbVoltageReached(int i) {
        readyToPublishEvent(getContract().EVENT_USB_VOLTAGE_REACHED, new USBVoltageEvent(i));
    }

}
