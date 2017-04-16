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
package ch.quantasy.mqtt.agents.remoteSwitch;

import ch.quantasy.gateway.service.device.remoteSwitch.RemoteSwitchServiceContract;
import ch.quantasy.gateway.service.stackManager.ManagerServiceContract;
import ch.quantasy.mqtt.agents.GenericAgent;
import ch.quantasy.mqtt.agents.GenericAgentContract;
import ch.quantasy.tinkerforge.device.TinkerforgeDeviceClass;
import ch.quantasy.tinkerforge.device.remoteSwitch.DimSocketBParameters;
import ch.quantasy.tinkerforge.device.remoteSwitch.SwitchSocketBParameters;
import ch.quantasy.tinkerforge.stack.TinkerforgeStackAddress;
import java.net.URI;
import org.eclipse.paho.client.mqttv3.MqttException;
import ch.quantasy.mqtt.gateway.client.MessageReceiver;

/**
 *
 * @author reto
 */
public class RemoteSwitchAgent extends GenericAgent {

    private final ManagerServiceContract managerServiceContract;
    private final RemoteSwitchServiceContract remoteSwitchUG;
    private final RemoteSwitchServiceContract remoteSwitchEG;
    private final RemoteSwitchServiceContract remoteSwitchOG;

    public RemoteSwitchAgent(URI mqttURI) throws MqttException {
        super(mqttURI, "5pq34in", new GenericAgentContract("RemoteSwitcher", "remoteSwitcher01"));
        connect();

        managerServiceContract = new ManagerServiceContract("Manager");

        remoteSwitchUG = new RemoteSwitchServiceContract("qD7", TinkerforgeDeviceClass.RemoteSwitch.toString());
        remoteSwitchEG = new RemoteSwitchServiceContract("jKQ", TinkerforgeDeviceClass.RemoteSwitch.toString());
        remoteSwitchOG = new RemoteSwitchServiceContract("jKE", TinkerforgeDeviceClass.RemoteSwitch.toString());

        connectStacks(new TinkerforgeStackAddress("obergeschoss"));
        connectStacks(new TinkerforgeStackAddress("untergeschoss"));
        connectStacks(new TinkerforgeStackAddress("erdgeschoss"));

        subscribe("WebView/RemoteSwitch/E/touched/remoteSwitch/#", new MessageReceiver() {
            @Override
            public void messageReceived(String topic, byte[] mm) throws Exception {

                Switcher[] switchers = getMapper().readValue(mm, Switcher[].class);
                Switcher switcher = switchers[0];
                RemoteSwitchServiceContract contract = null;
                switch (switcher.getFloor()) {
                    case "UG":
                        contract = remoteSwitchUG;
                        break;
                    case "OG":
                        contract = remoteSwitchOG;
                        break;
                    case "EG":
                        contract = remoteSwitchEG;
                        break;
                    default:
                }
                if (contract == null) {
                    return;
                }
                if (switcher.getType().equals("switchSocketB")) {
                    SwitchSocketBParameters[] bs = getMapper().readValue(mm, SwitchSocketBParameters[].class);
                    publishIntent(contract.INTENT_SWITCH_SOCKET_B, bs[0]);
                }
                if (switcher.getType().equals("dimSocketB")) {
                    DimSocketBParameters[] bs = getMapper().readValue(mm, DimSocketBParameters[].class);
                    publishIntent(contract.INTENT_DIM_SOCKET_B, bs[0]);
                }
            }
        }
        );
    }

    static class Switcher {

        private String floor;
        private String type;

        public String getFloor() {
            return floor;
        }

        public String getType() {
            return type;
        }

    }

    public static void main(String[] args) throws Throwable {
        URI mqttURI = URI.create("tcp://127.0.0.1:1883");
        if (args.length > 0) {
            mqttURI = URI.create(args[0]);
        } else {
            System.out.printf("Per default, 'tcp://127.0.0.1:1883' is chosen.\nYou can provide another address as first argument i.e.: tcp://iot.eclipse.org:1883\n");
        }
        System.out.printf("\n%s will be used as broker address.\n", mqttURI);
        RemoteSwitchAgent agent = new RemoteSwitchAgent(mqttURI);
        System.in.read();
    }

}
