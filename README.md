# Bank-Management-System
# Queries you need to perform in MySQL Database

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
