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
import com.tinkerforge.AlreadyConnectedException;
import com.tinkerforge.Device;
import com.tinkerforge.IPConnection;
import com.tinkerforge.IPConnection.ConnectedListener;
import com.tinkerforge.IPConnection.DisconnectedListener;
import com.tinkerforge.IPConnection.EnumerateListener;
import com.tinkerforge.IPConnectionBase;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.UnknownHostException;
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
public class TinkerforgeStack {

    public static final int DEFAULT_CONNECTION_TIMEOUT_IN_MILLISECONDS = 1000 * 60;
    private int connectionTimeoutInMilliseconds;
    private Timer timer;

    private final TinkerforgeStackAddress stackAddress;
    private final Set<TinkerforgeStackListener> listeners;
    private final Set<TinkerforgeDeviceListener> deviceListeners;

    private final IPConnection ipConnection;
    private final Map<String, TinkerforgeDevice> deviceMap;

    private final IPConnectionHandler ipConnectionHandler;
    private final DeviceEnumerationHandler deviceEnumerationHandler;

    private Exception actualConnectionException;

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
        this.connectionTimeoutInMilliseconds = TinkerforgeStack.DEFAULT_CONNECTION_TIMEOUT_IN_MILLISECONDS;
        this.listeners = new HashSet<>();
        this.deviceListeners = new HashSet<>();

        this.deviceMap = new HashMap<>();
        this.stackAddress = stackAddress;
        this.ipConnection = new IPConnection();
        this.ipConnectionHandler = new IPConnectionHandler(this.ipConnection);
        this.deviceEnumerationHandler = new DeviceEnumerationHandler(this.ipConnection);
    }

    public int getConnectionTimeoutInMilliseconds() {
        return this.connectionTimeoutInMilliseconds;
    }

    public void setConnectionTimeoutInMilliseconds(final int connectionTimeoutInMilliseconds) {
        this.connectionTimeoutInMilliseconds = connectionTimeoutInMilliseconds;
    }

    public Exception getActualConnectionException() {
        return this.actualConnectionException;
    }

    public void addListener(TinkerforgeStackListener listener) {

        if (this.listeners.add(listener)) {
            if (this.isConnected()) {
                listener.connected(this);
            } else {
                listener.disconnected(this);
            }
        }
    }

    public void removeListener(TinkerforgeStackListener listener) {
        this.listeners.remove(listener);
    }

    public void addListener(TinkerforgeDeviceListener listener) {
        this.deviceListeners.add(listener);
    }

    public void removeListener(TinkerforgeDeviceListener listener) {
        this.deviceListeners.remove(listener);
    }

    public TinkerforgeStackAddress getStackAddress() {
        return stackAddress;
    }

    /**
     * Keeps trying to connect to Tinkerforge-Stack. (After a first successful
     * connect, the Tinkerforge IP-Connection will manage auto-connect) Then it
     * connects to the real Tinkerforge-Stack. It then requests the connected
     * {@link Device}s. Therefore it waits for some time (3-seconds)
     *
     * @throws UnknownHostException
     * @throws IOException
     */
    public synchronized void connect() {
        if (this.timer != null) {
            return;
        }
        this.timer = new Timer(true);
        this.timer.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                try {
                    try {
                        ipConnection.connect(stackAddress.getHostName(), stackAddress.getPort());
                        Thread.sleep(3000);
                    } catch (final AlreadyConnectedException e) {
                        // So what
                    } catch (final InterruptedException e) {
                        // OK, we go on
                    }
                    actualConnectionException = null;
                    timer.cancel();
                    timer = null;
                } catch (final UnknownHostException e) {
                    actualConnectionException = e;
                } catch (final IOException e) {
                    actualConnectionException = e;
                }
            }
        }, 0, getConnectionTimeoutInMilliseconds());

    }

    /**
     * Disconnects from a real Tinkerforge-Stack. If a connection-timer is still
     * running, it is cancelled.
     */
    public synchronized void disconnect() {
        if (this.timer != null) {
            this.timer.cancel();
            this.timer = null;
        }
        try {
            this.ipConnection.disconnect();
        } catch (final NotConnectedException e) {
            // So what
        }
    }

    public boolean isConnected() {
        return (this.ipConnection != null)
                && (this.ipConnection.getConnectionState() == IPConnection.CONNECTION_STATE_CONNECTED);
    }

    /**
     * Called when a new {@link Device} has been found/connected
     *
     * @param device
     */
    protected void deviceConnected(TinkerforgeDevice device) {
        for (TinkerforgeDeviceListener listener : deviceListeners) {
            device.addListener(listener);
        }
        device.connected();
    }

    /**
     * Called when a {@link Device} already known to the system has been
     * reconnected. A good strategy is to drop the old device-instance and
     * connect the Listeners to this new instance of the {@link Device}.
     *
     * @param device
     */
    protected void deviceReConnected(TinkerforgeDevice device) {
        device.reconnected();
    }

    /**
     * Called when a {@link Device} already known to the system has been
     * disconnected.
     *
     * @param device
     */
    protected void deviceDisconnected(TinkerforgeDevice device) {
        device.disconnected();
    }

    /**
     * Returns the most recent list of all valid connected {@link Device}s.
     *
     * @return
     */
    public Set<TinkerforgeDevice> getDevices() {
        return new HashSet<>(this.deviceMap.values());
    }

    private class IPConnectionHandler implements ConnectedListener, DisconnectedListener {

        private final IPConnection connection;

        public IPConnectionHandler(final IPConnection connection) {
            this.connection = connection;
            connection.addConnectedListener(this);
            connection.addDisconnectedListener(this);
        }

        @Override
        public void disconnected(final short disconnectReason) {
            for (TinkerforgeDevice device : getDevices()) {
                deviceDisconnected(device);
            }
            for (TinkerforgeStackListener listener : listeners) {
                listener.disconnected(TinkerforgeStack.this);
            }
            System.out.println("Disconnected due to: " + disconnectReason);
            if(disconnectReason==IPConnectionBase.DISCONNECT_REASON_ERROR){
                connect();
            }
            //IPConnectionBase.java
            //DISCONNECT_REASON_REQUEST = 0;
            //DISCONNECT_REASON_ERROR = 1;
            //DISCONNECT_REASON_SHUTDOWN = 2;

        }

        @Override
        public void connected(final short connectReason) {
            try {
                this.connection.enumerate();
                for (TinkerforgeStackListener listener : listeners) {
                    listener.connected(TinkerforgeStack.this);
                }
                for (TinkerforgeDevice device : getDevices()) {
                    deviceConnected(device);
                }

            } catch (final Exception ex) {
                // Well, this should not happen?!
                // But will treat it gracefully.
                Logger.getLogger(TinkerforgeStack.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private class DeviceEnumerationHandler implements EnumerateListener {

        private final IPConnection connection;

        public DeviceEnumerationHandler(final IPConnection connection) {
            this.connection = connection;
            this.connection.addEnumerateListener(this);
        }

        @Override
        public void enumerate(final String uid, final String connectedUid, final char position,
                final short[] hardwareVersion, final short[] firmwareVersion, final int deviceIdentifier,
                final short enumerationType) {
            boolean isNewDevice = this.createTinkerforgeDevice(deviceIdentifier, uid);
            switch (enumerationType) {
                case IPConnection.ENUMERATION_TYPE_AVAILABLE:
                    if (isNewDevice) {
                        TinkerforgeStack.this.deviceConnected(TinkerforgeStack.this.deviceMap.get(uid));
                    }
                    break;
                case IPConnection.ENUMERATION_TYPE_CONNECTED:
                    if (isNewDevice) {
                        TinkerforgeStack.this.deviceConnected(TinkerforgeStack.this.deviceMap.get(uid));
                    } else {
                        TinkerforgeStack.this.deviceReConnected(TinkerforgeStack.this.deviceMap.get(uid));
                    }
                    break;
                case IPConnection.ENUMERATION_TYPE_DISCONNECTED:
                    if (isNewDevice) {
                        // That is strange!
                        deviceMap.remove(uid);
                        return;
                    }
                    TinkerforgeDevice device = TinkerforgeStack.this.deviceMap.get(uid);
                    if (device != null) {
                        TinkerforgeStack.this
                                .deviceDisconnected(device);
                    }
                    break;
                default:
                    System.out.println("!!!Unknown cause: " + enumerationType);
            }

        }

        private boolean createTinkerforgeDevice(final int deviceIdentifier, final String uid) {
            TinkerforgeDevice tinkerforgeDevice = TinkerforgeStack.this.deviceMap.get(uid);
            if (tinkerforgeDevice != null) {
                if (tinkerforgeDevice.isConnected()) {
                    return false;
                }
            }
            try {
                TinkerforgeDeviceClass tinkerforgeDeviceClass = TinkerforgeDeviceClass.getDevice(deviceIdentifier);
                if (tinkerforgeDeviceClass == null) {
                    return false; //Device not (yet)supported
                }
                final Device device = (Device) tinkerforgeDeviceClass.deviceClass
                        .getDeclaredConstructor(String.class, IPConnection.class).newInstance(uid,
                        TinkerforgeStack.this.ipConnection);
                if (tinkerforgeDevice != null) {
                    tinkerforgeDevice.setDevice(device);
                    return false;
                }
                tinkerforgeDevice = TinkerforgeDeviceMapper.getTinkerforgeDevice(stackAddress, device);
                TinkerforgeStack.this.deviceMap.put(tinkerforgeDevice.getUid(), tinkerforgeDevice);
                return true;
            } catch (final NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NotConnectedException | TimeoutException ex) {
                ex.printStackTrace();
            }
            return false;
        }

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

}
