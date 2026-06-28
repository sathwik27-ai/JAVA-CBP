# 🚗 Vehicle Rental Management System

A Java-based Vehicle Rental Management System developed as a coursework project.  
This application allows users to view available vehicles, register customers, and book vehicles for specific dates.

---

## 🛠️ Technologies Used

- Java (JDK 8+)
- JDBC (Java Database Connectivity)
- MySQL (Database)
- Eclipse IDE
- Git & GitHub

---

## 📁 Project Structure

src/

├── dao/

│ ├── DBConnection.java

│ ├── VehicleDAO.java

│ ├── CustomerDAO.java

│ └── BookingDAO.java

├── model/

│ ├── Vehicle.java

│ ├── Customer.java

│ └── Booking.java

└── main/

└── Main.java

---

## 🗃️ Database Setup (MySQL)

Run the following SQL commands:

 ```sql :
CREATE DATABASE vehicle_rental;

USE vehicle_rental;

CREATE TABLE vehicles (
    id INT AUTO_INCREMENT PRIMARY KEY,
    model VARCHAR(100),
    type VARCHAR(50),
    price_per_day DECIMAL(10,2),
    available BOOLEAN DEFAULT TRUE
);

CREATE TABLE customers (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    email VARCHAR(100),
    phone VARCHAR(15)
); 

CREATE TABLE bookings (
    id INT AUTO_INCREMENT PRIMARY KEY,
    customer_id INT,
    vehicle_id INT,
    start_date DATE,
    end_date DATE,
    FOREIGN KEY (customer_id) REFERENCES customers(id),
    FOREIGN KEY (vehicle_id) REFERENCES vehicles(id)
);

INSERT INTO vehicles (model, type, price_per_day, available) VALUES
('Honda City', 'Sedan', 2500.00, true),
('Hyundai Creta', 'SUV', 3200.00, true),
('Maruti Swift', 'Hatchback', 1800.00, true),
('Toyota Innova Crysta', 'MPV', 3500.00, true),
('Royal Enfield Classic 350', 'Bike', 900.00, true);
```

▶️ How to Run

Clone the repository:

git clone https://github.com/sathwik27-ai/JAVA-CBP.git

Open in Eclipse IDE

Add MySQL JDBC Driver:
Download from: https://dev.mysql.com/downloads/connector/j/

Right click project → Build Path → Add External JARs

Update database credentials in DBConnection.java:

private static final String URL = "jdbc:mysql://localhost:3306/vehicle_rental";

private static final String USER = "root";

private static final String PASSWORD = "your_password";

RUN : Main.java

📌 Features

1. View available vehicles

2. Customer registration

3. Vehicle booking system

4. Date validation

5. JDBC + MySQL integration

👨‍💻 Author

Chandanapu Sathwik

📬 Contact

Feel free to open issues or contribute to improve this project.

---

