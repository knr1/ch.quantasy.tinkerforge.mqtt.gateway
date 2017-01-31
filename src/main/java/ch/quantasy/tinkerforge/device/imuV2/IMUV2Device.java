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
package ch.quantasy.tinkerforge.device.imuV2;

import ch.quantasy.tinkerforge.device.generic.GenericDevice;
import ch.quantasy.tinkerforge.stack.TinkerforgeStack;
import com.tinkerforge.BrickIMUV2;

import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class IMUV2Device extends GenericDevice<BrickIMUV2, IMUV2DeviceCallback> {

    private Long accelerationPeriod;
    private Long allDataPeriod;
    private Long angularVelocityPeriod;
    private Long gravityVectorPeriod;
    private Long linearAccelerationPeriod;
    private Long magneticFieldPeriod;
    private Long orientationPeriod;
    private Long quaternionPeriod;
    private Long temperaturePeriod;
    
    private Short sensorFusionMode;

    private Boolean isStatusLEDEnabled;
    private Boolean areLEDsEnabled;

    public IMUV2Device(TinkerforgeStack stack, BrickIMUV2 device) throws NotConnectedException, TimeoutException {
        super(stack, device);
    }

    @Override
    protected void addDeviceListeners(BrickIMUV2 device) {
        device.addAccelerationListener(super.getCallback());
        device.addAllDataListener(super.getCallback());
        device.addAngularVelocityListener(super.getCallback());
        device.addGravityVectorListener(super.getCallback());
        device.addLinearAccelerationListener(super.getCallback());
        device.addMagneticFieldListener(super.getCallback());
        device.addOrientationListener(super.getCallback());
        device.addQuaternionListener(super.getCallback());
        device.addTemperatureListener(super.getCallback());

        if (accelerationPeriod != null) {
            setAccelerationPeriod(accelerationPeriod);
        }
        if (allDataPeriod != null) {
            setAllDataPeriod(allDataPeriod);
        }
        if (angularVelocityPeriod != null) {
            setAngularVelocityPeriod(angularVelocityPeriod);
        }
        if (gravityVectorPeriod != null) {
            setGravityVectorPeriod(gravityVectorPeriod);
        }
        if (linearAccelerationPeriod != null) {
            setLinearAccelerationPeriod(linearAccelerationPeriod);
        }
        if (magneticFieldPeriod != null) {
            setMagneticFieldPeriod(magneticFieldPeriod);
        }
        if (orientationPeriod != null) {
            setOrientationPeriod(orientationPeriod);
        }
        if (quaternionPeriod != null) {
            setQuaternionPeriod(quaternionPeriod);
        }
        if (temperaturePeriod != null) {
            setTemperaturePeriod(temperaturePeriod);
        }
        if(isStatusLEDEnabled!=null){
            setStatusLED(isStatusLEDEnabled);
        }
        if(areLEDsEnabled!=null){
            setLEDs(areLEDsEnabled);
        }
        if(sensorFusionMode!=null){
            setSensorFusionMode(sensorFusionMode);
        }
    }

    @Override
    protected void removeDeviceListeners(BrickIMUV2 device) {
        device.removeAllDataListener(super.getCallback());
        device.removeAngularVelocityListener(super.getCallback());
        device.removeGravityVectorListener(super.getCallback());
        device.removeLinearAccelerationListener(super.getCallback());
        device.removeMagneticFieldListener(super.getCallback());
        device.removeOrientationListener(super.getCallback());
        device.removeQuaternionListener(super.getCallback());
        device.removeTemperatureListener(super.getCallback());
    }

    public void setAccelerationPeriod(Long accelerationPeriod) {
        try {
            getDevice().setAccelerationPeriod(accelerationPeriod);
            this.accelerationPeriod = getDevice().getAccelerationPeriod();
            super.getCallback().accelerationPeriodChanged(this.accelerationPeriod);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(IMUV2Device.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setAllDataPeriod(Long allDataPeriod) {
        try {
            getDevice().setAllDataPeriod(allDataPeriod);
            this.allDataPeriod = getDevice().getAllDataPeriod();
            super.getCallback().allDataPeriodChanged(this.accelerationPeriod);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(IMUV2Device.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setAngularVelocityPeriod(Long angularVelocityPeriod) {
        try {
            getDevice().setAngularVelocityPeriod(angularVelocityPeriod);
            this.angularVelocityPeriod = getDevice().getAngularVelocityPeriod();
            super.getCallback().angularVelocityPeriodChanged(this.angularVelocityPeriod);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(IMUV2Device.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setGravityVectorPeriod(Long gravityVectorPeriod) {
        try {
            getDevice().setGravityVectorPeriod(gravityVectorPeriod);
            this.gravityVectorPeriod = getDevice().getGravityVectorPeriod();
            super.getCallback().gravityVectorPeriodChanged(this.gravityVectorPeriod);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(IMUV2Device.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setLinearAccelerationPeriod(Long linearAccelerationPeriod) {
        try {
            getDevice().setLinearAccelerationPeriod(linearAccelerationPeriod);
            this.linearAccelerationPeriod = getDevice().getLinearAccelerationPeriod();
            super.getCallback().linearAccelerationPeriodChanged(this.linearAccelerationPeriod);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(IMUV2Device.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setMagneticFieldPeriod(Long magneticFieldPeriod) {
        try {
            getDevice().setMagneticFieldPeriod(magneticFieldPeriod);
            this.magneticFieldPeriod = getDevice().getMagneticFieldPeriod();
            super.getCallback().magneticFieldPeriodChanged(this.magneticFieldPeriod);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(IMUV2Device.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setOrientationPeriod(Long orientationPeriod) {
        try {
            getDevice().setOrientationPeriod(orientationPeriod);
            this.orientationPeriod = getDevice().getOrientationPeriod();
            super.getCallback().orientationPeriodChanged(this.orientationPeriod);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(IMUV2Device.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setQuaternionPeriod(Long quaternionPeriod) {
        try {
            getDevice().setQuaternionPeriod(quaternionPeriod);
            this.quaternionPeriod = getDevice().getQuaternionPeriod();
            super.getCallback().quaternionPeriodChanged(this.quaternionPeriod);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(IMUV2Device.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setTemperaturePeriod(Long temperaturePeriod) {
        try {
            getDevice().setTemperaturePeriod(temperaturePeriod);
            this.temperaturePeriod = getDevice().getTemperaturePeriod();
            super.getCallback().temperaturePeriodChanged(this.temperaturePeriod);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(IMUV2Device.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setStatusLED(Boolean isStatusLEDEnabled) {
        try {
            if (isStatusLEDEnabled) {
                getDevice().enableStatusLED();
            } else {
                getDevice().disableStatusLED();
            }
            this.isStatusLEDEnabled = getDevice().isStatusLEDEnabled();
            super.getCallback().statusLEDChanged(this.isStatusLEDEnabled);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(IMUV2Device.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setSensorFusionMode(Short sensorFusionMode) {
        try {
            this.getDevice().setSensorFusionMode(sensorFusionMode);
            this.sensorFusionMode= getDevice().getSensorFusionMode();
            super.getCallback().sensorFusionModeChanged(this.sensorFusionMode);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(IMUV2Device.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void setLEDs(Boolean areLEDsEnabled) {
        try {
            if (areLEDsEnabled) {
                getDevice().ledsOn();;
            } else {
                getDevice().ledsOff();
            }
            this.areLEDsEnabled = getDevice().areLedsOn();
            super.getCallback().LEDsChanged(this.areLEDsEnabled);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(IMUV2Device.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
