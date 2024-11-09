package javawizzards.shopngo;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import javawizzards.shopngo.entities.*;
import javawizzards.shopngo.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Component
public class DataLoader {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @PostConstruct
    @Transactional
    public void loadData() throws IOException {
        // Clear all existing data
        orderItemRepository.deleteAll();
        cartItemRepository.deleteAll();
        orderRepository.deleteAll();
        cartRepository.deleteAll();
        productRepository.deleteAll();
        userRepository.deleteAll();
        categoryRepository.deleteAll();

        // Load and save products
        ObjectMapper mapper = new ObjectMapper();
        List<Product> products = mapper.readValue(
                new ClassPathResource("products.json").getInputStream(),
                new TypeReference<List<Product>>() {}
        );
        products = productRepository.saveAll(products);

        // Create categories
        List<Category> categories = createCategories();
        categories = categoryRepository.saveAll(categories);

        // Assign categories to products
        assignCategoriesToProducts(products, categories);
        productRepository.saveAll(products);

        // Create users
        List<User> users = createUsers();
        users = userRepository.saveAll(users);

        // Create carts for users
        createCartsForUsers(users, products);

        // Create orders
        createOrders(users, products);
    }

    private List<Category> createCategories() {
        List<Category> categories = new ArrayList<>();
        String[] categoryNames = {
                "Electronics", "Audio", "Mobile Accessories", "Computers",
                "Gaming", "Home Electronics", "Wearables"
        };

        for (String name : categoryNames) {
            Category category = new Category();
            category.setName(name);
            category.setDescription("Category for " + name.toLowerCase());
            categories.add(category);
        }

        return categories;
    }

    private void assignCategoriesToProducts(List<Product> products, List<Category> categories) {
        Map<String, List<Category>> productCategories = new HashMap<>();
        productCategories.put("Wireless Bluetooth Headphones", Arrays.asList(categories.get(0), categories.get(1)));
        productCategories.put("Smartwatch Pro 3000", Arrays.asList(categories.get(0), categories.get(6)));
        productCategories.put("4K Ultra HD TV 55\"", Arrays.asList(categories.get(0), categories.get(5)));
        productCategories.put("Gaming Laptop GX-900", Arrays.asList(categories.get(0), categories.get(3), categories.get(4)));
        productCategories.put("USB-C Fast Charger", Arrays.asList(categories.get(0), categories.get(2)));

        for (Product product : products) {
            List<Category> productCats = productCategories.get(product.getName());
            if (productCats != null) {
                product.setCategories(new HashSet<>(productCats));
            }
        }
    }

    private List<User> createUsers() {
        List<User> users = new ArrayList<>();

        for (int i = 1; i <= 5; i++) {
            User user = new User();
            user.setGoogleId("google_" + i);
            user.setEmail("user" + i + "@example.com");
            user.setUsername("user" + i);
            user.setPictureUrl("http://example.com/profile" + i + ".jpg");
            user.setPhoneNumber("+1234567890" + i);
            users.add(user);
        }

        return users;
    }

    private void createCartsForUsers(List<User> users, List<Product> products) {
        Random random = new Random();

        for (User user : users) {
            Cart cart = new Cart();
            cart.setUser(user);
            cart.setTotalPrice(BigDecimal.ZERO);
            cart = cartRepository.save(cart);

            // Add 1-3 random products to each cart
            int numItems = random.nextInt(3) + 1;
            for (int i = 0; i < numItems; i++) {
                Product randomProduct = products.get(random.nextInt(products.size()));

                CartItem cartItem = new CartItem();
                cartItem.setCart(cart);
                cartItem.setProduct(randomProduct);
                cartItem.setQuantity(random.nextInt(3) + 1);
                cartItem.setTotalPrice(randomProduct.getPrice()
                        .multiply(BigDecimal.valueOf(cartItem.getQuantity())));

                cartItemRepository.save(cartItem);
                cart.getItems().add(cartItem);
            }

            cart.calculateTotalPrice();
            cartRepository.save(cart);
        }
    }

    private void createOrders(List<User> users, List<Product> products) {
        Random random = new Random();
        Order.Status[] statuses = Order.Status.values();

        for (User user : users) {
            // Create 1-3 orders per user
            int numOrders = random.nextInt(3) + 1;

            for (int i = 0; i < numOrders; i++) {
                Order order = new Order();
                order.setUser(user);
                order.setStatus(statuses[random.nextInt(statuses.length)]);
                order.setDeliveryAddress(user.getUsername() + "'s Address, Street " + (i + 1));
                order.setDeliveryPhoneNumber(user.getPhoneNumber());
                order.setDeliveryName(user.getUsername());
                order.setDeliveryEmail(user.getEmail());
                order.setCreatedAt(LocalDateTime.now().minusDays(random.nextInt(30)));
                order = orderRepository.save(order);

                // Add 1-4 random products to each order
                int numItems = random.nextInt(4) + 1;
                for (int j = 0; j < numItems; j++) {
                    Product randomProduct = products.get(random.nextInt(products.size()));

                    OrderItem orderItem = new OrderItem();
                    orderItem.setOrder(order);
                    orderItem.setProduct(randomProduct);
                    orderItem.setQuantity(random.nextInt(3) + 1);
                    orderItem.setPriceAtPurchase(randomProduct.getPrice());
                    orderItem.setTotalPrice(orderItem.getPriceAtPurchase()
                            .multiply(BigDecimal.valueOf(orderItem.getQuantity())));

                    orderItemRepository.save(orderItem);
                    order.getOrderItems().add(orderItem);
                }

                order.calculateTotalPrice();
                orderRepository.save(order);
            }
        }
    }
}