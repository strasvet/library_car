package com.company.appl;

import com.company.appl.Generator;
import com.company.entities.Car;
import org.apache.commons.io.IOUtils;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.Buffer;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App {

    public static Object block = new Object();
    public static List<Car> cars = new LinkedList<>();
    public static boolean whiles = true;
    //private static BufferedReader buffer = IOUtils.buffer(new InputStreamReader(System.in));
    public static void main( String[] args )
    {
        //Поиск машины
        Thread search = new Thread(new Search());
        search.start();
        //Запускаем генератор
        Thread generator = new Thread(new Generator());
        generator.start();
        //Выждем паузу, нагенерируем машин //для первого запуска можно подождать по дольше
        try { Thread.sleep(1000); } catch (InterruptedException e) { e.printStackTrace(); }
        Thread listening = new Thread(new Listen());
        listening.start();



    }



}
