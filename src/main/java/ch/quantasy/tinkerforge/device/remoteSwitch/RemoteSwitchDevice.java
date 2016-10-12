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
package ch.quantasy.tinkerforge.device.remoteSwitch;

import ch.quantasy.tinkerforge.device.generic.GenericDevice;
import ch.quantasy.tinkerforge.stack.TinkerforgeStackAddress;
import com.tinkerforge.BrickletRemoteSwitch;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class RemoteSwitchDevice extends GenericDevice<BrickletRemoteSwitch, RemoteSwitchDeviceCallback> implements BrickletRemoteSwitch.SwitchingDoneListener {

    private Short repeats;
    private boolean isSwitching;
    private transient SocketParameters socketParameters;

    public RemoteSwitchDevice(TinkerforgeStackAddress address, BrickletRemoteSwitch device) throws NotConnectedException, TimeoutException {
        super(address, device);
    }

    @Override
    protected void addDeviceListeners() {
        getDevice().addSwitchingDoneListener(this);
        if (repeats != null) {
            setRepeats(repeats);
        }

    }

    @Override
    protected void removeDeviceListeners() {
        getDevice().removeSwitchingDoneListener(this);
        this.switchingDone();
    }

    public void setRepeats(short repeats) {
        try {
            getDevice().setRepeats(repeats);
            super.getCallback().repeatsChanged(getDevice().getRepeats());
            this.repeats = repeats;
        } catch (Exception ex) {
            Logger.getLogger(RemoteSwitchDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public synchronized void dimSocketB(DimSocketBParameters parameters) {
        while (isSwitching) {
            try {
                wait(5000);
            } catch (InterruptedException ex) {
                Logger.getLogger(RemoteSwitchDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        try {
            getDevice().dimSocketB(parameters.getAddress(), parameters.getUnit(), parameters.getDimValue());
            this.socketParameters = parameters;
            isSwitching = true;

        } catch (Exception ex) {
            Logger.getLogger(RemoteSwitchDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public synchronized void switchSocketB(SwitchSocketBParameters parameters) {
        while (isSwitching) {
            try {
                wait(5000);
            } catch (InterruptedException ex) {
                Logger.getLogger(RemoteSwitchDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        try {
            getDevice().switchSocketB(parameters.getAddress(), parameters.getUnit(), parameters.getSwitchingValue().getValue());
            this.socketParameters = parameters;
            isSwitching = true;

        } catch (Exception ex) {
            Logger.getLogger(RemoteSwitchDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public synchronized void switchSocketA(SwitchSocketAParameters parameters) {
        while (isSwitching) {
            try {
                wait(5000);
            } catch (InterruptedException ex) {
                Logger.getLogger(RemoteSwitchDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        try {
            getDevice().switchSocketA(parameters.getHouseCode(), parameters.getReceiverCode(), parameters.getSwitchingValue().getValue());
            this.socketParameters = parameters;
            isSwitching = true;

        } catch (Exception ex) {
            Logger.getLogger(RemoteSwitchDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public synchronized void switchSocketC(SwitchSocketCParameters parameters) {
        while (isSwitching) {
            try {
                wait(5000);
            } catch (InterruptedException ex) {
                Logger.getLogger(RemoteSwitchDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        try {
            getDevice().switchSocketC(parameters.getSystemCode(), parameters.getDeviceCode(), parameters.getSwitchingValue().getValue());
            this.socketParameters = parameters;
            isSwitching = true;

        } catch (Exception ex) {
            Logger.getLogger(RemoteSwitchDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public synchronized void switchingDone() {
        isSwitching = false;
        notifyAll();
        super.getCallback().switchingDone(socketParameters);
    }

}
