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

import ch.quantasy.gateway.message.event.IMU.OrientationEvent;
import ch.quantasy.gateway.message.event.IMU.QuaternionEvent;
import ch.quantasy.gateway.message.event.IMU.MagneticFieldEvent;
import ch.quantasy.gateway.message.event.IMU.AngularVelocityEvent;
import ch.quantasy.gateway.message.event.IMU.AccelerationEvent;
import ch.quantasy.gateway.message.event.IMU.AllDataEvent;
import ch.quantasy.gateway.message.event.IMU.TemperatureEvent;
import ch.quantasy.gateway.service.device.AbstractDeviceService;
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
        readyToPublishEvent(getContract().EVENT_ACCELERATION, new AccelerationEvent(s, s1, s2));
    }

    @Override
    public void allData(short shorts, short shorts1, short shorts2, short shorts3, short shorts4, short shorts5, short shorts6, short short7, short s8, short s9) {
        readyToPublishEvent(getContract().EVENT_ALL_DATA, new AllDataEvent(new AccelerationEvent(shorts, shorts1, shorts2),
                new MagneticFieldEvent(shorts3, shorts4, shorts5),
                new AngularVelocityEvent(shorts6, short7, s8),
                new TemperatureEvent(s9)));
    }

    @Override
    public void angularVelocity(short s, short s1, short s2) {
        readyToPublishEvent(getContract().EVENT_ANGULAR_VELOCITY, new AngularVelocityEvent(s, s1, s2));
    }

    @Override
    public void magneticField(short s, short s1, short s2) {
        readyToPublishEvent(getContract().EVENT_MAGNETIC_FIELD, new MagneticFieldEvent(s, s1, s2));
    }

    @Override
    public void orientation(short s, short s1, short s2) {
        readyToPublishEvent(getContract().EVENT_ORIENTATION, new OrientationEvent(s, s1, s2));
    }

    @Override
    public void quaternion(float s, float s1, float s2, float s3) {
        readyToPublishEvent(getContract().EVENT_QUATERNION, new QuaternionEvent(s, s1, s2, s3));
    }









}
