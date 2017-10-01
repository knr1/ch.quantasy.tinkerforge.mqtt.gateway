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

import ch.quantasy.gateway.intent.nfc.NFCIntent;
import ch.quantasy.gateway.service.device.AbstractDeviceService;
import ch.quantasy.tinkerforge.device.nfc.NFCRFIDDevice;
import ch.quantasy.tinkerforge.device.nfc.NFCRFIDDeviceCallback;
import ch.quantasy.tinkerforge.device.nfc.NFCTag;
import ch.quantasy.gateway.intent.nfc.NFCWrite;
import org.eclipse.paho.client.mqttv3.MqttException;
import java.net.URI;

/**
 *
 * @author reto
 */
public class NFCService extends AbstractDeviceService<NFCRFIDDevice, NFCServiceContract> implements NFCRFIDDeviceCallback {

    public NFCService(NFCRFIDDevice device, URI mqttURI) throws MqttException {
        super(mqttURI, device, new NFCServiceContract(device));
    }

    
    @Override
    public void scanningCallbackPeriodChanged(long period) {
        publishStatus(getContract().STATUS_SCANNING_CALLBACK_PERIOD, period);
    }

    @Override
    public void tagDiscovered(NFCTag nfcTag) {
        publishEvent(getContract().EVENT_TAG_DISCOVERD, new TagID(nfcTag), nfcTag.getLatestDiscoveryTimeStamp());
    }

    @Override
    public void tagVanished(NFCTag nfcTag) {
        publishEvent(getContract().EVENT_TAG_VANISHED, new TagID(nfcTag), nfcTag.getLatestDiscoveryTimeStamp());
    }

    @Override
    public void tagRead(NFCTag tag) {
        publishEvent(getContract().EVENT_TAG_READ, new TagRead(tag), tag.getLatestDiscoveryTimeStamp());
    }

    @Override
    public void tagWritten(NFCTag tag) {
        publishEvent(getContract().EVENT_TAG_WRITTEN, new TagWritten(tag));
    }

    static class TagWritten {

        private String id;
        private NFCTag.NFCRFIDReaderState state;
        private Short[] value;

        public TagWritten(NFCTag tag) {
            this(tag.getTidAsHexString(), tag.getLatestReaderState(), tag.getWriteContent());
        }

        private TagWritten() {
        }

        public TagWritten(String id, NFCTag.NFCRFIDReaderState readerState, Short[] value) {
            this.id = id;
            this.state = readerState;
            this.value = value;
        }

        public String getId() {
            return id;
        }

        public Short[] getValue() {
            return value;
        }

        public NFCTag.NFCRFIDReaderState getState() {
            return state;
        }

    }

    static class TagRead {

        private String id;
        private Short[] value;

        public TagRead(NFCTag tag) {
            this(tag.getTidAsHexString(), tag.getReadContent());
        }

        private TagRead() {
        }

        public TagRead(String id, Short[] value) {
            this.id = id;
            this.value = value;
        }

        public String getId() {
            return id;
        }

        public Short[] getValue() {
            return value;
        }

    }

    static class TagID {

        private String id;
        private NFCTag.NFCType type;

        private TagID() {
        }

        public TagID(NFCTag tag) {
            this(tag.getTidAsHexString(), tag.getType());
        }

        public TagID(String id, NFCTag.NFCType type) {
            this.id = id;
            this.type = type;
        }

        public String getId() {
            return id;
        }

        public NFCTag.NFCType getType() {
            return type;
        }

    }

}
