CREATE TABLE customer (
    custkey          decimal(9,0) not null,
    name             varchar(25) not null,
    location         varchar(40) not null,
    orderkey         decimal(9,0) not null,
    profilekey       decimal(9,0) not null
);
CREATE TABLE profile (
    profilekey       decimal(9,0) not null,
    custkey          decimal(9,0) not null,
    acctbal          decimal(12,0) not null
);
CREATE TABLE menu (
    menukey          decimal(9,0) not null,
    name             varchar(25) not null
);
CREATE TABLE drinks (
    drinkkey         decimal(9,0) not null,
    size             varchar(25) not null,
    type             varchar(25) not null,
    name             varchar(25) not null,
    price            decimal(9,2) not null,
    menukey          decimal(9,0) not null
);
CREATE TABLE food (
    foodkey          decimal(9,0) not null,
    type             varchar(25) not null,
    name             varchar(25) not null,
    price            decimal(9,2) not null
);
CREATE TABLE orders (
    orderkey         decimal(9,0) not null,
    cafekey          decimal(9,0) not null,
    custkey          decimal(9,0) not null,
    drinkkey         decimal(9,0) not null,
    foodkey          decimal(9,0) not null,
    paymentkey       decimal(9,0) not null
);

CREATE TABLE ordersDetail (
    detailkey        decimal(9,0) not null,
    date             date not null,
    itemcount        decimal(9,0) not null,
    waittime         decimal(9,2) not null,
    totalprice       decimal(9,2) not null
);
CREATE TABLE payment (
    paymentkey       decimal(9,0) not null,
    type             varchar(25) not null
);
CREATE TABLE cafe (
    cafekey          decimal(9,0) not null,
    driveopen        time not null,
    driveclose       time not null,
    lobbyopen        time not null,
    lobbyclose       time not null,
    name             varchar(25) not null,
    location         varchar(40) not null
);
.headers off