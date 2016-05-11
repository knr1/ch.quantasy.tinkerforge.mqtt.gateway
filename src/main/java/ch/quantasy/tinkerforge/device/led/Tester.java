/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerforge.device.led;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import java.io.IOException;

/**
 *
 * @author reto
 */
public class Tester {

    public static void main(String[] args) throws JsonProcessingException, IOException {
        LEDStripDeviceConfig config = new LEDStripDeviceConfig(LEDStripDeviceConfig.ChipType.WS2801, 2000000, 10, 2, LEDStripDeviceConfig.ChannelMapping.RGB);
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        String string = mapper.writeValueAsString(config);
        System.out.println(string);

        String serialized = "chipType: WS2801\n"
                + "frameDurationInMilliseconds: 10\n"
                + "numberOfLEDs: 2\n"
                + "clockFrequencyOfICsInHz: 2000000\n"
                + "channelMapping: RGB\n";
        LEDStripDeviceConfig config2=mapper.readValue(serialized, LEDStripDeviceConfig.class);
        System.out.println(config.equals(config2));
    }
}
