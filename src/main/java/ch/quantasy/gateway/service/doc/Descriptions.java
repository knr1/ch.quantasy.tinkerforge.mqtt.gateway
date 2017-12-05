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
package ch.quantasy.gateway.service.doc;

import static ch.quantasy.gateway.service.doc.ClassFinder.find;
import ch.quantasy.gateway.service.TinkerForgeServiceContract;
import ch.quantasy.gateway.service.device.DeviceServiceContract;
import ch.quantasy.gateway.service.device.IMU.IMUServiceContract;
import ch.quantasy.gateway.service.device.IMUV2.IMUV2ServiceContract;
import ch.quantasy.gateway.service.device.LCD16x2.LCD16x2ServiceContract;
import ch.quantasy.gateway.service.device.LCD20x4.LCD20x4ServiceContract;
import ch.quantasy.gateway.service.device.accelerometer.AccelerometerServiceContract;
import ch.quantasy.gateway.service.device.ambientLight.AmbientLightServiceContract;
import ch.quantasy.gateway.service.device.ambientLightV2.AmbientLightV2ServiceContract;
import ch.quantasy.gateway.service.device.analogInV2.AnalogInV2ServiceContract;
import ch.quantasy.gateway.service.device.analogOutV2.AnalogOutV2ServiceContract;
import ch.quantasy.gateway.service.device.barometer.BarometerServiceContract;
import ch.quantasy.gateway.service.device.co2.CO2ServiceContract;
import ch.quantasy.gateway.service.device.color.ColorServiceContract;
import ch.quantasy.gateway.service.device.dc.DCServiceContract;
import ch.quantasy.gateway.service.device.distanceIR.DistanceIRServiceContract;
import ch.quantasy.gateway.service.device.distanceUS.DistanceUSServiceContract;
import ch.quantasy.gateway.service.device.dualButton.DualButtonServiceContract;
import ch.quantasy.gateway.service.device.dustDetector.DustDetectorServiceContract;
import ch.quantasy.gateway.service.device.gps.GPSServiceContract;
import ch.quantasy.gateway.service.device.gpsv2.GPSv2ServiceContract;
import ch.quantasy.gateway.service.device.hallEffect.HallEffectServiceContract;
import ch.quantasy.gateway.service.device.humidity.HumidityServiceContract;
import ch.quantasy.gateway.service.device.joystick.JoystickServiceContract;
import ch.quantasy.gateway.service.device.laserRangeFinder.LaserRangeFinderServiceContract;
import ch.quantasy.gateway.service.device.ledStrip.LEDStripServiceContract;
import ch.quantasy.gateway.service.device.line.LineServiceContract;
import ch.quantasy.gateway.service.device.linearPoti.LinearPotiServiceContract;
import ch.quantasy.gateway.service.device.loadCell.LoadCellServiceContract;
import ch.quantasy.gateway.service.device.master.MasterServiceContract;
import ch.quantasy.gateway.service.device.moisture.MoistureServiceContract;
import ch.quantasy.gateway.service.device.motionDetector.MotionDetectorServiceContract;
import ch.quantasy.gateway.service.device.multiTouch.MultiTouchServiceContract;
import ch.quantasy.gateway.service.device.nfc.NFCServiceContract;
import ch.quantasy.gateway.service.device.piezoSpeaker.PiezoSpeakerServiceContract;
import ch.quantasy.gateway.service.device.ptc.PTCServiceContract;
import ch.quantasy.gateway.service.device.realTimeClock.RealTimeClockServiceContract;
import ch.quantasy.gateway.service.device.remoteSwitch.RemoteSwitchServiceContract;
import ch.quantasy.gateway.service.device.rotaryEncoder.RotaryEncoderServiceContract;
import ch.quantasy.gateway.service.device.rotaryPoti.RotaryPotiServiceContract;
import ch.quantasy.gateway.service.device.segment4x7.Segment4x7ServiceContract;
import ch.quantasy.gateway.service.device.servo.ServoServiceContract;
import ch.quantasy.gateway.service.device.solidStateRelay.SolidStateRelayServiceContract;
import ch.quantasy.gateway.service.device.soundIntensity.SoundIntensityServiceContract;
import ch.quantasy.gateway.service.device.temperature.TemperatureServiceContract;
import ch.quantasy.gateway.service.device.temperatureIR.TemperatureIRServiceContract;
import ch.quantasy.gateway.service.device.thermoCouple.ThermoCoupleServiceContract;
import ch.quantasy.gateway.service.device.tilt.TiltServiceContract;
import ch.quantasy.gateway.service.device.uvLight.UVLightServiceContract;
import ch.quantasy.gateway.service.device.voltageCurrent.VoltageCurrentServiceContract;
import ch.quantasy.gateway.service.stackManager.StackManagerServiceContract;
import ch.quantasy.mqtt.gateway.client.message.AnIntent;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.*;

/**
 *
 * @author reto
 */
public class Descriptions {

    public static void main(String[] args) throws Exception {
        List<Class<?>> classes = find("ch.quantasy.gateway.service");
        SortedSet<String> contractClassNames = new TreeSet();
        for (Class singleClass : classes) {
            if (DeviceServiceContract.class.equals(singleClass.getSuperclass())) {
                contractClassNames.add(singleClass.getName());
            }
            if (TinkerForgeServiceContract.class.equals(singleClass.getSuperclass())) {
                contractClassNames.add(singleClass.getName());

            }
        }

        for (String contractClassName : contractClassNames) {
            try {
                TinkerForgeServiceContract contract = (TinkerForgeServiceContract) (Class.forName(contractClassName).getConstructor(String.class).newInstance("<id>"));
                System.out.println(contract.toMD());
            } catch (Exception ex) {
            }
        }
    }
}
