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
package ch.quantasy.gateway.binding.tinkerforge.piezoSpeaker;

import ch.quantasy.gateway.binding.tinkerforge.DeviceServiceContract;
import ch.quantasy.gateway.binding.tinkerforge.DeviceServiceContract;
import ch.quantasy.gateway.binding.tinkerforge.piezoSpeaker.BeepEvent;
import ch.quantasy.gateway.binding.tinkerforge.piezoSpeaker.CalibratedEvent;
import ch.quantasy.gateway.binding.tinkerforge.piezoSpeaker.MorseEvent;
import ch.quantasy.gateway.binding.tinkerforge.piezoSpeaker.PiezoSpeakerIntent;
import ch.quantasy.mqtt.gateway.client.message.Message;
import ch.quantasy.tinkerforge.device.TinkerforgeDeviceClass;
import ch.quantasy.tinkerforge.device.piezoSpeaker.PiezoSpeakerDevice;
import java.util.Map;

/**
 *
 * @author reto
 */
public class PiezoSpeakerServiceContract extends DeviceServiceContract {

    public final String BEEP;
    public final String MORSE;
    public final String CALIBRATE;
    public final String CALIBRATED;
    public final String STARTED;
    public final String FINISHED;
    public final String EVENT_CALIBRATED;
    public final String EVENT_BEEP_STARTED;
    public final String EVENT_BEEP_FINISHED;
    public final String EVENT_MORSE_STARTED;
    public final String EVENT_MORSE_FINISHED;

    public PiezoSpeakerServiceContract(PiezoSpeakerDevice device) {
        this(device.getUid(), TinkerforgeDeviceClass.getDevice(device.getDevice()).toString());
    }

    public PiezoSpeakerServiceContract(String id) {
        this(id, TinkerforgeDeviceClass.PiezoSpeaker.toString());
    }

    public PiezoSpeakerServiceContract(String id, String device) {
        super(id, device, PiezoSpeakerIntent.class);
        BEEP = "beep";
        MORSE = "morse";
        CALIBRATE = "calibrate";
        CALIBRATED = "calibrated";
        STARTED = "started";
        FINISHED = "finished";
        EVENT_CALIBRATED = EVENT + "/" + CALIBRATED;
        EVENT_BEEP_STARTED = EVENT + "/" + STARTED;
        EVENT_BEEP_FINISHED = EVENT + "/" + FINISHED;
        EVENT_MORSE_FINISHED = EVENT + "/" + FINISHED;
        EVENT_MORSE_STARTED = EVENT + "/" + STARTED;
    }

    @Override
    public void setServiceMessageTopics(Map<String, Class<? extends Message>> messageTopicMap) {
        messageTopicMap.put(EVENT_CALIBRATED, CalibratedEvent.class);
        messageTopicMap.put(EVENT_BEEP_FINISHED, BeepEvent.class);
        messageTopicMap.put(EVENT_BEEP_STARTED, BeepEvent.class);
        messageTopicMap.put(EVENT_MORSE_FINISHED, MorseEvent.class);
        messageTopicMap.put(EVENT_MORSE_STARTED, MorseEvent.class);
    }

}
