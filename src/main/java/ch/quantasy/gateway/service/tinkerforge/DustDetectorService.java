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

import ch.quantasy.gateway.binding.tinkerforge.dustDetector.DustDetectorServiceContract;
import ch.quantasy.gateway.binding.tinkerforge.dustDetector.DustDensityEvent;
import ch.quantasy.gateway.binding.tinkerforge.dustDetector.DeviceDustDensityCallbackThreshold;
import ch.quantasy.gateway.binding.tinkerforge.dustDetector.DebouncePeriodStatus;
import ch.quantasy.gateway.binding.tinkerforge.dustDetector.DustDensityCallbackPeriodStatus;
import ch.quantasy.gateway.binding.tinkerforge.dustDetector.DustDensityCallbackThresholdStatus;
import ch.quantasy.gateway.binding.tinkerforge.dustDetector.MovingAverageStatus;
import ch.quantasy.gateway.service.tinkerforge.AbstractDeviceService;
import ch.quantasy.tinkerforge.device.dustDetector.DustDetectorDevice;
import ch.quantasy.tinkerforge.device.dustDetector.DustDetectorDeviceCallback;
import java.net.URI;
import org.eclipse.paho.client.mqttv3.MqttException;

/**
 *
 * @author reto
 */
public class DustDetectorService extends AbstractDeviceService<DustDetectorDevice, DustDetectorServiceContract> implements DustDetectorDeviceCallback {

    public DustDetectorService(DustDetectorDevice device, URI mqttURI) throws MqttException {
        super(mqttURI, device, new DustDetectorServiceContract(device));
    }

    @Override
    public void debouncePeriodChanged(long period) {
        readyToPublishStatus(getContract().STATUS_DEBOUNCE_PERIOD, new DebouncePeriodStatus(period));
    }

    @Override
    public void movingAverageChanged(short movingAverage) {
        readyToPublishStatus(getContract().STATUS_MOVING_AVERAGE, new MovingAverageStatus(movingAverage));
    }

    @Override
    public void dustDensityCallbackPeriodChanged(long period) {
        readyToPublishStatus(getContract().STATUS_DUST_DENSITY_CALLBACK_PERIOD, new DustDensityCallbackPeriodStatus(period));
    }

    @Override
    public void dustDensityCallbackThresholdChanged(DeviceDustDensityCallbackThreshold threshold) {
        readyToPublishStatus(getContract().STATUS_DUST_DENSITY_THRESHOLD, new DustDensityCallbackThresholdStatus(threshold));
    }

    @Override
    public void dustDensity(int i) {
        readyToPublishEvent(getContract().EVENT_DUST_DENSITY, new DustDensityEvent(i));
    }

    @Override
    public void dustDensityReached(int i) {
        readyToPublishEvent(getContract().EVENT_DUST_DENSITY_REACHED, new DustDensityEvent(i));
    }

}
