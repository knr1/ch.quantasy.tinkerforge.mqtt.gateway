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
package ch.quantasy.tinkerforge.device.solidState;

import ch.quantasy.gateway.intent.solidState.DeviceMonoflopParameters;
import ch.quantasy.gateway.intent.solidState.SolidStateRelayIntent;
import ch.quantasy.tinkerforge.device.generic.GenericDevice;
import ch.quantasy.tinkerforge.stack.TinkerforgeStack;
import com.tinkerforge.BrickletSolidStateRelay;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class SolidStateRelayDevice extends GenericDevice<BrickletSolidStateRelay, SolidStateRelayDeviceCallback, SolidStateRelayIntent> implements BrickletSolidStateRelay.MonoflopDoneListener {

    public SolidStateRelayDevice(TinkerforgeStack stack, BrickletSolidStateRelay device) throws NotConnectedException, TimeoutException {
        super(stack, device, new SolidStateRelayIntent());
    }

    @Override
    protected void addDeviceListeners(BrickletSolidStateRelay device) {
        device.addMonoflopDoneListener(super.getCallback());
        device.addMonoflopDoneListener(this);
    }

    @Override
    protected void removeDeviceListeners(BrickletSolidStateRelay device) {
        device.removeMonoflopDoneListener(super.getCallback());
        device.removeMonoflopDoneListener(this);

    }

    @Override
    public void update(SolidStateRelayIntent intent) {
        if (intent == null) {
            return;
        }
        if (!intent.isValid()) {
            return;
        }

        if (intent.monoflopParameters != null) {
            try {
                getDevice().setMonoflop(intent.monoflopParameters.getState(), intent.monoflopParameters.getPeriod());
                getIntent().monoflopParameters = new DeviceMonoflopParameters(getDevice().getMonoflop());
                getIntent().state = getDevice().getState();
                super.getCallback().stateChanged(getIntent().state);
            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(SolidStateRelayDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (intent.state != null) {
            try {
                getDevice().setState(intent.state);
                getIntent().state = getDevice().getState();
                super.getCallback().stateChanged(getIntent().state);
            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(SolidStateRelayDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void monoflopDone(boolean bln
    ) {
        try {
            getIntent().state = getDevice().getState();
            super.getCallback().stateChanged(getIntent().state);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(SolidStateRelayDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
