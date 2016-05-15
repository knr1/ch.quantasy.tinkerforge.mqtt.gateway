/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerforge.device.ambientLightV2;

import com.tinkerforge.BrickletAmbientLightV2;

/**
 *
 * @author reto
 */
public class DeviceConfiguration {

    public static enum IlluminanceRange {
        Lux_unlimitted((short) 6), lx_64000((short) 0), lx_32000((short) 1), lx_16000((short) 2), lx_8000((short) 3), lx_1300((short) 4), lx_600((short) 5);
        private short value;

        private IlluminanceRange(short value) {
            this.value = value;
        }

        public short getValue() {
            return value;
        }

        public static IlluminanceRange getIlluminanceRangeFor(short s) throws IllegalArgumentException{
            for (IlluminanceRange range : values()) {
                if (range.value == s) {
                    return range;
                }
            }
            throw new IllegalArgumentException("Not supported: "+s);
        }
    }

    public static enum IntegrationTime {
        ms_50((short) 0), ms_100((short) 1), ms_150((short) 2), ms_200((short) 3), ms_250((short) 4), ms_300((short) 5), ms_350((short) 6), ms_400((short) 7);
        private short value;

        private IntegrationTime(short value) {
            this.value = value;
        }

        public short getValue() {
            return value;
        }

        public static IntegrationTime getIntegrationTimeFor(short s) {
            for (IntegrationTime range : values()) {
                if (range.value == s) {
                    return range;
                }
            }
            return null;
        }
    }
    private IlluminanceRange illuminanceRange;
    private IntegrationTime integrationTime;

    public DeviceConfiguration() {
    }

    public DeviceConfiguration(IlluminanceRange illuminanceRange, IntegrationTime integrationTime) {
        this.illuminanceRange = illuminanceRange;
        this.integrationTime = integrationTime;
    }

    public DeviceConfiguration(short illuminanceRange, short integrationTime) throws IllegalArgumentException {
        this(IlluminanceRange.getIlluminanceRangeFor(illuminanceRange), IntegrationTime.getIntegrationTimeFor(integrationTime));
    }

    public DeviceConfiguration(BrickletAmbientLightV2.Configuration configuration) {
        this(configuration.illuminanceRange, configuration.integrationTime);
    }

    public IlluminanceRange getIlluminanceRange() {
        return illuminanceRange;
    }

    public IntegrationTime getIntegrationTime() {
        return integrationTime;
    }

}
