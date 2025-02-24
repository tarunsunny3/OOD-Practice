import java.util.*;

enum Size {
    SMALL, MEDIUM, LARGE
}

class Locker {
    private String id;
    private Size size;
    private boolean isOccupied;
    private Package currentPackage;

    public Locker(String id, Size size) {
        this.id = id;
        this.size = size;
        this.isOccupied = false;
        this.currentPackage = null;
    }

    public String getId() { return id; }
    public Size getSize() { return size; }
    public boolean isOccupied() { return isOccupied; }
    public Package getCurrentPackage() { return currentPackage; }

    public void assignPackage(Package pkg) {
        this.currentPackage = pkg;
        this.isOccupied = true;
    }

    public Package removePackage() {
        Package pkg = this.currentPackage;
        this.currentPackage = null;
        this.isOccupied = false;
        return pkg;
    }
}

class Package {
    private String id;
    private Size size;

    public Package(String id, Size size) {
        this.id = id;
        this.size = size;
    }

    public String getId() { return id; }
    public Size getSize() { return size; }
}

class LockerSystem {
    private Map<Size, Queue<Locker>> availableLockers;
    private Map<String, Locker> occupiedLockers;

    public LockerSystem() {
        availableLockers = new EnumMap<>(Size.class);
        for (Size size : Size.values()) {
            availableLockers.put(size, new LinkedList<>());
        }
        occupiedLockers = new HashMap<>();
    }

    public void addLocker(Locker locker) {
        availableLockers.get(locker.getSize()).add(locker);
    }

    public String assignPackage(Package pkg) {
        for (Size size : Size.values()) {
            if (size.ordinal() >= pkg.getSize().ordinal() && !availableLockers.get(size).isEmpty()) {
                Locker locker = availableLockers.get(size).poll();
                locker.assignPackage(pkg);
                occupiedLockers.put(pkg.getId(), locker);
                return locker.getId();
            }
        }
        return null;
    }

    public Package retrievePackage(String packageId) {
        Locker locker = occupiedLockers.remove(packageId);
        if (locker != null) {
            Package pkg = locker.removePackage();
            availableLockers.get(locker.getSize()).add(locker);
            return pkg;
        }
        return null;
    }
}

public class AmazonLockerSystem {
    public static void main(String[] args) {
        LockerSystem system = new LockerSystem();
        
        // Add lockers
        system.addLocker(new Locker("S1", Size.SMALL));
        system.addLocker(new Locker("M1", Size.MEDIUM));
        system.addLocker(new Locker("L1", Size.LARGE));

        // Assign a package
        Package pkg = new Package("P1", Size.MEDIUM);
        String lockerId = system.assignPackage(pkg);
        System.out.println("Package P1 assigned to locker: " + lockerId);

        // Retrieve the package
        Package retrievedPkg = system.retrievePackage("P1");
        if (retrievedPkg != null) {
            System.out.println("Retrieved package: " + retrievedPkg.getId());
        }
    }
}
