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
package ch.quantasy.gateway.intent.realTimeClock;

import ch.quantasy.gateway.intent.annotations.AValidator;
import ch.quantasy.gateway.intent.annotations.Range;
import ch.quantasy.gateway.intent.annotations.Validator;
import com.tinkerforge.BrickletRealTimeClock;

/**
 *
 * @author reto
 */
public class AlarmParamter extends AValidator {

    public static final byte MATCH_DISABLED = -1;

    public static enum WeekDay implements Validator {
        monday((byte) 1), tuesday((byte) 2), wednesday((byte) 3), thursday((byte) 4), friday((byte) 5), saturday((byte) 6), sunday((byte) 7), disabled(MATCH_DISABLED);
        private byte value;

        private WeekDay(byte value) {
            this.value = value;
        }

        public byte getValue() {
            return value;
        }

        public static WeekDay getWeekdayFor(byte s) throws IllegalArgumentException {
            for (WeekDay weekday : values()) {
                if (weekday.value == s) {
                    return weekday;
                }
            }
            throw new IllegalArgumentException("Not supported: " + s);
        }

        @Override
        public boolean isValid() {
            try {
                getWeekdayFor(value);
                return true;
            } catch (IllegalArgumentException ex) {
                return false;
            }
        }
    }

    @Range(from = 1, to = 12)
    private byte month;
    @Range(from = 1, to = 31)
    private byte day;
    @Range(from = 0, to = 23)
    private byte hour;
    @Range(from = 0, to = 59)
    private byte minute;
    @Range(from = 0, to = 59)
    private byte second;
    private WeekDay weekday;
    @Range(from = -1, to = Integer.MAX_VALUE)
    private int interval;

    private AlarmParamter() {
    }

    public AlarmParamter(byte month, byte day, byte hour, byte minute, byte second, byte weekday, int interval) {
        this(month, day, hour, minute, second, WeekDay.getWeekdayFor(weekday), interval);
    }

    public AlarmParamter(byte month, byte day, byte hour, byte minute, byte second, String weekday, int interval) {
        this(month, day, hour, minute, second, WeekDay.valueOf(weekday), interval);
    }

    public AlarmParamter(byte month, byte day, byte hour, byte minute, byte second, WeekDay weekday, int interval) {
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        this.second = second;
        this.weekday = weekday;
        this.interval = interval;
    }

    public AlarmParamter(BrickletRealTimeClock.Alarm alarm) {
        this(alarm.month, alarm.day, alarm.hour, alarm.minute, alarm.second, alarm.weekday, alarm.interval);
    }

    public byte getMonth() {
        return month;
    }

    public byte getDay() {
        return day;
    }

    public byte getHour() {
        return hour;
    }

    public byte getMinute() {
        return minute;
    }

    public byte getSecond() {
        return second;
    }

    public WeekDay getWeekday() {
        return weekday;
    }

    public int getInterval() {
        return interval;
    }

}
