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
package ch.quantasy.tinkerforge.device.gpsV2;

import ch.quantasy.tinkerforge.device.generic.GenericDevice;
import ch.quantasy.tinkerforge.stack.TinkerforgeStack;
import com.tinkerforge.BrickletGPSV2;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class GPSv2Device extends GenericDevice<BrickletGPSV2, GPSv2DeviceCallback> {

    private Long altitudeCallbackPeriod;
    private Long statusCallbackPeriod;
    private Long motionCallbackPeriod;
    private Long dateTimeCallbackPeriod;
    private Long coordinatesCallbackPeriod;
    private StatusLEDConfig statusLEDConfig;
    private FixLEDConfig fixLEDConfig;

    public GPSv2Device(TinkerforgeStack stack, BrickletGPSV2 device) throws NotConnectedException, TimeoutException {
        super(stack, device);
    }

    @Override
    protected void addDeviceListeners(BrickletGPSV2 device) {
        device.addAltitudeListener(super.getCallback());
        device.addCoordinatesListener(super.getCallback());
        device.addDateTimeListener(super.getCallback());
        device.addMotionListener(super.getCallback());
        device.addStatusListener(super.getCallback());
        device.addPulsePerSecondListener(super.getCallback());

        if (altitudeCallbackPeriod != null) {
            setAltitudeCallbackPeriod(altitudeCallbackPeriod);
        }
        if (statusCallbackPeriod != null) {
            setStatusCallbackPeriod(this.statusCallbackPeriod);
        }
        if (motionCallbackPeriod != null) {
            setMotionCallbackPeriod(motionCallbackPeriod);
        }
        if (dateTimeCallbackPeriod != null) {
            setDateTimeCallbackPeriod(dateTimeCallbackPeriod);
        }
        if (coordinatesCallbackPeriod != null) {
            setCoordinatesCallbackPeriod(coordinatesCallbackPeriod);
        }
        if(statusLEDConfig!=null){
            setLEDConfig(statusLEDConfig);
        }
        if(fixLEDConfig!=null){
            setLEDConfig(fixLEDConfig);
        }

    }

    @Override
    protected void removeDeviceListeners(BrickletGPSV2 device) {
        device.removeAltitudeListener(super.getCallback());
        device.removeCoordinatesListener(super.getCallback());
        device.removeDateTimeListener(super.getCallback());
        device.removeMotionListener(super.getCallback());
        device.removeStatusListener(super.getCallback());
        device.removePulsePerSecondListener(super.getCallback());
    }

    public void restart(RestartType restartType) {
        try {
            getDevice().restart(restartType.getValue());
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(GPSv2Device.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setAltitudeCallbackPeriod(Long period) {
        try {
            getDevice().setAltitudeCallbackPeriod(period);
            this.altitudeCallbackPeriod = getDevice().getAltitudeCallbackPeriod();
            super.getCallback().altitudeCallbackPeriodChanged(this.altitudeCallbackPeriod);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(GPSv2Device.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setCoordinatesCallbackPeriod(Long period) {
        try {
            getDevice().setCoordinatesCallbackPeriod(period);
            this.coordinatesCallbackPeriod = getDevice().getCoordinatesCallbackPeriod();
            super.getCallback().coordinatesCallbackPeriodChanged(this.coordinatesCallbackPeriod);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(GPSv2Device.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setDateTimeCallbackPeriod(Long period) {
        try {
            getDevice().setDateTimeCallbackPeriod(period);
            this.dateTimeCallbackPeriod = getDevice().getDateTimeCallbackPeriod();
            super.getCallback().dateTimeCallbackPeriodChanged(this.dateTimeCallbackPeriod);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(GPSv2Device.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setMotionCallbackPeriod(Long period) {
        try {
            getDevice().setMotionCallbackPeriod(period);
            this.motionCallbackPeriod = getDevice().getMotionCallbackPeriod();
            super.getCallback().motionCallbackPeriodChanged(motionCallbackPeriod);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(GPSv2Device.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setStatusCallbackPeriod(Long period) {
        try {
            getDevice().setStatusCallbackPeriod(period);
            this.statusCallbackPeriod = getDevice().getStatusCallbackPeriod();
            super.getCallback().statusCallbackPeriodChanged(statusCallbackPeriod);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(GPSv2Device.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setLEDConfig(FixLEDConfig config) {
        try {
            getDevice().setFixLEDConfig(config.getValue());
            this.fixLEDConfig=FixLEDConfig.getLEDConfigFor(getDevice().getFixLEDConfig());
            super.getCallback().fixLEDConfigChanged(this.fixLEDConfig);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(GPSv2Device.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void setLEDConfig(StatusLEDConfig config) {
        try {
            getDevice().setStatusLEDConfig(config.getValue());
            this.statusLEDConfig=StatusLEDConfig.getLEDConfigFor(getDevice().getStatusLEDConfig());
            super.getCallback().statusLEDConfigChanged(this.statusLEDConfig);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(GPSv2Device.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
