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

        if(Misc.is(Constants.DatabaseType, new String[] { "sqlite", "h2", "h