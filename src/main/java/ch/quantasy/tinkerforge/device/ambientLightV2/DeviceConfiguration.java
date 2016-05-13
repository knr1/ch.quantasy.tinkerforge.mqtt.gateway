/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerforge.device.ambientLightV2;

import ch.quantasy.tinkerforge.device.moisture.*;
import ch.quantasy.tinkerforge.device.humidity.*;
import com.tinkerforge.BrickletAmbientLightV2;
import com.tinkerforge.BrickletHumidity;
import com.tinkerforge.BrickletMoisture;

/**
 *
 * @author reto
 */
public class DeviceConfiguration {
    
    private short illuminanceRange;
    private short integrationTime;

    public DeviceConfiguration() {
    }

    public DeviceConfiguration(short illuminanceRange, short integrationTime) {
        this.illuminanceRange = illuminanceRange;
        this.integrationTime = integrationTime;
    }

    
    public DeviceConfiguration(BrickletAmbientLightV2.Configuration configuration) {
        this(configuration.illuminanceRange,configuration.integrationTime);
    }

    public short getIlluminanceRange() {
        return illuminanceRange;
    }

    public short getIntegrationTime() {
        return integrationTime;
    }
    
    
    
}
