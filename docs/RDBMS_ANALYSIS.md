# RDBMS Analysis – RetailPulse

## 1. ACID Violation

Without transaction:

* Stock updated but order failed → inconsistent state

With transaction:

* Rollback ensured consistency

## 2. Isolation Levels

READ_COMMITTED:

* Observed non-repeatable reads

SERIALIZABLE:

* Ensured consistent data visibility

## 3. Index Performance

Without index:

* Sequential scan used
* Execution time ~45 ms

With index:

* Bitmap index scan used
* Execution time ~2.7 ms

## 4. Composite Index

(name, price):

Full match:

* Index used
* Execution time ~0.06 ms

Partial match (price only):

* Composite index not used
* Single-column index used instead

## Conclusion

Transactions ensure consistency, isolation levels control concurrency, and indexing significantly improves query performance.
