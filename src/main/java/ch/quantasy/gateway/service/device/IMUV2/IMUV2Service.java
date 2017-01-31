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
import ch.quantasy.tinkerforge.device.imuV2.IMUV2Device;
import ch.quantasy.tinkerforge.device.imuV2.IMUV2DeviceCallback;
import org.eclipse.paho.client.mqttv3.MqttException;
import java.net.URI;

/**
 *
 * @author reto
 */
public class IMUV2Service extends AbstractDeviceService<IMUV2Device, IMUV2ServiceContract> implements IMUV2DeviceCallback {

    public IMUV2Service(IMUV2Device device, URI mqttURI) throws MqttException {

        super(mqttURI, device, new IMUV2ServiceContract(device));
        publishDescription(getContract().INTENT_ACCELERATION_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        publishDescription(getContract().INTENT_ALL_DATA_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        publishDescription(getContract().INTENT_ANGULAR_VELOCITY_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        publishDescription(getContract().INTENT_GRAVITY_VECTOR_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        publishDescription(getContract().INTENT_LINEAR_ACCELERATION_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        publishDescription(getContract().INTENT_MAGNETIC_FIELD_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        publishDescription(getContract().INTENT_ORIENTATION_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        publishDescription(getContract().INTENT_QUATERNION_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        publishDescription(getContract().INTENT_TEMPERATURE_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        publishDescription(getContract().INTENT_LEDS, "true|false]");
        publishDescription(getContract().INTENT_STATUS_LED, "true|false]");
        publishDescription(getContract().INTENT_SENSOR_FUSION_MODE, "[0..1]");


        publishDescription(getContract().STATUS_ACCELERATION_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        publishDescription(getContract().STATUS_ALL_DATA_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        publishDescription(getContract().STATUS_ANGULAR_VELOCITY_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        publishDescription(getContract().STATUS_GRAVITY_VECTOR_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        publishDescription(getContract().STATUS_LINEAR_ACCELERATION_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        publishDescription(getContract().STATUS_MAGNETIC_FIELD_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        publishDescription(getContract().STATUS_ORIENTATION_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        publishDescription(getContract().STATUS_QUATERNION_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        publishDescription(getContract().STATUS_TEMPERATURE_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        publishDescription(getContract().STATUS_STATUS_LED, "[true|false]");
        publishDescription(getContract().STATUS_LEDS, "[true|false]");
        publishDescription(getContract().STATUS_SENSOR_FUSION_MODE, "[0..1]");

        publishDescription(getContract().EVENT_ACCELERATION, "timestamp: [0.." + Long.MAX_VALUE + "]\n x: [" + Short.MIN_VALUE + ".." + Short.MAX_VALUE + "]\n y: [" + Short.MIN_VALUE + ".." + Short.MAX_VALUE + "]\n z: [" + Short.MIN_VALUE + ".." + Short.MAX_VALUE + "]");
        publishDescription(getContract().EVENT_ANGULAR_VELOCITY, "timestamp: [0.." + Long.MAX_VALUE + "]\n x: [" + Short.MIN_VALUE + ".." + Short.MAX_VALUE + "]\n y: [" + Short.MIN_VALUE + ".." + Short.MAX_VALUE + "]\n z: [" + Short.MIN_VALUE + ".." + Short.MAX_VALUE + "]");
        publishDescription(getContract().EVENT_GRAVITY_VECTOR, "timestamp: [0.." + Long.MAX_VALUE + "]\n x: [" + Short.MIN_VALUE + ".." + Short.MAX_VALUE + "]\n y: [" + Short.MIN_VALUE + ".." + Short.MAX_VALUE + "]\n z: [" + Short.MIN_VALUE + ".." + Short.MAX_VALUE + "]");
        publishDescription(getContract().EVENT_LINEAR_ACCELERATION, "timestamp: [0.." + Long.MAX_VALUE + "]\n x: [" + Short.MIN_VALUE + ".." + Short.MAX_VALUE + "]\n y: [" + Short.MIN_VALUE + ".." + Short.MAX_VALUE + "]\n z: [" + Short.MIN_VALUE + ".." + Short.MAX_VALUE + "]");
        publishDescription(getContract().EVENT_MAGNETIC_FIELD, "timestamp: [0.." + Long.MAX_VALUE + "]\n x: [" + Short.MIN_VALUE + ".." + Short.MAX_VALUE + "]\n y: [" + Short.MIN_VALUE + ".." + Short.MAX_VALUE + "]\n z: [" + Short.MIN_VALUE + ".." + Short.MAX_VALUE + "]");
        publishDescription(getContract().EVENT_ORIENTATION, "timestamp: [0.." + Long.MAX_VALUE + "]\n heading: [" + Short.MIN_VALUE + ".." + Short.MAX_VALUE + "]\n roll: [" + Short.MIN_VALUE + ".." + Short.MAX_VALUE + "]\n pitch: [" + Short.MIN_VALUE + ".." + Short.MAX_VALUE + "]");
        publishDescription(getContract().EVENT_QUATERNION, "timestamp: [0.." + Long.MAX_VALUE + "]\n w: [" + Short.MIN_VALUE + ".." + Short.MAX_VALUE + "]\n x: [" + Short.MIN_VALUE + ".." + Short.MAX_VALUE + "]\n y: [" + Short.MIN_VALUE + ".." + Short.MAX_VALUE + "]\n z: [" + Short.MIN_VALUE + ".." + Short.MAX_VALUE + "]");
        publishDescription(getContract().EVENT_TEMPERATURE, "timestamp: [0.." + Long.MAX_VALUE + "]\n value: [" + Byte.MIN_VALUE + ".." + Byte.MAX_VALUE + "]");
        publishDescription(getContract().EVENT_ALL_DATA, "timestamp: [0.." + Long.MAX_VALUE + "]\n @acceleration \n @magneticField \n @angularVelocity \n @orientation \n @quaternion \n @linearAcceleration \n @gravityVector \n @temperature \n calibrationStatus: [0..255]");

    }

    @Override
    public void messageReceived(String string, byte[] payload) throws Exception {

        if (string.startsWith(getContract().INTENT_ACCELERATION_CALLBACK_PERIOD)) {

            Long period = getMapper().readValue(payload, Long.class);
            getDevice().setAccelerationPeriod(period);
        }
        if (string.startsWith(getContract().INTENT_ALL_DATA_CALLBACK_PERIOD)) {
            Long period = getMapper().readValue(payload, Long.class);
            getDevice().setAllDataPeriod(period);
        }
        if (string.startsWith(getContract().INTENT_ANGULAR_VELOCITY_CALLBACK_PERIOD)) {
            Long period = getMapper().readValue(payload, Long.class);
            getDevice().setAngularVelocityPeriod(period);
        }
        if (string.startsWith(getContract().INTENT_GRAVITY_VECTOR_CALLBACK_PERIOD)) {
            Long period = getMapper().readValue(payload, Long.class);
            getDevice().setGravityVectorPeriod(period);
        }
        if (string.startsWith(getContract().INTENT_LINEAR_ACCELERATION_CALLBACK_PERIOD)) {
            Long period = getMapper().readValue(payload, Long.class);
            getDevice().setLinearAccelerationPeriod(period);
        }
        if (string.startsWith(getContract().INTENT_MAGNETIC_FIELD_CALLBACK_PERIOD)) {
            Long period = getMapper().readValue(payload, Long.class);
            getDevice().setMagneticFieldPeriod(period);
        }
        if (string.startsWith(getContract().INTENT_ORIENTATION_CALLBACK_PERIOD)) {
            Long period = getMapper().readValue(payload, Long.class);
            getDevice().setOrientationPeriod(period);
        }
        if (string.startsWith(getContract().INTENT_QUATERNION_CALLBACK_PERIOD)) {
            Long period = getMapper().readValue(payload, Long.class);
            getDevice().setQuaternionPeriod(period);
        }
        if (string.startsWith(getContract().INTENT_TEMPERATURE_CALLBACK_PERIOD)) {
            Long period = getMapper().readValue(payload, Long.class);
            getDevice().setTemperaturePeriod(period);
        }
        if (string.startsWith(getContract().INTENT_LEDS)) {
            Boolean enabled = getMapper().readValue(payload, Boolean.class);
            getDevice().setLEDs(enabled);
        }
        if (string.startsWith(getContract().INTENT_STATUS_LED)) {
            Boolean enabled = getMapper().readValue(payload, Boolean.class);
            getDevice().setStatusLED(enabled);
        }

    }

    @Override
    public void accelerationPeriodChanged(Long period) {
        publishStatus(getContract().STATUS_ACCELERATION_CALLBACK_PERIOD, period);

    }

    @Override
    public void allDataPeriodChanged(Long period) {
        publishStatus(getContract().STATUS_ALL_DATA_CALLBACK_PERIOD, period);
    }

    @Override
    public void angularVelocityPeriodChanged(Long period) {
        publishStatus(getContract().STATUS_ANGULAR_VELOCITY_CALLBACK_PERIOD, period);
    }

    @Override
    public void gravityVectorPeriodChanged(Long period) {
        publishStatus(getContract().STATUS_GRAVITY_VECTOR_CALLBACK_PERIOD, period);
    }

    @Override
    public void linearAccelerationPeriodChanged(Long period) {
        publishStatus(getContract().STATUS_LINEAR_ACCELERATION_CALLBACK_PERIOD, period);
    }

    @Override
    public void magneticFieldPeriodChanged(Long period) {
        publishStatus(getContract().STATUS_MAGNETIC_FIELD_CALLBACK_PERIOD, period);
    }

    @Override
    public void orientationPeriodChanged(Long period) {
        publishStatus(getContract().STATUS_ORIENTATION_CALLBACK_PERIOD, period);
    }

    @Override
    public void quaternionPeriodChanged(Long period) {
        publishStatus(getContract().STATUS_QUATERNION_CALLBACK_PERIOD, period);
    }

    @Override
    public void temperaturePeriodChanged(Long period) {
        publishStatus(getContract().STATUS_TEMPERATURE_CALLBACK_PERIOD, period);
    }

    @Override
    public void statusLEDChanged(Boolean isEnabled) {
        publishStatus(getContract().STATUS_STATUS_LED, isEnabled);
    }

    @Override
    public void LEDsChanged(Boolean areEnabled) {
        publishStatus(getContract().STATUS_LED, areEnabled);
    }

    @Override
    public void acceleration(short s, short s1, short s2) {
        publishEvent(getContract().EVENT_ACCELERATION, new Acceleration(s, s1, s2));
    }

    @Override
    public void allData(short[] shorts, short[] shorts1, short[] shorts2, short[] shorts3, short[] shorts4, short[] shorts5, short[] shorts6, byte b, short s) {
        publishEvent(getContract().EVENT_ALL_DATA, new AllData(new Acceleration(shorts[0], shorts[1], shorts[2]),
                new MagneticField(shorts1[0], shorts1[1], shorts1[2]),
                new AngularVelocity(shorts2[0], shorts2[1], shorts2[2]),
                new Orientation(shorts3[0], shorts3[1], shorts3[2]),
                new QuaternionEvent(shorts4[0], shorts4[1], shorts4[2], shorts4[3]),
                new LinearAcceleration(shorts5[0], shorts5[1], shorts5[2]),
                new GravityVector(shorts6[0], shorts6[1], shorts6[2]),
                b,
                s));
    }

    @Override
    public void angularVelocity(short s, short s1, short s2) {
        publishEvent(getContract().EVENT_ANGULAR_VELOCITY, new AngularVelocity(s, s1, s2));
    }

    @Override
    public void gravityVector(short s, short s1, short s2) {
        publishEvent(getContract().EVENT_GRAVITY_VECTOR, new GravityVector(s, s1, s2));
    }

    @Override
    public void linearAcceleration(short s, short s1, short s2) {
        publishEvent(getContract().EVENT_LINEAR_ACCELERATION, new LinearAcceleration(s, s1, s2));
    }

    @Override
    public void magneticField(short s, short s1, short s2) {
        publishEvent(getContract().EVENT_MAGNETIC_FIELD, new MagneticField(s, s1, s2));
    }

    @Override
    public void orientation(short s, short s1, short s2) {
        publishEvent(getContract().EVENT_ORIENTATION, new Orientation(s, s1, s2));
    }

    @Override
    public void quaternion(short s, short s1, short s2, short s3) {
        publishEvent(getContract().EVENT_QUATERNION, new QuaternionEvent(s, s1, s2, s3));
    }

    @Override
    public void temperature(byte b) {
        publishEvent(getContract().EVENT_TEMPERATURE, b);
    }

    @Override
    public void sensorFusionModeChanged(Short sensorFusionMode) {
        publishEvent(getContract().STATUS_SENSOR_FUSION_MODE, sensorFusionMode);
    }

    public static class Acceleration {

        private short x;
        private short y;
        private short z;

        private Acceleration() {
        }

        

        public Acceleration(short x, short y, short z) {
            this.x = x;
            this.y = y;
            this.z = z;
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

    public static class AngularVelocity {

        private short x;
        private short y;
        private short z;

        private AngularVelocity() {
        }

     

        public AngularVelocity(short x, short y, short z) {
            
            this.x = x;
            this.y = y;
            this.z = z;
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

    public static class GravityVector {

        private short x;
        private short y;
        private short z;

        private GravityVector() {
        }

      

        public GravityVector(short x, short y, short z) {
            this.x = x;
            this.y = y;
            this.z = z;
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

    public static class LinearAcceleration {

        private short x;
        private short y;
        private short z;

        private LinearAcceleration() {
        }

       

        public LinearAcceleration(short x, short y, short z) {
            this.x = x;
            this.y = y;
            this.z = z;
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

    public static class MagneticField {

        private short x;
        private short y;
        private short z;

        private MagneticField() {
        }

       

        public MagneticField(short x, short y, short z) {
            this.x = x;
            this.y = y;
            this.z = z;
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

    public static class Orientation {

        private short heading;
        private short roll;
        private short pitch;

        private Orientation() {
        }

       
        public Orientation(short heading, short roll, short pitch) {
            this.heading = heading;
            this.roll = roll;
            this.pitch = pitch;
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

        private short w;
        private short x;
        private short y;
        private short z;

        private QuaternionEvent() {
        }

       
        public QuaternionEvent(short w, short x, short y, short z) {
            this.w = w;
            this.x = x;
            this.y = y;
            this.z = z;
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


    public static class AllData {

        private Acceleration acceleration;
        private MagneticField magneticField;
        private AngularVelocity angularVelocity;
        private Orientation orientation;
        private QuaternionEvent quaternion;
        private LinearAcceleration linearAcceleration;
        private GravityVector gravityVector;
        private byte temperature;
        private short calibrationStatus;

        private AllData() {
        }

       
        public AllData(Acceleration acceleration, MagneticField magneticField, AngularVelocity angularVelocity, Orientation orientation, QuaternionEvent quaternion, LinearAcceleration linearAcceleration, GravityVector gravityVector, byte temperature, short calibrationStatus) {
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

        public Acceleration getAcceleration() {
            return acceleration;
        }

        public MagneticField getMagneticField() {
            return magneticField;
        }

        public AngularVelocity getAngularVelocity() {
            return angularVelocity;
        }

        public Orientation getOrientation() {
            return orientation;
        }

        public QuaternionEvent getQuaternion() {
            return quaternion;
        }

        public LinearAcceleration getLinearAcceleration() {
            return linearAcceleration;
        }

        public GravityVector getGravityVector() {
            return gravityVector;
        }

        public byte getTemperature() {
            return temperature;
        }

        public short getCalibrationStatus() {
            return calibrationStatus;
        }

    }

}
