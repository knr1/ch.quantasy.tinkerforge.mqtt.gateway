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
package ch.quantasy.tinkerforge.device.dualRelay;

import ch.quantasy.gateway.message.intent.dualRelay.DeviceSelectedRelayState;
import ch.quantasy.gateway.message.intent.dualRelay.DeviceRelayState;
import ch.quantasy.gateway.message.intent.dualRelay.DeviceMonoflopParameters;
import ch.quantasy.gateway.message.intent.dualRelay.DualRelayIntent;
import ch.quantasy.tinkerforge.device.generic.GenericDevice;
import ch.quantasy.tinkerforge.stack.TinkerforgeStack;
import com.tinkerforge.BrickletDualRelay;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class DualRelayDevice extends GenericDevice<BrickletDualRelay, DualRelayDeviceCallback, DualRelayIntent> implements BrickletDualRelay.MonoflopDoneListener {

    public DualRelayDevice(TinkerforgeStack stack, BrickletDualRelay device) throws NotConnectedException, TimeoutException {
        super(stack, device, new DualRelayIntent());
    }

    @Override
    protected void addDeviceListeners(BrickletDualRelay device) {
        device.addMonoflopDoneListener(super.getCallback());
        device.addMonoflopDoneListener(this);
    }

    @Override
    protected void removeDeviceListeners(BrickletDualRelay device) {
        device.removeMonoflopDoneListener(super.getCallback());
        device.removeMonoflopDoneListener(this);

    }

    @Override
    public void update(DualRelayIntent intent) {
        if (intent == null) {
            return;
        }
        if (!intent.isValid()) {
            return;
        }
        if (intent.monoflopParameters != null && !intent.monoflopParameters.isEmpty()) {
            try {
                for (DeviceMonoflopParameters parameters : intent.monoflopParameters) {
                    getDevice().setMonoflop(parameters.getRelay(), parameters.getState(), parameters.getPeriod());
                    BrickletDualRelay.Monoflop monoflop = getDevice().getMonoflop(parameters.getRelay());
                    // This is a patch, as Tinkerforge does not provide the relay.
                    this.getIntent().monoflopParameters.add(new DeviceMonoflopParameters(parameters.getRelay(), monoflop));
                }
                this.getIntent().relayState = new DeviceRelayState(getDevice().getState());
                super.getCallback().stateChanged(this.getIntent().relayState);
            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(DualRelayDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (intent.selectedRelayStates != null && !intent.selectedRelayStates.isEmpty()) {
            try {
                for (DeviceSelectedRelayState parameters : intent.selectedRelayStates) {
                    getDevice().setSelectedState(parameters.getRelay(), parameters.getState());
                }
                this.getIntent().relayState = new DeviceRelayState(getDevice().getState());
                super.getCallback().stateChanged(this.getIntent().relayState);
            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(DualRelayDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (intent.relayState != null) {
            try {
                getDevice().setState(intent.relayState.getRelay1(), intent.relayState.getRelay2());
                getIntent().relayState = new DeviceRelayState(getDevice().getState());
                super.getCallback().stateChanged(getIntent().relayState);
            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(DualRelayDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void monoflopDone(short s, boolean bln) {
        try {
            this.getIntent().monoflopParameters.add(new DeviceMonoflopParameters(s, bln, 0));
            this.getIntent().relayState = new DeviceRelayState(getDevice().getState());
            super.getCallback().stateChanged(this.getIntent().relayState);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(DualRelayDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
