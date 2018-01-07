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
package ch.quantasy.tinkerforge.device;

import ch.quantasy.tinkerforge.device.imu.IMUDevice;
import ch.quantasy.tinkerforge.device.imuV2.IMUV2Device;
import ch.quantasy.tinkerforge.device.LCD16x2.LCD16x2Device;
import ch.quantasy.tinkerforge.device.LCD20x4.LCD20x4Device;
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
import ch.quantasy.tinkerforge.device.gps.GPSDevice;
import ch.quantasy.tinkerforge.device.gpsV2.GPSv2Device;
import ch.quantasy.tinkerforge.device.hallEffect.HallEffectDevice;
import ch.quantasy.tinkerforge.device.humidity.HumidityDevice;
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
import ch.quantasy.tinkerforge.device.ptc.PTCDevice;
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
import ch.quantasy.tinkerforge.stack.TinkerforgeStack;
import ch.quantasy.gateway.message.stack.TinkerforgeStackAddress;
import ch.quantasy.tinkerforge.device.RGBLEDButton.RGBLEDButtonDevice;
import ch.quantasy.tinkerforge.device.motorizedLinearPoti.MotorizedLinearPotiDevice;
import ch.quantasy.tinkerforge.device.thermalImaging.ThermalImagingDevice;
import com.tinkerforge.BrickDC;
import com.tinkerforge.BrickIMU;
import com.tinkerforge.BrickIMUV2;
import com.tinkerforge.BrickMaster;
import com.tinkerforge.BrickServo;
import com.tinkerforge.BrickletAccelerometer;
import com.tinkerforge.BrickletAmbientLight;
import com.tinkerforge.BrickletAmbientLightV2;
import com.tinkerforge.BrickletAnalogInV2;
import com.tinkerforge.BrickletAnalogOutV2;
import com.tinkerforge.BrickletBarometer;
import com.tinkerforge.BrickletCO2;
import com.tinkerforge.BrickletColor;
import com.tinkerforge.BrickletDistanceIR;
import com.tinkerforge.BrickletDistanceUS;
import com.tinkerforge.BrickletDualButton;
import com.tinkerforge.BrickletDualRelay;
import com.tinkerforge.BrickletDustDetector;
import com.tinkerforge.BrickletGPS;
import com.tinkerforge.BrickletGPSV2;
import com.tinkerforge.BrickletHallEffect;
import com.tinkerforge.BrickletHumidity;
import com.tinkerforge.BrickletJoystick;
import com.tinkerforge.BrickletLCD16x2;
import com.tinkerforge.BrickletLCD20x4;
import com.tinkerforge.BrickletLEDStrip;
import com.tinkerforge.BrickletLaserRangeFinder;
import com.tinkerforge.BrickletLine;
import com.tinkerforge.BrickletLinearPoti;
import com.tinkerforge.BrickletLoadCell;
import com.tinkerforge.BrickletMoisture;
import com.tinkerforge.BrickletMotionDetector;
import com.tinkerforge.BrickletMotorizedLinearPoti;
import com.tinkerforge.BrickletMultiTouch;
import com.tinkerforge.BrickletNFCRFID;
import com.tinkerforge.BrickletPTC;
import com.tinkerforge.BrickletPiezoSpeaker;
import com.tinkerforge.BrickletRGBLEDButton;
import com.tinkerforge.BrickletRealTimeClock;
import com.tinkerforge.BrickletRemoteSwitch;
import com.tinkerforge.BrickletRotaryEncoder;
import com.tinkerforge.BrickletRotaryPoti;
import com.tinkerforge.BrickletSegmentDisplay4x7;
import com.tinkerforge.BrickletSolidStateRelay;
import com.tinkerforge.BrickletSoundIntensity;
import com.tinkerforge.BrickletTemperature;
import com.tinkerforge.BrickletTemperatureIR;
import com.tinkerforge.BrickletThermalImaging;
import com.tinkerforge.BrickletThermocouple;
import com.tinkerforge.BrickletTilt;
import com.tinkerforge.BrickletUVLight;
import com.tinkerforge.BrickletVoltageCurrent;
import com.tinkerforge.Device;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;

/**
 *
 * @author reto
 */
public class TinkerforgeDeviceMapper {

    public static TinkerforgeDevice getTinkerforgeDevice(TinkerforgeStack stack, Device device) throws NotConnectedException, TimeoutException {
        if (TinkerforgeDeviceClass.Humidity == TinkerforgeDeviceClass.getDevice(device)) {
            return new HumidityDevice(stack, (BrickletHumidity) device);
        }
        if (TinkerforgeDeviceClass.Moisture == TinkerforgeDeviceClass.getDevice(device)) {
            return new MoistureDevice(stack, (BrickletMoisture) device);
        }
        if (TinkerforgeDeviceClass.LEDStrip == TinkerforgeDeviceClass.getDevice(device)) {
            return new LEDStripDevice(stack, (BrickletLEDStrip) device);
        }
        if (TinkerforgeDeviceClass.MotionDetector == TinkerforgeDeviceClass.getDevice(device)) {
            return new MotionDetectorDevice(stack, (BrickletMotionDetector) device);
        }
        if (TinkerforgeDeviceClass.AmbientLight == TinkerforgeDeviceClass.getDevice(device)) {
            return new AmbientLightDevice(stack, (BrickletAmbientLight) device);
        }
        if (TinkerforgeDeviceClass.AmbientLightV2 == TinkerforgeDeviceClass.getDevice(device)) {
            return new AmbientLightV2Device(stack, (BrickletAmbientLightV2) device);
        }
        if (TinkerforgeDeviceClass.RemoteSwitch == TinkerforgeDeviceClass.getDevice(device)) {
            return new RemoteSwitchDevice(stack, (BrickletRemoteSwitch) device);
        }
        if (TinkerforgeDeviceClass.DualRelay == TinkerforgeDeviceClass.getDevice(device)) {
            return new DualRelayDevice(stack, (BrickletDualRelay) device);
        }
        if (TinkerforgeDeviceClass.Barometer == TinkerforgeDeviceClass.getDevice(device)) {
            return new BarometerDevice(stack, (BrickletBarometer) device);
        }
        if (TinkerforgeDeviceClass.DualButton == TinkerforgeDeviceClass.getDevice(device)) {
            return new DualButtonDevice(stack, (BrickletDualButton) device);
        }
        if (TinkerforgeDeviceClass.CO2 == TinkerforgeDeviceClass.getDevice(device)) {
            return new CO2Device(stack, (BrickletCO2) device);
        }
        if (TinkerforgeDeviceClass.DC == TinkerforgeDeviceClass.getDevice(device)) {
            return new DCDevice(stack, (BrickDC) device);
        }
        if (TinkerforgeDeviceClass.DistanceUS == TinkerforgeDeviceClass.getDevice(device)) {
            return new DistanceUSDevice(stack, (BrickletDistanceUS) device);
        }
        if (TinkerforgeDeviceClass.TemperatureIR == TinkerforgeDeviceClass.getDevice(device)) {
            return new TemperatureIRDevice(stack, (BrickletTemperatureIR) device);
        }
        if (TinkerforgeDeviceClass.UVLight == TinkerforgeDeviceClass.getDevice(device)) {
            return new UVLightDevice(stack, (BrickletUVLight) device);
        }
        if (TinkerforgeDeviceClass.DistanceIR == TinkerforgeDeviceClass.getDevice(device)) {
            return new DistanceIRDevice(stack, (BrickletDistanceIR) device);
        }
        if (TinkerforgeDeviceClass.DustDetector == TinkerforgeDeviceClass.getDevice(device)) {
            return new DustDetectorDevice(stack, (BrickletDustDetector) device);
        }
        if (TinkerforgeDeviceClass.Tilt == TinkerforgeDeviceClass.getDevice(device)) {
            return new TiltDevice(stack, (BrickletTilt) device);
        }
        if (TinkerforgeDeviceClass.Accelerometer == TinkerforgeDeviceClass.getDevice(device)) {
            return new AccelerometerDevice(stack, (BrickletAccelerometer) device);
        }
        if (TinkerforgeDeviceClass.Color == TinkerforgeDeviceClass.getDevice(device)) {
            return new ColorDevice(stack, (BrickletColor) device);
        }
        if (TinkerforgeDeviceClass.SegmentDisplay4x7 == TinkerforgeDeviceClass.getDevice(device)) {
            return new Segment4x7Device(stack, (BrickletSegmentDisplay4x7) device);
        }
        if (TinkerforgeDeviceClass.LaserRangeFinder == TinkerforgeDeviceClass.getDevice(device)) {
            return new LaserRangeFinderDevice(stack, (BrickletLaserRangeFinder) device);
        }
        if (TinkerforgeDeviceClass.HallEffect == TinkerforgeDeviceClass.getDevice(device)) {
            return new HallEffectDevice(stack, (BrickletHallEffect) device);
        }
        if (TinkerforgeDeviceClass.Joystick == TinkerforgeDeviceClass.getDevice(device)) {
            return new JoystickDevice(stack, (BrickletJoystick) device);
        }
        if (TinkerforgeDeviceClass.LinearPoti == TinkerforgeDeviceClass.getDevice(device)) {
            return new LinearPotiDevice(stack, (BrickletLinearPoti) device);
        }
        if (TinkerforgeDeviceClass.RotaryPoti == TinkerforgeDeviceClass.getDevice(device)) {
            return new RotaryPotiDevice(stack, (BrickletRotaryPoti) device);
        }
        if (TinkerforgeDeviceClass.RotaryEncoder == TinkerforgeDeviceClass.getDevice(device)) {
            return new RotaryEncoderDevice(stack, (BrickletRotaryEncoder) device);
        }
        if (TinkerforgeDeviceClass.SolidState == TinkerforgeDeviceClass.getDevice(device)) {
            return new SolidStateRelayDevice(stack, (BrickletSolidStateRelay) device);
        }
        if (TinkerforgeDeviceClass.MultiTouch == TinkerforgeDeviceClass.getDevice(device)) {
            return new MultiTouchDevice(stack, (BrickletMultiTouch) device);
        }
        if (TinkerforgeDeviceClass.AnalogInV2 == TinkerforgeDeviceClass.getDevice(device)) {
            return new AnalogInV2Device(stack, (BrickletAnalogInV2) device);
        }
        if (TinkerforgeDeviceClass.AnalogOutV2 == TinkerforgeDeviceClass.getDevice(device)) {
            return new AnalogOutV2Device(stack, (BrickletAnalogOutV2) device);
        }
        if (TinkerforgeDeviceClass.PiezoSpeaker == TinkerforgeDeviceClass.getDevice(device)) {
            return new PiezoSpeakerDevice(stack, (BrickletPiezoSpeaker) device);
        }
        if (TinkerforgeDeviceClass.SoundIntensity == TinkerforgeDeviceClass.getDevice(device)) {
            return new SoundIntensityDevice(stack, (BrickletSoundIntensity) device);
        }
        if (TinkerforgeDeviceClass.IMUV2 == TinkerforgeDeviceClass.getDevice(device)) {
            return new IMUV2Device(stack, (BrickIMUV2) device);
        }
        if (TinkerforgeDeviceClass.IMU == TinkerforgeDeviceClass.getDevice(device)) {
            return new IMUDevice(stack, (BrickIMU) device);
        }
        if (TinkerforgeDeviceClass.Line == TinkerforgeDeviceClass.getDevice(device)) {
            return new LineDevice(stack, (BrickletLine) device);
        }
        if (TinkerforgeDeviceClass.LCD20x4 == TinkerforgeDeviceClass.getDevice(device)) {
            return new LCD20x4Device(stack, (BrickletLCD20x4) device);
        }
        if (TinkerforgeDeviceClass.LCD16x2 == TinkerforgeDeviceClass.getDevice(device)) {
            return new LCD16x2Device(stack, (BrickletLCD16x2) device);
        }
        if (TinkerforgeDeviceClass.Temperature == TinkerforgeDeviceClass.getDevice(device)) {
            return new TemperatureDevice(stack, (BrickletTemperature) device);
        }
        if (TinkerforgeDeviceClass.LoadCell == TinkerforgeDeviceClass.getDevice(device)) {
            return new LoadCellDevice(stack, (BrickletLoadCell) device);
        }
        if (TinkerforgeDeviceClass.RealTimeClock == TinkerforgeDeviceClass.getDevice(device)) {
            return new RealTimeClockDevice(stack, (BrickletRealTimeClock) device);
        }
        if (TinkerforgeDeviceClass.ThermoCouple == TinkerforgeDeviceClass.getDevice(device)) {
            return new ThermoCoupleDevice(stack, (BrickletThermocouple) device);
        }
        if (TinkerforgeDeviceClass.PTC == TinkerforgeDeviceClass.getDevice(device)) {
            return new PTCDevice(stack, (BrickletPTC) device);
        }
        if (TinkerforgeDeviceClass.GPS == TinkerforgeDeviceClass.getDevice(device)) {
            return new GPSDevice(stack, (BrickletGPS) device);
        }
        if (TinkerforgeDeviceClass.GPSv2 == TinkerforgeDeviceClass.getDevice(device)) {
            return new GPSv2Device(stack, (BrickletGPSV2) device);
        }
        if (TinkerforgeDeviceClass.Master == TinkerforgeDeviceClass.getDevice(device)) {
            return new MasterDevice(stack, (BrickMaster) device);
        }

        if (TinkerforgeDeviceClass.NfcRfid == TinkerforgeDeviceClass.getDevice(device)) {
            return new NFCRFIDDevice(stack, (BrickletNFCRFID) device);
        }
        if (TinkerforgeDeviceClass.VoltageCurrent == TinkerforgeDeviceClass.getDevice(device)) {
            return new VoltageCurrentDevice(stack, (BrickletVoltageCurrent) device);
        }
        if (TinkerforgeDeviceClass.Servo == TinkerforgeDeviceClass.getDevice(device)) {
            return new ServoDevice(stack, (BrickServo) device);
        }
        if(TinkerforgeDeviceClass.MotorizedLinearPoti==TinkerforgeDeviceClass.getDevice(device)){
            return new MotorizedLinearPotiDevice(stack, (BrickletMotorizedLinearPoti)device);
        }
        if(TinkerforgeDeviceClass.RGBLEDButton==TinkerforgeDeviceClass.getDevice(device)){
            return new RGBLEDButtonDevice(stack, (BrickletRGBLEDButton)device);
        }
        if(TinkerforgeDeviceClass.ThermalImaging==TinkerforgeDeviceClass.getDevice(device)){
            return new ThermalImagingDevice(stack, (BrickletThermalImaging)device);
        }
        return new TinkerforgeDevice(stack, device);
    }
}
