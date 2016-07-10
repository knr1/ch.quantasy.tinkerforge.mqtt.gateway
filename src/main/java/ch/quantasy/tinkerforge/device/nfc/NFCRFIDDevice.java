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
import ch.quantasy.tinkerforge.device.nfc.NFCTagID.NFCType;
import ch.quantasy.tinkerforge.stack.TinkerforgeStackAddress;
import com.tinkerforge.BrickletNFCRFID;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
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

    class NFCReader implements Runnable, BrickletNFCRFID.StateChangedListener {

        private Map<String, NFCTagID> tagMap;

        private NFCRFIDReaderState nfcRFIDReaderState;

        private long interval;

        public NFCReader() {
            tagMap = new HashMap<>();
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
                                        NFCTagID nfcTagID = new NFCTagID(getDevice().getTagID());
                                        if (!this.tagMap.containsKey(nfcTagID.getTidAsHexString()))
                                        {
                                            getCallback().tagDiscovered(nfcTagID);
                                        }
                                        this.tagMap.put(nfcTagID.getTidAsHexString(), nfcTagID);

                                    } catch (TimeoutException ex) {
                                        Logger.getLogger(NFCRFIDDevice.class.getName()).log(Level.SEVERE, null, ex);
                                        Thread.sleep(200);
                                    }
                                } else {
                                    nfcRFIDReaderState = null;
                                }
                            }
                            for(Iterator<Map.Entry<String,NFCTagID>> entries=this.tagMap.entrySet().iterator();entries.hasNext();){
                                Map.Entry<String,NFCTagID> entry=entries.next();
                                if(System.currentTimeMillis()-entry.getValue().getLatestDiscoveryTimeStamp()>getScanningInterval()*3){
                                    entries.remove();
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
                // System.out.println("STATE: " + this.nfcRFIDReaderState.state + " " + this.nfcRFIDReaderState.isIdle);
                notifyAll();
            }
        }
    }

    

    public static class NFCRFIDReaderState {

        private long timeStamp;
        private short state;
        private boolean isIdle;

        public NFCRFIDReaderState(BrickletNFCRFID.State state) {
            this(state.state, state.idle);
        }

        public NFCRFIDReaderState(short state, boolean isIdle) {
            this.state = state;
            this.isIdle = isIdle;
            this.timeStamp = System.currentTimeMillis();
        }

        public NFCRFIDReaderState() {
        }

        public short getState() {
            return state;
        }

        public boolean getIsIdle() {
            return isIdle;
        }

        public long getTimeStamp() {
            return timeStamp;
        }
    }

}
