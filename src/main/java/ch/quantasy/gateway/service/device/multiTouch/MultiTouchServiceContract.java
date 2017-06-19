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
package ch.quantasy.gateway.service.device.multiTouch;

import ch.quantasy.gateway.service.device.DeviceServiceContract;
import ch.quantasy.tinkerforge.device.TinkerforgeDeviceClass;
import ch.quantasy.tinkerforge.device.multiTouch.MultiTouchDevice;
import java.util.Map;

/**
 *
 * @author reto
 */
public class MultiTouchServiceContract extends DeviceServiceContract {

    public final String ELECTRODE_SENSITIVITY;
    public final String ELECTRODE;
    public final String SENSITIVITY;
    public final String CONFIG;
    public final String ELECTRODE_CONFIG;
    public final String STATUS_ELECTRODE_SENSITIVITY;
    public final String INTENT_ELECTRODE_CONFIG;
    public final String INTENT_ELECTRODE_SENSITIVITY;

    public final String TOUCH_STATE;
    public final String STATUS_ELECTRODE_CONFIG;
    public final String EVENT_TOUCH_STATE;
    public final String RECALIBRATE;
    public final String RECALIBRATED;
    public final String EVENT_RECALIBRATED;
    public final String INTENT_RECALIBRATE;

    public MultiTouchServiceContract(MultiTouchDevice device) {
        this(device.getUid(), TinkerforgeDeviceClass.getDevice(device.getDevice()).toString());
    }

    public MultiTouchServiceContract(String id) {
        this(id, TinkerforgeDeviceClass.MultiTouch.toString());
    }

    public MultiTouchServiceContract(String id, String device) {
        super(id, device);

        ELECTRODE = "electrode";
        SENSITIVITY = "sensitivity";
        CONFIG = "config";
        ELECTRODE_SENSITIVITY = ELECTRODE + "/" + SENSITIVITY;
        ELECTRODE_CONFIG = ELECTRODE + "/" + CONFIG;
        INTENT_ELECTRODE_CONFIG = INTENT + "/" + ELECTRODE_CONFIG;
        STATUS_ELECTRODE_CONFIG = STATUS + "/" + ELECTRODE_CONFIG;
        INTENT_ELECTRODE_SENSITIVITY = INTENT + "/" + ELECTRODE_SENSITIVITY;
        STATUS_ELECTRODE_SENSITIVITY = STATUS + "/" + ELECTRODE_SENSITIVITY;

        TOUCH_STATE = "touchState";
        EVENT_TOUCH_STATE = EVENT + "/" + TOUCH_STATE;
        RECALIBRATE = "recalibrate";
        RECALIBRATED = "recalibrated";
        EVENT_RECALIBRATED = EVENT + "/" + RECALIBRATED;
        INTENT_RECALIBRATE = INTENT + "/" + RECALIBRATE;
    }

    @Override
    protected void descirbeMore(Map<String, String> descriptions) {
        descriptions.put(INTENT_ELECTRODE_CONFIG, "[0..8191]");
        descriptions.put(INTENT_ELECTRODE_SENSITIVITY, "[0..8191]");
        descriptions.put(INTENT_RECALIBRATE, "[true|false]");

        descriptions.put(EVENT_TOUCH_STATE, "- timestamp: [0.." + Long.MAX_VALUE + "]\n  value: [0..8191]");
        descriptions.put(EVENT_RECALIBRATED, "- timestamp: [0.." + Long.MAX_VALUE + "]\n  value: true");

        descriptions.put(STATUS_ELECTRODE_SENSITIVITY, "[5..201]");
        descriptions.put(STATUS_ELECTRODE_CONFIG, "[0..8191]");
    }
}
