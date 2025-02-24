import java.util.ArrayList;
import java.util.List;


/*
 * 
 * Support for multiple vehicle types:

The system can handle different types of vehicles: Cars, Motorcycles, and Trucks.

Different parking spot types:

The parking lot has specific spots for each vehicle type.

Parking functionality:

Vehicles can be parked in appropriate available spots.

The system ensures that vehicles are parked in spots matching their type.

Vehicle removal:

Parked vehicles can be removed from the parking lot.

Availability tracking:

The system can report the number of available spots for each vehicle type.
 */
class ParkingLot {
    private List<ParkingSpot> spots;

    public ParkingLot(int carSpots, int motorcycleSpots, int truckSpots) {
        spots = new ArrayList<>();
        for (int i = 0; i < carSpots; i++) {
            spots.add(new ParkingSpot(VehicleType.CAR));
        }
        for (int i = 0; i < motorcycleSpots; i++) {
            spots.add(new ParkingSpot(VehicleType.MOTORCYCLE));
        }
        for (int i = 0; i < truckSpots; i++) {
            spots.add(new ParkingSpot(VehicleType.TRUCK));
        }
    }

    public boolean parkVehicle(Vehicle vehicle) {
        for (ParkingSpot spot : spots) {
            if (spot.canPark(vehicle)) {
                spot.park(vehicle);
                return true;
            }
        }
        return false;
    }

    public boolean removeVehicle(Vehicle vehicle) {
        for (ParkingSpot spot : spots) {
            if (spot.getParkedVehicle() == vehicle) {
                spot.removeVehicle();
                return true;
            }
        }
        return false;
    }

    public int getAvailableSpots(VehicleType type) {
        int count = 0;
        for (ParkingSpot spot : spots) {
            if (spot.getType() == type && spot.isAvailable()) {
                count++;
            }
        }
        return count;
    }
}

class ParkingSpot {
    private VehicleType type;
    private Vehicle parkedVehicle;

    public ParkingSpot(VehicleType type) {
        this.type = type;
    }

    public boolean canPark(Vehicle vehicle) {
        return isAvailable() && type == vehicle.getType();
    }

    public void park(Vehicle vehicle) {
        this.parkedVehicle = vehicle;
    }

    public void removeVehicle() {
        this.parkedVehicle = null;
    }

    public boolean isAvailable() {
        return parkedVehicle == null;
    }

    public VehicleType getType() {
        return type;
    }

    public Vehicle getParkedVehicle() {
        return parkedVehicle;
    }
}

enum VehicleType {
    MOTORCYCLE, CAR, TRUCK
}

abstract class Vehicle {
    private String licensePlate;
    private VehicleType type;

    public Vehicle(String licensePlate, VehicleType type) {
        this.licensePlate = licensePlate;
        this.type = type;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public VehicleType getType() {
        return type;
    }
}

class Motorcycle extends Vehicle {
    public Motorcycle(String licensePlate) {
        super(licensePlate, VehicleType.MOTORCYCLE);
    }
}

class Car extends Vehicle {
    public Car(String licensePlate) {
        super(licensePlate, VehicleType.CAR);
    }
}

class Truck extends Vehicle {
    public Truck(String licensePlate) {
        super(licensePlate, VehicleType.TRUCK);
    }
}

public class ParkingSystem {
    public static void main(String[] args) {
        ParkingLot parkingLot = new ParkingLot(3, 2, 1);

        Vehicle car = new Car("CAR123");
        Vehicle motorcycle = new Motorcycle("MOTO456");
        Vehicle truck = new Truck("TRUCK789");

        System.out.println("Available car spots: " + parkingLot.getAvailableSpots(VehicleType.CAR));
        System.out.println("Available motorcycle spots: " + parkingLot.getAvailableSpots(VehicleType.MOTORCYCLE));
        System.out.println("Available truck spots: " + parkingLot.getAvailableSpots(VehicleType.TRUCK));

        parkingLot.parkVehicle(car);
        parkingLot.parkVehicle(motorcycle);
        parkingLot.parkVehicle(truck);

        System.out.println("\nAfter parking vehicles:");
        System.out.println("Available car spots: " + parkingLot.getAvailableSpots(VehicleType.CAR));
        System.out.println("Available motorcycle spots: " + parkingLot.getAvailableSpots(VehicleType.MOTORCYCLE));
        System.out.println("Available truck spots: " + parkingLot.getAvailableSpots(VehicleType.TRUCK));

        parkingLot.removeVehicle(car);

        System.out.println("\nAfter removing a car:");
        System.out.println("Available car spots: " + parkingLot.getAvailableSpots(VehicleType.CAR));
    }
}
