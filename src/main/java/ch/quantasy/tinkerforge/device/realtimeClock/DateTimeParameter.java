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
package ch.quantasy.tinkerforge.device.realtimeClock;

import com.tinkerforge.BrickletRealTimeClock;

/**
 *
 * @author reto
 */
public class DateTimeParameter {

    public static enum WeekDay {
        monday((short) 1), tuesday((short) 2), wednesday((short) 3), thursday((short) 4), friday((short) 5), saturday((short) 6), sunday((short) 7);
        private short value;

        private WeekDay(short value) {
            this.value = value;
        }

        public short getValue() {
            return value;
        }

        public static WeekDay getWeekdayFor(short s) throws IllegalArgumentException {
            for (WeekDay weekday : values()) {
                if (weekday.value == s) {
                    return weekday;
                }
            }
            throw new IllegalArgumentException("Not supported: " + s);
        }
    }

    private int year;
    private short month;
    private short day;
    private short hour;
    private short minute;
    private short second;
    private short centisecond;
    private WeekDay weekday;

    private DateTimeParameter() {
    }

    public DateTimeParameter(int year, short month, short day, short hour, short minute, short second, short centisecond, short weekday) {
        this(year, month, day, hour, minute, second, centisecond, WeekDay.getWeekdayFor(weekday));
    }

    public DateTimeParameter(int year, short month, short day, short hour, short minute, short second, short centisecond, String weekday) {
        this(year, month, day, hour, minute, second, centisecond, WeekDay.valueOf(weekday));
    }

    public DateTimeParameter(int year, short month, short day, short hour, short minute, short second, short centisecond, WeekDay weekday) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        this.second = second;
        this.centisecond = centisecond;
        this.weekday = weekday;
    }

    public DateTimeParameter(BrickletRealTimeClock.DateTime dateTime) {
        this.year = dateTime.year;
        this.month = dateTime.month;
        this.day = dateTime.day;
        this.hour = dateTime.hour;
        this.minute = dateTime.minute;
        this.second = dateTime.second;
        this.centisecond = dateTime.centisecond;
        this.weekday = WeekDay.getWeekdayFor(dateTime.weekday);
    }

    public int getYear() {
        return year;
    }

    public short getMonth() {
        return month;
    }

    public short getDay() {
        return day;
    }

    public short getHour() {
        return hour;
    }

    public short getMinute() {
        return minute;
    }

    public short getSecond() {
        return second;
    }

    public short getCentisecond() {
        return centisecond;
    }

    public WeekDay getWeekday() {
        return weekday;
    }

}
