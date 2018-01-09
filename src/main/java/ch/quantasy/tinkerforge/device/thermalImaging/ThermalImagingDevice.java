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
package ch.quantasy.tinkerforge.device.thermalImaging;

import ch.quantasy.gateway.message.thermalImage.TemperatureResolution;
import ch.quantasy.gateway.message.thermalImage.ThermalImageIntent;
import ch.quantasy.gateway.message.thermalImage.ImageTransferConfig;
import ch.quantasy.tinkerforge.device.generic.GenericDevice;
import ch.quantasy.tinkerforge.stack.TinkerforgeStack;
import com.tinkerforge.BrickletThermalImaging;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class ThermalImagingDevice extends GenericDevice<BrickletThermalImaging, ThermalImagIngDeviceCallback, ThermalImageIntent> implements BrickletThermalImaging.HighContrastImageListener, BrickletThermalImaging.TemperatureImageListener {

    public ThermalImagingDevice(TinkerforgeStack stack, BrickletThermalImaging device) throws NotConnectedException, TimeoutException {
        super(stack, device, new ThermalImageIntent());
    }

    @Override
    protected void addDeviceListeners(BrickletThermalImaging device) {
        device.addHighContrastImageListener(super.getCallback());
        device.addTemperatureImageListener(super.getCallback());
        device.addHighContrastImageListener(this);
        device.addTemperatureImageListener(this);
    }

    @Override
    protected void removeDeviceListeners(BrickletThermalImaging device) {
        device.removeHighContrastImageListener(super.getCallback());
        device.removeTemperatureImageListener(super.getCallback());
        device.removeHighContrastImageListener(this);
        device.removeTemperatureImageListener(this);
    }

    @Override
    public void update(ThermalImageIntent intent) {
        if (intent == null) {
            return;
        }
        if (!intent.isValid()) {
            return;
        }
        if (intent.resolution != null) {
            try {
                getDevice().setResolution(intent.resolution.getValue());
                getIntent().resolution = TemperatureResolution.getResolutionFor(getDevice().getResolution());
                super.getCallback().resolutionChanged(getIntent().resolution);
            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(ThermalImagingDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (intent.imageTransferConfig != null) {
            try {
                getDevice().setImageTransferConfig(intent.imageTransferConfig.getValue());
                getIntent().imageTransferConfig = ImageTransferConfig.getTransferConfigFor(getDevice().getImageTransferConfig());
                super.getCallback().imageTransferConfigChanged(getIntent().imageTransferConfig);
            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(ThermalImagingDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void highContrastImage(int[] image) {
        try {
            super.getCallback().statisticsChanged(getDevice().getStatistics());
        } catch (TimeoutException ex) {
            Logger.getLogger(ThermalImagingDevice.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotConnectedException ex) {
            Logger.getLogger(ThermalImagingDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void temperatureImage(int[] image) {
        try {
            super.getCallback().statisticsChanged(getDevice().getStatistics());
        } catch (TimeoutException ex) {
            Logger.getLogger(ThermalImagingDevice.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotConnectedException ex) {
            Logger.getLogger(ThermalImagingDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
