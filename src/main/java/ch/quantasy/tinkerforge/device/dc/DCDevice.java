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

import ch.quantasy.tinkerforge.device.generic.GenericDevice;
import ch.quantasy.tinkerforge.stack.TinkerforgeStackAddress;
import com.tinkerforge.BrickDC;

import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class DCDevice extends GenericDevice<BrickDC, DCDeviceCallback> implements BrickDC.EmergencyShutdownListener {

    private Integer acceleration;
    private Integer velocityPeriod;
    private Short driverMode;
    private Integer voltage;
    private Integer frequency;
    private Short velocity;
    private Boolean isEnabled;

    public DCDevice(TinkerforgeStackAddress address, BrickDC device) throws NotConnectedException, TimeoutException {
        super(address, device);
    }

    @Override
    protected void addDeviceListeners() {
        getDevice().addCurrentVelocityListener(super.getCallback());
        getDevice().addEmergencyShutdownListener(super.getCallback());
        getDevice().addEmergencyShutdownListener(this);
        getDevice().addUnderVoltageListener(super.getCallback());
        getDevice().addVelocityReachedListener(super.getCallback());

        if (acceleration != null) {
            setAcceleration(acceleration);
        }
        if (velocityPeriod != null) {
            setVelocityPeriod(velocityPeriod);
        }
        if (driverMode != null) {
            setDriveMode(driverMode);
        }
        if (voltage != null) {
            setMinimumVoltage(voltage);
        }
        if (frequency != null) {
            setPWMFrequency(frequency);
        }
        if (velocity != null) {
            setVelocity(velocity);
        }
        if (isEnabled != null) {
            setEnabled(isEnabled);
        }

    }

    @Override
    protected void removeDeviceListeners() {
        getDevice().removeCurrentVelocityListener(super.getCallback());
        getDevice().removeEmergencyShutdownListener(super.getCallback());
        getDevice().removeEmergencyShutdownListener(this);

        getDevice().removeUnderVoltageListener(super.getCallback());
        getDevice().removeVelocityReachedListener(super.getCallback());
    }

    public void setAcceleration(Integer acceleration) {
        try {
            getDevice().setAcceleration(acceleration);
            this.acceleration = getDevice().getAcceleration();
            super.getCallback().accelerationChanged(this.acceleration);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(DCDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setEnabled(Boolean isEnabled) {
        try {
            if (isEnabled) {
                getDevice().enable();
            } else {
                getDevice().disable();
            }
            this.isEnabled = getDevice().isEnabled();
            super.getCallback().enabledChanged(this.isEnabled);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(DCDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void fullBrake() {
        try {
            getDevice().fullBrake();
            super.getCallback().fullBrake();
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(DCDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setDriveMode(Short driveMode) {
        try {
            getDevice().setDriveMode(driveMode);
            this.driverMode = getDevice().getDriveMode();
            super.getCallback().driveModeChanged(this.driverMode);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(DCDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setMinimumVoltage(Integer voltage) {
        try {
            getDevice().setMinimumVoltage(voltage);
            this.voltage = getDevice().getMinimumVoltage();
            super.getCallback().minimumVoltageChanged(this.voltage);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(DCDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setPWMFrequency(Integer frequency) {
        try {
            getDevice().setPWMFrequency(frequency);
            this.frequency = getDevice().getPWMFrequency();
            super.getCallback().PWMFrequencyChanged(this.frequency);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(DCDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setVelocity(Short velocity) {
        try {
            getDevice().setVelocity(velocity);;
            this.velocity = getDevice().getVelocity();
            super.getCallback().velocityChanged(this.velocity);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(DCDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setVelocityPeriod(Integer period) {
        try {
            getDevice().setCurrentVelocityPeriod(period);
            this.velocityPeriod = getDevice().getCurrentVelocityPeriod();
            super.getCallback().velocityPeriodChanged(this.velocityPeriod);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(DCDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void emergencyShutdown() {
        try {
            this.isEnabled = getDevice().isEnabled();
            super.getCallback().enabledChanged(this.isEnabled);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(DCDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
