/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerforge.device.dc;


import ch.quantasy.tinkerforge.device.generic.DeviceCallback;
import com.tinkerforge.BrickDC;

/**
 *
 * @author reto
 */
public interface DCDeviceCallback extends DeviceCallback, BrickDC.CurrentVelocityListener, BrickDC.EmergencyShutdownListener, BrickDC.UnderVoltageListener, BrickDC.VelocityReachedListener {
    public void accelerationChanged(Integer acceleration);
    public void velocityPeriodChanged(Integer velocityPeriod);
    public void driveModeChanged(Short driverMode);
    public void minimumVoltageChanged(Integer minimumVoltage);
    public void PWMFrequencyChanged(Integer pwmFrequency);
    public void velocityChanged(Short velocity);
    public void enabledChanged(Boolean isEnabled);
    public void fullBrake();
}
