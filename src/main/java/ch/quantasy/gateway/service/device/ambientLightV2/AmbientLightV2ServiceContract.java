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
package ch.quantasy.gateway.service.device.ambientLightV2;

import ch.quantasy.gateway.message.event.ambinetLightV2.IlluminanceEvent;
import ch.quantasy.gateway.message.intent.ambientLightV2.AmbientLightV2Intent;
import ch.quantasy.gateway.service.device.DeviceServiceContract;
import ch.quantasy.tinkerforge.device.TinkerforgeDeviceClass;
import ch.quantasy.tinkerforge.device.ambientLightV2.AmbientLightV2Device;
import java.util.Map;

/**
 *
 * @author reto
 */
public class AmbientLightV2ServiceContract extends DeviceServiceContract {

    public final String REACHED;
    public final String PERIOD;
    public final String CALLBACK_PERIOD;
    public final String THRESHOLD;

    public final String ILLUMINANCE;
    public final String STATUS_ILLUMINANCE;
    public final String STATUS_ILLUMINANCE_THRESHOLD;
    public final String STATUS_ILLUMINANCE_CALLBACK_PERIOD;
    public final String EVENT_IllUMINANCE;
    public final String EVENT_ILLUMINANCE_REACHED;

    public final String DEBOUNCE;
    public final String STATUS_DEBOUNCE;
    public final String EVENT_DEBOUNCE;
    public final String STATUS_DEBOUNCE_PERIOD;

    public final String CONFIGURATION;
    public final String STATUS_CONFIGURATION;

    public AmbientLightV2ServiceContract(AmbientLightV2Device device) {
        this(device.getUid(), TinkerforgeDeviceClass.getDevice(device.getDevice()).toString());
    }

    public AmbientLightV2ServiceContract(String id) {
        this(id, TinkerforgeDeviceClass.AmbientLightV2.toString());
    }

    public AmbientLightV2ServiceContract(String id, String device) {
        super(id, device, AmbientLightV2Intent.class);

        PERIOD = "period";
        CALLBACK_PERIOD = "callbackPeriod";
        THRESHOLD = "threshold";

        REACHED = "reached";

        ILLUMINANCE = "illuminance";
        STATUS_ILLUMINANCE = STATUS + "/" + ILLUMINANCE;
        STATUS_ILLUMINANCE_THRESHOLD = STATUS_ILLUMINANCE + "/" + THRESHOLD;
        STATUS_ILLUMINANCE_CALLBACK_PERIOD = STATUS_ILLUMINANCE + "/" + CALLBACK_PERIOD;
        EVENT_IllUMINANCE = EVENT + "/" + ILLUMINANCE;
        EVENT_ILLUMINANCE_REACHED = EVENT_IllUMINANCE + "/" + REACHED;

        DEBOUNCE = "debounce";
        STATUS_DEBOUNCE = STATUS + "/" + DEBOUNCE;
        STATUS_DEBOUNCE_PERIOD = STATUS_DEBOUNCE + "/" + PERIOD;
        EVENT_DEBOUNCE = EVENT + "/" + DEBOUNCE;
        
        CONFIGURATION = "configuration";
        STATUS_CONFIGURATION = STATUS + "/" + CONFIGURATION;
        addMessageTopic(EVENT_IllUMINANCE, IlluminanceEvent.class);
        addMessageTopic(EVENT_ILLUMINANCE_REACHED, IlluminanceEvent.class);
    }

    @Override
    protected void descirbeMore(Map<String, String> descriptions) {

       
        descriptions.put(STATUS_ILLUMINANCE_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        descriptions.put(STATUS_ILLUMINANCE_THRESHOLD, "option: [x|o|i|<|>]\n min: [0..100000]\n max: [0..100000]");
        descriptions.put(STATUS_DEBOUNCE_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        descriptions.put(STATUS_CONFIGURATION, "illuminanceRange:[lx_unlimitted|lx_64000|lx_32000|lx_16000|lx_8000|lx_13000|lx_600]\n integrationTime: [ms_50|ms_100|ms_150|ms_200|ms_250|ms_300|ms_350|ms_400]\n");
    }
}
