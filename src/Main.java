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
public class Main {
    static Map<Integer, Restaurant> restaurants = new HashMap<>();
    static Restaurant curr_res = null;
    static Map<MenuItem, Integer> cart = new HashMap<>();
    static List<Order> orders = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        create_restaurant();
        while (true) {
            System.out.println("\n1.View Restaurants\n2.View Cart\n3.Clear Cart\n4.Place Order\n0.Exit");
            int ch = sc.nextInt();
            switch (ch) {
                case 0 -> {return;}
                case 1 -> viewRestaurants();
                case 2 -> viewCart();
                case 3 -> clearCart();
                case 4 -> placeOrder();
            }
        }
    }
    static void create_restaurant() {
        Restaurant r1 = new Restaurant(1, "Kumaraguru Mess");
        r1.menu.add(new MenuItem(1, "Parotta", 200));
        r1.menu.add(new MenuItem(2, "kolambu mutta kalaki", 120));
        Restaurant r2 = new Restaurant(2, "Sam Hotel");
        r2.menu.add(new MenuItem(1, "Sam fried chicken", 250));
        r2.menu.add(new MenuItem(2, "Fried rice", 100));
        restaurants.put(r1.id, r1);
        restaurants.put(r2.id, r2);
    }
    static void viewRestaurants() {
        while (true) {
            System.out.println("\n--- Restaurants ---");
            for (Restaurant r : restaurants.values()) System.out.println(r.id + " " + r.name);
            System.out.println("0. Back");
            System.out.print("Enter restaurant id or 0 to go back: ");
            int choice = sc.nextInt();
            if(restaurants.containsKey(choice)) viewMenu(choice);
            else if(choice == 0) return;
            else System.out.println("Invalid choice");
        }
    }
    static void viewMenu(int id) {
        Restaurant r = restaurants.get(id);
        if(r == null){
            System.out.println("Invalid Restaurant");
            return;
        }
        while(true){
            System.out.println("\n--- "+r.name+" ---");
            for(MenuItem m:r.menu) System.out.println(m.id+"."+m.name+" rs."+m.price);
            System.out.println("Enter items to add in cart or 0 to return:");
            int choice = sc.nextInt();
            if(choice==0) return;
            MenuItem it = findItem(r,choice);
            if(it==null) System.out.println("Enter Valid Item Id!");
            else{
                System.out.println("Enter Quantity: ");
                int qty = sc.nextInt();
                addToCart(r,choice,qty);
            }
        }
    }
    static MenuItem findItem(Restaurant r,int itemId) {
            for (MenuItem m : r.menu) {
                if (m.id == itemId) return m;
            }
        return null;
    }
    static void addToCart(Restaurant r,int id,int qty) {
        if(curr_res==null){
            curr_res = r;
        }
        else if(curr_res!=r){
            System.out.println("Cannot add items from another Restaurant!\nClear cart to add items.");
            return;
        }
        MenuItem item = findItem(r,id);
        if (item == null) {
            System.out.println("Item not found");
            return;
        }
        cart.put(item, cart.getOrDefault(item, 0) + qty);
        System.out.println("Added to cart");
    }
    static void viewCart() {
        double total = 0;
        for (MenuItem m : cart.keySet()) {
            int qty = cart.get(m);
            double cost = m.price * qty;
            total += cost;
            System.out.println(m.name + " x " + qty + " = " + cost);
        }
        System.out.println("Total: " + total);
    }
    static void clearCart(){
        cart.clear();
        curr_res = null;
        System.out.println("Cart is Cleared");
    }
    static void placeOrder() {
        if (cart.isEmpty()) {
            System.out.println("Cart empty");
            return;
        }
        Order order = new Order(cart);
        orders.add(order);
        cart.clear();
        curr_res = null;
        System.out.println("Order placed! ID: " + order.id);
    }
}