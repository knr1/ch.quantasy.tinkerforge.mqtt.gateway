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
package ch.quantasy.gateway;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.tinkerforge.BrickletHumidity;
import com.tinkerforge.BrickletHumidity.HumidityCallbackThreshold;
import java.io.IOException;

/**
 *
 * @author reto
 */
public class Tester {

    private ObjectMapper mapper;

    public Tester() throws IOException {
        mapper = new ObjectMapper(new YAMLFactory());
       
        String tester = "option: < \nmin: 10 \nmax: 100";
        MyHumididtyCallbackThreshold th=new MyHumididtyCallbackThreshold('<', 10, 100);
        System.out.println(mapper.writeValueAsString(th));
        MyHumididtyCallbackThreshold threshold = mapper.readValue(tester, MyHumididtyCallbackThreshold.class);
        System.out.println(threshold);
    }
    public static void main(String[] args) throws IOException {
       Tester t= new Tester();
        System.out.println(t);
    }
    
    public static  class MyHumididtyCallbackThreshold {
        public char option;
        public int min;
        public int max;

        public MyHumididtyCallbackThreshold(char option, int min, int max) {
            this.option = option;
            this.min = min;
            this.max = max;
        }
        private MyHumididtyCallbackThreshold() {
            
        }

        
        @Override
        public String toString() {
            return "MyHumididtyCallbackThreshold{" + "option=" + option + ", min=" + min + ", max=" + max + '}';
        }
        
        
    }
            
}
