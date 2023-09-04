package trafficLight;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class Intersection {
    private final Object lock = new Object();
    private Set<String> allowedDirections = Collections.synchronizedSet(new HashSet<>());
    private List<Car> waitingCars = new CopyOnWriteArrayList<>();;

    Map<String, Set<String>> intersections = Map.of(
            "NS", Set.of("EW", "WE"),
            "EW", Set.of("NS", "SN"),
            "ES", Set.of("WE", "SN"),
            "SN", Set.of("EW", "WE", "ES"),
            "WE", Set.of("NS", "SN", "ES")
    );

    public List<Car> getWaitingCars() {
        return waitingCars;
    }

    public void addCar(Car c) {
        waitingCars.add(c);
    }

    public void deleteCar(Car c) {
        waitingCars.remove(c);
    }

    public void allowDirections() {
        System.out.println("Светофор открыт для направлений: " + allowedDirections);
        synchronized (lock) {
            Iterator<Car> iterator = waitingCars.iterator();
            while (iterator.hasNext()) {
                Car car = iterator.next();
                if (allowedDirections.contains(car.getDirection())) {
                    synchronized (car) {
                        car.notify();
                    }
                }
            }
            allowedDirections.clear();
        }
    }

    public void setAllowedDirections() {
        Iterator<Car> iterator = waitingCars.iterator();
        Car firstCar = iterator.next();
        Set<String> intersectionsWithFirstCars = intersections.get(firstCar.getDirection());
        allowedDirections.add(firstCar.getDirection());
        while(iterator.hasNext()){
            Car car = iterator.next();
            boolean add = true;
            for (String dir: allowedDirections) {
                if (intersections.get(car.getDirection()).contains(dir)) {
                    add = false;
                }
            }
            if (add) {
                allowedDirections.add(car.getDirection());
            }
        }
    }
}
