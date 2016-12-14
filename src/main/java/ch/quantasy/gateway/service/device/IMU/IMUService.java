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
package ch.quantasy.gateway.service.device.IMU;

import ch.quantasy.gateway.service.device.AbstractDeviceService;
import ch.quantasy.mqtt.gateway.client.GCEvent;
import ch.quantasy.tinkerforge.device.imu.IMUDevice;
import ch.quantasy.tinkerforge.device.imu.IMUDeviceCallback;
import org.eclipse.paho.client.mqttv3.MqttException;
import java.net.URI;

/**
 *
 * @author reto
 */
public class IMUService extends AbstractDeviceService<IMUDevice, IMUServiceContract> implements IMUDeviceCallback {

    public IMUService(IMUDevice device, URI mqttURI) throws MqttException {

        super(mqttURI, device, new IMUServiceContract(device));
        publishDescription(getContract().INTENT_ACCELERATION_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        publishDescription(getContract().INTENT_ALL_DATA_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        publishDescription(getContract().INTENT_ANGULAR_VELOCITY_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        publishDescription(getContract().INTENT_MAGNETIC_FIELD_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        publishDescription(getContract().INTENT_ORIENTATION_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        publishDescription(getContract().INTENT_QUATERNION_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        publishDescription(getContract().INTENT_LEDS, "true|false]");
        publishDescription(getContract().INTENT_STATUS_LED, "true|false]");
        publishDescription(getContract().INTENT_ORIENTATION_CALCULATION, "true|false]");

        publishDescription(getContract().STATUS_ACCELERATION_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        publishDescription(getContract().STATUS_ALL_DATA_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        publishDescription(getContract().STATUS_ANGULAR_VELOCITY_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        publishDescription(getContract().STATUS_MAGNETIC_FIELD_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        publishDescription(getContract().STATUS_ORIENTATION_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        publishDescription(getContract().STATUS_QUATERNION_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        publishDescription(getContract().STATUS_STATUS_LED, "[true|false]");
        publishDescription(getContract().STATUS_LEDS, "[true|false]");
        publishDescription(getContract().STATUS_ORIENTATION_CALCULATION, "[true|false]");

        publishDescription(getContract().EVENT_ACCELERATION, "timestamp: [0.." + Long.MAX_VALUE + "]\n x: [" + Short.MIN_VALUE + ".." + Short.MAX_VALUE + "]\n y: [" + Short.MIN_VALUE + ".." + Short.MAX_VALUE + "]\n z: [" + Short.MIN_VALUE + ".." + Short.MAX_VALUE + "]");
        publishDescription(getContract().EVENT_ANGULAR_VELOCITY, "timestamp: [0.." + Long.MAX_VALUE + "]\n x: [" + Short.MIN_VALUE + ".." + Short.MAX_VALUE + "]\n y: [" + Short.MIN_VALUE + ".." + Short.MAX_VALUE + "]\n z: [" + Short.MIN_VALUE + ".." + Short.MAX_VALUE + "]");
        publishDescription(getContract().EVENT_MAGNETIC_FIELD, "timestamp: [0.." + Long.MAX_VALUE + "]\n x: [" + Short.MIN_VALUE + ".." + Short.MAX_VALUE + "]\n y: [" + Short.MIN_VALUE + ".." + Short.MAX_VALUE + "]\n z: [" + Short.MIN_VALUE + ".." + Short.MAX_VALUE + "]");
        publishDescription(getContract().EVENT_ORIENTATION, "timestamp: [0.." + Long.MAX_VALUE + "]\n heading: [" + Short.MIN_VALUE + ".." + Short.MAX_VALUE + "]\n roll: [" + Short.MIN_VALUE + ".." + Short.MAX_VALUE + "]\n pitch: [" + Short.MIN_VALUE + ".." + Short.MAX_VALUE + "]");
        publishDescription(getContract().EVENT_QUATERNION, "timestamp: [0.." + Long.MAX_VALUE + "]\n w: [" + Short.MIN_VALUE + ".." + Short.MAX_VALUE + "]\n x: [" + Short.MIN_VALUE + ".." + Short.MAX_VALUE + "]\n y: [" + Short.MIN_VALUE + ".." + Short.MAX_VALUE + "]\n z: [" + Short.MIN_VALUE + ".." + Short.MAX_VALUE + "]");
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

        if (string.startsWith(getContract().INTENT_LEDS)) {
            Boolean enabled = getMapper().readValue(payload, Boolean.class);
            getDevice().setLEDs(enabled);
        }
        if (string.startsWith(getContract().INTENT_STATUS_LED)) {
            Boolean enabled = getMapper().readValue(payload, Boolean.class);
            getDevice().setStatusLED(enabled);
        }
        if (string.startsWith(getContract().INTENT_ORIENTATION_CALCULATION)) {
            Boolean enabled = getMapper().readValue(payload, Boolean.class);
            getDevice().setOrientationCalculation(enabled);
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
    public void statusLEDChanged(Boolean isEnabled) {
        publishStatus(getContract().STATUS_STATUS_LED, isEnabled);
    }

    @Override
    public void LEDsChanged(Boolean areEnabled) {
        publishStatus(getContract().STATUS_LED, areEnabled);
    }

    @Override
    public void orientationCalculationChanged(Boolean isEnabled) {
        publishStatus(getContract().STATUS_ORIENTATION_CALCULATION, isEnabled);
    }

    @Override
    public void acceleration(short s, short s1, short s2) {
        publishEvent(getContract().EVENT_ACCELERATION, new Acceleration(s, s1, s2));
    }

    @Override
    public void allData(short shorts, short shorts1, short shorts2, short shorts3, short shorts4, short shorts5, short shorts6, short short7, short s8, short s9) {
        publishEvent(getContract().EVENT_ALL_DATA, new AllDataEvent(new Acceleration(shorts, shorts1, shorts2),
                new MagneticField(shorts3, shorts4, shorts5),
                new AngularVelocity(shorts6, short7, s8),
                s9));
    }

    @Override
    public void angularVelocity(short s, short s1, short s2) {
        publishEvent(getContract().EVENT_ANGULAR_VELOCITY, new AngularVelocity(s, s1, s2));
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
    public void quaternion(float s, float s1, float s2, float s3) {
        publishEvent(getContract().EVENT_QUATERNION, new Quaternion(s, s1, s2, s3));
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

    public static class Quaternion {

        private float w;
        private float x;
        private float y;
        private float z;

        private Quaternion() {
        }

        
        
        public Quaternion(float w, float x, float y, float z) {
            this.w = w;
            this.x = x;
            this.y = y;
            this.z = z;
        }

       
        public float getW() {
            return w;
        }

        public float getX() {
            return x;
        }

        public float getY() {
            return y;
        }

        public float getZ() {
            return z;
        }

    }

    

    public static class AllDataEvent {


        private Acceleration acceleration;
        private MagneticField magneticField;
        private AngularVelocity angularVelocity;
        private Short temperature;

        private AllDataEvent() {
        }

        

        public AllDataEvent(Acceleration acceleration, MagneticField magneticField, AngularVelocity angularVelocity, Short temperature) {
            this.acceleration = acceleration;
            this.magneticField = magneticField;
            this.angularVelocity = angularVelocity;
            this.temperature = temperature;
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

        public Short getTemperature() {
            return temperature;
        }

    }

}
