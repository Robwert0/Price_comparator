# 🛒 Grocery Price Comparator API

This project is a Spring Boot backend application designed to track and compare grocery prices from major Romanian retailers like Lidl, Kaufland, and Profi. It provides a RESTful API to access product information and price data, acting as a foundation for a price comparison service.

## ✨ Features

* **RESTful API:** A clean and modern API for fetching product and price data.
* **Multi-Retailer Support:** Built to handle data from multiple stores (e.g., Lidl, Kaufland, Profi).
* **Price Tracking:** Core logic to store and manage product prices over time.
* **Scalable Architecture:** Uses a standard Spring Boot service-repository pattern, making it easy to extend.
* **Maven Build Management:** Easy to build, test, and run with Apache Maven.

## 💻 Technology Stack

* **Java 17+**
* **Spring Boot:** For building the core application and RESTful services.
* **Spring Data JPA:** For database interaction and repository management.
* **H2 Database (or other):** For storing product and price information.
* **Maven:** For project dependency management and build automation.

## 📂 Project Structure

The project follows a standard Spring Boot application structure:
```bash
├── .mvn/
├── src/
│   ├── main/
│   │   ├── java/com/example/Price_comparator/
│   │   │   ├── controller/    # API endpoints (REST controllers)
│   │   │   ├── model/         # Data entities (e.g., Product)
│   │   │   ├── repository/    # Data access layer (JPA repositories)
│   │   │   └── service/       # Business logic
│   │   └── resources/
│   │       └── application.properties
│   └── test/
├── .gitignore
├── pom.xml                  # Maven project configuration
└── README.md
```

## 🚀 Setup and Installation

Follow these steps to get the project up and running on your local machine.

### Prerequisites

* **Java Development Kit (JDK)** - Version 17 or later.
* **Apache Maven** - To build the project and manage dependencies.

### Installation Steps

1.  **Clone the repository:**
    ```bash
    git clone [https://github.com/Robwert0/Price_comparator.git](https://github.com/Robwert0/Price_comparator.git)
    cd Price_comparator
    ```

2.  **Build the project:**
    Use Maven to compile the code and download all dependencies.
    ```bash
    mvn clean install
    ```

## ▶️ How to Run

You can run the application directly using the Spring Boot Maven plugin:

```bash
mvn spring-boot:run
```

The application will start, and the API will be accessible on `http://localhost:8080` (or the port configured in `application.properties`).

## ⚙️ API Endpoints
Once the application is running, you can interact with the API using a tool like Postman, Insomnia, or `curl`.

### Get All Products
* Request: `GET /api/v1/products`
* Description: Retrieves a list of all products from all retailers.
  
### Get Product by ID
* Request: `GET /api/v1/products/{id}`
* Description: Retrieves a single product by its unique ID.

### Add a New Product
* Request: `POST /api/v1/products`
* Description: Adds a new product to the database.
* Body (JSON):
  ```bash
  JSON
  {
    "name": "Lapte Zuzu 1.5L",
    "price": 7.50,
    "retailer": "Kaufland"
  }
   ```
  
### Search for Products
* Request: `GET /api/v1/products/search?name=lapte`
* Description: Searches for products containing a specific keyword in their name.

## 🤝 Contributing
Contributions are welcome! If you have ideas for new features or find any bugs, please feel free to open an issue or submit a pull request.

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/NewRetailer`)
3. Commit your Changes (`git commit -m 'Add support for a new retailer'`)
4. Push to the Branch (`git push origin feature/NewRetailer`)
5. Open a Pull Request
