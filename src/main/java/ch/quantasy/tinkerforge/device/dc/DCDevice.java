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
package ch.quantasy.tinkerforge.device.dc;

import ch.quantasy.gateway.message.dc.DCIntent;
import ch.quantasy.tinkerforge.device.generic.GenericDevice;
import ch.quantasy.tinkerforge.stack.TinkerforgeStack;
import com.tinkerforge.BrickDC;

import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class DCDevice extends GenericDevice<BrickDC, DCDeviceCallback, DCIntent> implements BrickDC.EmergencyShutdownListener {

    public DCDevice(TinkerforgeStack stack, BrickDC device) throws NotConnectedException, TimeoutException {
        super(stack, device, new DCIntent());
    }

    @Override
    protected void addDeviceListeners(BrickDC device) {
        device.addCurrentVelocityListener(super.getCallback());
        device.addEmergencyShutdownListener(super.getCallback());
        device.addEmergencyShutdownListener(this);
        device.addUnderVoltageListener(super.getCallback());
        device.addVelocityReachedListener(super.getCallback());

    }

    @Override
    protected void removeDeviceListeners(BrickDC device) {
        device.removeCurrentVelocityListener(super.getCallback());
        device.removeEmergencyShutdownListener(super.getCallback());
        device.removeEmergencyShutdownListener(this);

        device.removeUnderVoltageListener(super.getCallback());
        device.removeVelocityReachedListener(super.getCallback());
    }

    public DCDevice() throws NotConnectedException, TimeoutException {
        super(null, null, null);
    }

    @Override
    public void update(DCIntent intent) {
        if (intent == null) {
            return;
        }
        if (!intent.isValid()) {
            return;
        }
        if (intent.acceleration != null) {
            try {
                getDevice().setAcceleration(intent.acceleration);
                getIntent().acceleration = getDevice().getAcceleration();
                super.getCallback().accelerationChanged(getIntent().acceleration);
            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(DCDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (intent.enable!=null) {
            try {
                if (intent.enable) {
                    getDevice().enable();
                } else {
                    getDevice().disable();
                }
                getIntent().enable = getDevice().isEnabled();
                super.getCallback().enabledChanged(getIntent().enable);
            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(DCDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (intent.fullBrake != null && intent.fullBrake == true) {
            try {
                getDevice().fullBrake();
                super.getCallback().fullBrake();
            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(DCDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (intent.driveMode != null) {
            try {
                getDevice().setDriveMode(intent.driveMode);
                getIntent().driveMode = getDevice().getDriveMode();
                super.getCallback().driveModeChanged(getIntent().driveMode);
            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(DCDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (intent.minimumVoltage != null) {
            try {
                getDevice().setMinimumVoltage(intent.minimumVoltage);
                getIntent().minimumVoltage = getDevice().getMinimumVoltage();
                super.getCallback().minimumVoltageChanged(getIntent().minimumVoltage);
            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(DCDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (intent.pwmFrequency != null) {
            try {
                getDevice().setPWMFrequency(intent.pwmFrequency);
                getIntent().pwmFrequency = getDevice().getPWMFrequency();
                super.getCallback().PWMFrequencyChanged(getIntent().pwmFrequency);
            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(DCDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (intent.velocity != null) {
            try {
                getDevice().setVelocity(intent.velocity);
                getIntent().velocity = getDevice().getVelocity();
                super.getCallback().velocityChanged(getIntent().velocity);
            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(DCDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (intent.velocityPeriod != null) {
            try {
                getDevice().setCurrentVelocityPeriod(intent.velocityPeriod);
                getIntent().velocityPeriod = getDevice().getCurrentVelocityPeriod();
                super.getCallback().velocityPeriodChanged(getIntent().velocityPeriod);
            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(DCDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void emergencyShutdown() {
        try {
            getIntent().enable = getDevice().isEnabled();
            super.getCallback().enabledChanged(getIntent().enable);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(DCDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
