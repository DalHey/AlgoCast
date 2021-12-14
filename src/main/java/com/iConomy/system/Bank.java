package com.iConomy.system;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import com.iConomy.iConomy;
import com.iConomy.util.Constants;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

public class Bank {
    private int id = 0;
    private String name = "";

    public Bank(String name) {
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        int id = 0;

        try {
            conn = iConomy.getiCoDatabase().getConnection();
            ps = conn.prepareStatement("SELECT id FROM " + Constants.SQLT