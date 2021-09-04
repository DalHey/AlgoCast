package com.iConomy.system;

import com.iConomy.events.AccountRemoveEvent;
import java.sql.ResultSet;

import com.iConomy.iConomy;
import com.iConomy.util.Constants;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class Account {
    private String name;

    public Account(String name) {
        this.name = name;
    }

    public int getId() {
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        int id = -1;

        try {
            conn = iConomy.getiCoDatabase().getConnection();
            ps = conn.prepareStatement("SELECT * FROM " + Constants.SQLTable + " WHERE username = ? LIMIT 1");
            ps.setString(1, name);
            rs = ps.executeQuery();

            if(rs.next()) {
                id = rs.getInt("id");
            }
        } catch (Exception e) {
            id = -1;
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

    public String getName() {
        return name;
    }

    public Holdings getHoldings() {
        return new Holdings(0, this.name);
    }

    public boolean createBankAccount(int bankID) {
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement ps = null;

        try {
            conn = iConomy.getiCoDatabase().getConnection();
            ps = conn.prepareStatement("INSERT INTO " + Constants.SQLTable + "_BankRelations (account_name, bank_id, holdings) VALUES (?, ?, ?)");
            ps.setString(1, this.name);
            ps.setInt(2, bankID);
            ps.setDouble(2, iConomy.getBank(bankID).getInitialHoldings());
            ps.executeUpdate();
        } catch (Exception e) {
            return false;
        } finally {
            if(ps != null)
                try { ps.close(); } catch (SQLException ex) { }

            if(conn != null)
                try { conn.close(); } catch (SQLException ex) { }
        }

        return true;
    }

    public boolean createBankAccount(String bank) {
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement ps = null;

        int bankID = iConomy.getBank(bank).getId();

        try {
            conn = iConomy.getiCoDatabase().getConnection();
            ps = conn.prepareStatement("INSERT INTO " + Constants.SQLTable + "_BankRelations (account_name, bank_id, holdings) VALUES (?, ?, ?)");
            ps.setString(1, this.name);
            ps.setInt(2, bankID);
            ps.setDouble(2, iConomy.getBank(bankID).getInitialHoldings());
            ps.executeUpdate();
        } catch (Exception e) {
            return false;
        } finally {
           