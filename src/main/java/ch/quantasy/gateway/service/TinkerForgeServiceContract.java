/*
 * /*
 *  *   "SeMqWay"
 *  *
 *  *    SeMqWay(tm): A gateway to provide an MQTT-View for any micro-service (Service MQTT-Gateway).
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
package ch.quantasy.gateway.service;

import ch.quantasy.gateway.intent.Intent;
import ch.quantasy.gateway.intent.annotations.AValidator;
import ch.quantasy.gateway.intent.annotations.ArraySize;
import ch.quantasy.gateway.intent.annotations.Choice;
import ch.quantasy.gateway.intent.annotations.MultiArraySize;
import ch.quantasy.gateway.intent.annotations.Nullable;
import ch.quantasy.gateway.intent.annotations.Period;
import ch.quantasy.gateway.intent.annotations.Range;
import ch.quantasy.gateway.intent.annotations.Ranges;
import ch.quantasy.gateway.intent.annotations.SetSize;
import ch.quantasy.gateway.intent.annotations.StringForm;
import ch.quantasy.gateway.intent.annotations.StringSize;
import ch.quantasy.gateway.intent.annotations.Validator;
import ch.quantasy.gateway.intent.dualRelay.DualRelayIntent;
import ch.quantasy.gateway.intent.ledStrip.LedStripIntent;

import ch.quantasy.mqtt.gateway.client.AyamlServiceContract;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.lang.reflect.ParameterizedType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author reto
 */
public abstract class TinkerForgeServiceContract extends AyamlServiceContract {

    Class<? extends Intent> intentClass;

    public TinkerForgeServiceContract(String baseClass, String instance, Class<? extends Intent> intentClass) {
        super("TF", baseClass, instance);
        this.intentClass = intentClass;
    }

    public static String getDataFormatDescription(Class o) {
        return getDataFormatDescription(o, "");
    }

    public static String getDataFormatDescription(Class o, String indentation) {
        String description = "";
        if (o == null) {
            return description;
        }
        List<Field> fields = new ArrayList();
        fields.addAll(Arrays.asList(o.getDeclaredFields()));
        Class<?> current = o;
        while (current.getSuperclass() != null && AValidator.class.isAssignableFrom(current.getSuperclass())) {
            current = current.getSuperclass();
            fields.addAll(Arrays.asList(current.getDeclaredFields()));
        }
        Collections.sort(fields, new Comparator<Field>() {
            @Override
            public int compare(Field o1, Field o2) {
                return o1.getName().compareToIgnoreCase(o2.getName());
            }
        });

        for (Field field : fields) {
            try {
                field.setAccessible(true);
                if (field.isAnnotationPresent(Nullable.class)) {
                    description += field.getName() + ": ";

                    description += "null\n";
                }
                if (field.isAnnotationPresent(ArraySize.class)) {
                    description += indentation;
                    description += field.getName() + ": ";
                    description += "Array <";
                    ArraySize annotation = field.getAnnotation(ArraySize.class);
                    description += "min: " + annotation.min() + " ";
                    description += "max: " + annotation.max();
                    description += ">\n";
                }
                if (field.isAnnotationPresent(MultiArraySize.class)) {
                    description += indentation;
                    description += field.getName() + ": ";
                    description += "Arrays: <[";
                    MultiArraySize annotation = field.getAnnotation(MultiArraySize.class);
                    String separator = "";
                    for (ArraySize arraySize : annotation.values()) {
                        description += separator;
                        description += "min: " + arraySize.min() + " ";
                        description += "max: " + arraySize.max();
                        separator = ",";
                    }
                    description += "]>\n";

                }
                if (field.isAnnotationPresent(StringSize.class)) {
                    description += indentation;
                    description += field.getName() + ": ";
                    description += "String <";
                    StringSize annotation = field.getAnnotation(StringSize.class);
                    description += "min: " + annotation.min() + " ";
                    description += "max: " + annotation.max();
                    description += ">\n";
                }
                if (field.isAnnotationPresent(Period.class)) {
                    description += indentation;

                    description += field.getName() + ": ";
                    description += "Number <";
                    Period annotation = field.getAnnotation(Period.class);
                    description += "from: " + annotation.from() + " ";
                    description += "to: " + annotation.to();
                    description += ">\n";
                }
                if (field.isAnnotationPresent(Ranges.class)) {
                    description += indentation;

                    description += field.getName() + ": ";

                    description += "Number <[";
                    Ranges annotation = field.getAnnotation(Ranges.class);
                    String separator = "";
                    for (Range range : annotation.values()) {
                        description += separator;
                        description += "from: " + range.from() + " ";
                        description += "to: " + range.to();
                        separator = ",";
                    }
                    description += "]>\n";
                }
                if (field.isAnnotationPresent(Range.class)) {
                    description += indentation;

                    description += field.getName() + ": ";

                    description += "Number <";
                    Range annotation = field.getAnnotation(Range.class);
                    description += "from: " + annotation.from() + " ";
                    description += "to: " + annotation.to();
                    description += ">\n";

                }
                if (field.isAnnotationPresent(Choice.class)) {
                    description += indentation;

                    description += field.getName() + ": ";

                    description += "String <";
                    Choice annotation = field.getAnnotation(Choice.class);
                    description += Arrays.toString(annotation.values());
                    description += ">\n";

                }

                if (field.isAnnotationPresent(SetSize.class)) {
                    description += indentation;

                    description += field.getName() + ": ";

                    description += "Set <";
                    SetSize annotation = field.getAnnotation(SetSize.class);

                    description += "min: " + annotation.min() + " ";
                    description += "max: " + annotation.max();
                    description += ">\n";

                }
                if (field.isAnnotationPresent(StringForm.class)) {
                    description += indentation;
                    description += field.getName() + ": ";
                    description += "String <";
                    StringForm annotation = field.getAnnotation(StringForm.class);
                    description += "regEx: " + annotation.regEx();
                    description += ">\n";
                }

                Class c = field.getType();
                if (Boolean.class.isAssignableFrom(c)||boolean.class.isAssignableFrom(c)) {
                    description += indentation;
                    description += field.getName() + ": ";
                    description += "Boolean <true,false> \n";
                } else if (Enum.class.isAssignableFrom(c)) {
                    description += enumDescription(field.getType(), indentation);
                    description += "\n";
                } else if (c != o && Validator.class.isAssignableFrom(c)) {
                    description += indentation;
                    description += field.getName() + ": \n";
                    description += getDataFormatDescription(c, indentation + "  ");
                } else if (c.isArray()) {
                    c = c.getComponentType();
                    if (Validator.class.isAssignableFrom(c)) {
                        description += field.getName() + ": \n";
                        description += indentation + "  " + c.getSimpleName() + ": \n";
                        description += getDataFormatDescription(c, indentation + "    ");

                    }
                } else if (Collection.class.isAssignableFrom(c)) {
                    Type type = field.getGenericType();

                    if (type instanceof ParameterizedType) {

                        ParameterizedType pType = (ParameterizedType) type;
                        Type[] arr = pType.getActualTypeArguments();

                        for (Type tp : arr) {
                            Class<?> clzz = (Class<?>) tp;
                            description += indentation + "  " + clzz.getSimpleName() + ": \n";
                            description += getDataFormatDescription(clzz, indentation + "    ");
                        }
                    }

                }

            } catch (Exception ex) {
                Logger.getLogger(AValidator.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                field.setAccessible(false);
            }
        }

        return description;

    }

    public static String enumDescription(Class enumType, String indentation) {
        String description = indentation;
        description += enumType.getSimpleName() + ": String <";
        Field[] fields = enumType.getDeclaredFields();
        String separator = "";
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                if (field.isEnumConstant()) {
                    description += separator;
                    description += field.getName();
                    separator = ",";

                }

            } catch (Exception ex) {
                Logger.getLogger(AValidator.class
                        .getName()).log(Level.SEVERE, null, ex);
            } finally {
                field.setAccessible(false);
            }
        }
        description += ">";
        return description;
    }

    @Override
    protected void describe(Map<String, String> descriptions) {
        descriptions.put(INTENT, getDataFormatDescription(intentClass));

    }

    public static void main(String[] args) {
        System.out.println(TinkerForgeServiceContract.getDataFormatDescription(DualRelayIntent.class
        ));
    }

}
