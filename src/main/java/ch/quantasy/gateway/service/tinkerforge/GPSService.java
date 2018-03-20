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
package ch.quantasy.gateway.service.tinkerforge;

import ch.quantasy.gateway.binding.tinkerforge.gps.GPSServiceContract;
import ch.quantasy.gateway.binding.tinkerforge.gps.AltitudeEvent;
import ch.quantasy.gateway.binding.tinkerforge.gps.CoordinatesEvent;
import ch.quantasy.gateway.binding.tinkerforge.gps.DateTimeEvent;
import ch.quantasy.gateway.binding.tinkerforge.gps.MotionEvent;
import ch.quantasy.gateway.binding.tinkerforge.gps.StatusEvent;
import ch.quantasy.gateway.service.tinkerforge.AbstractDeviceService;
import ch.quantasy.tinkerforge.device.gps.GPSDevice;
import ch.quantasy.tinkerforge.device.gps.GPSDeviceCallback;
import ch.quantasy.gateway.binding.tinkerforge.gps.AltitudeCallbackPeriodStatus;
import ch.quantasy.gateway.binding.tinkerforge.gps.CoordinatesCallbackPeriodStatus;
import ch.quantasy.gateway.binding.tinkerforge.gps.DateTimeCallbackPeriodStatus;
import ch.quantasy.gateway.binding.tinkerforge.gps.MotionCallbackPeriodStatus;
import ch.quantasy.gateway.binding.tinkerforge.gps.StatusCallbackPeriodStatus;
import org.eclipse.paho.client.mqttv3.MqttException;
import java.net.URI;

/**
 *
 * @author reto
 */
public class GPSService extends AbstractDeviceService<GPSDevice, GPSServiceContract> implements GPSDeviceCallback {

    public GPSService(GPSDevice device, URI mqttURI) throws MqttException {

        super(mqttURI, device, new GPSServiceContract(device));
    }

    @Override
    public void altitudeCallbackPeriodChanged(long period) {
        super.readyToPublishStatus(getContract().STATUS_ALTITUDE_CALLBACK_PERIOD, new AltitudeCallbackPeriodStatus(period));
    }

    @Override
    public void coordinatesCallbackPeriodChanged(Long coordinatesCallbackPeriod) {
        super.readyToPublishStatus(getContract().STATUS_COORDINATES_CALLBACK_PERIOD, new CoordinatesCallbackPeriodStatus(coordinatesCallbackPeriod));
    }

    @Override
    public void dateTimeCallbackPeriodChanged(Long dateTimeCallbackPeriod) {
        super.readyToPublishStatus(getContract().STATUS_DATE_TIME_CALLBACK_PERIOD, new DateTimeCallbackPeriodStatus(dateTimeCallbackPeriod));
    }

    @Override
    public void motionCallbackPeriodChanged(Long motionCallbackPeriod) {
        super.readyToPublishStatus(getContract().STATUS_MOTION_CALLBACK_PERIOD, new MotionCallbackPeriodStatus(motionCallbackPeriod));
    }

    @Override
    public void statusCallbackPeriodChanged(Long statusCallbackPeriod) {
        super.readyToPublishStatus(getContract().STATUS_STATE_CALLBACK_PERIOD, new StatusCallbackPeriodStatus(statusCallbackPeriod));
    }

    @Override
    public void altitude(int altitude, int geoidalSeparation) {
        super.readyToPublishEvent(getContract().EVENT_ALTITUDE, new AltitudeEvent(altitude, geoidalSeparation));
    }

    @Override
    public void coordinates(long latitude, char ns, long longitude, char ew, int pdop, int hdop, int vdop, int epe) {
        super.readyToPublishEvent(getContract().EVENT_COORDINATES, new CoordinatesEvent(latitude, ns, longitude, ew, pdop, hdop, vdop, epe));
    }

    @Override
    public void dateTime(long date, long time) {
        super.readyToPublishEvent(getContract().EVENT_DATE_TIME, new DateTimeEvent(date, time));
    }

    @Override
    public void motion(long course, long speed) {
        super.readyToPublishEvent(getContract().EVENT_MOTION, new MotionEvent(course, speed));
    }

    @Override
    public void status(short fix, short satellitesView, short satellitesUsed) {
        super.readyToPublishEvent(getContract().EVENT_STATE, new StatusEvent(fix, satellitesView, satellitesUsed));
    }
}
