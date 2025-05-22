# 🛒 Price Comparator - Market Backend

This is a Java Spring Boot application designed to help users compare grocery prices across different stores like Lidl, Kaufland, and Profi. It supports features such as:

- Viewing product prices from multiple stores
- Viewing best and new discounts
- Setting price alerts for products
- Receiving email notifications when prices drop below a target

---

## 📁 Project Structure Overview

src/
├── controller/ # REST controllers
│ ├── BasketController.java
│ ├── DiscountController,java
│ ├── newDiscountController.java
│ ├── PriceAlertController.java
│ ├── PriceController.java
│ ├── PriceHistoryController.java
│ └── RecomandationController.java
├── model/ # Java model classes
│ ├── Product.java
│ ├── PriceEntry.java
│ ├── PriceAlert.java
│ ├── DiscountedProduct.java
│ └── BasketItems.java 
├── service/ # Business logic
│ ├── BasketOptoimizerService.java
│ ├── NewDiscountService.java
│ ├── PriceHistoryService.java
│ ├── PriceService.java
│ ├── EmailService.java
│ └── DiscountService.java
├── util/
│ ├── CsvLoader.java
│ └── PriceAlertScheduler.java
├── resources/
│ ├── application.properties
│ └── data/ # CSV files for each store's prices and discounts
└── PriceComparatorApplication.java
---

## ▶️ How to Build and Run

### ✅ Requirements

- Java 17+
- Maven
- Spring Boot
- (Optional) [MailHog](https://github.com/mailhog/MailHog) for local email testing

### 🔧 Build the project

```bash
mvn clean install

### 🚀 Run the application
./mvnw spring-boot:run

