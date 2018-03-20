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

import ch.quantasy.gateway.binding.tinkerforge.laserRangeFinder.LaserRangeFinderServiceContract;
import ch.quantasy.gateway.binding.tinkerforge.laserRangeFinder.DistanceEvent;
import ch.quantasy.gateway.binding.tinkerforge.laserRangeFinder.VelocityEvent;
import ch.quantasy.gateway.service.tinkerforge.AbstractDeviceService;
import ch.quantasy.gateway.binding.tinkerforge.laserRangeFinder.DeviceAveraging;
import ch.quantasy.gateway.binding.tinkerforge.laserRangeFinder.DeviceConfiguration;
import ch.quantasy.gateway.binding.tinkerforge.laserRangeFinder.DeviceDistanceCallbackThreshold;
import ch.quantasy.gateway.binding.tinkerforge.laserRangeFinder.DeviceMode;
import ch.quantasy.gateway.binding.tinkerforge.laserRangeFinder.DeviceVelocityCallbackThreshold;
import ch.quantasy.tinkerforge.device.laserRangeFinder.LaserRangeFinderDevice;
import ch.quantasy.tinkerforge.device.laserRangeFinder.LaserRangeFinderDeviceCallback;
import ch.quantasy.gateway.binding.tinkerforge.laserRangeFinder.SensorHardware;
import ch.quantasy.gateway.binding.tinkerforge.laserRangeFinder.ConfigurationStatus;
import ch.quantasy.gateway.binding.tinkerforge.laserRangeFinder.DebouncePeriodStatus;
import ch.quantasy.gateway.binding.tinkerforge.laserRangeFinder.DistanceCallbackPeriodStatus;
import ch.quantasy.gateway.binding.tinkerforge.laserRangeFinder.DistanceCallbackThresholdStatus;
import ch.quantasy.gateway.binding.tinkerforge.laserRangeFinder.LaserEnabledStatus;
import ch.quantasy.gateway.binding.tinkerforge.laserRangeFinder.ModeStatus;
import ch.quantasy.gateway.binding.tinkerforge.laserRangeFinder.MovingAverageStatus;
import ch.quantasy.gateway.binding.tinkerforge.laserRangeFinder.SensorHardwareStatus;
import ch.quantasy.gateway.binding.tinkerforge.laserRangeFinder.VelocityCallbackPeriodStatus;
import ch.quantasy.gateway.binding.tinkerforge.laserRangeFinder.VelocityCallbackThresholdStatus;
import java.net.URI;
import org.eclipse.paho.client.mqttv3.MqttException;

/**
 *
 * @author reto
 */
public class LaserRangeFinderService extends AbstractDeviceService<LaserRangeFinderDevice, LaserRangeFinderServiceContract> implements LaserRangeFinderDeviceCallback {

    public LaserRangeFinderService(LaserRangeFinderDevice device, URI mqttURI) throws MqttException {
        super(mqttURI, device, new LaserRangeFinderServiceContract(device));
    }

    @Override
    public void distance(int i) {
        readyToPublishEvent(getContract().EVENT_DISTANCE, new DistanceEvent(i));
    }

    @Override
    public void distanceReached(int i) {
        readyToPublishEvent(getContract().EVENT_DISTANCE_REACHED, new DistanceEvent(i));
    }

    @Override
    public void velocity(short i) {
        readyToPublishEvent(getContract().EVENT_VELOCITY, new VelocityEvent(i));
    }

    @Override
    public void velocityReached(short i) {
        readyToPublishEvent(getContract().EVENT_VELOCITY_REACHED, new VelocityEvent(i));
    }

    @Override
    public void distanceCallbackPeriodChanged(long period) {
        readyToPublishStatus(getContract().STATUS_DISTANCE_CALLBACK_PERIOD, new DistanceCallbackPeriodStatus(period));
    }

    @Override
    public void velocityCallbackPeriodChanged(long period) {
        readyToPublishStatus(getContract().STATUS_VELOCITY_CALLBACK_PERIOD, new VelocityCallbackPeriodStatus(period));
    }

    @Override
    public void debouncePeriodChanged(long period) {
        readyToPublishStatus(getContract().STATUS_DEBOUNCE_PERIOD, new DebouncePeriodStatus(period));
    }

    @Override
    public void distanceCallbackThresholdChanged(DeviceDistanceCallbackThreshold threshold) {
        readyToPublishStatus(getContract().STATUS_DISTANCE_THRESHOLD, new DistanceCallbackThresholdStatus(threshold));
    }

    @Override
    public void velocityCallbackThresholdChanged(DeviceVelocityCallbackThreshold threshold) {
        readyToPublishStatus(getContract().STATUS_VELOCITY_THRESHOLD, new VelocityCallbackThresholdStatus(threshold));
    }

    @Override
    public void laserStatusChanged(boolean laserEnabled) {
        readyToPublishStatus(getContract().STATUS_LASER, new LaserEnabledStatus(laserEnabled));
    }

    @Override
    public void movingAverageChanged(DeviceAveraging averaging) {
        readyToPublishStatus(getContract().STATUS_MOVING_AVERAGE, new MovingAverageStatus(averaging));
    }

    @Override
    public void deviceModeChanged(DeviceMode deviceMode) {
        readyToPublishStatus(getContract().STATUS_DEVICE_MODE, new ModeStatus(deviceMode));
    }

    @Override
    public void deviceConfigurationChanged(DeviceConfiguration configuration) {
        readyToPublishStatus(getContract().STATUS_DEVICE_CONFIGURATION, new ConfigurationStatus(configuration));
    }

    @Override
    public void sensorHardware(SensorHardware sensorHardware) {
        readyToPublishStatus(getContract().STATUS_SENSOR_HARDWARE, new SensorHardwareStatus(sensorHardware));
    }

}
