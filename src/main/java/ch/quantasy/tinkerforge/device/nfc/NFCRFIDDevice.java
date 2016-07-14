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

import ch.quantasy.tinkerforge.device.generic.GenericDevice;
import ch.quantasy.tinkerforge.device.nfc.NFCTag.NFCType;
import ch.quantasy.tinkerforge.stack.TinkerforgeStackAddress;
import com.tinkerforge.BrickletNFCRFID;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class NFCRFIDDevice extends GenericDevice<BrickletNFCRFID, NFCRFIDDeviceCallback> {

    private NFCReader nfcReader;
    private Thread nfcThread;

    private Long period;

    public NFCRFIDDevice(TinkerforgeStackAddress address, BrickletNFCRFID device) throws NotConnectedException, TimeoutException {
        super(address, device);
        nfcReader = new NFCReader();
    }

    @Override
    protected void addDeviceListeners() {
        getDevice().addStateChangedListener(nfcReader);
        nfcThread = new Thread(nfcReader);
        nfcThread.start();
        if (period != null) {
            setScanningCallbackPeriod(period);
        }
    }

    @Override
    protected void removeDeviceListeners() {
        getDevice().removeStateChangedListener(nfcReader);
        nfcThread.interrupt();
        nfcThread = null;
    }

    public void setScanningCallbackPeriod(Long period) {
        nfcReader.setScanningInterval(period);
        this.period = nfcReader.getScanningInterval();
        getCallback().scanningCallbackPeriodChanged(this.period);
    }

    public void setActiveTagIDToRead(String tagID) {
        nfcReader.addActiveTagToRead(tagID);
    }

    public void setActiveTagToWrite(NFCWrite write) {
        nfcReader.addActiveTagToWrite(write);
    }

    class NFCReader implements Runnable, BrickletNFCRFID.StateChangedListener {

        private Map<String, NFCTag> tagMap;

        private NFCRFIDReaderState nfcRFIDReaderState;

        private long interval;

        private Set<String> activeTagsToRead;

        private Map<String, NFCWrite> activeTagsToWrite;

        public NFCReader() {
            tagMap = new HashMap<>();
            activeTagsToRead = new HashSet<>();
            activeTagsToWrite = new HashMap<>();
        }

        public synchronized void addActiveTagToRead(String tagID) {
            this.activeTagsToRead.add(tagID);
            this.notifyAll();
        }

        public synchronized void addActiveTagToWrite(NFCWrite write) {
            this.activeTagsToWrite.put(write.getId(), write);
            this.notifyAll();
        }

        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                synchronized (this) {
                    try {
                        for (NFCType type : NFCType.values()) {
                            while (interval < 0) {
                                this.wait();
                            }
                            this.wait(interval);
                            for (int i = 0; i < 3; i++) {
                                getDevice().requestTagID(type.getType());
                                while (nfcRFIDReaderState == null || !nfcRFIDReaderState.getIsIdle() || nfcRFIDReaderState.getState() == BrickletNFCRFID.STATE_REQUEST_TAG_ID) {
                                    this.wait(1000);
                                }
                                if (nfcRFIDReaderState.getState() == BrickletNFCRFID.STATE_REQUEST_TAG_ID_READY) {
                                    nfcRFIDReaderState = null;
                                    try {
                                        BrickletNFCRFID.TagID tagID = getDevice().getTagID();
                                        String tidAsHexString = NFCTag.getTidAsHexString(tagID.tid);
                                        if (!this.tagMap.containsKey(tidAsHexString)) {
                                            NFCTag nfcTagID = new NFCTag(tagID);
                                            this.tagMap.put(tidAsHexString, nfcTagID);
                                            getCallback().tagDiscovered(nfcTagID);
                                        } else {
                                            this.tagMap.get(tidAsHexString).updateDiscoveryTimeStamp();
                                        }
                                        if (activeTagsToRead.contains(tidAsHexString)) {
                                            activeTagsToRead.remove(tidAsHexString);
                                            NFCTag nfcTag = this.tagMap.get(tidAsHexString);
                                            nfcTag.setReadContent(readTag(nfcTag));
                                            getCallback().tagRead(nfcTag);
                                        }
                                        if (activeTagsToWrite.containsKey(tidAsHexString)) {
                                            NFCWrite write = activeTagsToWrite.remove(tidAsHexString);
                                            NFCTag nfcTag = this.tagMap.get(tidAsHexString);
                                            nfcTag.setWriteContent(write.getValue());
                                            writeTag(nfcTag);
                                            getCallback().tagWritten(nfcTag);
                                        }
                                    } catch (TimeoutException ex) {
                                        Logger.getLogger(NFCRFIDDevice.class.getName()).log(Level.SEVERE, null, ex);
                                        Thread.sleep(200);
                                    }
                                } else {
                                    nfcRFIDReaderState = null;
                                }
                            }
                            for (Iterator<Map.Entry<String, NFCTag>> entries = this.tagMap.entrySet().iterator(); entries.hasNext();) {
                                Map.Entry<String, NFCTag> entry = entries.next();
                                if (System.currentTimeMillis() - entry.getValue().getLatestDiscoveryTimeStamp() > getScanningInterval() * 3) {
                                    entries.remove();
                                    activeTagsToRead.remove(entry.getValue().getTidAsHexString());
                                    getCallback().tagVanished(entry.getValue());
                                }
                            }
                        }
                    } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    } catch (Exception ex) {
                        Logger.getLogger(NFCRFIDDevice.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }

        private void writeTag(NFCTag nfcTag) throws Exception {
            for (Entry<Integer, short[]> entry : nfcTag.getPagesToWrite().entrySet()) {
//                System.out.println("Writing: @" + entry.getKey() + ": " + Arrays.toString(entry.getValue()));
                getDevice().writePage(entry.getKey(), entry.getValue());
                //nfcRFIDReaderState = new NFCRFIDReaderState(BrickletNFCRFID.STATE_WRITE_PAGE_READY, true);
                while (nfcRFIDReaderState == null || !nfcRFIDReaderState.getIsIdle() || nfcRFIDReaderState.getState() == BrickletNFCRFID.STATE_WRITE_PAGE) {
                    this.wait(1000);
                }
                nfcTag.setLatestReaderState(nfcRFIDReaderState);
                if (nfcRFIDReaderState.getState() == BrickletNFCRFID.STATE_WRITE_PAGE_ERROR) {
                    nfcRFIDReaderState = null;
                    return;
                }
                nfcRFIDReaderState = null;
            }
        }

        private Short[] readTag(NFCTag nfcTAGID) throws Exception {
            List<Short> shorts = new LinkedList<>();
            int requestingPage = 0;
            boolean endOfFile = false;
            while (!endOfFile) {
                getDevice().requestPage(requestingPage);
                while (nfcRFIDReaderState == null || !nfcRFIDReaderState.getIsIdle() || nfcRFIDReaderState.getState() == BrickletNFCRFID.STATE_REQUEST_PAGE) {
                    this.wait(1000);
                }
                if (nfcRFIDReaderState.getState() == BrickletNFCRFID.STATE_REQUEST_PAGE_READY) {
                    short[] page = getDevice().getPage();
                    for (short word : page) {
                        shorts.add(word);
                    };
                    if (nfcTAGID.getType() == NFCType.Type1) {
                        requestingPage += 2;
                    }
                    if (nfcTAGID.getType() == NFCType.Type2) {
                        requestingPage += 4;
                    }
                } else {
                    endOfFile = true;
                }
                nfcRFIDReaderState = null;
            }
            return shorts.toArray(new Short[0]);
        }

        public synchronized void setScanningInterval(long interval) {
            this.interval = interval;
            notifyAll();
        }

        public synchronized long getScanningInterval() {
            return interval;
        }

        @Override
        public void stateChanged(short s, boolean bln) {
            synchronized (this) {
                this.nfcRFIDReaderState = new NFCRFIDReaderState(s, bln);
                notifyAll();
            }
        }
    }
}
