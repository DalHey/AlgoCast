
package com.iConomy;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import java.sql.PreparedStatement;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;

import java.util.LinkedList;
import java.util.Locale;
import java.util.Timer;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Event;
import org.bukkit.Server;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import com.iConomy.entity.Players;
import com.iConomy.net.Database;
import com.iConomy.system.Account;
import com.iConomy.system.Accounts;
import com.iConomy.system.Bank;
import com.iConomy.system.Banks;
import com.iConomy.system.Interest;
import com.iConomy.util.Constants;
import com.iConomy.system.Transactions;
import com.iConomy.util.Downloader;
import com.iConomy.util.FileManager;
import com.iConomy.util.Misc;

import java.text.DecimalFormat;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.server.PluginEnableEvent;

/**
 * iConomy by Team iCo
 *
 * @copyright     Copyright AniGaiku LLC (C) 2010-2011
 * @author          Nijikokun <nijikokun@gmail.com>
 * @author          Coelho <robertcoelho@live.com>
 * @author       ShadowDrakken <shadowdrakken@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 *