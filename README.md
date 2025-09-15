# 🚀 Bank Management System

## 📖 Description
This project is a **Java + MySQL based Bank Management System** developed as part of our Software Engineering coursework.  
It provides essential banking functionalities with a user-friendly interface and secure database integration.

### ✨ Features
- Open **Current / Saving Accounts**
- Deposit and Transfer Funds
- Cash Withdrawal
- Bill Payments
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

## 👨‍💻 Author
Developed by  Haziq Khan and Ahtisham Sheikh

## 🗄️ Database Setup
Run the following SQL script to create the required table:

```sql
create table accounts (
account_number varchar(16) primary key,
name varchar(30) not null,
cnic varchar(13) unique not null,
phone varchar(11) unique not null,
account_type smallint, balance double,
pin varchar(4)
);
```
