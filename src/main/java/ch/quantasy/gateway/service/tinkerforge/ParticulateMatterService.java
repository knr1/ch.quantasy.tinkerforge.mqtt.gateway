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
package ch.quantasy.gateway.service.tinkerforge;

import ch.quantasy.gateway.binding.tinkerforge.moisture.MoistureServiceContract;
import ch.quantasy.gateway.binding.tinkerforge.moisture.MoistureEvent;
import org.eclipse.paho.client.mqttv3.MqttException;
import ch.quantasy.tinkerforge.device.moisture.MoistureDevice;
import ch.quantasy.gateway.binding.tinkerforge.moisture.DeviceMoistureCallbackThreshold;
import ch.quantasy.gateway.binding.tinkerforge.moisture.DebouncePeriodStatus;
import ch.quantasy.gateway.binding.tinkerforge.moisture.MoistureCallbackPeriodStatus;
import ch.quantasy.gateway.binding.tinkerforge.moisture.MoistureCallbackThresholdStatus;
import ch.quantasy.gateway.binding.tinkerforge.moisture.MovingAverageStatus;
import ch.quantasy.gateway.binding.tinkerforge.particulateMatter.ConcentrationCallbackConfiguration;
import ch.quantasy.gateway.binding.tinkerforge.particulateMatter.ConcentrationCallbackConfigurationStatus;
import ch.quantasy.gateway.binding.tinkerforge.particulateMatter.CountCallbackConfiguration;
import ch.quantasy.gateway.binding.tinkerforge.particulateMatter.CountCallbackConfigurationStatus;
import ch.quantasy.gateway.binding.tinkerforge.particulateMatter.EnableStatus;
import ch.quantasy.gateway.binding.tinkerforge.particulateMatter.ConcentrationEvent;
import ch.quantasy.gateway.binding.tinkerforge.particulateMatter.CountEvent;
import ch.quantasy.gateway.binding.tinkerforge.particulateMatter.ParticulateMatterServiceContract;
import ch.quantasy.tinkerforge.device.particulateMatter.ParticulateMatterDevice;
import ch.quantasy.tinkerforge.device.particulateMatter.ParticulateMatterDeviceCallback;
import java.net.URI;

/**
 *
 * @author reto
 */
public class ParticulateMatterService extends AbstractDeviceService<ParticulateMatterDevice, ParticulateMatterServiceContract> implements ParticulateMatterDeviceCallback {

    public ParticulateMatterService(ParticulateMatterDevice device, URI mqttURI) throws MqttException {
        super(mqttURI, device, new ParticulateMatterServiceContract(device));

    }

    @Override
    public void enableChanged(Boolean enable) {
        readyToPublishStatus(getContract().STATUS_ENABLED, new EnableStatus(enable));
    }

    @Override
    public void concentrationCallbackConfigurationChanged(ConcentrationCallbackConfiguration concentrationCallbackConfiguration) {
        readyToPublishStatus(getContract().STATUS_CONCENTRATON, new ConcentrationCallbackConfigurationStatus(concentrationCallbackConfiguration));
    }

    @Override
    public void CountCallbackConfigurationChanged(CountCallbackConfiguration countCallbackConfiguration) {
        readyToPublishStatus(getContract().STATUS_COUNT, new CountCallbackConfigurationStatus(countCallbackConfiguration));
    }

    @Override
    public void pmConcentration(int arg0, int arg1, int arg2) {
        readyToPublishEvent(getContract().EVENT_CONCENTRATION, new ConcentrationEvent(arg0, arg1, arg2));    }

    @Override
    public void pmCount(int arg0, int arg1, int arg2, int arg3, int arg4, int arg5) {
        readyToPublishEvent(getContract().EVENT_COUNT,new CountEvent(arg0, arg1, arg2, arg3, arg4, arg5));    }
}
