/*
 Project: Traffic Simulator
 Version: 1.0
 Start Day: 7/12/2020
 By: Nguyen Viet Hoang

 */

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int roadSpawns = 2;
        int carSpawns = 1;
        int lightSpawns = 1;
        int gasStationSpawns = 1;
        ArrayList<Road> roads = new ArrayList<>();
        ArrayList<Car> cars = new ArrayList<>();
        ArrayList<TrafficLight> lights = new ArrayList<>();
        ArrayList<GasStation> gasStations = new ArrayList<>();
        showUI(sc, roads, cars, lights, gasStations, roadSpawns, carSpawns, lightSpawns, gasStationSpawns);
        showGamePlay(sc, cars, lights, gasStations);
    }

    public static void showUI(Scanner sc, ArrayList<Road> roads, ArrayList<Car> cars, ArrayList<TrafficLight> lights, ArrayList<GasStation> gasStations, int roadSpawns, int carSpawns, int lightSpawns, int gasStationSpawns) {
        System.out.println("Object Creation:\n---------------------");
        System.out.println("Roads:");
        for (int i = 0; i < roadSpawns; i++) {
            System.out.println("Please input parameters for road_" + i + "...");
            System.out.print("Length:");
            int lengthInput = sc.nextInt();
            int speedLimitInput = 1; // force speed limit to be 1 for prototype.
            roads.add(new Road(Integer.toString(i), speedLimitInput, lengthInput, new int[]{0, 0}));
        }
        System.out.println("\nRoads;");
        for (Road road : roads
        ) {
            road.showRoadInfo();
        }

        System.out.println("\nCars;");
        for (int i = 0; i < carSpawns; i++) {
            cars.add(new Car(Integer.toString(i), roads.get(0))); // all created cars will begin on road_0.
            cars.get(i).showOutPut();
        }


        System.out.println("\nTraffic Lights;");

        for (int i = 0; i < lightSpawns; i++) {
            lights.add(new TrafficLight(Integer.toString(i), roads.get(0))); // all created lights will begin on road_0.
            lights.get(i).showLightInfo();
        }


        System.out.println("\nGas Stations;");

        for (int i = 0; i < gasStationSpawns; i++) {
            gasStations.add(new GasStation(Integer.toString(i), roads.get(0)));
            gasStations.get(i).showInfo();
        }

        System.out.println();

        System.out.println("Settings:");
        roads.get(1).setStartLocation(new int[]{roads.get(0).getLength() + 1, 0}); // place road_1 to a position at the end of road_0.
        roads.get(1).setEndLocation(new int[]{roads.get(0).getLength() + 1 + roads.get(1).getLength(), 0});
        roads.get(1).showRoadInfo();
        roads.get(0).getConnectedRoads().add(roads.get(1)); // connect road_0 to road_1
        System.out.println();
    }

    public static void showGamePlay(Scanner sc, ArrayList<Car> cars, ArrayList<TrafficLight> lights, ArrayList<GasStation> gasStations) {
        System.out.println("Simulation:");
        int time = 0;
        System.out.print("Set time scale in milliseconds:");
        int speedOfSim = sc.nextInt();
        int carsFinished = 0;
        while (carsFinished < cars.size()) {
            for (TrafficLight light : lights) {
                light.operate();
                light.showLightInfo();
            }

            for (GasStation station : gasStations) {
                station.showInfo();
            }

            for (Car car : cars) {
                car.move();
                car.showOutPut();
                if (car.getCurrentRoad().getConnectedRoads().isEmpty() && (car.getSpeed() == 0)) {
                    carsFinished += 1;
                }
            }
            time += 1;
            System.out.println(time + " Seconds have passed.\n");
            try {
                Thread.sleep(speedOfSim); // set speed of simulation.
            } catch (InterruptedException sim) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
