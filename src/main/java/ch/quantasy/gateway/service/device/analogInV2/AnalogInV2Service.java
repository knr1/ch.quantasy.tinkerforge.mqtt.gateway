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
package ch.quantasy.gateway.service.device.analogInV2;

import ch.quantasy.gateway.message.event.analogInV2.AnalogValueEvent;
import ch.quantasy.gateway.message.event.analogInV2.VoltageEvent;
import ch.quantasy.gateway.service.device.AbstractDeviceService;
import ch.quantasy.tinkerforge.device.analogInV2.AnalogInV2Device;
import ch.quantasy.tinkerforge.device.analogInV2.AnalogInV2DeviceCallback;
import ch.quantasy.gateway.message.intent.analogInV2.DeviceAnalogValueCallbackThreshold;
import ch.quantasy.gateway.message.intent.analogInV2.DeviceVoltageCallbackThreshold;
import ch.quantasy.gateway.message.status.analogInV2.AnalogValueCallbackPeriodStatus;
import ch.quantasy.gateway.message.status.analogInV2.AnalogValueCallbackThresholdStatus;
import ch.quantasy.gateway.message.status.analogInV2.DebouncePeriodStatus;
import ch.quantasy.gateway.message.status.analogInV2.MovingAverageStatus;
import ch.quantasy.gateway.message.status.analogInV2.VoltageCallbackPeriodStatus;
import ch.quantasy.gateway.message.status.analogInV2.VoltageCallbackThresholdStatus;
import java.net.URI;
import org.eclipse.paho.client.mqttv3.MqttException;

/**
 *
 * @author reto
 */
public class AnalogInV2Service extends AbstractDeviceService<AnalogInV2Device, AnalogInV2ServiceContract> implements AnalogInV2DeviceCallback {

    public AnalogInV2Service(AnalogInV2Device device, URI mqttURI) throws MqttException {
        super(mqttURI, device, new AnalogInV2ServiceContract(device));

    }

    @Override
    public void analogValue(int i) {

        readyToPublishEvent(getContract().EVENT_ANALOG_VALUE, new AnalogValueEvent(i));
    }

    @Override
    public void analogValueReached(int i) {
        readyToPublishEvent(getContract().EVENT_ANALOG_VALUE_REACHED, new AnalogValueEvent(i));
    }

    @Override
    public void voltage(int i) {
        readyToPublishEvent(getContract().EVENT_VOLTAGE, new VoltageEvent(i));
    }

    @Override
    public void voltageReached(int i) {
        readyToPublishEvent(getContract().EVENT_VOLTAGE_REACHED, new VoltageEvent(i));
    }

    @Override
    public void analogValueCallbackPeriodChanged(long period) {
        publishStatus(getContract().STATUS_ANALOG_VALUE_CALLBACK_PERIOD, new AnalogValueCallbackPeriodStatus(period));
    }

    @Override
    public void voltageCallbackPeriodChanged(long period) {
        publishStatus(getContract().STATUS_VOLTAGE_CALLBACK_PERIOD, new VoltageCallbackPeriodStatus(period));
    }

    @Override
    public void debouncePeriodChanged(long period) {
        publishStatus(getContract().STATUS_DEBOUNCE_PERIOD, new DebouncePeriodStatus(period));
    }

    @Override
    public void analogValueCallbackThresholdChanged(DeviceAnalogValueCallbackThreshold threshold) {
        publishStatus(getContract().STATUS_ANALOG_VALUE_THRESHOLD, new AnalogValueCallbackThresholdStatus(threshold));
    }

    @Override
    public void voltageCallbackThresholdChanged(DeviceVoltageCallbackThreshold threshold) {
        publishStatus(getContract().STATUS_VOLTAGE_THRESHOLD, new VoltageCallbackThresholdStatus(threshold));
    }

    @Override
    public void movingAverageChanged(Short averaging) {
        publishStatus(getContract().STATUS_MOVING_AVERAGE, new MovingAverageStatus(averaging));
    }

}
