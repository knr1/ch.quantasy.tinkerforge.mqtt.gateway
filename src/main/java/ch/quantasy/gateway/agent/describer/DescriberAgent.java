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
package ch.quantasy.gateway.agent.describer;

import ch.quantasy.gateway.agent.AbstractAgent;
import ch.quantasy.gateway.service.stackManager.ManagerServiceContract;
import java.net.URI;
import java.util.StringTokenizer;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 *
 * @author reto
 */
public class DescriberAgent extends AbstractAgent {

    private final ManagerServiceContract managerServiceContract;

    public DescriberAgent(URI mqttURI) throws MqttException {
        super(mqttURI, "349h3492zf", "DescriberAgent");

        managerServiceContract = new ManagerServiceContract("Manager");
        addMessage(managerServiceContract.INTENT_STACK_ADDRESS_ADD, "Lights01");

        subscribe("TF/LEDStrip/description/#", 0);

    }

    @Override
    public void messageArrived(String string, MqttMessage mm) throws Exception {
        string=string.replaceAll("description", "<serviceUID>").replaceAll("intent","intent/<agentID>");
        System.out.println(string);

        String message = new String(mm.getPayload()).replaceAll("\"", "");

        String[] split = message.split("\\\\n");
        for (String m : split) {
            m=m.replaceAll("\\\\\n", " ").replaceAll("\\\\", "").replaceAll("---", "---\n");
            
            if(m.length()<2)continue;
            System.out.println(" " + m);
        }
        System.out.println("");
    }

    public static void main(String... args) throws Throwable {
        URI mqttURI = URI.create("tcp://127.0.0.1:1883");
        if (args.length > 0) {
            mqttURI = URI.create(args[0]);
        } else {
            System.out.printf("Per default, 'tcp://127.0.0.1:1883' is chosen.\nYou can provide another address as first argument i.e.: tcp://iot.eclipse.org:1883\n");
        }
        System.out.printf("\n%s will be used as broker address.\n", mqttURI);
        DescriberAgent agent = new DescriberAgent(mqttURI);
        System.in.read();
    }

}
