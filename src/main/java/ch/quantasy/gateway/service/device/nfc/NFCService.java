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
package ch.quantasy.gateway.service.device.nfc;

import ch.quantasy.gateway.service.device.AbstractDeviceService;
import ch.quantasy.tinkerforge.device.nfc.NFCRFIDDevice;
import ch.quantasy.tinkerforge.device.nfc.NFCRFIDDeviceCallback;
import ch.quantasy.tinkerforge.device.nfc.NFCTag;
import ch.quantasy.tinkerforge.device.nfc.NFCWrite;
import org.eclipse.paho.client.mqttv3.MqttException;
import java.net.URI;

/**
 *
 * @author reto
 */
public class NFCService extends AbstractDeviceService<NFCRFIDDevice, NFCServiceContract> implements NFCRFIDDeviceCallback {

    public NFCService(NFCRFIDDevice device, URI mqttURI) throws MqttException {
        super(mqttURI, device, new NFCServiceContract(device));
        addDescription(getServiceContract().INTENT_SCANNING_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        addDescription(getServiceContract().INTENT_READ, "[00..FF]_9");

        addDescription(getServiceContract().EVENT_TAG_DISCOVERD, "timestamp: [0.." + Long.MAX_VALUE + "]\n id: [00..FF]_9\n  type: [MifareClassic|Type1|Type2]");
        addDescription(getServiceContract().EVENT_TAG_READ, "timestamp: [0.." + Long.MAX_VALUE + "]\n id: [00..FF]_9\n  value: [00..FF]_*");

        addDescription(getServiceContract().EVENT_TAG_VANISHED, "timestamp: [0.." + Long.MAX_VALUE + "]\n id: [00..FF]_9\n  type: [MifareClassic|Type1|Type2]");
        addDescription(getServiceContract().STATUS_SCANNING_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");

        addDescription(getServiceContract().EVENT_TAG_WRITTEN, "timestamp: [0.." + Long.MAX_VALUE + "]\n id: [00..FF]_9\n  state: [WritePageError|WritePageReady]\n value: [00..FF]_*");
        addDescription(getServiceContract().INTENT_WRITE, "id: [00..FF]_9\n  value: [00..FF]_*");

    }

    @Override
    public void messageArrived(String string, byte[] payload) throws Exception {

        if (string.startsWith(getServiceContract().INTENT_SCANNING_CALLBACK_PERIOD)) {
            Long period = getMapper().readValue(payload, Long.class);
            getDevice().setScanningCallbackPeriod(period);
        }
        if (string.startsWith(getServiceContract().INTENT_READ)) {
            String id = getMapper().readValue(payload, String.class);
            getDevice().setActiveTagIDToRead(id);
        }
        if (string.startsWith(getServiceContract().INTENT_WRITE)) {
            NFCWrite write = getMapper().readValue(payload, NFCWrite.class);
            getDevice().setActiveTagToWrite(write);
        }

    }

    @Override
    public void scanningCallbackPeriodChanged(long period) {
        addStatus(getServiceContract().STATUS_SCANNING_CALLBACK_PERIOD, period);
    }

    @Override
    public void tagDiscovered(NFCTag tagID) {
        addEvent(getServiceContract().EVENT_TAG_DISCOVERD, new TagIDEvent(tagID));
    }

    @Override
    public void tagVanished(NFCTag tagID) {
        addEvent(getServiceContract().EVENT_TAG_VANISHED, new TagIDEvent(tagID));
    }

    @Override
    public void tagRead(NFCTag tag) {
        addEvent(getServiceContract().EVENT_TAG_READ, new TagReadEvent(tag));
    }

    @Override
    public void tagWritten(NFCTag tag) {
        addEvent(getServiceContract().EVENT_TAG_WRITTEN, new TagWrittenEvent(tag));
    }

    static class TagWrittenEvent {

        private long timestamp;
        private String id;
        private NFCTag.NFCRFIDReaderState state;
        private Short[] value;

        public TagWrittenEvent(NFCTag tag) {
            this(tag.getLatestDiscoveryTimeStamp(), tag.getTidAsHexString(), tag.getLatestReaderState(), tag.getWriteContent());
        }

        public TagWrittenEvent() {
        }

        public TagWrittenEvent(long timestamp, String id, NFCTag.NFCRFIDReaderState readerState, Short[] value) {
            this.timestamp = timestamp;
            this.id = id;
            this.state = readerState;
            this.value = value;
        }

        public String getId() {
            return id;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public Short[] getValue() {
            return value;
        }

        public NFCTag.NFCRFIDReaderState getState() {
            return state;
        }

    }

    static class TagReadEvent {

        private long timestamp;
        private String id;
        private Short[] value;

        public TagReadEvent(NFCTag tag) {
            this(tag.getLatestDiscoveryTimeStamp(), tag.getTidAsHexString(), tag.getReadContent());
        }

        public TagReadEvent() {
        }

        public TagReadEvent(long timestamp, String id, Short[] value) {
            this.timestamp = timestamp;
            this.id = id;
            this.value = value;
        }

        public String getId() {
            return id;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public Short[] getValue() {
            return value;
        }

    }

    static class TagIDEvent {

        private long timestamp;
        private String id;
        private NFCTag.NFCType type;

        public TagIDEvent() {
        }

        public TagIDEvent(NFCTag tagID) {
            this(tagID.getLatestDiscoveryTimeStamp(), tagID.getTidAsHexString(), tagID.getType());
        }

        public TagIDEvent(long timestamp, String id, NFCTag.NFCType type) {
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

        public NFCTag.NFCType getType() {
            return type;
        }

    }

}
