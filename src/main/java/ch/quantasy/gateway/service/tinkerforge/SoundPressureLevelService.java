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

import ch.quantasy.gateway.binding.tinkerforge.soundPressureLevel.Configuration;
import ch.quantasy.gateway.binding.tinkerforge.soundPressureLevel.ConfigurationStatus;
import ch.quantasy.gateway.binding.tinkerforge.soundPressureLevel.DecibelCallbackConfiguration;
import ch.quantasy.gateway.binding.tinkerforge.soundPressureLevel.DecibelConfigurationStatus;
import ch.quantasy.gateway.binding.tinkerforge.soundPressureLevel.DecibelEvent;
import ch.quantasy.gateway.binding.tinkerforge.soundPressureLevel.SoundPressureLevelServiceContract;
import ch.quantasy.gateway.binding.tinkerforge.soundPressureLevel.SpectrumCallbackConfigurationStatus;
import ch.quantasy.gateway.binding.tinkerforge.soundPressureLevel.SpectrumEvent;
import ch.quantasy.tinkerforge.device.soundPressureLevel.SoundPressureLevelDevice;
import ch.quantasy.tinkerforge.device.soundPressureLevel.SoundPressureLevelDeviceCallback;
import java.net.URI;
import org.eclipse.paho.client.mqttv3.MqttException;

/**
 *
 * @author reto
 */
public class SoundPressureLevelService extends AbstractDeviceService<SoundPressureLevelDevice, SoundPressureLevelServiceContract> implements SoundPressureLevelDeviceCallback {

    public SoundPressureLevelService(SoundPressureLevelDevice device, URI mqttURI) throws MqttException {
        super(mqttURI, device, new SoundPressureLevelServiceContract(device));
    }

    @Override
    public void configurationChanged(Configuration configuration) {
        readyToPublishStatus(getContract().STATUS_CONFIGURATION, new ConfigurationStatus(configuration));
    }

    @Override
    public void decibelCallbackConfigurationChanged(DecibelCallbackConfiguration decibelCallbackConfiguration) {
        readyToPublishStatus(getContract().STATUS_DECIBEL_CONFIGURATION, new DecibelConfigurationStatus(decibelCallbackConfiguration));
    }

    @Override
    public void spectrumCallbackConfigurationChanged(Long spectrumCallbackConfiguration) {
        readyToPublishStatus(getContract().STATUS_SPECTRUM_CONFIGURATION, new SpectrumCallbackConfigurationStatus(spectrumCallbackConfiguration));
    }

    @Override
    public void decibel(int arg0) {
        readyToPublishEvent(getContract().EVENT_DECIBEL, new DecibelEvent(arg0));
    }

    @Override
    public void spectrum(int[] arg0) {
        readyToPublishEvent(getContract().EVENT_SPECTRUM, new SpectrumEvent(arg0));    }

}
