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
package ch.quantasy.gateway.service.tinkerforge.co2;

import ch.quantasy.gateway.message.co2.CO2ConcentrationEvent;
import ch.quantasy.gateway.message.co2.CO2Intent;
import ch.quantasy.gateway.message.co2.Co2ConcentrationCallbackPeriodStatus;
import ch.quantasy.gateway.message.co2.Co2ConcentrationCallbackThresholdStatus;
import ch.quantasy.gateway.message.co2.DebouncePeriodStatus;
import ch.quantasy.gateway.service.tinkerforge.DeviceServiceContract;
import ch.quantasy.mqtt.gateway.client.message.Message;
import ch.quantasy.tinkerforge.device.TinkerforgeDeviceClass;
import ch.quantasy.tinkerforge.device.co2.CO2Device;
import java.util.Map;

/**
 *
 * @author reto
 */
public class CO2ServiceContract extends DeviceServiceContract {

    public final String REACHED;
    public final String PERIOD;
    public final String CALLBACK_PERIOD;
    public final String THRESHOLD;

    public final String CO2_CONCENTRATION;
    public final String STATUS_CO2_CONCENTRATION;
    public final String STATUS_CO2_CONCENTRATION_THRESHOLD;
    public final String STATUS_CO2_CONCENTRATION_CALLBACK_PERIOD;
    public final String EVENT_CO2_CONCENTRATION;
    public final String EVENT_CO2_CONCENTRATION_REACHED;

    public final String DEBOUNCE;
    public final String STATUS_DEBOUNCE;
    public final String EVENT_DEBOUNCE;
    public final String STATUS_DEBOUNCE_PERIOD;

    public CO2ServiceContract(CO2Device device) {
        this(device.getUid(), TinkerforgeDeviceClass.getDevice(device.getDevice()).toString());
    }

    public CO2ServiceContract(String id) {
        this(id, TinkerforgeDeviceClass.CO2.toString());
    }

    public CO2ServiceContract(String id, String device) {
        super(id, device, CO2Intent.class);

        PERIOD = "period";
        CALLBACK_PERIOD = "callbackPeriod";
        THRESHOLD = "threshold";

        REACHED = "reached";

        CO2_CONCENTRATION = "CO2Concentration";
        STATUS_CO2_CONCENTRATION = STATUS + "/" + CO2_CONCENTRATION;
        STATUS_CO2_CONCENTRATION_THRESHOLD = STATUS_CO2_CONCENTRATION + "/" + THRESHOLD;
        STATUS_CO2_CONCENTRATION_CALLBACK_PERIOD = STATUS_CO2_CONCENTRATION + "/" + CALLBACK_PERIOD;
        EVENT_CO2_CONCENTRATION = EVENT + "/" + CO2_CONCENTRATION;
        EVENT_CO2_CONCENTRATION_REACHED = EVENT_CO2_CONCENTRATION + "/" + REACHED;

        DEBOUNCE = "debounce";
        STATUS_DEBOUNCE = STATUS + "/" + DEBOUNCE;
        STATUS_DEBOUNCE_PERIOD = STATUS_DEBOUNCE + "/" + PERIOD;
        EVENT_DEBOUNCE = EVENT + "/" + DEBOUNCE;

    }

    @Override
    public void setServiceMessageTopics(Map<String, Class<? extends Message>> messageTopicMap) {
        messageTopicMap.put(EVENT_CO2_CONCENTRATION, CO2ConcentrationEvent.class);
        messageTopicMap.put(EVENT_CO2_CONCENTRATION_REACHED, CO2ConcentrationEvent.class);
        messageTopicMap.put(STATUS_CO2_CONCENTRATION_CALLBACK_PERIOD, Co2ConcentrationCallbackPeriodStatus.class);
        messageTopicMap.put(STATUS_CO2_CONCENTRATION_THRESHOLD, Co2ConcentrationCallbackThresholdStatus.class);
        messageTopicMap.put(STATUS_DEBOUNCE_PERIOD, DebouncePeriodStatus.class);
    }

}
