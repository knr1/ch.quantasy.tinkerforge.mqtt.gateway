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
package ch.quantasy.tinkerforge.device.nfc;

import ch.quantasy.gateway.binding.tinkerforge.nfc.NFCType;
import com.tinkerforge.BrickletNFCRFID;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author reto
 */
public class NFCTag {

    public static enum NFCRFIDReaderState {
        WritePageError(BrickletNFCRFID.STATE_WRITE_PAGE_ERROR), WritePageReady(BrickletNFCRFID.STATE_WRITE_PAGE_READY);
        private short type;

        private NFCRFIDReaderState(short type) {
            this.type = type;
        }

        public short getType() {
            return type;
        }

        public static NFCRFIDReaderState getNFCTReaderStateTypeFor(short s) throws IllegalArgumentException {
            for (NFCRFIDReaderState type : values()) {
                if (type.type == s) {
                    return type;
                }
            }
            throw new IllegalArgumentException("Not supported: " + s);
        }

    }

    
    private long latestDiscoveryTimeStamp;
    private NFCType type;
    private transient short[] tid;
    private transient int tidLength;
    private String tidAsHexString;

    private Short[] readContent;
    private Short[] writeContent;

    NFCRFIDReaderState latestReaderState;

    private NFCTag() {
    }

    public NFCTag(BrickletNFCRFID.TagID tagID) {
        this(NFCType.getNFCTypeFor(tagID.tagType), tagID.tid, tagID.tidLength);
    }

    public NFCTag(NFCType type, String tidAsHexString) {
        this.type = type;
        this.tidAsHexString = tidAsHexString;
        this.tidLength = tidAsHexString.length();
        this.tid = new short[tidLength];
        this.tid = hexStringToShortArray(tidAsHexString);
        this.latestDiscoveryTimeStamp = System.currentTimeMillis();
    }

    //actually it was hexStringToByteArray
    public static short[] hexStringToShortArray(String s) {
        int len = s.length();
        short[] data = new short[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (short) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }

    public NFCTag(NFCType type, short[] tid, int tidLength) {
        this.type = type;
        this.tid = tid;
        this.tidLength = tidLength;
        this.tidAsHexString = getTidAsHexString(tid);
        latestDiscoveryTimeStamp = System.currentTimeMillis();

    }

    public static String getTidAsHexString(short[] tid) {
        String tidAsHexString = "";
        for (short id : tid) {
            if (id < 10) {
                tidAsHexString += "0";
            }
            tidAsHexString += Integer.toHexString(id);
        }
        return tidAsHexString;
    }

    public NFCTag(String type, short[] tid, int tidLength) {
        this(NFCType.valueOf(type), tid, tidLength);
    }

    public void setReadContent(Short[] content) {
        this.readContent = content.clone();
    }

    public void setWriteContent(Short[] writeContent) {
        this.writeContent = writeContent.clone();
    }

    public Short[] getReadContent() {
        return readContent.clone();
    }

    public Short[] getWriteContent() {
        return writeContent.clone();
    }

    public Map<Integer, short[]> getPagesToWrite() {
        Map<Integer, short[]> pages = new HashMap<>();
        Short[] readContent = this.readContent.clone();
        Short[] writeContent = this.writeContent.clone();
        if (readContent == null || writeContent == null || writeContent.length != readContent.length) {
            return pages;
        }
        for (int i = 0; i < readContent.length; i += 16) {
            short[] content = new short[16];
            boolean newContent = false;
            for (int j = 0; j < content.length; j++) {
                content[j] = writeContent[i + j];
                if (content[j] != readContent[i + j]) {
                    newContent = true;
                }
            }
            if (newContent) {
                if (this.type == NFCType.Type1) {
                    pages.put(i / 2, content);

                }
                if (this.type == NFCType.Type2) {
                    pages.put(i / 4, content);
                }
            }
        }
        return pages;
    }

    public void updateDiscoveryTimeStamp() {
        this.latestDiscoveryTimeStamp = System.currentTimeMillis();
    }

    public long getLatestDiscoveryTimeStamp() {
        return latestDiscoveryTimeStamp;
    }

    public short[] getTid() {
        return tid;
    }

    public String getTidAsHexString() {
        return tidAsHexString;
    }

    public int getTidLength() {
        return tidLength;
    }

    public NFCType getType() {
        return type;
    }

    public void setLatestReaderState(NFCRFIDReaderState latestReaderState) {
        this.latestReaderState = latestReaderState;
    }

    public NFCRFIDReaderState getLatestReaderState() {
        return latestReaderState;
    }

}
