# JDBC – Statement vs PreparedStatement

## Statement

- Executes SQL queries using string concatenation
- Query is compiled every time
- Vulnerable to SQL injection

Example:

Statement stmt = connection.createStatement();
String query = "SELECT * FROM users WHERE name = '" + input + "'";
ResultSet rs = stmt.executeQuery(query);

---

## PreparedStatement

- Uses parameterized queries
- Query is precompiled
- Prevents SQL injection

Example:

PreparedStatement ps = connection.prepareStatement(
"SELECT * FROM users WHERE name = ?"
);
ps.setString(1, input);
ResultSet rs = ps.executeQuery();

---

## Key Difference

Statement → unsafe  
PreparedStatement → safe

---

## SQL Injection Example

Input:
' OR '1'='1

Statement executes:
SELECT * FROM users WHERE name = '' OR '1'='1'

→ Returns all users ❌

PreparedStatement:
SELECT * FROM users WHERE name = ?

→ Safe ✅
---

## 6. DriverManager vs DataSource

### DriverManager
- Direct way to get DB connection
- Uses static method: DriverManager.getConnection()
- Creates new connection every time

### DataSource
- More advanced and flexible
- Supports connection pooling
- Managed by application server or libraries (e.g. HikariCP)

### Difference

- DriverManager → simple but not scalable
- DataSource → efficient and production-ready

---

## 7. execute() Approaches Comparison

### Approach A
```java
execute(String query, Object... args)

## Connection Pooling

### Without Pool (SimpleDataSource)
- Single connection reused
- Threads executed sequentially
- Total time ≈ 10 seconds

### With HikariCP
- Multiple connections used
- Threads executed in parallel
- Total time ≈ 2–3 seconds

### Conclusion
Connection pooling improves performance by allowing multiple database operations to run concurrently.