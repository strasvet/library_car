package com.company.appl;/*
  Author DSR Sosnovsky 
  January 
*/

import com.company.entities.Car;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Search implements Runnable {
    private static BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));

    @Override
    public void run() {
        while (App.whiles) {
            Car car = new Car();
            List<Car> answer = new LinkedList<>();
            try {
                Arrays.stream(Generator.brand).forEach(x -> System.out.print(" " + x));
                System.out.println("\n Please, enter brand");
                car.setBrand(buffer.readLine());

                Arrays.stream(Generator.model).forEach(x -> System.out.print(" " + x));
                System.out.println("\n Please, enter model");
                car.setModel(buffer.readLine());
                searchCar(car);


                System.out.println("For exit enter -1, or enter for resume, or 1 for print all cars");
                String ans = buffer.readLine();
                if (ans.equalsIgnoreCase("1")) {
                    App.cars.forEach(System.out::println);
                }
                if (ans.equalsIgnoreCase("-1")) {
                    App.whiles = false;
                    System.out.println("Bye!");
                }


            } catch (IOException e) {
                e.printStackTrace();
            }

        }


    }

    private void searchCar(Car car) {
        List<Car> answer = new LinkedList<>();
        synchronized (App.block) {
            for (Car carz : App.cars) {
                //if (car.getBrand().equalsIgnoreCase(carz.getBrand()) ){ answer.add(carz); }
                if (car.getBrand().equalsIgnoreCase(carz.getBrand()) && car.getModel().equalsIgnoreCase(carz.getModel())) {
                    answer.add(carz);
                }
            }
        }
        if (answer.size() != 0) {
            answer.forEach(System.out::println);
        } else {
            System.err.println("not found");
        }
    }
}
