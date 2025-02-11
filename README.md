# 🚌 Java-Based-Bus-Voyage-Ticket-Management-System

This project is a **Java-based bus voyage and ticket management system** that follows **Object-Oriented Programming (OOP) principles**, including **abstraction, encapsulation, inheritance, and polymorphism**. The system allows users to create and manage bus trips, sell and refund tickets, and generate voyage reports.

## 📌 Features
- **Three Different Bus Types**:
  - **Standard (2+2 seats)** – Refundable with a percentage deduction.
  - **Premium (1+2 seats)** – Includes premium pricing for single seats.
  - **Minibus (2 seats per row)** – Tickets are **non-refundable**.
- **Ticketing System**:
  - **Sell single or multiple tickets at once**.
  - **Refund tickets with a predefined cut percentage**.
  - **Cancel a voyage**, automatically refunding all sold tickets.
- **Real-Time Voyage Updates**:
  - Each voyage tracks **sold, available, and refunded seats**.
  - Revenue updates dynamically as tickets are sold or refunded.
- **Formatted Seat Printing**:
  - Shows occupied and available seats in a visual format.
- **End-of-Day Reports**:
  - A **Z Report** lists all active voyages with revenue and seat status.

## 🎮 How It Works
1. **Create bus voyages** using `INIT_VOYAGE` commands in `input.txt`.
2. **Sell tickets** using `SELL_TICKET`.
3. **Refund tickets** using `REFUND_TICKET`.
4. **Cancel a voyage** using `CANCEL_VOYAGE`.
5. **View a summary of all voyages** using `Z_REPORT`.


## 🚀 Running the Program
Compile and run the program using:
```bash
javac8 BookingSystem.java
java8 BookingSystem input.txt output.txt


