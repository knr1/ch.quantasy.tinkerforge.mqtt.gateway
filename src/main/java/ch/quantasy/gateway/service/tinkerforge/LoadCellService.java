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

import ch.quantasy.gateway.binding.tinkerforge.loadCell.LoadCellServiceContract;
import ch.quantasy.gateway.binding.tinkerforge.loadCell.WeightEvent;
import ch.quantasy.gateway.service.tinkerforge.AbstractDeviceService;
import ch.quantasy.gateway.binding.tinkerforge.loadCell.DeviceConfiguration;
import ch.quantasy.gateway.binding.tinkerforge.loadCell.DeviceWeightCallbackThreshold;
import ch.quantasy.gateway.binding.tinkerforge.loadCell.ConfigurationStatus;
import ch.quantasy.gateway.binding.tinkerforge.loadCell.DebouncePeriodStatus;
import ch.quantasy.gateway.binding.tinkerforge.loadCell.MovingAverageStatus;
import ch.quantasy.gateway.binding.tinkerforge.loadCell.StatusLEDStatus;
import ch.quantasy.gateway.binding.tinkerforge.loadCell.WeightCallbackPeriodStatus;
import ch.quantasy.gateway.binding.tinkerforge.loadCell.WeightCallbackThresholdStatus;
import ch.quantasy.tinkerforge.device.loadCell.LoadCellDevice;
import ch.quantasy.tinkerforge.device.loadCell.LoadCellDeviceCallback;

import org.eclipse.paho.client.mqttv3.MqttException;

import java.net.URI;

/**
 *
 * @author reto
 */
public class LoadCellService extends AbstractDeviceService<LoadCellDevice, LoadCellServiceContract> implements LoadCellDeviceCallback {

    public LoadCellService(LoadCellDevice device, URI mqttURI) throws MqttException {

        super(mqttURI, device, new LoadCellServiceContract(device));
    }

    @Override
    public void debouncePeriodChanged(long period) {
        readyToPublishStatus(getContract().STATUS_DEBOUNCE_PERIOD, new DebouncePeriodStatus(period));
    }

    @Override
    public void weightCallbackPeriodChanged(long period) {
        readyToPublishStatus(getContract().STATUS_WEIGHT_CALLBACK_PERIOD, new WeightCallbackPeriodStatus(period));
    }

    @Override
    public void weightCallbackThresholdChanged(DeviceWeightCallbackThreshold threshold) {
        readyToPublishStatus(getContract().STATUS_WEIGHT_THRESHOLD, new WeightCallbackThresholdStatus(threshold));
    }

    @Override
    public void configurationChanged(DeviceConfiguration configuration) {
        readyToPublishStatus(getContract().STATUS_CONFIGURATION, new ConfigurationStatus(configuration));
    }

    @Override
    public void movingAverageChanged(short average) {
        readyToPublishStatus(getContract().STATUS_MOVING_AVERAGE, new MovingAverageStatus(average));
    }

    @Override
    public void statusLEDChanged(boolean led) {
        readyToPublishStatus(getContract().STATUS_LED, new StatusLEDStatus(led));
    }

    @Override
    public void weight(int i) {
        readyToPublishEvent(getContract().EVENT_WEIGHT, new WeightEvent(i));
    }

    @Override
    public void weightReached(int i) {
        readyToPublishEvent(getContract().EVENT_WEIGHT_REACHED, new WeightEvent(i));
    }

}
