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
package ch.quantasy.tinkerforge.device.hallEffect;

import ch.quantasy.gateway.message.hallEffect.DeviceConfiguration;
import ch.quantasy.gateway.message.hallEffect.HallEffectIntent;
import ch.quantasy.tinkerforge.device.generic.GenericDevice;
import ch.quantasy.tinkerforge.stack.TinkerforgeStack;
import com.tinkerforge.BrickletHallEffect;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class HallEffectDevice extends GenericDevice<BrickletHallEffect, HallEffectDeviceCallback, HallEffectIntent> {

    public HallEffectDevice(TinkerforgeStack stack, BrickletHallEffect device) throws NotConnectedException, TimeoutException {
        super(stack, device, new HallEffectIntent());
    }

    @Override
    protected void addDeviceListeners(BrickletHallEffect device) {
        device.addEdgeCountListener(super.getCallback());

    }

    @Override
    protected void removeDeviceListeners(BrickletHallEffect device) {
        device.removeEdgeCountListener(super.getCallback());
    }

    public void update(HallEffectIntent intent) {
        if (intent == null) {
            return;
        }
        if (!intent.isValid()) {
            return;
        }
        if (intent.edgeCountInterrupt != null) {
            try {
                getDevice().setEdgeInterrupt(intent.edgeCountInterrupt);
                getIntent().edgeCountInterrupt = getDevice().getEdgeInterrupt();
                super.getCallback().edgeInterruptChanged(getIntent().edgeCountInterrupt);
            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(HallEffectDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (intent.edgeCountCallbackPeriod != null) {
            try {
                getDevice().setEdgeCountCallbackPeriod(intent.edgeCountCallbackPeriod);
                getIntent().edgeCountCallbackPeriod = getDevice().getEdgeCountCallbackPeriod();
                super.getCallback().edgeCountCallbackPeriodChanged(getIntent().edgeCountCallbackPeriod);
            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(HallEffectDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (intent.edgeCountConfiguration != null) {
            try {
                getDevice().setEdgeCountConfig(intent.edgeCountConfiguration.getEdgeType().getValue(), intent.edgeCountConfiguration.getDebounce());
                getIntent().edgeCountConfiguration = new DeviceConfiguration(getDevice().getEdgeCountConfig());
                super.getCallback().edgeCountConfigChanged(getIntent().edgeCountConfiguration);
            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(HallEffectDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (intent.edgeCountReset != null) {
            try {
                long edgeCount = getDevice().getEdgeCount(intent.edgeCountReset);
                super.getCallback().edgeCountReset(edgeCount);
            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(HallEffectDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
