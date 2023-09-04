package trafficLight;

public class Car extends Thread {
    private String direction;
    private Intersection intersection;

    public Car(String direction, Intersection intersection) {
        this.direction = direction;
        this.intersection = intersection;
    }

    public String getDirection() {
        return direction;
    }

    @Override
    public void run() {
        while (true) {
            System.out.println("Машина с направлением " + direction + " подъезжает к перекрестку");
            intersection.addCar(this);
            try {
                synchronized (this) {
                    this.wait();
                }
                System.out.println("Машина с направлением " + direction + " проезжает перекресток");
                intersection.deleteCar(this);
                sleep((int) (10000 * Math.random()));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
