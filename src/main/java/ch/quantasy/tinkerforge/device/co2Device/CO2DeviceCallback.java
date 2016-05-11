/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerforge.device.co2Device;

import ch.quantasy.tinkerforge.device.generic.DeviceCallback;
import com.tinkerforge.BrickletCO2;

/**
 *
 * @author reto
 */
public interface CO2DeviceCallback extends DeviceCallback, BrickletCO2.CO2ConcentrationListener, BrickletCO2.CO2ConcentrationReachedListener {

   
    public void co2ConcentrationCallbackPeriodChanged(long period);

    public void debouncePeriodChanged(long period);

    public void co2ConcentrationCallbackThresholdChanged(BrickletCO2.CO2ConcentrationCallbackThreshold threshold);

   }
