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

import ch.quantasy.gateway.intent.gps.GPSIntent;
import ch.quantasy.gateway.intent.gps.RestartType;
import ch.quantasy.tinkerforge.device.generic.GenericDevice;
import ch.quantasy.tinkerforge.stack.TinkerforgeStack;
import com.tinkerforge.BrickletGPS;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class GPSDevice extends GenericDevice<BrickletGPS, GPSDeviceCallback, GPSIntent> {

    public GPSDevice(TinkerforgeStack stack, BrickletGPS device) throws NotConnectedException, TimeoutException {
        super(stack, device, new GPSIntent());
    }

    @Override
    protected void addDeviceListeners(BrickletGPS device) {
        device.addAltitudeListener(super.getCallback());
        device.addCoordinatesListener(super.getCallback());
        device.addDateTimeListener(super.getCallback());
        device.addMotionListener(super.getCallback());
        device.addStatusListener(super.getCallback());

    }

    @Override
    protected void removeDeviceListeners(BrickletGPS device) {
        device.removeAltitudeListener(super.getCallback());
        device.removeCoordinatesListener(super.getCallback());
        device.removeDateTimeListener(super.getCallback());
        device.removeMotionListener(super.getCallback());
        device.removeStatusListener(super.getCallback());
    }

    public void update(GPSIntent intent) {
        if (intent == null) {
            return;
        }
        if (!intent.isValid()) {
            return;
        }

        if (intent.restart != null) {
            try {
                getDevice().restart(intent.restart.getValue());

            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(GPSDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (intent.altitudeCallbackPeriod != null) {
            try {
                getDevice().setAltitudeCallbackPeriod(intent.altitudeCallbackPeriod);
                getIntent().altitudeCallbackPeriod = getDevice().getAltitudeCallbackPeriod();
                super.getCallback().altitudeCallbackPeriodChanged(getIntent().altitudeCallbackPeriod);
            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(GPSDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (intent.coordinatesCallbackPeriod != null) {
            try {
                getDevice().setCoordinatesCallbackPeriod(intent.coordinatesCallbackPeriod);
                getIntent().coordinatesCallbackPeriod = getDevice().getCoordinatesCallbackPeriod();
                super.getCallback().coordinatesCallbackPeriodChanged(getIntent().coordinatesCallbackPeriod);
            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(GPSDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (intent.dateTimeCallbackPeriod != null) {
            try {
                getDevice().setDateTimeCallbackPeriod(intent.dateTimeCallbackPeriod);
                getIntent().dateTimeCallbackPeriod = getDevice().getDateTimeCallbackPeriod();
                super.getCallback().dateTimeCallbackPeriodChanged(getIntent().dateTimeCallbackPeriod);
            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(GPSDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (intent.motionCallbackPeriod != null) {
            try {
                getDevice().setMotionCallbackPeriod(intent.motionCallbackPeriod);
                getIntent().motionCallbackPeriod = getDevice().getMotionCallbackPeriod();
                super.getCallback().motionCallbackPeriodChanged(getIntent().motionCallbackPeriod);
            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(GPSDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (intent.statusCallbackPeriod != null) {
            try {
                getDevice().setStatusCallbackPeriod(intent.statusCallbackPeriod);
                getIntent().statusCallbackPeriod = getDevice().getStatusCallbackPeriod();
                super.getCallback().statusCallbackPeriodChanged(getIntent().statusCallbackPeriod);
            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(GPSDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
