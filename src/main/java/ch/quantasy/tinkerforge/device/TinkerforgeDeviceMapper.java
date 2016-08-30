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

import ch.quantasy.tinkerforge.device.IMU.IMUDevice;
import ch.quantasy.tinkerforge.device.IMUV2.IMUV2Device;
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
import ch.quantasy.tinkerforge.device.hallEffect.HallEffectDevice;
import ch.quantasy.tinkerforge.device.humidity.HumidityDevice;
import ch.quantasy.tinkerforge.device.joystick.JoystickDevice;
import ch.quantasy.tinkerforge.device.laserRangeFinder.LaserRangeFinderDevice;
import ch.quantasy.tinkerforge.device.led.LEDStripDevice;
import ch.quantasy.tinkerforge.device.line.LineDevice;
import ch.quantasy.tinkerforge.device.linearPoti.LinearPotiDevice;
import ch.quantasy.tinkerforge.device.loadCell.LoadCellDevice;
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
import ch.quantasy.tinkerforge.device.solidState.SolidStateRelayDevice;
import ch.quantasy.tinkerforge.device.soundIntensity.SoundIntensityDevice;
import ch.quantasy.tinkerforge.device.temperature.TemperatureDevice;
import ch.quantasy.tinkerforge.device.temperatureIR.TemperatureIRDevice;
import ch.quantasy.tinkerforge.device.thermoCouple.ThermoCoupleDevice;
import ch.quantasy.tinkerforge.device.tilt.TiltDevice;
import ch.quantasy.tinkerforge.device.uvLight.UVLightDevice;
import ch.quantasy.tinkerforge.stack.TinkerforgeStackAddress;
import com.tinkerforge.BrickDC;
import com.tinkerforge.BrickIMU;
import com.tinkerforge.BrickIMUV2;
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
import com.tinkerforge.BrickletMultiTouch;
import com.tinkerforge.BrickletNFCRFID;
import com.tinkerforge.BrickletPTC;
import com.tinkerforge.BrickletPiezoSpeaker;
import com.tinkerforge.BrickletRealTimeClock;
import com.tinkerforge.BrickletRemoteSwitch;
import com.tinkerforge.BrickletRotaryEncoder;
import com.tinkerforge.BrickletRotaryPoti;
import com.tinkerforge.BrickletSegmentDisplay4x7;
import com.tinkerforge.BrickletSolidStateRelay;
import com.tinkerforge.BrickletSoundIntensity;
import com.tinkerforge.BrickletTemperature;
import com.tinkerforge.BrickletTemperatureIR;
import com.tinkerforge.BrickletThermocouple;
import com.tinkerforge.BrickletTilt;
import com.tinkerforge.BrickletUVLight;
import com.tinkerforge.Device;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;

/**
 *
 * @author reto
 */
public class TinkerforgeDeviceMapper {

    public static TinkerforgeDevice getTinkerforgeDevice(TinkerforgeStackAddress address, Device device) throws NotConnectedException, TimeoutException {
        if (TinkerforgeDeviceClass.Humidity == TinkerforgeDeviceClass.getDevice(device)) {
            return new HumidityDevice(address, (BrickletHumidity) device);
        }
        if (TinkerforgeDeviceClass.Moisture == TinkerforgeDeviceClass.getDevice(device)) {
            return new MoistureDevice(address, (BrickletMoisture) device);
        }
        if (TinkerforgeDeviceClass.LEDStrip == TinkerforgeDeviceClass.getDevice(device)) {
            return new LEDStripDevice(address, (BrickletLEDStrip) device);
        }
        if (TinkerforgeDeviceClass.MotionDetector == TinkerforgeDeviceClass.getDevice(device)) {
            return new MotionDetectorDevice(address, (BrickletMotionDetector) device);
        }
        if (TinkerforgeDeviceClass.AmbientLight == TinkerforgeDeviceClass.getDevice(device)) {
            return new AmbientLightDevice(address, (BrickletAmbientLight) device);
        }
        if (TinkerforgeDeviceClass.AmbientLightV2 == TinkerforgeDeviceClass.getDevice(device)) {
            return new AmbientLightV2Device(address, (BrickletAmbientLightV2) device);
        }
        if (TinkerforgeDeviceClass.RemoteSwitch == TinkerforgeDeviceClass.getDevice(device)) {
            return new RemoteSwitchDevice(address, (BrickletRemoteSwitch) device);
        }
        if (TinkerforgeDeviceClass.DualRelay == TinkerforgeDeviceClass.getDevice(device)) {
            return new DualRelayDevice(address, (BrickletDualRelay) device);
        }
        if (TinkerforgeDeviceClass.Barometer == TinkerforgeDeviceClass.getDevice(device)) {
            return new BarometerDevice(address, (BrickletBarometer) device);
        }
        if (TinkerforgeDeviceClass.DualButton == TinkerforgeDeviceClass.getDevice(device)) {
            return new DualButtonDevice(address, (BrickletDualButton) device);
        }
        if (TinkerforgeDeviceClass.CO2 == TinkerforgeDeviceClass.getDevice(device)) {
            return new CO2Device(address, (BrickletCO2) device);
        }
        if (TinkerforgeDeviceClass.DC == TinkerforgeDeviceClass.getDevice(device)) {
            return new DCDevice(address, (BrickDC) device);
        }
        if (TinkerforgeDeviceClass.DistanceUS == TinkerforgeDeviceClass.getDevice(device)) {
            return new DistanceUSDevice(address, (BrickletDistanceUS) device);
        }
        if (TinkerforgeDeviceClass.TemperatureIR == TinkerforgeDeviceClass.getDevice(device)) {
            return new TemperatureIRDevice(address, (BrickletTemperatureIR) device);
        }
        if (TinkerforgeDeviceClass.UVLight == TinkerforgeDeviceClass.getDevice(device)) {
            return new UVLightDevice(address, (BrickletUVLight) device);
        }
        if (TinkerforgeDeviceClass.DistanceIR == TinkerforgeDeviceClass.getDevice(device)) {
            return new DistanceIRDevice(address, (BrickletDistanceIR) device);
        }
        if (TinkerforgeDeviceClass.DustDetector == TinkerforgeDeviceClass.getDevice(device)) {
            return new DustDetectorDevice(address, (BrickletDustDetector) device);
        }
        if (TinkerforgeDeviceClass.Tilt == TinkerforgeDeviceClass.getDevice(device)) {
            return new TiltDevice(address, (BrickletTilt) device);
        }
        if (TinkerforgeDeviceClass.Accelerometer == TinkerforgeDeviceClass.getDevice(device)) {
            return new AccelerometerDevice(address, (BrickletAccelerometer) device);
        }
        if (TinkerforgeDeviceClass.Color == TinkerforgeDeviceClass.getDevice(device)) {
            return new ColorDevice(address, (BrickletColor) device);
        }
        if (TinkerforgeDeviceClass.SegmentDisplay4x7 == TinkerforgeDeviceClass.getDevice(device)) {
            return new Segment4x7Device(address, (BrickletSegmentDisplay4x7) device);
        }
        if (TinkerforgeDeviceClass.LaserRangeFinder == TinkerforgeDeviceClass.getDevice(device)) {
            return new LaserRangeFinderDevice(address, (BrickletLaserRangeFinder) device);
        }
        if (TinkerforgeDeviceClass.HallEffect == TinkerforgeDeviceClass.getDevice(device)) {
            return new HallEffectDevice(address, (BrickletHallEffect) device);
        }
        if (TinkerforgeDeviceClass.Joystick == TinkerforgeDeviceClass.getDevice(device)) {
            return new JoystickDevice(address, (BrickletJoystick) device);
        }
        if (TinkerforgeDeviceClass.LinearPoti == TinkerforgeDeviceClass.getDevice(device)) {
            return new LinearPotiDevice(address, (BrickletLinearPoti) device);
        }
        if (TinkerforgeDeviceClass.RotaryPoti == TinkerforgeDeviceClass.getDevice(device)) {
            return new RotaryPotiDevice(address, (BrickletRotaryPoti) device);
        }
        if (TinkerforgeDeviceClass.RotaryEncoder == TinkerforgeDeviceClass.getDevice(device)) {
            return new RotaryEncoderDevice(address, (BrickletRotaryEncoder) device);
        }
        if (TinkerforgeDeviceClass.SolidState == TinkerforgeDeviceClass.getDevice(device)) {
            return new SolidStateRelayDevice(address, (BrickletSolidStateRelay) device);
        }
        if (TinkerforgeDeviceClass.MultiTouch == TinkerforgeDeviceClass.getDevice(device)) {
            return new MultiTouchDevice(address, (BrickletMultiTouch) device);
        }
        if (TinkerforgeDeviceClass.AnalogInV2 == TinkerforgeDeviceClass.getDevice(device)) {
            return new AnalogInV2Device(address, (BrickletAnalogInV2) device);
        }
        if (TinkerforgeDeviceClass.AnalogOutV2 == TinkerforgeDeviceClass.getDevice(device)) {
            return new AnalogOutV2Device(address, (BrickletAnalogOutV2) device);
        }
        if (TinkerforgeDeviceClass.PiezoSpeaker == TinkerforgeDeviceClass.getDevice(device)) {
            return new PiezoSpeakerDevice(address, (BrickletPiezoSpeaker) device);
        }
        if (TinkerforgeDeviceClass.SoundIntensity == TinkerforgeDeviceClass.getDevice(device)) {
            return new SoundIntensityDevice(address, (BrickletSoundIntensity) device);
        }
        if (TinkerforgeDeviceClass.IMUV2 == TinkerforgeDeviceClass.getDevice(device)) {
            return new IMUV2Device(address, (BrickIMUV2) device);
        }
        if (TinkerforgeDeviceClass.IMU == TinkerforgeDeviceClass.getDevice(device)) {
            return new IMUDevice(address, (BrickIMU) device);
        }
        if (TinkerforgeDeviceClass.Line == TinkerforgeDeviceClass.getDevice(device)) {
            return new LineDevice(address, (BrickletLine) device);
        }
        if (TinkerforgeDeviceClass.LCD20x4 == TinkerforgeDeviceClass.getDevice(device)) {
            return new LCD20x4Device(address, (BrickletLCD20x4) device);
        }
        if (TinkerforgeDeviceClass.LCD16x2 == TinkerforgeDeviceClass.getDevice(device)) {
            return new LCD16x2Device(address, (BrickletLCD16x2) device);
        }
        if (TinkerforgeDeviceClass.Temperature == TinkerforgeDeviceClass.getDevice(device)) {
            return new TemperatureDevice(address, (BrickletTemperature) device);
        }
        if (TinkerforgeDeviceClass.LoadCell == TinkerforgeDeviceClass.getDevice(device)) {
            return new LoadCellDevice(address, (BrickletLoadCell) device);
        }
        if (TinkerforgeDeviceClass.RealTimeClock == TinkerforgeDeviceClass.getDevice(device)) {
            return new RealTimeClockDevice(address, (BrickletRealTimeClock) device);
        }
        if (TinkerforgeDeviceClass.ThermoCouple == TinkerforgeDeviceClass.getDevice(device)) {
            return new ThermoCoupleDevice(address, (BrickletThermocouple) device);
        }
        if (TinkerforgeDeviceClass.PTC == TinkerforgeDeviceClass.getDevice(device)) {
            return new PTCDevice(address, (BrickletPTC) device);
        }
        if (TinkerforgeDeviceClass.GPS == TinkerforgeDeviceClass.getDevice(device)) {
            return new GPSDevice(address, (BrickletGPS) device);
        }
        
        if(TinkerforgeDeviceClass.NfcRfid == TinkerforgeDeviceClass.getDevice(device)){
            return new NFCRFIDDevice(address, (BrickletNFCRFID)device);
        }
        return new TinkerforgeDevice(address, device);
    }
}
