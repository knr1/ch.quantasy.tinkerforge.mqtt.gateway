/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerforge.device;

import ch.quantasy.tinkerforge.device.ambientLight.AmbientLightDevice;
import ch.quantasy.tinkerforge.device.ambientLightV2.AmbientLightV2Device;
import ch.quantasy.tinkerforge.device.barometer.BarometerDevice;
import ch.quantasy.tinkerforge.device.co2Device.CO2Device;
import ch.quantasy.tinkerforge.device.dualButton.DualButtonDevice;
import ch.quantasy.tinkerforge.device.dualRelay.DualRelayDevice;
import ch.quantasy.tinkerforge.device.humidity.HumidityDevice;
import ch.quantasy.tinkerforge.device.led.LEDStripDevice;
import ch.quantasy.tinkerforge.device.moisture.MoistureDevice;
import ch.quantasy.tinkerforge.device.motionDetector.MotionDetectorDevice;
import ch.quantasy.tinkerforge.device.remoteSwitch.RemoteSwitchDevice;
import ch.quantasy.tinkerforge.stack.TinkerforgeStackAddress;
import com.tinkerforge.BrickletAmbientLight;
import com.tinkerforge.BrickletAmbientLightV2;
import com.tinkerforge.BrickletBarometer;
import com.tinkerforge.BrickletCO2;
import com.tinkerforge.BrickletDualButton;
import com.tinkerforge.BrickletDualRelay;
import com.tinkerforge.BrickletHumidity;
import com.tinkerforge.BrickletLEDStrip;
import com.tinkerforge.BrickletMoisture;
import com.tinkerforge.BrickletMotionDetector;
import com.tinkerforge.BrickletRemoteSwitch;
import com.tinkerforge.Device;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;

/**
 *
 * @author reto
 */
public class TinkerforgeDeviceMapper {
    public static TinkerforgeDevice getTinkerforgeDevice(TinkerforgeStackAddress address,Device device) throws NotConnectedException, TimeoutException {
        if(TinkerforgeDeviceClass.Humidity==TinkerforgeDeviceClass.getDevice(device)){
            return new HumidityDevice(address,(BrickletHumidity)device);
        }
        if(TinkerforgeDeviceClass.Moisture==TinkerforgeDeviceClass.getDevice(device)){
            return new MoistureDevice(address,(BrickletMoisture)device);
        }
        if(TinkerforgeDeviceClass.LEDStrip==TinkerforgeDeviceClass.getDevice(device)){
            return new LEDStripDevice(address,(BrickletLEDStrip)device);
        }
        if(TinkerforgeDeviceClass.MotionDetector==TinkerforgeDeviceClass.getDevice(device)){
            return new MotionDetectorDevice(address,(BrickletMotionDetector)device);
        }
        if(TinkerforgeDeviceClass.AmbientLight==TinkerforgeDeviceClass.getDevice(device)){
            return new AmbientLightDevice(address,(BrickletAmbientLight)device);
        }
        if(TinkerforgeDeviceClass.AmbientLightV2==TinkerforgeDeviceClass.getDevice(device)){
            return new AmbientLightV2Device(address,(BrickletAmbientLightV2)device);
        }
        if(TinkerforgeDeviceClass.RemoteSwitch==TinkerforgeDeviceClass.getDevice(device)){
            return new RemoteSwitchDevice(address,(BrickletRemoteSwitch)device);
        }
        if(TinkerforgeDeviceClass.DualRelay==TinkerforgeDeviceClass.getDevice(device)){
            return new DualRelayDevice(address,(BrickletDualRelay)device);
        }
        if(TinkerforgeDeviceClass.Barometer==TinkerforgeDeviceClass.getDevice(device)){
            return new BarometerDevice(address,(BrickletBarometer)device);
        }
        if(TinkerforgeDeviceClass.DualButton==TinkerforgeDeviceClass.getDevice(device)){
            return new DualButtonDevice(address,(BrickletDualButton)device);
        }
        if(TinkerforgeDeviceClass.CO2==TinkerforgeDeviceClass.getDevice(device)){
            return new CO2Device(address,(BrickletCO2)device);
        }
        return new TinkerforgeDevice(address, device);
    }
}
