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
package ch.quantasy.gateway.service.device.hallEffect;

import ch.quantasy.gateway.service.device.AbstractDeviceService;
import ch.quantasy.gateway.intent.hallEffect.DeviceConfiguration;
import ch.quantasy.gateway.intent.hallEffect.HallEffectIntent;
import ch.quantasy.tinkerforge.device.hallEffect.HallEffectDevice;
import ch.quantasy.tinkerforge.device.hallEffect.HallEffectDeviceCallback;
import org.eclipse.paho.client.mqttv3.MqttException;
import java.net.URI;

/**
 *
 * @author reto
 */
public class HallEffectService extends AbstractDeviceService<HallEffectDevice, HallEffectServiceContract> implements HallEffectDeviceCallback {

    public HallEffectService(HallEffectDevice device, URI mqttURI) throws MqttException {
        super(mqttURI, device, new HallEffectServiceContract(device));
    }

   

    @Override
    public void edgeInterruptChanged(long period) {
        publishStatus(getContract().STATUS_EDGE_COUNT_INTERRUPT, period);
    }

    @Override
    public void edgeCountCallbackPeriodChanged(long period) {
        publishStatus(getContract().STATUS_EDGE_COUNT_CALLBACK_PERIOD, period);
    }

    @Override
    public void edgeCountConfigChanged(DeviceConfiguration configuration) {
        publishStatus(getContract().STATUS_CONFIGURATION, configuration);
    }

    @Override
    public void edgeCountReset(long latestEdgeCount) {
        publishEvent(getContract().EVENT_EDGE_COUNT_RESET, latestEdgeCount);
    }

    @Override
    public void edgeCount(long l, boolean bln) {
        publishEvent(getContract().EVENT_EDGE_COUNT, new EdgeCount(l, bln));
    }

    public static class EdgeCount {

        private long count;
        private boolean greater35Gauss;

        private EdgeCount() {
        }

        public EdgeCount(long value, boolean greater35Gauss) {
            this.count = value;
            this.greater35Gauss = greater35Gauss;
        }

        public long getCount() {
            return count;
        }

        public boolean getGreater35Gauss() {
            return greater35Gauss;
        }

    }

}
