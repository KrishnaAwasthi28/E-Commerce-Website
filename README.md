<h2>🛒 E-Commerce Web Application</h2>
<h3>
  📌 Overview
This is a full-stack e-commerce application built using Spring Boot for the backend and React.js for the frontend.
It provides a complete online shopping experience with product management, cart handling, authentication, and order management.
</h3>

🚀 Features
👤 User Features
User Registration & Login (Authentication)
Browse products by category, brand, and price
Add products to the cart
Update or remove items from the cart
Checkout process

🛠️ Admin Features
Add new products with image upload
Update product details
Delete products
Manage stock availability

🏗️ Tech Stack
Frontend: React.js, Tailwind CSS, Axios
Backend: Java, Spring Boot, Spring Security, Spring Data JPA
Database: MySQL
Build Tools: Maven
Version Control: Git & GitHub

⚡ API Endpoints
Products
GET /api/products → Get all products
GET /api/products/{id} → Get product by ID
POST /api/product → Add a new product (Admin only, supports image upload)
PUT /api/products/{id} → Update product details
DELETE /api/products/{id} → Delete product

Cart
GET /api/cart/{userId} → Get cart by user ID
POST /api/cart/{cartId}/items → Add item to cart
DELETE /api/cart/item/{cartItemId} → Remove item from cart

📌 Future Enhancements
✅ JWT-based authentication
✅ Online payment integration (Razorpay/Stripe)
✅ Order history & tracking
✅ Wishlist functionality
