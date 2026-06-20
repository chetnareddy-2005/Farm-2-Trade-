# 🌾 Farm-2-Trade

Farm-2-Trade is a full-stack agriculture marketplace platform that directly connects farmers and retailers, enabling efficient trading, secure transactions, logistics tracking, weather-based insights, and AI-powered recommendations. The platform also incorporates Continuous Authentication and Machine Learning-based risk analysis to enhance user security.

🤝 External Service Integrations

Farm-2-Trade securely communicates with multiple third-party services through API-based integrations and B2B communication channels:

Google Gemini AI – Intelligent farming recommendations and risk assessment
Weather API – Real-time weather insights and agricultural advisories
Cashfree Payment Gateway – Secure online transactions
Gmail SMTP – Automated email notifications and alerts
Continuous Authentication Service – Machine learning-based behavioral risk analysis

---

## 🚀 Features

### 👨‍🌾 Farmer Product Management

* Add, update, and manage agricultural products
* Product image uploads
* Inventory management

### 🏪 Retailer Marketplace

* Browse available products
* Place orders directly with farmers
* Order tracking and management

### 🤖 AI-Powered Insights

* Weather-based farming recommendations
* Agricultural advisory system using Gemini AI
* Risk assessment and decision support

### 🔐 Security & Authentication

* JWT-based authentication
* Continuous authentication monitoring
* Machine learning-based behavioral analysis
* Risk analysis and security alerts

### 📦 Logistics Tracking

* Shipment tracking
* Delivery status monitoring

### 💳 Payment Integration

* Cashfree payment gateway integration

### 📧 Notification System

* Email alerts and notifications
* Order updates and confirmations

### 🗺️ Location & Mapping

* Interactive maps using Leaflet
* Location-based services

---

## 🛠️ Technology Stack

### Frontend

* React.js
* Vite
* React Router
* Leaflet Maps
* CSS

### Backend

* Spring Boot
* Spring Security
* Spring Data JPA
* Hibernate
* JWT Authentication

### Database

* MySQL

### AI & Machine Learning

* Google Gemini AI
* Continuous Authentication ML Model
* Weather API

### External Services

* Cashfree Payment Gateway
* Gmail SMTP

---

## 📂 Project Structure

```text
Farm-2-Trade
│
├── backend/              # Spring Boot Backend
├── frontend/             # React Frontend
├── continuous-auth/      # ML-Based Continuous Authentication Service
├── architecture.png
├── README.md
└── LICENSE
```

---

## ⚙️ Prerequisites

Before running the project, install:

* Java 17+
* Maven 3.8+
* Node.js 18+
* Python 3.10+
* MySQL 8+
* Git

---

## 🗄️ Database Setup

Create the database:

```sql
CREATE DATABASE agri;
```

Update database credentials in:

```properties
backend/src/main/resources/application.properties
```

Example:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/agri
spring.datasource.username=root
spring.datasource.password=your_password
```

---

## 🔒 Continuous Authentication Setup

Navigate to the authentication module:

```bash
cd continuous-auth
```

Create a virtual environment:

```bash
python -m venv venv
venv\Scripts\activate
```

Install dependencies:

```bash
pip install -r requirements.txt
```

Train the ML model:

```bash
python -m src.train
```

Start the authentication service:

```bash
python app.py
```

Authentication Service URL:

```text
http://localhost:5000
```

---

## 🔧 Backend Setup

Navigate to backend:

```bash
cd backend
```

Run Spring Boot application:

```bash
mvn spring-boot:run
```

Backend URL:

```text
http://localhost:8080
```

---

## 🎨 Frontend Setup

Navigate to frontend:

```bash
cd frontend
```

Install dependencies:

```bash
npm install
```

Start development server:

```bash
npm run dev
```

Frontend URL:

```text
http://localhost:5173
```

---

## ▶️ Running the Complete Application

Start services in the following order:

### 1. Start MySQL

Ensure MySQL service is running and the database exists.

### 2. Start Continuous Authentication Service

```bash
cd continuous-auth
python app.py
```

### 3. Start Spring Boot Backend

```bash
cd backend
mvn spring-boot:run
```

### 4. Start React Frontend

```bash
cd frontend
npm run dev
```

---

## 🌐 Application URLs

| Service                   | URL                   |
| ------------------------- | --------------------- |
| Frontend                  | http://localhost:5173 |
| Backend                   | http://localhost:8080 |
| Continuous Authentication | http://localhost:5000 |
| MySQL                     | localhost:3306        |

---

## 🔐 Environment Variables

Configure the following variables before deployment:

```text
GEMINI_API_KEY
WEATHER_API_KEY
LOCATION_API_KEY
CASHFREE_APP_ID
CASHFREE_SECRET_KEY
MAIL_USERNAME
MAIL_PASSWORD
SPRING_DATASOURCE_PASSWORD
```

---

## 🧠 AI Features

The platform uses Google Gemini AI for:

* Weather-based agricultural recommendations
* Farmer advisory support
* Security risk assessment insights
* Intelligent decision support

---

## 🔄 System Workflow

```text
User
  ↓
React Frontend
  ↓
Spring Boot Backend
  ↓
MySQL Database
  ↓
Continuous Authentication Service
  ↓
Machine Learning Risk Analysis
  ↓
Gemini AI Recommendation
  ↓
ALLOW / OTP / BLOCK
```

---

## 📸 Screenshots

Add screenshots of:

* Home Page
* Farmer Dashboard
* Retailer Dashboard
* Product Marketplace
* Logistics Tracker
* Weather Advisory Dashboard
* Authentication Dashboard

---

## 🔮 Future Enhancements

* Mobile Application
* Multi-language Support
* Crop Disease Detection
* Real-Time Market Price Prediction
* Advanced Analytics Dashboard
* IoT Sensor Integration
* AI-Based Demand Forecasting

---

## 👩‍💻 Author

**Chetna Reddy C.Y**

B.Tech Student | Full Stack Developer | AI & Cloud Enthusiast

GitHub:
https://github.com/chetnareddy-2005

---

## 📄 License

This project is licensed under the MIT License.
