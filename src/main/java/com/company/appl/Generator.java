package com.company.appl;/*
  Author DSR Sosnovsky 
  January
*/

import com.company.entities.Car;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Random;
import java.util.UUID;

public class Generator implements Runnable {
    private static ObjectMapper jsonMapper =new ObjectMapper();
    private static XmlMapper xmlMapper = new XmlMapper();
    private static Object block = new Object();
    //Количество генерируемых машин
    //private static int COUNTOF = 550;
    Random rnd = new Random();
    public static final String[] brand ={"BMW", "Jaguar","Ziguli", "Toyota", "Mercedes", "Tank", "China", "Korea","Lamborgini", "Ferrari"};
    public static final String[] model={"X5","Merkava IV","2101","Corolla","S600","Sportage","Aventador","599 GTB Fiorano","Haval H9","Niva4X4"};
    @Override
    public void run() {
        Properties properties = new Properties();
        InputStream instrem = Generator.class.getResourceAsStream("/application.properties");
        try {
            properties.load(instrem);
            //проверяем на наличие директорий, если нет, создаем
            checkDirectory(properties);
        } catch (IOException e) { e.printStackTrace();}
        checkDirectory(properties);

        //System.out.println(properties.toString());
        int i=0;
        //for(int i=0; i<COUNTOF; i++){
        while(App.whiles){
            i++;
            if(i%2!=0){
                synchronized (block){
                    Car car = generateCar();
                    try {jsonMapper.writeValue(new File(properties.getProperty("input")+i+car.getBrand()),car);
                    } catch (IOException e) { e.printStackTrace(); }
                }
            }else{
                synchronized (block){
                    Car car = generateCar();
                    try {xmlMapper.writeValue(new File(properties.getProperty("input")+i+car.getBrand()),car);
                    } catch (IOException e) {e.printStackTrace();}
                }
            }
            try { Thread.sleep(1000); } catch (InterruptedException e) { e.printStackTrace(); }
        }


    }

    private void checkDirectory(Properties properties) {
        for(String folder:properties.stringPropertyNames()){
            File directory = new File(properties.getProperty(folder));
            if(!directory.exists()) directory.mkdir();
        }
    }

    private Car generateCar(){
        Car car = new Car();
        car.setBrand(brand[rnd.nextInt(brand.length)]);
        car.setModel(model[rnd.nextInt(model.length)]);
        car.setKilometerRun(1000+rnd.nextInt(50000));
        car.setYear(2000+rnd.nextInt(18));
        car.setUuid(UUID.randomUUID().toString());
        return car;
    }
}
