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
package ch.quantasy.tinkerforge.device.soundPressureLevel;

import ch.quantasy.gateway.binding.tinkerforge.soundPressureLevel.Configuration;
import ch.quantasy.gateway.binding.tinkerforge.soundPressureLevel.DecibelCallbackConfiguration;
import ch.quantasy.gateway.binding.tinkerforge.soundPressureLevel.SoundPressureLevelIntent;
import ch.quantasy.tinkerforge.device.generic.GenericDevice;
import ch.quantasy.tinkerforge.stack.TinkerforgeStack;
import com.tinkerforge.BrickletSoundPressureLevel;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class SoundPressureLevelDevice extends GenericDevice<BrickletSoundPressureLevel, SoundPressureLevelDeviceCallback, SoundPressureLevelIntent> {

    public SoundPressureLevelDevice(TinkerforgeStack stack, BrickletSoundPressureLevel device) throws NotConnectedException, TimeoutException {
        super(stack, device, new SoundPressureLevelIntent());
    }

    @Override
    protected void addDeviceListeners(BrickletSoundPressureLevel device) {
        device.addDecibelListener(super.getCallback());
        device.addSpectrumListener(super.getCallback());
    }

    @Override
    protected void removeDeviceListeners(BrickletSoundPressureLevel device) {
        device.removeDecibelListener(super.getCallback());
        device.removeSpectrumListener(super.getCallback());
    }

    @Override
    public void update(SoundPressureLevelIntent intent) {
        if (intent == null) {
            return;
        }
        if (!intent.isValid()) {
            return;
        }
        if (intent.configuration != null) {
            try {
                getDevice().setConfiguration(intent.configuration.fftSize.getValue(),intent.configuration.weighting.getValue());
                getIntent().configuration = new Configuration(getDevice().getConfiguration());
                super.getCallback().configurationChanged(getIntent().configuration);
            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(SoundPressureLevelDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (intent.decibelCallbackConfiguration != null) {
            try {
                getDevice().setDecibelCallbackConfiguration(intent.decibelCallbackConfiguration.period,intent.decibelCallbackConfiguration.valueHasToChange,intent.decibelCallbackConfiguration.option,intent.decibelCallbackConfiguration.min,intent.decibelCallbackConfiguration.max);
                intent.decibelCallbackConfiguration = new DecibelCallbackConfiguration(getDevice().getDecibelCallbackConfiguration());
                super.getCallback().decibelCallbackConfigurationChanged(intent.decibelCallbackConfiguration);
            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(SoundPressureLevelDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (intent.spectrumCallbackConfiguration != null) {
            try {
                getDevice().setSpectrumCallbackConfiguration(intent.spectrumCallbackConfiguration);
                getIntent().spectrumCallbackConfiguration = getDevice().getSpectrumCallbackConfiguration();
                super.getCallback().spectrumCallbackConfigurationChanged(getIntent().spectrumCallbackConfiguration);
            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(SoundPressureLevelDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
