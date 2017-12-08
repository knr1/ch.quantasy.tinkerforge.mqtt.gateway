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

import ch.quantasy.gateway.message.multiTouch.RecalibratedEvent;
import ch.quantasy.gateway.message.multiTouch.TouchStateEvent;
import ch.quantasy.gateway.message.multiTouch.MultiTouchIntent;
import ch.quantasy.gateway.message.multiTouch.ElectrodeConfigStatus;
import ch.quantasy.gateway.message.multiTouch.SensitivityStatus;
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

    public final String TOUCH_STATE;
    public final String STATUS_ELECTRODE_CONFIG;
    public final String EVENT_TOUCH_STATE;
    public final String RECALIBRATE;
    public final String RECALIBRATED;
    public final String EVENT_RECALIBRATED;

    public MultiTouchServiceContract(MultiTouchDevice device) {
        this(device.getUid(), TinkerforgeDeviceClass.getDevice(device.getDevice()).toString());
    }

    public MultiTouchServiceContract(String id) {
        this(id, TinkerforgeDeviceClass.MultiTouch.toString());
    }

    public MultiTouchServiceContract(String id, String device) {
        super(id, device, MultiTouchIntent.class);

        ELECTRODE = "electrode";
        SENSITIVITY = "sensitivity";
        CONFIG = "config";
        ELECTRODE_SENSITIVITY = ELECTRODE + "/" + SENSITIVITY;
        ELECTRODE_CONFIG = ELECTRODE + "/" + CONFIG;
        STATUS_ELECTRODE_CONFIG = STATUS + "/" + ELECTRODE_CONFIG;
        STATUS_ELECTRODE_SENSITIVITY = STATUS + "/" + ELECTRODE_SENSITIVITY;

        TOUCH_STATE = "touchState";
        EVENT_TOUCH_STATE = EVENT + "/" + TOUCH_STATE;
        RECALIBRATE = "recalibration";
        RECALIBRATED = "recalibrated";
        EVENT_RECALIBRATED = EVENT + "/" + RECALIBRATED;

        addMessageTopic(EVENT_TOUCH_STATE, TouchStateEvent.class);
        addMessageTopic(EVENT_RECALIBRATED, RecalibratedEvent.class);
        addMessageTopic(STATUS_ELECTRODE_SENSITIVITY, SensitivityStatus.class);
        addMessageTopic(STATUS_ELECTRODE_CONFIG, ElectrodeConfigStatus.class);
    }

    
}
