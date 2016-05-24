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
package ch.quantasy.gateway.service.device.soundIntensity;
import ch.quantasy.gateway.service.device.AbstractDeviceService;
import ch.quantasy.tinkerforge.device.soundIntensity.DeviceSoundIntensityCallbackThreshold;
import ch.quantasy.tinkerforge.device.soundIntensity.SoundIntensityDevice;
import ch.quantasy.tinkerforge.device.soundIntensity.SoundIntensityDeviceCallback;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 *
 * @author reto
 */
public class SoundIntensityService extends AbstractDeviceService<SoundIntensityDevice, SoundIntensityServiceContract> implements SoundIntensityDeviceCallback {

    public SoundIntensityService(SoundIntensityDevice device,URI mqttURI) throws MqttException {

        super(device, new SoundIntensityServiceContract(device),mqttURI);
        addDescription(getServiceContract().INTENT_SOUND_INTENSITY_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        addDescription(getServiceContract().INTENT_DEBOUNCE_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        addDescription(getServiceContract().INTENT_SOUND_INTENSITY_THRESHOLD, "option: [x|o|i|<|>]\n min: [0..10000]\n max: [0..10000]");
        
        addDescription(getServiceContract().EVENT_INTENSITY, "timestamp: [0.." + Long.MAX_VALUE + "]\n value: [0..10000]\n");
        addDescription(getServiceContract().EVENT_INTENSITY_REACHED, "timestamp: [0.." + Long.MAX_VALUE + "]\n value: [0..10000]\n");
        addDescription(getServiceContract().STATUS_SOUND_INTENSITY_CALLBACK_PERIOD, "[0.." + Long.MAX_VALUE + "]");
        addDescription(getServiceContract().STATUS_SOUND_INTENSITY_THRESHOLD, "option: [x|o|i|<|>]\n min: [0..10000]\n max: [0..10000]");
        addDescription(getServiceContract().STATUS_DEBOUNCE_PERIOD, "[0.." + Long.MAX_VALUE + "]");

    }

    @Override
    public void messageArrived(String string, MqttMessage mm) {
        byte[] payload = mm.getPayload();
        if (payload == null) {
            return;

        }
        try {
            if (string.startsWith(getServiceContract().INTENT_DEBOUNCE_PERIOD)) {

                Long period = getMapper().readValue(payload, Long.class);
                getDevice().setDebouncePeriod(period);
            }
            if (string.startsWith(getServiceContract().INTENT_SOUND_INTENSITY_CALLBACK_PERIOD)) {

                Long period = getMapper().readValue(payload, Long.class);
                getDevice().setSoundIntensityCallbackPeriod(period);
            }

            if (string.startsWith(getServiceContract().INTENT_SOUND_INTENSITY_THRESHOLD)) {

                DeviceSoundIntensityCallbackThreshold threshold = getMapper().readValue(payload, DeviceSoundIntensityCallbackThreshold.class);
                getDevice().setSoundIntensityCallbackThreshold(threshold);
            }

        } catch (Exception ex) {
            Logger.getLogger(SoundIntensityService.class
                    .getName()).log(Level.SEVERE, null, ex);
            return;
        }

    }


    @Override
    public void debouncePeriodChanged(long period) {
        addStatus(getServiceContract().STATUS_DEBOUNCE_PERIOD, period);
    }

    @Override
    public void soundIntensityCallbackPeriodChanged(long period) {
        addStatus(getServiceContract().STATUS_SOUND_INTENSITY_CALLBACK_PERIOD, period);
    }

    @Override
    public void soundIntensityCallbackThresholdChanged(DeviceSoundIntensityCallbackThreshold threshold) {
        addStatus(getServiceContract().STATUS_SOUND_INTENSITY_THRESHOLD, threshold);
    }

    @Override
    public void intensity(int i) {
        addEvent(getServiceContract().EVENT_INTENSITY, new SoundIntensityEvent(i));
    }

    @Override
    public void intensityReached(int i) {
        addEvent(getServiceContract().EVENT_INTENSITY_REACHED, new SoundIntensityEvent(i));
    }

    

    class SoundIntensityEvent {

        protected long timestamp;
        protected long value;

        public SoundIntensityEvent(long value) {
            this(value, System.currentTimeMillis());
        }

        public SoundIntensityEvent(long value, long timeStamp) {
            this.value = value;
            this.timestamp = timeStamp;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public long getValue() {
            return value;
        }

    }

}
