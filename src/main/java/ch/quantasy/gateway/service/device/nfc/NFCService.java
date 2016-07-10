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
package ch.quantasy.gateway.service.device.nfc;

import ch.quantasy.gateway.service.device.AbstractDeviceService;
import ch.quantasy.tinkerforge.device.nfc.NFCRFIDDevice;
import ch.quantasy.tinkerforge.device.nfc.NFCRFIDDeviceCallback;
import ch.quantasy.tinkerforge.device.nfc.NFCTagID;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import java.net.URI;

/**
 *
 * @author reto
 */
public class NFCService extends AbstractDeviceService<NFCRFIDDevice, NFCServiceContract> implements NFCRFIDDeviceCallback {

    public NFCService(NFCRFIDDevice device, URI mqttURI) throws MqttException {
        super(mqttURI, device, new NFCServiceContract(device));
        addDescription(getServiceContract().INTENT_SCANNING_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");

        addDescription(getServiceContract().EVENT_TAG_DISCOVERD, "timestamp: [0.." + Long.MAX_VALUE + "]\n id: [00..FF]_9\n  type: [MifareClassic|Type1|Type2]\n");
        addDescription(getServiceContract().EVENT_TAG_VANISHED, "timestamp: [0.." + Long.MAX_VALUE + "]\n id: [00..FF]_9\n  type: [MifareClassic|Type1|Type2]\n");
        addDescription(getServiceContract().STATUS_SCANNING_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");

    }

    @Override
    public void messageArrived(String string, MqttMessage mm) {
        byte[] payload = mm.getPayload();
        if (payload == null) {
            return;

        }
        try {
            if (string.startsWith(getServiceContract().INTENT_SCANNING_CALLBACK_PERIOD)) {
                Long period = getMapper().readValue(payload, Long.class);
                getDevice().setScanningCallbackPeriod(period);
            }
        } catch (Exception ex) {
            Logger.getLogger(NFCService.class
                    .getName()).log(Level.SEVERE, null, ex);
            return;
        }

    }

    @Override
    public void scanningCallbackPeriodChanged(long period) {
        addStatus(getServiceContract().STATUS_SCANNING_CALLBACK_PERIOD, period);
    }

    @Override
    public void tagDiscovered(NFCTagID tagID) {
        addEvent(getServiceContract().EVENT_TAG_DISCOVERD,new TagIDEvent(tagID));
    }

    @Override
    public void tagVanished(NFCTagID tagID) {
        addEvent(getServiceContract().EVENT_TAG_VANISHED,new TagIDEvent(tagID));
    }

    static class TagIDEvent{
        private long timestamp;
        private String id;
        private NFCTagID.NFCType type;

        public TagIDEvent() {
        }

        
        public TagIDEvent(NFCTagID tagID) {
            this(tagID.getLatestDiscoveryTimeStamp(),tagID.getTidAsHexString(),tagID.getType());
        }

        public TagIDEvent(long timestamp, String id, NFCTagID.NFCType type) {
            this.timestamp = timestamp;
            this.id = id;
            this.type = type;
        }

        public String getId() {
            return id;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public NFCTagID.NFCType getType() {
            return type;
        }     
        
    }

}
