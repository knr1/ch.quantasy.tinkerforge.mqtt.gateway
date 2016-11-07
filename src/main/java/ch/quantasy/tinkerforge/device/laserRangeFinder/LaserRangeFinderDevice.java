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
package ch.quantasy.tinkerforge.device.laserRangeFinder;

import ch.quantasy.tinkerforge.device.generic.GenericDevice;
import ch.quantasy.tinkerforge.stack.TinkerforgeStack;
import com.tinkerforge.BrickletLaserRangeFinder;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class LaserRangeFinderDevice extends GenericDevice<BrickletLaserRangeFinder, LaserRangeFinderDeviceCallback> {

    private Long distanceCallbackPeriod;
    private Long velocityCallbackPeriod;
    private Long debouncePeriod;
    private DeviceDistanceCallbackThreshold analogValueThreshold;
    private DeviceVelocityCallbackThreshold velocityCallbackThreshold;
    private Boolean laserEnabled;
    private DeviceAveraging averaging;
    private DeviceMode mode;

    public LaserRangeFinderDevice(TinkerforgeStack stack, BrickletLaserRangeFinder device) throws NotConnectedException, TimeoutException {
        super(stack, device);
    }

    @Override
    protected void addDeviceListeners() {
        getDevice().addDistanceListener(super.getCallback());
        getDevice().addDistanceReachedListener(super.getCallback());
        getDevice().addVelocityListener(super.getCallback());
        getDevice().addVelocityReachedListener(super.getCallback());
        if (distanceCallbackPeriod != null) {
            setDistanceCallbackPeriod(distanceCallbackPeriod);
        }
        if (velocityCallbackPeriod != null) {
            setVelocityCallbackPeriod(this.velocityCallbackPeriod);
        }
        if (debouncePeriod != null) {
            setDebouncePeriod(debouncePeriod);
        }
        if (analogValueThreshold != null) {
            setDistanceCallbackThreshold(analogValueThreshold);
        }
        if (velocityCallbackThreshold != null) {
            setVelocityCallbackThreshold(velocityCallbackThreshold);
        }
        if(laserEnabled!=null){
            setLaser(laserEnabled);
        }
        if(averaging!=null){
            setMovingAverage(averaging);
        }
    }

    @Override
    protected void removeDeviceListeners() {
        getDevice().removeDistanceListener(super.getCallback());
        getDevice().removeDistanceReachedListener(super.getCallback());
        getDevice().removeVelocityListener(super.getCallback());
        getDevice().removeVelocityReachedListener(super.getCallback());
    }

    public void setDebouncePeriod(Long period) {
        try {
            getDevice().setDebouncePeriod(period);
            this.debouncePeriod = getDevice().getDebouncePeriod();
            super.getCallback().debouncePeriodChanged(this.debouncePeriod);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(LaserRangeFinderDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setDistanceCallbackPeriod(Long period) {
        try {
            getDevice().setDistanceCallbackPeriod(period);
            this.distanceCallbackPeriod = getDevice().getDistanceCallbackPeriod();
            super.getCallback().distanceCallbackPeriodChanged(this.distanceCallbackPeriod);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(LaserRangeFinderDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setVelocityCallbackPeriod(Long period) {
        try {
            getDevice().setVelocityCallbackPeriod(period);
            this.velocityCallbackPeriod = getDevice().getVelocityCallbackPeriod();
            super.getCallback().velocityCallbackPeriodChanged(this.velocityCallbackPeriod);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(LaserRangeFinderDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setDistanceCallbackThreshold(DeviceDistanceCallbackThreshold threshold) {
        try {
            getDevice().setDistanceCallbackThreshold(threshold.getOption(), threshold.getMin(), threshold.getMax());
            this.analogValueThreshold = new DeviceDistanceCallbackThreshold(getDevice().getDistanceCallbackThreshold());
            super.getCallback().distanceCallbackThresholdChanged(this.analogValueThreshold);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(LaserRangeFinderDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setVelocityCallbackThreshold(DeviceVelocityCallbackThreshold threshold) {
        try {
            getDevice().setVelocityCallbackThreshold(threshold.getOption(), threshold.getMin(), threshold.getMax());
            this.velocityCallbackThreshold = new DeviceVelocityCallbackThreshold(getDevice().getVelocityCallbackThreshold());
            super.getCallback().velocityCallbackThresholdChanged(this.velocityCallbackThreshold);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(LaserRangeFinderDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void setLaser(Boolean laserEnabled){
        try{
            if(laserEnabled){
                getDevice().enableLaser();
            }else{
                getDevice().disableLaser();
            }
            this.laserEnabled=getDevice().isLaserEnabled();
            super.getCallback().laserStatusChanged(laserEnabled);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(LaserRangeFinderDevice.class.getName()).log(Level.SEVERE, null, ex);
        }  
    }
    public void setMovingAverage(DeviceAveraging movingAverage) {
        try {
            getDevice().setMovingAverage(movingAverage.getAveragingDistance(),movingAverage.getAveragingVelocity());
            this.averaging = new DeviceAveraging(getDevice().getMovingAverage());
            super.getCallback().movingAverageChanged(movingAverage);

        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(LaserRangeFinderDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void setMode(DeviceMode deviceMode){
        try {
            getDevice().setMode(deviceMode.getMode().getValue());
            this.mode = new DeviceMode(getDevice().getMode());
            super.getCallback().deviceModeChanged(mode);

        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(LaserRangeFinderDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
