# SQL Querying Module – RetailPulse

## 1. Objective
Practice advanced SQL querying concepts including joins, subqueries, aggregation, and grouping.

---

## 2. Schema Design

The following schema was created:

- **continents** → stores continent data
- **countries** → linked to continents (One-to-Many)
- **people** → individuals
- **citizenship** → mapping table (Many-to-Many between people and countries)

---

## 3. Relationships

- One-to-Many: Continents → Countries
- Many-to-Many: People ↔ Countries (via citizenship)

---

## 4. Data Population

- Sample data inserted for continents, countries, and people
- Includes:
    - Multiple continents
    - Countries with different population and area
    - People with multiple citizenships
    - Duplicate names for testing queries

---

## 5. Queries Implemented

### Country Queries
- Country with highest population
- Countries with lowest population density
- Countries above average density
- Country with longest name
- Countries containing specific characters
- Country closest to average population

### Continent Queries
- Count of countries per continent
- Total area per continent
- Average density per continent
- Smallest country per continent
- Continents with low average population

### People Queries
- Person with most citizenships
- People with no citizenship
- Country with least people
- Continent with most people
- Duplicate name detection

---

## 6. Concepts Covered

- SQL Joins (INNER, LEFT)
- Subqueries
- Aggregations (COUNT, SUM, AVG)
- GROUP BY and HAVING
- String functions
- Mathematical calculations
- Many-to-many relationships

---

## 7. Conclusion

This module demonstrates the ability to:
- Design relational schemas
- Write complex SQL queries
- Work with real-world data relationships
- Perform analytical and statistical queries