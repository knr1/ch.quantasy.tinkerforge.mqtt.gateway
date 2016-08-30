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
package ch.quantasy.tinkerforge.device.gps;

import ch.quantasy.tinkerforge.device.generic.GenericDevice;
import ch.quantasy.tinkerforge.stack.TinkerforgeStackAddress;
import com.tinkerforge.BrickletGPS;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class GPSDevice extends GenericDevice<BrickletGPS, GPSDeviceCallback> {

    private Long altitudeCallbackPeriod;
    private Long statusCallbackPeriod;
    private Long motionCallbackPeriod;
    private Long dateTimeCallbackPeriod;
    private Long coordinatesCallbackPeriod;

    public GPSDevice(TinkerforgeStackAddress address, BrickletGPS device) throws NotConnectedException, TimeoutException {
        super(address, device);
    }

    @Override
    protected void addDeviceListeners() {
        getDevice().addAltitudeListener(super.getCallback());
        getDevice().addCoordinatesListener(super.getCallback());
        getDevice().addDateTimeListener(super.getCallback());
        getDevice().addMotionListener(super.getCallback());
        getDevice().addStatusListener(super.getCallback());

        if (altitudeCallbackPeriod != null) {
            setAltitudeCallbackPeriod(altitudeCallbackPeriod);
        }
        if (statusCallbackPeriod != null) {
            setStatusCallbackPeriod(this.statusCallbackPeriod);
        }
        if (motionCallbackPeriod != null) {
            setMotionCallbackPeriod(motionCallbackPeriod);
        }
        if(dateTimeCallbackPeriod!=null){
            setDateTimeCallbackPeriod(dateTimeCallbackPeriod);
        }
        if(coordinatesCallbackPeriod!=null){
            setCoordinatesCallbackPeriod(coordinatesCallbackPeriod);
        }
        
    }

    @Override
    protected void removeDeviceListeners() {
        getDevice().removeAltitudeListener(super.getCallback());
        getDevice().removeCoordinatesListener(super.getCallback());
        getDevice().removeDateTimeListener(super.getCallback());
        getDevice().removeMotionListener(super.getCallback());
        getDevice().removeStatusListener(super.getCallback());
    }

    public void restart(RestartType restartType) {
        try {
            getDevice().restart(restartType.getValue());
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(GPSDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setAltitudeCallbackPeriod(Long period) {
        try {
            getDevice().setAltitudeCallbackPeriod(period);
            this.altitudeCallbackPeriod = getDevice().getAltitudeCallbackPeriod();
            super.getCallback().altitudeCallbackPeriodChanged(this.altitudeCallbackPeriod);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(GPSDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setCoordinatesCallbackPeriod(Long period) {
        try {
            getDevice().setCoordinatesCallbackPeriod(period);
            this.coordinatesCallbackPeriod = getDevice().getCoordinatesCallbackPeriod();
            super.getCallback().coordinatesCallbackPeriodChanged(this.coordinatesCallbackPeriod);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(GPSDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setDateTimeCallbackPeriod(Long period) {
        try {
            getDevice().setDateTimeCallbackPeriod(period);
            this.dateTimeCallbackPeriod = getDevice().getDateTimeCallbackPeriod();
            super.getCallback().dateTimeCallbackPeriodChanged(this.dateTimeCallbackPeriod);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(GPSDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void setMotionCallbackPeriod(Long period) {
        try {
            getDevice().setMotionCallbackPeriod(period);
            this.motionCallbackPeriod = getDevice().getMotionCallbackPeriod();
            super.getCallback().motionCallbackPeriodChanged(motionCallbackPeriod);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(GPSDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void setStatusCallbackPeriod(Long period) {
        try {
            getDevice().setStatusCallbackPeriod(period);
            this.statusCallbackPeriod = getDevice().getStatusCallbackPeriod();
            super.getCallback().statusCallbackPeriodChanged(statusCallbackPeriod);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(GPSDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
