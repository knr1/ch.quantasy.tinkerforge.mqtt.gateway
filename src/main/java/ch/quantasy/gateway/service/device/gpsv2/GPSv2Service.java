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
package ch.quantasy.gateway.service.device.gpsv2;

import ch.quantasy.gateway.message.event.gps.AltitudeEvent;
import ch.quantasy.gateway.message.event.gps.DateTimeEvent;
import ch.quantasy.gateway.message.event.gps.MotionEvent;
import ch.quantasy.gateway.message.event.gpsV2.CoordinatesEvent;
import ch.quantasy.gateway.message.event.gpsV2.StatusEvent;
import ch.quantasy.gateway.service.device.AbstractDeviceService;
import ch.quantasy.gateway.message.intent.gpsv2.FixLEDConfig;
import ch.quantasy.gateway.message.intent.gpsv2.GPSv2Intent;
import ch.quantasy.tinkerforge.device.gpsV2.GPSv2Device;
import ch.quantasy.tinkerforge.device.gpsV2.GPSv2DeviceCallback;
import ch.quantasy.gateway.message.intent.gpsv2.RestartType;
import ch.quantasy.gateway.message.intent.gpsv2.StatusLEDConfig;

import org.eclipse.paho.client.mqttv3.MqttException;
import java.net.URI;

/**
 *
 * @author reto
 */
public class GPSv2Service extends AbstractDeviceService<GPSv2Device, GPSv2ServiceContract> implements GPSv2DeviceCallback {

    public GPSv2Service(GPSv2Device device, URI mqttURI) throws MqttException {

        super(mqttURI, device, new GPSv2ServiceContract(device));
    }

    @Override
    public void altitudeCallbackPeriodChanged(long period) {
        super.publishStatus(getContract().STATUS_ALTITUDE_CALLBACK_PERIOD, period);
    }

    @Override
    public void coordinatesCallbackPeriodChanged(Long coordinatesCallbackPeriod) {
        super.publishStatus(getContract().STATUS_COORDINATES_CALLBACK_PERIOD, coordinatesCallbackPeriod);
    }

    @Override
    public void dateTimeCallbackPeriodChanged(Long dateTimeCallbackPeriod) {
        super.publishStatus(getContract().STATUS_DATE_TIME_CALLBACK_PERIOD, dateTimeCallbackPeriod);
    }

    @Override
    public void motionCallbackPeriodChanged(Long motionCallbackPeriod) {
        super.publishStatus(getContract().STATUS_MOTION_CALLBACK_PERIOD, motionCallbackPeriod);
    }

    @Override
    public void statusCallbackPeriodChanged(Long statusCallbackPeriod) {
        super.publishStatus(getContract().STATUS_STATE_CALLBACK_PERIOD, statusCallbackPeriod);
    }

    @Override
    public void altitude(int altitude, int geoidalSeparation) {
        super.publishEvent(getContract().EVENT_ALTITUDE, new AltitudeEvent(altitude, geoidalSeparation));
    }

    @Override
    public void coordinates(long latitude, char ns, long longitude, char ew) {
        super.publishEvent(getContract().EVENT_COORDINATES, new CoordinatesEvent(latitude, ns, longitude, ew));
    }

    @Override
    public void dateTime(long date, long time) {
        super.publishEvent(getContract().EVENT_DATE_TIME, new DateTimeEvent(date, time));
    }

    @Override
    public void motion(long course, long speed) {
        super.publishEvent(getContract().EVENT_MOTION, new MotionEvent(course, speed));
    }

    @Override
    public void status(boolean fix, int satellitesView) {
        super.publishEvent(getContract().EVENT_STATE, new StatusEvent(fix, satellitesView));
    }

    @Override
    public void fixLEDConfigChanged(FixLEDConfig fixLEDConfig) {
        super.publishStatus(getContract().STATUS_FIX_LED_CONFIG, fixLEDConfig);
    }

    @Override
    public void statusLEDConfigChanged(StatusLEDConfig statusLEDConfig) {
        super.publishStatus(getContract().STATUS_STATE_LED_CONFIG, statusLEDConfig);
    }

    @Override
    public void pulsePerSecond() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
  

    
}
