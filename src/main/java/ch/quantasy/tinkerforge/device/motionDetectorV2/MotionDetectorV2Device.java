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
package ch.quantasy.tinkerforge.device.motionDetectorV2;

import ch.quantasy.gateway.message.motionDetectorV2.Indicator;
import ch.quantasy.gateway.message.motionDetectorV2.MotionDetectorV2Intent;
import ch.quantasy.mqtt.gateway.client.message.Intent;
import ch.quantasy.tinkerforge.device.generic.GenericDevice;
import ch.quantasy.tinkerforge.stack.TinkerforgeStack;
import com.tinkerforge.BrickletMotionDetectorV2;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class MotionDetectorV2Device extends GenericDevice<BrickletMotionDetectorV2, MotionDetectorV2DeviceCallback, MotionDetectorV2Intent> {

    public MotionDetectorV2Device(TinkerforgeStack stack, BrickletMotionDetectorV2 device) throws NotConnectedException, TimeoutException {
        super(stack, device, new MotionDetectorV2Intent());
    }

    @Override
    protected void addDeviceListeners(BrickletMotionDetectorV2 device) {
        device.addDetectionCycleEndedListener(super.getCallback());
        device.addMotionDetectedListener(super.getCallback());

    }

    @Override
    protected void removeDeviceListeners(BrickletMotionDetectorV2 device) {
        device.removeDetectionCycleEndedListener(super.getCallback());
        device.removeMotionDetectedListener(super.getCallback());
    }

    @Override
    public void update(MotionDetectorV2Intent intent) {
        if (intent.sensitivity != null) {
            try {
                getDevice().setSensitivity(intent.sensitivity);
                getIntent().sensitivity = getDevice().getSensitivity();
                getCallback().sensitivityChanged(getIntent().sensitivity);
            } catch (TimeoutException ex) {
                Logger.getLogger(MotionDetectorV2Device.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NotConnectedException ex) {
                Logger.getLogger(MotionDetectorV2Device.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (intent.indicator != null) {
            try {
                Indicator newIndicator = intent.indicator;
                Indicator currentIndicator = getIntent().indicator;

                if (currentIndicator != null) {
                    newIndicator = currentIndicator.updatedIndicator(intent.indicator);
                }
                if (newIndicator.bottom == null) {
                    newIndicator.bottom = 0;
                }
                if (newIndicator.topLeft == null) {
                    newIndicator.topLeft = 0;
                }
                if (newIndicator.topRight == null) {
                    newIndicator.topRight = 0;
                }

                getDevice().setIndicator(newIndicator.topLeft, newIndicator.topRight, newIndicator.bottom);
                getIntent().indicator = new Indicator(getDevice().getIndicator());
                getCallback().indicatorChanged(getIntent().indicator);
            } catch (TimeoutException ex) {
                Logger.getLogger(MotionDetectorV2Device.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NotConnectedException ex) {
                Logger.getLogger(MotionDetectorV2Device.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

}
