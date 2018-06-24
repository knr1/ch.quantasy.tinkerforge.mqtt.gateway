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
package ch.quantasy.gateway.binding.tinkerforge.soundPressureLevel;

import ch.quantasy.gateway.binding.tinkerforge.DeviceServiceContract;
import ch.quantasy.mqtt.gateway.client.message.Message;
import ch.quantasy.tinkerforge.device.TinkerforgeDeviceClass;
import ch.quantasy.tinkerforge.device.soundPressureLevel.SoundPressureLevelDevice;
import java.util.Map;

/**
 *
 * @author reto
 */
public class SoundPressureLevelServiceContract extends DeviceServiceContract {

    public final String STATUS_CONFIGURATION;
    public final String STATUS_DECIBEL_CONFIGURATION;
    public final String STATUS_SPECTRUM_CONFIGURATION;
    public final String EVENT_DECIBEL;
    public final String EVENT_SPECTRUM;

    public SoundPressureLevelServiceContract(SoundPressureLevelDevice device) {
        this(device.getUid(), TinkerforgeDeviceClass.getDevice(device.getDevice()).toString());
    }

    public SoundPressureLevelServiceContract(String id) {
        this(id, TinkerforgeDeviceClass.SoundPressureLevel.toString());
    }

    public SoundPressureLevelServiceContract(String id, String device) {
        super(id, device, SoundPressureLevelIntent.class);

        STATUS_CONFIGURATION = STATUS + "/" + "configuration";
        STATUS_DECIBEL_CONFIGURATION = STATUS + "/" + "decibel";
        STATUS_SPECTRUM_CONFIGURATION = STATUS + "/" + "spectrum";
        EVENT_DECIBEL = EVENT + "/" + "decibel";
        EVENT_SPECTRUM = EVENT + "/" + "spectrum";

    }

    @Override
    public void setServiceMessageTopics(Map<String, Class<? extends Message>> messageTopicMap) {

        messageTopicMap.put(STATUS_CONFIGURATION, ConfigurationStatus.class);
        messageTopicMap.put(STATUS_DECIBEL_CONFIGURATION, DecibelConfigurationStatus.class);
        messageTopicMap.put(STATUS_SPECTRUM_CONFIGURATION, SpectrumCallbackConfigurationStatus.class);
        messageTopicMap.put(EVENT_DECIBEL, DecibelEvent.class);

    }

}
