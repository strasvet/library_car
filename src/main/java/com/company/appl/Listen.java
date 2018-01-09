package com.company.appl;/*
  Author DSR Sosnovsky 
  January 
*/

import com.company.entities.Car;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.List;
import java.util.Properties;

public class Listen implements Runnable{
    private static ObjectMapper jsonMapper =new ObjectMapper();
    private static XmlMapper xmlMapper = new XmlMapper();

    public void run() {

        while (App.whiles) {
            Properties properties = new Properties();
            InputStream instrem = Generator.class.getResourceAsStream("/application.properties");
            try {
                properties.load(instrem);
                checkDirectory(properties);
            } catch (IOException e) {
                e.printStackTrace();
            }
            checkDirectory(properties);
            File directory = new File(properties.getProperty("input"));
            File[] files = directory.listFiles();
            for (File file : files) {
                if (file.isFile()) {
                    List<String> linesInFile = null;
                    try {
                        linesInFile = FileUtils.readLines(file, "UTF-8");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    StringBuilder builder = new StringBuilder();
                    linesInFile.forEach(builder::append);
                    String filesContent = builder.toString();
                    //если файл есть, подгрузить с него данные
                    if (new File(properties.getProperty("storage") + "storage.json").exists()) {
                        synchronized (App.block){
                        try { App.cars = jsonMapper.readValue(new File(properties.getProperty("storage") + "storage.json"), new TypeReference<List<Car>>() {}); }
                        catch (IOException e) { e.printStackTrace(); }}
                    }
                    //если json
                    if (filesContent.startsWith("{")) {
                        Car car = null;
                        try { car = jsonMapper.readValue(filesContent, Car.class); }
                        catch (IOException e) { e.printStackTrace(); }
                        synchronized (App.block){
                        App.cars.add(car);}
                        file.delete();
                        //если xml
                    } else if (filesContent.startsWith("<")) {
                        Car car = null;
                        try { car = xmlMapper.readValue(filesContent, Car.class); }
                        catch (IOException e) { e.printStackTrace(); }
                        synchronized (App.block){
                        App.cars.add(car);}
                        file.delete();
                        //иначе
                    } else {
                       // System.out.println("file not be read");
                    }
                    //записываем все данные в файл в виде json
                    try {
                        synchronized (App.block){
                        jsonMapper.writeValue(new File(properties.getProperty("storage") + "storage.json"), App.cars);}
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //System.out.println(App.cars);
                    //удаляем файл
                    //file.delete();

                }


            }

        }
    }

    private void checkDirectory(Properties properties) {
        for(String folder:properties.stringPropertyNames()){
            File directory = new File(properties.getProperty(folder));
            if(!directory.exists()) directory.mkdir();
        }
    }
}
