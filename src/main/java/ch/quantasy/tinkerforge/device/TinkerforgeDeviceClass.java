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

import com.tinkerforge.BrickDC;
import com.tinkerforge.BrickIMU;
import com.tinkerforge.BrickIMUV2;
import com.tinkerforge.BrickMaster;
import com.tinkerforge.BrickServo;
import com.tinkerforge.BrickStepper;
import com.tinkerforge.BrickletAccelerometer;
import com.tinkerforge.BrickletAmbientLight;
import com.tinkerforge.BrickletAmbientLightV2;
import com.tinkerforge.BrickletAnalogIn;
import com.tinkerforge.BrickletAnalogInV2;
import com.tinkerforge.BrickletAnalogOut;
import com.tinkerforge.BrickletAnalogOutV2;
import com.tinkerforge.BrickletBarometer;
import com.tinkerforge.BrickletCO2;
import com.tinkerforge.BrickletColor;
import com.tinkerforge.BrickletCurrent12;
import com.tinkerforge.BrickletCurrent25;
import com.tinkerforge.BrickletDistanceIR;
import com.tinkerforge.BrickletDistanceUS;
import com.tinkerforge.BrickletDualButton;
import com.tinkerforge.BrickletDualRelay;
import com.tinkerforge.BrickletDustDetector;
import com.tinkerforge.BrickletGPS;
import com.tinkerforge.BrickletGPSV2;
import com.tinkerforge.BrickletHallEffect;
import com.tinkerforge.BrickletHumidity;
import com.tinkerforge.BrickletIO16;
import com.tinkerforge.BrickletIO4;
import com.tinkerforge.BrickletIndustrialDigitalIn4;
import com.tinkerforge.BrickletIndustrialDigitalOut4;
import com.tinkerforge.BrickletIndustrialDual020mA;
import com.tinkerforge.BrickletIndustrialQuadRelay;
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
import com.tinkerforge.BrickletPiezoBuzzer;
import com.tinkerforge.BrickletPiezoSpeaker;
import com.tinkerforge.BrickletRGBLEDButton;
import com.tinkerforge.BrickletRS232;
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
import com.tinkerforge.BrickletVoltage;
import com.tinkerforge.BrickletVoltageCurrent;
import com.tinkerforge.Device;
import com.tinkerforge.Device.Identity;

/**
 * This enum is used as a convenience to map the Tinker-Bricks and
 * Tinker-Bricklets It tries to hide the 'strange' stuff from the Java-Developer
 * ;-)
 *
 * @author reto
 *
 */
public enum TinkerforgeDeviceClass {

    DC(BrickDC.class), IMU(BrickIMU.class), IMUV2(BrickIMUV2.class), Master(BrickMaster.class), Servo(BrickServo.class), Stepper(
            BrickStepper.class), Accelerometer(BrickletAccelerometer.class), AmbientLight(BrickletAmbientLight.class),
    AmbientLightV2(BrickletAmbientLightV2.class), AnalogIn(BrickletAnalogIn.class), AnalogInV2(BrickletAnalogInV2.class), AnalogOut(
            BrickletAnalogOut.class), AnalogOutV2(BrickletAnalogOutV2.class), Barometer(BrickletBarometer.class), CO2(BrickletCO2.class), Color(
            BrickletColor.class), Current12(BrickletCurrent12.class), Current25(
            BrickletCurrent25.class), DistanceIR(BrickletDistanceIR.class), DistanceUS(BrickletDistanceUS.class), DualButton(
            BrickletDualButton.class), DualRelay(BrickletDualRelay.class), DustDetector(BrickletDustDetector.class), GPS(BrickletGPS.class), GPSv2(BrickletGPSV2.class),
    HallEffect(
            BrickletHallEffect.class), Humidity(BrickletHumidity.class), IndustrialDigitalIn4(
            BrickletIndustrialDigitalIn4.class), IndustrialDigitalOut4(BrickletIndustrialDigitalOut4.class), IndustrialDual020mA(
            BrickletIndustrialDual020mA.class), IndustrialQuadRelay(BrickletIndustrialQuadRelay.class), IO16(
            BrickletIO16.class), IO4(BrickletIO4.class), Joystick(BrickletJoystick.class), LaserRangeFinder(BrickletLaserRangeFinder.class), LCD16x2(
            BrickletLCD16x2.class), LCD20x4(BrickletLCD20x4.class), LEDStrip(BrickletLEDStrip.class), Line(
            BrickletLine.class), LinearPoti(BrickletLinearPoti.class), LoadCell(BrickletLoadCell.class), Moisture(BrickletMoisture.class), MotionDetector(
            BrickletMotionDetector.class), MultiTouch(BrickletMultiTouch.class), NfcRfid(BrickletNFCRFID.class), PiezoBuzzer(BrickletPiezoBuzzer.class), PiezoSpeaker(
            BrickletPiezoSpeaker.class), MotorizedLinearPoti(BrickletMotorizedLinearPoti.class),PTC(BrickletPTC.class), RealTimeClock(BrickletRealTimeClock.class), RemoteSwitch(BrickletRemoteSwitch.class), RGBLEDButton(BrickletRGBLEDButton.class), RotaryEncoder(
            BrickletRotaryEncoder.class), RotaryPoti(BrickletRotaryPoti.class), RS232(BrickletRS232.class), SegmentDisplay4x7(
            BrickletSegmentDisplay4x7.class), SolidState(BrickletSolidStateRelay.class), SoundIntensity(BrickletSoundIntensity.class), Temperature(
            BrickletTemperature.class), TemperatureIR(BrickletTemperatureIR.class), ThermalImaging(BrickletThermalImaging.class),ThermoCouple(BrickletThermocouple.class), Tilt(BrickletTilt.class), UVLight(BrickletUVLight.class), Voltage(
            BrickletVoltage.class), VoltageCurrent(BrickletVoltageCurrent.class);

    public final int identifier;
    public final Class<?> deviceClass;

    private TinkerforgeDeviceClass(final Class<?> deviceClass) {
        if (deviceClass == null) {
            throw new IllegalArgumentException();
        }
        this.deviceClass = deviceClass;
        int internalIdentifier = -1;
        try {
            internalIdentifier = deviceClass.getField("DEVICE_IDENTIFIER").getInt(null);
        } catch (final Exception e) {
            // No identifier
        }
        this.identifier = internalIdentifier;
    }

    public static String toString(final Device device) {
        try {
            return device.getIdentity().toString();
        } catch (final Exception e) {
            return device.toString();
        }
    }

    public static boolean areEqual(final Device device1, final Device device2) {
        Identity id1 = null;
        Identity id2 = null;
        try {
            id1 = device1.getIdentity();
        } catch (final Exception ex) {

        }
        try {
            id2 = device2.getIdentity();
        } catch (final Exception ex) {

        }
        if ((id1 == null) && (id2 == null)) {
            return TinkerforgeDeviceClass.getDevice(device1) == TinkerforgeDeviceClass.getDevice(device2);
        }
        if ((id1 == null) || (id2 == null)) {
            return false;
        }
        if (id1.deviceIdentifier != id2.deviceIdentifier) {
            return false;
        }
        if (!id1.uid.equals(id2.uid)) {
            return false;
        }
        if (!id1.connectedUid.equals(id2.connectedUid)) {
            return false;
        }
        if (id1.position != id2.position) {
            return false;
        }
        return true;
    }

    public static TinkerforgeDeviceClass getDevice(final Device device) {
        if (device == null) {
            return null;
        }
        final Class<? extends Device> deviceClass = device.getClass();
        for (final TinkerforgeDeviceClass tinkerforgeDevice : TinkerforgeDeviceClass.values()) {
            if (deviceClass == tinkerforgeDevice.deviceClass) {
                return tinkerforgeDevice;
            }
        }
        return null;
    }

    public static TinkerforgeDeviceClass getDevice(final int deviceIdentifier) {
        for (final TinkerforgeDeviceClass tinkerforgeDevice : TinkerforgeDeviceClass.values()) {
            if (deviceIdentifier == tinkerforgeDevice.identifier) {
                return tinkerforgeDevice;
            }
        }
        return null;
    }

}
