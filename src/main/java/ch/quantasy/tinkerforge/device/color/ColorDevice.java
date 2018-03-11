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
package ch.quantasy.tinkerforge.device.color;

import ch.quantasy.gateway.message.color.ColorIntent;
import ch.quantasy.gateway.message.color.DeviceColorCallbackThreshold;
import ch.quantasy.gateway.message.color.DeviceConfiguration;
import ch.quantasy.tinkerforge.device.generic.GenericDevice;
import ch.quantasy.tinkerforge.stack.TinkerforgeStack;
import com.tinkerforge.BrickletColor;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class ColorDevice extends GenericDevice<BrickletColor, ColorDeviceCallback, ColorIntent> {

    public ColorDevice(TinkerforgeStack stack, BrickletColor device) throws NotConnectedException, TimeoutException {
        super(stack, device, new ColorIntent());
    }

    @Override
    protected void addDeviceListeners(BrickletColor device) {
        device.addColorListener(super.getCallback());
        device.addColorReachedListener(super.getCallback());
        device.addColorTemperatureListener(super.getCallback());
        device.addIlluminanceListener(super.getCallback());
    }

    @Override
    protected void removeDeviceListeners(BrickletColor device) {
        device.removeColorListener(super.getCallback());
        device.removeColorReachedListener(super.getCallback());
        device.addColorTemperatureListener(super.getCallback());
        device.addIlluminanceListener(super.getCallback());
    }

    @Override
    public void update(ColorIntent intent) {
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
                Logger.getLogger(ColorDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (intent.colorCallbackPeriod != null) {
            try {
                getDevice().setColorCallbackPeriod(intent.colorCallbackPeriod);
                getIntent().colorCallbackPeriod = getDevice().getColorCallbackPeriod();
                super.getCallback().colorCallbackPeriodChanged(getIntent().colorCallbackPeriod);
            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(ColorDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (intent.illuminanceCallbackPeriod != null) {
            try {
                getDevice().setIlluminanceCallbackPeriod(intent.illuminanceCallbackPeriod);
                getIntent().illuminanceCallbackPeriod = getDevice().getIlluminanceCallbackPeriod();
                super.getCallback().illuminanceCallbackPeriodChanged(getIntent().illuminanceCallbackPeriod);
            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(ColorDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (intent.colorCallbackThreshold != null) {
            try {
                getDevice().setColorCallbackThreshold(intent.colorCallbackThreshold.option, intent.colorCallbackThreshold.minR, intent.colorCallbackThreshold.maxR, intent.colorCallbackThreshold.minG, intent.colorCallbackThreshold.maxG, intent.colorCallbackThreshold.minB, intent.colorCallbackThreshold.maxB, intent.colorCallbackThreshold.minC, intent.colorCallbackThreshold.maxC);
                getIntent().colorCallbackThreshold = new DeviceColorCallbackThreshold(getDevice().getColorCallbackThreshold());
                super.getCallback().colorCallbackThresholdChanged(getIntent().colorCallbackThreshold);
            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(ColorDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (intent.colorTemperatureCallbackPeriod != null) {
            try {
                getDevice().setColorTemperatureCallbackPeriod(intent.colorTemperatureCallbackPeriod);
                getIntent().colorTemperatureCallbackPeriod = getDevice().getColorTemperatureCallbackPeriod();
                super.getCallback().colorTemperatureCallbackPeriodChanged(getIntent().colorTemperatureCallbackPeriod);

            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(ColorDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (intent.config != null) {
            try {
                getDevice().setConfig(intent.config.getGain().getValue(), intent.config.getIntegrationTime().getValue());
                getIntent().config = new DeviceConfiguration(getDevice().getConfig());
                super.getCallback().configurationChanged(getIntent().config);
            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(ColorDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (intent.light != null) {
            try {
                if (intent.light) {
                    getDevice().lightOn();
                } else {
                    getDevice().lightOff();
                }
                getIntent().light = getDevice().isLightOn() != 0;
                super.getCallback().lightStatusChanged(getIntent().light);
            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(ColorDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
