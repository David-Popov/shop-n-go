# Shop-N-Go Project Documentation

## Overview
Shop-N-Go is an e-commerce backend application built with Spring Boot, PostgreSQL, and Docker. The application provides REST APIs for managing products, shopping carts, and orders.

## Prerequisites
- Docker and Docker Compose
- Java 17
- Maven (if running locally)

## Getting Started

### Running the Application
1. Clone the repository
2. Navigate to the project directory
3. Run the application using Docker:
```bash
docker compose up --build
```
The application will be available at `http://localhost:8088`

### Database Setup
The application automatically creates the required tables and loads sample data on startup.

### Querying Data
To get the necessary IDs for testing the APIs, you can use these SQL queries:

```sql
-- Get user IDs
SELECT id, email, username FROM users;

-- Get product IDs
SELECT id, name, price FROM products;

-- Get category names
SELECT name FROM categories;
```

## Sample Data

### Users
| ID | Email | Username |
|---|---|---|
| c08307ee-dc83-4d9e-a87c-da5da5f8798d | user1@example.com | user1 |
| 8867bddd-e834-4ea3-96dc-3a2269703b49 | user2@example.com | user2 |
| 906f61ad-ca37-45d9-9163-8d7dd15ec332 | user3@example.com | user3 |
| 09c927cb-1333-4a63-a96d-4eddf29c1838 | user4@example.com | user4 |
| 226fdd40-649f-4b63-8610-2ea0d4de3f31 | user5@example.com | user5 |

### Products
| ID | Name | Price |
|---|---|---|
| dcd9c91d-a8bd-410a-b8de-9c8d3bbf341d | Wireless Bluetooth Headphones | 99.99 |
| b07d18fd-8ab0-4878-87fc-11f0ce7814e5 | Smartwatch Pro 3000 | 199.99 |
| f7ef8e36-0a57-4c1c-8de6-2ab1c173af9c | 4K Ultra HD TV 55" | 699.99 |
| 3fb5b352-4c97-4892-82b4-ef54a5e1b108 | Gaming Laptop GX-900 | 1299.99 |
| b5b7ce36-6287-48ef-a7ae-ef3c220b8885 | USB-C Fast Charger | 29.99 |

### Categories
- Electronics
- Audio
- Mobile Accessories
- Computers
- Gaming
- Home Electronics
- Wearables

## API Documentation

### Products API

#### Get All Products (Paginated)
```http
GET http://localhost:8088/products?page=0&size=5&sortBy=price&direction=DESC
```

#### Get Products by Category
```http
GET http://localhost:8088/products/categories?categories=Electronics,Gaming&page=0&size=10
```

#### Search Products
```http
GET http://localhost:8088/products/search?term=laptop&page=0&size=10
```

#### Filter Products by Price and Rating
```http
GET http://localhost:8088/products/filter?minPrice=100&maxPrice=1000&minRating=4.5
```

### Cart API

#### View User's Cart
```http
GET http://localhost:8088/cart/c08307ee-dc83-4d9e-a87c-da5da5f8798d
```

#### Add Item to Cart
```http
POST http://localhost:8088/cart/c08307ee-dc83-4d9e-a87c-da5da5f8798d/items?productId=dcd9c91d-a8bd-410a-b8de-9c8d3bbf341d&quantity=2
```

#### Get Cart Summary
```http
GET http://localhost:8088/cart/c08307ee-dc83-4d9e-a87c-da5da5f8798d/summary
```

### Orders API

#### Create New Order
```http
POST http://localhost:8088/orders/c08307ee-dc83-4d9e-a87c-da5da5f8798d
Content-Type: application/json

{
    "deliveryAddress": "123 Test Street",
    "deliveryPhoneNumber": "+1234567890",
    "deliveryName": "Test User",
    "deliveryEmail": "test@example.com"
}
```

#### Get User's Orders
```http
GET http://localhost:8088/orders/c08307ee-dc83-4d9e-a87c-da5da5f8798d?page=0&size=10
```

#### View Specific Order
```http
GET http://localhost:8088/orders/c08307ee-dc83-4d9e-a87c-da5da5f8798d/{ORDER_ID}
```

## Response Format
All API responses follow this format:
```json
{
    "date": "2024-11-08T00:00:00",
    "errorDescription": "",
    "responseId": "20241108000000000000",
    "status": "OK",
    "description": "",
    "data": {}
}
```

## Error Handling
The API uses HTTP status codes and provides detailed error messages in the response body. Common status codes:
- 200: Success
- 400: Bad Request
- 404: Not Found
- 500: Internal Server Error

## Security
The application uses JWT authentication. Include the JWT token in the Authorization header for protected endpoints.

## Notes
- The database is automatically populated with sample data on startup
- All IDs are UUIDs
- Pagination is zero-based (first page is 0)
- Sort direction can be ASC or DESC

## Authentication

### Register a New User
```http
POST http://localhost:8088/users/create
Content-Type: application/json

{
    "data": {
        "email": "test@example.com",
        "username": "testuser",
        "googleId": "google_test_id",
        "pictureUrl": "http://example.com/picture.jpg",
        "phoneNumber": "+1234567890"
    }
}
```

### Login
```http
POST http://localhost:8088/users/login
Content-Type: application/json

{
    "data": {
        "googleId": "google_test_id"
    }
}
```

The login response will include a JWT token that must be included in subsequent requests:

```json
{
    "token": "eyJhbGciOiJIUzI1NiJ9..."
}
```

### Using the Token
Include the token in the Authorization header for protected endpoints:

```http
GET http://localhost:8088/cart/{userId}
Authorization: eyJhbGciOiJIUzI1NiJ9...
```

Note: Users can only access their own cart and order data. Attempting to access other users' data will result in a 403 Forbidden response.

## API Endpoints

### Products (Public)
- `GET /products?page=0&size=5&sortBy=price&direction=DESC`
- `GET /products/categories?categories=Electronics,Gaming&page=0&size=10`
- `GET /products/search?term=laptop&page=0&size=10`
- `GET /products/filter?minPrice=100&maxPrice=1000&minRating=4.5`

### Cart (Protected)
- `GET /cart/{userId}` - Get user's cart
- `POST /cart/{userId}/items?productId={productId}&quantity=2` - Add item to cart
- `GET /cart/{userId}/summary` - Get cart summary

### Orders (Protected)
- `POST /orders/{userId}` - Create new order
- `GET /orders/{userId}?page=0&size=10` - Get user's orders
- `GET /orders/{userId}/{orderId}` - Get specific order

## Security Notes
- All product endpoints are publicly accessible
- Cart and order endpoints require authentication
- Users can only access their own cart and orders
- JWT tokens expire after 1 hour
- Invalid or expired tokens will receive a 401 Unauthorized response