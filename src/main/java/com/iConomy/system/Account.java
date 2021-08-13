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
           