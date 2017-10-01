/*
 *   "TiMqWay"
 *
 *    TiMqWay(tm): A gateway to provide an MQTT-View for the Tinkerforge(tm) world (Tinkerforge-MQTT-Gateway).
 *
 *    Copyright (c) 2016 Bern University of Applied Sciences (BFH),
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
package ch.quantasy.gateway.tinkerforge;

import ch.quantasy.gateway.service.AbstractService;
import ch.quantasy.gateway.service.device.IMU.IMUService;
import ch.quantasy.gateway.service.device.IMUV2.IMUV2Service;
import ch.quantasy.gateway.service.device.LCD16x2.LCD16x2Service;
import ch.quantasy.gateway.service.device.LCD20x4.LCD20x4Service;
import ch.quantasy.gateway.service.device.accelerometer.AccelerometerService;
import ch.quantasy.gateway.service.device.ambientLight.AmbientLightService;
import ch.quantasy.gateway.service.device.ambientLightV2.AmbientLightV2Service;
import ch.quantasy.gateway.service.device.analogInV2.AnalogInV2Service;
import ch.quantasy.gateway.service.device.analogOutV2.AnalogOutV2Service;
import ch.quantasy.gateway.service.device.barometer.BarometerService;
import ch.quantasy.gateway.service.device.co2.CO2Service;
import ch.quantasy.gateway.service.device.color.ColorService;
import ch.quantasy.gateway.service.device.dc.DCService;
import ch.quantasy.gateway.service.device.distanceIR.DistanceIRService;
import ch.quantasy.gateway.service.device.distanceUS.DistanceUSService;
import ch.quantasy.gateway.service.device.dualButton.DualButtonService;
import ch.quantasy.gateway.service.device.dualRelay.DualRelayService;
import ch.quantasy.gateway.service.device.dustDetector.DustDetectorService;
import ch.quantasy.gateway.service.device.gpsv2.GPSv2Service;
import ch.quantasy.gateway.service.device.hallEffect.HallEffectService;
import ch.quantasy.gateway.service.device.humidity.HumidityService;
import ch.quantasy.gateway.service.device.joystick.JoystickService;
import ch.quantasy.gateway.service.device.laserRangeFinder.LaserRangeFinderService;
import ch.quantasy.gateway.service.device.ledStrip.LEDStripService;
import ch.quantasy.gateway.service.device.line.LineService;
import ch.quantasy.gateway.service.device.linearPoti.LinearPotiService;
import ch.quantasy.gateway.service.device.loadCell.LoadCellService;
import ch.quantasy.gateway.service.device.master.MasterService;
import ch.quantasy.gateway.service.device.moisture.MoistureService;
import ch.quantasy.gateway.service.device.motionDetector.MotionDetectorService;
import ch.quantasy.gateway.service.device.multiTouch.MultiTouchService;
import ch.quantasy.gateway.service.device.nfc.NFCService;
import ch.quantasy.gateway.service.device.piezoSpeaker.PiezoSpeakerService;
import ch.quantasy.gateway.service.device.realTimeClock.RealTimeClockService;
import ch.quantasy.gateway.service.device.remoteSwitch.RemoteSwitchService;
import ch.quantasy.gateway.service.device.rotaryEncoder.RotaryEncoderService;
import ch.quantasy.gateway.service.device.rotaryPoti.RotaryPotiService;
import ch.quantasy.gateway.service.device.segment4x7.Segment4x7Service;
import ch.quantasy.gateway.service.device.servo.ServoService;
import ch.quantasy.gateway.service.device.solidStateRelay.SolidStateRelayService;
import ch.quantasy.gateway.service.device.soundIntensity.SoundIntensityService;
import ch.quantasy.gateway.service.device.temperature.TemperatureService;
import ch.quantasy.gateway.service.device.temperatureIR.TemperatureIRService;
import ch.quantasy.gateway.service.device.thermoCouple.ThermoCoupleService;
import ch.quantasy.gateway.service.device.tilt.TiltService;
import ch.quantasy.gateway.service.device.uvLight.UVLightService;
import ch.quantasy.gateway.service.device.voltageCurrent.VoltageCurrentService;
import ch.quantasy.tinkerforge.device.LCD16x2.LCD16x2Device;
import ch.quantasy.tinkerforge.device.LCD20x4.LCD20x4Device;
import ch.quantasy.tinkerforge.device.TinkerforgeDevice;
import ch.quantasy.tinkerforge.device.accelerometer.AccelerometerDevice;
import ch.quantasy.tinkerforge.device.ambientLight.AmbientLightDevice;
import ch.quantasy.tinkerforge.device.ambientLightV2.AmbientLightV2Device;
import ch.quantasy.tinkerforge.device.analogInV2.AnalogInV2Device;
import ch.quantasy.tinkerforge.device.analogOutV2.AnalogOutV2Device;
import ch.quantasy.tinkerforge.device.barometer.BarometerDevice;
import ch.quantasy.tinkerforge.device.co2.CO2Device;
import ch.quantasy.tinkerforge.device.color.ColorDevice;
import ch.quantasy.tinkerforge.device.dc.DCDevice;
import ch.quantasy.tinkerforge.device.distanceIR.DistanceIRDevice;
import ch.quantasy.tinkerforge.device.distanceUS.DistanceUSDevice;
import ch.quantasy.tinkerforge.device.dualButton.DualButtonDevice;
import ch.quantasy.tinkerforge.device.dualRelay.DualRelayDevice;
import ch.quantasy.tinkerforge.device.dustDetector.DustDetectorDevice;
import ch.quantasy.tinkerforge.device.gpsV2.GPSv2Device;
import ch.quantasy.tinkerforge.device.hallEffect.HallEffectDevice;
import ch.quantasy.tinkerforge.device.humidity.HumidityDevice;
import ch.quantasy.tinkerforge.device.imu.IMUDevice;
import ch.quantasy.tinkerforge.device.imuV2.IMUV2Device;
import ch.quantasy.tinkerforge.device.joystick.JoystickDevice;
import ch.quantasy.tinkerforge.device.laserRangeFinder.LaserRangeFinderDevice;
import ch.quantasy.tinkerforge.device.ledStrip.LEDStripDevice;
import ch.quantasy.tinkerforge.device.line.LineDevice;
import ch.quantasy.tinkerforge.device.linearPoti.LinearPotiDevice;
import ch.quantasy.tinkerforge.device.loadCell.LoadCellDevice;
import ch.quantasy.tinkerforge.device.master.MasterDevice;
import ch.quantasy.tinkerforge.device.moisture.MoistureDevice;
import ch.quantasy.tinkerforge.device.motionDetector.MotionDetectorDevice;
import ch.quantasy.tinkerforge.device.multiTouch.MultiTouchDevice;
import ch.quantasy.tinkerforge.device.nfc.NFCRFIDDevice;
import ch.quantasy.tinkerforge.device.piezoSpeaker.PiezoSpeakerDevice;
import ch.quantasy.tinkerforge.device.realTimeClock.RealTimeClockDevice;
import ch.quantasy.tinkerforge.device.remoteSwitch.RemoteSwitchDevice;
import ch.quantasy.tinkerforge.device.rotaryEncoder.RotaryEncoderDevice;
import ch.quantasy.tinkerforge.device.rotaryPoti.RotaryPotiDevice;
import ch.quantasy.tinkerforge.device.segment4x7.Segment4x7Device;
import ch.quantasy.tinkerforge.device.servo.ServoDevice;
import ch.quantasy.tinkerforge.device.solidState.SolidStateRelayDevice;
import ch.quantasy.tinkerforge.device.soundIntensity.SoundIntensityDevice;
import ch.quantasy.tinkerforge.device.temperature.TemperatureDevice;
import ch.quantasy.tinkerforge.device.temperatureIR.TemperatureIRDevice;
import ch.quantasy.tinkerforge.device.thermoCouple.ThermoCoupleDevice;
import ch.quantasy.tinkerforge.device.tilt.TiltDevice;
import ch.quantasy.tinkerforge.device.uvLight.UVLightDevice;
import ch.quantasy.tinkerforge.device.voltageCurrent.VoltageCurrentDevice;
import java.net.URI;
import org.eclipse.paho.client.mqttv3.MqttException;

/**
 *
 * @author reto
 */
public class TinkerforgeServiceMapper{

    public static AbstractService getService(TinkerforgeDevice tinkerforgeDevice, URI mqttURI) throws MqttException{
        if (tinkerforgeDevice instanceof HumidityDevice) {
            return new HumidityService((HumidityDevice) tinkerforgeDevice, mqttURI);
        }
        if (tinkerforgeDevice instanceof LEDStripDevice) {
            return new LEDStripService((LEDStripDevice) tinkerforgeDevice, mqttURI);
        }
        if (tinkerforgeDevice instanceof MotionDetectorDevice) {
            return new MotionDetectorService((MotionDetectorDevice) tinkerforgeDevice, mqttURI);
        }
        if (tinkerforgeDevice instanceof MoistureDevice) {
            return new MoistureService((MoistureDevice) tinkerforgeDevice, mqttURI);
        }
        if (tinkerforgeDevice instanceof AmbientLightDevice) {
            return new AmbientLightService((AmbientLightDevice) tinkerforgeDevice, mqttURI);
        }
        if (tinkerforgeDevice instanceof AmbientLightV2Device) {
            return new AmbientLightV2Service((AmbientLightV2Device) tinkerforgeDevice, mqttURI);
        }
        if (tinkerforgeDevice instanceof RemoteSwitchDevice) {
            return new RemoteSwitchService((RemoteSwitchDevice) tinkerforgeDevice, mqttURI);
        }
        if (tinkerforgeDevice instanceof DualRelayDevice) {
            return new DualRelayService((DualRelayDevice) tinkerforgeDevice, mqttURI);
        }
        if (tinkerforgeDevice instanceof BarometerDevice) {
            return new BarometerService((BarometerDevice) tinkerforgeDevice, mqttURI);
        }
        if (tinkerforgeDevice instanceof DualButtonDevice) {
            return new DualButtonService((DualButtonDevice) tinkerforgeDevice, mqttURI);
        }
        if (tinkerforgeDevice instanceof CO2Device) {
            return new CO2Service((CO2Device) tinkerforgeDevice, mqttURI);
        }
        if (tinkerforgeDevice instanceof DCDevice) {
            return new DCService((DCDevice) tinkerforgeDevice, mqttURI);
        }
        if (tinkerforgeDevice instanceof DistanceUSDevice) {
            return new DistanceUSService((DistanceUSDevice) tinkerforgeDevice, mqttURI);
        }
        if (tinkerforgeDevice instanceof TemperatureIRDevice) {
            return new TemperatureIRService((TemperatureIRDevice) tinkerforgeDevice, mqttURI);
        }
        if (tinkerforgeDevice instanceof UVLightDevice) {
            return new UVLightService((UVLightDevice) tinkerforgeDevice, mqttURI);
        }
        if (tinkerforgeDevice instanceof DistanceIRDevice) {
            return new DistanceIRService((DistanceIRDevice) tinkerforgeDevice, mqttURI);
        }
        if (tinkerforgeDevice instanceof DustDetectorDevice) {
            return new DustDetectorService((DustDetectorDevice) tinkerforgeDevice, mqttURI);
        }
        if (tinkerforgeDevice instanceof TiltDevice) {
            return new TiltService((TiltDevice) tinkerforgeDevice, mqttURI);
        }
        if (tinkerforgeDevice instanceof AccelerometerDevice) {
            return new AccelerometerService((AccelerometerDevice) tinkerforgeDevice, mqttURI);
        }
        if (tinkerforgeDevice instanceof ColorDevice) {
            return new ColorService((ColorDevice) tinkerforgeDevice, mqttURI);
        }
        if (tinkerforgeDevice instanceof Segment4x7Device) {
            return new Segment4x7Service((Segment4x7Device) tinkerforgeDevice, mqttURI);
        }
        if (tinkerforgeDevice instanceof LaserRangeFinderDevice) {
            return new LaserRangeFinderService((LaserRangeFinderDevice) tinkerforgeDevice, mqttURI);
        }
        if (tinkerforgeDevice instanceof HallEffectDevice) {
            return new HallEffectService((HallEffectDevice) tinkerforgeDevice, mqttURI);
        }
        if (tinkerforgeDevice instanceof JoystickDevice) {
            return new JoystickService((JoystickDevice) tinkerforgeDevice, mqttURI);
        }
        if (tinkerforgeDevice instanceof LinearPotiDevice) {
            return new LinearPotiService((LinearPotiDevice) tinkerforgeDevice, mqttURI);
        }
        if (tinkerforgeDevice instanceof RotaryPotiDevice) {
            return new RotaryPotiService((RotaryPotiDevice) tinkerforgeDevice, mqttURI);
        }
        if (tinkerforgeDevice instanceof RotaryEncoderDevice) {
            return new RotaryEncoderService((RotaryEncoderDevice) tinkerforgeDevice, mqttURI);
        }
        if (tinkerforgeDevice instanceof SolidStateRelayDevice) {
            return new SolidStateRelayService((SolidStateRelayDevice) tinkerforgeDevice, mqttURI);
        }
        if (tinkerforgeDevice instanceof MultiTouchDevice) {
            return new MultiTouchService((MultiTouchDevice) tinkerforgeDevice, mqttURI);
        }
        if (tinkerforgeDevice instanceof AnalogInV2Device) {
            return new AnalogInV2Service((AnalogInV2Device) tinkerforgeDevice, mqttURI);
        }
        if (tinkerforgeDevice instanceof AnalogOutV2Device) {
            return new AnalogOutV2Service((AnalogOutV2Device) tinkerforgeDevice, mqttURI);
        }
        if (tinkerforgeDevice instanceof PiezoSpeakerDevice) {
            return new PiezoSpeakerService((PiezoSpeakerDevice) tinkerforgeDevice, mqttURI);
        }
        if (tinkerforgeDevice instanceof SoundIntensityDevice) {
            return new SoundIntensityService((SoundIntensityDevice) tinkerforgeDevice, mqttURI);
        }
        if (tinkerforgeDevice instanceof IMUV2Device) {
            return new IMUV2Service((IMUV2Device) tinkerforgeDevice, mqttURI);
        }
        if (tinkerforgeDevice instanceof IMUDevice) {
            return new IMUService((IMUDevice) tinkerforgeDevice, mqttURI);
        }
        if (tinkerforgeDevice instanceof LineDevice) {
            return new LineService((LineDevice) tinkerforgeDevice, mqttURI);
        }
        if (tinkerforgeDevice instanceof LCD20x4Device) {
            return new LCD20x4Service((LCD20x4Device) tinkerforgeDevice, mqttURI);
        }
        if (tinkerforgeDevice instanceof LCD16x2Device) {
            return new LCD16x2Service((LCD16x2Device) tinkerforgeDevice, mqttURI);
        }
        if (tinkerforgeDevice instanceof TemperatureDevice) {
            return new TemperatureService((TemperatureDevice) tinkerforgeDevice, mqttURI);
        }
        if (tinkerforgeDevice instanceof LoadCellDevice) {
            return new LoadCellService((LoadCellDevice) tinkerforgeDevice, mqttURI);
        }
        if (tinkerforgeDevice instanceof RealTimeClockDevice) {
            return new RealTimeClockService((RealTimeClockDevice) tinkerforgeDevice, mqttURI);
        }
        if (tinkerforgeDevice instanceof ThermoCoupleDevice) {
            return new ThermoCoupleService((ThermoCoupleDevice) tinkerforgeDevice, mqttURI);
        }
        if (tinkerforgeDevice instanceof GPSv2Device) {
            return new GPSv2Service((GPSv2Device) tinkerforgeDevice, mqttURI);
        }
        if (tinkerforgeDevice instanceof NFCRFIDDevice) {
            return new NFCService((NFCRFIDDevice) tinkerforgeDevice, mqttURI);
        }
        if (tinkerforgeDevice instanceof MasterDevice) {
            return new MasterService((MasterDevice) tinkerforgeDevice, mqttURI);
        }
        if (tinkerforgeDevice instanceof VoltageCurrentDevice) {
            return new VoltageCurrentService((VoltageCurrentDevice) tinkerforgeDevice, mqttURI);
        }
        if (tinkerforgeDevice instanceof ServoDevice) {
            return new ServoService((ServoDevice) tinkerforgeDevice, mqttURI);
        }
        return null;

    }

}
