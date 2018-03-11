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
package ch.quantasy.gateway.service.tinkerforge.thermalImaging;

import ch.quantasy.gateway.message.thermalImage.HighContrastImageEvent;
import ch.quantasy.gateway.message.thermalImage.TemperatureResolutionStatus;
import ch.quantasy.gateway.message.thermalImage.ImageTransferStatus;
import ch.quantasy.gateway.message.thermalImage.SpotMeterConfigStatus;
import ch.quantasy.gateway.message.thermalImage.StatisticsEvent;
import ch.quantasy.gateway.message.thermalImage.TemperatureImageEvent;
import ch.quantasy.gateway.message.thermalImage.ThermalImageIntent;
import ch.quantasy.gateway.service.tinkerforge.DeviceServiceContract;
import ch.quantasy.mqtt.gateway.client.message.Message;
import ch.quantasy.tinkerforge.device.TinkerforgeDeviceClass;
import ch.quantasy.tinkerforge.device.thermalImaging.ThermalImagingDevice;
import java.util.Map;

/**
 *
 * @author reto
 */
public class ThermalImagingServiceContract extends DeviceServiceContract {

    public final String STATUS_TEMPERATURE_RESOLUTION;
    public final String STATUS_IMAGE_TRANSFER_CONFIG;
    public final String STATUS_SPOT_METER_CONFIG;
    public final String EVENT_IMAGE_HIGH_CONTRAST;
    public final String EVENT_IMAGE_TEMPERATURE;
    public final String EVENT_STATISTICS;
    

    public ThermalImagingServiceContract(ThermalImagingDevice device) {
        this(device.getUid(), TinkerforgeDeviceClass.getDevice(device.getDevice()).toString());
    }

    public ThermalImagingServiceContract(String id) {
        this(id, TinkerforgeDeviceClass.ThermalImaging.toString());
    }

    public ThermalImagingServiceContract(String id, String device) {
        super(id, device, ThermalImageIntent.class);

        STATUS_IMAGE_TRANSFER_CONFIG=STATUS+"/transferConfig";
        STATUS_TEMPERATURE_RESOLUTION=STATUS+"/resolution";
        STATUS_SPOT_METER_CONFIG=STATUS+"/spotMeterConfig";
        
        EVENT_IMAGE_HIGH_CONTRAST=EVENT+"/image/highContrast";
        EVENT_IMAGE_TEMPERATURE=EVENT+"/image/temperature";
        EVENT_STATISTICS=EVENT+"/statistics";
    }

    @Override
    public void setServiceMessageTopics(Map<String, Class<? extends Message>> messageTopicMap) {
    
        messageTopicMap.put(STATUS_IMAGE_TRANSFER_CONFIG, ImageTransferStatus.class);
        messageTopicMap.put(STATUS_TEMPERATURE_RESOLUTION, TemperatureResolutionStatus.class);
        messageTopicMap.put(STATUS_SPOT_METER_CONFIG, SpotMeterConfigStatus.class);
        messageTopicMap.put(EVENT_IMAGE_HIGH_CONTRAST, HighContrastImageEvent.class);
        messageTopicMap.put(EVENT_IMAGE_TEMPERATURE, TemperatureImageEvent.class);
        messageTopicMap.put(EVENT_STATISTICS, StatisticsEvent.class);
    }

   
}
