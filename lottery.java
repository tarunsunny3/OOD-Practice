import java.util.*;

class User {
    private String userId;
    private String name;

    public User(String userId, String name) {
        this.userId = userId;
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }
}

class Ticket {
    private static int counter = 1;
    private final int ticketId;
    private final User user;
    private final double price;

    public Ticket(User user, double price) {
        this.ticketId = counter++;
        this.user = user;
        this.price = price;
    }

    public int getTicketId() {
        return ticketId;
    }

    public User getUser() {
        return user;
    }

    public double getPrice() {
        return price;
    }
}

class Lottery {
    private final String lotteryId;
    private final double prizeAmount;
    private final List<Ticket> tickets;
    private final List<Integer> cumulativeWeights;
    private int totalWeight;
    private boolean isActive;

    public Lottery(String lotteryId, double prizeAmount) {
        this.lotteryId = lotteryId;
        this.prizeAmount = prizeAmount;
        this.tickets = new ArrayList<>();
        this.cumulativeWeights = new ArrayList<>();
        this.totalWeight = 0;
        this.isActive = true;
    }

    public void buyTicket(User user, double price) {
        if (!isActive) {
            System.out.println("Lottery is closed!");
            return;
        }
        Ticket ticket = new Ticket(user, price);
        tickets.add(ticket);
        
        // Compute weight based on price (e.g., $10 = 1 weight, $50 = 5 weight)
        int weight = (int) (price / 10);
        totalWeight += weight;
        cumulativeWeights.add(totalWeight);

        System.out.println(user.getName() + " bought Ticket ID: " + ticket.getTicketId() +
                           " for $" + price + " with " + weight + " chances.");
    }

    public void drawWinner() {
        if (!isActive || tickets.isEmpty()) {
            System.out.println("No tickets sold or Lottery is closed!");
            return;
        }

        Random random = new Random();
        int randomWeight = random.nextInt(totalWeight) + 1; // Pick a random weight

        // Use binary search to find the winning ticket
        int index = Collections.binarySearch(cumulativeWeights, randomWeight);
        // BinarySearch returns the (-insertPosition - 1) if not found
        // so, since it's a negative index, to get the insertPosition
        // we do the following operation
        if (index < 0) {
            index = -index - 1;
        }

        Ticket winningTicket = tickets.get(index);
        System.out.println("Winner is: " + winningTicket.getUser().getName() + 
                           " with Ticket ID: " + winningTicket.getTicketId() +
                           " and wins $" + prizeAmount);
        isActive = false; // Close lottery after drawing winner
    }
}

public class LotterySystem {
    public static void main(String[] args) {
        User user1 = new User("U1", "Alice");
        User user2 = new User("U2", "Bob");
        User user3 = new User("U3", "Charlie");

        Lottery lottery = new Lottery("L1", 5000.00); // Prize amount set to $5000
        
        // Users buying tickets with different prices
        lottery.buyTicket(user1, 10);  // 1 weight
        lottery.buyTicket(user2, 20);  // 2 weights
        lottery.buyTicket(user3, 50);  // 5 weights

        // Draw the winner
        lottery.drawWinner();
    }
}
