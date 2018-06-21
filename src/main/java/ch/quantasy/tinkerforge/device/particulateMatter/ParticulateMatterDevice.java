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
package ch.quantasy.tinkerforge.device.particulateMatter;

import ch.quantasy.tinkerforge.device.moisture.*;
import ch.quantasy.gateway.binding.tinkerforge.moisture.DeviceMoistureCallbackThreshold;
import ch.quantasy.gateway.binding.tinkerforge.moisture.MoistureIntent;
import ch.quantasy.gateway.binding.tinkerforge.particulateMatter.ConcentrationCallbackConfiguration;
import ch.quantasy.gateway.binding.tinkerforge.particulateMatter.CountCallbackConfiguration;
import ch.quantasy.gateway.binding.tinkerforge.particulateMatter.ParticulateMatterIntent;
import ch.quantasy.tinkerforge.device.generic.GenericDevice;
import ch.quantasy.tinkerforge.stack.TinkerforgeStack;
import com.tinkerforge.BrickletMoisture;
import com.tinkerforge.BrickletParticulateMatter;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class ParticulateMatterDevice extends GenericDevice<BrickletParticulateMatter, ParticulateMatterDeviceCallback, ParticulateMatterIntent> {

    public ParticulateMatterDevice(TinkerforgeStack stack, BrickletParticulateMatter device) throws NotConnectedException, TimeoutException {
        super(stack, device, new ParticulateMatterIntent());
    }

    @Override
    protected void addDeviceListeners(BrickletParticulateMatter device) {
        device.addPMConcentrationListener(super.getCallback());
        device.addPMCountListener(super.getCallback());
    }

    @Override
    protected void removeDeviceListeners(BrickletParticulateMatter device) {
        device.removePMConcentrationListener(super.getCallback());
        device.removePMCountListener(super.getCallback());
    }

    @Override
    public void update(ParticulateMatterIntent intent) {
        if (intent == null) {
            return;
        }
        if (!intent.isValid()) {
            return;
        }
        if (intent.enable != null) {
            try {
                getDevice().setEnable(intent.enable);
                getIntent().enable = getDevice().getEnable();
                super.getCallback().enableChanged(getIntent().enable);
            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(ParticulateMatterDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (intent.concentrationCallbackConfiguration != null) {
            try {
                getDevice().setPMConcentrationCallbackConfiguration(intent.concentrationCallbackConfiguration.period, intent.concentrationCallbackConfiguration.valueHasToChange);
                intent.concentrationCallbackConfiguration = new ConcentrationCallbackConfiguration(getDevice().getPMConcentrationCallbackConfiguration());
                super.getCallback().concentrationCallbackConfigurationChanged(intent.concentrationCallbackConfiguration);
            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(ParticulateMatterDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (intent.countCallbackConfiguration != null) {
            try {
                getDevice().setPMCountCallbackConfiguration(intent.countCallbackConfiguration.period, intent.countCallbackConfiguration.valueHasToChange);
                getIntent().countCallbackConfiguration = new CountCallbackConfiguration(getDevice().getPMCountCallbackConfiguration());
                super.getCallback().CountCallbackConfigurationChanged(getIntent().countCallbackConfiguration);
            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(ParticulateMatterDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
