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
package ch.quantasy.tinkerforge.device.joystick;

import ch.quantasy.gateway.binding.tinkerforge.joystick.DeviceAnalogValueCallbackThreshold;
import ch.quantasy.gateway.binding.tinkerforge.joystick.DevicePositionCallbackThreshold;
import ch.quantasy.gateway.binding.tinkerforge.joystick.JoystickIntent;
import ch.quantasy.tinkerforge.device.generic.GenericDevice;
import ch.quantasy.tinkerforge.stack.TinkerforgeStack;
import com.tinkerforge.BrickletJoystick;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class JoystickDevice extends GenericDevice<BrickletJoystick, JoystickDeviceCallback, JoystickIntent> {

    public JoystickDevice(TinkerforgeStack stack, BrickletJoystick device) throws NotConnectedException, TimeoutException {
        super(stack, device, new JoystickIntent());
    }

    @Override
    protected void addDeviceListeners(BrickletJoystick device) {
        device.addAnalogValueListener(super.getCallback());
        device.addAnalogValueReachedListener(super.getCallback());
        device.addPositionListener(super.getCallback());
        device.addPositionReachedListener(super.getCallback());
        device.addPressedListener(super.getCallback());
        device.addReleasedListener(super.getCallback());

    }

    @Override
    protected void removeDeviceListeners(BrickletJoystick device) {
        device.removeAnalogValueListener(super.getCallback());
        device.removeAnalogValueReachedListener(super.getCallback());
        device.removePositionListener(super.getCallback());
        device.removePositionReachedListener(super.getCallback());
        device.removePressedListener(super.getCallback());
        device.removeReleasedListener(super.getCallback());
    }

    @Override
    public void update(JoystickIntent intent) {
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
                Logger.getLogger(JoystickDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (intent.analogCallbackPeriod != null) {
            try {
                getDevice().setAnalogValueCallbackPeriod(intent.analogCallbackPeriod);
                getIntent().analogCallbackPeriod = getDevice().getAnalogValueCallbackPeriod();
                super.getCallback().analogValueCallbackPeriodChanged(getIntent().analogCallbackPeriod);
            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(JoystickDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (intent.positionCallbackPeriod != null) {
            try {
                getDevice().setPositionCallbackPeriod(intent.positionCallbackPeriod);
                getIntent().positionCallbackPeriod = getDevice().getPositionCallbackPeriod();
                super.getCallback().positionCallbackPeriodChanged(getIntent().positionCallbackPeriod);
            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(JoystickDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (intent.analogValueCallbackThreshold != null) {
            try {
                getDevice().setAnalogValueCallbackThreshold(intent.analogValueCallbackThreshold.option, intent.analogValueCallbackThreshold.minX, intent.analogValueCallbackThreshold.maxX, intent.analogValueCallbackThreshold.maxX, intent.analogValueCallbackThreshold.maxY);
                getIntent().analogValueCallbackThreshold = new DeviceAnalogValueCallbackThreshold(getDevice().getAnalogValueCallbackThreshold());
                super.getCallback().analogValueCallbackThresholdChanged(getIntent().analogValueCallbackThreshold);
            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(JoystickDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (intent.positionCallbackThreshold != null) {
            try {
                getDevice().setPositionCallbackThreshold(intent.positionCallbackThreshold.option, intent.positionCallbackThreshold.minX, intent.positionCallbackThreshold.maxX, intent.positionCallbackThreshold.minY, intent.positionCallbackThreshold.maxY);
                getIntent().positionCallbackThreshold = new DevicePositionCallbackThreshold(getDevice().getPositionCallbackThreshold());
                super.getCallback().positionCallbackThresholdChanged(getIntent().positionCallbackThreshold);
            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(JoystickDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (intent.calibrate != null) {
            try {
                if (intent.calibrate) {
                    getDevice().calibrate();
                    super.getCallback().calibrated();
                }
            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(JoystickDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
