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
package ch.quantasy.gateway.agent.TimedSwitch;

import ch.quantasy.gateway.service.device.motionDetector.MotionDetectorServiceContract;
import ch.quantasy.gateway.service.device.remoteSwitch.RemoteSwitchServiceContract;
import ch.quantasy.mqtt.gateway.agent.AbstractAgent;
import ch.quantasy.mqtt.gateway.agent.AgentContract;
import ch.quantasy.tinkerforge.device.TinkerforgeDeviceClass;
import ch.quantasy.tinkerforge.device.remoteSwitch.SwitchSocketCParameters;
import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 *
 * @author reto
 */
public class TimedSwitch extends AbstractAgent<AgentContract> {

    private final RemoteSwitchServiceContract remoteSwitchServiceContract;

    public TimedSwitch(URI mqttURI) throws MqttException {
        super(mqttURI, "9520efj30sdk", new AgentContract("Agent","TimedSwitch","qD7"));
        remoteSwitchServiceContract = new RemoteSwitchServiceContract("qD7", TinkerforgeDeviceClass.RemoteSwitch.toString());
        super.subscribe(remoteSwitchServiceContract.ID_TOPIC + "/#", 1);
        Timer t = new Timer();
        t.scheduleAtFixedRate(new Switcher(), 0, 1000*60*60);
    }

    class Switcher extends TimerTask {

        @Override
        public void run() {
            LocalDateTime theDate = LocalDateTime.now();
            int hour = theDate.getHour();
            System.out.print("Switch: ");
            if (state == SwitchSocketCParameters.SwitchTo.ON || (hour >21 || hour < 7)) {
                switchMotor(SwitchSocketCParameters.SwitchTo.OFF);
                state = SwitchSocketCParameters.SwitchTo.OFF;
                System.out.println("off");
            } else {
                switchMotor(SwitchSocketCParameters.SwitchTo.ON);
                state = SwitchSocketCParameters.SwitchTo.ON;
                System.out.println("on");
            }
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
                Logger.getLogger(TimedSwitch.class.getName()).log(Level.SEVERE, null, ex);
            }
            switchAlwaysOn();
            System.out.println("Always-on switched");
        }

    }

    @Override
    public void messageArrived(String string, MqttMessage mm) throws Exception {
        System.out.printf("Topic: %s Message: %s\n", string, new String(mm.getPayload()));
    }

    private SwitchSocketCParameters.SwitchTo state;

    private void switchMotor(SwitchSocketCParameters.SwitchTo state) {
        if (this.state == state) {
            return;
        }
        this.state = state;
        SwitchSocketCParameters config = new SwitchSocketCParameters('L', (short) 2, state);
        String topic = remoteSwitchServiceContract.INTENT_SWITCH_SOCKET_C;
        addMessage(topic, config);
        System.out.println("Switching: " + state);
    }
    
    private void switchAlwaysOn() { 
        SwitchSocketCParameters config = new SwitchSocketCParameters('L', (short) 1, SwitchSocketCParameters.SwitchTo.ON);
        String topic = remoteSwitchServiceContract.INTENT_SWITCH_SOCKET_C;
        addMessage(topic, config);
    }

    public static void main(String[] args) throws Throwable {
        URI mqttURI = URI.create("tcp://127.0.0.1:1883");
        //URI mqttURI = URI.create("tcp://matrix:1883");

        if (args.length > 0) {
            mqttURI = URI.create(args[0]);
        } else {
            System.out.printf("Per default, 'tcp://127.0.0.1:1883' is chosen.\nYou can provide another address as first argument i.e.: tcp://iot.eclipse.org:1883\n");
        }
        System.out.printf("\n%s will be used as broker address.\n", mqttURI);
        TimedSwitch agent = new TimedSwitch(mqttURI);
        System.in.read();
    }

}
