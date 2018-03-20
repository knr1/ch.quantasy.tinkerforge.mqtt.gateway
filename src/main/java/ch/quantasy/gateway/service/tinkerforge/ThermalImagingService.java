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

import ch.quantasy.gateway.binding.tinkerforge.thermalImage.ThermalImagingServiceContract;
import ch.quantasy.gateway.binding.tinkerforge.thermalImage.HighContrastImageEvent;
import ch.quantasy.gateway.binding.tinkerforge.thermalImage.TemperatureResolution;
import ch.quantasy.gateway.binding.tinkerforge.thermalImage.TemperatureResolutionStatus;
import ch.quantasy.gateway.binding.tinkerforge.thermalImage.ImageTransferConfig;
import ch.quantasy.gateway.binding.tinkerforge.thermalImage.ImageTransferStatus;
import ch.quantasy.gateway.binding.tinkerforge.thermalImage.SpotMeterConfig;
import ch.quantasy.gateway.binding.tinkerforge.thermalImage.SpotMeterConfigStatus;
import ch.quantasy.gateway.binding.tinkerforge.thermalImage.StatisticsEvent;
import ch.quantasy.gateway.binding.tinkerforge.thermalImage.TemperatureImageEvent;
import ch.quantasy.gateway.service.tinkerforge.AbstractDeviceService;
import ch.quantasy.tinkerforge.device.thermalImaging.ThermalImagingDevice;
import java.net.URI;
import org.eclipse.paho.client.mqttv3.MqttException;
import ch.quantasy.tinkerforge.device.thermalImaging.ThermalImagIngDeviceCallback;
import com.tinkerforge.BrickletThermalImaging;

/**
 *
 * @author reto
 */
public class ThermalImagingService extends AbstractDeviceService<ThermalImagingDevice, ThermalImagingServiceContract> implements ThermalImagIngDeviceCallback {

    public ThermalImagingService(ThermalImagingDevice device, URI mqttURI) throws MqttException {
        super(mqttURI, device, new ThermalImagingServiceContract(device));
    }

    @Override
    public void imageTransferConfigChanged(ImageTransferConfig config) {
        readyToPublishStatus(getContract().STATUS_IMAGE_TRANSFER_CONFIG, new ImageTransferStatus(config));
    }

    @Override
    public void resolutionChanged(TemperatureResolution resolution) {
        readyToPublishStatus(getContract().STATUS_TEMPERATURE_RESOLUTION, new TemperatureResolutionStatus(resolution));
    }

    @Override
    public void highContrastImage(int[] image) {
        readyToPublishEvent(getContract().EVENT_IMAGE_HIGH_CONTRAST, new HighContrastImageEvent(image));
    }

    @Override
    public void temperatureImage(int[] image) {
        readyToPublishEvent(getContract().EVENT_IMAGE_TEMPERATURE, new TemperatureImageEvent(image));
    }

    @Override
    public void statisticsChanged(BrickletThermalImaging.Statistics statistics) {
        readyToPublishEvent(getContract().EVENT_STATISTICS,new StatisticsEvent(statistics));
    }

    @Override
    public void spotMeterConfigChanged(SpotMeterConfig spotMeterConfig) {
        readyToPublishStatus(getContract().STATUS_SPOT_METER_CONFIG, new SpotMeterConfigStatus(spotMeterConfig));
    }

}
