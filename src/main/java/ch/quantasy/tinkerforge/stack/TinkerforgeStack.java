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
package ch.quantasy.tinkerforge.stack;

import ch.quantasy.tinkerforge.device.TinkerforgeDevice;
import ch.quantasy.tinkerforge.device.TinkerforgeDeviceClass;
import ch.quantasy.tinkerforge.device.TinkerforgeDeviceListener;
import ch.quantasy.tinkerforge.device.TinkerforgeDeviceMapper;
import com.tinkerforge.Device;
import com.tinkerforge.IPConnection;
import com.tinkerforge.IPConnection.EnumerateListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class tries to give a helping hand in order to represent a
 * Tinkerforge-stack. It gives convenience-methods for the Java-Developer.
 *
 * @author reto
 *
 */
public class TinkerforgeStack implements EnumerateListener {

    private int enumCount;

    public static final int DEFAULT_CONNECTION_TIMEOUT_IN_MILLISECONDS = 1000 * 10;

    private final TinkerforgeStackAddress stackAddress;
    private final ConnectionHandler ipConnectionHandler;

    private final Set<TinkerforgeStackListener> tinkerforgeStackListeners;
    private final Set<TinkerforgeDeviceListener> tinkerforgeDeviceListeners;

    private final Map<String, TinkerforgeDevice> deviceMap;

    Timer timer;

    /**
     * Creates a representation of a Tinkerforge-Stack. Either at 'localhost' or
     * via a 'Server'-name given in the WLAN-Brick
     *
     * @param stackAddress
     */
    public TinkerforgeStack(final TinkerforgeStackAddress stackAddress) {
        if (stackAddress == null) {
            throw new IllegalArgumentException();
        }
        this.ipConnectionHandler = new ConnectionHandler(this);
        this.ipConnectionHandler.setConnectionTimeoutInMilliseconds(DEFAULT_CONNECTION_TIMEOUT_IN_MILLISECONDS);
        this.tinkerforgeStackListeners = new HashSet<>();
        this.tinkerforgeDeviceListeners = new HashSet<>();
        this.deviceMap = new HashMap<>();
        this.stackAddress = stackAddress;

    }

    public void addListener(TinkerforgeStackListener listener) {

        if (this.tinkerforgeStackListeners.add(listener)) {
            if (this.isConnected()) {
                listener.connected(this);
            } else {
                listener.disconnected(this);
            }
        }
    }

    public void removeListener(TinkerforgeStackListener listener) {
        this.tinkerforgeStackListeners.remove(listener);
    }

    public void addListener(TinkerforgeDeviceListener listener) {
        this.tinkerforgeDeviceListeners.add(listener);
    }

    public void removeListener(TinkerforgeDeviceListener listener) {
        this.tinkerforgeDeviceListeners.remove(listener);
    }

    public TinkerforgeStackAddress getStackAddress() {
        return stackAddress;
    }

    public void connect() {
        ipConnectionHandler.connect();
    }

    public void disconnect() {
        ipConnectionHandler.disconnect();
    }

    /**
     * Called when a new {@link Device} has been found/connected
     *
     * @param device
     */
    protected void deviceConnected(TinkerforgeDevice device) {
        for (TinkerforgeDeviceListener listener : tinkerforgeDeviceListeners) {
            device.addListener(listener);
        }
        device.connected();
        Logger.getLogger(TinkerforgeStack.class.getName()).log(Level.INFO, "Connected @Stack:" + this.getStackAddress() + " Device: " + device.getUid());
    }

    public boolean isConnected() {
        return ipConnectionHandler.isConnected();
    }

    /**
     * Called when a {@link Device} already known to the system has been
     * reconnected. A good strategy is to drop the old device-instance and
     * connect the Listeners to this new instance of the {@link Device}.
     *
     * @param device
     */
    protected void deviceReConnected(TinkerforgeDevice device) {
        for (TinkerforgeDeviceListener listener : tinkerforgeDeviceListeners) {
            device.addListener(listener);
        }
        device.reconnected();
        Logger.getLogger(TinkerforgeStack.class.getName()).log(Level.INFO, "Reconnected @Stack:" + this.getStackAddress() + " Device: " + device.getUid());
    }

    /**
     * Called when a {@link Device} already known to the system has been
     * disconnected.
     *
     * @param device
     */
    protected void deviceDisconnected(TinkerforgeDevice device) {
        device.disconnected();
        Logger.getLogger(TinkerforgeStack.class.getName()).log(Level.INFO, "Disconnected @Stack:" + this.getStackAddress() + " Device: " + device.getUid());

    }

    /**
     * Returns the most recent list of all valid connected {@link Device}s.
     *
     * @return
     */
    public Set<TinkerforgeDevice> getDevices() {
        return new HashSet<>(this.deviceMap.values());
    }

    private IPConnection ipConnection;

    public synchronized IPConnection getIpConnection() {
        return ipConnection;
    }

    protected synchronized void connected(IPConnection ipConnection) throws Exception {
        System.out.println("Connected at TinkerforgeStack");
        this.ipConnection = ipConnection;
        this.ipConnection.addEnumerateListener(this);
        this.ipConnection.enumerate();
        for (TinkerforgeStackListener listener : tinkerforgeStackListeners) {
            listener.connected(TinkerforgeStack.this);
        }
        if (timer != null) {
            timer.cancel();
        }
        timer = new Timer();
        this.timer.schedule(new WatchDog(1), 10000, 10000);

    }

    protected void disconnected() {
        this.ipConnection.removeEnumerateListener(this);
        for (TinkerforgeDevice device : getDevices()) {
            deviceDisconnected(device);
        }
        for (TinkerforgeStackListener listener : tinkerforgeStackListeners) {
            listener.disconnected(TinkerforgeStack.this);
        }
        this.ipConnection = null;
        if (timer != null) {
            timer.cancel();
        }
    }

    @Override
    public void enumerate(final String uid, final String connectedUid, final char position,
            final short[] hardwareVersion, final short[] firmwareVersion, final int deviceIdentifier,
            final short enumerationType) {
        count--;
        boolean isNewDevice = this.manageTinkerforgeDevice(deviceIdentifier, uid, ipConnection);
        switch (enumerationType) {
            case IPConnection.ENUMERATION_TYPE_AVAILABLE:
                if (isNewDevice) {
                    TinkerforgeStack.this.deviceConnected(TinkerforgeStack.this.deviceMap.get(uid));
                    Logger.getLogger(TinkerforgeStack.class.getName()).log(Level.INFO, "Enumerated: available for new device " + uid);
                } else {
                    //Logger.getLogger(TinkerforgeStack.class.getName()).log(Level.INFO, "Enumerated: available for known device" + uid);
                }
                break;
            case IPConnection.ENUMERATION_TYPE_CONNECTED:
                if (isNewDevice) {
                    TinkerforgeStack.this.deviceConnected(TinkerforgeStack.this.deviceMap.get(uid));
                    Logger.getLogger(TinkerforgeStack.class.getName()).log(Level.INFO, "Enumerated: new" + uid);

                } else {
                    TinkerforgeStack.this.deviceReConnected(TinkerforgeStack.this.deviceMap.get(uid));
                    Logger.getLogger(TinkerforgeStack.class.getName()).log(Level.INFO, "Enumerated: reconnect" + uid);

                }
                break;
            case IPConnection.ENUMERATION_TYPE_DISCONNECTED:
                if (isNewDevice) {
                    // That is strange!
                    Logger.getLogger(TinkerforgeStack.class.getName()).log(Level.WARNING, "Strange:" + uid);
                    deviceMap.remove(uid);
                    return;
                }
                TinkerforgeDevice device = TinkerforgeStack.this.deviceMap.get(uid);
                if (device != null) {
                    TinkerforgeStack.this
                            .deviceDisconnected(device);
                    Logger.getLogger(TinkerforgeStack.class.getName()).log(Level.INFO, "Enumerated: disconnected for non null device" + uid);

                }
                Logger.getLogger(TinkerforgeStack.class.getName()).log(Level.INFO, "Enumerated: disconnected for null device" + uid);

                break;
            default:
                Logger.getLogger(TinkerforgeStack.class.getName()).log(Level.WARNING, "!!!Unknown cause: " + enumerationType);
        }

    }

    private boolean manageTinkerforgeDevice(final int deviceIdentifier, final String uid, final IPConnection connection) {
        try {
            TinkerforgeDevice tinkerforgeDevice = TinkerforgeStack.this.deviceMap.get(uid);
            if (tinkerforgeDevice != null) {
                try {
                    if (this.getIpConnection().equals(tinkerforgeDevice.getIPConnection())) {
                        if (tinkerforgeDevice.isConnected()) {
                            return false; //Device already known.
                        }
                    }
                } catch (Exception ex) {
                    Logger.getLogger(TinkerforgeStack.class.getName()).log(Level.SEVERE, null, ex);
                    // There is a device, but the connection to it is broken.
                }
            }

            TinkerforgeDeviceClass tinkerforgeDeviceClass = TinkerforgeDeviceClass.getDevice(deviceIdentifier);
            if (tinkerforgeDeviceClass == null) {
                return false; //Device not (yet)supported
            }
            final Device device = (Device) tinkerforgeDeviceClass.deviceClass
                    .getDeclaredConstructor(String.class, IPConnection.class).newInstance(uid,
                    TinkerforgeStack.this.ipConnection);

            if (tinkerforgeDevice != null) {
                tinkerforgeDevice.updateDevice(device);
                return false;
            }
            tinkerforgeDevice = TinkerforgeDeviceMapper.getTinkerforgeDevice(TinkerforgeStack.this, device);
            TinkerforgeStack.this.deviceMap.put(tinkerforgeDevice.getUid(), tinkerforgeDevice);
            return true;
        } catch (final Exception ex) {
            Logger.getLogger(TinkerforgeStack.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.stackAddress);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TinkerforgeStack other = (TinkerforgeStack) obj;
        if (!Objects.equals(this.stackAddress, other.stackAddress)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getClass().getName() + "{" + "stackAddress=" + stackAddress + '}';

    }
    private int count;

    /**
     * The purpose of this class is to check, if Tinkerforge lost the
     * IP-Connection (Why is not known).
     * http://www.tinkerunity.org/forum/index.php/topic,3809.msg23169.html#msg23169
     * If the watchdog cannot get some value (via Tinkerforges IP-Connection),
     * the IP-Connection must be 'dead'. Hence disconnect the stack and connect
     * it again.
     */
    class WatchDog extends TimerTask {

        private int failCount;
        private final int maxFailCount;

        public WatchDog(int maxFailCount) {
            this.maxFailCount = maxFailCount;
        }

        @Override
        public void run() {
            try {
                System.out.println(System.currentTimeMillis() + " deviceMap: " + deviceMap.size());
                count = deviceMap.size();
                ipConnection.enumerate();
                Thread.sleep(5000);
                if (count > 0) {
                    ipConnectionHandler.reconnect();
                    this.cancel();
                }
            } catch (Exception ex) {
                Logger.getLogger(TinkerforgeStack.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        public int getMaxFailCount() {
            return maxFailCount;
        }

        public int getFailCount() {
            return failCount;
        }

        public void resetFailCount() {
            this.failCount = 0;
        }

    }

}
