import java.util.*;
class MenuItem {
    int id;
    String name;
    double price;
    MenuItem(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }
}
class Restaurant {
    int id;
    String name;
    List<MenuItem> menu = new ArrayList<>();
    Restaurant(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
class Order {
    static int counter = 1;
    int id;
    Map<MenuItem, Integer> items;
    double total;
    String status;
    Order(Map<MenuItem, Integer> items) {
        this.id = counter++;
        this.items = new HashMap<>(items);
        this.status = "PLACED";
        calculateTotal();
    }
    void calculateTotal() {
        total = 0;
        for (MenuItem item : items.keySet()) {
            total += item.price * items.get(item);
        }
    }
}
class FoodApp {
    static Map<Integer, Restaurant> restaurants = new HashMap<>();
    static Map<MenuItem, Integer> cart = new HashMap<>();
    static List<Order> orders = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        seedData();
        while (true) {
            System.out.println("\n1.View Restaurants\n2.View Menu\n3.Add to Cart\n4.View Cart\n5.Place Order\n6.Exit");
            int ch = sc.nextInt();
            switch (ch) {
                case 1 -> viewRestaurants();
                case 2 -> viewMenu();
                case 3 -> addToCart();
                case 4 -> viewCart();
                case 5 -> placeOrder();
                case 6 -> System.exit(0);
            }
        }
    }
    static void seedData() {
        Restaurant r1 = new Restaurant(1, "Domino's");
        r1.menu.add(new MenuItem(1, "Pizza", 200));
        r1.menu.add(new MenuItem(2, "Burger", 120));
        Restaurant r2 = new Restaurant(2, "KFC");
        r2.menu.add(new MenuItem(3, "Chicken", 250));
        r2.menu.add(new MenuItem(4, "Fries", 100));
        restaurants.put(r1.id, r1);
        restaurants.put(r2.id, r2);
    }
    static void viewRestaurants() {
        for (Restaurant r : restaurants.values()) {
            System.out.println(r.id + " " + r.name);
        }
    }
    static void viewMenu() {
        System.out.print("Enter restaurant id: ");
        int id = sc.nextInt();
        Restaurant r = restaurants.get(id);
        if (r == null) {
            System.out.println("Invalid restaurant");
            return;
        }
        for (MenuItem m : r.menu) {
            System.out.println(m.id + " " + m.name + " Rs." + m.price);
        }
    }
    static MenuItem findItem(int itemId) {
        for (Restaurant r : restaurants.values()) {
            for (MenuItem m : r.menu) {
                if (m.id == itemId) return m;
            }
        }
        return null;
    }
    static void addToCart() {
        System.out.print("Enter item id: ");
        int id = sc.nextInt();

        MenuItem item = findItem(id);
        if (item == null) {
            System.out.println("Item not found");
            return;
        }
        cart.put(item, cart.getOrDefault(item, 0) + 1);
        System.out.println("Added to cart");
    }
    static void viewCart() {
        double total = 0;
        for (MenuItem m : cart.keySet()) {
            int qty = cart.get(m);
            double cost = m.price * qty;
            total += cost;
            System.out.println(m.name + " x" + qty + " = " + cost);
        }
        System.out.println("Total: " + total);
    }
    static void placeOrder() {
        if (cart.isEmpty()) {
            System.out.println("Cart empty");
            return;
        }
        Order order = new Order(cart);
        orders.add(order);
        cart.clear();
        System.out.println("Order placed! ID: " + order.id);
    }
}