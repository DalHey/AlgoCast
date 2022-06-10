
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