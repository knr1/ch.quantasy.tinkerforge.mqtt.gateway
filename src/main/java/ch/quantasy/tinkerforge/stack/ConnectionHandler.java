/*
 *   "TiMqWay"
 *
 *    TiMqWay(tm): A gateway to provide an MQTT-View for the Tinkerforge(tm) world (Tinkerforge-MQTT-Gateway).
 *
 *    Copyright (c) 2016 Bern University of Applied Sciences (BFH),
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
package ch.quantasy.tinkerforge.stack;

import static ch.quantasy.tinkerforge.stack.TinkerforgeStack.DEFAULT_CONNECTION_TIMEOUT_IN_MILLISECONDS;
import com.tinkerforge.AlreadyConnectedException;
import com.tinkerforge.Device;
import com.tinkerforge.IPConnection;
import com.tinkerforge.IPConnectionBase;
import com.tinkerforge.NotConnectedException;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author reto
 */
public class ConnectionHandler implements IPConnection.ConnectedListener, IPConnection.DisconnectedListener {

    private final TinkerforgeStack stack;
    private long connectionTimeoutInMilliseconds;

    IPConnection ipConnection;
    private final ScheduledExecutorService timerService;

    private Exception actualConnectionException;

    public ConnectionHandler(TinkerforgeStack stack) {
        this.connectionTimeoutInMilliseconds = DEFAULT_CONNECTION_TIMEOUT_IN_MILLISECONDS;
        this.stack = stack;
        this.timerService = Executors.newScheduledThreadPool(1, new ThreadFactory() {
            public Thread newThread(Runnable r) {
                Thread t = Executors.defaultThreadFactory().newThread(r);
                t.setDaemon(true);
                return t;
            }
        });
    }

    @Override
    public void disconnected(final short disconnectReason) {
        this.stack.disconnected();
        Logger.getLogger(ConnectionHandler.class.getName()).log(Level.INFO, " Disconnected due to: " + disconnectReason);
        if (disconnectReason == IPConnectionBase.DISCONNECT_REASON_ERROR) {
            connect();
        }
        //IPConnectionBase.java
        //DISCONNECT_REASON_REQUEST = 0;
        //DISCONNECT_REASON_ERROR = 1;
        //DISCONNECT_REASON_SHUTDOWN = 2;

    }

    /**
     * When the ipConnection to the real Tinkerforge-Stack is established, an
     * enumeration of the Brick/lets is called. {@link Device}s. Therefore it
     * waits for some time (3-seconds) before telling the TinkerforgeStack, that
     * ipConnection is established.
     */
    @Override
    public void connected(final short connectReason) {
        try {
            if (this.timerFuture != null) {
                this.timerFuture.cancel(false);
            }
            this.stack.connected(ipConnection);
        } catch (final Exception ex) {
            // Well, this should not happen?!
            // But will treat it gracefully.
            Logger.getLogger(ConnectionHandler.class.getName()).log(Level.SEVERE, "Exception during connected...", ex);
        }
    }

    /**
     * Keeps trying to connect to Tinkerforge-Stack. (After a first successful
     * connect, the Tinkerforge IP-Connection will manage auto-connect)
     *
     * @throws UnknownHostException
     * @throws IOException
     */
    private Future timerFuture;

    public void connect() {
        Logger.getLogger(ConnectionHandler.class.getName()).log(Level.INFO, "Connection requested...");
        if (this.timerFuture != null && !this.timerFuture.isCancelled()) {
            System.out.println("Timer is still up...");
            return;
        }
        timerFuture = this.timerService.scheduleAtFixedRate(new Runnable() {

            private int count;

            @Override
            public void run() {
                Logger.getLogger(ConnectionHandler.class.getName()).log(Level.INFO, "Attempt: ", count++);
                try {
                    try {
                        try {
                            if (ipConnection != null) {
                                ipConnection.disconnect();
                            }
                        } catch (NotConnectedException ex) {
                            //Just wanted to make sure!
                            //Logger.getLogger(TinkerforgeStack.class.getName()).log(Level.SEVERE, null, ex);
                        }
//                        //Patch! Tinkerforge runs into a out of memory, as it allocates threads that are not removed...
//                        //@TODO: Remove, as soon as Tinkerforge has corrected that problem.
//                        Socket tmpSocket = new Socket(stack.getStackAddress().getHostName(), stack.getStackAddress().getPort());
//                        tmpSocket.close();
//                        // End of Patch!
                        ipConnection = new IPConnection();
                        ipConnection.setAutoReconnect(true);
                        Logger.getLogger(ConnectionHandler.class.getName()).log(Level.INFO, "Got a new IP-Connection");
                        ipConnection.addConnectedListener(ConnectionHandler.this);
                        ipConnection.addDisconnectedListener(ConnectionHandler.this);
                        ipConnection.connect(stack.getStackAddress().getHostName(), stack.getStackAddress().getPort());
                        Logger.getLogger(ConnectionHandler.class.getName()).log(Level.INFO, "New IP-Connection connected");
                        timerFuture.cancel(false);

                    } catch (final AlreadyConnectedException e) {
                        // Oh, great, that is what we want!
                        Logger.getLogger(TinkerforgeStack.class.getName()).log(Level.SEVERE, null, e);

                    }
                    actualConnectionException = null;
                    //} catch (final NetworkException e) {
//                } catch (final UnknownHostException e) {  //Patch-catch
//                    // This Host is not up! try again in some seconds...
//                    Logger.getLogger(ConnectionHandler.class.getName()).log(Level.INFO, "Host not up or not reachable, I try again in about " + getConnectionTimeoutInMilliseconds() + "ms.");

                } catch (final Exception e) {
                    actualConnectionException = e;
                    Logger.getLogger(TinkerforgeStack.class.getName()).log(Level.SEVERE, null, e);
                }
            }
        }, 0, getConnectionTimeoutInMilliseconds(), TimeUnit.MILLISECONDS);

    }

    public long getConnectionTimeoutInMilliseconds() {
        return this.connectionTimeoutInMilliseconds;
    }

    public void setConnectionTimeoutInMilliseconds(long connectionTimeoutInMilliseconds) {
        this.connectionTimeoutInMilliseconds = connectionTimeoutInMilliseconds;
    }

    public Exception getActualConnectionException() {
        return this.actualConnectionException;
    }

    /**
     * Disconnects from a real Tinkerforge-Stack. If a ipConnection-timer is
     * still running, it is canceled.
     */
    public void disconnect() {
        if (this.timerFuture != null) {
            this.timerFuture.cancel(false);
        }
        try {
            Logger.getLogger(ConnectionHandler.class.getName()).log(Level.INFO, "IP will be disconnected");
            this.ipConnection.disconnect();
            this.ipConnection = null;
            Logger.getLogger(ConnectionHandler.class.getName()).log(Level.INFO, "IP is disconnected");

        } catch (final NotConnectedException e) {
            // So what
            Logger.getLogger(TinkerforgeStack.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    private boolean isReconnecting = false;

    public void reconnect() {
        Logger.getLogger(ConnectionHandler.class.getName()).log(Level.INFO, "Reconnecting:...");
        if (isReconnecting) {
            return;
        }
        isReconnecting = true;
        Logger.getLogger(ConnectionHandler.class.getName()).log(Level.INFO, "Disconnect...");
        disconnect();
        try {
            Logger.getLogger(ConnectionHandler.class.getName()).log(Level.INFO, "Will sleep for 2 Secs...");
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            Logger.getLogger(TinkerforgeStack.class.getName()).log(Level.SEVERE, null, ex);
        }
        Logger.getLogger(ConnectionHandler.class.getName()).log(Level.INFO, "Connect...");
        connect();
        isReconnecting = false;
    }

    public synchronized boolean isConnected() {
        return (this.ipConnection != null)
                && (this.ipConnection.getConnectionState() == IPConnection.CONNECTION_STATE_CONNECTED);
    }
}
