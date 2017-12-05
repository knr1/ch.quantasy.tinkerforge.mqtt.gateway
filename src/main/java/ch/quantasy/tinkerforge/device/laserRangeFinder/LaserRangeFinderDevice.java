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

import ch.quantasy.gateway.message.intent.laserRangeFinder.SensorHardware;
import ch.quantasy.gateway.message.intent.laserRangeFinder.DeviceMode;
import ch.quantasy.gateway.message.intent.laserRangeFinder.DeviceVelocityCallbackThreshold;
import ch.quantasy.gateway.message.intent.laserRangeFinder.DeviceAveraging;
import ch.quantasy.gateway.message.intent.laserRangeFinder.DeviceConfiguration;
import ch.quantasy.gateway.message.intent.laserRangeFinder.DeviceDistanceCallbackThreshold;
import ch.quantasy.gateway.message.intent.laserRangeFinder.LaserRangeFinderIntent;
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
public class LaserRangeFinderDevice extends GenericDevice<BrickletLaserRangeFinder, LaserRangeFinderDeviceCallback, LaserRangeFinderIntent> {

    private SensorHardware sensorHardware;

    public LaserRangeFinderDevice(TinkerforgeStack stack, BrickletLaserRangeFinder device) throws NotConnectedException, TimeoutException {
        super(stack, device, new LaserRangeFinderIntent());
    }

    @Override
    protected void addDeviceListeners(BrickletLaserRangeFinder device) {
        device.addDistanceListener(super.getCallback());
        device.addDistanceReachedListener(super.getCallback());
        device.addVelocityListener(super.getCallback());
        device.addVelocityReachedListener(super.getCallback());
        if (sensorHardware == null) {
            updateSensorHardwareVersion();
        }

    }

    @Override
    protected void removeDeviceListeners(BrickletLaserRangeFinder device) {
        device.removeDistanceListener(super.getCallback());
        device.removeDistanceReachedListener(super.getCallback());
        device.removeVelocityListener(super.getCallback());
        device.removeVelocityReachedListener(super.getCallback());
    }

    private void updateSensorHardwareVersion() {
        try {
            this.sensorHardware = new SensorHardware(getDevice().getSensorHardwareVersion());
            super.getCallback().sensorHardware(this.sensorHardware);

        } catch (NotConnectedException | TimeoutException ex) {
            Logger.getLogger(LaserRangeFinderDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(LaserRangeFinderIntent intent) {
        if (intent == null) {
            return;
        }
        if (!intent.isValid()) {
            return;
        }

        if (intent.debouncePeriod != null) {
            try {
                getDevice().setDebouncePeriod(intent.debouncePeriod);
                getIntent().debouncePeriod = getDevice().getDebouncePeriod();
                super.getCallback().debouncePeriodChanged(getIntent().debouncePeriod);
            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(LaserRangeFinderDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (intent.distanceCallbackPeriod != null) {
            try {
                getDevice().setDistanceCallbackPeriod(intent.distanceCallbackPeriod);
                getIntent().distanceCallbackPeriod = getDevice().getDistanceCallbackPeriod();
                super.getCallback().distanceCallbackPeriodChanged(getIntent().distanceCallbackPeriod);
            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(LaserRangeFinderDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (intent.velocityCallbackPeriod != null) {
            try {
                getDevice().setVelocityCallbackPeriod(intent.velocityCallbackPeriod);
                getIntent().velocityCallbackPeriod = getDevice().getVelocityCallbackPeriod();
                super.getCallback().velocityCallbackPeriodChanged(getIntent().velocityCallbackPeriod);
            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(LaserRangeFinderDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (intent.distanceCallbackThreshold != null) {
            try {
                getDevice().setDistanceCallbackThreshold(intent.distanceCallbackThreshold.getOption(), intent.distanceCallbackThreshold.getMin(), intent.distanceCallbackThreshold.getMax());
                getIntent().distanceCallbackThreshold = new DeviceDistanceCallbackThreshold(getDevice().getDistanceCallbackThreshold());
                super.getCallback().distanceCallbackThresholdChanged(getIntent().distanceCallbackThreshold);
            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(LaserRangeFinderDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (intent.velocityCallbackThreshold != null) {
            try {
                getDevice().setVelocityCallbackThreshold(intent.velocityCallbackThreshold.getOption(), intent.velocityCallbackThreshold.getMin(), intent.velocityCallbackThreshold.getMax());
                getIntent().velocityCallbackThreshold = new DeviceVelocityCallbackThreshold(getDevice().getVelocityCallbackThreshold());
                super.getCallback().velocityCallbackThresholdChanged(getIntent().velocityCallbackThreshold);
            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(LaserRangeFinderDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (intent.laserEnabled != null) {
            try {
                if (intent.laserEnabled) {
                    getDevice().enableLaser();
                } else {
                    getDevice().disableLaser();
                }
                getIntent().laserEnabled = getDevice().isLaserEnabled();
                super.getCallback().laserStatusChanged(getIntent().laserEnabled);
            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(LaserRangeFinderDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (intent.movingAverage != null) {
            try {
                getDevice().setMovingAverage(intent.movingAverage.getAveragingDistance(), intent.movingAverage.getAveragingVelocity());
                getIntent().movingAverage = new DeviceAveraging(getDevice().getMovingAverage());
                super.getCallback().movingAverageChanged(getIntent().movingAverage);

            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(LaserRangeFinderDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (intent.mode != null) {
            try {
                getDevice().setMode(intent.mode.getMode().getValue());
                getIntent().mode = new DeviceMode(getDevice().getMode());
                super.getCallback().deviceModeChanged(getIntent().mode);

            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(LaserRangeFinderDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (intent.configuration != null) {
            try {
                getDevice().setConfiguration(intent.configuration.getAquisitionCount(), intent.configuration.getQuickTermination(), intent.configuration.getThresholdValue(), intent.configuration.getMeasurementFrequency());
                getIntent().configuration = new DeviceConfiguration(getDevice().getConfiguration());
                super.getCallback().deviceConfigurationChanged(getIntent().configuration);

            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(LaserRangeFinderDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
