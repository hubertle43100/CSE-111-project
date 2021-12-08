// STEP: Import required packages
import java.sql.*;
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

    private void populateTables() {
        System.out.println();
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("Populate table");

        try{
            Statement stmt = c.createStatement();
            String sql = 
                "SELECT " + 
                    "nation.n_nationkey," +
                    "nation.n_name," +
                    "supplier.s_suppkey," +
                    "supplier.s_name," +
                    "count(*) as cnt," +
                    "sum(part.p_size) as capacity" + 
                "FROM" + 
                    "supplier" +
                    "INNER JOIN " + 
                    "lineitem ON lineitem.l_suppkey = supplier.s_suppkey" +
                    "INNER JOIN " + 
                    "part ON lineitem.l_partkey = part.p_partkey" +
                    "INNER JOIN " + 
                    "orders ON lineitem.l_orderkey = orders.o_orderkey" +
                    "INNER JOIN " + 
                    "customer ON customer.c_custkey = orders.o_custkey" +
                    "INNER JOIN " + 
                    "nation ON nation.n_nationkey = customer.c_nationkey" +
                "WHERE" +
                    "supplier.s_suppkey" +
                "GROUP BY nation.n_nationkey" +
                "ORDER BY cnt DESC, nation.n_name";


            stmt.execute(sql);

            // STEP: Commit transaction
            c.commit();

            stmt.close();
            System.out.println("success");
            
            c.commit();
            stmt.close();
            } 
            catch (Exception e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
                try {
                    c.rollback();
                } catch (Exception e1) {
                    System.err.println(e1.getClass().getName() + ": " + e1.getMessage());
                }
            }
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            System.out.println();
        }

    private void Q1() {
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("Q1");

        try {
            FileWriter writer = new FileWriter("output/1.out", false);
            PrintWriter printer = new PrintWriter(writer);
                
            String sql = "SELECT *" +
                         "FROM warehouse" +
                         "GROUP BY w_warehousekey";

            PreparedStatement stmt = c.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            printer.printf("%10s %-40s %10s %10s %10s\n", "wId", "wName", "wCap", "sId", "nId");
            System.out.println("-------------------------------");

            while (rs.next()) {
                int warehouse = rs.getInt("warehouse");
                System.out.printf("%10d\n", warehouse);
            }

            printer.close();
            writer.close();
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

    public static void main(String args[]) {
        CoffeeShop sj = new CoffeeShop();
        
        sj.openConnection("coffeeshop.sqlite");

        sj.dropTables();
        sj.createTables();
        //sj.populateTables();
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



/*
Assistance:
    https://www.tutorialspoint.com/java_mysql/java_mysql_drop_tables.htm


*/