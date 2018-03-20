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
package ch.quantasy.tinkerforge.device.master;

import ch.quantasy.gateway.binding.tinkerforge.master.MasterIntent;
import ch.quantasy.gateway.binding.tinkerforge.master.StackVoltageCallbackThreshold;
import ch.quantasy.gateway.binding.tinkerforge.master.USBVoltageCallbackThreshold;
import ch.quantasy.gateway.binding.tinkerforge.master.StackCurrentCallbackThreshold;
import ch.quantasy.tinkerforge.device.generic.GenericDevice;
import ch.quantasy.tinkerforge.stack.TinkerforgeStack;
import com.tinkerforge.BrickMaster;

import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class MasterDevice extends GenericDevice<BrickMaster, MasterDeviceCallback, MasterIntent> {

    public MasterDevice(TinkerforgeStack stack, BrickMaster device) throws NotConnectedException, TimeoutException {
        super(stack, device, new MasterIntent());
    }

    @Override
    protected void addDeviceListeners(BrickMaster device) {
        device.addStackCurrentListener(super.getCallback());
        device.addStackCurrentReachedListener(super.getCallback());
        device.addStackVoltageListener(super.getCallback());
        device.addStackVoltageReachedListener(super.getCallback());
        device.addUSBVoltageListener(super.getCallback());
        device.addUSBVoltageReachedListener(super.getCallback());
    }

    @Override
    protected void removeDeviceListeners(BrickMaster device) {
        device.removeStackCurrentListener(super.getCallback());
        device.removeStackCurrentReachedListener(super.getCallback());
        device.removeStackVoltageListener(super.getCallback());
        device.removeStackVoltageReachedListener(super.getCallback());
        device.removeUSBVoltageListener(super.getCallback());
        device.removeUSBVoltageReachedListener(super.getCallback());
    }

    @Override
    public void update(MasterIntent intent) {
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
                Logger.getLogger(MasterDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (intent.currentCallbackPeriod != null) {
            try {
                getDevice().setStackCurrentCallbackPeriod(intent.currentCallbackPeriod);
                getIntent().currentCallbackPeriod = getDevice().getStackCurrentCallbackPeriod();
                super.getCallback().stackCurrentCallbackPeriodChanged(getIntent().currentCallbackPeriod);
            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(MasterDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (intent.currentCallbackThreshold != null) {
            try {
                getDevice().setStackCurrentCallbackThreshold(intent.currentCallbackThreshold.getOption(), intent.currentCallbackThreshold.getMin(), intent.currentCallbackThreshold.getMax());
                getIntent().currentCallbackThreshold = new StackCurrentCallbackThreshold(getDevice().getStackCurrentCallbackThreshold());
                super.getCallback().stackCurrentCallbackThresholdChanged(getIntent().currentCallbackThreshold);
            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(MasterDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (intent.stackVoltageCallbackPeriod != null) {
            try {
                getDevice().setStackVoltageCallbackPeriod(intent.stackVoltageCallbackPeriod);
                getIntent().stackVoltageCallbackPeriod = getDevice().getStackVoltageCallbackPeriod();
                super.getCallback().stackVoltageCallbackPeriodChanged(getIntent().stackVoltageCallbackPeriod);
            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(MasterDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (intent.stackVoltageCallbackThreshold != null) {
            try {
                getDevice().setStackVoltageCallbackThreshold(intent.stackVoltageCallbackThreshold.getOption(), intent.stackVoltageCallbackThreshold.getMin(), intent.stackVoltageCallbackThreshold.getMax());
                getIntent().stackVoltageCallbackThreshold = new StackVoltageCallbackThreshold(getDevice().getStackVoltageCallbackThreshold());
                super.getCallback().stackVoltageCallbackThresholdChanged(getIntent().stackVoltageCallbackThreshold);
            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(MasterDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (intent.usbVoltageCallbackPeriod != null) {
            try {
                getDevice().setUSBVoltageCallbackPeriod(intent.usbVoltageCallbackPeriod);
                getIntent().usbVoltageCallbackPeriod = getDevice().getUSBVoltageCallbackPeriod();
                super.getCallback().usbVoltageCallbackPeriodChanged(getIntent().usbVoltageCallbackPeriod);
            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(MasterDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (intent.usbVoltageCallbackThreshold != null) {
            try {
                getDevice().setUSBVoltageCallbackThreshold(intent.usbVoltageCallbackThreshold.getOption(), intent.usbVoltageCallbackThreshold.getMin(), intent.usbVoltageCallbackThreshold.getMax());
                getIntent().usbVoltageCallbackThreshold = new USBVoltageCallbackThreshold(getDevice().getUSBVoltageCallbackThreshold());
                super.getCallback().USBVoltageCallbackThresholdChanged(getIntent().usbVoltageCallbackThreshold);
            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(MasterDevice.class.getName()).log(Level.SEVERE, null, ex);
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
                super.getCallback().statusLEDEnabledChanged(getIntent().statusLED);
            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(MasterDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (intent.reset != null) {
            try {
                if (intent.reset) {
                    getDevice().reset();
                    super.getCallback().reset();
                }
            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(MasterDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
