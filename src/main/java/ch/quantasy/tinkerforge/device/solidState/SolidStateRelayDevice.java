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
public class SolidStateRelayDevice extends GenericDevice<BrickletSolidStateRelay, SolidStateRelayDeviceCallback> implements BrickletSolidStateRelay.MonoflopDoneListener {

    private DeviceMonoflopParameters monoflopParameters;
    private Boolean state;

    public SolidStateRelayDevice(TinkerforgeStack stack, BrickletSolidStateRelay device) throws NotConnectedException, TimeoutException {
        super(stack, device);
    }

    @Override
    protected void addDeviceListeners() {
        getDevice().addMonoflopDoneListener(super.getCallback());
        getDevice().addMonoflopDoneListener(this);

        if(monoflopParameters!=null) {
            setMonoflop(monoflopParameters);
        }
        if (state != null) {
            setState(state);
        }

    }

    @Override
    protected void removeDeviceListeners() {
        getDevice().removeMonoflopDoneListener(super.getCallback());
        getDevice().removeMonoflopDoneListener(this);

    }

    public void setMonoflop(DeviceMonoflopParameters parameters) {
        try {
            getDevice().setMonoflop(parameters.getState(), parameters.getPeriod());
            this.monoflopParameters=new DeviceMonoflopParameters(getDevice().getMonoflop());
            this.state = getDevice().getState();
            super.getCallback().stateChanged(this.state);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(SolidStateRelayDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setState(Boolean state) {
        try {
            getDevice().setState(state);
            this.state = getDevice().getState();
            super.getCallback().stateChanged(this.state);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(SolidStateRelayDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void monoflopDone(boolean bln) {
        try {
            this.state = getDevice().getState();
            super.getCallback().stateChanged(this.state);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(SolidStateRelayDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
