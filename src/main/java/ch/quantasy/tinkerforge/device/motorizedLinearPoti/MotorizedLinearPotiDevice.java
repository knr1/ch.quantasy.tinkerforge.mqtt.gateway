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
package ch.quantasy.tinkerforge.device.motorizedLinearPoti;

import ch.quantasy.gateway.message.motorizedLinearPoti.DevicePositionCallbackConfiguration;
import ch.quantasy.gateway.message.motorizedLinearPoti.DeviceMotorPosition;
import ch.quantasy.gateway.message.motorizedLinearPoti.MotorizedLinearPotiIntent;
import ch.quantasy.tinkerforge.device.generic.GenericDevice;
import ch.quantasy.tinkerforge.stack.TinkerforgeStack;
import com.tinkerforge.BrickletMotorizedLinearPoti;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class MotorizedLinearPotiDevice extends GenericDevice<BrickletMotorizedLinearPoti, MotorizedLinearPotiDeviceCallback, MotorizedLinearPotiIntent> {

    public MotorizedLinearPotiDevice(TinkerforgeStack stack, BrickletMotorizedLinearPoti device) throws NotConnectedException, TimeoutException {
        super(stack, device, new MotorizedLinearPotiIntent());
    }

    @Override
    protected void addDeviceListeners(BrickletMotorizedLinearPoti device) {
        device.addPositionListener(super.getCallback());
        device.addPositionReachedListener(super.getCallback());

    }

    @Override
    protected void removeDeviceListeners(BrickletMotorizedLinearPoti device) {
        device.removePositionListener(super.getCallback());
        device.removePositionReachedListener(super.getCallback());
    }

    @Override
    public void update(MotorizedLinearPotiIntent intent) {
        if (intent == null) {
            return;
        }
        if (!intent.isValid()) {
            return;
        }
        if(intent.calibration!=null&&intent.calibration)
        {
            try {
                getDevice().calibrate();
            } catch (TimeoutException ex) {
                Logger.getLogger(MotorizedLinearPotiDevice.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NotConnectedException ex) {
                Logger.getLogger(MotorizedLinearPotiDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (intent.motorPosition != null) {
            try {
                getDevice().setMotorPosition(intent.motorPosition.getPosition(), intent.motorPosition.getDriveMode().getValue(), intent.motorPosition.isHoldPosition());
                getIntent().motorPosition = new DeviceMotorPosition(getDevice().getMotorPosition());
                super.getCallback().motorPositionChanged(getIntent().motorPosition);
            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(MotorizedLinearPotiDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (intent.positionReachedCallbackConfiguration != null) {
            try {
                getDevice().setPositionReachedCallbackConfiguration(intent.positionReachedCallbackConfiguration);
                getIntent().positionReachedCallbackConfiguration = getDevice().getPositionReachedCallbackConfiguration();
                super.getCallback().positionReachedCallbackConfigurationChanged(getIntent().positionReachedCallbackConfiguration);
            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(MotorizedLinearPotiDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (intent.positionCallbackConfiguration != null) {
            try {
                getDevice().setPositionCallbackConfiguration(intent.positionCallbackConfiguration.getPeriod(), intent.positionCallbackConfiguration.valueHasToChange(), intent.positionCallbackConfiguration.getOption(), intent.positionCallbackConfiguration.getMin(), intent.positionCallbackConfiguration.getMax());
                getIntent().positionCallbackConfiguration = new DevicePositionCallbackConfiguration(getDevice().getPositionCallbackConfiguration());
                super.getCallback().positionCallbackConfigurationChanged(getIntent().positionCallbackConfiguration);
            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(MotorizedLinearPotiDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
