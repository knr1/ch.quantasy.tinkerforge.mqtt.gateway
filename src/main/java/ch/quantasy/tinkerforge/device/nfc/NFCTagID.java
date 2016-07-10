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
package ch.quantasy.tinkerforge.device.nfc;

import com.tinkerforge.BrickletNFCRFID;

/**
 *
 * @author reto
 */
public class NFCTagID {

    public static enum NFCType {
        MifareClassic(BrickletNFCRFID.TAG_TYPE_MIFARE_CLASSIC), Type1(BrickletNFCRFID.TAG_TYPE_TYPE1), Type2(BrickletNFCRFID.TAG_TYPE_TYPE2);
        private short type;

        private NFCType(short type) {
            this.type = type;
        }

        public short getType() {
            return type;
        }

        public static NFCType getNFCTypeFor(short s) throws IllegalArgumentException {
            for (NFCType type : values()) {
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

    private NFCTagID() {
    }

    public NFCTagID(BrickletNFCRFID.TagID tagID) {
        this(NFCType.getNFCTypeFor(tagID.tagType), tagID.tid, tagID.tidLength);
    }

    public NFCTagID(NFCType type, String tidAsHexString) {
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

    public NFCTagID(NFCType type, short[] tid, int tidLength) {
        this.type = type;
        this.tid = tid;
        this.tidLength = tidLength;
        this.tidAsHexString = "";
        for (short id : tid) {
            if (id < 10) {
                this.tidAsHexString += "0";
            }
            this.tidAsHexString += Integer.toHexString(id);
        }
        this.latestDiscoveryTimeStamp = System.currentTimeMillis();
    }

    public NFCTagID(String type, short[] tid, int tidLength) {
        this(NFCType.valueOf(type), tid, tidLength);
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

}
