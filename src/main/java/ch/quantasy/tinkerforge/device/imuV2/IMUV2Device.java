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

import ch.quantasy.gateway.message.IMUV2.IMUV2Intent;
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
public class IMUV2Device extends GenericDevice<BrickIMUV2, IMUV2DeviceCallback, IMUV2Intent> {

    public IMUV2Device(TinkerforgeStack stack, BrickIMUV2 device) throws NotConnectedException, TimeoutException {
        super(stack, device, new IMUV2Intent());
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

    @Override
    public void update(IMUV2Intent intent) {
        if (intent == null) {
            return;
        }
        if (!intent.isValid()) {
            return;
        }
        if (intent.accelerationCallbackPeriod != null) {
            try {
                getDevice().setAccelerationPeriod(intent.accelerationCallbackPeriod);
                getIntent().accelerationCallbackPeriod = getDevice().getAccelerationPeriod();
                super.getCallback().accelerationPeriodChanged(getIntent().accelerationCallbackPeriod);
            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(IMUV2Device.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (intent.allDataCallbackPeriod != null) {
            try {
                getDevice().setAllDataPeriod(intent.allDataCallbackPeriod);
                getIntent().allDataCallbackPeriod = getDevice().getAllDataPeriod();
                super.getCallback().allDataPeriodChanged(getIntent().allDataCallbackPeriod);
            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(IMUV2Device.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (intent.angularVelocityCallbackPeriod != null) {
            try {
                getDevice().setAngularVelocityPeriod(intent.angularVelocityCallbackPeriod);
                getIntent().angularVelocityCallbackPeriod = getDevice().getAngularVelocityPeriod();
                super.getCallback().angularVelocityPeriodChanged(getIntent().angularVelocityCallbackPeriod);
            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(IMUV2Device.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (intent.gravityVectorCallbackPeriod != null) {
            try {
                getDevice().setGravityVectorPeriod(intent.gravityVectorCallbackPeriod);
                getIntent().gravityVectorCallbackPeriod = getDevice().getGravityVectorPeriod();
                super.getCallback().gravityVectorPeriodChanged(getIntent().gravityVectorCallbackPeriod);
            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(IMUV2Device.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (intent.linearAccelerationCallbackPeriod != null) {
            try {
                getDevice().setLinearAccelerationPeriod(intent.linearAccelerationCallbackPeriod);
                getIntent().linearAccelerationCallbackPeriod = getDevice().getLinearAccelerationPeriod();
                super.getCallback().linearAccelerationPeriodChanged(getIntent().linearAccelerationCallbackPeriod);
            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(IMUV2Device.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (intent.magneticFieldCallbackPeriod != null) {
            try {
                getDevice().setMagneticFieldPeriod(intent.magneticFieldCallbackPeriod);
                getIntent().magneticFieldCallbackPeriod = getDevice().getMagneticFieldPeriod();
                super.getCallback().magneticFieldPeriodChanged(intent.magneticFieldCallbackPeriod);
            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(IMUV2Device.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (intent.orientationCallbackPeriod != null) {
            try {
                getDevice().setOrientationPeriod(intent.orientationCallbackPeriod);
                getIntent().orientationCallbackPeriod = getDevice().getOrientationPeriod();
                super.getCallback().orientationPeriodChanged(getIntent().orientationCallbackPeriod);
            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(IMUV2Device.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (intent.quaternionCallbackPeriod != null) {
            try {
                getDevice().setQuaternionPeriod(intent.quaternionCallbackPeriod);
                getIntent().quaternionCallbackPeriod = getDevice().getQuaternionPeriod();
                super.getCallback().quaternionPeriodChanged(getIntent().quaternionCallbackPeriod);
            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(IMUV2Device.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (intent.temperatureCallbackPeriod != null) {
            try {
                getDevice().setTemperaturePeriod(intent.temperatureCallbackPeriod);
                getIntent().temperatureCallbackPeriod = getDevice().getTemperaturePeriod();
                super.getCallback().temperaturePeriodChanged(getIntent().temperatureCallbackPeriod);
            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(IMUV2Device.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (intent.statusLED != null) {
            try {
                if (intent.statusLED) {
                    getDevice().enableStatusLED();
                } else {
                    getDevice().disableStatusLED();
                }
                getIntent().statusLED = getDevice().isStatusLEDEnabled();
                super.getCallback().statusLEDChanged(getIntent().statusLED);
            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(IMUV2Device.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (intent.sensorFusionMode != null) {
            try {
                this.getDevice().setSensorFusionMode(intent.sensorFusionMode);
                getIntent().sensorFusionMode = getDevice().getSensorFusionMode();
                super.getCallback().sensorFusionModeChanged(getIntent().sensorFusionMode);
            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(IMUV2Device.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (intent.leds != null) {
            try {
                if (intent.leds) {
                    getDevice().ledsOn();;
                } else {
                    getDevice().ledsOff();
                }
                getIntent().leds = getDevice().areLedsOn();
                super.getCallback().LEDsChanged(getIntent().leds);
            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(IMUV2Device.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
