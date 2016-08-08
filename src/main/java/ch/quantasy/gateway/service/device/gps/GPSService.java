/*
 *   "TiMqWay"
 *
 *    TiMqWay(tm): A gateway to provide an MQTT-View for the Tinkerforge(tm) world (Tinkerforge-MQTT-Gateway).
 *
 *    Copyright (c) 2015 Bern University of Applied Sciences (BFH),
 *    Research Institute for Security in the Information Society (RISIS), Wireless Communications & Secure Internet of Things (WiCom & SIoT),
 *    Quellgasse 21, CH-2501 Biel, Switzerland
 *
 *    Licensed under Dual License consisting of:
 *    1. GNU Affero General Public License (AGPL) v3
 *    and
 *    2. Commercial license
 *
 *
 *    1. This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Affero General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Affero General Public License for more details.
 *
 *     You should have received a copy of the GNU Affero General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 *    2. Licensees holding valid commercial licenses for TiMqWay may use this file in
 *     accordance with the commercial license agreement provided with the
 *     Software or, alternatively, in accordance with the terms contained in
 *     a written agreement between you and Bern University of Applied Sciences (BFH),
 *     Research Institute for Security in the Information Society (RISIS), Wireless Communications & Secure Internet of Things (WiCom & SIoT),
 *     Quellgasse 21, CH-2501 Biel, Switzerland.
 *
 *
 *     For further information contact <e-mail: reto.koenig@bfh.ch>
 *
 *
 */
package ch.quantasy.gateway.service.device.gps;

import ch.quantasy.gateway.service.device.AbstractDeviceService;
import ch.quantasy.tinkerforge.device.gps.GPSDevice;
import ch.quantasy.tinkerforge.device.gps.GPSDeviceCallback;
import ch.quantasy.tinkerforge.device.gps.RestartType;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import java.net.URI;

/**
 *
 * @author reto
 */
public class GPSService extends AbstractDeviceService<GPSDevice, GPSServiceContract> implements GPSDeviceCallback {

    public GPSService(GPSDevice device, URI mqttURI) throws MqttException {

        super(mqttURI, device, new GPSServiceContract(device));
        addDescription(getServiceContract().INTENT_ALTITUDE_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        addDescription(getServiceContract().INTENT_COORDINATES_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        addDescription(getServiceContract().INTENT_DATE_TIME_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        addDescription(getServiceContract().STATUS_DATE_TIME_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");

        addDescription(getServiceContract().INTENT_MOTION_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        addDescription(getServiceContract().STATUS_MOTION_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        addDescription(getServiceContract().STATUS_ALTITUDE_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        addDescription(getServiceContract().STATUS_COORDINATES_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");

        addDescription(getServiceContract().EVENT_ALTITUDE, "timestamp: [0.." + Long.MAX_VALUE + "]\n altitude: [" + Integer.MIN_VALUE + ".." + Integer.MAX_VALUE + "]\n geoidalSeparation: [" + Integer.MIN_VALUE + ".." + Integer.MAX_VALUE + "]");
        addDescription(getServiceContract().EVENT_DATE_TIME, "timestamp: [0.." + Long.MAX_VALUE + "]\n date: [[d|dd]mmyy]\n time: [hhmmssxxx]");
        addDescription(getServiceContract().EVENT_MOTION, "timestamp: [0.." + Long.MAX_VALUE + "]\n course: [0..36000]\n speed: [0.." + Long.MAX_VALUE + "]");
        addDescription(getServiceContract().EVENT_STATE, "timestamp: [0.." + Long.MAX_VALUE + "]\n fix: [1|2|3]]\n satellitesView: [0.." + Short.MAX_VALUE + "]\n satellitesUsed: [0.." + Short.MAX_VALUE + "]");
        addDescription(getServiceContract().EVENT_COORDINATES, "timestamp: [0.." + Long.MAX_VALUE + "]\n latitude: [" + Long.MIN_VALUE + ".." + Long.MAX_VALUE + "]\n ns: ['N'|'S']\n longitude: [" + Long.MIN_VALUE + ".." + Long.MAX_VALUE + "]\n ew: ['E'|'W']\n pdop: [" + Integer.MIN_VALUE + ".." + Integer.MAX_VALUE + "]\n hdop: [" + Integer.MIN_VALUE + ".." + Integer.MAX_VALUE + "]\n vdop: [" + Integer.MIN_VALUE + ".." + Integer.MAX_VALUE + "]\n epe: [" + Integer.MIN_VALUE + ".." + Integer.MAX_VALUE + "]\n");
    }

    @Override
    public void messageArrived(String string, byte[] payload) throws Exception {

        if (string.startsWith(getServiceContract().INTENT_ALTITUDE_CALLBACK_PERIOD)) {

            Long period = getMapper().readValue(payload, Long.class);
            getDevice().setAltitudeCallbackPeriod(period);
        }
        if (string.startsWith(getServiceContract().INTENT_COORDINATES_CALLBACK_PERIOD)) {

            Long period = getMapper().readValue(payload, Long.class);
            getDevice().setCoordinatesCallbackPeriod(period);
        }

        if (string.startsWith(getServiceContract().INTENT_DATE_TIME_CALLBACK_PERIOD)) {

            Long period = getMapper().readValue(payload, Long.class);
            getDevice().setDateTimeCallbackPeriod(period);
        }

        if (string.startsWith(getServiceContract().INTENT_MOTION_CALLBACK_PERIOD)) {

            Long period = getMapper().readValue(payload, Long.class);
            getDevice().setMotionCallbackPeriod(period);
        }
        if (string.startsWith(getServiceContract().INTENT_STATE_CALLBACK_PERIOD)) {
            Long period = getMapper().readValue(payload, Long.class);
            getDevice().setStatusCallbackPeriod(period);
        }
        if (string.startsWith(getServiceContract().INTENT_RESTART)) {
            RestartType type = getMapper().readValue(payload, RestartType.class);
            getDevice().restart(type);
        }

    }

    @Override
    public void altitudeCallbackPeriodChanged(long period) {
        super.addStatus(getServiceContract().STATUS_ALTITUDE_CALLBACK_PERIOD, period);
    }

    @Override
    public void coordinatesCallbackPeriodChanged(Long coordinatesCallbackPeriod) {
        super.addStatus(getServiceContract().STATUS_COORDINATES_CALLBACK_PERIOD, coordinatesCallbackPeriod);
    }

    @Override
    public void dateTimeCallbackPeriodChanged(Long dateTimeCallbackPeriod) {
        super.addStatus(getServiceContract().STATUS_DATE_TIME_CALLBACK_PERIOD, dateTimeCallbackPeriod);
    }

    @Override
    public void motionCallbackPeriodChanged(Long motionCallbackPeriod) {
        super.addStatus(getServiceContract().STATUS_MOTION_CALLBACK_PERIOD, motionCallbackPeriod);
    }

    @Override
    public void statusCallbackPeriodChanged(Long statusCallbackPeriod) {
        super.addStatus(getServiceContract().STATUS_STATE_CALLBACK_PERIOD, statusCallbackPeriod);
    }

    @Override
    public void altitude(int altitude, int geoidalSeparation) {
        super.addEvent(getServiceContract().EVENT_ALTITUDE, new AltitudeEvent(altitude, geoidalSeparation));
    }

    @Override
    public void coordinates(long latitude, char ns, long longitude, char ew, int pdop, int hdop, int vdop, int epe) {
        super.addEvent(getServiceContract().EVENT_COORDINATES, new CoordinatesEvent(latitude, ns, longitude, ew, pdop, hdop, vdop, epe));
    }

    @Override
    public void dateTime(long date, long time) {
        super.addEvent(getServiceContract().EVENT_DATE_TIME, new DateTimeEvent(date, time));
    }

    @Override
    public void motion(long course, long speed) {
        super.addEvent(getServiceContract().EVENT_MOTION, new MotionEvent(course, speed));
    }

    @Override
    public void status(short fix, short satellitesView, short satellitesUsed) {
        super.addEvent(getServiceContract().EVENT_STATE, new StatusEvent(fix, satellitesView, satellitesUsed));
    }

    public static class StatusEvent {

        protected long timestamp;
        protected short fix;
        protected short satellitesView;
        protected short satellitesUsed;

        public StatusEvent(short fix, short satellitesView, short satellitesUsed) {
            this(fix, satellitesView, satellitesUsed, System.currentTimeMillis());
        }

        public StatusEvent(short fix, short satellitesView, short satellitesUsed, long timeStamp) {
            this.fix = fix;
            this.satellitesView = satellitesView;
            this.satellitesUsed = satellitesUsed;
            this.timestamp = timeStamp;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public short getFix() {
            return fix;
        }

        public short getSatellitesUsed() {
            return satellitesUsed;
        }

        public short getSatellitesView() {
            return satellitesView;
        }

    }

    public static class AltitudeEvent {

        protected long timestamp;
        protected int altitude;
        protected int geoidalSeparation;

        public AltitudeEvent(int altitude, int geoidalSeparation) {
            this(altitude, geoidalSeparation, System.currentTimeMillis());
        }

        public AltitudeEvent(int altitude, int geoidalSeparation, long timeStamp) {
            this.altitude = altitude;
            this.geoidalSeparation = geoidalSeparation;
            this.timestamp = timeStamp;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public int getAltitude() {
            return altitude;
        }

        public int getGeoidalSeparation() {
            return geoidalSeparation;
        }
    }

    public static class MotionEvent {

        protected long timestamp;
        protected long course;
        protected long speed;

        public MotionEvent(long course, long speed) {
            this(course, speed, System.currentTimeMillis());
        }

        public MotionEvent(long course, long speed, long timeStamp) {
            this.course = course;
            this.speed = speed;
            this.timestamp = timeStamp;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public long getCourse() {
            return course;
        }

        public long getSpeed() {
            return speed;
        }
    }

    public static class DateTimeEvent {

        protected long timestamp;
        protected long date;
        protected long time;

        public DateTimeEvent(long date, long time) {
            this(date, time, System.currentTimeMillis());
        }

        public DateTimeEvent(long date, long time, long timeStamp) {
            this.date = date;
            this.time = time;
            this.timestamp = timeStamp;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public long getDate() {
            return date;
        }

        public long getTime() {
            return time;
        }
    }

    public static class CoordinatesEvent {

        protected long timestamp;
        protected long latitude;
        protected char ns;
        protected long longitude;
        protected char ew;
        protected int pdop;
        protected int hdop;
        protected int vdop;
        protected int epe;

        public CoordinatesEvent(long latitude, char ns, long longitude, char ew, int pdop, int hdop, int vdop, int epe) {
            this(latitude, ns, longitude, ew, pdop, hdop, vdop, epe, System.currentTimeMillis());
        }

        public CoordinatesEvent(long latitude, char ns, long longitude, char ew, int pdop, int hdop, int vdop, int epe, long timeStamp) {
            this.latitude = latitude;
            this.ns = ns;
            this.longitude = longitude;
            this.ew = ew;
            this.pdop = pdop;
            this.hdop = hdop;
            this.vdop = vdop;
            this.epe = epe;
            this.timestamp = timeStamp;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public int getEpe() {
            return epe;
        }

        public char getEw() {
            return ew;
        }

        public int getHdop() {
            return hdop;
        }

        public long getLatitude() {
            return latitude;
        }

        public long getLongitude() {
            return longitude;
        }

        public char getNs() {
            return ns;
        }

        public int getPdop() {
            return pdop;
        }

        public int getVdop() {
            return vdop;
        }

    }

}
