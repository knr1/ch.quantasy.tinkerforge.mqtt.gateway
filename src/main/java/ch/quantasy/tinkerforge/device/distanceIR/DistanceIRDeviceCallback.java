/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerforge.device.distanceIR;

import ch.quantasy.tinkerforge.device.generic.DeviceCallback;
import com.tinkerforge.BrickletDistanceIR;


/**
 *
 * @author reto
 */
public interface DistanceIRDeviceCallback extends DeviceCallback, BrickletDistanceIR.AnalogValueListener, BrickletDistanceIR.AnalogValueReachedListener, BrickletDistanceIR.DistanceListener, BrickletDistanceIR.DistanceReachedListener {

    public void distanceCallbackPeriodChanged(long period);

    public void analogValueCallbackPeriodChanged(long period);

    public void debouncePeriodChanged(long period);

    public void analogValueCallbackThresholdChanged(DeviceAnalogValueCallbackThreshold threshold);

    public void distanceCallbackThresholdChanged(DeviceDistanceCallbackThreshold threshold);
}
