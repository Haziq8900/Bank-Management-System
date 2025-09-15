# 🚀 Bank Management System

## 📖 Description
This project is a **Java + MySQL based Bank Management System** developed as part of our Software Engineering coursework.  
It provides essential banking functionalities with a user-friendly interface and secure database integration.

### ✨ Features
- Open **Current / Saving Accounts**
- Deposit and Transfer Funds
- Cash Withdrawal
- Close Accounts
- Real-time Database Integration with MySQL

## 🎥 Demo
➡️ [Click here to watch full demo](https://www.linkedin.com/your-post-link)  

![Demo GIF](screenshots/demo.gif)  

## 📸 Screenshots
### Dashboard
![Dashboard](screenshots/screen1.png)  

### Database View
![Database](screenshots/screen2.png)  

## 🛠 Tech Stack
- Java  
- Java Swing  
- MySQL  

## 👨‍💻 Authors
- **Haziq Khan**  
- **Ahtisham Sheikh**

---

## 🗄️ Database Setup
Run the following SQL script to create the required table:

```sql
CREATE TABLE accounts (
  account_number VARCHAR(16) PRIMARY KEY,
  name VARCHAR(30) NOT NULL,
  cnic VARCHAR(13) UNIQUE NOT NULL,
  phone VARCHAR(11) UNIQUE NOT NULL,
  account_type SMALLINT,
  balance DOUBLE,
  pin VARCHAR(4)
);
