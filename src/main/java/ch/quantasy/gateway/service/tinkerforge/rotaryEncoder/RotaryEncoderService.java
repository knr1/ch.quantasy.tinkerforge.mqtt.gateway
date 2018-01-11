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
package ch.quantasy.gateway.service.tinkerforge.rotaryEncoder;

import ch.quantasy.gateway.message.rotaryEncoder.ButtonEvent;
import ch.quantasy.gateway.message.rotaryEncoder.CountEvent;
import ch.quantasy.gateway.service.tinkerforge.AbstractDeviceService;
import ch.quantasy.gateway.message.rotaryEncoder.DeviceCountCallbackThreshold;
import ch.quantasy.gateway.message.rotaryEncoder.CountCallbackPeriodStatus;
import ch.quantasy.gateway.message.rotaryEncoder.CountThresholdStatus;
import ch.quantasy.gateway.message.rotaryEncoder.DebouncePeriodStatus;
import ch.quantasy.tinkerforge.device.rotaryEncoder.RotaryEncoderDevice;
import ch.quantasy.tinkerforge.device.rotaryEncoder.RotaryEncoderDeviceCallback;

import java.net.URI;
import org.eclipse.paho.client.mqttv3.MqttException;

/**
 *
 * @author reto
 */
public class RotaryEncoderService extends AbstractDeviceService<RotaryEncoderDevice, RotaryEncoderServiceContract> implements RotaryEncoderDeviceCallback {

    public RotaryEncoderService(RotaryEncoderDevice device, URI mqttURI) throws MqttException {
        super(mqttURI, device, new RotaryEncoderServiceContract(device));
    }

    @Override
    public void count(int i) {
        readyToPublishEvent(getContract().EVENT_COUNT, new CountEvent(i));
    }

    @Override
    public void countReached(int i) {
        readyToPublishEvent(getContract().EVENT_COUNT_REACHED, new CountEvent(i));
    }

    @Override
    public void countCallbackPeriodChanged(long period) {
        readyToPublishStatus(getContract().STATUS_COUNT_CALLBACK_PERIOD, new CountCallbackPeriodStatus(period));
    }

    @Override
    public void debouncePeriodChanged(long period) {
        readyToPublishStatus(getContract().STATUS_DEBOUNCE_PERIOD, new DebouncePeriodStatus(period));
    }

    @Override
    public void countCallbackThresholdChanged(DeviceCountCallbackThreshold threshold) {
        readyToPublishStatus(getContract().STATUS_COUNT_THRESHOLD, new CountThresholdStatus(threshold));

    }

    @Override
    public void countReset(int latestCount) {
        readyToPublishEvent(getContract().EVENT_COUNT_RESET, new CountEvent(latestCount,true));
    }

    @Override
    public void pressed() {
        readyToPublishEvent(getContract().EVENT_PRESSED, new ButtonEvent(true));
    }

    @Override
    public void released() {
        readyToPublishEvent(getContract().EVENT_RELEASED, new ButtonEvent(false));
    }

}
