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
package ch.quantasy.tinkerforge.device.servo;

import ch.quantasy.gateway.message.intent.servo.Servo;
import ch.quantasy.gateway.message.intent.servo.PulseWidth;
import ch.quantasy.gateway.message.intent.servo.Degree;
import ch.quantasy.gateway.message.intent.servo.ServoIntent;
import ch.quantasy.tinkerforge.device.generic.GenericDevice;
import ch.quantasy.tinkerforge.stack.TinkerforgeStack;
import com.tinkerforge.BrickServo;

import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class ServoDevice extends GenericDevice<BrickServo, ServoDeviceCallback, ServoIntent> {

    public static final int AMOUNT_OF_SERVOS = 7;
    private Map<Integer, Servo> servoMap;

    public ServoDevice(TinkerforgeStack stack, BrickServo device) throws NotConnectedException, TimeoutException {
        super(stack, device, new ServoIntent());
        getIntent().servos = new HashSet<>();
        for (int i = 0; i < 7; i++) {
            Servo servo = new Servo(i);
            servoMap.put(i, servo);
        }
    }

    @Override
    protected void addDeviceListeners(BrickServo device) {
        try {
            device.addPositionReachedListener(super.getCallback());
            device.addUnderVoltageListener(super.getCallback());
            device.addVelocityReachedListener(super.getCallback());
            device.enablePositionReachedCallback();
            device.enableVelocityReachedCallback();
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(ServoDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void removeDeviceListeners(BrickServo device) {
        device.removePositionReachedListener(super.getCallback());
        device.removeUnderVoltageListener(super.getCallback());
        device.removeVelocityReachedListener(super.getCallback());
    }

    @Override
    public void update(ServoIntent intent) {
        if (intent == null) {
            return;
        }
        if (!intent.isValid()) {
            return;
        }
        if (intent.servos != null) {
            List<Servo> deltaList = new ArrayList<>();

            for (Servo servo : intent.servos) {
                deltaList.add(servo.update(servoMap.get(servo.getId())));
            }
            if (!deltaList.isEmpty()) {
                setAcceleration(deltaList);
                setVelocity(deltaList);
                setPeriod(deltaList);
                setPosition(deltaList);
                setDegree(deltaList);
                setPulseWidth(deltaList);
                setEnabled(deltaList);
                getCallback().servosChanged(new TreeSet(this.servoMap.values()));
            }
        }
        if (intent.statusLED != null) {
            try {

                if (intent.statusLED) {

                    getDevice().enableStatusLED();

                } else {
                    getDevice().disableStatusLED();
                }
                getIntent().statusLED = getDevice().isStatusLEDEnabled();
                getCallback().statusLEDChanged(getIntent().statusLED);
            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(ServoDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (intent.minimumVoltage != null) {
            try {

                getDevice().setMinimumVoltage(intent.minimumVoltage);
                getIntent().minimumVoltage = getDevice().getMinimumVoltage();
                getCallback().minimumVoltageChanged(getIntent().minimumVoltage);
            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(ServoDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (intent.outputVoltage != null) {
            try {
                getDevice().setOutputVoltage(intent.outputVoltage);
                getIntent().outputVoltage = getDevice().getOutputVoltage();
                getCallback().outputVoltageChanged(getIntent().outputVoltage);
            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(ServoDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    private void setAcceleration(Collection<Servo> deltaServos) {
        Map<Integer, Short> requestMap = new HashMap<>();
        for (Servo servo : deltaServos) {
            Integer acceleration = servo.getAcceleration();
            if (acceleration != null) {
                Short numServos = requestMap.get(acceleration);
                if (numServos == null) {
                    numServos = (short) (1 << 7);
                }
                numServos = (short) (numServos | 1 << servo.getId());
                requestMap.put(acceleration, numServos);
            }
        }
        for (Map.Entry<Integer, Short> entry : requestMap.entrySet()) {
            try {
                getDevice().setAcceleration(entry.getValue(), entry.getKey());
                for (Servo servo : deltaServos) {
                    servoMap.get(servo.getId()).setAcceleration(getDevice().getAcceleration((short) servo.getId()));
                }
            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(ServoDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void setVelocity(Collection<Servo> deltaServos) {
        Map<Integer, Short> requestMap = new HashMap<>();
        for (Servo servo : deltaServos) {
            Integer velocity = servo.getVelocity();
            if (velocity != null) {
                Short numServos = requestMap.get(velocity);
                if (numServos == null) {
                    numServos = (short) (1 << 7);
                }
                numServos = (short) (numServos | 1 << servo.getId());
                requestMap.put(velocity, numServos);
            }
        }
        for (Map.Entry<Integer, Short> entry : requestMap.entrySet()) {
            try {
                getDevice().setVelocity(entry.getValue(), entry.getKey());
                for (Servo servo : deltaServos) {
                    servoMap.get(servo.getId()).setVelocity(getDevice().getVelocity((short) servo.getId()));
                }
            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(ServoDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void setPeriod(Collection<Servo> deltaServos) {
        Map<Integer, Short> requestMap = new HashMap<>();
        for (Servo servo : deltaServos) {
            Integer period = servo.getPeriod();
            if (period != null) {
                Short numServos = requestMap.get(period);
                if (numServos == null) {
                    numServos = (short) (1 << 7);
                }
                numServos = (short) (numServos | 1 << servo.getId());
                requestMap.put(period, numServos);
            }
        }
        for (Map.Entry<Integer, Short> entry : requestMap.entrySet()) {
            try {
                getDevice().setPeriod(entry.getValue(), entry.getKey());
                for (Servo servo : deltaServos) {
                    servoMap.get(servo.getId()).setPeriod(getDevice().getPeriod((short) servo.getId()));
                }
            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(ServoDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void setPosition(Collection<Servo> deltaServos) {
        Map<Short, Short> requestMap = new HashMap<>();
        for (Servo servo : deltaServos) {
            Short position = servo.getPosition();
            if (position != null) {
                Short numServos = requestMap.get(position);
                if (numServos == null) {
                    numServos = (short) (1 << 7);
                }
                numServos = (short) (numServos | 1 << servo.getId());
                requestMap.put(position, numServos);
            }
        }
        for (Map.Entry<Short, Short> entry : requestMap.entrySet()) {
            try {
                getDevice().setPosition(entry.getValue(), entry.getKey());
                for (Servo servo : deltaServos) {
                    servoMap.get(servo.getId()).setPosition(getDevice().getPosition((short) servo.getId()));
                }
            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(ServoDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void setDegree(Collection<Servo> deltaServos) {
        Map<Degree, Short> requestMap = new HashMap<>();
        for (Servo servo : deltaServos) {
            Degree degree = servo.getDegree();
            if (degree != null) {
                Short numServos = requestMap.get(degree);
                if (numServos == null) {
                    numServos = (short) (1 << 7);
                }
                numServos = (short) (numServos | 1 << servo.getId());
                requestMap.put(degree, numServos);
            }
        }
        for (Map.Entry<Degree, Short> entry : requestMap.entrySet()) {
            try {
                getDevice().setDegree(entry.getValue(), entry.getKey().getMin(), entry.getKey().getMax());
                for (Servo servo : deltaServos) {
                    servoMap.get(servo.getId()).setDegree(new Degree(getDevice().getDegree((short) servo.getId())));
                }
            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(ServoDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void setPulseWidth(Collection<Servo> deltaServos) {
        Map<PulseWidth, Short> requestMap = new HashMap<>();
        for (Servo servo : deltaServos) {
            PulseWidth pulseWidth = servo.getPulseWidth();
            if (pulseWidth != null) {
                Short numServos = requestMap.get(pulseWidth);
                if (numServos == null) {
                    numServos = (short) (1 << 7);
                }
                numServos = (short) (numServos | 1 << servo.getId());
                requestMap.put(pulseWidth, numServos);
            }
        }
        for (Map.Entry<PulseWidth, Short> entry : requestMap.entrySet()) {
            try {
                getDevice().setPulseWidth(entry.getValue(), entry.getKey().getMin(), entry.getKey().getMax());
                for (Servo servo : deltaServos) {
                    servoMap.get(servo.getId()).setPulseWidth(new PulseWidth(getDevice().getPulseWidth((short) servo.getId())));
                }
            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(ServoDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void setEnabled(Collection<Servo> deltaServos) {
        Map<Boolean, Short> requestMap = new HashMap<>();
        for (Servo servo : deltaServos) {
            Boolean enabled = servo.getEnabled();
            if (enabled != null) {
                Short numServos = requestMap.get(enabled);
                if (numServos == null) {
                    numServos = (short) (1 << 7);
                }
                numServos = (short) (numServos | 1 << servo.getId());
                requestMap.put(enabled, numServos);
            }
        }
        for (Map.Entry<Boolean, Short> entry : requestMap.entrySet()) {
            try {
                if (entry.getKey()) {
                    getDevice().enable(entry.getValue());
                } else {
                    getDevice().disable(entry.getValue());
                }
                for (Servo servo : deltaServos) {
                    servoMap.get(servo.getId()).setEnabled(getDevice().isEnabled((short) servo.getId()));
                }
            } catch (TimeoutException | NotConnectedException ex) {
                Logger.getLogger(ServoDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
