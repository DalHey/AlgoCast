package com.iConomy.util;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import org.bukkit.configuration.file.YamlConfiguration;

public class Constants {
    // Code name
    public static final String Codename = "Eruanna";

    // Nodes
    private static String[] nodes = new String[] {
        "System.Banking.Enabled:false",
        "System.Logging.Enabled:false",
        "System.Interest.Enabled:false",
        "System.Interest.Announce.Enabled:false",

        "System.Default.Account.Holdings:30.0",
        "System.Default.Currency.Major:[ 'Dollar', 'Dollars' ]",
        "System.Default.Currency.Minor:[ 'Coin', 'Coins' ]",

        "System.Default.Bank.Name:iConomy",
        "System.Default.Bank.Account.Fee:30.0",
        "System.Default.Bank.Account.Holdings:30.0",
        "System.Default.Bank.Currency.Major:[ 'Dollar', 'Dollars' ]",
        "System.Default.Bank.Currency.Minor:[ 'Coin', 'Coins' ]",

        "System.Banking.Accounts.Multiple:true",

        "System.Formatting.Minor:true",
        "System.Formatting.Seperate:false",

        "System.Interest.Online:true",
        "System.Interest.Interval.Seconds:60",
        "System.Interest.Amount.Cutoff:0.0",
        "System.Interest.Amount.On:Players",
        "System.Interest.Amount.Percent:0.0",
        "System.Interest.Amount.Minimum:1",
        "System.Interest.Amount.Maximum:2",

        "System.Database.Type:H2SQL",
        "System.Database.Settings.Name:minecraft",
        "System.Database.Settings.Table:iConomy",

        "System.Database.Settings.MySQL.Hostname:localhost",
        "System.Database.Settings.MySQL.Port:3306",
        "System.Database.Settings.MySQL.Username:root",
        "System.Database.Settings.MySQ