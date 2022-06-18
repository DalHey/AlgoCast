
package com.iConomy.system;

import com.iConomy.events.AccountResetEvent;
import com.iConomy.events.AccountSetEvent;
import com.iConomy.events.AccountUpdateEvent;
import com.iConomy.iConomy;
import com.iConomy.util.Constants;
import com.iConomy.util.Misc;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;

/**
 * Controls player Holdings, and Bank Account holdings.
 * 
 * @author Nijikokun
 */
public class Holdings {
    private String name = "";
    private boolean bank = false;
    private int bankId = 0;

    public Holdings(String name) {
        this.name = name;
    }

    public Holdings(int id, String name) {
        this.bankId = id;
        this.name = name;
    }

    public Holdings(int id, String name, boolean bank) {
        this.bank = bank;
        this.bankId = id;
        this.name = name;
    }
