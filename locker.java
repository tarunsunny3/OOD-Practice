import java.util.*;

// Enum for Package Size
enum PackageSize {
    SMALL, MEDIUM, LARGE;
}

// Package Class
class Package {
    private final String trackingId;
    private final PackageSize size;

    public Package(PackageSize size) {
        this.trackingId = generateTrackingId();
        this.size = size;
    }

    private String generateTrackingId() {
        return "PKG-" + (new Random().nextInt(9000) + 1000);
    }

    public String getTrackingId() {
        return trackingId;
    }

    public PackageSize getSize() {
        return size;
    }
}

// Locker Class
class Locker {
    private final int lockerId;
    private final PackageSize lockerSize;
    private boolean isOccupied;
    private Package storedPackage;

    public Locker(int lockerId, PackageSize lockerSize) {
        this.lockerId = lockerId;
        this.lockerSize = lockerSize;
        this.isOccupied = false;
        this.storedPackage = null;
    }

    public boolean isAvailableFor(Package pack) {
        return !isOccupied && canFitPackage(pack);
    }

    private boolean canFitPackage(Package pack) {
        return pack.getSize().ordinal() <= lockerSize.ordinal(); // SMALL fits in all, MEDIUM in M/L, LARGE in L
    }

    public String storePackage(Package pack) {
        if (!isAvailableFor(pack)) {
            return null;
        }
        this.storedPackage = pack;
        this.isOccupied = true;
        return pack.getTrackingId();
    }

    public boolean retrievePackage(String trackingId) {
        if (isOccupied && storedPackage != null && storedPackage.getTrackingId().equals(trackingId)) {
            isOccupied = false;
            storedPackage = null;
            return true;
        }
        return false; // Invalid code or already empty
    }

    public int getLockerId() {
        return lockerId;
    }

    public PackageSize getLockerSize() {
        return lockerSize;
    }
}

// Locker System
class LockerSystem {
    private final List<Locker> lockers;

    public LockerSystem(int small, int medium, int large) {
        lockers = new ArrayList<Locker>();

        for (int i = 1; i <= small; i++) lockers.add(new Locker(i, PackageSize.SMALL));
        for (int i = 1; i <= medium; i++) lockers.add(new Locker(small + i, PackageSize.MEDIUM));
        for (int i = 1; i <= large; i++) lockers.add(new Locker(small + medium + i, PackageSize.LARGE));
    }

    public String depositPackage(Package pack) {
        for (Locker locker : lockers) {
            if (locker.isAvailableFor(pack)) {
                return "Locker " + locker.getLockerId() + " (Size: " + locker.getLockerSize() + ") - Code: " + locker.storePackage(pack);
            }
        }
        return "No available lockers for package size: " + pack.getSize();
    }

    public boolean retrievePackage(int lockerId, String trackingId) {
        for (Locker locker : lockers) {
            if (locker.getLockerId() == lockerId) {
                return locker.retrievePackage(trackingId);
            }
        }
        return false;
    }
}

// Main Class
public class AmazonLockerApp {
    public static void main(String[] args) {
        LockerSystem system = new LockerSystem(2, 2, 1); // 2 Small, 2 Medium, 1 Large

        Package smallPackage = new Package(PackageSize.SMALL);
        Package largePackage = new Package(PackageSize.LARGE);

        String receipt1 = system.depositPackage(smallPackage);
        System.out.println(receipt1);

        String receipt2 = system.depositPackage(largePackage);
        System.out.println(receipt2);

        // Extract locker ID & code for retrieval (mocked for demo)
        int lockerId = Integer.parseInt(receipt1.split(" ")[1]);
        String trackingId = receipt1.split(": ")[1];

        boolean success = system.retrievePackage(lockerId, trackingId);
        System.out.println("Retrieval Successful: " + success);
    }
}
