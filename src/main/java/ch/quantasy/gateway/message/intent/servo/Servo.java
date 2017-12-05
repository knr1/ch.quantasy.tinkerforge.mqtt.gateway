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
package ch.quantasy.gateway.message.intent.servo;

import ch.quantasy.mqtt.gateway.client.message.annotations.AValidator;
import ch.quantasy.mqtt.gateway.client.message.annotations.Range;
import java.util.Objects;

/**
 *
 * @author reto
 */
public class Servo extends AValidator {

    @Range(from = 0, to = 6)
    private int id;
    private Boolean enabled;
    @Range(from = 0, to = 65536)
    private Integer acceleration;
    @Range(from = 0, to = 65536)
    private Integer velocity;
    private Degree degree;
    @Range(from = 1, to = 65536)
    private Integer period;
    @Range(from = -32767, to = 32767)
    private Short position;
    private PulseWidth pulseWidth;

    private Servo() {

    }

    public Servo(int id) {
        this.id = id;
    }

    public Servo update(Servo otherServo) {
        Servo deltaServo = null;
        if (!this.equals(otherServo)) {
            return deltaServo;
        }
        deltaServo = new Servo(this.id);
        if (!Objects.equals(this.acceleration, otherServo.acceleration)) {
            this.acceleration = otherServo.acceleration;
            deltaServo.acceleration = otherServo.acceleration;
        }
        if (!Objects.equals(this.enabled, otherServo.enabled)) {
            this.enabled = otherServo.enabled;
            deltaServo.enabled = otherServo.enabled;
        }
        if (!Objects.equals(this.degree, otherServo.degree)) {
            this.degree = otherServo.degree;
            deltaServo.degree = otherServo.degree;
        }
        if (!Objects.equals(this.pulseWidth, otherServo.pulseWidth)) {
            this.pulseWidth = otherServo.pulseWidth;
            deltaServo.pulseWidth = otherServo.pulseWidth;
        }

        if (!Objects.equals(this.period, otherServo.period)) {
            this.period = otherServo.period;
            deltaServo.period = otherServo.period;
        }
        if (!Objects.equals(this.position, otherServo.position)) {
            this.position = otherServo.position;
            deltaServo.position = otherServo.position;
        }
        if (!Objects.equals(this.velocity, otherServo.velocity)) {
            this.velocity = otherServo.velocity;
            deltaServo.velocity = otherServo.velocity;
        }
        return deltaServo;
    }

    public int getId() {
        return id;
    }

    public Degree getDegree() {
        return degree;
    }

    public void setDegree(Degree degree) {
        this.degree = degree;
    }

    public PulseWidth getPulseWidth() {
        return pulseWidth;
    }

    public void setPulseWidth(PulseWidth pulseWidth) {
        this.pulseWidth = pulseWidth;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Integer getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(Integer acceleration) {
        this.acceleration = acceleration;
    }

    public Integer getVelocity() {
        return velocity;
    }

    public void setVelocity(Integer velocity) {
        this.velocity = velocity;
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public Short getPosition() {
        return position;
    }

    public void setPosition(Short position) {
        this.position = position;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Servo other = (Servo) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

}
