.mode "csv"
.separator ","
.headers off

.import '/Users/hubertle/Desktop/CSE 111 project/CSE 111 Project Phase 2 - Customer.csv' customer
DELETE FROM customer WHERE name = 'name';
.import '/Users/hubertle/Desktop/CSE 111 project/CSE 111 Project Phase 2 - Profile.csv' profile
DELETE FROM profile WHERE profilekey = 'profilekey';
.import '/Users/hubertle/Desktop/CSE 111 project/CSE 111 Project Phase 2 - Menu.csv' menu
DELETE FROM menu WHERE name = 'name';
.import '/Users/hubertle/Desktop/CSE 111 project/CSE 111 Project Phase 2 - Drinks.csv' drinks
DELETE FROM drinks WHERE name = 'name';
.import '/Users/hubertle/Desktop/CSE 111 project/CSE 111 Project Phase 2 - Food.csv' food
DELETE FROM food WHERE type = 'type';
.import '/Users/hubertle/Desktop/CSE 111 project/CSE 111 Project Phase 2 - Order.csv' orders
DELETE FROM orders WHERE custkey = 'custkey';
.import '/Users/hubertle/Desktop/CSE 111 project/CSE 111 Project Phase 2 - Order Detail.csv' ordersDetail
DELETE FROM ordersDetail WHERE date = 'date';
.import '/Users/hubertle/Desktop/CSE 111 project/CSE 111 Project Phase 2 - Payment.csv' payment
DELETE FROM payment WHERE type = 'type';
.import '/Users/hubertle/Desktop/CSE 111 project/CSE 111 Project Phase 2 - Cafe.csv' cafe
DELETE FROM cafe WHERE name = 'name';

.save customer.sqlite
.save profile.sqlite
.save menu.sqlite
.save drinks.sqlite
.save food.sqlite
.save orders.sqlite
.save orderDetail.sqlite
.save payment.sqlite
.save cafe.sqlite
