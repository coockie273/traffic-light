import trafficLight.Car;
import trafficLight.Intersection;

public class Main {
    public static void main(String[] args) {
        Intersection intersection = new Intersection();

        Car[] cars = {
                new Car("NS", intersection),
                new Car("EW", intersection),
                new Car("ES", intersection),
                new Car("SN", intersection),
                new Car("WE", intersection)
        };

        for (Car car : cars) {
            car.start();
        }

        while (true) {
            if (intersection.getWaitingCars().size() >= 2) {
                intersection.setAllowedDirections();
                intersection.allowDirections();
            }
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
