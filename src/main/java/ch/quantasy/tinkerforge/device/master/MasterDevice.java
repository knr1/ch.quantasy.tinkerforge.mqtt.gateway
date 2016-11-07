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
public class MasterDevice extends GenericDevice<BrickMaster, MasterDeviceCallback> {

    private Long debouncePeriod;
    private Long currentCallbackPeriod;
    private StackCurrentCallbackThreshold currentCallbackThreshold;
    private Boolean isStatusLEDEnabled;
    private Long voltageCallbackPeriod;
    private StackVoltageCallbackThreshold voltageCallbackThreshold;
    private Long usbVoltageCallbackPeriod;
    private USBVoltageCallbackThreshold usbVoltageCallbackThreshold;

    public MasterDevice(TinkerforgeStack stack, BrickMaster device) throws NotConnectedException, TimeoutException {
        super(stack, device);
    }

    @Override
    protected void addDeviceListeners(BrickMaster device) {
        device.addStackCurrentListener(super.getCallback());
        device.addStackCurrentReachedListener(super.getCallback());
        device.addStackVoltageListener(super.getCallback());
        device.addStackVoltageReachedListener(super.getCallback());
        device.addUSBVoltageListener(super.getCallback());
        device.addUSBVoltageReachedListener(super.getCallback());

        if (debouncePeriod != null) {
            setDebouncePeriod(debouncePeriod);
        }
        if (currentCallbackPeriod != null) {
            setStackCurrentCallbackPeriod(currentCallbackPeriod);
        }
        if (currentCallbackThreshold != null) {
            setStackCurrentCallbackThreshold(currentCallbackThreshold);
        }
        if (voltageCallbackPeriod != null) {
            setStackVoltageCallbackPeriod(voltageCallbackPeriod);
        }
        if (voltageCallbackThreshold != null) {
            setStackVoltageCallbackThreshold(voltageCallbackThreshold);
        }
        if (usbVoltageCallbackPeriod != null) {
            setUSBVoltageCallbackPeriod(usbVoltageCallbackPeriod);
        }
        if (usbVoltageCallbackThreshold != null) {
            setUSBVoltageCallbackThreshold(usbVoltageCallbackThreshold);
        }
        if (isStatusLEDEnabled != null) {
            setEnableStatusLED(isStatusLEDEnabled);
        }

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

    public void setDebouncePeriod(Long debouncePeriod) {
        try {
            getDevice().setDebouncePeriod(debouncePeriod);
            this.debouncePeriod = getDevice().getDebouncePeriod();
            super.getCallback().debouncePeriodChanged(this.debouncePeriod);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(MasterDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setStackCurrentCallbackPeriod(Long currentCallbackPeriod) {
        try {
            getDevice().setStackCurrentCallbackPeriod(currentCallbackPeriod);
            this.currentCallbackPeriod = getDevice().getStackCurrentCallbackPeriod();
            super.getCallback().stackCurrentCallbackPeriodChanged(this.currentCallbackPeriod);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(MasterDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setStackCurrentCallbackThreshold(StackCurrentCallbackThreshold threshold) {
        try {
            getDevice().setStackCurrentCallbackThreshold(threshold.getOption(), threshold.getMin(), threshold.getMax());
            this.currentCallbackThreshold = new StackCurrentCallbackThreshold(getDevice().getStackCurrentCallbackThreshold());
            super.getCallback().stackCurrentCallbackThresholdChanged(this.currentCallbackThreshold);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(MasterDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setStackVoltageCallbackPeriod(Long voltageCallbackPeriod) {
        try {
            getDevice().setStackVoltageCallbackPeriod(voltageCallbackPeriod);
            this.voltageCallbackPeriod = getDevice().getStackVoltageCallbackPeriod();
            super.getCallback().stackVoltageCallbackPeriodChanged(this.voltageCallbackPeriod);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(MasterDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setStackVoltageCallbackThreshold(StackVoltageCallbackThreshold threshold) {
        try {
            getDevice().setStackVoltageCallbackThreshold(threshold.getOption(), threshold.getMin(), threshold.getMax());
            this.voltageCallbackThreshold = new StackVoltageCallbackThreshold(getDevice().getStackVoltageCallbackThreshold());
            super.getCallback().stackVoltageCallbackThresholdChanged(this.voltageCallbackThreshold);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(MasterDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void setUSBVoltageCallbackPeriod(Long usbVoltageCallbackPeriod) {
        try {
            getDevice().setUSBVoltageCallbackPeriod(usbVoltageCallbackPeriod);
            this.usbVoltageCallbackPeriod = getDevice().getUSBVoltageCallbackPeriod();
            super.getCallback().usbVoltageCallbackPeriodChanged(this.usbVoltageCallbackPeriod);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(MasterDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setUSBVoltageCallbackThreshold(USBVoltageCallbackThreshold threshold) {
        try {
            getDevice().setUSBVoltageCallbackThreshold(threshold.getOption(), threshold.getMin(), threshold.getMax());
            this.usbVoltageCallbackThreshold = new USBVoltageCallbackThreshold(getDevice().getUSBVoltageCallbackThreshold());
            super.getCallback().USBVoltageCallbackThresholdChanged(this.usbVoltageCallbackThreshold);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(MasterDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
       

    public void setEnableStatusLED(Boolean isEnabled) {
        try {
            if (isEnabled) {
                getDevice().enableStatusLED();
            } else {
                getDevice().disableStatusLED();
            }
            this.isStatusLEDEnabled = getDevice().isStatusLEDEnabled();
            super.getCallback().statusLEDEnabledChanged(this.isStatusLEDEnabled);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(MasterDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void reset() {
        try {
            getDevice().reset();
            super.getCallback().reset();
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(MasterDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
