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
package ch.quantasy.tinkerforge.device.soundIntensity;

import ch.quantasy.gateway.intent.soundIntensity.DeviceSoundIntensityCallbackThreshold;
import ch.quantasy.gateway.intent.soundIntensity.SoundIntensityIntent;
import ch.quantasy.tinkerforge.device.generic.GenericDevice;
import ch.quantasy.tinkerforge.stack.TinkerforgeStack;
import com.tinkerforge.BrickletSoundIntensity;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class SoundIntensityDevice extends GenericDevice<BrickletSoundIntensity, SoundIntensityDeviceCallback,SoundIntensityIntent> {

    

    public SoundIntensityDevice(TinkerforgeStack stack, BrickletSoundIntensity device) throws NotConnectedException, TimeoutException {
        super(stack, device,new SoundIntensityIntent());
    }

    @Override
    protected void addDeviceListeners(BrickletSoundIntensity device) {
        device.addIntensityListener(getCallback());
        device.addIntensityReachedListener(getCallback());
        
    }

    @Override
    protected void removeDeviceListeners(BrickletSoundIntensity device) {
        device.removeIntensityListener(getCallback());
        device.removeIntensityReachedListener(getCallback());
    }

    @Override
    public void update(SoundIntensityIntent intent) {
        if (intent == null) {
            return;
        }
        if (!intent.isValid()) {
            return;
        }

        if (intent.debouncePeriod != null) {
            try {
                getDevice().setDebouncePeriod(intent.debouncePeriod);
                getIntent().debouncePeriod = getDevice().getDebouncePeriod();
                super.getCallback().debouncePeriodChanged(getIntent().debouncePeriod);
            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(SoundIntensityDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (intent.intensityCallbackPeriod != null) {
            try {
                getDevice().setIntensityCallbackPeriod(intent.intensityCallbackPeriod);
                getIntent().intensityCallbackPeriod = getDevice().getIntensityCallbackPeriod();
                super.getCallback().soundIntensityCallbackPeriodChanged(getIntent().intensityCallbackPeriod);
            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(SoundIntensityDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

      
        if (intent.intensityCallbackThreshold != null) {
            try {
                getDevice().setIntensityCallbackThreshold(intent.intensityCallbackThreshold.getOption(), intent.intensityCallbackThreshold.getMin(), intent.intensityCallbackThreshold.getMax());
                getIntent().intensityCallbackThreshold = new DeviceSoundIntensityCallbackThreshold(getDevice().getIntensityCallbackThreshold());
                super.getCallback().soundIntensityCallbackThresholdChanged(getIntent().intensityCallbackThreshold);
            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(SoundIntensityDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
