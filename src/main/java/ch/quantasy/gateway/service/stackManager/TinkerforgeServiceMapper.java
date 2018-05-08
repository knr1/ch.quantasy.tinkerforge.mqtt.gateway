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
package ch.quantasy.gateway.service.stackManager;

import ch.quantasy.gateway.service.AbstractService;
import ch.quantasy.gateway.service.tinkerforge.IMUService;
import ch.quantasy.gateway.service.tinkerforge.IMUV2Service;
import ch.quantasy.gateway.service.tinkerforge.LCD16x2Service;
import ch.quantasy.gateway.service.tinkerforge.LCD20x4Service;
import ch.quantasy.gateway.service.tinkerforge.RGBLEDButtonService;
import ch.quantasy.gateway.service.tinkerforge.AccelerometerService;
import ch.quantasy.gateway.service.tinkerforge.AmbientLightService;
import ch.quantasy.gateway.service.tinkerforge.AmbientLightV2Service;
import ch.quantasy.gateway.service.tinkerforge.AnalogInV2Service;
import ch.quantasy.gateway.service.tinkerforge.AnalogOutV2Service;
import ch.quantasy.gateway.service.tinkerforge.BarometerService;
import ch.quantasy.gateway.service.tinkerforge.CO2Service;
import ch.quantasy.gateway.service.tinkerforge.ColorService;
import ch.quantasy.gateway.service.tinkerforge.DCService;
import ch.quantasy.gateway.service.tinkerforge.DistanceIRService;
import ch.quantasy.gateway.service.tinkerforge.DistanceUSService;
import ch.quantasy.gateway.service.tinkerforge.DualButtonService;
import ch.quantasy.gateway.service.tinkerforge.DualRelayService;
import ch.quantasy.gateway.service.tinkerforge.DustDetectorService;
import ch.quantasy.gateway.service.tinkerforge.GPSv2Service;
import ch.quantasy.gateway.service.tinkerforge.HallEffectService;
import ch.quantasy.gateway.service.tinkerforge.HumidityService;
import ch.quantasy.gateway.service.tinkerforge.JoystickService;
import ch.quantasy.gateway.service.tinkerforge.LaserRangeFinderService;
import ch.quantasy.gateway.service.tinkerforge.LEDStripService;
import ch.quantasy.gateway.service.tinkerforge.LineService;
import ch.quantasy.gateway.service.tinkerforge.LinearPotiService;
import ch.quantasy.gateway.service.tinkerforge.LoadCellService;
import ch.quantasy.gateway.service.tinkerforge.MasterService;
import ch.quantasy.gateway.service.tinkerforge.MoistureService;
import ch.quantasy.gateway.service.tinkerforge.MotionDetectorService;
import ch.quantasy.gateway.service.tinkerforge.MotionDetectorV2Service;
import ch.quantasy.gateway.service.tinkerforge.MotorizedLinearPotiService;
import ch.quantasy.gateway.service.tinkerforge.MultiTouchService;
import ch.quantasy.gateway.service.tinkerforge.NFCService;
import ch.quantasy.gateway.service.tinkerforge.OutdoorWeatherService;
import ch.quantasy.gateway.service.tinkerforge.PiezoSpeakerService;
import ch.quantasy.gateway.service.tinkerforge.RealTimeClockService;
import ch.quantasy.gateway.service.tinkerforge.RemoteSwitchService;
import ch.quantasy.gateway.service.tinkerforge.RemoteSwitchV2Service;
import ch.quantasy.gateway.service.tinkerforge.RotaryEncoderService;
import ch.quantasy.gateway.service.tinkerforge.RotaryPotiService;
import ch.quantasy.gateway.service.tinkerforge.Segment4x7Service;
import ch.quantasy.gateway.service.tinkerforge.ServoService;
import ch.quantasy.gateway.service.tinkerforge.SolidStateRelayService;
import ch.quantasy.gateway.service.tinkerforge.SoundIntensityService;
import ch.quantasy.gateway.service.tinkerforge.TemperatureService;
import ch.quantasy.gateway.service.tinkerforge.TemperatureIRService;
import ch.quantasy.gateway.service.tinkerforge.ThermalImagingService;
import ch.quantasy.gateway.service.tinkerforge.ThermoCoupleService;
import ch.quantasy.gateway.service.tinkerforge.TiltService;
import ch.quantasy.gateway.service.tinkerforge.UVLightService;
import ch.quantasy.gateway.service.tinkerforge.VoltageCurrentService;
import ch.quantasy.tinkerforge.device.LCD16x2.LCD16x2Device;
import ch.quantasy.tinkerforge.device.LCD20x4.LCD20x4Device;
import ch.quantasy.tinkerforge.device.RGBLEDButton.RGBLEDButtonDevice;
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
import ch.quantasy.tinkerforge.device.motionDetectorV2.MotionDetectorV2Device;
import ch.quantasy.tinkerforge.device.motorizedLinearPoti.MotorizedLinearPotiDevice;
import ch.quantasy.tinkerforge.device.multiTouch.MultiTouchDevice;
import ch.quantasy.tinkerforge.device.nfc.NFCRFIDDevice;
import ch.quantasy.tinkerforge.device.outdoorWeather.OutdoorWeatherDevice;
import ch.quantasy.tinkerforge.device.piezoSpeaker.PiezoSpeakerDevice;
import ch.quantasy.tinkerforge.device.realTimeClock.RealTimeClockDevice;
import ch.quantasy.tinkerforge.device.remoteSwitch.RemoteSwitchDevice;
import ch.quantasy.tinkerforge.device.remoteSwitchV2.RemoteSwitchV2Device;
import ch.quantasy.tinkerforge.device.rotaryEncoder.RotaryEncoderDevice;
import ch.quantasy.tinkerforge.device.rotaryPoti.RotaryPotiDevice;
import ch.quantasy.tinkerforge.device.segment4x7.Segment4x7Device;
import ch.quantasy.tinkerforge.device.servo.ServoDevice;
import ch.quantasy.tinkerforge.device.solidState.SolidStateRelayDevice;
import ch.quantasy.tinkerforge.device.soundIntensity.SoundIntensityDevice;
import ch.quantasy.tinkerforge.device.temperature.TemperatureDevice;
import ch.quantasy.tinkerforge.device.temperatureIR.TemperatureIRDevice;
import ch.quantasy.tinkerforge.device.thermalImaging.ThermalImagingDevice;
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
public class TinkerforgeServiceMapper {

    public static AbstractService getService(TinkerforgeDevice tinkerforgeDevice, URI mqttURI) throws MqttException {
        if (tinkerforgeDevice instanceof HumidityDevice) {
            return new HumidityService((HumidityDevice) tinkerforgeDevice, mqttURI);
        }
        if (tinkerforgeDevice instanceof LEDStripDevice) {
            return new LEDStripService((LEDStripDevice) tinkerforgeDevice, mqttURI);
        }
        if (tinkerforgeDevice instanceof MotionDetectorDevice) {
            return new MotionDetectorService((MotionDetectorDevice) tinkerforgeDevice, mqttURI);
        }
        if (tinkerforgeDevice instanceof MotionDetectorV2Device) {
            return new MotionDetectorV2Service((MotionDetectorV2Device) tinkerforgeDevice, mqttURI);
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
        if (tinkerforgeDevice instanceof RemoteSwitchV2Device) {
            return new RemoteSwitchV2Service((RemoteSwitchV2Device) tinkerforgeDevice, mqttURI);
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
        if(tinkerforgeDevice instanceof MotorizedLinearPotiDevice){
            return new MotorizedLinearPotiService((MotorizedLinearPotiDevice)tinkerforgeDevice, mqttURI);
        }
        if(tinkerforgeDevice instanceof RGBLEDButtonDevice){
            return new RGBLEDButtonService((RGBLEDButtonDevice)tinkerforgeDevice, mqttURI);
        }
        if(tinkerforgeDevice instanceof ThermalImagingDevice){
            return new ThermalImagingService((ThermalImagingDevice)tinkerforgeDevice, mqttURI);
        }
        if(tinkerforgeDevice instanceof OutdoorWeatherDevice){
            return new OutdoorWeatherService((OutdoorWeatherDevice)tinkerforgeDevice, mqttURI);
        }
        return null;

    }

}
