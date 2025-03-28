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

import java.util.*;

enum SpotSize {
    SMALL, MEDIUM, LARGE
}

class ParkingSpot {
    private SpotSize size;
    private Vehicle parkedVehicle;

    public ParkingSpot(SpotSize size) {
        this.size = size;
    }

    public boolean canPark(Vehicle vehicle) {
        return isAvailable() && vehicle.fitsIn(size);
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

  
    public Vehicle getParkedVehicle() {
        return parkedVehicle;
    }
}
class ParkingLot {
    private Map<SpotSize, List<ParkingSpot>> spotMap;
    private Map<String, ParkingSpot> ticketSpotMap;
    private int ticketCounter = 0;

    public ParkingLot(int smallSpots, int mediumSpots, int largeSpots) {
        spotMap = new HashMap<>();
        ticketSpotMap = new HashMap<>();

        spotMap.put(SpotSize.SMALL, new ArrayList<>());
        spotMap.put(SpotSize.MEDIUM, new ArrayList<>());
        spotMap.put(SpotSize.LARGE, new ArrayList<>());

        for (int i = 0; i < smallSpots; i++) {
            spotMap.get(SpotSize.SMALL).add(new ParkingSpot(SpotSize.SMALL));
        }
        for (int i = 0; i < mediumSpots; i++) {
            spotMap.get(SpotSize.MEDIUM).add(new ParkingSpot(SpotSize.MEDIUM));
        }
        for (int i = 0; i < largeSpots; i++) {
            spotMap.get(SpotSize.LARGE).add(new ParkingSpot(SpotSize.LARGE));
        }
    }

    public String parkVehicle(Vehicle vehicle) {
        for (SpotSize size : SpotSize.values()) {
            if (vehicle.fitsIn(size)) {
                List<ParkingSpot> spots = spotMap.get(size);
                for (ParkingSpot spot : spots) {
                    if (spot.isAvailable()) {
                        spot.park(vehicle);
                        String ticket = generateTicket(vehicle);
                        ticketSpotMap.put(ticket, spot);
                        return ticket;
                    }
                }
            }
        }
        return null; // No available spot
    }

    public boolean removeVehicle(String ticket) {
        if (ticketSpotMap.containsKey(ticket)) {
            ParkingSpot spot = ticketSpotMap.remove(ticket);
            spot.removeVehicle();
            return true;
        }
        return false;
    }

    private String generateTicket(Vehicle vehicle) {
        ticketCounter++;
        String ticket = "TICKET-" + ticketCounter;
        vehicle.setTicket(ticket);
        return ticket;
    }
}


abstract class Vehicle {
    private String licensePlate;
    private SpotSize requiredSize;
    private String ticket;

    public Vehicle(String licensePlate, SpotSize requiredSize) {
        this.licensePlate = licensePlate;
        this.requiredSize = requiredSize;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public boolean fitsIn(SpotSize spotSize) {
        return spotSize.ordinal() >= requiredSize.ordinal();
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public String getTicket() {
        return ticket;
    }
}

class Car extends Vehicle {
    public Car(String licensePlate) {
        super(licensePlate, SpotSize.SMALL);
    }
}

public class ParkingSystem {
    public static void main(String[] args) {
        ParkingLot parkingLot = new ParkingLot(3, 2, 1);

        Vehicle car1 = new Car("CAR123");

        System.out.println("Available small spots: " + parkingLot.getAvailableSpots(SpotSize.SMALL));

        String ticket = parkingLot.parkVehicle(car1);
        System.out.println("Car parked with ticket: " + ticket);

        System.out.println("Available small spots: " + parkingLot.getAvailableSpots(SpotSize.SMALL));

        parkingLot.removeVehicle(ticket);
        System.out.println("After removing car:");
        System.out.println("Available small spots: " + parkingLot.getAvailableSpots(SpotSize.SMALL));
    }
}
