/*
 *   "TiMqWay"
 *
 *    TiMqWay(tm): A gateway to provide an MQTT-View for the Tinkerforge(tm) world (Tinkerforge-MQTT-Gateway).
 *
 *    Copyright (c) 2015 Bern University of Applied Sciences (BFH),
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
import ch.quantasy.gateway.service.device.accelerometer.AccelerometerService;
import ch.quantasy.gateway.service.device.ambientLight.AmbientLightService;
import ch.quantasy.gateway.service.device.ambientLightV2.AmbientLightV2Service;
import ch.quantasy.gateway.service.device.barometer.BarometerService;
import ch.quantasy.gateway.service.device.co2.CO2Service;
import ch.quantasy.gateway.service.device.color.ColorService;
import ch.quantasy.gateway.service.device.dc.DCService;
import ch.quantasy.gateway.service.device.distanceIR.DistanceIRService;
import ch.quantasy.gateway.service.device.distanceUS.DistanceUSService;
import ch.quantasy.gateway.service.device.dualRelay.DualRelayService;
import ch.quantasy.gateway.service.device.humidity.HumidityService;
import ch.quantasy.gateway.service.device.ledStrip.LEDStripService;
import ch.quantasy.gateway.service.device.moisture.MoistureService;
import ch.quantasy.gateway.service.device.motionDetector.MotionDetectorService;
import ch.quantasy.gateway.service.device.remoteSwitch.RemoteSwitchService;
import ch.quantasy.gateway.service.device.dualButton.DualButtonService;
import ch.quantasy.gateway.service.device.dustDetector.DustDetectorService;
import ch.quantasy.gateway.service.device.hallEffect.HallEffectService;
import ch.quantasy.gateway.service.device.joystick.JoystickService;
import ch.quantasy.gateway.service.device.laserRangeFinder.LaserRangeFinderService;
import ch.quantasy.gateway.service.device.linearPoti.LinearPotiService;
import ch.quantasy.gateway.service.device.rotaryEncoder.RotaryEncoderService;
import ch.quantasy.gateway.service.device.rotaryPoti.RotaryPotiService;
import ch.quantasy.gateway.service.device.segment4x7.Segment4x7Service;
import ch.quantasy.gateway.service.device.solidStateRelay.SolidStateRelayService;
import ch.quantasy.gateway.service.device.temperatureIR.TemperatureIRService;
import ch.quantasy.gateway.service.device.tilt.TiltService;
import ch.quantasy.gateway.service.device.uvLight.UVLightService;
import ch.quantasy.tinkerforge.device.humidity.HumidityDevice;
import ch.quantasy.tinkerforge.device.TinkerforgeDevice;
import ch.quantasy.tinkerforge.device.TinkerforgeDeviceListener;
import ch.quantasy.tinkerforge.device.accelerometer.AccelerometerDevice;
import ch.quantasy.tinkerforge.device.ambientLight.AmbientLightDevice;
import ch.quantasy.tinkerforge.device.ambientLightV2.AmbientLightV2Device;
import ch.quantasy.tinkerforge.device.barometer.BarometerDevice;
import ch.quantasy.tinkerforge.device.co2.CO2Device;
import ch.quantasy.tinkerforge.device.color.ColorDevice;
import ch.quantasy.tinkerforge.device.dc.DCDevice;
import ch.quantasy.tinkerforge.device.distanceIR.DistanceIRDevice;
import ch.quantasy.tinkerforge.device.distanceUS.DistanceUSDevice;
import ch.quantasy.tinkerforge.device.dualButton.DualButtonDevice;
import ch.quantasy.tinkerforge.device.dualRelay.DualRelayDevice;
import ch.quantasy.tinkerforge.device.dustDetector.DustDetectorDevice;
import ch.quantasy.tinkerforge.device.hallEffect.HallEffectDevice;
import ch.quantasy.tinkerforge.device.joystick.JoystickDevice;
import ch.quantasy.tinkerforge.device.laserRangeFinder.LaserRangeFinderDevice;
import ch.quantasy.tinkerforge.device.led.LEDStripDevice;
import ch.quantasy.tinkerforge.device.linearPoti.LinearPotiDevice;
import ch.quantasy.tinkerforge.device.moisture.MoistureDevice;
import ch.quantasy.tinkerforge.device.motionDetector.MotionDetectorDevice;
import ch.quantasy.tinkerforge.device.remoteSwitch.RemoteSwitchDevice;
import ch.quantasy.tinkerforge.device.rotaryEncoder.RotaryEncoderDevice;
import ch.quantasy.tinkerforge.device.rotaryPoti.RotaryPotiDevice;
import ch.quantasy.tinkerforge.device.segment4x7.Segment4x7Device;
import ch.quantasy.tinkerforge.device.solidState.SolidStateRelayDevice;
import ch.quantasy.tinkerforge.device.temperatureIR.TemperatureIRDevice;
import ch.quantasy.tinkerforge.device.tilt.TiltDevice;
import ch.quantasy.tinkerforge.device.uvLight.UVLightDevice;
import ch.quantasy.tinkerforge.factory.TinkerforgeStackFactory;
import ch.quantasy.tinkerforge.stack.TinkerforgeStack;
import ch.quantasy.tinkerforge.stack.TinkerforgeStackAddress;
import java.net.URI;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.paho.client.mqttv3.MqttException;

/**
 *
 * @author reto
 */
public class TinkerForgeManager implements TinkerforgeDeviceListener {

    private final Set<TinkerforgeFactoryListener> listeners;

    private final TinkerforgeStackFactory stackFactory;

    private final URI mqttURI;

    public TinkerForgeManager(URI mqttURI) {
        this.mqttURI = mqttURI;
        stackFactory = TinkerforgeStackFactory.getInstance();
        this.listeners = new HashSet<>();
    }

    public void addListener(TinkerforgeFactoryListener listener) {
        this.listeners.add(listener);

    }

    public void removeListener(TinkerforgeFactoryListener listener) {
        this.listeners.remove(listener);
    }

    public TinkerforgeStack addStack(TinkerforgeStackAddress address) {
        TinkerforgeStack stack = null;
        if (stackFactory.addStack(address)) {
            stack = stackFactory.getStack(address);
            for (TinkerforgeFactoryListener listener : listeners) {
                listener.stackAdded(stack);
            }
            stack.addListener(this);
            stack.connect();
        }
        return stackFactory.getStack(address);
    }

    public TinkerforgeStack removeStack(TinkerforgeStackAddress address) {
        TinkerforgeStack stack = stackFactory.removeStack(address);
        if (stack != null) {
            stack.disconnect();
            for (TinkerforgeFactoryListener listener : listeners) {
                listener.stackRemoved(stack);
            }
            stack.removeListener(this);
        }
        return stack;
    }

    public boolean containsStack(TinkerforgeStackAddress address) {
        return stackFactory.containsStack(address);
    }

    public Set<TinkerforgeStackAddress> getStackAddresses() {
        Set<TinkerforgeStackAddress> stackAddresses = new HashSet<>();
        for (TinkerforgeStack stack : stackFactory.getTinkerforgeStacks()) {
            stackAddresses.add(stack.getStackAddress());
        }
        return stackAddresses;
    }

    public TinkerforgeStackFactory getStackFactory() {
        return stackFactory;
    }

    private Set<TinkerforgeDevice> managedDevices = new HashSet<>();
    private Set<AbstractService> services = new HashSet<>();

    @Override
    public void connected(TinkerforgeDevice tinkerforgeDevice) {
        if (managedDevices.contains(tinkerforgeDevice)) {
            return;
        }
        managedDevices.add(tinkerforgeDevice);
        try {

            if (tinkerforgeDevice instanceof HumidityDevice) {
                HumidityService service = new HumidityService((HumidityDevice) tinkerforgeDevice, mqttURI);
                services.add(service);
            }
            if (tinkerforgeDevice instanceof LEDStripDevice) {
                LEDStripService service = new LEDStripService((LEDStripDevice) tinkerforgeDevice, mqttURI);
                services.add(service);
            }
            if (tinkerforgeDevice instanceof MotionDetectorDevice) {
                MotionDetectorService service = new MotionDetectorService((MotionDetectorDevice) tinkerforgeDevice, mqttURI);
                services.add(service);
            }
            if (tinkerforgeDevice instanceof MoistureDevice) {
                MoistureService service = new MoistureService((MoistureDevice) tinkerforgeDevice, mqttURI);
                services.add(service);
            }
            if (tinkerforgeDevice instanceof AmbientLightDevice) {
                AmbientLightService service = new AmbientLightService((AmbientLightDevice) tinkerforgeDevice, mqttURI);
                services.add(service);
            }
            if (tinkerforgeDevice instanceof AmbientLightV2Device) {
                AmbientLightV2Service service = new AmbientLightV2Service((AmbientLightV2Device) tinkerforgeDevice, mqttURI);
                services.add(service);
            }
            if (tinkerforgeDevice instanceof RemoteSwitchDevice) {
                RemoteSwitchService service = new RemoteSwitchService((RemoteSwitchDevice) tinkerforgeDevice, mqttURI);
                services.add(service);
            }
            if (tinkerforgeDevice instanceof DualRelayDevice) {
                DualRelayService service = new DualRelayService((DualRelayDevice) tinkerforgeDevice, mqttURI);
                services.add(service);
            }
            if (tinkerforgeDevice instanceof BarometerDevice) {
                BarometerService service = new BarometerService((BarometerDevice) tinkerforgeDevice, mqttURI);
                services.add(service);
            }
            if (tinkerforgeDevice instanceof DualButtonDevice) {
                DualButtonService service = new DualButtonService((DualButtonDevice) tinkerforgeDevice, mqttURI);
                services.add(service);
            }
            if (tinkerforgeDevice instanceof CO2Device) {
                CO2Service service = new CO2Service((CO2Device) tinkerforgeDevice, mqttURI);
                services.add(service);
            }
            if (tinkerforgeDevice instanceof DCDevice) {
                DCService service = new DCService((DCDevice) tinkerforgeDevice, mqttURI);
                services.add(service);
            }
            if (tinkerforgeDevice instanceof DistanceUSDevice) {
                DistanceUSService service = new DistanceUSService((DistanceUSDevice) tinkerforgeDevice, mqttURI);
                services.add(service);
            }
            if (tinkerforgeDevice instanceof TemperatureIRDevice) {
                TemperatureIRService service = new TemperatureIRService((TemperatureIRDevice) tinkerforgeDevice, mqttURI);
                services.add(service);
            }
            if (tinkerforgeDevice instanceof UVLightDevice) {
                UVLightService service = new UVLightService((UVLightDevice) tinkerforgeDevice, mqttURI);
                services.add(service);
            }
            if (tinkerforgeDevice instanceof DistanceIRDevice) {
                DistanceIRService service = new DistanceIRService((DistanceIRDevice) tinkerforgeDevice, mqttURI);
                services.add(service);
            }
            if (tinkerforgeDevice instanceof DustDetectorDevice) {
                DustDetectorService service = new DustDetectorService((DustDetectorDevice) tinkerforgeDevice, mqttURI);
                services.add(service);
            }
            if (tinkerforgeDevice instanceof TiltDevice) {
                TiltService service = new TiltService((TiltDevice) tinkerforgeDevice, mqttURI);
                services.add(service);
            }
            if (tinkerforgeDevice instanceof AccelerometerDevice) {
                AccelerometerService service = new AccelerometerService((AccelerometerDevice) tinkerforgeDevice, mqttURI);
                services.add(service);
            }
            if (tinkerforgeDevice instanceof ColorDevice) {
                ColorService service = new ColorService((ColorDevice) tinkerforgeDevice, mqttURI);
                services.add(service);
            }
            if (tinkerforgeDevice instanceof Segment4x7Device) {
                Segment4x7Service service = new Segment4x7Service((Segment4x7Device) tinkerforgeDevice, mqttURI);
                services.add(service);
            }
            if (tinkerforgeDevice instanceof LaserRangeFinderDevice) {
                LaserRangeFinderService service = new LaserRangeFinderService((LaserRangeFinderDevice) tinkerforgeDevice, mqttURI);
                services.add(service);
            }
            if (tinkerforgeDevice instanceof HallEffectDevice) {
                HallEffectService service = new HallEffectService((HallEffectDevice) tinkerforgeDevice, mqttURI);
                services.add(service);
            }
            if (tinkerforgeDevice instanceof JoystickDevice) {
                JoystickService service = new JoystickService((JoystickDevice) tinkerforgeDevice, mqttURI);
                services.add(service);
            }
            if (tinkerforgeDevice instanceof LinearPotiDevice) {
                LinearPotiService service = new LinearPotiService((LinearPotiDevice) tinkerforgeDevice, mqttURI);
                services.add(service);
            }
            if (tinkerforgeDevice instanceof RotaryPotiDevice) {
                RotaryPotiService service = new RotaryPotiService((RotaryPotiDevice) tinkerforgeDevice, mqttURI);
                services.add(service);
            }
            if (tinkerforgeDevice instanceof RotaryEncoderDevice) {
                RotaryEncoderService service = new RotaryEncoderService((RotaryEncoderDevice) tinkerforgeDevice, mqttURI);
                services.add(service);
            }
            if (tinkerforgeDevice instanceof SolidStateRelayDevice) {
                SolidStateRelayService service = new SolidStateRelayService((SolidStateRelayDevice) tinkerforgeDevice, mqttURI);
                services.add(service);
            }
        } catch (MqttException ex) {
            Logger.getLogger(TinkerForgeManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void reConnected(TinkerforgeDevice tinkerforgeDevice) {
        //Nothing to do
    }

    @Override
    public void disconnected(TinkerforgeDevice tinkerforgeDevice) {
        //Nothing to do 
    }
}
