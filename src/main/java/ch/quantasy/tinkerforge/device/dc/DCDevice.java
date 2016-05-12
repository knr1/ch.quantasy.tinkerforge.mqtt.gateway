/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
        getDevice().addCurrentVelocityListener(super.getCallback());
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
