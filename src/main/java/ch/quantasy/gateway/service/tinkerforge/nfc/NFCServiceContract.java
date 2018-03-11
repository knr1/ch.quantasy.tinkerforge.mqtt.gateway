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
package ch.quantasy.gateway.service.tinkerforge.nfc;

import ch.quantasy.gateway.message.nfc.TagIDEvent;
import ch.quantasy.gateway.message.nfc.TagReadEvent;
import ch.quantasy.gateway.message.nfc.TagWrittenEvent;
import ch.quantasy.gateway.message.nfc.NFCIntent;
import ch.quantasy.gateway.message.nfc.ScanningIntervalStatus;
import ch.quantasy.gateway.service.tinkerforge.DeviceServiceContract;
import ch.quantasy.mqtt.gateway.client.message.Message;
import ch.quantasy.tinkerforge.device.TinkerforgeDeviceClass;
import ch.quantasy.tinkerforge.device.nfc.NFCRFIDDevice;
import java.util.Map;

/**
 *
 * @author reto
 */
public class NFCServiceContract extends DeviceServiceContract {

    public final String SCANNING;
    public final String CALLBACK_PERIOD;
    public final String STATUS_SCANNING_CALLBACK_PERIOD;
    public final String TAG;
    public final String EVENT_TAG_DISCOVERD;
    public final String EVENT_TAG_VANISHED;
    public final String READ;
    public final String EVENT_TAG_READ;
    public final String WRITE;
    public final String EVENT_TAG_WRITTEN;

    public NFCServiceContract(NFCRFIDDevice device) {
        this(device.getUid(), TinkerforgeDeviceClass.getDevice(device.getDevice()).toString());
    }

    public NFCServiceContract(String id) {
        this(id, TinkerforgeDeviceClass.NfcRfid.toString());
    }

    public NFCServiceContract(String id, String device) {
        super(id, device, NFCIntent.class);
        SCANNING = "scanning";
        CALLBACK_PERIOD = "callbackPeriod";
        STATUS_SCANNING_CALLBACK_PERIOD = STATUS + "/" + SCANNING + "/" + CALLBACK_PERIOD;
        TAG = "tag";
        EVENT_TAG_DISCOVERD = EVENT + "/" + TAG + "/discovered";
        EVENT_TAG_VANISHED = EVENT + "/" + TAG + "/vanished";
        READ = "read";
        EVENT_TAG_READ = EVENT + "/" + TAG + "/" + READ;
        WRITE = "write";
        EVENT_TAG_WRITTEN = EVENT + "/" + TAG + "/written";
    }

    @Override
    public void setServiceMessageTopics(Map<String, Class<? extends Message>> messageTopicMap) {
    
        messageTopicMap.put(EVENT_TAG_DISCOVERD, TagIDEvent.class);
        messageTopicMap.put(EVENT_TAG_READ, TagReadEvent.class);
        messageTopicMap.put(EVENT_TAG_VANISHED, TagIDEvent.class);
        messageTopicMap.put(EVENT_TAG_WRITTEN, TagWrittenEvent.class);
        messageTopicMap.put(STATUS_SCANNING_CALLBACK_PERIOD, ScanningIntervalStatus.class);

    }

   
}
