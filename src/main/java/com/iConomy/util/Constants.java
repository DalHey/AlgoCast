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
        "Sys