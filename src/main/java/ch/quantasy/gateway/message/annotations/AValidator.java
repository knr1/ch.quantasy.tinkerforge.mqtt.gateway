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
package ch.quantasy.gateway.message.annotations;

import ch.quantasy.mqtt.gateway.client.message.Validator;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author reto
 */
public abstract class AValidator implements Validator {

    @Override
    public boolean isValid() {
        Collection<Field> fields = new ArrayList();
        fields.addAll(Arrays.asList(this.getClass().getDeclaredFields()));
        Class<?> current = this.getClass();
        while (current.getSuperclass() != null) {
            current = current.getSuperclass();
            fields.addAll(Arrays.asList(current.getDeclaredFields()));
        }

        for (Field field : fields) {
            try {
                field.setAccessible(true);
                Object object = field.get(this);
                if (field.isAnnotationPresent(NonNull.class)) {
                    if (object == null) {
                        return false;
                    }
                }
                if (object != null) {
                    if (field.isAnnotationPresent(StringSize.class)) {
                        StringSize annotation = field.getAnnotation(StringSize.class);
                        if (((String) object).length() < annotation.min()) {
                            return false;
                        }
                        if (((String) object).length() > annotation.max()) {
                            return false;
                        }
                    }
                    if (field.isAnnotationPresent(Period.class)) {
                        Period annotation = field.getAnnotation(Period.class);
                        Long value = ((Number) object).longValue();
                        if (value < annotation.from() || value > annotation.to()) {
                            return false;
                        }
                    }
                    if (field.isAnnotationPresent(Ranges.class)) {
                        Ranges annotation = field.getAnnotation(Ranges.class);
                        Long value = ((Number) object).longValue();
                        boolean isValid = false;
                        for (Range range : annotation.values()) {
                            if (value >= range.from() && value <= range.to()) {
                                isValid |= true;
                                break;
                            }
                        }
                        if (!isValid) {
                            return false;
                        }

                    }
                    if (field.isAnnotationPresent(Range.class)) {
                        Range annotation = field.getAnnotation(Range.class);
                        if (field.isAnnotationPresent(ArraySize.class)) {
                            //Well check if each entry is within Range...
                        } else {
                            Long value = ((Number) object).longValue();
                            if (value < annotation.from() || value > annotation.to()) {
                                return false;
                            }
                        }
                    }
                    if (field.isAnnotationPresent(Choice.class)) {
                        Choice annotation = field.getAnnotation(Choice.class);
                        String value = object.toString();
                        if (!Arrays.asList(annotation.values()).contains(value)) {
                            return false;
                        }
                    }

                    if (field.isAnnotationPresent(SetSize.class)) {
                        SetSize annotation = field.getAnnotation(SetSize.class);
                        Collection value = ((Collection) object);
                        if (value.size() < annotation.min() || value.size() > annotation.max()) {
                            return false;
                        }
                        for (Object val : value) {
                            if (val instanceof Validator) {
                                if (!((Validator) val).isValid()) {
                                    return false;
                                }
                            }
                        }

                    }
                    if (field.isAnnotationPresent(StringForm.class)) {
                        StringForm annotation = field.getAnnotation(StringForm.class);
                        String value = object.toString();
                        if (!value.matches(annotation.regEx())) {
                            return false;
                        }
                    }
                    if (field.isAnnotationPresent(ArraySize.class)) {
                        ArraySize annotation = field.getAnnotation(ArraySize.class);
                        int size = -1;
                        if (object instanceof boolean[]) {
                            size = ((boolean[]) (object)).length;
                        }
                        else if (object instanceof byte[]) {
                            size = ((byte[]) (object)).length;
                        }
                        else if (object instanceof char[]) {
                            size = ((char[]) (object)).length;
                        }
                        else if (object instanceof short[]) {
                            size = ((short[]) (object)).length;
                        }
                        else if (object instanceof int[]) {
                            size = ((int[]) (object)).length;
                        }
                        else if (object instanceof long[]) {
                            size = ((long[]) (object)).length;
                        }
                        else if (object instanceof float[]) {
                            size = ((float[]) (object)).length;
                        }
                        else if (object instanceof double[]) {
                            size = ((double[]) (object)).length;
                        }
                        else if (object instanceof Object[]) {
                            size = ((Object[]) (object)).length;
                        }

                        if (size < annotation.min() || size > annotation.max()) {
                            return false;
                        }

                    }

                    if (field.get(this) instanceof Validator) {
                        if (!((Validator) field.get(this)).isValid()) {
                            return false;
                        }
                    }

                }
            } catch (Exception ex) {
                Logger.getLogger(AValidator.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            } finally {
                field.setAccessible(false);
            }
        }
        return true;
    }
}
