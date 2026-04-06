# RetailPulse Database Schema

## Overview
This project implements a relational database schema for a retail system.

## Entities
- Users
- Products
- Orders
- User Profile

## Relationships
- One-to-One: Users ↔ User_Profile
- One-to-Many: Users → Orders
- Many-to-Many: Users ↔ Products (via Orders)

## How to Run (Docker)

```bash
docker-compose up
