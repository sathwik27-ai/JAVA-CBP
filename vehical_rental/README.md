
```markdown
# 🚗 Vehicle Rental Management System

This is a Java-based Vehicle Rental Management System built for coursework. It allows users to view available vehicles, register as customers, and book vehicles for specific dates. The system uses JDBC for MySQL database connectivity and is designed to run in Eclipse IDE.

## 🛠️ Technologies Used
- Java (JDK 8+)
- JDBC (Java Database Connectivity)
- MySQL (Database)
- Eclipse IDE
- Git & GitHub for version control

## 📁 Project Structure
```
src/
├── dao/
│   ├── DBConnection.java
│   ├── VehicleDAO.java
│   ├── CustomerDAO.java
│   └── BookingDAO.java
├── model/
│   ├── Vehicle.java
│   ├── Customer.java
│   └── Booking.java
└── main/
    └── Main.java
```

## 🗃️ Database Setup (MySQL)
Run the following SQL to create the database and tables:

```sql
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
```

## 🚙 Sample Vehicle Data

```sql
INSERT INTO vehicles (model, type, price_per_day, available) VALUES
('Honda City', 'Sedan', 2500.00, true),
('Hyundai Creta', 'SUV', 3200.00, true),
('Maruti Swift', 'Hatchback', 1800.00, true),
('Toyota Innova Crysta', 'MPV', 3500.00, true),
('Royal Enfield Classic 350', 'Bike', 900.00, true);
```

## ▶️ How to Run the Project

1. Clone the repository:
   ```
   git clone https://github.com/Laasya8/vehical_rental.git
   ```

2. Open the project in Eclipse IDE

3. Add MySQL JDBC Driver:
   - Download from [dev.mysql.com](https://dev.mysql.com/downloads/connector/j/)
   - Right-click project → Build Path → Add External JARs → Select the `.jar` file

4. Update DB credentials in `DBConnection.java`:
   ```java
   private static final String URL = "jdbc:mysql://localhost:3306/vehicle_rental";
   private static final String USER = "root";
   private static final String PASSWORD = "";
   ```

5. Run `Main.java` to start the console-based app

## 👥 Contributors
- Laasya (Project Lead)
- Friends and collaborators via GitHub

## 📌 Features
- View available vehicles
- Register customers
- Book vehicles with date validation
- JDBC-based MySQL integration
- GitHub collaboration-ready

## 📬 Contact
For questions or contributions, open an issue or reach out via GitHub.

```

---

Just paste this into a file named `README.md` in your project root folder or directly into your GitHub repo. Let me know if you want to add screenshots or upgrade it with GUI instructions!
