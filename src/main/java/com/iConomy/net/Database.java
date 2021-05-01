package com.iConomy.net;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.iConomy.util.Constants;
import com.iConomy.util.Misc;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.h2.jdbcx.JdbcConnectionPool;

public class Database {
    private JdbcConnectionPool h2pool;
    private String driver;
    private String dsn;
    private String username;
    private String password;

    public Database() {
        if(Misc.is(Constants.DatabaseType, new String[] { "sqlite", "h2", "h2sql", "h2db" })) {
            driver = "org.h2.Driver";
            dsn = "jdbc:h2:" + Constants.Plugin_Directory + File.separator + Constants.SQLDatabase + ";AUTO_RECONNECT=TRUE";
            username = "sa";
            password = "sa";
        } else if (Constants.DatabaseType.equalsIgnoreCase("mysql")) {
            driver = "com.mysql.jdbc.Driver";
            dsn = "jdbc:mysql://" + Constants.SQLHostname + ":" + Constants.SQLPort + "/" + Constants.SQLDatabase;
            username = Constants.SQLUsername;
            password = Constants.SQLPassword;
        }

        try {
            Class.forName(driver).newInstance();
        } catch (Exception e) { System.out.println("[iConomy] Driver error: " + e); }

        if(Misc.is(Constants.DatabaseType, new String[] { "sqlite", "h2", "h2sql", "h2db" })) {
            if(h2pool == null) {
                h2pool = JdbcConnectionPool.create(dsn, username, password);
            }
        }
    }

    public Connection getConnection() {
        try {
            if(username.equalsIgnoreCase("") && password.equalsIgnoreCase(""))
                return (DriverManager.getConnection(dsn));
            else {
                if(Misc.is(Constants.DatabaseType, new String[] { "sqlite", "h2", "h2sql", "h2db" })) {
                    return h2pool.getConnection();
                } else {
                    return (DriverManager.getConnection(dsn, username, password));
                }
            }
        } catch (SQLException e) {
            System.out.println("[iConomy] Could not create connection: " + e);
            return (null);
        }
    }

    public void close(Connection connection) {
        if(connection != null) {
            try {
                connection.close();
            } catch (SQLException ex) { }
        }
    }

    /**
     * Create the bank table if it doesn't exist already.
     * @throws Exception
     */
    public void setupBankTable() throws Exception {
        Connection conn = getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        if (Misc.is(Constants.DatabaseType, new String[] { "sqlite", "h2", "h2sql" })) {
            try {
                ps = conn.prepareStatement(
                    "CREATE TABLE " + Constants.SQLTable + "_Banks(" +
                        "id INT auto_increment PRIMARY KEY," +
                        "name VARCHAR(32)," +
                        "major VARCHAR(255)," +
                        "minor VARCHAR(255)," +
                        "initial DECIMAL(64,2)," +
                        "fee DECIMAL(64,2)"+
                    ");"
                );

                ps.executeUpdate();
            } catch(SQLException E) { }
        } else {
            DatabaseMetaData dbm = conn.getMetaData();
            rs = dbm.getTables(null, null, Constants.SQLTable + "_Banks", null);

            if (!rs.next()) {
                System.out.println("[iConomy] Creating table: " + Constants.SQLTable + "_Banks");

                ps = conn.prepareStatement(
                    "CREATE TABLE " + Constants.SQLTable + "_Banks(" +
                        "`id` INT(10) NOT NULL AUTO_INCREMENT," +
                        "`name` VARCHAR(32) NOT NULL," +
                        "`major` VARCHAR(255)," +
                        "`minor` VARCHAR(255)," +
                        "`initial` DECIMAL(64,2)," +
                        "`fee` DECIMAL(64,2),"+
                        "PRIMARY KEY (`id`)" +
                    ")"
                );

                ps.executeUpdate();

                System.out.println("[iConomy] Table Created.");
            }
        }

        if(ps != null)
            try { ps.close(); } catch (SQLException ex) { }

        if(rs != null)
            try { rs.close(); } catch (SQLException ex) { }

        if(conn != null)
            try { conn.close(); } catch (SQLException ex) { }
    }

    /**
     * Create the bank table if it doesn't exist already.
     * @throws Exception
     */
    public void setupBankRelationTable() throws Exception {
        Connection conn = getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        if (Misc.is(Constants.DatabaseType, new String[] { "sqlite", "h2", "h2sql" })) {
            try {
                ps = conn.prepareStatement(
               