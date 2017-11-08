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
package ch.quantasy.tinkerforge.device.ambientLight;

import ch.quantasy.gateway.message.intent.ambientLight.AmbientLightIntent;
import ch.quantasy.gateway.message.intent.ambientLight.DeviceIlluminanceCallbackThreshold;
import ch.quantasy.gateway.message.intent.ambientLight.DeviceAnalogValueCallbackThreshold;
import ch.quantasy.tinkerforge.device.generic.GenericDevice;
import ch.quantasy.tinkerforge.stack.TinkerforgeStack;
import com.tinkerforge.BrickletAmbientLight;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class AmbientLightDevice extends GenericDevice<BrickletAmbientLight, AmbientLightDeviceCallback, AmbientLightIntent> {

    public AmbientLightDevice(TinkerforgeStack stack, BrickletAmbientLight device) throws NotConnectedException, TimeoutException {
        super(stack, device, new AmbientLightIntent());
    }

    @Override
    protected void addDeviceListeners(BrickletAmbientLight device) {
        device.addAnalogValueListener(super.getCallback());
        device.addAnalogValueReachedListener(super.getCallback());
        device.addIlluminanceListener(super.getCallback());
        device.addIlluminanceReachedListener(super.getCallback());

    }

    @Override
    protected void removeDeviceListeners(BrickletAmbientLight device) {
        device.removeAnalogValueListener(super.getCallback());
        device.removeAnalogValueReachedListener(super.getCallback());
        device.removeIlluminanceListener(super.getCallback());
        device.removeIlluminanceReachedListener(super.getCallback());
    }

    @Override
    public void update(AmbientLightIntent intent) {
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
                Logger.getLogger(AmbientLightDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (intent.analogCallbackPeriod != null) {
            try {
                getDevice().setAnalogValueCallbackPeriod(intent.analogCallbackPeriod);
                getIntent().analogCallbackPeriod = getDevice().getAnalogValueCallbackPeriod();
                super.getCallback().analogValueCallbackPeriodChanged(getIntent().analogCallbackPeriod);
            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(AmbientLightDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (intent.illuminanceCallbackPeriod != null) {
            try {
                getDevice().setIlluminanceCallbackPeriod(intent.illuminanceCallbackPeriod);
                getIntent().illuminanceCallbackPeriod = getDevice().getIlluminanceCallbackPeriod();
                super.getCallback().illuminanceCallbackPeriodChanged(getIntent().illuminanceCallbackPeriod);
            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(AmbientLightDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (intent.analogValueThreshold != null) {
            try {
                getDevice().setAnalogValueCallbackThreshold(intent.analogValueThreshold.getOption(), intent.analogValueThreshold.getMin(), intent.analogValueThreshold.getMax());
                getIntent().analogValueThreshold = new DeviceAnalogValueCallbackThreshold(getDevice().getAnalogValueCallbackThreshold());
                super.getCallback().analogValueCallbackThresholdChanged(getIntent().analogValueThreshold);
            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(AmbientLightDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (intent.illuminanceThreshold != null) {
            try {
                getDevice().setIlluminanceCallbackThreshold(intent.illuminanceThreshold.getOption(), intent.illuminanceThreshold.getMin(), intent.illuminanceThreshold.getMax());
                getIntent().illuminanceThreshold = new DeviceIlluminanceCallbackThreshold(getDevice().getIlluminanceCallbackThreshold());
                super.getCallback().illuminanceCallbackThresholdChanged(getIntent().illuminanceThreshold);

            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(AmbientLightDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
