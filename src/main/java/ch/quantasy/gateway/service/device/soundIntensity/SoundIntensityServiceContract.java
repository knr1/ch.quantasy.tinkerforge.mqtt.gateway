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
package ch.quantasy.gateway.service.device.soundIntensity;

import ch.quantasy.gateway.intent.soundIntensity.SoundIntensityIntent;
import ch.quantasy.gateway.service.device.DeviceServiceContract;
import ch.quantasy.tinkerforge.device.TinkerforgeDeviceClass;
import ch.quantasy.tinkerforge.device.soundIntensity.SoundIntensityDevice;
import java.util.Map;

/**
 *
 * @author reto
 */
public class SoundIntensityServiceContract extends DeviceServiceContract {

    public final String REACHED;
    public final String PERIOD;
    public final String CALLBACK_PERIOD;
    public final String THRESHOLD;

    public final String SOUND_INTENSITY;
    public final String STATUS_SOUND_INTENSITY;
    public final String STATUS_SOUND_INTENSITY_THRESHOLD;
    public final String STATUS_SOUND_INTENSITY_CALLBACK_PERIOD;
    public final String EVENT_INTENSITY;
    public final String EVENT_INTENSITY_REACHED;
    
    public final String DEBOUNCE;
    public final String STATUS_DEBOUNCE;
    public final String EVENT_DEBOUNCE;
    public final String STATUS_DEBOUNCE_PERIOD;

    public SoundIntensityServiceContract(SoundIntensityDevice device) {
        this(device.getUid(), TinkerforgeDeviceClass.getDevice(device.getDevice()).toString());
    }

    public SoundIntensityServiceContract(String id) {
        this(id, TinkerforgeDeviceClass.SoundIntensity.toString());
    }

    public SoundIntensityServiceContract(String id, String device) {
        super(id, device,SoundIntensityIntent.class);

        PERIOD = "period";
        CALLBACK_PERIOD = "callbackPeriod";
        THRESHOLD = "threshold";

        REACHED = "reached";

        SOUND_INTENSITY = "soundIntensity";
        STATUS_SOUND_INTENSITY = STATUS + "/" + SOUND_INTENSITY;
        STATUS_SOUND_INTENSITY_THRESHOLD = STATUS_SOUND_INTENSITY + "/" + THRESHOLD;
        STATUS_SOUND_INTENSITY_CALLBACK_PERIOD = STATUS_SOUND_INTENSITY + "/" + CALLBACK_PERIOD;
        EVENT_INTENSITY = EVENT + "/" + SOUND_INTENSITY;
        EVENT_INTENSITY_REACHED = EVENT_INTENSITY + "/" + REACHED;
    
        DEBOUNCE = "debounce";
        STATUS_DEBOUNCE = STATUS + "/" + DEBOUNCE;
        STATUS_DEBOUNCE_PERIOD = STATUS_DEBOUNCE + "/" + PERIOD;
        EVENT_DEBOUNCE = EVENT + "/" + DEBOUNCE;
    }

    @Override
    protected void descirbeMore(Map<String, String> descriptions) {
    
        descriptions.put(EVENT_INTENSITY, "- timestamp: [0.." + Long.MAX_VALUE + "]\n  value: [0..10000]\n");
        descriptions.put(EVENT_INTENSITY_REACHED, "- timestamp: [0.." + Long.MAX_VALUE + "]\n  value: [0..10000]\n");
        descriptions.put(STATUS_SOUND_INTENSITY_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        descriptions.put(STATUS_SOUND_INTENSITY_THRESHOLD, "option: [x|o|i|<|>]\n min: [0..10000]\n max: [0..10000]");
        descriptions.put(STATUS_DEBOUNCE_PERIOD, "[0.." + Long.MAX_VALUE + "]");
    }
}
