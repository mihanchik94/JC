## Описание:
Есть созданная существующая база данных с таблицами.
В приложении, которое использует эту базу есть вот такой запрос:
```
SELECT SUM(products.price * cart_items.quantity) AS total_cost
FROM products
JOIN cart_items ON products.id = cart_items.product_id
JOIN orders ON orders.user_id = cart_items.user_id
WHERE orders.status = 'active'
AND orders.user_id = <user_id>;
```
Данный запрос работает медленно.

## Задача:
Улучшить производительнсть данного запроса.

### Решение:
Вызываем ```EXPLAIN``` на исходном запросе и получаем следующие данные: 
```
Aggregate  (cost=16858.23..16858.24 rows=1 width=32)
->  Nested Loop  (cost=2000.43..16857.82 rows=54 width=10)
->  Gather  (cost=1000.43..11440.78 rows=18 width=14)
Workers Planned: 2
->  Nested Loop  (cost=0.43..10438.98 rows=8 width=14)
->  Parallel Seq Scan on cart_items  (cost=0.00..10420.50 rows=8 width=12)
Filter: (user_id = 2)
->  Memoize  (cost=0.43..8.45 rows=1 width=10)
Cache Key: cart_items.product_id
->  Index Scan using products_pkey on products  (cost=0.42..8.44 rows=1 width=10)
Index Cond: (id = cart_items.product_id)
->  Materialize  (cost=1000.00..5416.37 rows=3 width=4)
->  Gather  (cost=1000.00..5416.36 rows=3 width=4)
Workers Planned: 1
->  Parallel Seq Scan on orders  (cost=0.00..4416.06 rows=2 width=4)
Filter: ((user_id = 2) AND ((status)::text = 'active'::text))
```

Из этих даннызх мы видим: 
1. Запрос действительно выполняется медленно:  ```cost=16858.23..16858.24```
Агрегация выполняется после всех соединений и фильтраций.
Это означает, что если мы оптимизируем операции по поиску, то оптимизируется процесс сбора данных.

2. Данные в таблицах ```cart_items``` и ```orders``` обрабатываются последовательно.
Мы видим, что есть индекс на ```cart_items.product_id```, но у нас нет индексов на
```orders.user_id``` и ```orders.status```, которые участвуют в поиске.
Возможно, при установке индексов мы сможем добиться оптимизации запроса

Для полноты картины также вызовем ```EXPLAIN ANALYSE```:
```
Aggregate  (cost=16858.23..16858.24 rows=1 width=32) (actual time=38.477..40.762 rows=1 loops=1)
->  Nested Loop  (cost=2000.43..16857.82 rows=54 width=10) (actual time=10.573..40.743 rows=18 loops=1)
->  Gather  (cost=1000.43..11440.78 rows=18 width=14) (actual time=0.334..17.100 rows=18 loops=1)
Workers Planned: 2
Workers Launched: 2
->  Nested Loop  (cost=0.43..10438.98 rows=8 width=14) (actual time=2.115..14.958 rows=6 loops=3)
->  Parallel Seq Scan on cart_items  (cost=0.00..10420.50 rows=8 width=12) (actual time=2.096..14.916 rows=6 loops=3)
Filter: (user_id = 2)
Rows Removed by Filter: 299994
->  Memoize  (cost=0.43..8.45 rows=1 width=10) (actual time=0.006..0.006 rows=1 loops=18)
Cache Key: cart_items.product_id
Hits: 8  Misses: 3  Evictions: 0  Overflows: 0  Memory Usage: 1kB
Worker 0:  Hits: 2  Misses: 3  Evictions: 0  Overflows: 0  Memory Usage: 1kB
Worker 1:  Hits: 0  Misses: 2  Evictions: 0  Overflows: 0  Memory Usage: 1kB
->  Index Scan using products_pkey on products  (cost=0.42..8.44 rows=1 width=10) (actual time=0.009..0.009 rows=1 loops=8)
Index Cond: (id = cart_items.product_id)
->  Materialize  (cost=1000.00..5416.37 rows=3 width=4) (actual time=0.569..1.313 rows=1 loops=18)
->  Gather  (cost=1000.00..5416.36 rows=3 width=4) (actual time=10.231..23.624 rows=1 loops=1)
Workers Planned: 1
Workers Launched: 1
->  Parallel Seq Scan on orders  (cost=0.00..4416.06 rows=2 width=4) (actual time=5.064..7.088 rows=1 loops=2)
Filter: ((user_id = 2) AND ((status)::text = 'active'::text))
Rows Removed by Filter: 150000
Planning Time: 0.693 ms
Execution Time: 40.804 ms
```

Время выполнения 40.804 ms.

Пробуем создать индексы. 
Сначала попробуем вызвать ```EXPLAIN```:
```
Aggregate  (cost=112.13..112.14 rows=1 width=32)
->  Nested Loop  (cost=5.42..111.73 rows=54 width=10)
->  Nested Loop  (cost=5.00..106.56 rows=18 width=14)
->  Bitmap Heap Scan on cart_items  (cost=4.56..73.76 rows=18 width=12)
Recheck Cond: (user_id = 2)
->  Bitmap Index Scan on idx_cart_items_user_id  (cost=0.00..4.56 rows=18 width=0)
Index Cond: (user_id = 2)
->  Memoize  (cost=0.43..8.45 rows=1 width=10)
Cache Key: cart_items.product_id
->  Index Scan using products_pkey on products  (cost=0.42..8.44 rows=1 width=10)
Index Cond: (id = cart_items.product_id)
->  Materialize  (cost=0.42..4.50 rows=3 width=4)
->  Index Only Scan using idx_orders_user_status on orders  (cost=0.42..4.48 rows=3 width=4)
Index Cond: ((user_id = 2) AND (status = 'active'::text))
```
Мы видим, что показатель ```cost``` в агрегации данных снизился практически в 150 раз.

Попробуем запустить ```EXPLAIN ANALYSE``` и посмотреть на время выполнения запроса:

```
Aggregate  (cost=112.13..112.14 rows=1 width=32) (actual time=0.065..0.066 rows=1 loops=1)
->  Nested Loop  (cost=5.42..111.73 rows=54 width=10) (actual time=0.029..0.058 rows=18 loops=1)
->  Nested Loop  (cost=5.00..106.56 rows=18 width=14) (actual time=0.020..0.045 rows=18 loops=1)
->  Bitmap Heap Scan on cart_items  (cost=4.56..73.76 rows=18 width=12) (actual time=0.011..0.024 rows=18 loops=1)
Recheck Cond: (user_id = 2)
Heap Blocks: exact=18
->  Bitmap Index Scan on idx_cart_items_user_id  (cost=0.00..4.56 rows=18 width=0) (actual time=0.008..0.008 rows=18 loops=1)
Index Cond: (user_id = 2)
->  Memoize  (cost=0.43..8.45 rows=1 width=10) (actual time=0.001..0.001 rows=1 loops=18)
Cache Key: cart_items.product_id
Hits: 15  Misses: 3  Evictions: 0  Overflows: 0  Memory Usage: 1kB
->  Index Scan using products_pkey on products  (cost=0.42..8.44 rows=1 width=10) (actual time=0.004..0.004 rows=1 loops=3)
Index Cond: (id = cart_items.product_id)
->  Materialize  (cost=0.42..4.50 rows=3 width=4) (actual time=0.001..0.001 rows=1 loops=18)
->  Index Only Scan using idx_orders_user_status on orders  (cost=0.42..4.48 rows=3 width=4) (actual time=0.008..0.008 rows=1 loops=1)
Index Cond: ((user_id = 2) AND (status = 'active'::text))
Heap Fetches: 0
Planning Time: 0.153 ms
Execution Time: 0.107 ms
```

Получаем время выполнения 0.107 ms против 40.804 ms до введения индексов.
Таким образом, оптимизация времени выполнения запроса более чем в 380 раз.
Таким образом можно считать задачу выполненной.



