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
package ch.quantasy.gateway.service.tinkerforge.realTimeClock;

import ch.quantasy.gateway.message.realTimeClock.DateTimeEvent;
import ch.quantasy.gateway.message.realTimeClock.AlarmParamter;
import ch.quantasy.gateway.message.realTimeClock.DateTimeParameter;
import ch.quantasy.gateway.message.realTimeClock.RealTimeClockIntent;
import ch.quantasy.gateway.message.gps.DateTimeCallbackPeriodStatus;
import ch.quantasy.gateway.message.realTimeClock.AlarmParameterStatus;
import ch.quantasy.gateway.message.realTimeClock.DateTimeParameterStatus;
import ch.quantasy.gateway.message.realTimeClock.OffsetStatus;
import ch.quantasy.gateway.service.tinkerforge.AbstractDeviceService;
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
    }

    @Override
    public void dateTimeChanged(DateTimeParameter dateTimeParameter) {
        readyToPublishStatus(getContract().STATUS_DATE_TIME, new DateTimeParameterStatus(dateTimeParameter));
    }

    @Override
    public void offsetChanged(byte offset) {
        readyToPublishStatus(getContract().STATUS_OFFSET, new OffsetStatus(offset));
    }

    @Override
    public void dateTimeCallbackPeriodChanged(long period) {
        readyToPublishStatus(getContract().STATUS_DATE_TIME_CALLBACK_PERIOD, new DateTimeCallbackPeriodStatus(period));
    }

    @Override
    public void alarmChanged(AlarmParamter alarmParameter) {
        readyToPublishStatus(getContract().STATUS_ALARM, new AlarmParameterStatus(alarmParameter));
    }

    @Override
    public void alarm(int year, short month, short day, short hour, short minute, short second, short centisecond, short weekday, long timestamp) {
        readyToPublishEvent(getContract().EVENT_ALARM, new DateTimeEvent(true,new DateTimeParameter(year, month, day, hour, minute, second, centisecond, weekday),timestamp));
    }

    @Override
    public void dateTime(int year, short month, short day, short hour, short minute, short second, short centisecond, short weekday, long timestamp) {
        readyToPublishEvent(getContract().EVENT_DATE_TIME, new DateTimeEvent(new DateTimeParameter(year, month, day, hour, minute, second, centisecond, weekday),timestamp));
    }

}
