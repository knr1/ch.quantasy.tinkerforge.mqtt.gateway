/*
 *   "TiMqWay"
 *
 *    TiMqWay(tm): A gateway to provide an MQTT-View for the Tinkerforge(tm) world (Tinkerforge-MQTT-Gateway).
 *
 *    Copyright (c) 2015 Bern University of Applied Sciences (BFH),
 *    Research Institute for Security in the Information Society (RISIS), Wireless Communications & Secure Internet of Things (WiCom & SIoT),
 *    Quellgasse 21, CH-2501 Biel, Switzerland
 *
 *    Licensed under Dual License consisting of:
 *    1. GNU Affero General Public License (AGPL) v3
 *    and
 *    2. Commercial license
 *
 *
 *    1. This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Affero General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Affero General Public License for more details.
 *
 *     You should have received a copy of the GNU Affero General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 *    2. Licensees holding valid commercial licenses for TiMqWay may use this file in
 *     accordance with the commercial license agreement provided with the
 *     Software or, alternatively, in accordance with the terms contained in
 *     a written agreement between you and Bern University of Applied Sciences (BFH),
 *     Research Institute for Security in the Information Society (RISIS), Wireless Communications & Secure Internet of Things (WiCom & SIoT),
 *     Quellgasse 21, CH-2501 Biel, Switzerland.
 *
 *
 *     For further information contact <e-mail: reto.koenig@bfh.ch>
 *
 *
 */
package ch.quantasy.tinkerforge.device.IMU;

import ch.quantasy.tinkerforge.device.generic.GenericDevice;
import ch.quantasy.tinkerforge.stack.TinkerforgeStackAddress;
import com.tinkerforge.BrickIMU;

import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class IMUDevice extends GenericDevice<BrickIMU, IMUDeviceCallback> {

    private Long accelerationPeriod;
    private Long allDataPeriod;
    private Long angularVelocityPeriod;
    private Long magneticFieldPeriod;
    private Long orientationPeriod;
    private Long quaternionPeriod;
    private Boolean isOrientationCalculationOn;

    private Boolean isStatusLEDEnabled;
    private Boolean areLEDsEnabled;

    public IMUDevice(TinkerforgeStackAddress address, BrickIMU device) throws NotConnectedException, TimeoutException {
        super(address, device);
    }

    @Override
    protected void addDeviceListeners() {
        getDevice().addAccelerationListener(super.getCallback());
        getDevice().addAllDataListener(super.getCallback());
        getDevice().addAngularVelocityListener(super.getCallback());
        getDevice().addMagneticFieldListener(super.getCallback());
        getDevice().addOrientationListener(super.getCallback());
        getDevice().addQuaternionListener(super.getCallback());

        if (accelerationPeriod != null) {
            setAccelerationPeriod(accelerationPeriod);
        }
        if (allDataPeriod != null) {
            setAllDataPeriod(allDataPeriod);
        }
        if (angularVelocityPeriod != null) {
            setAngularVelocityPeriod(angularVelocityPeriod);
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
        if(isOrientationCalculationOn!=null){
            setOrientationCalculation(isOrientationCalculationOn);
        }
        if(isStatusLEDEnabled!=null){
            setStatusLED(isStatusLEDEnabled);
        }
        if(areLEDsEnabled!=null){
            setLEDs(areLEDsEnabled);
        }

    }

    @Override
    protected void removeDeviceListeners() {
        getDevice().removeAllDataListener(super.getCallback());
        getDevice().removeAngularVelocityListener(super.getCallback());
        getDevice().removeMagneticFieldListener(super.getCallback());
        getDevice().removeOrientationListener(super.getCallback());
        getDevice().removeQuaternionListener(super.getCallback());
    }

    public void setAccelerationPeriod(Long accelerationPeriod) {
        try {
            getDevice().setAccelerationPeriod(accelerationPeriod);
            this.accelerationPeriod = getDevice().getAccelerationPeriod();
            super.getCallback().accelerationPeriodChanged(this.accelerationPeriod);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(IMUDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setAllDataPeriod(Long allDataPeriod) {
        try {
            getDevice().setAllDataPeriod(allDataPeriod);
            this.allDataPeriod = getDevice().getAllDataPeriod();
            super.getCallback().allDataPeriodChanged(this.accelerationPeriod);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(IMUDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setAngularVelocityPeriod(Long angularVelocityPeriod) {
        try {
            getDevice().setAngularVelocityPeriod(angularVelocityPeriod);
            this.angularVelocityPeriod = getDevice().getAngularVelocityPeriod();
            super.getCallback().angularVelocityPeriodChanged(this.angularVelocityPeriod);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(IMUDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setMagneticFieldPeriod(Long magneticFieldPeriod) {
        try {
            getDevice().setMagneticFieldPeriod(magneticFieldPeriod);
            this.magneticFieldPeriod = getDevice().getMagneticFieldPeriod();
            super.getCallback().magneticFieldPeriodChanged(this.magneticFieldPeriod);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(IMUDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setOrientationCalculation(Boolean isOrientationCalculationOn){
try {
            if (isOrientationCalculationOn) {
                getDevice().orientationCalculationOn();;
            } else {
                getDevice().orientationCalculationOff();
            }
            this.isOrientationCalculationOn = getDevice().isOrientationCalculationOn();
            super.getCallback().orientationCalculationChanged(this.isOrientationCalculationOn);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(IMUDevice.class.getName()).log(Level.SEVERE, null, ex);
        }    }
    public void setOrientationPeriod(Long orientationPeriod) {
        try {
            getDevice().setOrientationPeriod(orientationPeriod);
            this.orientationPeriod = getDevice().getOrientationPeriod();
            super.getCallback().orientationPeriodChanged(this.orientationPeriod);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(IMUDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setQuaternionPeriod(Long quaternionPeriod) {
        try {
            getDevice().setQuaternionPeriod(quaternionPeriod);
            this.quaternionPeriod = getDevice().getQuaternionPeriod();
            super.getCallback().quaternionPeriodChanged(this.quaternionPeriod);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(IMUDevice.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(IMUDevice.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(IMUDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
