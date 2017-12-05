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
package ch.quantasy.gateway.service.doc;

import ch.quantasy.gateway.message.intent.voltageCurrent.VoltageCurrentIntent;
import static ch.quantasy.gateway.service.doc.ClassFinder.find;
import ch.quantasy.mqtt.gateway.client.message.AnIntent;
import ch.quantasy.mqtt.gateway.client.message.Intent;
import ch.quantasy.mqtt.gateway.client.message.annotations.Nullable;
import ch.quantasy.mqtt.gateway.client.message.annotations.Period;
import java.lang.reflect.Field;
import java.lang.annotation.Annotation;
import java.io.OutputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.util.ServiceLoader;
import java.lang.*;
import java.io.*;
import java.util.*;

/**
 *
 * @author reto
 */
import ch.quantasy.mqtt.gateway.client.message.AStatus;
public class IntentToStatus {

    public static String createStatus(Class<? extends Intent> intent, File path) throws IOException {
        path = new File(path, "src/main/generated/");
        String packageName = intent.getPackage().getName();
        packageName = packageName.replace("intent", "status");
        path = new File(path, packageName.replaceAll("\\.", "\\/"));
        path.mkdirs();
        Field[] fields = intent.getFields();
        for (Field field : fields) {
            String className = field.getName();
            className = className.substring(0, 1).toUpperCase() + className.substring(1);
            className = className + "Status";
            File file = new File(path, className + ".java");
            file.createNewFile();
            PrintWriter pw = new PrintWriter(new FileWriter(file));

            pw.println("package " + packageName + ";");
            String importStatements = field.getGenericType().getTypeName();
            importStatements = "import " + importStatements + ";";
            pw.println(importStatements);
            pw.println("import ch.quantasy.mqtt.gateway.client.message.AStatus;");
            Annotation[] annotations = field.getDeclaredAnnotations();
            for (Annotation annotation : annotations) {
                if (annotation.annotationType().equals(Nullable.class)) {
                    continue;
                }
                pw.println("import " + annotation.annotationType().getCanonicalName() + ";");
            }

            pw.println("public class " + className + " extends AStatus");
            pw.println("{");

            for (Annotation annotation : annotations) {
                if (annotation.annotationType().equals(Nullable.class)) {
                    continue;
                }
                String simpleAnnotation = annotation.annotationType().getSimpleName();
                int simpleAnnotationOffset = annotation.toString().lastIndexOf(simpleAnnotation);
                simpleAnnotation = annotation.toString().substring(simpleAnnotationOffset);
                if (annotation instanceof Period) {
                    simpleAnnotation = simpleAnnotation.replace("from=0", "");
                    simpleAnnotation = simpleAnnotation.replace("to=9223372036854775807", "");
                    simpleAnnotation = simpleAnnotation.replace("(, )", "");
                }
                System.out.println("---" + simpleAnnotation);
                pw.println("@" + simpleAnnotation);
            }
            String simpleTypeName = field.getGenericType().getTypeName();
            simpleTypeName = simpleTypeName.substring(simpleTypeName.lastIndexOf(".") + 1);

            pw.println("public " + simpleTypeName + " value;");
            pw.println("private " + className + "(){}");
            pw.println("public " + className + "(" + simpleTypeName + " value){");
            pw.println("  this.value=value;");
            pw.println("}");

            pw.println("}");
            pw.flush();
            pw.close();
        }
        return null;
    }

    public static void main(String[] args) throws IOException {
        List<Class<?>> classes = find("ch.quantasy.gateway.message.intent");
        List<Class> intentClasses = new ArrayList();
        for (Class singleClass : classes) {
            //if (singleClass.getEnclosingClass().isAssignableFrom(AnIntent.class)) {
            if (AnIntent.class.equals(singleClass.getSuperclass())) {
                intentClasses.add(singleClass);
            }

        }
        for (Class singleClass : intentClasses) {
            OutputStream os = System.out;//new ByteArrayOutputStream();
            File f = new File(".");
            createStatus(singleClass, f);
        }
    }

}
