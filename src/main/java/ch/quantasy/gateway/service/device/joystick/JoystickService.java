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
package ch.quantasy.gateway.service.device.joystick;

import ch.quantasy.gateway.message.event.joystick.AnalogValueEvent;
import ch.quantasy.gateway.message.event.joystick.ButtonEvent;
import ch.quantasy.gateway.message.event.joystick.CalibratedEvent;
import ch.quantasy.gateway.message.event.joystick.PositionEvent;
import ch.quantasy.gateway.message.intent.joystick.DeviceAnalogValueCallbackThreshold;
import ch.quantasy.gateway.message.intent.joystick.DevicePositionCallbackThreshold;
import ch.quantasy.gateway.message.intent.joystick.JoystickIntent;
import ch.quantasy.gateway.message.status.joystick.AnalogCallbackPeriodStatus;
import ch.quantasy.gateway.message.status.joystick.AnalogValueCallbackThresholdStatus;
import ch.quantasy.gateway.message.status.joystick.DebouncePeriodStatus;
import ch.quantasy.gateway.message.status.joystick.PositionCallbackPeriodStatus;
import ch.quantasy.gateway.message.status.joystick.PositionCallbackThresholdStatus;
import ch.quantasy.gateway.service.device.AbstractDeviceService;
import ch.quantasy.tinkerforge.device.joystick.JoystickDevice;
import ch.quantasy.tinkerforge.device.joystick.JoystickDeviceCallback;

import java.net.URI;
import org.eclipse.paho.client.mqttv3.MqttException;

/**
 *
 * @author reto
 */
public class JoystickService extends AbstractDeviceService<JoystickDevice, JoystickServiceContract> implements JoystickDeviceCallback {

    public JoystickService(JoystickDevice device, URI mqttURI) throws MqttException {
        super(mqttURI, device, new JoystickServiceContract(device));
    }

    @Override
    public void analogValueCallbackPeriodChanged(long period) {
        publishStatus(getContract().STATUS_ANALOG_VALUE_CALLBACK_PERIOD, new AnalogCallbackPeriodStatus(period));
    }

    @Override
    public void positionCallbackPeriodChanged(long period) {
        publishStatus(getContract().STATUS_POSITION_CALLBACK_PERIOD, new PositionCallbackPeriodStatus(period));
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
    public void positionCallbackThresholdChanged(DevicePositionCallbackThreshold threshold) {
        publishStatus(getContract().STATUS_POSITION_THRESHOLD, new PositionCallbackThresholdStatus(threshold));
    }

    @Override
    public void calibrated() {
        readyToPublishEvent(getContract().EVENT_CALIBRATE, new CalibratedEvent(true));
    }

    @Override
    public void analogValue(int i, int i1) {
        readyToPublishEvent(getContract().EVENT_ANALOG_VALUE, new AnalogValueEvent(i, i1));
    }

    @Override
    public void analogValueReached(int i, int i1) {
        readyToPublishEvent(getContract().EVENT_ANALOG_VALUE_REACHED, new AnalogValueEvent(i, i1));
    }

    @Override
    public void position(short s, short s1) {
        readyToPublishEvent(getContract().EVENT_POSITION, new PositionEvent(s, s1));
    }

    @Override
    public void positionReached(short s, short s1) {
        readyToPublishEvent(getContract().EVENT_POSITION_REACHED, new PositionEvent(s, s1));
    }

    @Override
    public void pressed() {
        readyToPublishEvent(getContract().EVENT_PRESSED, new ButtonEvent(true));
    }

    @Override
    public void released() {
        readyToPublishEvent(getContract().EVENT_PRESSED, new ButtonEvent(false));
    }
}
