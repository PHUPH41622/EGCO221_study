package Ex4_6513012;

// Napasrapee Satittham 6513012

import java.util.*;

public class Newmain4 {
    public static void main(String[] args) {
        PriorityQueue<Customer> orderQueue = new PriorityQueue<>( new SortCustomerbyOrder().reversed()
                                                   .thenComparing(new SortCustomerbyID())
                                                                );
        Random random = new Random();
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter refill items : ");
        int refill = scan.nextInt();
        System.out.println("Enter max days : ");
        int days = scan.nextInt();

        System.out.println("\n=== Day 0 : Customer arrival ===");
        for(int j = 1; j <= 5; j++) {
            int order = random.nextInt(20) + 1;
            orderQueue.add( new Customer(j, order));
            Customer c = new Customer(j, order);
            c.print();
            System.out.println();
        }

        Shop info = new Shop(refill, days, orderQueue);
        info.simulation();
    }
}

class Customer {
    private static int runningID = 1; // for running customer ID
    private int ID;
    private int order; // order amount (random value 1-20)

    public Customer(int ID, int order) {
        this.ID = ID;
        this.order = order;
    }

    public int getOrder() {
        return order;
    }

    public int getID() {
        return ID;
    }

    public void print() {
        System.out.printf("[Customer  %-2d  order %2d lots]", ID, order);
    }
}

class Shop {
    Customer c;
    Random random = new Random();
    private int itemsToRefill; // items to refill in odd day
    private int maxDays; // max day for simulation
    int order;
    private PriorityQueue<Customer> orderQueue = new PriorityQueue<>( new SortCustomerbyOrder().reversed()
                                                       .thenComparing(new SortCustomerbyID())
                                                                    );
    private ArrayDeque<Customer> billingQueue = new ArrayDeque<>();

    public Shop(int refill, int days, PriorityQueue orderQueue) {
        itemsToRefill = refill;
        maxDays = days;
        this.orderQueue = orderQueue;
    }

    public void simulation() {
        System.out.println();
        System.out.println("=== Simulation ===");
        int RefillPerOddDay = 0;
            for(int day = 1; day <= maxDays; day++) {
                if (day % 2 != 0) {
                    try {
                        System.out.println("Day " + day);
                        RefillPerOddDay = RefillPerOddDay + itemsToRefill;
                        System.out.println("Refilling    >> Remaining items = " + RefillPerOddDay + " lots");
                        order = random.nextInt(20) + 1;
                        orderQueue.add(new Customer(5 + day, order));
                        System.out.print("New arrival  >> ");
                        c = new Customer(5 + day, order);
                        c.print();
                        System.out.println();
                        System.out.print("Packing 1    >> ");
                        orderQueue.peek().print();
                        int rem = RemainCheck(RefillPerOddDay, orderQueue.peek().getOrder(), 1);
                        if (RefillPerOddDay == rem) {
                            billingQueue.addLast(orderQueue.poll());
                            System.out.print("Packing 2    >> ");
                            orderQueue.peek().print();
                            RefillPerOddDay = RemainCheck(RefillPerOddDay, orderQueue.peek().getOrder(), 2);
                            orderQueue.add(billingQueue.pollLast());
                            System.out.printf("\n\n");
                            continue;
                        }
                        RefillPerOddDay = rem;
                        System.out.println();
                        System.out.print("Packing 2    >> ");
                        orderQueue.peek().print();
                        RefillPerOddDay = RemainCheck(RefillPerOddDay, orderQueue.peek().getOrder(), 2);
                        System.out.printf("\n\n");
                    } catch (Exception e) {
                        System.out.println("No remaining customer in order queue\n");
                    }
                } else {
                    try {
                        System.out.println("Day " + day);
                        order = random.nextInt(20) + 1;
                        orderQueue.add(new Customer(5 + day, order));
                        System.out.print("New arrival  >> ");
                        c = new Customer(5 + day, order);
                        c.print();
                        System.out.println();
                        System.out.print("Packing 1    >> ");
                        orderQueue.peek().print();
                        int rem = RemainCheck(RefillPerOddDay, orderQueue.peek().getOrder(), 1);
                        if (RefillPerOddDay == rem) { //fail
                            System.out.println();
                            billingQueue.addFirst(orderQueue.poll());
                            System.out.print("Packing 2    >> ");
                            orderQueue.peek().print();
                            RefillPerOddDay = RemainCheck(RefillPerOddDay, orderQueue.peek().getOrder(), 2);
                            orderQueue.add(billingQueue.pollFirst());
                            for (Customer c : billingQueue) {
                                System.out.print("\nBilling      >> Customer " + billingQueue.poll().getID());
                            }
                            System.out.printf("\n\n");
                            continue;
                        }
                        RefillPerOddDay = rem;
                        System.out.println();
                        System.out.print("Packing 2    >> ");
                        orderQueue.peek().print();
                        RefillPerOddDay = RemainCheck(RefillPerOddDay, orderQueue.peek().getOrder(), 2);
                    } catch (Exception e) {
                        System.out.print("No remaining customer in order queue");
                    }
                    for (Customer c : billingQueue) {
                        System.out.print("\nBilling      >> Customer " + billingQueue.poll().getID());
                    }
                    System.out.printf("\n\n");
                }
            }
        System.out.println("\n=== Remaining customers in order queue ===");
        while (orderQueue.size() > 0) {
            orderQueue.poll().print();
            System.out.println();
        }
        System.out.println("\n=== Remaining customers in billing queue (latest to earliest) ===");
        Deque<Customer> billingQueueReverse = new ArrayDeque<>(billingQueue);
        while (!billingQueueReverse.isEmpty()) {
            billingQueueReverse.pollLast().print();
            System.out.println();
        }
    }
    private int RemainCheck(int RefillPerOddDay, int order, int pack) {
        if (RefillPerOddDay - order >= 0) {
            RefillPerOddDay = RefillPerOddDay - order;
            System.out.printf("  success      Remaining items = %4d lots", RefillPerOddDay);
            billingQueue.add(orderQueue.poll());
            return RefillPerOddDay;
        } else {
            System.out.print("  failure");
            return RefillPerOddDay;
        }
    }
}

class SortCustomerbyOrder implements Comparator<Customer> {
    public int compare(Customer c1, Customer c2) {
        return c1.getOrder() - c2.getOrder();
    }
}

class SortCustomerbyID implements Comparator<Customer> {
    public int compare(Customer c1, Customer c2) {
        return c1.getID() - c2.getID();
    }
}
