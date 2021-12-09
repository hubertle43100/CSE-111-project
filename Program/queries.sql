-- SQLite
.headers on

--1     Price of food greater than $3.00
SELECT "----- 1 -----";
SELECT name , price
FROM food 
WHERE price > '3.00'
GROUP BY price;

--2     Customer that paid with Debit Cards
SELECT "----- 2 -----";
SELECT c.name, p.type
FROM customer as c, orders as o, payment as p
WHERE o.custkey = c.custkey
    AND o.paymentkey = p.paymentkey
    AND p.paymentkey = 2
    GROUP By c.name;

--3     Insert iced Matcha/Chai Latte
SELECT "----- 3 -----";
.headers on
INSERT INTO drinks VALUES (85,'Small','cold tea', 'iced Matcha Latte',3.95,5);
INSERT INTO drinks VALUES (86,'Medium','cold tea', 'iced Matcha Latte',4.75,5);
INSERT INTO drinks VALUES (87,'Large','cold tea', 'iced Matcha Latte',5.10,5);

INSERT INTO drinks VALUES (88,'Small','cold tea', 'iced Chai Tea Latte',3.45,3);
INSERT INTO drinks VALUES (89,'Medium','cold tea', 'iced Chai Tea Latte',4.25,3);
INSERT INTO drinks VALUES (90,'Large','cold tea', 'iced Chai Tea Latte',4.65,3);

INSERT INTO customer VALUES(26,'Oscar','37.33427722910417, -120.46021752245883',26,26);
INSERT INTO customer VALUES(27,'Brandon','37.32455972074082, -120.48573345160051',27,27);
INSERT INTO customer VALUES(28,'Aramy','37.290378283589895, -120.45417427601869',28,28);
;
.headers off

--4     List all of cold tea
SELECT "----- 4 -----";
SELECT drinkkey, name
FROM drinks
WHERE type = 'cold tea';

--5     Customer that have the ordered same exact location as cafe store
SELECT "----- 5 -----";
SELECT cu.name, ca.name
FROM cafe as ca, customer as cu
WHERE ca.location = cu.location;

--6     Delete Summer drinks && customer
SELECT "----- 6 -----";
DELETE FROM drinks WHERE menukey = 5;
DELETE FROM customer WHERE custkey >= 26;

--7     Drinks where the large && delete winter drinks
SELECT "----- 7 -----";
SELECT name, price
FROM drinks
WHERE size = 'Large'
    AND price BETWEEN 4.50 AND 4.75;
DELETE FROM drinks WHERE menukey = 3;

--8     Customers who ordered at 1st Street
SELECT "----- 8 -----";
SELECT cu.name, ca.name
FROM customer as cu, cafe as ca, orders as o
WHERE cu.orderkey = o.orderkey
    AND o.cafekey = ca.cafekey
    AND ca.name = '1st Street';

--9     How many items orderd in September and October
SELECT "----- 9 -----";
SELECT SUM(o.itemcount)
FROM ordersDetail as o
WHERE date BETWEEN '2021-09-01' AND '2021-10-31';

--10     Customer that cannot afford their totalprice
SELECT "----- 10 -----";
SELECT p.acctbal, o.totalprice, c.name
FROM profile as p, ordersDetail as o, customer as c
WHERE p.profilekey = o.detailkey
    AND p.profilekey = c.profilekey
    AND p.acctbal < o.totalprice
    GROUP BY p.acctbal;

--11    Customer that ordered a small that cost more than $4.00
SELECT "----- 11 -----";
SELECT c.name, d.name, d.price
FROM customer as c, orders as o, drinks as d
WHERE c.orderkey = o.orderkey
    AND o.drinkkey = d.drinkkey
    AND d.size = 'Small'
    AND d.price > '4.00';

--12    Customer with more than $20.00 in their bank account from greatest to least
SELECT "----- 12 -----";
SELECT c.name, p.acctbal
FROM customer as c, profile as p
WHERE c.profilekey = p.profilekey
    AND p.acctbal > '20.00'
    GROUP BY p.acctbal
    ORDER BY p.acctbal DESC;

--13    Customer who orders food that is less than $2.00
SELECT "----- 13 -----";
SELECT c.name, f.name, f.price
FROM customer as c, orders as o, food as f
WHERE c.orderkey = o.orderkey
    AND o.foodkey = f.foodkey
    AND f.price < '2.00';

--14    Average waiting time for 2 drinks
SELECT "----- 14 -----";
SELECT AVG(waittime)
FROM ordersDetail as o
WHERE itemcount = 2;

--15    Customer who ordered food iteam with at least 1 drink
SELECT "----- 15 -----";
SELECT c.name
FROM customer as c, orders as o, food as f
WHERE c.custkey = o.custkey
    AND o.foodkey = f.foodkey
    AND f.foodkey > 0
INTERSECT
SELECT c.name
FROM customer as c, orders as o, drinks as d
WHERE c.custkey = o.custkey
    AND o.drinkkey = d.drinkkey
    AND d.drinkkey >= 3;

--16    Customer who ordered 3 plus items paying with a debit card
SELECT "----- 16 -----";
SELECT c.name
FROM customer as c, orders as o, ordersDetail as od
WHERE c.custkey = o.custkey
    AND c.custkey = o.custkey
    AND o.orderkey = od.detailkey
    AND itemcount >= 3
INTERSECT
SELECT c.name
FROM customer as c, orders as o, payment as p
WHERE c.custkey = o.custkey
    AND o.paymentkey = p.paymentkey
    AND p.type = 'Debit Card';

--17    total amount earned in July and August combined
SELECT "----- 17 -----";
SELECT SUM(July + August)
FROM (SELECT SUM(totalprice) as July
        FROM ordersDetail as od
        WHERE od.date BETWEEN '2021-07-01' AND '2021-07-31'),
        (SELECT SUM(totalprice) as August
        FROM ordersDetail as od
        WHERE od.date BETWEEN '2021-08-01' AND '2021-08-31');

--18    Orders have a waittime less than 3
SELECT "----- 18 -----";
SELECT DISTINCT orderkey
FROM orders as o, ordersDetail as od
WHERE o.orderkey = od.detailkey
    AND od.waittime < 3
    GROUP BY o.orderkey;

--19    Drinks orded on August 9th, 2021
SELECT "----- 19 -----";
SELECT DISTINCT d.size, d.type, d.name
FROM drinks as d, ordersDetail as od, orders as o
WHERE o.drinkkey = d.drinkkey
    AND o.drinkkey = od.detailkey
    AND  od.date = '2021-08-09';

--20    ADD $10 to Drinks that have been ordered 
SELECT "----- 20 -----";
UPDATE drinks as d
SET price = 10 + price
WHERE d.price IN (
    SELECT d.price
    FROM orders as o, drinks as d
    WHERE o.drinkkey = d.drinkkey
    );

SELECT d.size,d.name, d.price
FROM drinks as d
WHERE d.price > 10;

--21
SELECT "----- 21 -----";

SELECT orderkey
FROM orders as o, payment as p
WHERE o.paymentkey = p.paymentkey
    AND p.type = 'Debit Card'

UNION

SELECT orderkey
FROM orders as o, payment as p
WHERE o.paymentkey = p.paymentkey
    AND p.type = 'Account'
