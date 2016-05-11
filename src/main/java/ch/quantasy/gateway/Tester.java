/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
