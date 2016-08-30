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
package ch.quantasy.gateway.service.device.IMUV2;

import ch.quantasy.gateway.service.device.AbstractDeviceService;
import ch.quantasy.tinkerforge.device.IMUV2.IMUV2Device;
import ch.quantasy.tinkerforge.device.IMUV2.IMUV2DeviceCallback;
import org.eclipse.paho.client.mqttv3.MqttException;
import java.net.URI;

/**
 *
 * @author reto
 */
public class IMUV2Service extends AbstractDeviceService<IMUV2Device, IMUV2ServiceContract> implements IMUV2DeviceCallback {

    public IMUV2Service(IMUV2Device device, URI mqttURI) throws MqttException {

        super(mqttURI, device, new IMUV2ServiceContract(device));
        addDescription(getServiceContract().INTENT_ACCELERATION_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        addDescription(getServiceContract().INTENT_ALL_DATA_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        addDescription(getServiceContract().INTENT_ANGULAR_VELOCITY_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        addDescription(getServiceContract().INTENT_GRAVITY_VECTOR_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        addDescription(getServiceContract().INTENT_LINEAR_ACCELERATION_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        addDescription(getServiceContract().INTENT_MAGNETIC_FIELD_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        addDescription(getServiceContract().INTENT_ORIENTATION_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        addDescription(getServiceContract().INTENT_QUATERNION_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        addDescription(getServiceContract().INTENT_TEMPERATURE_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        addDescription(getServiceContract().INTENT_LEDS, "true|false]");
        addDescription(getServiceContract().INTENT_STATUS_LED, "true|false]");

        addDescription(getServiceContract().STATUS_ACCELERATION_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        addDescription(getServiceContract().STATUS_ALL_DATA_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        addDescription(getServiceContract().STATUS_ANGULAR_VELOCITY_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        addDescription(getServiceContract().STATUS_GRAVITY_VECTOR_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        addDescription(getServiceContract().STATUS_LINEAR_ACCELERATION_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        addDescription(getServiceContract().STATUS_MAGNETIC_FIELD_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        addDescription(getServiceContract().STATUS_ORIENTATION_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        addDescription(getServiceContract().STATUS_QUATERNION_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        addDescription(getServiceContract().STATUS_TEMPERATURE_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        addDescription(getServiceContract().STATUS_STATUS_LED, "[true|false]");
        addDescription(getServiceContract().STATUS_LEDS, "[true|false]");

        addDescription(getServiceContract().EVENT_ACCELERATION, "timestamp: [0.." + Long.MAX_VALUE + "]\n x: [" + Short.MIN_VALUE + ".." + Short.MAX_VALUE + "]\n y: [" + Short.MIN_VALUE + ".." + Short.MAX_VALUE + "]\n z: [" + Short.MIN_VALUE + ".." + Short.MAX_VALUE + "]");
        addDescription(getServiceContract().EVENT_ANGULAR_VELOCITY, "timestamp: [0.." + Long.MAX_VALUE + "]\n x: [" + Short.MIN_VALUE + ".." + Short.MAX_VALUE + "]\n y: [" + Short.MIN_VALUE + ".." + Short.MAX_VALUE + "]\n z: [" + Short.MIN_VALUE + ".." + Short.MAX_VALUE + "]");
        addDescription(getServiceContract().EVENT_GRAVITY_VECTOR, "timestamp: [0.." + Long.MAX_VALUE + "]\n x: [" + Short.MIN_VALUE + ".." + Short.MAX_VALUE + "]\n y: [" + Short.MIN_VALUE + ".." + Short.MAX_VALUE + "]\n z: [" + Short.MIN_VALUE + ".." + Short.MAX_VALUE + "]");
        addDescription(getServiceContract().EVENT_LINEAR_ACCELERATION, "timestamp: [0.." + Long.MAX_VALUE + "]\n x: [" + Short.MIN_VALUE + ".." + Short.MAX_VALUE + "]\n y: [" + Short.MIN_VALUE + ".." + Short.MAX_VALUE + "]\n z: [" + Short.MIN_VALUE + ".." + Short.MAX_VALUE + "]");
        addDescription(getServiceContract().EVENT_MAGNETIC_FIELD, "timestamp: [0.." + Long.MAX_VALUE + "]\n x: [" + Short.MIN_VALUE + ".." + Short.MAX_VALUE + "]\n y: [" + Short.MIN_VALUE + ".." + Short.MAX_VALUE + "]\n z: [" + Short.MIN_VALUE + ".." + Short.MAX_VALUE + "]");
        addDescription(getServiceContract().EVENT_ORIENTATION, "timestamp: [0.." + Long.MAX_VALUE + "]\n heading: [" + Short.MIN_VALUE + ".." + Short.MAX_VALUE + "]\n roll: [" + Short.MIN_VALUE + ".." + Short.MAX_VALUE + "]\n pitch: [" + Short.MIN_VALUE + ".." + Short.MAX_VALUE + "]");
        addDescription(getServiceContract().EVENT_QUATERNION, "timestamp: [0.." + Long.MAX_VALUE + "]\n w: [" + Short.MIN_VALUE + ".." + Short.MAX_VALUE + "]\n x: [" + Short.MIN_VALUE + ".." + Short.MAX_VALUE + "]\n y: [" + Short.MIN_VALUE + ".." + Short.MAX_VALUE + "]\n z: [" + Short.MIN_VALUE + ".." + Short.MAX_VALUE + "]");
        addDescription(getServiceContract().EVENT_TEMPERATURE, "timestamp: [0.." + Long.MAX_VALUE + "]\n value: [" + Byte.MIN_VALUE + ".." + Byte.MAX_VALUE + "]");
        addDescription(getServiceContract().EVENT_ALL_DATA, "timestamp: [0.." + Long.MAX_VALUE + "]\n @acceleration \n @magneticField \n @angularVelocity \n @orientation \n @quaternion \n @linearAcceleration \n @gravityVector \n @temperature \n calibrationStatus: [0..255]");

    }

    @Override
    public void messageArrived(String string, byte[] payload) throws Exception {

        if (string.startsWith(getServiceContract().INTENT_ACCELERATION_CALLBACK_PERIOD)) {

            Long period = getMapper().readValue(payload, Long.class);
            getDevice().setAccelerationPeriod(period);
        }
        if (string.startsWith(getServiceContract().INTENT_ALL_DATA_CALLBACK_PERIOD)) {
            Long period = getMapper().readValue(payload, Long.class);
            getDevice().setAllDataPeriod(period);
        }
        if (string.startsWith(getServiceContract().INTENT_ANGULAR_VELOCITY_CALLBACK_PERIOD)) {
            Long period = getMapper().readValue(payload, Long.class);
            getDevice().setAngularVelocityPeriod(period);
        }
        if (string.startsWith(getServiceContract().INTENT_GRAVITY_VECTOR_CALLBACK_PERIOD)) {
            Long period = getMapper().readValue(payload, Long.class);
            getDevice().setGravityVectorPeriod(period);
        }
        if (string.startsWith(getServiceContract().INTENT_LINEAR_ACCELERATION_CALLBACK_PERIOD)) {
            Long period = getMapper().readValue(payload, Long.class);
            getDevice().setLinearAccelerationPeriod(period);
        }
        if (string.startsWith(getServiceContract().INTENT_MAGNETIC_FIELD_CALLBACK_PERIOD)) {
            Long period = getMapper().readValue(payload, Long.class);
            getDevice().setMagneticFieldPeriod(period);
        }
        if (string.startsWith(getServiceContract().INTENT_ORIENTATION_CALLBACK_PERIOD)) {
            Long period = getMapper().readValue(payload, Long.class);
            getDevice().setOrientationPeriod(period);
        }
        if (string.startsWith(getServiceContract().INTENT_QUATERNION_CALLBACK_PERIOD)) {
            Long period = getMapper().readValue(payload, Long.class);
            getDevice().setQuaternionPeriod(period);
        }
        if (string.startsWith(getServiceContract().INTENT_TEMPERATURE_CALLBACK_PERIOD)) {
            Long period = getMapper().readValue(payload, Long.class);
            getDevice().setTemperaturePeriod(period);
        }
        if (string.startsWith(getServiceContract().INTENT_LEDS)) {
            Boolean enabled = getMapper().readValue(payload, Boolean.class);
            getDevice().setLEDs(enabled);
        }
        if (string.startsWith(getServiceContract().INTENT_STATUS_LED)) {
            Boolean enabled = getMapper().readValue(payload, Boolean.class);
            getDevice().setStatusLED(enabled);
        }

    }

    @Override
    public void accelerationPeriodChanged(Long period) {
        addStatus(getServiceContract().STATUS_ACCELERATION_CALLBACK_PERIOD, period);

    }

    @Override
    public void allDataPeriodChanged(Long period) {
        addStatus(getServiceContract().STATUS_ALL_DATA_CALLBACK_PERIOD, period);
    }

    @Override
    public void angularVelocityPeriodChanged(Long period) {
        addStatus(getServiceContract().STATUS_ANGULAR_VELOCITY_CALLBACK_PERIOD, period);
    }

    @Override
    public void gravityVectorPeriodChanged(Long period) {
        addStatus(getServiceContract().STATUS_GRAVITY_VECTOR_CALLBACK_PERIOD, period);
    }

    @Override
    public void linearAccelerationPeriodChanged(Long period) {
        addStatus(getServiceContract().STATUS_LINEAR_ACCELERATION_CALLBACK_PERIOD, period);
    }

    @Override
    public void magneticFieldPeriodChanged(Long period) {
        addStatus(getServiceContract().STATUS_MAGNETIC_FIELD_CALLBACK_PERIOD, period);
    }

    @Override
    public void orientationPeriodChanged(Long period) {
        addStatus(getServiceContract().STATUS_ORIENTATION_CALLBACK_PERIOD, period);
    }

    @Override
    public void quaternionPeriodChanged(Long period) {
        addStatus(getServiceContract().STATUS_QUATERNION_CALLBACK_PERIOD, period);
    }

    @Override
    public void temperaturePeriodChanged(Long period) {
        addStatus(getServiceContract().STATUS_TEMPERATURE_CALLBACK_PERIOD, period);
    }

    @Override
    public void statusLEDChanged(Boolean isEnabled) {
        addStatus(getServiceContract().STATUS_STATUS_LED, isEnabled);
    }

    @Override
    public void LEDsChanged(Boolean areEnabled) {
        addStatus(getServiceContract().STATUS_LED, areEnabled);
    }

    @Override
    public void acceleration(short s, short s1, short s2) {
        addEvent(getServiceContract().EVENT_ACCELERATION, new AccelerationEvent(s, s1, s2));
    }

    @Override
    public void allData(short[] shorts, short[] shorts1, short[] shorts2, short[] shorts3, short[] shorts4, short[] shorts5, short[] shorts6, byte b, short s) {
        addEvent(getServiceContract().EVENT_ALL_DATA, new AllDataEvent(new AccelerationEvent(shorts[0], shorts[1], shorts[2]),
                new MagneticFieldEvent(shorts1[0], shorts1[1], shorts1[2]),
                new AngularVelocityEvent(shorts2[0], shorts2[1], shorts2[2]),
                new OrientationEvent(shorts3[0], shorts3[1], shorts3[2]),
                new QuaternionEvent(shorts4[0], shorts4[1], shorts4[2], shorts4[3]),
                new LinearAccelerationEvent(shorts5[0], shorts5[1], shorts5[2]),
                new GravityVectorEvent(shorts6[0], shorts6[1], shorts6[2]),
                new TemperatureEvent(b),
                s));
    }

    @Override
    public void angularVelocity(short s, short s1, short s2) {
        addEvent(getServiceContract().EVENT_ANGULAR_VELOCITY, new AngularVelocityEvent(s, s1, s2));
    }

    @Override
    public void gravityVector(short s, short s1, short s2) {
        addEvent(getServiceContract().EVENT_GRAVITY_VECTOR, new GravityVectorEvent(s, s1, s2));
    }

    @Override
    public void linearAcceleration(short s, short s1, short s2) {
        addEvent(getServiceContract().EVENT_LINEAR_ACCELERATION, new LinearAccelerationEvent(s, s1, s2));
    }

    @Override
    public void magneticField(short s, short s1, short s2) {
        addEvent(getServiceContract().EVENT_MAGNETIC_FIELD, new MagneticFieldEvent(s, s1, s2));
    }

    @Override
    public void orientation(short s, short s1, short s2) {
        addEvent(getServiceContract().EVENT_ORIENTATION, new OrientationEvent(s, s1, s2));
    }

    @Override
    public void quaternion(short s, short s1, short s2, short s3) {
        addEvent(getServiceContract().EVENT_QUATERNION, new QuaternionEvent(s, s1, s2, s3));
    }

    @Override
    public void temperature(byte b) {
        addEvent(getServiceContract().EVENT_TEMPERATURE, new TemperatureEvent(b));
    }

    public static class AccelerationEvent {

        private long timestamp;
        private short x;
        private short y;
        private short z;

        public AccelerationEvent(short x, short y, short z) {
            this(x, y, z, System.currentTimeMillis());
        }

        public AccelerationEvent(short x, short y, short z, long timestamp) {
            this.timestamp = timestamp;
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public short getX() {
            return x;
        }

        public short getY() {
            return y;
        }

        public short getZ() {
            return z;
        }

    }

    public static class AngularVelocityEvent {

        private long timestamp;
        private short x;
        private short y;
        private short z;

        public AngularVelocityEvent(short x, short y, short z) {
            this(x, y, z, System.currentTimeMillis());
        }

        public AngularVelocityEvent(short x, short y, short z, long timestamp) {
            this.timestamp = timestamp;
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public short getX() {
            return x;
        }

        public short getY() {
            return y;
        }

        public short getZ() {
            return z;
        }

    }

    public static class GravityVectorEvent {

        private long timestamp;
        private short x;
        private short y;
        private short z;

        public GravityVectorEvent(short x, short y, short z) {
            this(x, y, z, System.currentTimeMillis());
        }

        public GravityVectorEvent(short x, short y, short z, long timestamp) {
            this.timestamp = timestamp;
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public short getX() {
            return x;
        }

        public short getY() {
            return y;
        }

        public short getZ() {
            return z;
        }

    }

    public static class LinearAccelerationEvent {

        private long timestamp;
        private short x;
        private short y;
        private short z;

        public LinearAccelerationEvent(short x, short y, short z) {
            this(x, y, z, System.currentTimeMillis());
        }

        public LinearAccelerationEvent(short x, short y, short z, long timestamp) {
            this.timestamp = timestamp;
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public short getX() {
            return x;
        }

        public short getY() {
            return y;
        }

        public short getZ() {
            return z;
        }

    }

    public static class MagneticFieldEvent {

        private long timestamp;
        private short x;
        private short y;
        private short z;

        public MagneticFieldEvent(short x, short y, short z) {
            this(x, y, z, System.currentTimeMillis());
        }

        public MagneticFieldEvent(short x, short y, short z, long timestamp) {
            this.timestamp = timestamp;
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public short getX() {
            return x;
        }

        public short getY() {
            return y;
        }

        public short getZ() {
            return z;
        }

    }

    public static class OrientationEvent {

        private long timestamp;
        private short heading;
        private short roll;
        private short pitch;

        public OrientationEvent(short heading, short roll, short pitch) {
            this(heading, roll, pitch, System.currentTimeMillis());
        }

        public OrientationEvent(short heading, short roll, short pitch, long timestamp) {
            this.timestamp = timestamp;
            this.heading = heading;
            this.roll = roll;
            this.pitch = pitch;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public short getHeading() {
            return heading;
        }

        public short getRoll() {
            return roll;
        }

        public short getPitch() {
            return pitch;
        }

    }

    public static class QuaternionEvent {

        private long timestamp;
        private short w;
        private short x;
        private short y;
        private short z;

        public QuaternionEvent(short w, short x, short y, short z) {
            this(w, x, y, z, System.currentTimeMillis());
        }

        public QuaternionEvent(short w, short x, short y, short z, long timestamp) {
            this.timestamp = timestamp;
            this.w = w;
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public short getW() {
            return w;
        }

        public short getX() {
            return x;
        }

        public short getY() {
            return y;
        }

        public short getZ() {
            return z;
        }

    }

    public static class TemperatureEvent {

        private long timestamp;
        private byte value;

        public TemperatureEvent(byte value) {
            this(value, System.currentTimeMillis());
        }

        public TemperatureEvent(byte value, long timestamp) {
            this.timestamp = timestamp;
            this.value = value;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public byte getValue() {
            return value;
        }
    }

    public static class AllDataEvent {

        private long timestamp;

        private AccelerationEvent acceleration;
        private MagneticFieldEvent magneticField;
        private AngularVelocityEvent angularVelocity;
        private OrientationEvent orientation;
        private QuaternionEvent quaternion;
        private LinearAccelerationEvent linearAcceleration;
        private GravityVectorEvent gravityVector;
        private TemperatureEvent temperature;
        private short calibrationStatus;

        public AllDataEvent(AccelerationEvent acceleration, MagneticFieldEvent magneticField, AngularVelocityEvent angularVelocity, OrientationEvent orientation, QuaternionEvent quaternion, LinearAccelerationEvent linearAcceleration, GravityVectorEvent gravityVector, TemperatureEvent temperature, short calibrationStatus) {
            this(acceleration, magneticField, angularVelocity, orientation, quaternion, linearAcceleration, gravityVector, temperature, calibrationStatus, System.currentTimeMillis());
        }

        public AllDataEvent(AccelerationEvent acceleration, MagneticFieldEvent magneticField, AngularVelocityEvent angularVelocity, OrientationEvent orientation, QuaternionEvent quaternion, LinearAccelerationEvent linearAcceleration, GravityVectorEvent gravityVector, TemperatureEvent temperature, short calibrationStatus, long timestamp) {
            this.timestamp = timestamp;
            this.acceleration = acceleration;
            this.magneticField = magneticField;
            this.angularVelocity = angularVelocity;
            this.orientation = orientation;
            this.quaternion = quaternion;
            this.linearAcceleration = linearAcceleration;
            this.gravityVector = gravityVector;
            this.temperature = temperature;
            this.calibrationStatus = calibrationStatus;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public AccelerationEvent getAcceleration() {
            return acceleration;
        }

        public MagneticFieldEvent getMagneticField() {
            return magneticField;
        }

        public AngularVelocityEvent getAngularVelocity() {
            return angularVelocity;
        }

        public OrientationEvent getOrientation() {
            return orientation;
        }

        public QuaternionEvent getQuaternion() {
            return quaternion;
        }

        public LinearAccelerationEvent getLinearAcceleration() {
            return linearAcceleration;
        }

        public GravityVectorEvent getGravityVector() {
            return gravityVector;
        }

        public TemperatureEvent getTemperature() {
            return temperature;
        }

        public short getCalibrationStatus() {
            return calibrationStatus;
        }

    }

}
