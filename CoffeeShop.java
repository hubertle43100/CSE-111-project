// STEP: Import required packages
import java.sql.*;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.File;

public class CoffeeShop {
    private Connection c = null;
    private String dbName;
    private boolean isConnected = false;

    private void openConnection(String _dbName) {
        dbName = _dbName;

        if (false == isConnected) {
            System.out.println();
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            System.out.println("Open database: " + _dbName);

            try {
                String connStr = new String("jdbc:sqlite:");
                connStr = connStr + _dbName;

                // STEP: Register JDBC driver
                Class.forName("org.sqlite.JDBC");

                // STEP: Open a connection
                c = DriverManager.getConnection(connStr);

                // STEP: Diable auto transactions
                c.setAutoCommit(false);

                isConnected = true;
                System.out.println("success");
            } catch (Exception e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
                System.exit(0);
            }

            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            System.out.println();
        }
    }

    private void closeConnection() {
        if (true == isConnected) {
            System.out.println();
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            System.out.println("Close database: " + dbName);

            try {
                // STEP: Close connection
                c.close();

                isConnected = false;
                dbName = "";
                System.out.println("success");
            } catch (Exception e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
                System.exit(0);
            }

            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            System.out.println();
        }
    }

    private void dropTables() {
        System.out.println();
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("Drop tables");

        try{
            Statement stmt = c.createStatement();
            String  sql = "DROP TABLE IF EXISTS cafe";
                    stmt.execute(sql);

                    sql = "DROP TABLE IF EXISTS customer";
                    stmt.execute(sql);

                    sql = "DROP TABLE IF EXISTS drinks";
                    stmt.execute(sql);

                    sql = "DROP TABLE IF EXISTS food";
                    stmt.execute(sql);

                    sql = "DROP TABLE IF EXISTS menu_drinks";
                    stmt.execute(sql);

                    sql = "DROP TABLE IF EXISTS menu_food";
                    stmt.execute(sql);

                    sql = "DROP TABLE IF EXISTS menu";
                    stmt.execute(sql);

                    sql = "DROP TABLE IF EXISTS orderdetail";
                    stmt.execute(sql);

                    sql = "DROP TABLE IF EXISTS orders";
                    stmt.execute(sql);

                    sql = "DROP TABLE IF EXISTS payment";
                    stmt.execute(sql);

                    sql = "DROP TABLE IF EXISTS profile";
                    stmt.execute(sql);

            c.commit();

            stmt.close();
            System.out.println("success");
        }
        catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            try {
                c.rollback();
            } 
            catch (Exception e1) {
                System.err.println(e1.getClass().getName() + ": " + e1.getMessage());
            }
        }
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println();
    }

    private void createTables() {
        System.out.println();
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("Create tables");

        try{
            Statement stmt = c.createStatement();
            String sql =   
                    "CREATE TABLE cafe (" + 
                        "cafekey DECIMAL(10, 0), " +
                        "driveopen TIME, " +  
                        "driveclose TIME, " +
                        "lobbyopen TIME, " +
                        "lobbyclose TIME, " +
                        "cf_name VARCHAR(50) )";
            stmt.execute(sql);

            sql =   "CREATE TABLE customer (" + 
                        "custkey DECIMAL(10, 0), " + 
                        "c_name  VARCHAR(50), " +
                        "orderkey DECIMAL(10, 0), " + 
                        "profilekey DECIMAL(10, 0) )";
            stmt.execute(sql);

            sql =   "CREATE TABLE drinks (" + 
                        "drinkkey DECIMAL(10, 0), " +
                        "size VARCHAR(10), " + 
                        "d_type  VARCHAR(25), " +
                        "d_name VARCHAR(50), " +
                        "price DECIMAL(10, 2) )";
            stmt.execute(sql);

            sql =   "CREATE TABLE food (" + 
                        "foodkey DECIMAL(10, 0), " +
                        "f_type VARCHAR(50), " + 
                        "f_name VARCHAR(50), " + 
                        "price DECIMAL(10,2) )";
            stmt.execute(sql);  

            sql =   "CREATE TABLE menu_drinks (" +
                        "menukey DECIMAL(10, 0), " +
                        "drinkkey DECIMAL(10, 0) )";
            stmt.execute(sql);

            sql =   "CREATE TABLE menu_food (" +
                        "menukey DECIMAL(10, 0), " +
                        "foodkey DECIMAL(10, 0) )";
            stmt.execute(sql);

            sql =   "CREATE TABLE menu (" + 
                        "menukey DECIMAL(10, 0), " +
                        "m_name VARCHAR(25) )";
            stmt.execute(sql);

            sql =   "CREATE TABLE orderdetail (" + 
                        "detailkey DECIMAL(10, 0), " + 
                        "od_date DATE, " +
                        "itemcount DECIMAL(10, 0), " +
                        "waittime DECIMAL (5, 2), " +
                        "totalprice DECIMAL (10, 2) )";
            stmt.execute(sql);

            sql =   "CREATE TABLE orders (" + 
                        "orderkey DECIMAL(10, 0), " + 
                        "cafekey DECIMAL(10, 0), " + 
                        "custkey DECIMAL(10, 0), " + 
                        "drinkkey DECIMAL(10, 0), " + 
                        "foodkey DECIMAL(10, 0), " + 
                        "paymentkey DECIMAL(10, 0) )";
            stmt.execute(sql);

            sql =   "CREATE TABLE payment (" + 
                        "paymentkey DECIMAL(10, 0), " +
                        "p_type VARCHAR(25) )";
            stmt.execute(sql);

            sql =   "CREATE TABLE profile (" + 
                        "profilekey DECIMAL(10, 0), " + 
                        "custkey DECIMAL(10, 0), " + 
                        "acctbal DECIMAL(7, 2) )";
            stmt.execute(sql);

        c.commit();

        stmt.close();
        System.out.println("success");
        }
        catch (Exception e){
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            try{
                c.rollback();
            }
            catch (Exception el){
                System.err.println(el.getClass().getName() + ": " + el.getMessage());
            }
        }
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println();
    }

    // Table Populate and Insert Functions 
    private void populateCafe() {
        //System.out.println();
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("Populate cafe");

        insertCafe(1, 5, 23, 7, 21, "1st Street");
        insertCafe(1, 5, 23, 7, 21, "Trish & Holland");
        insertCafe(1, 5, 23, 7, 21, "McDougal");
        insertCafe(1, 5, 23, 7, 21, "Priscilla");
        insertCafe(1, 5, 23, 7, 21, "Airline Hwy");

        System.out.println("success");
        //System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println();
    }
    private void insertCafe(int cafekey, int driveopen, int driveclose,
        int lobbyopen, int lobbyclose, String cf_name){
        //System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        //System.out.println("Insert Cafe");
        
        try {
            String sql = "INSERT INTO cafe(cafekey, driveopen, driveclose, lobbyopen, lobbyclose, cf_name) " +
                "VALUES(?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = c.prepareStatement(sql);

            stmt.setInt(1, cafekey);
            stmt.setInt(2, driveopen);
            stmt.setInt(3, driveclose);
            stmt.setInt(4, lobbyopen);
            stmt.setInt(5, lobbyclose);
            stmt.setString(6, cf_name);

            // STEP: Execute batch
            stmt.executeUpdate();

            // STEP: Commit transaction
            c.commit();

            stmt.close();
            //System.out.println("success");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            try {
                c.rollback();
            } catch (Exception e1) {
                System.err.println(e1.getClass().getName() + ": " + e1.getMessage());
            }
        }
        //System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        //System.out.println();
    }

    private void populateCustomer() {
        //System.out.println();
        //System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("Populate customer");

        insertCustomer(1, "Edward", 1, 1);
        insertCustomer(2, "Jonathan", 2, 2);
        insertCustomer(3, "Katherine", 3, 3);
        insertCustomer(4, "Jim", 4, 4);
        insertCustomer(5, "Anna", 5, 5);
        insertCustomer(6, "Rebekah", 6, 6);
        insertCustomer(7, "Tim", 7, 7);
        insertCustomer(8, "Michelle", 8, 8);
        insertCustomer(9, "Robert", 9, 9);
        insertCustomer(10, "Jonah", 10, 10);
        insertCustomer(11, "Rylan", 11, 11);
        insertCustomer(12, "Kate", 12, 12);
        insertCustomer(13, "Klaus", 13, 13);
        insertCustomer(14, "Bonnie", 14, 14);
        insertCustomer(15, "Michael", 15, 15);
        insertCustomer(16, "Hope", 16, 16);
        insertCustomer(17, "Freya", 17, 17);
        insertCustomer(18, "Hayley", 18, 18);
        insertCustomer(19, "Elijah", 19, 19);
        insertCustomer(20, "Alaric", 20, 20);
        insertCustomer(21, "Stefan", 21, 21);
        insertCustomer(22, "Caroline", 22, 22);
        insertCustomer(23, "Davina", 23, 23);
        insertCustomer(24, "Sofia", 24, 24);
        insertCustomer(25, "Josh", 25, 25);

        System.out.println("success");
        //System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println();
    }
    private void insertCustomer(int custkey, String c_name, int orderkey,
        int profilekey){
        //System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        //System.out.println("Insert Cafe");
        
        try {
            String sql = "INSERT INTO customer(custkey, c_name, orderkey, profilekey) " +
                "VALUES(?, ?, ?, ?)";
            PreparedStatement stmt = c.prepareStatement(sql);

            stmt.setInt(1, custkey);
            stmt.setString(2, c_name);
            stmt.setInt(3, orderkey);
            stmt.setInt(4, profilekey);

            // STEP: Execute batch
            stmt.executeUpdate();

            // STEP: Commit transaction
            c.commit();

            stmt.close();
            //System.out.println("success");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            try {
                c.rollback();
            } catch (Exception e1) {
                System.err.println(e1.getClass().getName() + ": " + e1.getMessage());
            }
        }
        //System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        //System.out.println();
    }

    private void populateDrinks() {
        //System.out.println();
        //System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("Populate customer");

        insertDrinks(0, "none", "none", "none", 0.00);

        // Hot Coffee
        insertDrinks(1, "Small", "hot coffee", "Americano", 2.95);
        insertDrinks(2, "Medium", "hot coffee", "Americano", 3.45);
        insertDrinks(3, "Large", "hot coffee", "Americano", 3.65);

        insertDrinks(4, "Small", "hot coffee", "Cappuccino", 3.45);
        insertDrinks(5, "Medium", "hot coffee", "Cappuccino", 4.25);
        insertDrinks(6, "Large", "hot coffee", "Cappuccino", 4.75);

        insertDrinks(7, "Small", "hot coffee", "Dirty Chai", 3.95);
        insertDrinks(8, "Medium", "hot coffee", "Dirty Chai", 4.65);
        insertDrinks(9, "Large", "hot coffee", "Dirty Chai", 4.95);

        insertDrinks(10, "Small", "hot coffee", "Double Espresso", 2.65);

        insertDrinks(11, "Small", "hot coffee", "Latte", 3.45);
        insertDrinks(12, "Medium", "hot coffee", "Latte", 4.25);
        insertDrinks(13, "Large", "hot coffee", "Latte", 4.75);

        insertDrinks(14, "Small", "hot coffee", "Macchiatto", 4.45);
        insertDrinks(15, "Medium", "hot coffee", "Macchiatto", 4.95);
        insertDrinks(16, "Large", "hot coffee", "Macchiatto", 5.25);

        insertDrinks(17, "Small", "hot coffee", "Mocha", 4.15);
        insertDrinks(18, "Medium", "hot coffee", "Mocha", 4.65);
        insertDrinks(19, "Large", "hot coffee", "Mocha", 4.95);

        insertDrinks(20, "Small", "hot coffee", "White Mocha", 4.65);
        insertDrinks(21, "Medium", "hot coffee", "White Mocha", 4.95);
        insertDrinks(22, "Large", "hot coffee", "White Mocha", 5.45);

        // Hot Drinks
        insertDrinks(23, "Small", "hot drink", "Hot Chocolate", 2.95);
        insertDrinks(24, "Medium", "hot drink", "Hot Chocolate", 3.45);
        insertDrinks(25, "Large", "hot drink", "Hot Chocolate", 3.65);

        insertDrinks(26, "Small", "hot drink", "Hot White Chocolate", 3.45);
        insertDrinks(27, "Medium", "hot drink", "Hot White Chocolate", 3.95);
        insertDrinks(28, "Large", "hot drink", "Hot White Chocolate", 4.15);

        // Hot Tea
        insertDrinks(29, "Small", "hot tea", "Chai", 3.95);
        insertDrinks(30, "Medium", "hot tea", "Chai", 4.65);
        insertDrinks(31, "Large", "hot tea", "Chai", 4.95);

        insertDrinks(32, "Small", "hot tea", "Chamomile", 2.45);
        insertDrinks(33, "Medium", "hot tea", "Chamomile", 2.75);
        insertDrinks(34, "Large", "hot tea", "Chamomile", 2.95);

        insertDrinks(35, "Small", "hot tea", "English Breakfast", 2.45);
        insertDrinks(36, "Medium", "hot tea", "English Breakfast", 2.75);
        insertDrinks(37, "Large", "hot tea", "English Breakfast", 2.95);

        insertDrinks(38, "Small", "hot tea", "Matcha", 3.95);
        insertDrinks(39, "Medium", "hot tea", "Matcha", 4.65);
        insertDrinks(40, "Large", "hot tea", "Matcha", 4.95);

        insertDrinks(41, "Small", "hot tea", "Peppermint", 2.45);
        insertDrinks(42, "Medium", "hot tea", "Peppermint", 2.75);
        insertDrinks(43, "Large", "hot tea", "Peppermint", 2.95);

        // Iced Coffee
        insertDrinks(44, "Small", "iced coffee", "Americano", 3.45);
        insertDrinks(45, "Medium", "iced coffee", "Americano", 3.95);
        insertDrinks(46, "Large", "iced coffee", "Americano", 4.45);

        insertDrinks(47, "Small", "iced coffee", "Dirty Chai", 4.65);
        insertDrinks(48, "Medium", "iced coffee", "Dirty Chai", 5.45);
        insertDrinks(49, "Large", "iced coffee", "Dirty Chai", 5.95);

        insertDrinks(50, "Small", "iced coffee", "Latte", 3.45);
        insertDrinks(51, "Medium", "iced coffee", "Latte", 4.25);
        insertDrinks(52, "Large", "iced coffee", "Latte", 4.95);

        insertDrinks(53, "Small", "iced coffee", "Macchiatto", 4.45);
        insertDrinks(54, "Medium", "iced coffee", "Macchiatto", 4.95);
        insertDrinks(55, "Large", "iced coffee", "Macchiatto", 5.45);

        // Iced Tea
        insertDrinks(56, "Small", "iced tea", "Black Tea", 2.45);
        insertDrinks(57, "Medium", "iced tea", "Black Tea", 2.95);
        insertDrinks(58, "Large", "iced tea", "Black Tea", 3.25);

        insertDrinks(59, "Small", "iced tea", "Green Tea", 2.45);
        insertDrinks(60, "Medium", "iced tea", "Green Tea", 2.95);
        insertDrinks(61, "Large", "iced tea", "Green Tea", 3.25);

        insertDrinks(62, "Small", "iced tea", "Lemonade", 2.95);
        insertDrinks(63, "Medium", "iced tea", "Lemonade", 3.45);
        insertDrinks(64, "Large", "iced tea", "Lemonade", 3.95);

        insertDrinks(65, "Small", "iced tea", "Chai", 4.25);
        insertDrinks(66, "Medium", "iced tea", "Chai", 4.75);
        insertDrinks(67, "Large", "iced tea", "Chai", 4.95);

        insertDrinks(68, "Small", "iced tea", "Matcha", 4.25);
        insertDrinks(69, "Medium", "iced tea", "Matcha", 4.75);
        insertDrinks(70, "Large", "iced tea", "Matcha", 4.95);

        // Blended
        insertDrinks(71, "Small", "blended", "Matcha", 4.45);
        insertDrinks(72, "Medium", "blended", "Matcha", 4.95);
        insertDrinks(73, "Large", "blended", "Matcha", 5.45);

        insertDrinks(74, "Small", "blended", "Mocha", 4.45);
        insertDrinks(75, "Medium", "blended", "Mocha", 4.95);
        insertDrinks(76, "Large", "blended", "Mocha", 5.45);

        insertDrinks(78, "Small", "blended", "Vanilla Creme", 4.25);
        insertDrinks(79, "Medium", "blended", "Vanilla Creme", 4.75);
        insertDrinks(80, "Large", "blended", "Vanilla Creme", 5.25);

        // Chilled
        insertDrinks(81, "Large", "chilled", "Cup of Ice", 0.25);
        insertDrinks(82, "n/a", "chilled", "Fruit Juice", 2.99);
        insertDrinks(83, "Large", "chilled", "Ice Water", 0.25);
        insertDrinks(84, "n/a", "chilled", "Water Bottle", 2.59);

        System.out.println("success");
        //System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println();
    }
    private void insertDrinks(int drinkkey, String size, String d_type,
        String d_name, double price){
        //System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        //System.out.println("Insert Cafe");
        
        try {
            String sql = "INSERT INTO drinks(drinkkey, size, d_type, d_name, price) " +
                "VALUES(?, ?, ?, ?, ?)";
            PreparedStatement stmt = c.prepareStatement(sql);

            stmt.setInt(1, drinkkey);
            stmt.setString(2, size);
            stmt.setString(3, d_type);
            stmt.setString(4, d_name);
            stmt.setDouble(5, price);

            // STEP: Execute batch
            stmt.executeUpdate();

            // STEP: Commit transaction
            c.commit();

            stmt.close();
            //System.out.println("success");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            try {
                c.rollback();
            } catch (Exception e1) {
                System.err.println(e1.getClass().getName() + ": " + e1.getMessage());
            }
        }
        //System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        //System.out.println();
    }

    private void populateFood() {
        //System.out.println();
        //System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("Populate Food");

        insertFood(0, "none", "none", 0.00);
        insertFood(1, "bakery", "Bagel", 1.95);
        insertFood(2, "bakery", "Muffin", 2.65);
        insertFood(3, "bakery", "Danish", 2.95);
        insertFood(4, "bakery", "Brownie", 2.95);
        insertFood(5, "bakery", "Cookie", 2.45);
        insertFood(6, "sandwhich", "Double Smoked Bacon", 5.25);
        insertFood(7, "sandwhich", "Turkey Bacon", 4.15);
        insertFood(8, "sandwhich", "Chicken Wrap", 4.68);
        insertFood(9, "sandwhich", "Ham & Swiss Panini", 6.75);
        insertFood(10, "snacks", "Chips", 1.79);
        insertFood(11, "snacks", "Yogurt", 2.45);
        insertFood(12, "snacks", "protein Box", 6.45);

        System.out.println("success");
        //System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println();
    }
    private void insertFood(int foodkey, String f_type, String f_name, double price){
        //System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        //System.out.println("Insert Cafe");
        
        try {
            String sql = "INSERT INTO food(foodkey, f_type, f_name, price) " +
                "VALUES(?, ?, ?, ?)";
            PreparedStatement stmt = c.prepareStatement(sql);

            stmt.setInt(1, foodkey);
            stmt.setString(2, f_type);
            stmt.setString(3, f_name);
            stmt.setDouble(4, price);

            // STEP: Execute batch
            stmt.executeUpdate();

            // STEP: Commit transaction
            c.commit();

            stmt.close();
            //System.out.println("success");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            try {
                c.rollback();
            } catch (Exception e1) {
                System.err.println(e1.getClass().getName() + ": " + e1.getMessage());
            }
        }
        //System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        //System.out.println();
    }

    private void populateMenuDrinks() {
        //System.out.println();
        //System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("Populate Menu Drinks");

        insertMenuDrinks(1, 0);
        insertMenuDrinks(1, 1);
        insertMenuDrinks(1, 2);
        insertMenuDrinks(1, 3);
        insertMenuDrinks(1, 4);
        insertMenuDrinks(1, 5);
        insertMenuDrinks(1, 6);
        insertMenuDrinks(1, 7);
        insertMenuDrinks(1, 8);
        insertMenuDrinks(1, 9);
        insertMenuDrinks(1, 10);
        insertMenuDrinks(1, 11);
        insertMenuDrinks(1, 12);
        insertMenuDrinks(1, 13);
        insertMenuDrinks(1, 14);
        insertMenuDrinks(1, 15);
        insertMenuDrinks(1, 16);
        insertMenuDrinks(1, 17);
        insertMenuDrinks(1, 18);
        insertMenuDrinks(1, 19);
        insertMenuDrinks(1, 20);
        insertMenuDrinks(1, 21);
        insertMenuDrinks(1, 22);

        insertMenuDrinks(1, 23);
        insertMenuDrinks(2, 23);

        insertMenuDrinks(1, 24);
        insertMenuDrinks(2, 24);

        insertMenuDrinks(1, 25);
        insertMenuDrinks(2, 25);

        insertMenuDrinks(1, 26);
        insertMenuDrinks(2, 26);

        insertMenuDrinks(1, 27);
        insertMenuDrinks(2, 27);

        insertMenuDrinks(1, 28);
        insertMenuDrinks(2, 28);

        insertMenuDrinks(1, 29);
        insertMenuDrinks(1, 30);
        insertMenuDrinks(1, 31);

        insertMenuDrinks(1, 32);
        insertMenuDrinks(2, 32);

        insertMenuDrinks(1, 33);
        insertMenuDrinks(2, 33);

        insertMenuDrinks(1, 34);
        insertMenuDrinks(2, 34);

        insertMenuDrinks(1, 35);
        insertMenuDrinks(1, 36);
        insertMenuDrinks(1, 37);
        insertMenuDrinks(1, 38);
        insertMenuDrinks(1, 39);
        insertMenuDrinks(1, 40);

        insertMenuDrinks(1, 41);
        insertMenuDrinks(2, 41);

        insertMenuDrinks(1, 42);
        insertMenuDrinks(2, 42);

        insertMenuDrinks(1, 43);
        insertMenuDrinks(2, 43);

        insertMenuDrinks(1, 44);
        insertMenuDrinks(1, 45);
        insertMenuDrinks(1, 46);
        insertMenuDrinks(1, 47);
        insertMenuDrinks(1, 48);
        insertMenuDrinks(1, 49);
        insertMenuDrinks(1, 50);
        insertMenuDrinks(1, 51);
        insertMenuDrinks(1, 52);
        insertMenuDrinks(1, 53);
        insertMenuDrinks(1, 54);
        insertMenuDrinks(1, 55);
        insertMenuDrinks(1, 56);
        insertMenuDrinks(1, 57);
        insertMenuDrinks(1, 58);
        insertMenuDrinks(1, 59);
        insertMenuDrinks(1, 60);
        insertMenuDrinks(1, 61);

        insertMenuDrinks(1, 62);
        insertMenuDrinks(2, 62);

        insertMenuDrinks(1, 63);
        insertMenuDrinks(2, 63);

        insertMenuDrinks(1, 64);
        insertMenuDrinks(2, 64);

        insertMenuDrinks(1, 65);
        insertMenuDrinks(1, 66);
        insertMenuDrinks(1, 67);
        insertMenuDrinks(1, 68);
        insertMenuDrinks(1, 69);
        insertMenuDrinks(1, 70);
        insertMenuDrinks(1, 71);
        insertMenuDrinks(1, 72);
        insertMenuDrinks(1, 73);
        insertMenuDrinks(1, 74);
        insertMenuDrinks(1, 75);
        insertMenuDrinks(1, 76);

        insertMenuDrinks(1, 77);
        insertMenuDrinks(2, 77);

        insertMenuDrinks(1, 78);
        insertMenuDrinks(2, 78);

        insertMenuDrinks(1, 79);
        insertMenuDrinks(2, 79);

        insertMenuDrinks(1, 80);
        insertMenuDrinks(2, 80);

        insertMenuDrinks(1, 81);
        insertMenuDrinks(2, 81);

        insertMenuDrinks(1, 82);
        insertMenuDrinks(2, 82);

        insertMenuDrinks(1, 83);
        insertMenuDrinks(2, 83);


        System.out.println("success");
        //System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println();
    }
    private void insertMenuDrinks(int menukey, int drinkkey){
        //System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        //System.out.println("Insert Cafe");
        
        try {
            String sql = "INSERT INTO menu_drinks(menukey, drinkkey) " +
                "VALUES(?, ?)";
            PreparedStatement stmt = c.prepareStatement(sql);

            stmt.setInt(1, menukey);
            stmt.setInt(2, drinkkey);

            // STEP: Execute batch
            stmt.executeUpdate();

            // STEP: Commit transaction
            c.commit();

            stmt.close();
            //System.out.println("success");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            try {
                c.rollback();
            } catch (Exception e1) {
                System.err.println(e1.getClass().getName() + ": " + e1.getMessage());
            }
        }
        //System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        //System.out.println();
    }

    private void populateMenuFood() {
        //System.out.println();
        //System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("Populate Menu Food");

        insertMenuFood(1, 0);

        insertMenuFood(1, 1);
        insertMenuFood(3, 1);

        insertMenuFood(1, 2);
        insertMenuFood(3, 2);

        insertMenuFood(1, 3);
        insertMenuFood(3, 3);

        insertMenuFood(1, 4);
        insertMenuFood(3, 4);

        insertMenuFood(1, 5);
        insertMenuFood(3, 5);

        insertMenuFood(1, 6);
        insertMenuFood(1, 7);
        insertMenuFood(1, 8);
        insertMenuFood(1, 9);

        insertMenuFood(1, 10);
        insertMenuFood(3, 10);

        insertMenuFood(1, 11);
        insertMenuFood(3, 11);

        insertMenuFood(1, 12);
        insertMenuFood(3, 12);


        System.out.println("success");
        //System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println();
    }
    private void insertMenuFood(int menukey, int foodkey){
        //System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        //System.out.println("Insert Cafe");
        
        try {
            String sql = "INSERT INTO menu_food(menukey, foodkey) " +
                "VALUES(?, ?)";
            PreparedStatement stmt = c.prepareStatement(sql);

            stmt.setInt(1, menukey);
            stmt.setInt(2, foodkey);

            // STEP: Execute batch
            stmt.executeUpdate();

            // STEP: Commit transaction
            c.commit();

            stmt.close();
            //System.out.println("success");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            try {
                c.rollback();
            } catch (Exception e1) {
                System.err.println(e1.getClass().getName() + ": " + e1.getMessage());
            }
        }
        //System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        //System.out.println();
    }

    private void populateMenu() {
        //System.out.println();
        //System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("Populate Menu");

        insertMenu(1, "General");
        insertMenu(2, "Caffeine-free");
        insertMenu(3, "Meat-free");

        System.out.println("success");
        //System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println();
    }
    private void insertMenu(int menukey, String m_name){
        //System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        //System.out.println("Insert Cafe");
        
        try {
            String sql = "INSERT INTO menu(menukey, m_name) " +
                "VALUES(?, ?)";
            PreparedStatement stmt = c.prepareStatement(sql);

            stmt.setInt(1, menukey);
            stmt.setString(2, m_name);

            // STEP: Execute batch
            stmt.executeUpdate();

            // STEP: Commit transaction
            c.commit();

            stmt.close();
            //System.out.println("success");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            try {
                c.rollback();
            } catch (Exception e1) {
                System.err.println(e1.getClass().getName() + ": " + e1.getMessage());
            }
        }
        //System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        //System.out.println();
    }

    private void populateOrderDetail() {
        //System.out.println();
        //System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("Populate Order Detail");

        insertOrderDetail(1, "2021-07-07", 2, 3.26, 8.40);
        insertOrderDetail(2, "2021-07-15", 4, 6.25, 13.80);
        insertOrderDetail(3, "2021-07-15", 5, 6, 17.00);
        insertOrderDetail(4, "2021-07-18", 2, 5, 3.45);
        insertOrderDetail(5, "2021-07-18", 2, 3, 6.20);
        insertOrderDetail(6, "2021-07-21", 2, 2, 5.40);
        insertOrderDetail(7, "2021-07-26", 2, 3, 5.40);
        insertOrderDetail(8, "2021-07-28", 2, 3.5, 6.40);
        insertOrderDetail(9, "2021-08-03", 2, 2.75, 6.40);
        insertOrderDetail(10, "2021-08-05", 2, 3.25, 6.90);
        insertOrderDetail(11, "2021-08-08", 2, 2.49, 6.90);
        insertOrderDetail(12, "2021-08-09", 2, 2.13, 7.20);
        insertOrderDetail(13, "2021-08-23", 3, 2, 9.65);
        insertOrderDetail(14, "2021-08-25", 2, 2, 10.00);
        insertOrderDetail(15, "2021-08-30", 2, 3.45, 7.60);
        insertOrderDetail(16, "2021-09-09", 2, 2.55, 7.60);
        insertOrderDetail(17, "2021-09-12", 2, 2.14, 6.04);
        insertOrderDetail(18, "2021-09-16", 2, 2.16, 6.04);
        insertOrderDetail(19, "2021-09-18", 4, 6.45, 20.70);
        insertOrderDetail(20, "2021-09-29", 3, 4.25, 13.35);
        insertOrderDetail(21, "2021-10-05", 4, 5.5, 7.85);
        insertOrderDetail(22, "2021-10-19", 2, 3.2, 3.95);
        insertOrderDetail(23, "2021-10-20", 3, 3.45, 4.70);
        insertOrderDetail(24, "2021-10-21", 3, 4.1, 5.70);
        insertOrderDetail(25, "2021-10-26", 4, 5.59, 15.73);


        System.out.println("success");
        //System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println();
    }
    private void insertOrderDetail(int detailkey, String od_date, int itemcount,
        double waittime, double totalprice){
        //System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        //System.out.println("Insert Cafe");
        
        try {
            String sql = "INSERT INTO orderdetail(detailkey, od_date, itemcount, waittime, totalprice) " +
                "VALUES(?, ?, ?, ?, ?)";
            PreparedStatement stmt = c.prepareStatement(sql);

            stmt.setInt(1, detailkey);
            stmt.setString(2, od_date);
            stmt.setInt(3, itemcount);
            stmt.setDouble(4, waittime);
            stmt.setDouble(5, totalprice);

            // STEP: Execute batch
            stmt.executeUpdate();

            // STEP: Commit transaction
            c.commit();

            stmt.close();
            //System.out.println("success");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            try {
                c.rollback();
            } catch (Exception e1) {
                System.err.println(e1.getClass().getName() + ": " + e1.getMessage());
            }
        }
        //System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        //System.out.println();
    }

    private void populateOrders() {
        //System.out.println();
        //System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("Populate orders");

        insertOrders(1, 2, 1, 17, 12, 1);

        insertOrders(2, 2, 2, 1, 1, 1);
        insertOrders(2, 2, 2, 25, 0, 1);
        insertOrders(2, 2, 2, 16, 0, 1);

        insertOrders(3, 7, 3, 65, 0, 1);
        insertOrders(3, 7, 3, 65, 0, 1);
        insertOrders(3, 7, 3, 68, 0, 1);
        insertOrders(3, 7, 3, 77, 0, 1);

        insertOrders(4, 8, 4, 11, 0, 1);
        insertOrders(5, 1, 5, 12, 1, 1);
        insertOrders(6, 10, 6, 11, 1, 1);
        insertOrders(7, 3, 7, 11, 1, 2);
        insertOrders(8, 3, 8, 11, 3, 2);
        insertOrders(9, 3, 9, 11, 3, 3);
        insertOrders(10, 4, 10, 12, 2, 2);
        insertOrders(11, 3, 11, 12, 2, 3);
        insertOrders(12, 8, 12, 12, 3, 3);

        insertOrders(13, 6, 13, 13, 1, 3);
        insertOrders(13, 6, 13, 13, 3, 3);

        insertOrders(14, 5, 14, 13, 6, 1);
        insertOrders(15, 9, 15, 50, 7, 3);
        insertOrders(16, 9, 16, 0, 7, 1);
        insertOrders(17, 3, 17, 51, 10, 2);
        insertOrders(18, 1, 18, 51, 10, 1);

        insertOrders(19, 4, 19, 18, 9, 3);
        insertOrders(19, 4, 19, 18, 0, 3);
        insertOrders(19, 4, 19, 47, 13, 3);

        insertOrders(20, 7, 20, 38, 12, 3);
        insertOrders(20, 7, 20, 23, 0, 3);

        insertOrders(21, 2, 21, 62, 0, 2);
        insertOrders(21, 2, 21, 59, 0, 2);
        insertOrders(21, 2, 21, 56, 0, 2);

        insertOrders(22, 5, 22, 64, 0, 2);

        insertOrders(23, 8, 23, 80, 0, 1);
        insertOrders(23, 8, 23, 74, 0, 1);

        insertOrders(24, 3, 24, 82, 0, 1);
        insertOrders(24, 3, 24, 48, 0, 1);

        insertOrders(25, 6, 25, 1, 8, 1);
        insertOrders(25, 6, 25, 4, 13, 1);
        insertOrders(25, 6, 25, 20, 0, 1);

        System.out.println("success");
        //System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println();
    }
    private void insertOrders(int orderkey, int cafekey, int custkey,
        int drinkkey, int foodkey, int paymentkey){
        //System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        //System.out.println("Insert Cafe");
        
        try {
            String sql = "INSERT INTO orders(orderkey, cafekey, custkey, drinkkey, foodkey, paymentkey) " +
                "VALUES(?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = c.prepareStatement(sql);

            stmt.setInt(1, orderkey);
            stmt.setInt(2, cafekey);
            stmt.setInt(3, custkey);
            stmt.setInt(4, drinkkey);
            stmt.setInt(5, foodkey);
            stmt.setInt(6, paymentkey);

            // STEP: Execute batch
            stmt.executeUpdate();

            // STEP: Commit transaction
            c.commit();

            stmt.close();
            //System.out.println("success");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            try {
                c.rollback();
            } catch (Exception e1) {
                System.err.println(e1.getClass().getName() + ": " + e1.getMessage());
            }
        }
        //System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        //System.out.println();
    }
    
    private void populatePayment() {
        //System.out.println();
        //System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("Populate Payment");

        insertPayment(1, "Credit Card");
        insertPayment(2, "Debit Card");
        insertPayment(3, "Account");


        System.out.println("success");
        //System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println();
    }
    private void insertPayment(int paymentkey, String p_type){
        //System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        //System.out.println("Insert Cafe");
        
        try {
            String sql = "INSERT INTO payment(paymentkey, p_type) " +
                "VALUES(?, ?)";
            PreparedStatement stmt = c.prepareStatement(sql);

            stmt.setInt(1, paymentkey);
            stmt.setString(2, p_type);


            // STEP: Execute batch
            stmt.executeUpdate();

            // STEP: Commit transaction
            c.commit();

            stmt.close();
            //System.out.println("success");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            try {
                c.rollback();
            } catch (Exception e1) {
                System.err.println(e1.getClass().getName() + ": " + e1.getMessage());
            }
        }
        //System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        //System.out.println();
    }

    private void populateProfile() {
        //System.out.println();
        //System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("Populate Profile");

        insertProfile(1, 1, 122.32);
        insertProfile(2, 2, 2.56);
        insertProfile(3, 3, 5.79);
        insertProfile(4, 4, 1.23);
        insertProfile(5, 5, 4.59);
        insertProfile(6, 6, 0.25);
        insertProfile(7, 7, 15.56);
        insertProfile(8, 8, 78.56);
        insertProfile(9, 9, 8.20);
        insertProfile(10, 10, 92.89);

        insertProfile(11, 11, 25.00);
        insertProfile(12, 12, 26.13);
        insertProfile(13, 13, 0.45);
        insertProfile(14, 14, 12.32);
        insertProfile(15, 15, 4.87);
        insertProfile(16, 16, 13.21);
        insertProfile(17, 17, 8.97);
        insertProfile(18, 18, 19.25);
        insertProfile(19, 19, 15.00);
        insertProfile(20, 20, 17.95);

        insertProfile(21, 21, 5.67);
        insertProfile(22, 22, 2.10);
        insertProfile(23, 23, 15.32);
        insertProfile(24, 24, 18.32);
        insertProfile(25, 25, 45.59);

        System.out.println("success");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        //System.out.println();
    }
    private void insertProfile(int profilekey, int custkey, double acctbal){
        //System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        //System.out.println("Insert Cafe");
        
        try {
            String sql = "INSERT INTO profile(profilekey, custkey, acctbal) " +
                "VALUES(?, ?, ?)";
            PreparedStatement stmt = c.prepareStatement(sql);

            stmt.setInt(1, profilekey);
            stmt.setInt(2, custkey);
            stmt.setDouble(3, acctbal);

            // STEP: Execute batch
            stmt.executeUpdate();

            // STEP: Commit transaction
            c.commit();

            stmt.close();
            //System.out.println("success");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            try {
                c.rollback();
            } catch (Exception e1) {
                System.err.println(e1.getClass().getName() + ": " + e1.getMessage());
            }
        }
        //System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        //System.out.println();
    }


    
        
    private void Input1() {
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("Caffeine-free Menu");

        try {              
            String sql = "SELECT size, d_type, d_name, price " +
                         "FROM   drinks, menu_drinks " +
                         "WHERE  drinks.drinkkey = menu_drinks.drinkkey " + 
                            "and menukey = 2";

            PreparedStatement stmt = c.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            System.out.printf("%10s %20s %25s %10s\n", "Size", "Type", "Drink Name", "Price");
            System.out.println("--------------------------------------------------------------------");
                                
            while (rs.next()) {
                String size = rs.getString("size");
                String type = rs.getString("d_type");
                String name = rs.getString("d_name");
                Double price = rs.getDouble("price");

                System.out.printf("%10s %20s %25s %10s\n", size, type, name, price);
            }

            rs.close();
            stmt.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    }

    private void Q2() {
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("Q2");

        try {
            FileWriter writer = new FileWriter("output/2.out", false);
            PrintWriter printer = new PrintWriter(writer);

            

            printer.printf("%-40s %10s %10s\n", "nation", "numW", "totCap");

            printer.close();
            writer.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    }

    private void Q3() {
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("Q3");

        try {
            File fn = new File("input/3.in");
            FileReader reader = new FileReader(fn);
            BufferedReader in = new BufferedReader(reader);
            String nation = in.readLine();
            in.close();

            FileWriter writer = new FileWriter("output/3.out", false);
            PrintWriter printer = new PrintWriter(writer);




            printer.printf("%-20s %-20s %-40s\n", "supplier", "nation", "warehouse");

            printer.close();
            writer.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    }

    private void Q4() {
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("Q4");

        try {
            File fn = new File("input/4.in");
            FileReader reader = new FileReader(fn);
            BufferedReader in = new BufferedReader(reader);
            String region = in.readLine();
            int cap = Integer.parseInt(in.readLine());
            in.close();

            FileWriter writer = new FileWriter("output/4.out", false);
            PrintWriter printer = new PrintWriter(writer);

            printer.printf("%-40s %10s\n", "warehouse", "capacity");

            printer.close();
            writer.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    }

    private void Q5() {
        System.out.println();
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("Q5");

        try {
            File fn = new File("input/5.in");
            FileReader reader = new FileReader(fn);
            BufferedReader in = new BufferedReader(reader);
            String nation = in.readLine();
            in.close();

            FileWriter writer = new FileWriter("output/5.out", false);
            PrintWriter printer = new PrintWriter(writer);

            printer.printf("%-20s %20s\n", "region", "capacity");

            printer.close();
            writer.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    }


    private void userInputs(int x){
        switch (x){
            case 1: System.out.println("Hello");
                break;
        }
    }

    public static void main(String args[]) {
        CoffeeShop sj = new CoffeeShop();
        
        sj.openConnection("coffeeshop.sqlite");

        

        sj.dropTables();
        sj.createTables();

        sj.populateCafe();
        sj.populateCustomer();
        sj.populateDrinks();
        sj.populateFood();
        sj.populateMenuDrinks();
        sj.populateMenuFood();
        sj.populateMenu();
        sj.populateOrderDetail();
        sj.populateOrders();
        sj.populatePayment();
        sj.populateProfile();
        
        sj.Input1();
        
        /*
        System.out.println("Enter a number for the following statements: ");

        System.out.println("1: Caffeine-free Menu");
        System.out.println("2: Meat-free Menu");
        System.out.println("3: Coffee Only");
        System.out.println("4: Tea Only");
        System.out.println("5: Iced Drinks Only");
        System.out.println("6: Hot Drinks Only");

        Scanner sc = new Scanner(System.in);
        int input = sc.nextInt();
        
        
        sj.userInputs(input);
        */
        
        /*
        sj.Q1();
        sj.Q2();
        sj.Q3();
        sj.Q4();
        sj.Q5();
        */
        sj.closeConnection();
    }
}