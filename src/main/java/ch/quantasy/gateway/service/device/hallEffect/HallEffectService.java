/*
 *   "TiMqWay"
 *
 *    TiMqWay(tm): A gateway to provide an MQTT-View for the Tinkerforge(tm) world (Tinkerforge-MQTT-Gateway).
 *
 *    Copyright (c) 2015 Bern University of Applied Sciences (BFH),
 *    Research Institute for Security in the Information Society (RISIS), Wireless Communications & Secure Internet of Things (WiCom & SIoT),
 *    Quellgasse 21, CH-2501 Biel, Switzerland
 *
 *    Licensed under Dual License consisting of:
 *    1. GNU Affero General Public License (AGPL) v3
 *    and
 *    2. Commercial license
 *
 *
 *    1. This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Affero General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Affero General Public License for more details.
 *
 *     You should have received a copy of the GNU Affero General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 *    2. Licensees holding valid commercial licenses for TiMqWay may use this file in
 *     accordance with the commercial license agreement provided with the
 *     Software or, alternatively, in accordance with the terms contained in
 *     a written agreement between you and Bern University of Applied Sciences (BFH),
 *     Research Institute for Security in the Information Society (RISIS), Wireless Communications & Secure Internet of Things (WiCom & SIoT),
 *     Quellgasse 21, CH-2501 Biel, Switzerland.
 *
 *
 *     For further information contact <e-mail: reto.koenig@bfh.ch>
 *
 *
 */
package ch.quantasy.gateway.service.device.hallEffect;

import ch.quantasy.gateway.service.device.AbstractDeviceService;
import ch.quantasy.tinkerforge.device.hallEffect.DeviceConfiguration;
import ch.quantasy.tinkerforge.device.hallEffect.HallEffectDevice;
import ch.quantasy.tinkerforge.device.hallEffect.HallEffectDeviceCallback;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import java.net.URI;

/**
 *
 * @author reto
 */
public class HallEffectService extends AbstractDeviceService<HallEffectDevice, HallEffectServiceContract> implements HallEffectDeviceCallback {

    public HallEffectService(HallEffectDevice device,URI mqttURI) throws MqttException {

        super(mqttURI, device, new HallEffectServiceContract(device));
        addDescription(getServiceContract().INTENT_EDGE_COUNT_INTERRUPT, "[0.." + Long.MAX_VALUE + "]");
        addDescription(getServiceContract().INTENT_EDGE_COUNT_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        addDescription(getServiceContract().INTENT_EDGE_COUNT_RESET, "[true|false]");
        addDescription(getServiceContract().INTENT_CONFIGURATION, "edgeType: [RISING|FALLING|BOTH]\n debounce: [0..100]\n");

        addDescription(getServiceContract().EVENT_EDGE_COUNT, "timestamp: [0.." + Long.MAX_VALUE + "]\n count: [0.." + Long.MAX_VALUE + "]\n greater35Gauss: [true|false]");
        addDescription(getServiceContract().EVENT_EDGE_COUNT_RESET, "timestamp: [0.." + Long.MAX_VALUE + "]\n count: [0.." + Long.MAX_VALUE + "]\n");
        
        addDescription(getServiceContract().STATUS_EDGE_COUNT_INTERRUPT, "[0.." + Long.MAX_VALUE + "]");
        addDescription(getServiceContract().STATUS_EDGE_COUNT_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        addDescription(getServiceContract().STATUS_CONFIGURATION, "edgeType: [RISING|FALLING|BOTH]\n debounce: [0..100]\n");
    }

    @Override
       public void messageArrived(String string, byte[] payload) throws Exception {

            if (string.startsWith(getServiceContract().INTENT_EDGE_COUNT_INTERRUPT)) {

                Long edges = getMapper().readValue(payload, Long.class);
                getDevice().setEdgeInterrupt(edges);
            }
            if (string.startsWith(getServiceContract().INTENT_EDGE_COUNT_CALLBACK_PERIOD)) {

                Long period = getMapper().readValue(payload, Long.class);
                getDevice().setEdgeCountCallbackPeriod(period);
            }
            
            if (string.startsWith(getServiceContract().INTENT_EDGE_COUNT_RESET)) {

                Boolean reset = getMapper().readValue(payload, Boolean.class);
                getDevice().setEdgeCountReset(reset);
            }    

            if (string.startsWith(getServiceContract().INTENT_CONFIGURATION)) {

                DeviceConfiguration configuration = getMapper().readValue(payload, DeviceConfiguration.class);
                getDevice().setEdgeCountConfig(configuration);
            }
    }

    @Override
    public void edgeInterruptChanged(long period) {
        addStatus(getServiceContract().STATUS_EDGE_COUNT_INTERRUPT, period);
    }

    @Override
    public void edgeCountCallbackPeriodChanged(long period) {
        addStatus(getServiceContract().STATUS_EDGE_COUNT_CALLBACK_PERIOD, period);
    }

    @Override
    public void edgeCountConfigChanged(DeviceConfiguration configuration) {
        addStatus(getServiceContract().STATUS_CONFIGURATION, configuration);
    }

    @Override
    public void edgeCountReset(long latestEdgeCount) {
        addEvent(getServiceContract().EVENT_EDGE_COUNT_RESET, new EdgeCountResetEvent(latestEdgeCount));
    }


    @Override
    public void edgeCount(long l, boolean bln) {
        addEvent(getServiceContract().EVENT_EDGE_COUNT, new EdgeCountEvent(l,bln));
    }

    
    public static class EdgeCountEvent {

        protected long timestamp;
        protected long count;
        protected boolean greater35Gauss;

        public EdgeCountEvent(long count,boolean greater35Gauss) {
            this(count,greater35Gauss, System.currentTimeMillis());
        }

        public EdgeCountEvent(long value, boolean greater35Gauss, long timeStamp) {
            this.count = value;
            this.greater35Gauss=greater35Gauss;
            this.timestamp = timeStamp;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public long getCount() {
            return count;
        }

        public boolean getGreater35Gauss() {
            return greater35Gauss;
        }

    }

    public static class EdgeCountResetEvent {

        protected long timestamp;
        protected long count;

        public EdgeCountResetEvent(long value) {
            this(value, System.currentTimeMillis());
        }

        public EdgeCountResetEvent(long value, long timeStamp) {
            this.count = value;
            this.timestamp = timeStamp;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public long getCount() {
            return count;
        }
    }

}
