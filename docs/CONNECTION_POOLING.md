# Connection Pooling in RetailPulse

## Objective
To compare performance between single connection and connection pooling under concurrent load.

## Approach

### 1. Single Connection DataSource
- Used one shared connection
- Threads executed sequentially

### 2. HikariCP Connection Pool
- Used multiple connections
- Threads executed in parallel

## Experiment
- 5 threads
- Each executed: SELECT pg_sleep(2)

## Results

| Approach | Time | Behavior |
|---------|------|----------|
| Single Connection | ~10000 ms | Sequential |
| HikariCP | ~2000 ms | Parallel |

## Conclusion
Connection pooling significantly improves performance by allowing parallel execution and reducing connection creation overhead.