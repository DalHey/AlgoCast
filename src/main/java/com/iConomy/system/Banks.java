
package com.iConomy.system;

import com.iConomy.iConomy;
import com.iConomy.util.Constants;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Banks, holder of all banks.
 * @author Nijikokun
 */
public class Banks {
    
    /**
     * Check and see if the bank exists through id.
     *
     * @param id
     * @return Boolean
     */
    public boolean exists(int id) {
        if(!Constants.Banking)
            return false;

        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        boolean exists = false;

        try {
            conn = iConomy.getiCoDatabase().getConnection();
            ps = conn.prepareStatement("SELECT * FROM " + Constants.SQLTable + "_Banks WHERE id = ? LIMIT 1");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            exists = rs.next();
        } catch (Exception e) {
            exists = false;
        } finally {
            if(ps != null)
                try { ps.close(); } catch (SQLException ex) { }

            if(rs != null)
                try { rs.close(); } catch (SQLException ex) { }

            if(conn != null)
                try { conn.close(); } catch (SQLException ex) { }
        }

        return exists;
    }

    /**
     * Check and see if the bank actually exists, through name.
     *
     * @param name
     * @return Boolean
     */
    public boolean exists(String name) {
        if(!Constants.Banking)
            return false;

        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        boolean exists = false;

        try {
            conn = iConomy.getiCoDatabase().getConnection();
            ps = conn.prepareStatement("SELECT * FROM " + Constants.SQLTable + "_Banks WHERE name = ? LIMIT 1");
            ps.setString(1, name);
            rs = ps.executeQuery();
            exists = rs.next();
        } catch (Exception e) {
            exists = false;
        } finally {
            if(ps != null)
                try { ps.close(); } catch (SQLException ex) { }

            if(rs != null)
                try { rs.close(); } catch (SQLException ex) { }

            if(conn != null)
                try { conn.close(); } catch (SQLException ex) { }
        }

        return exists;
    }

    /**
     * Fetch the id through the name, no questions.
     *
     * @param name
     * @return Integer
     */
    private int getId(String name) {
        if(!Constants.Banking)
            return -1;

        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        int id = 0;

        try {
            conn = iConomy.getiCoDatabase().getConnection();
            ps = conn.prepareStatement("SELECT id FROM " + Constants.SQLTable + "_Banks WHERE name = ? LIMIT 1");
            ps.setString(1, name);
            rs = ps.executeQuery();

            if(rs.next()) {
                id = rs.getInt("id");
            }
        } catch (Exception e) {
            id = 0;
        } finally {
            if(ps != null)
                try { ps.close(); } catch (SQLException ex) { }

            if(rs != null)
                try { rs.close(); } catch (SQLException ex) { }

            if(conn != null)
                try { conn.close(); } catch (SQLException ex) { }
        }

        return id;
    }

    /**
     * Create a totally customized bank.
     *
     * @param name
     * @param currency
     * @param currency_plural
     * @param initial
     * @return Bank
     */
    public Bank create(String name, String major, String minor, double initial, double fee) {
        if(!Constants.Banking)
            return null;

        if(!exists(name)) {
            Connection conn = null;
            ResultSet rs = null;
            PreparedStatement ps = null;

            try {
                conn = iConomy.getiCoDatabase().getConnection();
                ps = conn.prepareStatement("INSERT INTO " + Constants.SQLTable + "_Banks(name, major, minor, initial, fee) VALUES (?, ?, ?, ?, ?)");

                ps.setString(1, name);
                ps.setString(2, Constants.Major.get(0) + "," + Constants.Major.get(1));
                ps.setString(3, Constants.Minor.get(0) + "," + Constants.Minor.get(1));
                ps.setDouble(4, initial);
                ps.setDouble(5, fee);

                ps.executeUpdate();
            } catch (Exception e) {
                System.out.println("[iConomy] Failed to set holdings balance: " + e);
            } finally {
                if(ps != null)
                    try { ps.close(); } catch (SQLException ex) { }

                if(conn != null)
                    try { conn.close(); } catch (SQLException ex) { }
            }
        }

        return new Bank(name);
    }

    /**
     * Uses the default settings for a bank upon creation with a different name.
     * @param name
     * @return Bank
     */
    public Bank create(String name) {
        if(!Constants.Banking)
            return null;

        if(!exists(name)) {
            Connection conn = null;
            ResultSet rs = null;
            PreparedStatement ps = null;

            try {
                conn = iConomy.getiCoDatabase().getConnection();
                ps = conn.prepareStatement("INSERT INTO " + Constants.SQLTable + "_Banks(name, major, minor, initial, fee) VALUES (?, ?, ?, ?, ?)");
