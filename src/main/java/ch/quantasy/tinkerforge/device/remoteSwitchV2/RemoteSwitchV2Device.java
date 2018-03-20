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
package ch.quantasy.tinkerforge.device.remoteSwitchV2;

import ch.quantasy.gateway.binding.tinkerforge.remoteSwitch.SocketParameters;
import ch.quantasy.gateway.binding.tinkerforge.remoteSwitchV2.RemoteSwitchConfiguration;
import ch.quantasy.gateway.binding.tinkerforge.remoteSwitchV2.RemoteSwitchV2Intent;
import ch.quantasy.tinkerforge.device.generic.GenericDevice;
import ch.quantasy.tinkerforge.stack.TinkerforgeStack;
import com.tinkerforge.BrickletRemoteSwitch;
import com.tinkerforge.BrickletRemoteSwitchV2;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class RemoteSwitchV2Device extends GenericDevice<BrickletRemoteSwitchV2, RemoteSwitchDeviceV2Callback, RemoteSwitchV2Intent> implements BrickletRemoteSwitchV2.SwitchingDoneListener {

    private boolean isSwitching;
    private transient SocketParameters socketParameters;

    public RemoteSwitchV2Device(TinkerforgeStack stack, BrickletRemoteSwitchV2 device) throws NotConnectedException, TimeoutException {
        super(stack, device, new RemoteSwitchV2Intent());
    }

    @Override
    protected void addDeviceListeners(BrickletRemoteSwitchV2 device) {
        device.addSwitchingDoneListener(this);
        device.addRemoteStatusAListener(super.getCallback());
        device.addRemoteStatusBListener(super.getCallback());
        device.addRemoteStatusCListener(super.getCallback());
    }

    @Override
    protected void removeDeviceListeners(BrickletRemoteSwitchV2 device) {
        device.removeSwitchingDoneListener(this);
        this.switchingDone();
        device.removeRemoteStatusAListener(super.getCallback());
        device.removeRemoteStatusBListener(super.getCallback());
        device.removeRemoteStatusCListener(super.getCallback());
    }

    @Override
    public void update(RemoteSwitchV2Intent intent) {
        if (intent == null) {
            return;
        }
        if (!intent.isValid()) {
            return;
        }
        if (intent.repeats != null) {

            try {
                getDevice().setRepeats(intent.repeats);
                super.getCallback().repeatsChanged(getDevice().getRepeats());
                getIntent().repeats = getDevice().getRepeats();
            } catch (Exception ex) {
                Logger.getLogger(RemoteSwitchV2Device.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (intent.dimSocketBParameters != null) {
            synchronized (this) {
                blockWhileBusy();
                try {
                    getDevice().dimSocketB(intent.dimSocketBParameters.getAddress(), intent.dimSocketBParameters.getUnit(), intent.dimSocketBParameters.getDimValue());
                    this.socketParameters = intent.dimSocketBParameters;
                    isSwitching = true;
                } catch (Exception ex) {
                    Logger.getLogger(RemoteSwitchV2Device.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        if (intent.switchSocketAParameters != null) {
            synchronized (this) {
                blockWhileBusy();
                try {
                    getDevice().switchSocketA(intent.switchSocketAParameters.getHouseCode(), intent.switchSocketAParameters.getReceiverCode(), intent.switchSocketAParameters.getSwitchingValue().getValue());
                    this.socketParameters = intent.switchSocketAParameters;
                    isSwitching = true;
                } catch (Exception ex) {
                    Logger.getLogger(RemoteSwitchV2Device.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        if (intent.switchSocketBParameters != null) {
            synchronized (this) {
                blockWhileBusy();
                try {
                    getDevice().switchSocketB(intent.switchSocketBParameters.getAddress(), intent.switchSocketBParameters.getUnit(), intent.switchSocketBParameters.getSwitchingValue().getValue());
                    this.socketParameters = intent.switchSocketBParameters;
                    isSwitching = true;
                } catch (Exception ex) {
                    Logger.getLogger(RemoteSwitchV2Device.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        if (intent.switchSocketCParameters != null) {
            synchronized (this) {
                blockWhileBusy();
                try {
                    getDevice().switchSocketC(intent.switchSocketCParameters.getSystemCode(), intent.switchSocketCParameters.getDeviceCode(), intent.switchSocketCParameters.getSwitchingValue().getValue());
                    this.socketParameters = intent.switchSocketCParameters;
                    isSwitching = true;
                } catch (Exception ex) {
                    Logger.getLogger(RemoteSwitchV2Device.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        if (intent.remoteSwitchConfiguration != null) {
            try {
                getDevice().setRemoteConfiguration(intent.remoteSwitchConfiguration.remoteType.getType(), intent.remoteSwitchConfiguration.minimumRepeats, intent.remoteSwitchConfiguration.callbackEnabled);
                this.getIntent().remoteSwitchConfiguration = new RemoteSwitchConfiguration(getDevice().getRemoteConfiguration());
                super.getCallback().remoteSwitchConfigChanged(this.getIntent().remoteSwitchConfiguration);
            } catch (Exception ex) {
                Logger.getLogger(RemoteSwitchV2Device.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    private synchronized boolean blockWhileBusy() {
        while (isSwitching) {
            try {
                try {
                    if (getDevice().getSwitchingState() == BrickletRemoteSwitch.SWITCHING_STATE_READY) {
                        switchingDone();

                    }
                } catch (TimeoutException | NotConnectedException ex) {
                    Logger.getLogger(RemoteSwitchV2Device.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
                wait(5000);

            } catch (InterruptedException ex) {
                Logger.getLogger(RemoteSwitchV2Device.class
                        .getName()).log(Level.SEVERE, null, ex);
                return false;
            }
        }
        return true;
    }

    @Override
    public synchronized void switchingDone() {
        isSwitching = false;
        notifyAll();
        super.getCallback().switchingDone(socketParameters);
    }

}
