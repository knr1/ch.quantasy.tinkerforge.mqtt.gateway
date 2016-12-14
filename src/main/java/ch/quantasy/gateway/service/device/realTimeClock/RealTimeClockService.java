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
package ch.quantasy.gateway.service.device.realTimeClock;

import ch.quantasy.gateway.service.device.AbstractDeviceService;
import ch.quantasy.tinkerforge.device.realTimeClock.AlarmParamter;
import ch.quantasy.tinkerforge.device.realTimeClock.DateTimeParameter;
import ch.quantasy.tinkerforge.device.realTimeClock.RealTimeClockDevice;
import java.net.URI;
import org.eclipse.paho.client.mqttv3.MqttException;
import ch.quantasy.tinkerforge.device.realTimeClock.RealTimeClockDeviceCallback;

/**
 *
 * @author reto
 */
public class RealTimeClockService extends AbstractDeviceService<RealTimeClockDevice, RealTimeClockServiceContract> implements RealTimeClockDeviceCallback {

    public RealTimeClockService(RealTimeClockDevice device, URI mqttURI) throws MqttException {
        super(mqttURI, device, new RealTimeClockServiceContract(device));
        publishDescription(getContract().INTENT_DATE_TIME_SET, "year: [2000..2099]\n month: [1..12]\n day:b[1..31]\n hour: [0..23]\n minute: [0..59]\n second: [0..59]\n centisecond: [0..9]\n weekday: [monday|tuesday|wednesday|thursday|friday|saturday|sunday]");
        publishDescription(getContract().STATUS_DATE_TIME, "year: [2000..2099]\n month: [1..12]\n day: [1..31]\n hour: [0..23]\n minute: [0..59]\n second: [0..59]\n centisecond: [0..9]\n weekday: [monday|tuesday|wednesday|thursday|friday|saturday|sunday]");
        publishDescription(getContract().INTENT_OFFSET, "[-128..127]");
        publishDescription(getContract().STATUS_OFFSET, "[-128..127]");
        publishDescription(getContract().INTENT_DATE_TIME_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        publishDescription(getContract().STATUS_DATE_TIME_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        publishDescription(getContract().EVENT_DATE_TIME, "timestamp: [0.." + Long.MAX_VALUE + "]\n value:\n  year: [2000..2099]\n month: [1..12]\n day: [1..31]\n hour: [0..23]\n minute: [0..59]\n second: [0..59]\n centisecond: [0..9]\n weekday: [monday|tuesday|wednesday|thursday|friday|saturday|sunday]");
        publishDescription(getContract().INTENT_ALARM, "month: [-1|1..12]\n day: [-1|1..31]\n hour: [-1|0..23]\n minute: [-1|0..59]\n second: [-1|0..59]\n weekday: [disabled|monday|tuesday|wednesday|thursday|friday|saturday|sunday]\n interval:[-1|0.." + Integer.MAX_VALUE + "]");
        publishDescription(getContract().STATUS_ALARM, "month: [-1|1..12]\n day: [-1|1..31]\n hour: [-1|0..23]\n minute: [-1|0..59]\n second: [-1|0..59]\n weekday: [disabled|monday|tuesday|wednesday|thursday|friday|saturday|sunday]\n interval:[-1|0.." + Integer.MAX_VALUE + "]");
        publishDescription(getContract().EVENT_ALARM, "timestamp: [0.." + Long.MAX_VALUE + "]\n value:\n   year: [2000..2099]\n month: [1..12]\n day: [1..31]\n hour: [0..23]\n minute: [0..59]\n second: [0..59]\n centisecond: [0..9]\n weekday: [monday|tuesday|wednesday|thursday|friday|saturday|sunday]");

    }

    @Override
    public void messageReceived(String string, byte[] payload) throws Exception {

        if (string.startsWith(getContract().INTENT_DATE_TIME_SET)) {
            DateTimeParameter parameter = getMapper().readValue(payload, DateTimeParameter.class);
            getDevice().setDateTime(parameter);
        }

        if (string.startsWith(getContract().INTENT_OFFSET)) {
            Byte offset = getMapper().readValue(payload, Byte.class);
            getDevice().setOffset(offset);
        }
        if (string.startsWith(getContract().INTENT_DATE_TIME_CALLBACK_PERIOD)) {
            Long callback = getMapper().readValue(payload, Long.class);
            getDevice().setDateTimeCallbackPeriod(callback);
        }
        if (string.startsWith(getContract().INTENT_ALARM)) {
            AlarmParamter alarm = getMapper().readValue(payload, AlarmParamter.class);
            getDevice().setAlarm(alarm);
        }
    }

    @Override
    public void dateTimeChanged(DateTimeParameter dateTimeParameter) {
        publishStatus(getContract().STATUS_DATE_TIME, dateTimeParameter);
    }

    @Override
    public void offsetChanged(byte offset) {
        publishStatus(getContract().STATUS_OFFSET, offset);
    }

    @Override
    public void dateTimeCallbackPeriodChanged(long period) {
        publishStatus(getContract().STATUS_DATE_TIME_CALLBACK_PERIOD, period);
    }

    @Override
    public void alarmChanged(AlarmParamter alarmParameter) {
        publishStatus(getContract().STATUS_ALARM, alarmParameter);
    }

    @Override
    public void alarm(int year, short month, short day, short hour, short minute, short second, short centisecond, short weekday, long timestamp) {
        publishEvent(getContract().EVENT_ALARM, new DateTimeParameter(year, month, day, hour, minute, second, centisecond, weekday), timestamp);
    }

    @Override
    public void dateTime(int year, short month, short day, short hour, short minute, short second, short centisecond, short weekday, long timestamp) {
        publishEvent(getContract().EVENT_DATE_TIME, new DateTimeParameter(year, month, day, hour, minute, second, centisecond, weekday), timestamp);
    }

}
